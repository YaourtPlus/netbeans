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
public class NotificationsStatutEntity extends NotificationsEntity {
    private static final long serialVersionUID = 1L;

    /* La notification ne possède qu'un seul des deux objets */
    // Le statut que peut référencer la notification
    @JoinColumn(name = "statutID")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private StatutsEntity statut;

    public NotificationsStatutEntity() {
        this.setDate(Calendar.getInstance().getTime());
        this.setNotifieur(null);
        //this.setStatut(null);
        this.statut = null;
        //this.setMessage(null);
    }

    public NotificationsStatutEntity(Date date) {
        this.setDate(date);
        this.setNotifieur(null);
        //this.setStatut(null);
        this.statut = null;
        //this.setMessage(null);
    }

    @Override
    public StatutsEntity getStatut() {
        return statut;
    }
    
    public void setStatut(StatutsEntity statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        String result = getNotifieur().getPrenom() + " " + getNotifieur().getNom();
        result += " a posté un statut sur votre mur.";
        return result;
    }
}
