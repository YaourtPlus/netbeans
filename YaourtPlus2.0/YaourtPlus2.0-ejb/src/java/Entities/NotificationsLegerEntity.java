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
public class NotificationsLegerEntity extends NotificationsEntity{
    
    public NotificationsLegerEntity() {
        this.setDate(Calendar.getInstance().getTime());
        this.setType(0);
        this.setNotifieur(null);
        this.setStatut(null);
        this.setMessage(null);
    }
    
    public NotificationsLegerEntity(Date date) {
        this.setDate(date);
        this.setType(0);
        this.setNotifieur(null);
        this.setStatut(null);
        this.setMessage(null);
    }
    
    @Override
    public String toString() {
        String result = getNotifieur().getPrenom() + " " + getNotifieur().getNom();
        result += " a allégé un statut.";
        return result;
    }
}
