/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import DAO.StatutsEntity;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author tbenoist
 */
@Service
public class ProfilServiceImpl implements ProfilService {

    @Resource
    PersonnesDAO personnesDAO;

    @Override
    public PersonnesEntity getPersonne(int id) {
        return personnesDAO.find(id);
    }

    
    @Override
    public String getFilous(int id){
        PersonnesEntity user = getPersonne(id);
        String listAmis = "";
        for(PersonnesEntity p : user.getListFilous()){
            listAmis += "<div class=\"col-lg-offset-1 col-lg-10\">";
            listAmis += p.getPrenom() + " " + p.getNom() + " <a href='suppression.htm?id=" + p.getId() + "' > Supprimer </a> ";
            listAmis += "</div>";
        }
        return listAmis;
    }
    
    
    @Override
    public boolean exists(String login) {
        return personnesDAO.findByLogin(login) != null;
    }

    @Override
    public boolean ajoutStatut(int idUser, String statut) {
        PersonnesEntity user = getPersonne(idUser);
        StatutsEntity newStatut = new StatutsEntity(statut, new Date(), user);
        
        return personnesDAO.ajoutStatut(user, newStatut);
        
    }
}
