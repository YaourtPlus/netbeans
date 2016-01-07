/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tbenoist
 */
@Repository
public class CommentairesDAOImpl implements CommentairesDAO {

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
    public void save(CommentairesEntity c) {
        c = em.merge(c);
        em.persist(c);
    }

    @Transactional
    @Override
    public void update(CommentairesEntity c) {
        em.merge(c);
    }

    @Transactional
    @Override
    public void delete(CommentairesEntity c) {
        c = em.merge(c);
        em.remove(c);
    }

// Custom transaction ==========================================================    
    @Transactional
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
    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    @Override
    public List<CommentairesEntity> findByAuteur(int auteurId) {
        return null;
    }

}
