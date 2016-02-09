/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.PersonnesDAO;
import Entities.PersonnesEntity;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Olivier
 */
@Stateless
public class FilousService implements FilousServiceLocal {

    @EJB
    PersonnesDAO personneDAO;
    
    @EJB
    PersonnesServiceLocal personneService;
    
    @Override
    public List<PersonnesEntity> getFilous(int idUtilisateur) {
        List<PersonnesEntity> filous = personneDAO.findFilous(idUtilisateur);
        return filous;        
    }

    @Override
    public List<PersonnesEntity> getFilousPossibles(int idUtilisateur) {
        List<PersonnesEntity> filous = getFilous(idUtilisateur);
        List<PersonnesEntity> gens = personneService.getPersonnes();
        List<PersonnesEntity> filousPossibles = new ArrayList<>();
        
        for(PersonnesEntity g : gens){
            if(!filous.contains(g)){
                filousPossibles.add(g);
            }
        }
        return filousPossibles;
    }

}
