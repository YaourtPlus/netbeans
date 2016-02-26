/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.PersonnesEntity;
import Entities.StatutsEntity;
import Services.AfficheStatutsServiceLocal;
import Services.PersonnesServiceLocal;
import Services.StatutServiceLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Thomas
 */
@ManagedBean
@ViewScoped
public class StatutsController {
    
    private Integer idPersonne;
    
    private String statut;
    
    @EJB
    AfficheStatutsServiceLocal afficheStatutService;

    @EJB
    StatutServiceLocal statutService;
    
    @ManagedProperty(value = "#{murController}")
    private MurController murController;
    /**
     * Creates a new instance of StatutsController
     */
    public StatutsController() {
    }

    public MurController getMurController() {
        return murController;
    }

    public void setMurController(MurController murController) {
        this.murController = murController;
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
    
    public List<StatutsEntity> getStatutsEmis(){
        return afficheStatutService.getStatutsEmis(idPersonne);
    }
    
    public List<StatutsEntity> getStatutsRecu(){
        return afficheStatutService.getStatutsRecus(idPersonne);
    }
    
    public List<StatutsEntity> getStatuts() {
        return afficheStatutService.afficheMurStatuts(murController.getIdUtilisateur());
    }
    
    public String ajoutStatut() {
        int idStatut = statutService.ajoutStatut(statut, murController.getIdUtilisateur());
        if (murController.getPart() != null) {
            murController.ajoutFichier(idStatut);
        }
        return murController.goToCurrentPage();
    }

    public String postStatut() {
        if(idPersonne == murController.getIdUtilisateur()){
            return ajoutStatut();
        }
        else{
            int idStatut = statutService.postStatut(statut, murController.getIdUtilisateur(), idPersonne);
            if (murController.getPart() != null) {
                murController.ajoutFichier(idStatut);
            }
            return murController.goToCurrentPage();
        }
    }
}
