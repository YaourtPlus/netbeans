/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.elementaires;

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
        System.err.println(idDestinataire);
        PersonnesEntity dest = personneService.getPersonne(idDestinataire);

        
        MessagesEntity newMessagesEntity = new MessagesEntity(message, Calendar.getInstance().getTime());
        newMessagesEntity.setEmetteur(sender);
        newMessagesEntity.setDestinataire(dest);

        int idMessage = messagesDAO.save(newMessagesEntity);

        personneService.ajoutMessageEnvoi(sender, newMessagesEntity);
        System.err.println(newMessagesEntity);
        
        personneService.ajoutMessageRecu(dest, newMessagesEntity);

        // Création d'une notification auprès du destinataire
        notificationService.createNotificationMessage(sender, dest, idMessage);
        
    }

    @Override
    public List<MessagesEntity> getMessagesSinglePersonne(int idUser, int idPersonne) {
        return messagesDAO.findByPersonne(idPersonne, idUser);
    }
}
