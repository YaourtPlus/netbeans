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
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tbenoist
 */
@Service
public class FilousServiceImpl implements FilousService {

    @Autowired
    ServletContext servletContext;
    
    @Resource
    PersonnesDAO personneDAO;

    @Resource
    NotificationsDAO notificationsDAO;

    /**
     * Créé la liste des filous que l'utilisateur peut ajouter
     *
     * @param idUtilisateur id de l'utilisateur
     * @return Un string contenant les informations des potentiels filous
     */
    @Override
    public String getFilous(int idUtilisateur) {
        String affichagePersonnes = "";

        // Récupération des personnes inscrites dans la BD
        List<PersonnesEntity> filous = personneDAO.findAll();

        // On enlève l'utilisateur de la liste précédente pour ne pas qu'il 
        // puisse s'ajouter
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        filous.remove((PersonnesEntity) user);

        // Parcours de la liste
        for (PersonnesEntity p : filous) {
            // Filtre des personnes que l'utilisateur a déjà en ami
            if (!user.getListFilous().contains(personneDAO.find(p.getId()))) {
                // Mise en forme des donnése
                affichagePersonnes += "<div class=\"col-lg-offset-1 "
                        + "col-lg-10\">";
                affichagePersonnes += p.getPrenom() + " " + p.getNom();
                affichagePersonnes += " <a href='" + servletContext.getContextPath() + "/ajout.htm?id=" + p.getId()
                        + "' > Ajouter </a> ";
                affichagePersonnes += "</div>";
            }
        }
        return affichagePersonnes;
    }

    /**
     * Ajout d'un filou à l'utilisateur
     *
     * @param idUtilisateur id de l'utilisateur
     * @param idNouveauFilous id du filou à ajouter
     * @return true si l'ajout s'est effectué correctement, false sinon
     */
    @Override
    public boolean ajoutFilous(int idUtilisateur, int idNouveauFilous) {
        // Récupération des personnes
        PersonnesEntity utilisateur = personneDAO.find(idUtilisateur);
        PersonnesEntity nouveauFilous = personneDAO.find(idNouveauFilous);

        // Création d'une notification d'ajout en amis
        NotificationsEntity notif = new NotificationsEntity(new Date(),
                TypeNotifications.notifFilou.getId());
        notif.setNotifieur(utilisateur);
		
        // Création de la notification dans la BD
        notificationsDAO.save(notif);
		
        // Ajout de la notification au nouveau filou
        personneDAO.ajoutNotif(utilisateur, nouveauFilous, notif);

        // Ajout du filou
        return personneDAO.ajoutFilous(utilisateur, nouveauFilous);
    }

    /**
     * Suppression d'un filou de l'utilisateur
     *
     * @param idUtilisateur id de l'utilisateur
     * @param idFilou id du filou à supprimer
     * @return true si la suppression s'est effectué correctement, false sinon
     */
    @Override
    public boolean suppressionFilous(int idUtilisateur, int idFilou) {
        // Récupération des personnes
        PersonnesEntity utilisateur = personneDAO.find(idUtilisateur);
        PersonnesEntity nouveauFilous = personneDAO.find(idFilou);

        // Suppression du filou
        return personneDAO.suppressionFilous(utilisateur, nouveauFilous);
    }
}
