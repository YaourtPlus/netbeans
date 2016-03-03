/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author tbenoist
 */
@Entity
public class NotificationsEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Date de la notification
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

// Relations ONE TO ONE
// Relations ONE TO MANY
// Relations MANY TO ONE
    // Auteur de la notification
    @ManyToOne
    @JoinColumn(name = "notifieurId")
    private PersonnesEntity notifieur;

// Relations MANY TO MANY
    // Liste des personnes liées a la notification
    @JoinTable(
            name = "Personnes_Notifications",
            joinColumns = @JoinColumn(name = "NotificationsId"),
            inverseJoinColumns = @JoinColumn(name = "DestinataireId")
    )
    @ManyToMany(fetch = FetchType.EAGER)
    List<PersonnesEntity> listeDestinataires = new ArrayList<>();

// Constructeurs ===============================================================
    public NotificationsEntity() {
        this.date = Calendar.getInstance().getTime();
        this.notifieur = null;
        //this.statut = null;
        //this.message = null;
    }

    public NotificationsEntity(Date date, Integer type) {
        this.date = date;
        this.notifieur = null;
        //this.statut = null;
        //this.message = null;
    }

// Accesseurs ==================================================================
    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public PersonnesEntity getNotifieur() {
        return notifieur;
    }

    public List<PersonnesEntity> getListeDestinataires() {
        return listeDestinataires;
    }

// Mutateurs ===================================================================
    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNotifieur(PersonnesEntity notifieur) {
        this.notifieur = notifieur;
    }

    public void setListeDestinataires(List<PersonnesEntity> listeDestinataires) {
        this.listeDestinataires = listeDestinataires;
    }

    
    
// Gestion des destinataires ===================================================
    public boolean ajoutDestinataire(PersonnesEntity destinataire) {
        return this.listeDestinataires.add(destinataire);
    }

// =============================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.date);
        hash = 43 * hash + Objects.hashCode(this.notifieur);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NotificationsEntity other = (NotificationsEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String result = notifieur.getPrenom() + " " + notifieur.getNom();
        result += " vous a notifié";
        return result;
    }

}
