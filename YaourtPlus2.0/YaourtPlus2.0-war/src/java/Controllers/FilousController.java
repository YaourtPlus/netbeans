/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.PersonnesEntity;
import Services.FilousServiceLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author tbenoist
 */
@ManagedBean
@RequestScoped
public class FilousController {

    private boolean added;

    @EJB
    FilousServiceLocal filouService;

    public FilousController() {
        added = false;
    }

    public boolean getAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    // TO FIX => Access murController directly
    public List<PersonnesEntity> getFilous(int idUtilisateur) {

        List<PersonnesEntity> list = filouService.getFilous(idUtilisateur);

        return list;
    }

    public List<PersonnesEntity> getFilousAjout(int idUtilisateur) {

        List<PersonnesEntity> list = filouService.getFilousPossibles(idUtilisateur);

        return list;
    }

    public String ajoutFilou(int idFilous, int idUtilisateur) {

        added = filouService.ajoutFilous(idFilous, idUtilisateur);
        return "/secured/filous?faces-redirect=true";
    }

    public String suppressionFilou(int idFilous, int idUtilisateur) {

        if (filouService.suppressionFilous(idFilous, idUtilisateur)) {
            return "/secured/mur?faces-redirect=true";
        } else {
            return "/secured/filous?faces-redirect=true";
        }
    }
}
