/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

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
import Enumerations.TypeNotifications;
import java.util.Calendar;
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

    @Override
    public boolean createNotificationCommentaire(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idStatut) {
        NotificationsCommentaireEntity notif = new NotificationsCommentaireEntity(Calendar.getInstance().getTime());
        notif.setNotifieur(idNotifieur);
        notif.ajoutDestinataire(idDestinataire);

        StatutsEntity statut = statutDAO.find(idStatut);
        notif.setStatut(statut);
        notificationDAO.save(notif);

        // Ajout de la notification a l'auteur du statut
        boolean add = false;
        add = personneDAO.ajoutNotif(idNotifieur, idDestinataire, notif);
        return add;
    }

    @Override
    public boolean createNotificationFilou(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire) {
        NotificationsFilouEntity notif = new NotificationsFilouEntity(Calendar.getInstance().getTime());
        notif.setNotifieur(idNotifieur);
        notif.ajoutDestinataire(idDestinataire);

        notificationDAO.save(notif);

        // Ajout de la notification a l'auteur du statut
        boolean add = false;
        add = personneDAO.ajoutNotif(idNotifieur, idDestinataire, notif);
        return add;
    }

    @Override
    public boolean createNotificationLeger(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idStatut) {
        NotificationsLegerEntity notif = new NotificationsLegerEntity(Calendar.getInstance().getTime());
        notif.setNotifieur(idNotifieur);
        notif.ajoutDestinataire(idDestinataire);

        StatutsEntity statut = statutDAO.find(idStatut);
        notif.setStatut(statut);
        notificationDAO.save(notif);

        // Ajout de la notification a l'auteur du statut
        boolean add = false;
        add = personneDAO.ajoutNotif(idNotifieur, idDestinataire, notif);
        return add;
    }

    @Override
    public boolean createNotificationLourd(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idStatut) {
        NotificationsLourdEntity notif = new NotificationsLourdEntity(Calendar.getInstance().getTime());
        notif.setNotifieur(idNotifieur);
        notif.ajoutDestinataire(idDestinataire);

        StatutsEntity statut = statutDAO.find(idStatut);
        notif.setStatut(statut);
        notificationDAO.save(notif);

        // Ajout de la notification a l'auteur du statut
        boolean add = false;
        add = personneDAO.ajoutNotif(idNotifieur, idDestinataire, notif);
        return add;
    }

    @Override
    public boolean createNotificationMessage(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idMessage) {
        NotificationsMessageEntity notif = new NotificationsMessageEntity(Calendar.getInstance().getTime());
        MessagesEntity message = messageDAO.find(idMessage);
        notif.setMessage(message);
        notif.setNotifieur(idNotifieur);
        notif.ajoutDestinataire(idDestinataire);

        notificationDAO.save(notif);

        // Ajout de la notification a l'auteur du statut
        boolean add = false;
        add = personneDAO.ajoutNotif(idNotifieur, idDestinataire, notif);
        return add;
    }

    @Override
    public boolean createNotificationStatut(PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idStatut) {
        NotificationsStatutEntity notif = new NotificationsStatutEntity(Calendar.getInstance().getTime());
        notif.setNotifieur(idNotifieur);
        notif.ajoutDestinataire(idDestinataire);

        StatutsEntity statut = statutDAO.find(idStatut);
        notif.setStatut(statut);
        notificationDAO.save(notif);

        // Ajout de la notification a l'auteur du statut
        boolean add;
        add = personneDAO.ajoutNotif(idNotifieur, idDestinataire, notif);
        return add;
    }

    @Override
    public List<NotificationsEntity> getNotifs(int utilisateurId) {
        PersonnesEntity user = personneService.getPersonne(utilisateurId);
        
        return user.getNotificationRecues();
    }

    @Override
    public StatutsEntity getStatut(int idNotif) {
        NotificationsEntity notif = notificationDAO.find(idNotif);
        
        return notif.getStatut();
    }

    @Override
    public List<MessagesEntity> getMessages(int idNotif) {
        NotificationsEntity notif = notificationDAO.find(idNotif);
        MessagesEntity msg = notif.getMessage();
        
        List<MessagesEntity> messages = messageService.getMessagesSinglePersonne(msg.getEmetteur().getId() , msg.getDestinataire().getId());
        return messages;
    }
}
