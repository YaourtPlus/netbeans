/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entities.FichiersEntity;
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
public class FichiersDAOImpl implements FichiersDAO {

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
    public void save(FichiersEntity f) {
        f = em.merge(f);
        em.persist(f);
    }

    
    @Override
    public void update(FichiersEntity f) {
        em.merge(f);
    }

    
    @Override
    public void delete(FichiersEntity f) {
        f = em.merge(f);
        em.remove(f);
    }

// Lecture =====================================================================
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

    @Override
    public List<FichiersEntity> findAll() {
        Query q = em.createQuery("SELECT f FROM FichiersEntity f");
        return q.getResultList();
    }
}
