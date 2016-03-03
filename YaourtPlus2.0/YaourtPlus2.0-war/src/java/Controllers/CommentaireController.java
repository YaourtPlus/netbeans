/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Services.elementaires.CommentaireServiceLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Thomas
 */
@ManagedBean
@RequestScoped
public class CommentaireController {

    private String commentaire;

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;

    @ManagedProperty(value = "#{redirectController}")
    private RedirectController redirectController;

    @EJB
    CommentaireServiceLocal commentaireService;

    /**
     * Creates a new instance of CommentaireController
     */
    public CommentaireController() {
    }

// Getters =====================================================================
    public String getCommentaire() {
        return commentaire;
    }

    public RedirectController getRedirectController() {
        return redirectController;
    }

    public SessionController getSessionController() {
        return sessionController;
    }

// Setters =====================================================================
    public void setRedirectController(RedirectController redirectController) {
        this.redirectController = redirectController;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setSessionController(SessionController murController) {
        this.sessionController = murController;
    }

// Methodes ====================================================================
    public String ajoutCommentaire(int idStatut) {
        commentaireService.ajoutCommentaire(commentaire, idStatut, sessionController.getIdUtilisateur());
        return redirectController.goToCurrentPage();
    }

}
