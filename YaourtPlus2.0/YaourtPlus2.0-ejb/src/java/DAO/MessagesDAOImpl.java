/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entities.MessagesEntity;
import java.util.Date;
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
public class MessagesDAOImpl implements MessagesDAO {

    // Communication avec la BD
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
    public int save(MessagesEntity m) {
        m = em.merge(m);
        em.persist(m);
        return m.getId();
    }

    
    @Override
    public void update(MessagesEntity m) {
        em.merge(m);
    }

    
    @Override
    public void delete(MessagesEntity m) {
        m = em.merge(m);
        em.remove(m);
    }

// Lecture =====================================================================
    
    @Override
    public MessagesEntity find(int id) {
        Query q = em.createQuery("SELECT m FROM MessagesEntity m where m.id = :id");
        q.setParameter("id", id);
        try {
            return (MessagesEntity) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    
    @Override
    public List<MessagesEntity> findAll() {
        Query q = em.createQuery("SELECT m FROM MessagesEntity m");
        return q.getResultList();
    }

    /**
     * NOT YET IMPLEMENTED
     *
     * @param auteurId
     * @return
     */
    
    @Override
    public List<MessagesEntity> findByAuteur(int auteurId) {
        Query q = em.createQuery("SELECT m FROM MessagesEntity m where m.emetteur.id = :id");
        q.setParameter("id", auteurId);
        return q.getResultList();
    }

    /**
     *
     * @param date
     * @return
     */
    
    @Override
    public List<MessagesEntity> findByDate(Date date) {
        return null;
    }

    /**
     *
     * @param destinataireId
     * @return
     */
    
    @Override
    public List<MessagesEntity> findByDestinataire(int destinataireId) {
        Query q = em.createQuery("SELECT m FROM MessagesEntity m where m.destinataire.id = :id");
        q.setParameter("id", destinataireId);
        return q.getResultList();
    }

    /**
     *
     * @param auteurId
     * @param destinataireId
     * @return
     */
    @Override
    public List<MessagesEntity> findByPersonne(int auteurId, int destinataireId) {
        Query q = em.createQuery("SELECT m FROM MessagesEntity m where "
                + "(m.emetteur.id = :emetteurId AND m.destinataire.id = :destinataireId) "
                + "OR "
                + "(m.emetteur.id = :destinataireId AND m.destinataire.id = :emetteurId)"); 
        
        
        q.setParameter("emetteurId", auteurId);
        q.setParameter("destinataireId", destinataireId);
        
        return q.getResultList();
    }
}
