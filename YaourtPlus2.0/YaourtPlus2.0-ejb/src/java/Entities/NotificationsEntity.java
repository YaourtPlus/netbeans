/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Enumerations.TypeNotifications;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;

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
    private Date date;

    // Type de la notification
    // Id de la valeur de l'énum de la notification
    // => Stocker énum trop compliquer
    @Column(nullable = false)
    private Integer type;
    
    
// Relations ONE TO ONE
    /* La notification ne possède qu'un seul des deux objets */
    
    // Le statut que peut référencer la notification
    @JoinColumn(name = "statutID")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private StatutsEntity statut;
    
    // Le message que peut référencer la notification
    @JoinColumn(name = "messageID")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private MessagesEntity message;
    
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
        this.type = 0;
        this.notifieur = null;
        this.statut = null;
        this.message = null;
    }

    public NotificationsEntity(Date date, Integer type) {
        this.date = date;
        this.type = type;
        this.notifieur = null;
        this.statut = null;
        this.message = null;
    }

// Accesseurs ==================================================================
    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public TypeNotifications getTypeNotification() {
        return TypeNotifications.getType(this.type);
    }

    public PersonnesEntity getNotifieur() {
        return notifieur;
    }

    public Integer getType() {
        return type;
    }

    public StatutsEntity getStatut() {
        return statut;
    }

    public MessagesEntity getMessage() {
        return message;
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

    public void setType(TypeNotifications type) {
        if (type == null) {
            this.type = null;
        } else {
            this.type = type.getId();
        }
    }

    public void setNotifieur(PersonnesEntity notifieur) {
        this.notifieur = notifieur;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setStatut(StatutsEntity statut) {
        this.statut = statut;
    }

    public void setMessage(MessagesEntity message) {
        this.message = message;
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
        hash = 43 * hash + Objects.hashCode(this.type);
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
        switch (TypeNotifications.getType(type)) {
            case emptyNotification:
                result = "";
                break;
            case notifFilou:
                result += " vous a ajouté en tant que Filou.";
                break;
            case notifMessage:
                result += " vous a envoyé un message.";
                break;
            case notifLeger:
                result += " a allégé un statut.";
                break;
            case notifLourd:
                result += " a allourdi un statut.";
                break;
            case notifCommentaire:
                result += " a commenté un statut.";
                break;
            case notifStatut:
                result += " a posté un statut sur votre mur.";
                break;
            default:
                break;
        };
        return result;
    }

}
