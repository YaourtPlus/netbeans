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

    
    /**
     * Ajout d'un message
     * 
     * @param message Texte du message à ajouter
     * @param idUser id de l'utilisateur qui envoie le message
     * @param idDestinataire  id du destinataire du message
     */
    @Override
    public void ajoutMessage(String message, int idUser, int idDestinataire) {
        if (message.length() == 0) {
            return;
        }

        // Récupération de l'émetteur (utilisateur)
        PersonnesEntity sender = personneService.getPersonne(idUser);
        // Récupération du destinataire
        PersonnesEntity dest = personneService.getPersonne(idDestinataire);

        // Création de l'entité
        MessagesEntity newMessagesEntity = new MessagesEntity(message, Calendar.getInstance().getTime());
        // Mise à jour de l'émetteur et du destinataire
        newMessagesEntity.setEmetteur(sender);
        newMessagesEntity.setDestinataire(dest);

        // Sauvegarde dans la BD du message
        int idMessage = messagesDAO.save(newMessagesEntity);

        // Ajout du message dans la liste des messages envoyé par l'émetteur
        personneService.ajoutMessageEnvoi(sender, newMessagesEntity);
        
        // Ajout du message dans la liste des messages reçu par le destinataire
        personneService.ajoutMessageRecu(dest, newMessagesEntity);

        // Création d'une notification auprès du destinataire
        notificationService.createNotificationMessage(sender, dest, idMessage);
    }

    /**
     * Récupération des messages d'une conversation entre l'utilisateur et la personne sélectionnée
     * 
     * @param idUser id de l'utilisateur
     * @param idPersonne id de la personne de la conversation
     * @return la liste des messages de la conversation
     */
    @Override
    public List<MessagesEntity> getMessagesSinglePersonne(int idUser, int idPersonne) {
        // Récupération des messages de la conversation
        return messagesDAO.findByPersonne(idPersonne, idUser);
    }
}
