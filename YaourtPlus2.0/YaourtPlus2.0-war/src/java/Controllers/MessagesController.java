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

// Getters =====================================================================
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

// Setters =====================================================================
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

// Methodes ====================================================================
    /**
     * Récupération de l'id du premier filou de la liste des filous de l'utilisateur
     * @return l'id du premier filou de l'utilisateur
     */
    private int getFirstFilousId() {
        return filousService.getFirstFilousId(sessionController.getIdUtilisateur());
    }

    
    /**
     * Récupération de la conversation entre l'utilisateur et la personne dont l'id est stocké en attibut
     * @return la liste des messages de la conversation
     */
    public List<MessagesEntity> getMessages() {
        // Il n'y a pas de problèmes dans le cas où l'utilisateur n'a pas de filous, car la fonction n'est pas appelée.
        // On n'a donc pas de problème d'id.
        if (!redirectController.isParameter("id")) { // On arrive sur la page sans paramètres
            idPersonne = getFirstFilousId(); // On récupère le premier filou de la liste de filous
        }
        return afficheMessageService.getMessagesSinglePersonne(sessionController.getIdUtilisateur(), idPersonne);
    }

    /**
     * Envoie d'un message de l'utilisateur à la personne dont l'id est stocké en attribut
     * On ne peut pas passer par les méthodes du redirectController pour 
     * rediriger directement car il y a un problème de passage de valeur avec le 
     * selectOneMenu pour choisir le filous
     * 
     * @return l'url vers la page courante
     */
    public String envoieMessage() {
        messageService.ajoutMessage(message, sessionController.getIdUtilisateur(), idPersonne);
        /* Du fait de l'utilisation d'un h:selectOneMenu, on est obligé de redirect 
            en récupérant l'id de la view courante sinon l'id de la personne avec laquelle on discute 
            n'est pas pris en comtpe correctement
            (Pour le test, remplacer le return par redirectController.goToCurrentPage();
        */
        return redirectController.getViewId() + "?faces-redirect=true&id=" + idPersonne;
    }

    
    /**
     * Sélection d'un filou pour l'envoie de messages
     * @return l'url vers la page courante pour mettre à jour l'affichage de la conversation
     */
    public String selectFilous() {
        /* Du fait de l'utilisation d'un h:selectOneMenu, on est obligé de redirect 
            en récupérant l'id de la view courante sinon l'id de la personne avec laquelle on discute 
            n'est pas pris en comtpe correctement
            (Pour le test, remplacer le return par redirectController.goToCurrentPage();
        */
        return redirectController.getViewId() + "?faces-redirect=true&id=" + idPersonne;
    }
}
