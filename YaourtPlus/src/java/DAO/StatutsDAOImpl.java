/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Enumerations.TypeActions;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tbenoist
 */
@Repository
public class StatutsDAOImpl implements StatutsDAO {

    // Communication avec la 
    @PersistenceContext(unitName = "Yaourt_PU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

// Ecriture ====================================================================
    @Transactional
    @Override
    public void save(StatutsEntity s) {
        s = em.merge(s);
        em.persist(s);
    }

    @Transactional
    @Override
    public void update(StatutsEntity s) {
        em.merge(s);
    }

    @Transactional
    @Override
    public void delete(StatutsEntity s) {
        s = em.merge(s);
        em.remove(s);
    }

    /**
     * Ajout d'un léger au statut
     * @param s Statut auquel le léger est ajouté
     * @param p Personne ajoutant le léger
     */
    @Transactional
    @Override
    public void addLeger(StatutsEntity s, PersonnesEntity p) {
        // Ajout du léger au statut
        s.addLeger();

        // Création d'une action entre la personne et le statut
        PersonnesStatutsEntity ps = new PersonnesStatutsEntity(p, s, 1, false);

        // Si non existence d'une action, on sauvegarde, sinon on met juste à 
        // jour
        if (s.addPersonnesStatuts(ps) && p.addPersonnesStatuts(ps)) {
            em.persist(ps);
        } else {
            setAction(p, s, TypeActions.leger);
        }
        
        // Update de la BD
        // Simplification de merge avec setAction possible ?
        em.merge(p);
        em.merge(s);
    }

    /**
     * Suppression d'un léger au statut
     * @param s Statut duquel le léger est supprimé
     * @param p Personne supprimant le léger
     * Suppression de l'action par l'utilisateur à définir ?
     */
    @Transactional
    @Override
    public void removeLeger(StatutsEntity s, PersonnesEntity p) {
        s.delLeger();
        em.merge(s);
    }

   
    /**
     * Ajout d'un lourd au statut
     * @param s Statut auquel le lourd est ajouté
     * @param p Personne ajoutant le lourd
     */
    @Transactional
    @Override
    public void addLourd(StatutsEntity s, PersonnesEntity p) {
        // Ajout du lourd au statut
        s.addLourd();

        // Création d'une action entre la personne et le statut
        PersonnesStatutsEntity ps = new PersonnesStatutsEntity(p, s, 2, false);

        // Si non existence d'une action, on sauvegarde, sinon on met juste à 
        // jour
        if (s.addPersonnesStatuts(ps) && p.addPersonnesStatuts(ps)) {
            em.persist(ps);
        } else {
            setAction(p, s, TypeActions.lourd);
        }

        // Update de la BD
        // Simplification de merge avec setAction possible ?
        em.merge(p);
        em.merge(s);
    }
    
    /**
     * Suppression d'un lourd au statut
     * @param s Statut duquel le lourd est supprimé
     * @param p Personne supprimant le lourd
     * Suppression de l'action par l'utilisateur à définir ?
     */
    @Transactional
    @Override
    public void removeLourd(StatutsEntity s, PersonnesEntity p) {
        s.delLourd();
        em.merge(s);
    }

    /**
     * Mise à jour de l'action de l'utilisateur sur le statut
     * @param p Personne effectuant l'action sur le statut
     * @param s Statut sur lequel l'action est effectuée
     * @param action Action effectuée sur le statut
     */
    @Transactional
    public void setAction(PersonnesEntity p, StatutsEntity s, TypeActions action) {
        // Récupération de la liste des PersonnesStatutsEntity associée 
        // au statut.
        // s ou p, peu importe puisque ce sont les même listes
        List<PersonnesStatutsEntity> setPS = s.getStatutsActeurs();
        
        // Parcours de la liste
        for (PersonnesStatutsEntity ps : setPS) {
            // Recherche de l'instance comprenant p et s
            if (ps.getPersonne().equals(p) && ps.getStatut().equals(s)) {
                // Mise à jour de l'action
                ps.setTypeAction(action);
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
    public StatutsEntity find(int id) {
        return em.find(StatutsEntity.class, id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StatutsEntity> findAll() {
        Query q = em.createQuery("SELECT s FROM StatutsEntity s");
        return q.getResultList();
    }

    /**
     * NOT YET IMPLEMENTED
     * @param auteurId
     * @return 
     */
    @Transactional(readOnly = true)
    @Override
    public List<StatutsEntity> findByAuteur(int auteurId) {
        return null;
    }

}
