/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.PersonnesDAO;
import Entities.PersonnesEntity;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Olivier
 */
@Stateless
public class ProfilService implements ProfilServiceLocal {
    
    @EJB
    PersonnesDAO personneDAO;

    @Override
    public boolean connect(String login, String passWord) {
        //TODO
        return false;
    }


    @Override
    public void inscrire(String login, String passWord, String nom, String prenom, int age, String mail) {
        /*PersonnesEntity pe = new PersonnesEntity(nom, prenom, age, mail, login, passWord);
        personneDAO.save(pe);*/
    }
}
