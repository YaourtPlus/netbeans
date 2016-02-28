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
import Services.elementaires.StatutServiceLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Thomas
 */
@ManagedBean
@ViewScoped
public class NotificationsController {

    private int idNotification;

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;

    @EJB
    NotificationServiceLocal notificationService;

    public NotificationsController() {
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public int getIdNotification() {
        return idNotification;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }

    public List<NotificationsEntity> getNotifs() {
        return notificationService.getNotifs(sessionController.getIdUtilisateur());
    }
    
    public StatutsEntity getStatut(){
        return notificationService.getStatutNotif(idNotification);
    }
    
    public List<MessagesEntity> getMessages(){
        return notificationService.getMessagesNotif(idNotification);
    }
}
