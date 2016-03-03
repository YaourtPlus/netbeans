/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.elementaires;

import DAO.PersonnesDAO;
import Entities.MessagesEntity;
import Entities.PersonnesEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Olivier
 */
@Stateless
public class PersonnesService implements PersonnesServiceLocal {

    @EJB
    PersonnesDAO personneDAO;

    
    /**
     * Ajout d'une personne à la BD
     * 
     * @param p Personne à ajouter
     */
    @Override
    public void addPersonne(PersonnesEntity p){
        personneDAO.save(p);
    }
    
    
    /**
     * Récupération de toutes les personnes de la BD
     * 
     * @return la liste de toutes les personnes de la BD
     */
    @Override
    public List<PersonnesEntity> getPersonnes() {
        return personneDAO.findAll();
    }

    
    /**
     * Récupération d'une personne selon son ID
     * 
     * @param idPersonne id de la personne à récupérer
     * @return la personne correspondant à l'id
     */
    @Override
    public PersonnesEntity getPersonne(int idPersonne) {
        return personneDAO.find(idPersonne);
    }

    
    /**
     * Récupération des filous de l'utilisateur
     * @param idUtilisateur id de l'utilisateur
     * @return la liste des filous de l'utilisateur
     */
    @Override
    public List<PersonnesEntity> findFilous(int idUtilisateur) {
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        
        return user.getListFilous();
    }

    
    /**
     * Ajout d'un filous à un utilisateur
     * 
     * @param utilisateur utilisateur auquel on ajoute le filou
     * @param filous filou à ajouter
     * @return true si l'ajout s'est correctement effectué, false sinon
     */
    @Override
    public boolean ajoutFilous(PersonnesEntity utilisateur, PersonnesEntity filous){
        return personneDAO.ajoutFilous(utilisateur, filous);
    }
       
    
    /**
     * Suppression d'un filou d'un utilisateur
     * 
     * @param utilisateur utilisateur auquel on ajoute le filou
     * @param filous filou à ajouter
     * @return true si l'ajout s'est correctement effectué, false sinon
     */
    @Override
    public boolean suppressionFilous(PersonnesEntity utilisateur, PersonnesEntity filous){
        return personneDAO.suppressionFilous(utilisateur, filous);
    }
    
    
    /**
     * Ajout d'un message à la liste des messages envoyés d'une personne
     * 
     * @param sender personne émettrice du message
     * @param newMessagesEntity  message envoyé
     */
    @Override
    public void ajoutMessageEnvoi(PersonnesEntity sender, MessagesEntity newMessagesEntity) {
        personneDAO.ajoutMessageEnvoi(sender, newMessagesEntity);
    }

    
    /**
     * Ajout d'un message à la liste des messages reçus d'une personne
     * 
     * @param dest destinataire du message
     * @param newMessagesEntity ùessage envoyé
     */
    @Override
    public void ajoutMessageRecu(PersonnesEntity dest, MessagesEntity newMessagesEntity) {
        personneDAO.ajoutMessageRecu(dest, newMessagesEntity);
    }

    
    /**
     * Récupération d'une personne selon son login et son password
     * 
     * @param login login de la personne
     * @param password password de la personne
     * @return la personne si elle existe, null sinon
     */
    @Override
    public PersonnesEntity getPersonne(String login, String password) {
        return personneDAO.find(login, password);
    }   
    
    
    /**
     * Récupération d'une personne selon son login
     * 
     * @param login le login de la personne à récupérer
     * @return la personne si elle existe, null sinon
     */
    @Override
    public PersonnesEntity getPersonne(String login) {
        return personneDAO.findByLogin(login);
    }

}
