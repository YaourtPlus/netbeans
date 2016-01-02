/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
import javax.persistence.OneToMany;

/**
 *
 * @author tbenoist
 */
@Entity
public class StatutsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 1024)
    private String texte;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Integer nbLeger;

    @Column(nullable = false)
    private Integer nbLourd;

// Relations ONE TO ONE
// Relations ONE TO MANY
    
    // Liste des acteurs sur le statut (notamment pour notification)
    @OneToMany(mappedBy = "statut", fetch = FetchType.EAGER)
    private List<PersonnesStatutsEntity> statutsActeurs = new ArrayList<>();
    
// Relations MANY TO ONE
    // Auteur du statut
    @ManyToOne
    @JoinColumn(name = "auteurId")
    private PersonnesEntity auteur;

// Relations MANY TO MANY
    // Liste des fichiers liés au statut
    @JoinTable(
            name = "Statuts_Fichiers",
            joinColumns = @JoinColumn(name = "FichiersID"),
            inverseJoinColumns = @JoinColumn(name = "StatutsID")
    )
    @ManyToMany(fetch = FetchType.EAGER)
    private List<FichiersEntity> listeFichiers = new ArrayList<>();


// Constructeur ================================================================
    public StatutsEntity() {
        this.texte = "";
        this.date = null;
        this.auteur = null;
        this.nbLeger = 0;
        this.nbLourd = 0;
    }

    public StatutsEntity(String texte, Date date) {
        this.texte = texte;
        this.date = date;
        this.auteur = auteur;
        this.nbLeger = 0;
        this.nbLourd = 0;
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

    public Integer getNbLeger() {
        return nbLeger;
    }

    public Integer getNbLourd() {
        return nbLourd;
    }

    public PersonnesEntity getAuteur() {
        return auteur;
    }

    public List<FichiersEntity> getListeFichiers() {
        return listeFichiers;
    }

    public List<PersonnesStatutsEntity> getStatutsActeurs() {
        return statutsActeurs;
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

    public void setNbLeger(Integer nbLeger) {
        this.nbLeger = nbLeger;
    }

    public void setNbLourd(Integer nbLourd) {
        this.nbLourd = nbLourd;
    }

    public void setAuteur(PersonnesEntity auteur) {
        this.auteur = auteur;
    }

    public void setListeFichiers(List<FichiersEntity> listeFichiers) {
        this.listeFichiers = listeFichiers;
    }

    public void setStatutsActeurs(List<PersonnesStatutsEntity> statutsActeurs) {
        this.statutsActeurs = statutsActeurs;
    }
    
// Gestion rapide nbLéger / nbLourd ============================================
    public void addLeger() {
        nbLeger++;
    }

    public void addLourd() {
        nbLourd++;
    }

    public void delLeger() {
        if (nbLeger > 0) {
            nbLeger--;
        }
    }

    public void delLourd() {
        if (nbLourd > 0) {
            nbLourd--;
        }
    }

// Gestion action sur le statut ================================================
    public boolean addPersonnesStatuts(PersonnesStatutsEntity ps){
        if(statutsActeurs.contains(ps)){
            return false;
        }
        return statutsActeurs.add(ps);
        
    }
    
    public boolean removePersonnesStatuts(PersonnesStatutsEntity ps){
        if(!statutsActeurs.contains(ps)){
            return false;
        }
        return statutsActeurs.remove(ps);
    }
// =============================================================================

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.texte);
        hash = 97 * hash + Objects.hashCode(this.date);
        hash = 97 * hash + Objects.hashCode(this.nbLeger);
        hash = 97 * hash + Objects.hashCode(this.nbLourd);
        hash = 97 * hash + Objects.hashCode(this.auteur);
        hash = 97 * hash + Objects.hashCode(this.listeFichiers);
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
        final StatutsEntity other = (StatutsEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StatutsEntity{" + "id=" + id + ", texte=" + texte + ", date=" + date + ", nbLeger=" + nbLeger + ", nbLourd=" + nbLourd + ", auteur=" + auteur + '}';
    }

}
