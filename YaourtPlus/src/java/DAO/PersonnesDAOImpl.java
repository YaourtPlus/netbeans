
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
public class PersonnesDAOImpl implements PersonnesDAO {

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
	public void save(PersonnesEntity p) {
		p = em.merge(p);
		em.persist(p);
	}

	@Transactional
	@Override
	public void update(PersonnesEntity p) {
		p = em.merge(p);
	}

	@Transactional
	@Override
	public void delete(PersonnesEntity p) {
		p = em.merge(p);
		em.remove(p);
	}

// Lecture =====================================================================
	@Transactional(readOnly = true)
	@Override
	public PersonnesEntity find(int id) {
		return em.find(PersonnesEntity.class, id);
	}

	@Transactional(readOnly = true)
	@Override
	public PersonnesEntity find(String login, String password){
		Query q = em.createQuery("SELECT p FROM PersonnesEntity p WHERE p.login = ? AND p.password = ?");
		q.setParameter(1, login);
		q.setParameter(2, password);
		PersonnesEntity p = null;
		if(q.getResultList().size() == 1){
			p = (PersonnesEntity) q.getSingleResult();
		}
		return p;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<PersonnesEntity> findAll() {
		Query q = em.createQuery("SELECT p FROM PersonnesEntity p");
		return q.getResultList();
	}

	@Transactional(readOnly = true)
	@Override
	public List<PersonnesEntity> findByNom(String nom) {
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<PersonnesEntity> findByPrenom(String prenom) {
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<PersonnesEntity> findByMail(String mail) {
		return null;
	}
}
