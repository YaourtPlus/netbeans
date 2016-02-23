/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Services.StatutServiceLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Thomas
 */
@ManagedBean
@RequestScoped
public class CommentaireController {

    private String commentaire;

    @EJB
    StatutServiceLocal statutService;

    /**
     * Creates a new instance of CommentaireController
     */
    public CommentaireController() {
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        System.err.println("setCommentaire");
        System.err.println(commentaire);
        this.commentaire = commentaire;
    }

    public String ajoutCommentaire(int idStatut, int idUtilisateur) {
        System.err.println("AjoutCommentaire");
        System.err.println(commentaire);
        System.err.println(idStatut);
        System.err.println(idUtilisateur);

        statutService.ajoutCommentaire(commentaire, idStatut, idUtilisateur);
        return "mur?faces-redirect=true";
    }

}
