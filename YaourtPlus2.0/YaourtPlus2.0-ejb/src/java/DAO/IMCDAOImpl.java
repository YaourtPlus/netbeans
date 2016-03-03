/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entities.IMCEntity;
import Entities.PersonnesEntity;
import java.util.List;
import javax.ejb.Local;
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
public class IMCDAOImpl implements IMCDAO {

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
    public void save(IMCEntity i) {
        i = em.merge(i);
        em.persist(i);
    }

    
    @Override
    public void update(IMCEntity i) {
        em.merge(i);
    }

    
    @Override
    public void delete(IMCEntity i) {
        i = em.merge(i);
        em.remove(i);
    }

// Lecture =====================================================================
    
    @Override
    public IMCEntity find(int id) {
        Query q = em.createQuery("SELECT i FROM IMCEntity i where i.id = ?");
        q.setParameter(1, id);
        try{
           return (IMCEntity) q.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    
    @Override
    public List<IMCEntity> findAll() {
        Query q = em.createQuery("SELECT i FROM IMCEntity i");
        return q.getResultList();
    }
// Custom ======================================================================
    @Override
    public void addIMC(PersonnesEntity auteur, PersonnesEntity allegeur) {
        IMCEntity imcAuteurStatut = auteur.getImc();
        IMCEntity imcAuteurLeger = allegeur.getImc();

        imcAuteurStatut.setValeur(imcAuteurStatut.getValeur()+(1/imcAuteurLeger.getValeur()));
        imcAuteurStatut = em.merge(imcAuteurStatut);
    }

    @Override
    public void removeIMC(PersonnesEntity auteur, PersonnesEntity allourdeur) {
        IMCEntity imcAuteurStatut = auteur.getImc();
        IMCEntity imcAuteurLourd = allourdeur.getImc();
        
        imcAuteurStatut.setValeur(imcAuteurStatut.getValeur()-(1/imcAuteurLourd.getValeur()));
        imcAuteurStatut = em.merge(imcAuteurStatut);}

}
