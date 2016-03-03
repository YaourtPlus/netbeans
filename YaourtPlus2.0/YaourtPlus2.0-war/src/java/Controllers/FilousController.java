/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.PersonnesEntity;
import Services.composites.FilousServiceLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author tbenoist
 */
@ManagedBean
@RequestScoped
public class FilousController {

    private boolean added;

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;
    
    @ManagedProperty(value = "#{redirectController}")
    RedirectController redirectController;
    
    @ManagedProperty(value = "#{messagesController}")
    private MessagesController messagesController;

    @EJB
    FilousServiceLocal filouService;

    public FilousController() {
        added = false;
    }

// Getters =====================================================================
    public SessionController getSessionController() {
        return sessionController;
    }

    public MessagesController getMessagesController() {
        return messagesController;
    }
    
    public RedirectController getRedirectController() {
        return redirectController;
    }
        
    public boolean getAdded() {
        return added;
    }
    
// Setters =====================================================================
    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }
    
    public void setMessagesController(MessagesController messagesController) {
        this.messagesController = messagesController;
    }
    
    public void setRedirectController(RedirectController redirectController) {
        this.redirectController = redirectController;
    }
    
    public void setAdded(boolean added) {
        this.added = added;
    }
    
// Methodes ====================================================================
    /**
     * Récupération de la liste des filous de l'utilisateur
     * 
     * @return la liste des filous de l'utilisateur
     */
    public List<PersonnesEntity> getFilous() {
        List<PersonnesEntity> list = filouService.getFilous(sessionController.getIdUtilisateur());
        return list;
    }

    
    /**
     * Récupération des filous possible pour l'ajout
     * 
     * @return la liste des filous possible pour l'ajout
     */
    public List<PersonnesEntity> getFilousAjout() {
        List<PersonnesEntity> list = filouService.getFilousPossibles(sessionController.getIdUtilisateur());
        return list;
    }

    
    /**
     * Ajout d'un filou
     * 
     * @param idFilous id du filou à ajouter
     * @return l'url de la page d'ajout de filous
     */
    public String ajoutFilou(int idFilous) {
        filouService.ajoutFilous(idFilous, sessionController.getIdUtilisateur());
        return redirectController.goToAddFilous();
    }
    

    /**
     * Suppression d'un filou
     * 
     * @param idFilous id du filou à supprimer
     * @return l'url de la page d'accueil
     */
    public String suppressionFilou(int idFilous) {
       filouService.suppressionFilous(idFilous, sessionController.getIdUtilisateur());
       return redirectController.goToCurrentPage();
    }
}
