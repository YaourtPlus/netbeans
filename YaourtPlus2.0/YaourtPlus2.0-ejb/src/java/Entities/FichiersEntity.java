/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author tbenoist
 */
@Entity
public class FichiersEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Type du fichier => Enum?
    @Column(length = 32, nullable = false)
    private String type;

    // Path du fichier sur le serveur
    @Column(length = 64, nullable = false)
    private String contenu;

    @Column(length = 256, nullable = false)
    private String nom;

// Relations ONE TO ONE
// Relations ONE TO MANY
// Relations MANY TO ONE	
// Relations MANY TO MANY
    // Liste des statuts auxquels est associé le fichier
    @ManyToMany(mappedBy = "listeFichiers", fetch = FetchType.EAGER)
    List<StatutsEntity> listeStatuts = new ArrayList<>();

// Constructeurs ===============================================================
    public FichiersEntity() {
        this.type = "txt";
        this.contenu = "";
        this.nom = "";
    }

    public FichiersEntity(String type, String contenu, String nom) {
        this.type = type;
        this.contenu = contenu;
        this.nom = nom;
    }

// Accesseurs ==================================================================
    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getContenu() {
        return contenu;
    }

    public String getNom() {
        return nom;
    }

    public List<StatutsEntity> getListeStatuts() {
        return listeStatuts;
    }

// Mutateurs ===================================================================
    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setListeStatuts(List<StatutsEntity> listeStatuts) {
        this.listeStatuts = listeStatuts;
    }
    public boolean addStatutsFichier(StatutsEntity se)
    {
        return this.listeStatuts.add(se);
    }
    
    public boolean removeStatutsFichier(StatutsEntity se)
    {
        return this.listeStatuts.remove(se);
    }
    
    
    
// =============================================================================
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.type);
        hash = 47 * hash + Objects.hashCode(this.contenu);
        hash = 47 * hash + Objects.hashCode(this.nom);
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
        final FichiersEntity other = (FichiersEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.contenu, other.contenu)) {
            return false;
        }

        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FichiersEntity{" + "id=" + id + ", type=" + type
                + ", contenu=" + contenu + ", nom = " + nom + '}';
    }

}
