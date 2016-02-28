/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Thomas
 */
@ManagedBean
@ApplicationScoped
public class RedirectController {
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

    public String goToMur(int id) {
        return "/secured/mur?faces-redirect=true&id=" + id;
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
}
