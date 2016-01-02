/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.NotificationsEntity;
import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import DAO.StatutsDAO;
import DAO.StatutsEntity;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author tbenoist
 */
@Service
public class ProfilServiceImpl implements ProfilService {

    @Resource
    PersonnesDAO personnesDAO;

    @Resource
    StatutsDAO statutDAO;

    @Override
    public PersonnesEntity getPersonne(int id) {
        return personnesDAO.find(id);
    }

    @Override
    public String getFilous(int id) {
        PersonnesEntity user = getPersonne(id);
        String listAmis = "<ul class=\"list-unstyled\">";
        for (PersonnesEntity p : user.getListFilous()) {
            listAmis += "<li>";
            listAmis += p.getPrenom() + " " + p.getNom() + " <a href='suppression.htm?id=" + p.getId() + "' > Supprimer </a> ";
            listAmis += "</li>";
        }
        listAmis += "</ul>";
        return listAmis;
    }

    @Override
    public boolean exists(String login) {
        return personnesDAO.findByLogin(login) != null;
    }

    @Override
    public boolean ajoutStatut(int idUser, String statut) {
        if (statut.length() == 0) {
            return false;
        }
        PersonnesEntity user = getPersonne(idUser);
        StatutsEntity newStatut = new StatutsEntity(statut, new Date());
        statutDAO.save(newStatut);
        return personnesDAO.ajoutStatut(user, newStatut);

    }

    @Override
    public String getNotifications(int idUtilisateur) {
        PersonnesEntity user = getPersonne(idUtilisateur);

        String afficheNotifications = "";
        for (NotificationsEntity n : user.getNotificationRecues()) {
            afficheNotifications += "<div class=\"col-lg-offset-1 col-lg-10\">";
            // Utiliser toString
            afficheNotifications += n.toString();
            afficheNotifications += "</div>";
        }
        
        user.resetNotif();
        
        return afficheNotifications;
    }
}
