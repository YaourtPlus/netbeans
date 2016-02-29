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
    
    public String goToCurrentPage() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String id = fc.getExternalContext().getRequestParameterMap().get("id");
        String viewId = fc.getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&id=" + id;
    }

    public String goToMur() {
        return "/secured/mur?faces-redirect=true";
    }

    public String goToAddFilous() {
        return "/secured/filous?faces-redirect=true";
    }
    
    public String goToConnexion(int id) {
        return "../connexion?faces-redirect=true&id=" + id;
    }

    public String goToFilou(Integer filouId) {
        return "/secured/profil?faces-redirect=true&id=" + filouId;
    }
    
    public String getViewId(){
        return FacesContext.getCurrentInstance().getViewRoot().getViewId();
    }
    
    public boolean isParameter(String name){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("id");
    }
}
