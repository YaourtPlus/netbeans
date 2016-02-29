/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.StatutsEntity;
import Services.composites.AfficheStatutsServiceLocal;
import Services.elementaires.FichierServiceLocal;
import Services.elementaires.StatutServiceLocal;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

/**
 *
 * @author Thomas
 */
@ManagedBean
@ViewScoped
public class StatutsController implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer idPersonne;
    private String statut;
    private String pathFichier;
    private Part part;

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;

    @ManagedProperty(value = "#{redirectController}")
    private RedirectController redirectController;

    @EJB
    AfficheStatutsServiceLocal afficheStatutService;

    @EJB
    StatutServiceLocal statutService;

    @EJB
    FichierServiceLocal fichierService;

    /**
     * Creates a new instance of StatutsController
     */
    public StatutsController() {
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public RedirectController getRedirectController() {
        return redirectController;
    }

    public void setRedirectController(RedirectController redirectController) {
        this.redirectController = redirectController;
    }

    public String getPathFichier() {
        return pathFichier;
    }

    public Part getPart() {
        return part;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public void setPathFichier(String pathFichier) {
        this.pathFichier = pathFichier;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Integer getIdPersonne() {
        return idPersonne;
    }

    public String getStatut() {
        return statut;
    }

    public void setIdPersonne(Integer idPersonne) {
        this.idPersonne = idPersonne;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public List<StatutsEntity> getStatutsEmis() {
        return afficheStatutService.afficheStatutsEmis(idPersonne);
    }

    public List<StatutsEntity> getStatutsRecu() {
        return afficheStatutService.afficheStatutsRecus(idPersonne);
    }

    public List<StatutsEntity> getStatuts() {
        return afficheStatutService.afficheMurStatuts(sessionController.getIdUtilisateur());
    }

    public String ajoutStatut() {
        int idStatut = statutService.ajoutStatut(statut, sessionController.getIdUtilisateur());
        System.err.println(idStatut);
        if (part != null) {
            ajoutFichier(idStatut);
        }
        return redirectController.goToCurrentPage();
    }

    public String postStatut() {
        if (idPersonne == sessionController.getIdUtilisateur()) {
            return ajoutStatut();
        } else {
            int idStatut = statutService.postStatut(statut, sessionController.getIdUtilisateur(), idPersonne);
            if (part != null) {
                ajoutFichier(idStatut);
            }
            return redirectController.goToCurrentPage();
        }
    }

    public String ajoutLeger(int idStatut) {
        statutService.ajoutLeger(idStatut, sessionController.getIdUtilisateur());
        return redirectController.goToCurrentPage();
    }

    public String ajoutLourd(int idStatut) {
        statutService.ajoutLourd(idStatut, sessionController.getIdUtilisateur());
        return redirectController.goToCurrentPage();
    }

    public String suppressionAction(int idStatut) {
        statutService.removeAction(idStatut, sessionController.getIdUtilisateur());
        return redirectController.goToCurrentPage();
    }

    public String ajoutFichier(int idStatut) {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        File f = new File(servletContext.getRealPath("/") + "/files");
        f.mkdir();
        pathFichier = servletContext.getRealPath("/files") + " - ";
        pathFichier += fichierService.ajoutFichier(part, servletContext.getRealPath("/files"), idStatut);
        return "mur?faces-redirect=true&id=" + sessionController.getIdUtilisateur();
    }
}
