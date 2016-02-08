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

    private String idPersonne;

    private String login;
    private String passWord;
    private String nom;
    private String prenom;
    private String age;
    private String mail;
    private int idUtilisateur;
        
    @EJB
    ProfilServiceLocal profilService;

    public CompteController() {
        idUtilisateur = -1;
        idPersonne = "";
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

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getIdPersonne() {
        return idPersonne;
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

     public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public void getIdPersonne(String idPersonne) {
        this.idPersonne = idPersonne;
    }
    
// Methodes ====================================================================
    public String connect() {

        idUtilisateur = profilService.connect(login, passWord);
        if (idUtilisateur != -1) {
            return "secured/mur?faces-redirect=true&idUtilisateur="+idUtilisateur;
        } else {
            return "connexion?faces-redirect=true";
        }
    }

    public String inscrire() {

        profilService.inscrire(login, passWord, nom, prenom, 10, mail);
        return "connexion?faces-redirect=true";
    }

}
