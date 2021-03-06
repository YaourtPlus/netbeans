/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author tbenoist
 */
@Entity
public class MessagesEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Texte du message
    @Column(length = 1024, nullable = false)
    private String texte;

    // Date d'envoi du message
    @Column(nullable = true)
    private Date date;

// Relations ONE TO ONE    
// Relations ONE TO MANY
    // Auteur du message
    @ManyToOne
    @JoinColumn(name = "auteurMessage")
    private PersonnesEntity emetteur;

    @ManyToOne
    @JoinColumn(name = "destinataireMessage")
    private PersonnesEntity destinataire;
// Relations MANY TO ONE
// Relations MANY TO MANY

// Constructeurs ===============================================================
    public MessagesEntity() {
        this.texte = "";
        this.date = new Date();
    }

    public MessagesEntity(String texte, Date date) {
        this.texte = texte;
        this.date = date;
    }

// Accesseurs ==================================================================
    public Integer getId() {
        return id;
    }

    public String getTexte() {
        return texte;
    }

    public Date getDate() {
        return date;
    }

    public PersonnesEntity getEmetteur() {
        return emetteur;
    }

    public PersonnesEntity getDestinataire() {
        return destinataire;
    }

// Mutateurs ===================================================================
    public void setId(Integer id) {
        this.id = id;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEmetteur(PersonnesEntity emetteur) {
        this.emetteur = emetteur;
    }

    public void setDestinataire(PersonnesEntity destinataire) {
        this.destinataire = destinataire;
    }

// =============================================================================
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.texte);
        hash = 53 * hash + Objects.hashCode(this.date);

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
        final MessagesEntity other = (MessagesEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MessagesEntity{" + "id=" + id + ", texte=" + texte
                + ", date=" + date + '}';
    }

}
