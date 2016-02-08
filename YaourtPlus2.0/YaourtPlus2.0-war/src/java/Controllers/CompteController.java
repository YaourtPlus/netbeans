/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Services.ProfilServiceLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author tbenoist
 */
@ManagedBean
@RequestScoped
public class CompteController {

    private String login;
    private String passWord;
        
    @EJB
    ProfilServiceLocal profilService;

    public CompteController() {
    }

// Getters =====================================================================
    public String getLogin() {
        return login;
    }

    public String getPassWord() {
        return passWord;
    }

// Setters =====================================================================
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

// Methodes ====================================================================
    public String connect() {

        if (profilService.connect(login, passWord)) {
            return "secured/mur?faces-redirect=true";
        } else {
            return "inscription?faces-redirect=true";
        }
    }
    
    public String inscrire(){
        
        profilService.inscrire(login, passWord, login, login, 10, login);
        return "connexion?faces-redirect=true";
    }

}
