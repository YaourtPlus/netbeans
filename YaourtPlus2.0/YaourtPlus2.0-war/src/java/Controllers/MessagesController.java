/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.MessagesEntity;
import Services.MessageServiceLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author tbenoist
 */
@ManagedBean
@ViewScoped
public class MessagesController {

    private int idPersonne;
    
    private String message;
    
    @ManagedProperty(value = "#{murController}")
    private MurController murController;

    @EJB
    MessageServiceLocal messageService;
    /**
     * Creates a new instance of MessagesController
     */
    public MessagesController() {
    }

        
    public int getIdPersonne() {
        return idPersonne;
    }

    public String getMessage() {
        return message;
    }

    public MurController getMurController() {
        return murController;
    }
    
    
    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMurController(MurController murController) {
        this.murController = murController;
    }
    
    public String envoieMessage(){   
        messageService.ajoutMessage(message, murController.getIdUtilisateur(), idPersonne);
        return murController.goToCurrentPage();
    }
    
    public List<MessagesEntity> getMessages(){
        return messageService.getMessagesSinglePersonne(murController.getIdUtilisateur(), idPersonne);
    }
    
}
