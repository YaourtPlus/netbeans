
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entities.MessagesEntity;
import Entities.NotificationsEntity;
import Entities.PersonnesEntity;
import Entities.StatutsEntity;
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
public class PersonnesDAOImpl implements PersonnesDAO {

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
    public void save(PersonnesEntity p) {
        p = em.merge(p);
        em.persist(p);
    }

    @Override
    public void update(PersonnesEntity p) {
        em.merge(p);
    }

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
     * Ajout d'un statut émis par l'utilisateur
     *
     * @param p Utilisateur
     * @param s Statut à ajouter
     * @return true si l'ajout s'est effectué correctement
     */
    @Override
    public boolean ajoutStatutEmis(PersonnesEntity p, StatutsEntity s) {
        // Ajout du statut dans la liste des statuts émis par l'utilisateur
        boolean add = p.ajoutStatutEmis(s);

        // Mise à jour de la BD
        if (add) {
            em.merge(p);
        }
        return add;
    }

    
    /**
     * Ajout d'un statut posté sur le mur de la personne
     *
     * @param p Utilisateur
     * @param s Statut à ajouter
     * @return true si l'ajout s'est effectué correctement
     */
    @Override
    public boolean ajoutStatutRecu(PersonnesEntity p, StatutsEntity s) {
        // Ajout du statut dans la liste des statuts émis par l'utilisateur
        boolean add = p.ajoutStatutRecu(s);

        // Mise à jour de la BD
        if (add) {
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
    @Override
    public boolean ajoutNotif(PersonnesEntity notifieur,
            PersonnesEntity destinataire, NotificationsEntity notif) {

        // Mise à jour des personnes
        boolean added = notifieur.ajoutNotificationEmise(notif);
        added = added && destinataire.ajoutNotificationsRecu(notif);

        // Incrémentation du nombre de notification du destinataire
        destinataire.addNotif();
        

        // Mise à jour de la BD
        if (added) {
            em.merge(notifieur);
            em.merge(destinataire);
        }

        return added;
    }

    
    /**
     * Ajout d'un message envoyé par une personne
     * 
     * @param sender Personne émettrice du message
     * @param newMessagesEntity message envoyé
     * @return true si l'ajout s'est correctement effecuté, false sinon
     */
    @Override
    public boolean ajoutMessageEnvoi(PersonnesEntity sender, MessagesEntity newMessagesEntity) {
        boolean add = sender.ajoutMessagesEmis(newMessagesEntity);

        if (add) {
            em.merge(sender);
        }

        return add;
    }

    
    /**
     * Ajout d'un message reçu par une personne
     * 
     * @param dest destinataire du message
     * @param newMessagesEntity message reçu
     * @return true si l'ajout s'est correctement effectué, false sinon
     */
    @Override
    public boolean ajoutMessageRecu(PersonnesEntity dest, MessagesEntity newMessagesEntity) {
        boolean add = dest.ajoutMessagesRecu(newMessagesEntity);

        if (add) {
            em.merge(dest);
        }

        return add;
    }
    
// Lecture =====================================================================
    @Override
    public PersonnesEntity find(int id) {
        Query q = em.createQuery("SELECT p FROM PersonnesEntity p where p.id = :id");
        q.setParameter("id", id);
        try {
            return (PersonnesEntity) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Récupération d'une personne selon un login et un password
     * 
     * @param login login de la personne
     * @param password password de la personne
     * @return la personne correspondant au login et au password, null sinon
     */
    @Override
    public PersonnesEntity find(String login, String password) {
        Query q = em.createQuery("SELECT p FROM PersonnesEntity p "
                + "WHERE p.login = :login AND p.password = :password");
        q.setParameter("login", login);
        q.setParameter("password", password);
        PersonnesEntity p = null;
        if (q.getResultList().size() == 1) {
            p = (PersonnesEntity) q.getSingleResult();
        }
        return p;
    }

    
    /**
     * Récupération des filous d'une personne
     * 
     * @param id id de la personne dont on veut les filous
     * @return list des filous de la personne
     */
    @Override
    public List<PersonnesEntity> findFilous(int id) {
        Query q = em.createQuery("SELECT pl FROM PersonnesEntity P JOIN P.listFilous pl "
                + "WHERE P.id = :id");
        q.setParameter("id", id);
        return ((PersonnesEntity) q.getSingleResult()).getListFilous();
    }

    
    /**
     * Récupération d'un personne selon son login
     * 
     * @param login login de la personne
     * @return la personne correspondant au login, null sinon
     */
    @Override
    public PersonnesEntity findByLogin(String login) {
        Query q = em.createQuery("SELECT p FROM PersonnesEntity p "
                + "WHERE p.login = :login");
        q.setParameter("login", login);
        PersonnesEntity p = null;
        if (q.getResultList().size() == 1) {
            p = (PersonnesEntity) q.getSingleResult();
        }
        return p;
    }


    @Override
    public List<PersonnesEntity> findAll() {
        Query q = em.createQuery("SELECT p FROM PersonnesEntity p");
        return q.getResultList();
    }

}
