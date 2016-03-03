package Controllers;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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
public class SessionController implements Serializable {
    private static final long serialVersionUID = 1L;

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
    
// Methodes ====================================================================

}
