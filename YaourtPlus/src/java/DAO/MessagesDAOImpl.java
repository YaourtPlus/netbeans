/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.Date;
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
public class MessagesDAOImpl implements MessagesDAO {

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
	public void save(MessagesEntity m) {
		m = em.merge(m);
		em.persist(m);
	}

	@Transactional
	@Override
	public void update(MessagesEntity m) {
		m = em.merge(m);
	}

	@Transactional
	@Override
	public void delete(MessagesEntity m) {
		m = em.merge(m);
		em.remove(m);
	}

// Lecture =====================================================================
	@Transactional(readOnly = true)
	@Override
	public MessagesEntity find(int id) {
		return em.find(MessagesEntity.class, id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<MessagesEntity> findAll() {
		Query q = em.createQuery("SELECT m FROM MessagesEntity m");
		return q.getResultList();
	}

	@Transactional(readOnly = true)
	@Override
	public List<MessagesEntity> findByAuteur(int auteurId) {
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<MessagesEntity> findByDate(Date date) {
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<MessagesEntity> findByDestinataire(int destinataireId) {
		return null;
	}
}
