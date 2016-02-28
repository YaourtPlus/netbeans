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
    
    @Override
    public void addPersonne(PersonnesEntity p){
        personneDAO.save(p);
    }
    
    @Override
    public List<PersonnesEntity> getPersonnes() {
        return personneDAO.findAll();
    }

    @Override
    public PersonnesEntity getPersonne(int idPersonne) {
        return personneDAO.find(idPersonne);
    }

    /**
     * Dans PersonnesService ou dans FilousService?
     * @param idUtilisateur
     * @return 
     */
    @Override
    public List<PersonnesEntity> findFilous(int idUtilisateur) {
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        
        return user.getListFilous();
    }

    @Override
    public boolean ajoutFilous(PersonnesEntity utilisateur, PersonnesEntity filous){
        return personneDAO.ajoutFilous(utilisateur, filous);
    }
       
    @Override
    public boolean suppressionFilous(PersonnesEntity utilisateur, PersonnesEntity filous){
        return personneDAO.suppressionFilous(utilisateur, filous);
    }
    
    @Override
    public void ajoutMessageEnvoi(PersonnesEntity sender, MessagesEntity newMessagesEntity) {
        personneDAO.ajoutMessageEnvoi(sender, newMessagesEntity);
    }

    
    @Override
    public void ajoutMessageRecu(PersonnesEntity dest, MessagesEntity newMessagesEntity) {
        personneDAO.ajoutMessageRecu(dest, newMessagesEntity);
    }

    @Override
    public PersonnesEntity getPersonne(String login, String password) {
        return personneDAO.find(login, password);
    }   
    
    @Override
    public PersonnesEntity getPersonne(String login) {
        return personneDAO.findByLogin(login);
    }

}
