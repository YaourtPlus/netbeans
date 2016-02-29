/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.MessagesEntity;
import Services.elementaires.MessageServiceLocal;
import Services.composites.AfficheMessagesServiceLocal;
import Services.composites.FilousServiceLocal;
import java.io.Serializable;
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
public class MessagesController implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idPersonne;

    private String message;

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;

    @ManagedProperty(value = "#{redirectController}")
    private RedirectController redirectController;

    @EJB
    MessageServiceLocal messageService;

    @EJB
    FilousServiceLocal filousService;

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

    private int getFirstFilousId() {
        return filousService.getFirstFilousId(sessionController.getIdUtilisateur());
    }

    public List<MessagesEntity> getMessages() {
        // Il n'y a pas de problèmes dans le cas où l'utilisateur n'a pas de filous, car la fonction n'est pas appelée.
        // On n'a donc pas de problème d'id.
        if (!redirectController.isParameter("id")) { // On arrive sur la page sans paramètres
            idPersonne = getFirstFilousId(); // On récupère le premier filou de la liste de filous
        }
        return afficheMessageService.getMessagesSinglePersonne(sessionController.getIdUtilisateur(), idPersonne);
    }

    /*
     * On ne peut pas passer par les méthodes du redirectController pour 
     * rediriger directement car il y a un problème de passage de valeur avec le 
     * selectOneMenu pour choisir le filous
     */
    public String envoieMessage() {
        messageService.ajoutMessage(message, sessionController.getIdUtilisateur(), idPersonne);
        return redirectController.getViewId() + "?faces-redirect=true&id=" + idPersonne;
    }

    public String selectFilous() {
        return redirectController.getViewId() + "?faces-redirect=true&id=" + idPersonne;
    }
}
