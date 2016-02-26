/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Services.StatutServiceLocal;
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

    @ManagedProperty(value = "#{murController}")
    private MurController murController;

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

    public MurController getMurController() {
        return murController;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setMurController(MurController murController) {
        this.murController = murController;
    }

    public String ajoutCommentaire(int idStatut) {
        statutService.ajoutCommentaire(commentaire, idStatut, murController.getIdUtilisateur());
        
        return murController.goToCurrentPage();
    }
    

}
