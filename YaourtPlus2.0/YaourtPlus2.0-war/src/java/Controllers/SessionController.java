package Controllers;

import Entities.PersonnesEntity;
import Entities.StatutsEntity;
import Services.composites.AfficheStatutsServiceLocal;
import Services.elementaires.FichierServiceLocal;
import Services.composites.FilousServiceLocal;
import Services.elementaires.PersonnesServiceLocal;
import Services.elementaires.StatutServiceLocal;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Olivier
 */
@ManagedBean
@SessionScoped
public class SessionController {

    private int idUtilisateur;


    public SessionController() {
        idUtilisateur = -1;
    }

// Getters =====================================================================
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

// Setters =====================================================================
    public void setIdUtilisateur(int idPersonne) {
        this.idUtilisateur = idPersonne;
    }
    
// Methods =====================================================================

}
