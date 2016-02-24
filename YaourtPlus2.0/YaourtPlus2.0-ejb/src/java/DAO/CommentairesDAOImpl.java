/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entities.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author tbenoist
 */
@Stateless
public class CommentairesDAOImpl implements CommentairesDAO {

    // Communication avec la 
    @PersistenceContext(unitName = "YaourtPlus2.0-ejbPU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

// Ecriture ====================================================================
    @Override
    public int save(CommentairesEntity c) {
        c = em.merge(c);
        em.persist(c);
        return c.getId();
    }

    @Override
    public void update(CommentairesEntity c) {
        em.merge(c);
    }

    @Override
    public void delete(CommentairesEntity c) {
        c = em.merge(c);
        em.remove(c);
    }

// Custom transaction ==========================================================
    @Override
    public boolean ajoutCommentaire(StatutsEntity s, CommentairesEntity c) {
        // Ajout du commentaire au statut
        boolean add = s.addCommentaire(c);

        // Mise à jour dans la BD
        if (add) {
            em.merge(s);
        }

        return add;
    }

// Lecture =====================================================================
    @Override
    public CommentairesEntity find(int id) {
        Query q = em.createQuery("SELECT c FROM CommentairesEntity c where c.id = ?");
        q.setParameter(1, id);
        try{
           return (CommentairesEntity) q.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    @Override
    public List<CommentairesEntity> findAll() {
        Query q = em.createQuery("SELECT c FROM CommentairesEntity c");
        return q.getResultList();
    }

    /**
     * NOT YET IMPLEMENTED
     * @param auteurId
     * @return 
     */
    @Override
    public List<CommentairesEntity> findByAuteur(int auteurId) {
        return null;
    }

}
