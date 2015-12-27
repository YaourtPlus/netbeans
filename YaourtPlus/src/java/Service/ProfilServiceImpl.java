/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author tbenoist
 */
@Service
public class ProfilServiceImpl implements ProfilService {

    @Resource
    PersonnesDAO personneDAO;

    @Override
    public PersonnesEntity getPersonne(int id) {
        return personneDAO.find(id);
    }

    
    @Override
    public String getFilous(int id){
        PersonnesEntity user = personneDAO.find(id);
        
        String listAmis = "";
        for(PersonnesEntity p : user.getListFilous()){
            listAmis += "<div class=\"col-lg-offset-1 col-lg-10\">";
            listAmis += p.getPrenom() + " " + p.getNom();
            listAmis += "</div>";
        }
        return listAmis;
    }
    
    
    @Override
    public boolean exists(String login) {
        return personneDAO.findByLogin(login) != null;
    }
}
