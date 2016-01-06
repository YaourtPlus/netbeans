/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

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
public class IMCDAOImpl implements IMCDAO {

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
    public void save(IMCEntity i) {
        i = em.merge(i);
        em.persist(i);
    }

    @Transactional
    @Override
    public void update(IMCEntity i) {
        em.merge(i);
    }

    @Transactional
    @Override
    public void delete(IMCEntity i) {
        i = em.merge(i);
        em.remove(i);
    }

// Lecture =====================================================================
    @Transactional
    @Override
    public IMCEntity find(int id) {
        return em.find(IMCEntity.class, id);
    }

    @Transactional
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
        IMCEntity imcAuteurLeger = allourdeur.getImc();

        imcAuteurStatut.setValeur(imcAuteurStatut.getValeur()-(1/imcAuteurLeger.getValeur()));
        imcAuteurStatut = em.merge(imcAuteurStatut);}

}
