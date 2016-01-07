/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.MessagesDAO;
import DAO.MessagesEntity;
import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import DAO.StatutsEntity;
import Enumerations.TypeNotifications;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olivier
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    MessagesDAO messagesDAO;

    @Resource
    PersonnesDAO personnesDAO;

    @Resource
    NotificationsService notificationService;

    @Override
    public void envoyerMessage(int idUser, int idDestinataire, String message) {
        if (message.length() == 0) {
            return;
        }

        PersonnesEntity sender = personnesDAO.find(idUser);
        PersonnesEntity dest = personnesDAO.find(idDestinataire);

        MessagesEntity newMessagesEntity = new MessagesEntity(message, Calendar.getInstance().getTime());
        newMessagesEntity.setEmetteur(sender);
        newMessagesEntity.setDestinataire(dest);

        int idMessage = messagesDAO.save(newMessagesEntity);

        personnesDAO.ajoutMessageEnvoi(sender, newMessagesEntity);
        personnesDAO.ajoutMessageRecu(dest, newMessagesEntity);

        // Création d'une notification auprès du destinataire
        notificationService.createNotification(TypeNotifications.notifMessage, sender, dest, idMessage);
    }

    @Override
    public String getMessages(int idUser) {
        List<MessagesEntity> messages = messagesDAO.findByDestinataire(idUser);
        messages.addAll(messagesDAO.findByAuteur(idUser));
        Collections.sort(messages, new Comparator<MessagesEntity>() {

            @Override
            public int compare(MessagesEntity o1, MessagesEntity o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        String message = "";
        for (MessagesEntity me : messages) {
            message += "<div class=\"message\">"; // Conteneur du statut
            message += me.getEmetteur().getPrenom() + " " + me.getEmetteur().getNom();
            message += "<div class=\"message-texte\">"; // Conteneur du texte du statut
            message += me.getTexte();
            message += "<br/>";

            message += "</div>";
            message += "</div>";
        }
        return message;
    }

    /**
     * Récupère les messages envoyés par une personne à l'utilisateur
     * @param idUser l'id de l'utilisateur
     * @param idPersonne l'id de la personne qui a envoyé les messages
     * @return un string des messages formatés
     */
    public String getMessagesSinglePersonne(int idUser, int idPersonne) {
        List<MessagesEntity> messages = messagesDAO.findByPersonne(idPersonne, idUser);
        Collections.sort(messages, new Comparator<MessagesEntity>() {

            @Override
            public int compare(MessagesEntity o1, MessagesEntity o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        String message = "";
        for (MessagesEntity me : messages) {
            message += "<div class=\"message\">"; // Conteneur du statut
            message += me.getEmetteur().getPrenom() + " " + me.getEmetteur().getNom();
            message += "<div class=\"message-texte\">"; // Conteneur du texte du statut
            message += me.getTexte();
            message += "<br/>";

            message += "</div>";
            message += "</div>";
        }
        return message;
    }
}
