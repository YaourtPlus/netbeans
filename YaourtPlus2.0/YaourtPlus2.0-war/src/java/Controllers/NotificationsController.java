/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.MessagesEntity;
import Entities.NotificationsEntity;
import Entities.StatutsEntity;
import Services.NotificationServiceLocal;
import Services.StatutServiceLocal;
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

    @ManagedProperty(value = "#{murController}")
    private MurController murController;

    @EJB
    NotificationServiceLocal notificationService;

    public NotificationsController() {
    }

    public MurController getMurController() {
        return murController;
    }

    public int getIdNotification() {
        return idNotification;
    }

    public void setMurController(MurController murController) {
        this.murController = murController;
    }

    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }

    public List<NotificationsEntity> getNotifs() {
        return notificationService.getNotifs(murController.getIdUtilisateur());
    }
    
    public StatutsEntity getStatut(){
        return notificationService.getStatut(idNotification);
    }
    
    public List<MessagesEntity> getMessages(){
        return notificationService.getMessages(idNotification);
    }
}
