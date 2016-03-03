/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.elementaires;

import DAO.PersonnesDAO;
import DAO.StatutsDAO;
import Entities.PersonnesEntity;
import Entities.StatutsEntity;
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
    
    
    /**
     * Ajout d'un léger à un statut
     * 
     * @param idStatut id du statut à modifier
     * @param idUtilisateur id de l'utilisateur qui à émis un léger
     */
    @Override
    public void ajoutLeger(int idStatut, int idUtilisateur) {
        // Récupération du statut
        StatutsEntity statut = statutDAO.find(idStatut);
        
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        
        // Récupération de l'auteur pour modifier son IMC
        PersonnesEntity auteur = statut.getAuteur();
        
        // Ajout du léger au statut
        statutDAO.addLeger(statut, user);

        // Création de la notification dans la BD
        notificationService.createNotificationLeger(user, auteur, statut.getId());
    }

    
    /**
     * Ajout d'un lourd à un statut
     * 
     * @param idStatut id du statut à modifier
     * @param idUtilisateur id de l'utilisateur qui a émis un lourd
     */
    @Override
    public void ajoutLourd(int idStatut, int idUtilisateur) {
        // Récupération du statut
        StatutsEntity statut = statutDAO.find(idStatut);
        
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        
        // Récupération de l'auteur pour modifier son IMC
        PersonnesEntity auteur = statut.getAuteur();
        
        // Ajout d'un lourd au statut
        statutDAO.addLourd(statut, user);

        // Création de la notification dans la BD
        notificationService.createNotificationLourd(user, auteur, statut.getId());
    }

    
    /**
     * Suppression d'un léger d'un statut
     * 
     * @param idStatut id du statut à modifier
     * @param idUtilisateur id de l'utilisateur qui a enlevé son léger
     */
    @Override
    public void suppressionLeger(int idStatut, int idUtilisateur) {
        // Récupération du statut
        StatutsEntity statut = statutDAO.find(idStatut);
        
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        
        // Suppression du léger du statut
        statutDAO.removeLeger(statut, user);
    }

    
    /**
     * Suppression d'un lourd d'un statut
     * 
     * @param idStatut id du statut à modifier
     * @param idUtilisateur id de l'utilisateur qui a enlevé son lourd
     */
    @Override
    public void suppressionLourd(int idStatut, int idUtilisateur) {
        // Récupération du statut
        StatutsEntity statut = statutDAO.find(idStatut);
        
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        
        // Suppression du lourd du statut
        statutDAO.removeLourd(statut, user);
    }

}
