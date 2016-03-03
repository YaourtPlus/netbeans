/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.MessagesEntity;
import Entities.NotificationsEntity;
import Entities.StatutsEntity;
import Services.elementaires.NotificationServiceLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Thomas
 */
@ManagedBean
@ViewScoped
public class NotificationsController implements Serializable{
    private static final long serialVersionUID = 1L;

    private int idNotification;

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;

    @EJB
    NotificationServiceLocal notificationService;

    public NotificationsController() {
    }

// Getters =====================================================================
    public int getIdNotification() {
        return idNotification;
    }
    
    public SessionController getSessionController() {
        return sessionController;
    }
    
// Setters =====================================================================
    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }
    
    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }
    
// Methodes ====================================================================
    /**
     * Récupération des notifications de l'utilisateur
     * 
     * @return la liste des notifications de l'utilisateur
     */
    public List<NotificationsEntity> getNotifs() {
        return notificationService.getNotifs(sessionController.getIdUtilisateur());
    }
    
    
    /**
     * Récupération du statut associé à la notification sélectionnée 
     * 
     * @return le statut de la notification
     */
    public StatutsEntity getStatut(){
        return notificationService.getStatutNotif(idNotification);
    }
    
    
    /**
     * Récupération des messages associés à la notification sélectionnée
     * 
     * @return la conversation de la notification
     */
    public List<MessagesEntity> getMessages(){
        return notificationService.getMessagesNotif(idNotification);
    }
    
    
    /**
     * Récupération de la quantité de notifications non lues par l'utilisateur
     *
     * @return la quantité de notifications non lues par l'utilisateur
     */
    public int getNbNotifsNonLues(){
        return notificationService.getNbNotifsNonLues(sessionController.getIdUtilisateur());
    }
    
}
