/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.composites;

import Entities.IMCEntity;
import Entities.PersonnesEntity;
import Services.elementaires.HashServiceLocal;
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
    
    @EJB
    HashServiceLocal hashService;

    
    /**
     * Connection d'un utilisateur
     * 
     * @param login login de l'utilisateur
     * @param passWord password de l'utilisateur
     * @return l'identifiant de la personne connecté si il existe, -1 sinon
     */
    @Override
    public int connect(String login, String passWord) {
        // Récupération de la personne à parti du login et du password
        PersonnesEntity pe = personneService.getPersonne(login, hashService.hash(passWord));
        
        // Si on a récupéré une personne
        if(pe != null){
            // On met à jour la date de connexion
            pe.setDateConnexion(new Date());
            // On retourne l'id de la personne
            return pe.getId();
        }
        else{ // Si la personne n'existe pas
            // On retourne -1
            return -1;
        }
    }


    /**
     * Inscription d'une personne
     * 
     * @param login login de la personne
     * @param passWord password de la personne
     * @param nom nom de la personne
     * @param prenom prénom de la personne
     * @param age age de la personne
     * @param mail mail de la personne
     */
    @Override
    public void inscrire(String login, String passWord, String nom, String prenom, int age, String mail) {
        // Création de la nouvelle entité
        PersonnesEntity pe = new PersonnesEntity(nom, prenom, age, mail, login, hashService.hash(passWord));

        // Ajout de la date d'inscription
        Date dateInscription = Calendar.getInstance().getTime();        
        pe.setDateInscription(dateInscription);
        
        // Création d'un IMC
        pe.setImc(new IMCEntity());
        
        // Ajout de la personne à la BD
        personneService.addPersonne(pe);
    }

    
    /**
     * Test d'existence d'une personne dans la BD (l'id et le login sont uniques)
     * 
     * @param login le login de la personne
     * @return true si une personne possède ce login, false sinon
     */
    @Override
    public boolean exists(String login) {
        return personneService.getPersonne(login) != null;
    }
}
