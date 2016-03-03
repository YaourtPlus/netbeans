/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.elementaires;

import DAO.MessagesDAO;
import DAO.NotificationsDAO;
import DAO.PersonnesDAO;
import DAO.StatutsDAO;
import Entities.MessagesEntity;
import Entities.NotificationsCommentaireEntity;
import Entities.NotificationsEntity;
import Entities.NotificationsFilouEntity;
import Entities.NotificationsLegerEntity;
import Entities.NotificationsLourdEntity;
import Entities.NotificationsMessageEntity;
import Entities.NotificationsStatutEntity;
import Entities.PersonnesEntity;
import Entities.StatutsEntity;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Olivier
 */
@Stateless
public class NotificationService implements NotificationServiceLocal {

    @EJB
    PersonnesServiceLocal personneService;

    @EJB
    StatutsDAO statutDAO;

    @EJB
    MessagesDAO messageDAO;

    @EJB
    NotificationsDAO notificationDAO;

    @EJB
    PersonnesDAO personneDAO;

    @EJB
    MessageServiceLocal messageService;

    
    /**
     * Création d'une notification de commentaire
     * 
     * @param idNotifieur id de la personne qui émet la notificaiton
     * @param idDestinataire id de la personne destinataire de la notification
     * @param idStatut id du statut sur lequel est posté le commentaire
     * @return true si la notification à été correctement envoyée/créée, false sinon
     */
    @Override
    public boolean createNotificationCommentaire(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idStatut) {
        // Création de l'entité
        NotificationsCommentaireEntity notif = new NotificationsCommentaireEntity(Calendar.getInstance().getTime());
        notif.setNotifieur(idNotifieur);
        notif.ajoutDestinataire(idDestinataire);

        // Récupération du statut
        StatutsEntity statut = statutDAO.find(idStatut);
        notif.setStatut(statut);
        
        // Persistance de la notificaiton
        notificationDAO.save(notif);

        // Ajout de la notification a l'auteur du statut
        return personneDAO.ajoutNotif(idNotifieur, idDestinataire, notif);
    }

    
    /**
     * Création d'une notification d'ajout de Filou
     * 
     * @param idNotifieur id de la personne qui émet la notification
     * @param idDestinataire id de la personne destinataire de la notification
     * @return true si la notification à été correctement envoyée/créée, false sinon
     */
    @Override
    public boolean createNotificationFilou(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire) {
        // Création de l'entité
        NotificationsFilouEntity notif = new NotificationsFilouEntity(Calendar.getInstance().getTime());
        notif.setNotifieur(idNotifieur);
        notif.ajoutDestinataire(idDestinataire);

        // Persistance de la notificaiton
        notificationDAO.save(notif);

        // Ajout de la notification a l'auteur du statut
        return personneDAO.ajoutNotif(idNotifieur, idDestinataire, notif);
    }

    
    /**
     * Création d'une notification de léger sur statut
     * 
     * @param idNotifieur id de la personne qui émet la notification
     * @param idDestinataire id de la personne destinataire de la notifiation
     * @param idStatut id du statut auquel le léger à été ajouté
     * @return true si la notification à été correctement envoyée/créée, false sinon
     */
    @Override
    public boolean createNotificationLeger(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idStatut) {
        // Création de l'entité
        NotificationsLegerEntity notif = new NotificationsLegerEntity(Calendar.getInstance().getTime());
        notif.setNotifieur(idNotifieur);
        notif.ajoutDestinataire(idDestinataire);
        
        StatutsEntity statut = statutDAO.find(idStatut);
        notif.setStatut(statut);
        
        // Persistance de la notification
        notificationDAO.save(notif);

        // Ajout de la notification a l'auteur du statut
        return personneDAO.ajoutNotif(idNotifieur, idDestinataire, notif);
    }

    
    /**
     * Création d'une notification de lourd sur statut
     * 
     * @param idNotifieur id de la personne qui émet la notification
     * @param idDestinataire id de la personne destinataire de la notification
     * @param idStatut id du statut auquel le lourd à été ajouté
     * @return true si la notification à été correctement envoyée/créée, false sinon
     */
    @Override
    public boolean createNotificationLourd(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idStatut) {
        // Création de l'entité
        NotificationsLourdEntity notif = new NotificationsLourdEntity(Calendar.getInstance().getTime());
        notif.setNotifieur(idNotifieur);
        notif.ajoutDestinataire(idDestinataire);

        StatutsEntity statut = statutDAO.find(idStatut);
        notif.setStatut(statut);
        
        // Persistance de la notification
        notificationDAO.save(notif);

        // Ajout de la notification a l'auteur du statut
        return personneDAO.ajoutNotif(idNotifieur, idDestinataire, notif);
    }

    
    /**
     * Création d'une notification de message
     * 
     * @param idNotifieur id de la personne qui émet la notification
     * @param idDestinataire id de la personne destinataire de la notification
     * @param idMessage id du message de la notification
     * @return true si la notification à été correctement envoyée/créée, false sinon
     */
    @Override
    public boolean createNotificationMessage(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idMessage) {
        // Création de l'entité
        NotificationsMessageEntity notif = new NotificationsMessageEntity(Calendar.getInstance().getTime());
        MessagesEntity message = messageDAO.find(idMessage);
        notif.setMessage(message);
        notif.setNotifieur(idNotifieur);
        notif.ajoutDestinataire(idDestinataire);

        // Persistance de la notification
        notificationDAO.save(notif);

        // Ajout de la notification a l'auteur du statut
        return personneDAO.ajoutNotif(idNotifieur, idDestinataire, notif);
    }

    
    /**
     * Création d'une notification de statut
     * 
     * @param idNotifieur id de la personne qui émet la notification
     * @param idDestinataire id de la personne destinataire de la notification
     * @param idStatut id du statut de la notification
     * @return true si la notification à été correctement envoyée/créée, false sinon
     */
    @Override
    public boolean createNotificationStatut(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idStatut) {
        // Création de l'entité
        NotificationsStatutEntity notif = new NotificationsStatutEntity(Calendar.getInstance().getTime());
        notif.setNotifieur(idNotifieur);
        notif.ajoutDestinataire(idDestinataire);

        StatutsEntity statut = statutDAO.find(idStatut);
        notif.setStatut(statut);
        
        // Persistance de la notification
        notificationDAO.save(notif);

        // Ajout de la notification a l'auteur du statut
        return personneDAO.ajoutNotif(idNotifieur, idDestinataire, notif);
    }

    
    /**
     * Récupération de la liste des notification d'un utilisateur
     * 
     * @param utilisateurId id de l'utilisateur dont on veut récupérer les notifications
     * @return la liste des notifications d'un utilisateur
     */
    @Override
    public List<NotificationsEntity> getNotifs(int utilisateurId) {
        // Récupération de l'utilisateur
        PersonnesEntity user = personneService.getPersonne(utilisateurId);
        
        // Remise à zéro du nombre de notification non lues
        user.resetNotif();
        
        // Inversion de l'ordre des notification
        List<NotificationsEntity> list = user.getNotificationRecues();
        Collections.reverse(list);
        return list;
    }

    
    /**
     * Récupération du statut de la notification
     * 
     * @param idNotif id de la notification dont on veut récupérer le statut
     * @return le statut lié à la notification
     */
    @Override
    public StatutsEntity getStatutNotif(int idNotif) {
        // Récupération de la notification
        NotificationsEntity notif = notificationDAO.find(idNotif);

        return notif.getStatut();
    }

    
    /**
     * Récupération de la conversation lié à la notification
     * 
     * @param idNotif id de la notification dont on veut récupérer la conversation
     * @return la liste des messages (conversation) liés à la notification
     */
    @Override
    public List<MessagesEntity> getMessagesNotif(int idNotif) {
        // Récupération de la notification
        NotificationsEntity notif = notificationDAO.find(idNotif);
        
        // Récupération du message originaire de la notification
        MessagesEntity msg = notif.getMessage();

        // Récupération de la conversation entre l'auteur du message et le destinataire
        List<MessagesEntity> messages = messageService.getMessagesSinglePersonne(msg.getEmetteur().getId(), msg.getDestinataire().getId());
        return messages;
    }

    
    /**
     * Récupération de la quantité de notifications non lues
     * 
     * @param utilisateurId id de l'utilisateur
     * @return la quantité de notifications non lues
     */
    @Override
    public int getNbNotifsNonLues(int utilisateurId) {
        // Récupération de l'utilisateur
        PersonnesEntity user = personneService.getPersonne(utilisateurId);

        return user.getNotifNonLues();
    }
}
