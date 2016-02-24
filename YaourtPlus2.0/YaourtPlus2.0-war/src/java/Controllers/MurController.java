package Controllers;

import Entities.StatutsEntity;
import Services.AfficheStatutsServiceLocal;
import Services.FichierServiceLocal;
import Services.FilousServiceLocal;
import Services.StatutServiceLocal;
import java.io.File;
import java.util.List;
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
public class MurController {

    private int idUtilisateur;
    private Part part;
    private String statut;
    private String pathFichier;

    @EJB
    FichierServiceLocal fichierService;

    @EJB
    FilousServiceLocal filouService;

    @EJB
    AfficheStatutsServiceLocal afficheStatutService;

    @EJB
    StatutServiceLocal statutService;

    public MurController() {
        idUtilisateur = -1;
    }

// Getters =====================================================================
    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public Part getPart() {
        return part;
    }

    public String getStatut() {
        return statut;
    }

    public String getPathFichier() {
        return pathFichier;
    }

// Setters =====================================================================
    public void setIdUtilisateur(int idPersonne) {
        this.idUtilisateur = idPersonne;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setPathFichier(String pathFichier) {
        this.pathFichier = pathFichier;
    }

// Methods =====================================================================
    public String goToMur(int idUtilisateur) {
        return "mur?faces-redirect=true&idUtilisateur=" + idUtilisateur;
    }

    public String goToConnexion() {
        return "../connexion?faces-redirect=true&idUtilisateur=" + idUtilisateur;
    }
    
    public String goToFilou(Integer filouId){
        return "profil?faces-redirect=true&idPersonne=" + filouId;
    }

    public String ajoutFichier(int idStatut) {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        File f = new File(servletContext.getRealPath("/") + "/files");
        f.mkdir();
        pathFichier = servletContext.getRealPath("/files") + " - ";
        pathFichier += fichierService.ajoutFichier(part, servletContext.getRealPath("/files"), idStatut);
        return "mur?faces-redirect=true&idUtilisateur=" + idUtilisateur;
    }

    public List<StatutsEntity> getStatuts() {
        return afficheStatutService.afficheMurStatuts(idUtilisateur);
    }

    public String ajoutStatut() {
        int idStatut = statutService.ajoutStatut(statut, idUtilisateur);
        if (part != null) {
            ajoutFichier(idStatut);
        }
        return "mur?faces-redirect=true";
    }

    public void ajoutLeger(int idStatut, int idUtilisateur) {
        statutService.ajoutLeger(idStatut, idUtilisateur);
    }

    public void ajoutLourd(int idStatut, int idUtilisateur) {
        statutService.ajoutLourd(idStatut, idUtilisateur);
    }

    public void suppressionAction(int idStatut, int idUtilisateur) {
        statutService.removeAction(idStatut, idUtilisateur);
    }
}
