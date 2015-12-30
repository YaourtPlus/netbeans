/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.NotificationsDAO;
import DAO.NotificationsEntity;
import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import Enumerations.TypeNotifications;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author tbenoist
 */
@Service
public class FilousServiceImpl implements FilousService {

    @Resource
    PersonnesDAO personnesDAO;

    @Resource
    NotificationsDAO notificationsDAO;

    /**
     * @param idUtilisateur Permet d'enlever le user de la liste des amis créée
     * @return Un string contenant les informations des potentiels filous
     */
    @Override
    public String getFilous(int idUtilisateur) {
        String affichagePersonnes = "";
        List<PersonnesEntity> filous = personnesDAO.findAll();

        PersonnesEntity user = personnesDAO.find(idUtilisateur);
        filous.remove((PersonnesEntity) user);

        for (PersonnesEntity p : filous) {
            if (!user.getListFilous().contains(personnesDAO.find(p.getId()))) {
                affichagePersonnes += "<div class=\"col-lg-offset-1 col-lg-10\">";
                affichagePersonnes += p.getPrenom() + " " + p.getNom();
                affichagePersonnes += " <a href='ajout.htm?id=" + p.getId() + "' > Ajouter </a> ";
                affichagePersonnes += "</div>";
            }
        }
        return affichagePersonnes;
    }

    @Override
    public boolean ajoutFilous(int idUtilisateur, int idNouveauFilous) {
        PersonnesEntity utilisateur = personnesDAO.find(idUtilisateur);
        PersonnesEntity nouveauFilous = personnesDAO.find(idNouveauFilous);

        NotificationsEntity notif = new NotificationsEntity(new Date(), TypeNotifications.notifFilou.getId());
        
        notif.setNotifieur(utilisateur);
        
        notificationsDAO.save(notif);
        
        personnesDAO.ajoutNotif(utilisateur, nouveauFilous, notif);
        nouveauFilous.addNotif();
        
        return personnesDAO.ajoutFilous(utilisateur, nouveauFilous);
    }

    @Override
    public boolean suppressionFilous(int idUtilisateur, int idFilou) {
        PersonnesEntity utilisateur = personnesDAO.find(idUtilisateur);
        PersonnesEntity nouveauFilous = personnesDAO.find(idFilou);
        return personnesDAO.suppressionFilous(utilisateur, nouveauFilous);
    }
}
