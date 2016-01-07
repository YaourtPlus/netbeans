/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.CommentairesEntity;
import DAO.MessagesDAO;
import DAO.MessagesEntity;
import DAO.NotificationsDAO;
import DAO.NotificationsEntity;
import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import DAO.StatutsDAO;
import DAO.StatutsEntity;
import Enumerations.TypeNotifications;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tbenoist
 */
@Service
public class NotificationsServiceImpl implements NotificationsService {

    @Autowired
    ServletContext servletContext;

    @Resource
    NotificationsDAO notificationDAO;

    @Resource
    PersonnesDAO personneDAO;

    @Resource
    StatutsDAO statutDAO;

    @Resource
    MessagesDAO messageDAO;

    @Autowired
    StatutsService statutService;
    
    @Autowired
    MessageService messageService;

    @Override
    public boolean createNotification(TypeNotifications typeNotif, PersonnesEntity notifieur, PersonnesEntity destinataire, int id) {
        boolean add = false;
        if (!notifieur.equals(destinataire)) {
            NotificationsEntity notif = new NotificationsEntity(Calendar.getInstance().getTime(),
                    typeNotif.getId());
            notif.setNotifieur(notifieur);
            notif.ajoutDestinataire(destinataire);
            switch (typeNotif) {
                case notifStatut:
                case notifCommentaire:
                case notifLeger:
                case notifLourd:
                    StatutsEntity statut = statutDAO.find(id);
                    notif.setStatut(statut);
                    break;
                case notifMessage:
                    MessagesEntity message = messageDAO.find(id);
                    notif.setMessage(message);
                    break;
                default:
                    break;
            } // End switch
            // Création de la notification dans la BD
            notificationDAO.save(notif);
            // Ajout de la notification a l'auteur du statut
            add = personneDAO.ajoutNotif(notifieur, destinataire, notif);
        }
        return add;
    }

    @Override
    public String afficheData(int idUtilisateur, int idObject) {
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);

        StatutsEntity s = statutDAO.find(idObject);
        MessagesEntity m = null;
        if (s == null) {
            m = messageDAO.find(idObject);
        }

        String result = "";
        if (m != null) {
            messageService.get();
        } else if (s != null) {
            result = "Statut ";
            result += statutService.statutToString(s, user, "vueNotif");
        }
        return result;
    }

}
