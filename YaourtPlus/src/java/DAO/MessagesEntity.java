/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
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
public class MessagesEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 1024, nullable = false)
    private String texte;

    @Column(length = 64)
    private String image;

    @Column(length = 64)
    private String video;

    @Column(nullable = true)
    private Date date;

// Relations ONE TO ONE
// Relations ONE TO MANY
// Relations MANY TO ONE
    // Auteur du message
    @ManyToOne
    @JoinColumn(name = "auteurMessage")
    private PersonnesEntity emetteur;

// Relations MANY TO MANY
    // Liste des fichiers liés au message
    @JoinTable(
            name = "Messages_Fichiers",
            joinColumns = @JoinColumn(name = "FichiersID"),
            inverseJoinColumns = @JoinColumn(name = "MessagesID")
    )
    @ManyToMany(fetch = FetchType.EAGER)
    List<FichiersEntity> listeFichiers = new ArrayList<>();
// Accesseurs ==================================================================			

    public Integer getId() {
        return id;
    }

    public String getTexte() {
        return texte;
    }

    public String getImage() {
        return image;
    }

    public String getVideo() {
        return video;
    }

    public Date getDate() {
        return date;
    }

    public PersonnesEntity getEmetteur() {
        return emetteur;
    }

    public List<FichiersEntity> getListeFichiers() {
        return listeFichiers;
    }

// Mutateurs ===================================================================
    public void setId(Integer id) {
        this.id = id;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEmetteur(PersonnesEntity emetteur) {
        this.emetteur = emetteur;
    }

    public void setListeFichiers(List<FichiersEntity> listeFichiers) {
        this.listeFichiers = listeFichiers;
    }

// =============================================================================
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.texte);
        hash = 53 * hash + Objects.hashCode(this.image);
        hash = 53 * hash + Objects.hashCode(this.video);
        hash = 53 * hash + Objects.hashCode(this.date);
        hash = 53 * hash + Objects.hashCode(this.emetteur);
        hash = 53 * hash + Objects.hashCode(this.listeFichiers);
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
        if (!Objects.equals(this.texte, other.texte)) {
            return false;
        }
        if (!Objects.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.video, other.video)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.emetteur, other.emetteur)) {
            return false;
        }
        if (!Objects.equals(this.listeFichiers, other.listeFichiers)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MessagesEntity{" + "id=" + id + ", texte=" + texte + ", image=" + image + ", video=" + video + ", date=" + date + ", emetteur=" + emetteur + ", listeFichiers=" + listeFichiers + '}';
    }

}
