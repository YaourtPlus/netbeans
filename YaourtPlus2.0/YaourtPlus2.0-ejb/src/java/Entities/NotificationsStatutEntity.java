/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author Olivier
 */
@Entity
public class NotificationsStatutEntity extends NotificationsEntity{
    
    public NotificationsStatutEntity() {
        this.setDate(Calendar.getInstance().getTime());
        this.setNotifieur(null);
        this.setStatut(null);
        this.setMessage(null);
    }
    
    public NotificationsStatutEntity(Date date) {
        this.setDate(date);
        this.setNotifieur(null);
        this.setStatut(null);
        this.setMessage(null);
    }
    
    @Override
    public String toString() {
        String result = getNotifieur().getPrenom() + " " + getNotifieur().getNom();
        result += " a posté un statut sur votre mur.";
        return result;
    }
}
