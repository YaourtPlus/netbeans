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
import Entities.NotificationsEntity;
import Entities.PersonnesEntity;
import Entities.StatutsEntity;
import Enumerations.TypeNotifications;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Olivier
 */
@Stateless
public class NotificationService implements NotificationServiceLocal {

    @EJB
    StatutsDAO statutDAO;
    
    @EJB
    MessagesDAO messageDAO;
    
    @EJB 
    NotificationsDAO notificationDAO;
    
    @EJB
    PersonnesDAO personneDAO;
    
    @Override
    public boolean createNotification(TypeNotifications typeNotif, PersonnesEntity idNotifieur, PersonnesEntity idDestinataire, int idStatut) {
        boolean add = false;
        if (!idNotifieur.equals(idDestinataire)) {
            NotificationsEntity notif = new NotificationsEntity(Calendar.getInstance().getTime(),
                    typeNotif.getId());
            notif.setNotifieur(idNotifieur);
            notif.ajoutDestinataire(idDestinataire);
            switch (typeNotif) {
                case notifStatut:
                case notifCommentaire:
                case notifLeger:
                case notifLourd:
                    StatutsEntity statut = statutDAO.find(idStatut);
                    notif.setStatut(statut);
                    break;
                case notifMessage:
                    MessagesEntity message = messageDAO.find(idStatut);
                    notif.setMessage(message);
                    break;
                default:
                    break;
            } // End switch
            // Création de la notification dans la BD
            notificationDAO.save(notif);
            // Ajout de la notification a l'auteur du statut
            add = personneDAO.ajoutNotif(idNotifieur, idDestinataire, notif);
        }
        return add;
    }

    
}
