/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Olivier
 */
@Entity
public class NotificationsMessageEntity extends NotificationsEntity{
    private static final long serialVersionUID = 1L;
        
    // Le message que peut référencer la notification
    @JoinColumn(name = "messageID")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private MessagesEntity message;
    
    public NotificationsMessageEntity() {
        this.setDate(Calendar.getInstance().getTime());
        this.setNotifieur(null);
        //this.setStatut(null);
        //this.setMessage(null);
        this.message = null;
    }
    
    public NotificationsMessageEntity(Date date) {
        this.setDate(date);
        this.setNotifieur(null);
        //this.setStatut(null);
        //this.setMessage(null);
        this.message = null;
    }

    public MessagesEntity getMessage() {
        return message;
    }
    
    public void setMessage(MessagesEntity message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        String result = getNotifieur().getPrenom() + " " + getNotifieur().getNom();
        result += " vous a envoyé un message.";
        return result;
    }
}
