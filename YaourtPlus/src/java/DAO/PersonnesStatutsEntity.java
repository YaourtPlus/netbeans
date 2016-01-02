/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Enumerations.TypeActions;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Thomas
 */
@Entity
public class PersonnesStatutsEntity implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "personneId")
    private PersonnesEntity personne;

    @Id
    @ManyToOne
    @JoinColumn(name = "statutId")
    private StatutsEntity statut;

    /**
     * Type action peut prendre 3 valeurs selon l'action qu'aura fait
     * l'utilisateur sur le statut 0 pour indiquer que l'utilisateur a annuler
     * un Léger ou un T'es lourd 1 pour indiquer que l'utilisateur a mis un
     * Léger 2 pour indiquer que l'utilisateur a mis un T'es lourd
     */
    @Column(nullable = false)
    private Integer typeAction;

// Constructeur ================================================================
    public PersonnesStatutsEntity() {
        this.personne = null;
        this.typeAction = 0;
        this.statut = null;
    }

    public PersonnesStatutsEntity(PersonnesEntity personne, StatutsEntity statut, int action) {
        this.personne = personne;
        this.statut = statut;
        this.typeAction = action;
    }

// Getters =====================================================================
    public PersonnesEntity getPersonne() {
        return personne;
    }

    public StatutsEntity getStatut() {
        return statut;
    }

    public TypeActions getTypeAction() {
        return TypeActions.getType(typeAction);
    }

// Setters =====================================================================
    public void setPersonne(PersonnesEntity personne) {
        this.personne = personne;
    }

    public void setStatut(StatutsEntity statut) {
        this.statut = statut;
    }

    public void setTypeAction(TypeActions typeAction) {
        if (typeAction == null) {
            this.typeAction = null;
        } else {
            this.typeAction = typeAction.getId();
        }
    }

// =============================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.personne);
        hash = 71 * hash + Objects.hashCode(this.statut);
        hash = 71 * hash + Objects.hashCode(this.typeAction);
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
        final PersonnesStatutsEntity other = (PersonnesStatutsEntity) obj;
        if (!Objects.equals(this.personne, other.personne)) {
            return false;
        }
        if (!Objects.equals(this.statut, other.statut)) {
            return false;
        }
        return true;
    }

}