/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.composites;

import Entities.MessagesEntity;
import Services.elementaires.MessageServiceLocal;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Thomas
 */
@Stateless
public class AfficheMessagesService implements AfficheMessagesServiceLocal {

    @EJB
    MessageServiceLocal messageService;
    
    @Override
    public List<MessagesEntity> getMessagesSinglePersonne(int idPersonne, int idUser) {
        // Récupération des messages émis par une seule personne
        List<MessagesEntity> messages = messageService.getMessagesSinglePersonne(idPersonne, idUser);
        // On inverse l'ordre des messages récupérés pour les afficher ensuite du plus récent au plus vieux.
        Collections.reverse(messages);
        return messages;
    }
}
