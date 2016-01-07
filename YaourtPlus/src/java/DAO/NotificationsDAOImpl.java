/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.Date;
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
public class NotificationsDAOImpl implements NotificationsDAO {

    // Communication avec la BD
    @PersistenceContext(unitName = "Yaourt_PU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    @Transactional
    public void setEm(EntityManager em) {
        this.em = em;
    }

// Ecriture ====================================================================
    @Transactional
    @Override
    public void save(NotificationsEntity n) {
        n = em.merge(n);
        em.persist(n);
    }

    @Transactional
    @Override
    public void update(NotificationsEntity n) {
        em.merge(n);
    }

    @Transactional
    @Override
    public void delete(NotificationsEntity n) {
        n = em.merge(n);
        em.remove(n);
    }

// Lecture =====================================================================
    @Transactional(readOnly = true)
    @Override
    public NotificationsEntity find(int id) {
        Query q = em.createQuery("SELECT n FROM NotificationsEntity n where n.id = ?");
        q.setParameter(1, id);
        try{
           return (NotificationsEntity) q.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    @Override
    public List<NotificationsEntity> findByNotifieur(int notifieurId) {
        return null;
    }

    /**
     * NOT YET IMPLEMENTED
     * @param date
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<NotificationsEntity> findByDate(Date date) {
        return null;
    }

    /**
     * NOT YET IMPLEMENTED
     * @param destinataireId
     * @return 
     */
    @Transactional(readOnly = true)
    @Override
    public List<NotificationsEntity> findByDestinataire(int destinataireId) {
        return null;
    }

}
