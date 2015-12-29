/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import DAO.StatutsEntity;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olivier
 */
@Service
public class MurServiceImpl implements MurService{

    @Resource
    PersonnesDAO personnesDAO;
    
    public MurServiceImpl() {
    
    }
    
    @Override
    public String getStatuts(int id){
        PersonnesEntity user = personnesDAO.find(id);

        
        String statuts = "";
        for(PersonnesEntity p : user.getListFilous()){
            for(StatutsEntity s : p.getStatuts()){
                statuts += "<div class=\"statuts\">";
                statuts += p.getPrenom() + " " + p.getNom(); 
                // Gestion des léger + lourd
                statuts += "<div class=\"statuts-texte\">";
                statuts += s.getTexte();
                statuts += "</div>";
                statuts += "</div>";
            }
        }
        return statuts;
    }
    
    
}
