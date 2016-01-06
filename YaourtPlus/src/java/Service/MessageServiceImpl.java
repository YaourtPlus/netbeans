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
import java.util.Calendar;
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

    @Override
    public void envoyerMessage(int idUser, int idDestinataire, String message) {
        PersonnesEntity sender = personnesDAO.find(idUser);
        PersonnesEntity dest = personnesDAO.find(idDestinataire);

        MessagesEntity messagesEntity = new MessagesEntity(message, Calendar.getInstance().getTime(), sender, dest);

        messagesDAO.save(messagesEntity);

    }

    @Override
    public String getMessages(int idUser) {
        List<MessagesEntity> messages = messagesDAO.findByDestinataire(idUser);
        String message="";
        for (MessagesEntity me : messages) {
            message += "<div class=\"statuts\">"; // Conteneur du statut
            message += me.getEmetteur().getPrenom() + " " + me.getEmetteur().getNom();
            message += "<div class=\"statuts-texte\">"; // Conteneur du texte du statut
            message += me.getTexte();
            message += "<br/>";

            message += "</div>";
            message += "</div>";
        }
return message;
    }

}
