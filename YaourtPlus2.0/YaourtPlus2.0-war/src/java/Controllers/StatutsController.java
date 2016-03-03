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

// Getters =====================================================================
    public SessionController getSessionController() {
        return sessionController;
    }

    public RedirectController getRedirectController() {
        return redirectController;
    }

    public String getPathFichier() {
        return pathFichier;
    }

    public Part getPart() {
        return part;
    }
    
    public Integer getIdPersonne() {
        return idPersonne;
    }

    public String getStatut() {
        return statut;
    }
// Setters =====================================================================
    public void setRedirectController(RedirectController redirectController) {
        this.redirectController = redirectController;
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
    
    public void setIdPersonne(Integer idPersonne) {
        this.idPersonne = idPersonne;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

// Methodes ====================================================================
    /**
     * Récupération des statuts émis par la personne dont l'id est stocké en attribut
     * @return la liste des statuts émis par la personne
     */
    public List<StatutsEntity> getStatutsEmis() {
        return afficheStatutService.afficheStatutsEmis(idPersonne);
    }

    
    /**
     * Récupération des stattus reçus par la personne dont l'id est stocké en attribut
     * @return la liste des statuts reçus par la personne
     */
    public List<StatutsEntity> getStatutsRecu() {
        return afficheStatutService.afficheStatutsRecus(idPersonne);
    }

    
    /**
     * Récupération des statuts visible par l'utilisateur (filous et les siens)
     * @return la liste des statuts postés par les filous et les siens
     */
    public List<StatutsEntity> getStatuts() {
        return afficheStatutService.afficheMurStatuts(sessionController.getIdUtilisateur());
    }

    
    /**
     * Ajout d'un statut par l'utilisateur
     * @return l'url vers la page courante
     */
    public String ajoutStatut() {
        int idStatut = statutService.ajoutStatut(statut, sessionController.getIdUtilisateur());
        if (part != null) {
            ajoutFichier(idStatut);
        }
        return redirectController.goToCurrentPage();
    }

    
    /**
     * Ajout d'un statut par l'utilisateur à direction de la personne dont l'id est stocké en attribut
     * @return l'url vers la page courante
     */
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

    
    /**
     * Ajout d'un léger sur un statut par l'utilisateur
     * @param idStatut l'id du statut auquel on ajoute le léger
     * @return l'url vers la page courante
     */
    public String ajoutLeger(int idStatut) {
        statutService.ajoutLeger(idStatut, sessionController.getIdUtilisateur());
        return redirectController.goToCurrentPage();
    }

    
    /**
     * Ajout d'un lourd sur un statut par l'utilisateur
     * @param idStatut l'id du statut auquel on ajout le lourd
     * @return l'url vers la page courante
     */
    public String ajoutLourd(int idStatut) {
        statutService.ajoutLourd(idStatut, sessionController.getIdUtilisateur());
        return redirectController.goToCurrentPage();
    }

    
    /**
     * Annulation d'une action effectué par l'utilisateur sur un statut
     * @param idStatut id du statut dont on annule l'action
     * @return l'url vers la page courante
     */
    public String suppressionAction(int idStatut) {
        statutService.removeAction(idStatut, sessionController.getIdUtilisateur());
        return redirectController.goToCurrentPage();
    }

    
    /**
     * Ajout du fichier en attribut au statut
     * 
     * @param idStatut id du statut
     * @return l'url vers la page courante
     */
    public String ajoutFichier(int idStatut) {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        File f = new File(servletContext.getRealPath("/") + "/files");
        f.mkdir();
        pathFichier = servletContext.getRealPath("/files") + " - ";
        pathFichier += fichierService.ajoutFichier(part, servletContext.getRealPath("/files"), idStatut);
        return redirectController.goToCurrentPage();
    }
}
