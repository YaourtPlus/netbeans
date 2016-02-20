/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.PersonnesDAO;
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

}
