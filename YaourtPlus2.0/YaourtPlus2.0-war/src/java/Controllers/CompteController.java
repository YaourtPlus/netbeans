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
    private String nom;
    private String prenom;
    private String age;
    private String mail;
    private int id;
        
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

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAge() {
        return age;
    }

    public String getMail() {
        return mail;
    }
    
    

// Setters =====================================================================
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    

// Methodes ====================================================================
    public String connect() {

        id = profilService.connect(login, passWord);
        if (id != -1) {
            return "secured/mur?faces-redirect=true&idUtilisateur="+id;
        } else {
            return "connexion?faces-redirect=true";
        }
    }
    
    public String inscrire(){
        
        profilService.inscrire(login, passWord, nom, prenom, 10, mail);
        return "connexion?faces-redirect=true";
    }

}
