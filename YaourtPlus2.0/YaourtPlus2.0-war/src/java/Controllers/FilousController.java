/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.PersonnesEntity;
import Services.FilousServiceLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.jms.Message;

/**
 *
 * @author tbenoist
 */
@ManagedBean
@RequestScoped
public class FilousController {

    private boolean added;

    @ManagedProperty(value = "#{murController}")
    private MurController murController;
    
    @ManagedProperty(value = "#{messagesController}")
    private MessagesController messagesController;

    @EJB
    FilousServiceLocal filouService;

    public FilousController() {
        added = false;
    }

    public MurController getMurController() {
        return murController;
    }

    public void setMurController(MurController murController) {
        this.murController = murController;
    }

    public MessagesController getMessagesController() {
        return messagesController;
    }

    public void setMessagesController(MessagesController messagesController) {
        this.messagesController = messagesController;
    }
    
    
    public boolean getAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    // TO FIX => Access murController directly
    public List<PersonnesEntity> getFilous() {

        List<PersonnesEntity> list = filouService.getFilous(murController.getIdUtilisateur());
        if(list.size() > 0){
            messagesController.setIdPersonne(list.get(0).getId());
        }
        return list;
    }

    public List<PersonnesEntity> getFilousAjout() {

        List<PersonnesEntity> list = filouService.getFilousPossibles(murController.getIdUtilisateur());

        return list;
    }

    public String ajoutFilou(int idFilous) {

        added = filouService.ajoutFilous(idFilous, murController.getIdUtilisateur());
        return "/secured/filous?faces-redirect=true";
    }

    public void suppressionFilou(int idFilous) {
       filouService.suppressionFilous(idFilous, murController.getIdUtilisateur());
    }
}
