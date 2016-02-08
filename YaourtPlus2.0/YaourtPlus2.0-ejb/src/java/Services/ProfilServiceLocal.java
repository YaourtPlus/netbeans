/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import javax.ejb.Local;

/**
 *
 * @author Olivier
 */
@Local
public interface ProfilServiceLocal {
    public boolean connect(String login, String passWord);
    public void inscrire(String login, String passWord, String nom, String prenom, int age, String mail);
}
