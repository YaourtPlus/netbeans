/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.PersonnesDAO;
import DAO.StatutsDAO;
import Entities.PersonnesEntity;
import Entities.StatutsEntity;
import Enumerations.TypeNotifications;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Olivier
 */
@Stateless
public class IMCService implements IMCServiceLocal {

    @EJB
    StatutsDAO statutDAO;
    
    @EJB
    PersonnesDAO personneDAO;
    
    @EJB NotificationServiceLocal notificationService;
    
    @Override
    public void ajoutLeger(int idStatut, int idUtilisateur) {
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        PersonnesEntity auteur = statut.getAuteur();
        statutDAO.addLeger(statut, user);

        // Création de la notification dans la BD
        notificationService.createNotification(TypeNotifications.notifLeger, user, auteur, statut.getId());
    }

    @Override
    public void ajoutLourd(int idStatut, int idUtilisateur) {
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        PersonnesEntity auteur = statut.getAuteur();
        statutDAO.addLourd(statut, user);

        // Création de la notification dans la BD
        notificationService.createNotification(TypeNotifications.notifLourd, user, auteur, statut.getId());
    }

    @Override
    public void suppressionLeger(int idStatut, int idUtilisateur) {
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        PersonnesEntity auteur = statut.getAuteur();
        statutDAO.removeLeger(statut, user);

        // Création de la notification dans la BD
        //notificationService.createNotification(TypeNotifications.notifLourd, user, auteur, statut.getId());
    }

    @Override
    public void suppressionLourd(int idStatut, int idUtilisateur) {
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        PersonnesEntity auteur = statut.getAuteur();
        statutDAO.removeLourd(statut, user);

        // Création de la notification dans la BD
        //notificationService.createNotification(TypeNotifications.notifLeger, user, auteur, statut.getId());
    }

}
