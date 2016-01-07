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
public class FichiersDAOImpl implements FichiersDAO {

    // Communication avec la BD
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
    public void save(FichiersEntity f) {
        f = em.merge(f);
        em.persist(f);
    }

    @Transactional
    @Override
    public void update(FichiersEntity f) {
        em.merge(f);
    }

    @Transactional
    @Override
    public void delete(FichiersEntity f) {
        f = em.merge(f);
        em.remove(f);
    }

// Lecture =====================================================================
    @Transactional(readOnly = true)
    @Override
    public FichiersEntity find(int id) {
        Query q = em.createQuery("SELECT f FROM FichiersEntity f where f.id = ?");
        q.setParameter(1, id);
        try{
           return (FichiersEntity) q.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<FichiersEntity> findAll() {
        Query q = em.createQuery("SELECT f FROM FichiersEntity f");
        return q.getResultList();
    }

    /**
     * NOT YET IMPLEMENTED
     *
     * @param statutId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<FichiersEntity> findByStatuts(int statutId) {
        return null;
    }

    /**
     * NOT YET IMPLEMENTED
     *
     * @param messageId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<FichiersEntity> findByMessages(int messageId) {
        return null;
    }

}
