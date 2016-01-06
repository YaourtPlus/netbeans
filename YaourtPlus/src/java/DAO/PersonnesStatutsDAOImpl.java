/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Enumerations.TypeActions;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thomas
 */
@Repository
public class PersonnesStatutsDAOImpl implements PersonnesStatutsDAO {

    @Resource
    StatutsDAO statutDAO;
    
    // Communication avec la BD
    @PersistenceContext(unitName = "Yaourt_PU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

// Ecriture ====================================================================
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Transactional
    @Override
    public void save(PersonnesStatutsEntity ps) {
        ps = em.merge(ps);
        em.persist(ps);
    }

    @Transactional
    @Override
    public void update(PersonnesStatutsEntity ps) {
        em.merge(ps);
    }

    @Transactional
    @Override
    public void delete(PersonnesStatutsEntity ps) {
        ps = em.merge(ps);
        em.remove(ps);
    }

    /**
     * Création d'une action par sur le statut par le posteur. Met le champ post
     * de PersonnesStatutsEntity à true
     *
     * @param idStatut id du statut que la personne poste
     * @param p Personne ajoutant le léger
     */
    @Transactional
    @Override
    public void addPost(int idStatut, PersonnesEntity p) {
        // Récupération du statut
        StatutsEntity s = statutDAO.find(idStatut);
        
        // Création d'une action entre la personne et le statut
        PersonnesStatutsEntity ps = new PersonnesStatutsEntity(p, s, 1, false, true);
        
        em.persist(ps);
                
        // Mise à jour des listes
        s.addPersonnesStatuts(ps);
        p.addPersonnesStatuts(ps);

        // Update de la BD
        em.merge(p);
        em.merge(s);
    }

    /**
     * Annule une action de l'utilisateur Passe le type d'action à noAction
     *
     * @param p la oersonne effectuant l'action,
     * @param s le statut sur lequel l'action effectue l'action
     * @return null, noAction, leger, lourd
     */
    @Transactional
    @Override
    public TypeActions removeAction(PersonnesEntity p, StatutsEntity s) {
        // Récupération de la liste des PersonnesStatutsEntity associée 
        // au statut.
        // s ou p, peu importe puisque ce sont les même listes
        List<PersonnesStatutsEntity> setPS = s.getStatutsActeurs();

        TypeActions typeAction = null;

        // Parcours de la liste
        for (PersonnesStatutsEntity ps : setPS) {
            // Recherche de l'instance comprenant p et s
            if (ps.getPersonne().equals(p) && ps.getStatut().equals(s)) {
                // Récupération de la dernière action effectuée sur le statut
                typeAction = ps.getTypeAction();
                // Ecrasement de l'action
                ps.setTypeAction(TypeActions.noAction);
                // Mise à jour de la BD
                em.merge(ps);
                break;
            }
        }

        // Mise à jour des listes
        s.setStatutsActeurs(setPS);
        p.setStatutsActeurs(setPS);

        // Mise à jour de la BD
        em.merge(s);
        em.merge(p);

        return typeAction;
    }

    
    /**
     * Met à jour le PersonnesStatutsEntity concerné par le post
     * Passe le champ post à true pour indiquer que la personne à poster le statut
     * 
     * @param p Personne effectuant l'action sur le statut
     * @param s Statut sur lequel l'action est effectuée
     */
    private void setPost(PersonnesEntity p, StatutsEntity s) {
        // Récupération de la liste des PersonnesStatutsEntity associée 
        // au statut.
        // s ou p, peu importe puisque ce sont les mêmes listes
        List<PersonnesStatutsEntity> setPS = s.getStatutsActeurs();

        // Parcours de la liste
        for (PersonnesStatutsEntity ps : setPS) {
            // Recherche de l'instance comprenant p et s
            if (ps.getPersonne().equals(p) && ps.getStatut().equals(s)) {
                // Mise à jour de l'action
                ps.setPost(true);
                // Mise à jour dans la BD
                em.merge(ps);
                break;
            }
        }

        // Mise à jour des listes
        s.setStatutsActeurs(setPS);
        p.setStatutsActeurs(setPS);

        // Mise à jour de la BD
        em.merge(s);
        em.merge(p);
    }

// Lecture =====================================================================
    @Transactional(readOnly = true)
    @Override
    public PersonnesStatutsEntity find(PersonnesEntity p, StatutsEntity s) {
        Query q = em.createQuery("SELECT ps FROM PersonnesStatutsEntity ps WHERE ps.personne.id = ? AND ps.statut.id = ?");
        q.setParameter(1, p.getId());
        q.setParameter(2, s.getId());
        return (PersonnesStatutsEntity) q.getSingleResult();
    }

    @Transactional(readOnly = true)
    @Override
    public List<PersonnesStatutsEntity> findAll() {
        Query q = em.createQuery("SELECT ps FROM PersonnesStatutsEntity ps");
        return q.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public boolean exist(PersonnesEntity p, StatutsEntity s) {
        Query q = em.createQuery("SELECT ps FROM PersonnesStatutsEntity ps WHERE ps.personne.id = ? AND ps.statut.id = ?");
        q.setParameter(1, p.getId());
        q.setParameter(2, s.getId());
        return q.getSingleResult() != null;
    }

}
