/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.composites;

import Entities.IMCEntity;
import Entities.PersonnesEntity;
import Services.elementaires.PersonnesServiceLocal;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Olivier
 */
@Stateless
public class ProfilService implements ProfilServiceLocal {
    
    @EJB
    PersonnesServiceLocal personneService;

    @Override
    public int connect(String login, String passWord) {
        PersonnesEntity pe = personneService.getPersonne(login, passWord);
        return pe != null ? pe.getId() : -1;
    }


    @Override
    public void inscrire(String login, String passWord, String nom, String prenom, int age, String mail) {
        PersonnesEntity pe = new PersonnesEntity(nom, prenom, age, mail, login, passWord);
        // Création de la date d'inscription
        Date dateInscription = Calendar.getInstance().getTime();
        
        pe.setDateInscription(dateInscription);
        
        // Création d'un IMC
        pe.setImc(new IMCEntity());
        personneService.addPersonne(pe);
    }

    @Override
    public boolean exists(String login) {
        return personneService.getPersonne(login) != null;
    }
}
