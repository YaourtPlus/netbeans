/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

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
public class CommentairesEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 1024)
    private String texte;

    @Column(nullable = false)
    private Date date;

// Relations ONE TO ONE
// Relations ONE TO MANY
// Relations MANY TO ONE
    // Auteur du commentaire
    @ManyToOne
    @JoinColumn(name = "auteurId")
    private PersonnesEntity auteur;

    // Statut lié au commentaire
    @ManyToOne
    @JoinColumn(name = "statutID")
    private StatutsEntity statut;

// Relations MANY TO MANY

// Constructeur ================================================================
    public CommentairesEntity() {
        this.texte = "";
        this.date = null;
        this.auteur = null;
    }

    public CommentairesEntity(String texte, Date date) {
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

    public PersonnesEntity getAuteur() {
        return auteur;
    }

    public StatutsEntity getStatut() {
        return statut;
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

    public void setStatut(StatutsEntity statut) {
        this.statut = statut;
    }

    public void setAuteur(PersonnesEntity auteur) {
        this.auteur = auteur;
    }

// =============================================================================

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.texte);
        hash = 97 * hash + Objects.hashCode(this.date);
        hash = 97 * hash + Objects.hashCode(this.auteur);
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
        final CommentairesEntity other = (CommentairesEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CommentairesEntity{" + "id=" + id + ", texte=" + texte + ", date="
                + date + ", auteur=" + auteur + '}';
    }

}
