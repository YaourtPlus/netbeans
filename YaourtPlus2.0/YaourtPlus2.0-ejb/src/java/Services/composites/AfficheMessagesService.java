/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.composites;

import Entities.MessagesEntity;
import Services.elementaires.MessageServiceLocal;
import java.util.Collections;
import java.util.Comparator;
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
        List<MessagesEntity> messages = messageService.getMessagesSinglePersonne(idPersonne, idUser);
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
