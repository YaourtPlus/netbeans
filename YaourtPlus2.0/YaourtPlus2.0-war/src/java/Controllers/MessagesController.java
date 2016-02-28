/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.MessagesEntity;
import Services.elementaires.MessageServiceLocal;
import Services.composites.AfficheMessagesServiceLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author tbenoist
 */
@ManagedBean
@ViewScoped
public class MessagesController {

    private int idPersonne;

    private String message;

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;

    @ManagedProperty(value = "#{redirectController}")
    private RedirectController redirectController;

    @EJB
    MessageServiceLocal messageService;

    @EJB
    AfficheMessagesServiceLocal afficheMessageService;

    /**
     * Creates a new instance of MessagesController
     */
    public MessagesController() {
    }

    public int getIdPersonne() {
        return idPersonne;
    }

    public String getMessage() {
        return message;
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public RedirectController getRedirectController() {
        return redirectController;
    }

    
    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public void setRedirectController(RedirectController redirectController) {
        this.redirectController = redirectController;
    }

    public String envoieMessage() {
        messageService.ajoutMessage(message, sessionController.getIdUtilisateur(), idPersonne);
        return redirectController.goToCurrentPage();
    }

    public List<MessagesEntity> getMessages() {
        return afficheMessageService.getMessagesSinglePersonne(sessionController.getIdUtilisateur(), idPersonne);
    }
}
