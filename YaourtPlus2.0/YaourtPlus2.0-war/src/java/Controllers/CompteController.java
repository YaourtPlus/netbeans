/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Services.composites.ProfilServiceLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

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

    private int idUtilisateur;

    @EJB
    ProfilServiceLocal profilService;

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;

    @ManagedProperty(value = "#{redirectController}")
    private RedirectController redirectController;

    public CompteController() {
        idUtilisateur = -1;
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

    public SessionController getSessionController() {
        return sessionController;
    }

    public RedirectController getRedirectController() {
        return redirectController;
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

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    public void setRedirectController(RedirectController redirectController) {
        this.redirectController = redirectController;
    }
// Methodes ====================================================================
    public String connect() {
        idUtilisateur = profilService.connect(login, passWord);
        if (idUtilisateur != -1) {
            sessionController.setIdUtilisateur(idUtilisateur);
            
            return redirectController.goToMur();
        } else {
            return "connexion?faces-redirect=true";
        }
    }

    public String inscrire() {
        profilService.inscrire(login, passWord, nom, prenom, !"".equals(age) ? Integer.parseInt(age):0, mail);
        return "connexion?faces-redirect=true";
    }

}
