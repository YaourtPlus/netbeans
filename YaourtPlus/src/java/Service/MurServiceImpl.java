/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import DAO.StatutsDAO;
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
    
    @Resource
    StatutsDAO statutDAO;
    
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
                statuts += "<br/>";
                int nb = s.getNbLeger();
                statuts += "<a href='leger.htm?id=" + s.getId() + "'> Léger !" + getQuantity(nb) + "</a>"; // Gestion de l'IMC
                nb = s.getNbLourd();
                statuts += "<a href='lourd.htm?id=" + s.getId() + "'> T'es lourd !" + getQuantity(nb) + "</a>"; // Gestion de l'IMC
                statuts += "</div>";
               
                statuts += "</div>";
            }
        }
        return statuts;
    }
    
    private String getQuantity(int nb){
        return nb > 0 ? " " + Integer.toString(nb) : "" ;
    }

    @Override
    public void addLeger(int idStatut) {
        StatutsEntity statut = statutDAO.find(idStatut);
        
        statut.addLeger();
        
        statutDAO.update(statut);
    }
    
    @Override
    public void addLourd(int idStatut) {
        StatutsEntity statut = statutDAO.find(idStatut);
        
        statut.addLourd();
        
        statutDAO.update(statut);
    }
    
}
