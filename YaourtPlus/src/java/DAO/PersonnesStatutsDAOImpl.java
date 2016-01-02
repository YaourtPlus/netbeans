/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Enumerations.TypeActions;
import java.util.List;
import java.util.Set;
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
     * Annule une action de l'utilisateur
     * Passe le type d'action à noAction
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

// Lecture =====================================================================
    @Transactional(readOnly = true)
    @Override
    public PersonnesStatutsEntity find(PersonnesEntity p, StatutsEntity s){
        PersonnesStatutsEntity ps = new PersonnesStatutsEntity(p, s, 0);
        return em.find(PersonnesStatutsEntity.class, ps);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PersonnesStatutsEntity> findAll() {
        Query q = em.createQuery("SELECT ps FROM PersonnesStatutsEntity ps");
        return q.getResultList();
    }

}
