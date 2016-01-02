/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Enumerations.TypeActions;
import java.util.List;
import java.util.Set;
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

    @Transactional(readOnly = true)
    @Override
    public List<StatutsEntity> findByAuteur(int auteurId) {
        return null;
    }

    @Transactional
    @Override
    public void addLeger(StatutsEntity s, PersonnesEntity p) {
        s.addLeger();

        PersonnesStatutsEntity ps = new PersonnesStatutsEntity(p, s, 1);

        if (s.addPersonnesStatuts(ps) && p.addPersonnesStatuts(ps)) {
            em.persist(ps);
        } else {
            setAction(p, s, TypeActions.leger);
        }
// Simplification de merge avec setAction possible ?
        em.merge(p);
        em.merge(s);
    }

    @Transactional
    @Override
    public void removeLeger(StatutsEntity s, PersonnesEntity p) {
        s.delLeger();
        em.merge(s);
    }

    @Transactional
    @Override
    public void addLourd(StatutsEntity s, PersonnesEntity p) {
        s.addLourd();

        PersonnesStatutsEntity ps = new PersonnesStatutsEntity(p, s, 2);
        
        if (s.addPersonnesStatuts(ps) && p.addPersonnesStatuts(ps)) {
            em.persist(ps);
        } else {
            setAction(p, s, TypeActions.lourd);
        }
        
        em.merge(p);
        em.merge(s);
    }

    @Transactional
    @Override
    public void removeLourd(StatutsEntity s, PersonnesEntity p) {
        s.delLourd();
        em.merge(s);
    }

    @Transactional
    public void setAction(PersonnesEntity p, StatutsEntity s, TypeActions action) {
        List<PersonnesStatutsEntity> setPS = s.getStatutsActeurs();
        for (PersonnesStatutsEntity ps : setPS) {
            if (ps.getPersonne().equals(p) && ps.getStatut().equals(s)) {
                ps.setTypeAction(TypeActions.leger);
                em.merge(ps);
                break;
            }
        }

        s.setStatutsActeurs(setPS);
        p.setStatutsActeurs(setPS);

        em.merge(s);
        em.merge(p);
    }
}
