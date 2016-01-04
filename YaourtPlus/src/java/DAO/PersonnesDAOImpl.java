
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
	public void save(PersonnesEntity p) {
		p = em.merge(p);
		em.persist(p);
	}

	@Transactional
	@Override
	public void update(PersonnesEntity p) {
		em.merge(p);
	}

	@Transactional
	@Override
	public void delete(PersonnesEntity p) {
		p = em.merge(p);
		em.remove(p);
	}

	/**
	 * Ajout d'un filou par l'utilisateur
	 *
	 * @param p1 Utilisateur
	 * @param p2 Filous à ajouter
	 * @return true si l'ajout s'est effectué correctement
	 */
	@Transactional
	@Override
	public boolean ajoutFilous(PersonnesEntity p1, PersonnesEntity p2) {
		// Ajout dans la table de jointure
		boolean added = p1.ajoutFilous(p2);
		added = added && p2.ajoutFilousDe(p1);

		// Mise à jour de la BD
		if (added) {
			em.merge(p1);
			em.merge(p2);
		}
		return added;
	}

	/**
	 * Suppression d'un filou par l'utilisateur
	 *
	 * @param p1 Utilisateur
	 * @param p2 Filous à supprimer
	 * @return true si la suppression s'est fait correctemnt
	 */
	@Transactional
	@Override
	public boolean suppressionFilous(PersonnesEntity p1, PersonnesEntity p2) {
		// Suppression de la table de jointure
		boolean delete = p1.suppressionFilous(p2);
		delete = delete && p2.suppressionFilousDe(p1);

		// Mise à jour de la BD
		if (delete) {
			em.merge(p1);
			em.merge(p2);
		}
		return delete;
	}

	/**
	 * Ajout d'un statut par l'utilisateur
	 *
	 * @param p Utilisateur
	 * @param s Statut à ajouter
	 * @return true si l'ajout s'est effectué correctement
	 */
	@Transactional
	@Override
	public boolean ajoutStatut(PersonnesEntity p, StatutsEntity s) {
		// Ajout du statut dans la liste des statuts émis par l'utilisateur
		boolean add = p.ajoutStatut(s);
		// Mise à jour de l'auteur du statut
		s.setAuteur(p);

		// Mise à jour de la BD
		if (add) {
			em.merge(s);
			em.merge(p);
		}
		return add;
	}

	/**
	 * Ajout d'une notification
	 *
	 * @param notifieur Personne ayant fait l'action nécessitant une
	 * notification
	 * @param destinataire Personnes recevant la notification
	 * @param notif Notification à envoyer
	 * @return true si l'ajout s'est effectué correctement
	 */
	@Transactional
	@Override
	public boolean ajoutNotif(PersonnesEntity notifieur,
			PersonnesEntity destinataire, NotificationsEntity notif) {
		
		notif.ajoutDestinataire(destinataire);
		
		// Mise à jour des personnes
		boolean added = notifieur.ajoutNotificationEmise(notif);
		added = added && destinataire.ajoutNotificationsRecu(notif);

		// Incrémentation du nombre de notification du destinataire
		destinataire.addNotif();

		// Mise à jour de la BD
		if (added) {
			em.merge(notifieur);
			em.merge(destinataire);
			em.merge(notif);
		}

		return added;
	}

// Lecture =====================================================================
	@Transactional(readOnly = true)
	@Override
	public PersonnesEntity find(int id) {
		return em.find(PersonnesEntity.class, id);
	}

	@Transactional(readOnly = true)
	@Override
	public PersonnesEntity find(String login, String password) {
		Query q = em.createQuery("SELECT p FROM PersonnesEntity p "
				+ "WHERE p.login = ? AND p.password = ?");
		q.setParameter(1, login);
		q.setParameter(2, password);
		PersonnesEntity p = null;
		if (q.getResultList().size() == 1) {
			p = (PersonnesEntity) q.getSingleResult();
		}
		return p;
	}
	
	@Transactional(readOnly = true)
	@Override
	public PersonnesEntity findFilous(int id) {
		Query q = em.createQuery("SELECT P FROM PersonnesEntity P JOIN FETCH P.listFilous pl "
				+ "WHERE P.id = ?");
		q.setParameter(1, id);
		return (PersonnesEntity)q.getSingleResult();
	}
	
	
	@Transactional(readOnly = true)
	@Override
	public PersonnesEntity findByLogin(String login) {
		Query q = em.createQuery("SELECT p FROM PersonnesEntity p "
				+ "WHERE p.login = ?");
		q.setParameter(1, login);
		PersonnesEntity p = null;
		if (q.getResultList().size() == 1) {
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

	/**
	 * NOT YET IMPLEMENTED
	 *
	 * @param nom
	 * @return
	 */
	@Transactional(readOnly = true)
	@Override
	public List<PersonnesEntity> findByNom(String nom) {
		return null;
	}

	/**
	 * NOT YET IMPLEMENTED
	 *
	 * @param prenom
	 * @return
	 */
	@Transactional(readOnly = true)
	@Override
	public List<PersonnesEntity> findByPrenom(String prenom) {
		return null;
	}

	/**
	 * NOT YET IMPLEMENTED
	 *
	 * @param mail
	 * @return
	 */
	@Transactional(readOnly = true)
	@Override
	public List<PersonnesEntity> findByMail(String mail) {
		return null;
	}

}
