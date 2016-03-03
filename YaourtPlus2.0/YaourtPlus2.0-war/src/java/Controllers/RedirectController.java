/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Thomas
 */
@ManagedBean
@ApplicationScoped
public class RedirectController implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * Creates a new instance of RedirectController
     */
    public RedirectController() {
    }

// Getters =====================================================================
// Setters =====================================================================
// Methodes ====================================================================    
    /**
     * Redirige l'utilisateur vers la page courante avec le paramètre id 
     * l'id est utilisé par la majorité des bean en viewScoped et correspond 
     * en général à la personne cible de l'action de l'utilisateur
     * 
     * @return l'url vers la page courante
     */
    public String goToCurrentPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String id = fc.getExternalContext().getRequestParameterMap().get("id");
        String viewId = fc.getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&id=" + id;
    }

    
    /**
     * Redirige l'utilisateur vers la page d'accueil (mur principal)
     * 
     * @return l'url vers la page d'accueil
     */
    public String goToMur() {
        return "/secured/mur?faces-redirect=true";
    }

    
    /**
     * Redirige l'utilisateur vers la page des filous
     * 
     * @return l'url vers la page des filous
     */
    public String goToAddFilous() {
        return "/secured/filous?faces-redirect=true";
    }
    
    
    /**
     * Redirige l'utilisateur vers la page de connexion
     * 
     * @return 
     */
    public String goToConnexion() {
        return "../connexion?faces-redirect=true";
    }

    
    /**
     * Redirige l'utilisateur vers le mur d'un filou
     * 
     * @param filouId l'id du filou que l'utilisateur veut visiter
     * @return l'url vers le mur du filou
     */
    public String goToFilou(Integer filouId) {
        return "/secured/profil?faces-redirect=true&id=" + filouId;
    }
    
    
    /**
     * Récupération de l'id de la page courante
     * 
     * @return l'id de la page courante
     */
    public String getViewId(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId();
    }
    
    
    /**
     * Test si un paramètre existe dans la session
     * 
     * @param name nom du paramètre à tester
     * @return true si le paramètre existe, false sinon
     */
    public boolean isParameter(String name){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("id");
    }
}
