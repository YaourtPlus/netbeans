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
 * @author Thomas
 */
@Repository
public class PersonnesStatutsDAOImpl implements PersonnesStatutsDAO {

    @PersistenceContext(unitName = "Yaourt_PU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Transactional
    @Override
    public void save(PersonnesStatutsEntity ps) {
        ps = em.merge(ps);
        em.persist(ps);
    }

    @Transactional
    @Override
    public void update(PersonnesStatutsEntity ps) {
        em.merge(ps);
    }

    @Transactional
    @Override
    public void delete(PersonnesStatutsEntity ps) {
        ps = em.merge(ps);
        em.remove(ps);
    }

    @Transactional
    @Override
    public TypeActions removeAction(PersonnesEntity p, StatutsEntity s) {
        List<PersonnesStatutsEntity> setPS = s.getStatutsActeurs();
        TypeActions typeAction = null;
        for (PersonnesStatutsEntity ps : setPS) {
            if (ps.getPersonne().equals(p) && ps.getStatut().equals(s)) {
                typeAction = ps.getTypeAction();
                ps.setTypeAction(TypeActions.noAction);
                em.merge(ps);
                break;
            }
        }
        s.setStatutsActeurs(setPS);
        p.setStatutsActeurs(setPS);
        
        em.merge(s);
        em.merge(p);
        
        return typeAction;
    }

    @Transactional(readOnly = true)
    @Override
    public PersonnesStatutsEntity find(PersonnesEntity p, StatutsEntity s){
        PersonnesStatutsEntity ps = new PersonnesStatutsEntity(p, s, 0);
        return em.find(PersonnesStatutsEntity.class, ps);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PersonnesStatutsEntity> findAll() {
        Query q = em.createQuery("SELECT ps FROM PersonnesStatutsEntity ps");
        return q.getResultList();
    }

}
