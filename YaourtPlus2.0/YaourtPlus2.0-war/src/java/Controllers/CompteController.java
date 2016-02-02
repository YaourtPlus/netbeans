/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

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
    private String password;
    
    public CompteController() {
    }

    
// Getters =====================================================================
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    
// Setters =====================================================================
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
