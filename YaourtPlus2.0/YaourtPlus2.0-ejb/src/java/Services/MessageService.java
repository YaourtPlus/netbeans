/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.MessagesDAO;
import Entities.MessagesEntity;
import Entities.PersonnesEntity;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author tbenoist
 */
@Stateless
public class MessageService implements MessageServiceLocal {

    @EJB
    PersonnesServiceLocal personneService;

    @EJB
    MessagesDAO messagesDAO;

    @EJB
    NotificationServiceLocal notificationService;

    @Override
    public void ajoutMessage(String message, int idUser, int idDestinataire) {
        if (message.length() == 0) {
            return;
        }

        PersonnesEntity sender = personneService.getPersonne(idUser);
        PersonnesEntity dest = personneService.getPersonne(idDestinataire);

        MessagesEntity newMessagesEntity = new MessagesEntity(message, Calendar.getInstance().getTime());
        newMessagesEntity.setEmetteur(sender);
        newMessagesEntity.setDestinataire(dest);

        int idMessage = messagesDAO.save(newMessagesEntity);

        personneService.ajoutMessageEnvoi(sender, newMessagesEntity);
        personneService.ajoutMessageRecu(dest, newMessagesEntity);

        // Création d'une notification auprès du destinataire
        notificationService.createNotificationMessage(sender, dest, idMessage);
    }

    @Override
    public List<MessagesEntity> getMessagesSinglePersonne(int idUser, int idPersonne) {
        System.err.println(idPersonne);
        System.err.println(idUser);
        
        List<MessagesEntity> messages = messagesDAO.findByPersonne(idPersonne, idUser);
        System.err.println(messages);
        Collections.sort(messages, new Comparator<MessagesEntity>() {
            @Override
            public int compare(MessagesEntity o1, MessagesEntity o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        Collections.reverse(messages);
        return messages;
    }
}
