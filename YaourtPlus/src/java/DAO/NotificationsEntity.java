/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import java.util.ArrayList;
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

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false, length = 32)
    private String type;

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

// Accesseurs ==================================================================
    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public PersonnesEntity getNotifieur() {
        return notifieur;
    }

    public NotificationsEntity() {
        this.date = new Date();
        this.type = "emptyNotification";
        this.notifieur = null;
    }

    public NotificationsEntity(Date date, String type) {
        this.date = date;
        this.type = type;
        this.notifieur = null;
    }

// Mutateurs ===================================================================
    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNotifieur(PersonnesEntity notifieur) {
        this.notifieur = notifieur;
    }
    
// Gestion des destinataires ===================================================
    
    public boolean ajoutDestinataire(PersonnesEntity destinataire) {
        return this.listeDestinataires.add(destinataire);
    }
// Affichage notification ======================================================
    
    public String afficheFilous(){
        String result = this.notifieur.getPrenom() + " " + this.notifieur.getNom();
        return  result + " vous a ajouté à sa liste de Filous.";
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

    // Redéfinir le toString en fonction d'une énum de types?
    @Override
    public String toString() {
        return "NotificationsEntity{" + "id=" + id + ", date=" + date + ", type=" + type + ", notifieur=" + notifieur + '}';
    }

}
