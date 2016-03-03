/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entities.PersonnesEntity;
import Entities.PersonnesStatutsEntity;
import Entities.StatutsEntity;
import Enumerations.TypeActions;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Thomas
 */
@Stateless
public class PersonnesStatutsDAOImpl implements PersonnesStatutsDAO {

    @EJB
    StatutsDAO statutDAO;

    // Communication avec la BD
    @PersistenceContext(unitName = "YaourtPlus2.0-ejbPU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

// Ecriture ====================================================================
    public void setEm(EntityManager em) {
        this.em = em;
    }

    
    @Override
    public void save(PersonnesStatutsEntity ps) {
        ps = em.merge(ps);
        em.persist(ps);
    }

    
    @Override
    public void update(PersonnesStatutsEntity ps) {
        em.merge(ps);
    }

    
    @Override
    public void delete(PersonnesStatutsEntity ps) {
        ps = em.merge(ps);
        em.remove(ps);
    }

    
    /**
     * Création d'une action par l'utilisateurs sur le statut par le posteur.
     * Met le champ post de PersonnesStatutsEntity à true
     *
     * @param idStatut id du statut que la personne poste
     * @param p Personne ajoutant le léger
     */
    
    @Override
    public void addPost(int idStatut, PersonnesEntity p) {
        // Récupération du statut
        StatutsEntity s = statutDAO.find(idStatut);

        // Création d'une action entre la personne et le statut
        PersonnesStatutsEntity ps = new PersonnesStatutsEntity(p, s, 0, false, true);
        em.persist(ps);

        PersonnesStatutsEntity ps2 = find(p, s);
        // Mise à jour des listes
        s.addPersonnesStatuts(ps2);
        p.addPersonnesStatuts(ps2);

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

    
// Lecture =====================================================================
    /**
     * Récupération d'un PersonnesStatutEntity selon une personne et un statut
     * 
     * @param p personne du personneStatut
     * @param s statut du personneStatut
     * @return le personneStatut possédant la personne et le statut
     */
    @Override
    public PersonnesStatutsEntity find(PersonnesEntity p, StatutsEntity s) {
        Query q = em.createQuery("SELECT ps FROM PersonnesStatutsEntity ps WHERE ps.personne.id = :pID AND ps.statut.id = :sID");
        q.setParameter("pID", p.getId());
        q.setParameter("sID", s.getId());
        try{
           return (PersonnesStatutsEntity) q.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    
    @Override
    public List<PersonnesStatutsEntity> findAll() {
        Query q = em.createQuery("SELECT ps FROM PersonnesStatutsEntity ps");
        return q.getResultList();
    }

    
    /**
     * Test l'existance d'un personne statut dans la BD
     * 
     * @param p personne du personneStatut
     * @param s statut du personneStatut
     * @return true si le personneStatut existe, false sinon
     */
    @Override
    public boolean exist(PersonnesEntity p, StatutsEntity s) {
        Query q = em.createQuery("SELECT ps FROM PersonnesStatutsEntity ps WHERE ps.personne.id = :pID AND ps.statut.id = :sID");
        q.setParameter("pID", p.getId());
        q.setParameter("sID", s.getId());
        return q.getSingleResult() != null;
    }

}
