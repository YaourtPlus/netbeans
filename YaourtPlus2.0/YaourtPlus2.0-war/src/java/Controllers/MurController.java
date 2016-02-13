package Controllers;

import Entities.CommentairesEntity;
import Entities.PersonnesEntity;
import Entities.PersonnesStatutsEntity;
import Entities.StatutsEntity;
import Services.FichierServiceLocal;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
    
    public MurController() {
        idUtilisateur = -1;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idPersonne) {
        this.idUtilisateur = idPersonne;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getPathFichier() {
        return pathFichier;
    }

    public void setPathFichier(String pathFichier) {
        this.pathFichier = pathFichier;
    }
    
    
    public List<StatutsEntity> getStatuts() {
        List<StatutsEntity> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            String iS = i + "";
            StatutsEntity s = new StatutsEntity("Statut" + i, new Date());
            s.addCommentaire(new CommentairesEntity("commentaire" + i, new Date()));
            s.addLeger();
            s.addLeger();
            s.addLeger();
            s.addLourd();
            PersonnesEntity p = new PersonnesEntity(iS, iS, i, iS, iS, iS);
            PersonnesEntity p1 = new PersonnesEntity(iS, iS, i, iS, iS, iS);
            p.setId(i);
            p1.setId(0);
            s.setId(i);
            s.addPersonnesStatuts(new PersonnesStatutsEntity(p, s, 0, false, true));
            s.addPersonnesStatuts(new PersonnesStatutsEntity(p1, s, 1, false, true));
            s.setAuteur(p);
            list.add(s);
        }
        return list;
    }

    public String goToMur(int idUtilisateur) {
        return "/mur?faces-redirect=true&idUtilisateur=" + idUtilisateur;
    }
    
    public String goToConnexion() {
        return "../connexion?faces-redirect=true&idUtilisateur=" + idUtilisateur;
    }
    
    

    public String ajoutFichier() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        File f = new File(servletContext.getRealPath("/")+"/files");
        f.mkdir();
        pathFichier = servletContext.getRealPath("/files")+" - ";
        pathFichier += fichierService.ajoutFichier(part, servletContext.getRealPath("/files"), this.idUtilisateur);
        return "mur?faces-redirect=true&idUtilisateur=" + idUtilisateur;
    }
}
