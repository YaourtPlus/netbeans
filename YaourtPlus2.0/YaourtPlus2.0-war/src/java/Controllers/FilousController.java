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

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public MessagesController getMessagesController() {
        return messagesController;
    }

    public void setMessagesController(MessagesController messagesController) {
        this.messagesController = messagesController;
    }

    public RedirectController getRedirectController() {
        return redirectController;
    }

    public void setRedirectController(RedirectController redirectController) {
        this.redirectController = redirectController;
    }
    
    
    public boolean getAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    // TO FIX => Access murController directly
    public List<PersonnesEntity> getFilous() {

        List<PersonnesEntity> list = filouService.getFilous(sessionController.getIdUtilisateur());
        return list;
    }

    public List<PersonnesEntity> getFilousAjout() {

        List<PersonnesEntity> list = filouService.getFilousPossibles(sessionController.getIdUtilisateur());
        return list;
    }

    public String ajoutFilou(int idFilous) {

        added = filouService.ajoutFilous(idFilous, sessionController.getIdUtilisateur());
        return redirectController.goToAddFilous();
    }

    public String suppressionFilou(int idFilous) {
       filouService.suppressionFilous(idFilous, sessionController.getIdUtilisateur());
       return redirectController.goToCurrentPage();
    }
}
