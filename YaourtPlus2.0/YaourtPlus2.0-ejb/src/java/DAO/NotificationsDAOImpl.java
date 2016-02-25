/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entities.NotificationsEntity;
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
public class NotificationsDAOImpl implements NotificationsDAO {

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
    public void save(NotificationsEntity n) {
        n = em.merge(n);
        em.persist(n);
    }

    
    @Override
    public void update(NotificationsEntity n) {
        em.merge(n);
    }

    
    @Override
    public void delete(NotificationsEntity n) {
        n = em.merge(n);
        em.remove(n);
    }

// Lecture =====================================================================
    @Override
    public NotificationsEntity find(int id) {
        Query q = em.createQuery("SELECT n FROM NotificationsEntity n where n.id = :id");
        q.setParameter("id", id);
        try{
           return (NotificationsEntity) q.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    @Override
    public List<NotificationsEntity> findAll() {
        Query q = em.createQuery("SELECT n FROM NotificationsEntity n");
        return q.getResultList();
    }

    /**
     * NOT YET IMPLEMENTED
     *
     * @param notifieurId
     * @return
     */
    @Override
    public List<NotificationsEntity> findByNotifieur(int notifieurId) {
        return null;
    }

    /**
     * NOT YET IMPLEMENTED
     * @param date
     * @return
     */
    @Override
    public List<NotificationsEntity> findByDate(Date date) {
        return null;
    }

    /**
     * NOT YET IMPLEMENTED
     * @param destinataireId
     * @return 
     */
    @Override
    public List<NotificationsEntity> findByDestinataire(int destinataireId) {
        return null;
    }

}
