/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.NotificationsEntity;
import Services.NotificationServiceLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Thomas
 */
@ManagedBean
@RequestScoped
public class NotificationsController {

    @ManagedProperty(value = "#{murController}")
    private MurController murController;

    @EJB
    NotificationServiceLocal notificationService;
    
    public NotificationsController() {
    }

    public MurController getMurController() {
        return murController;
    }

    public void setMurController(MurController murController) {
        this.murController = murController;
    }

    public List<NotificationsEntity> getNotifs(){
        return notificationService.getNotifs(murController.getIdUtilisateur());
    }
}
