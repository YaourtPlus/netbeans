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
		s = em.merge(s);
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
}
