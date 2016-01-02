/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import DAO.PersonnesStatutsDAO;
import DAO.StatutsDAO;
import DAO.StatutsEntity;
import Enumerations.TypeActions;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olivier
 */
@Service
public class MurServiceImpl implements MurService {

    @Resource
    PersonnesDAO personneDAO;

    @Resource
    StatutsDAO statutDAO;

    @Resource
    PersonnesStatutsDAO personneStatutDAO;

    public MurServiceImpl() {

    }

    @Override
    public String getStatuts(int id) {
        PersonnesEntity user = personneDAO.find(id);

        String statuts = "";
        for (PersonnesEntity p : user.getListFilous()) {
            for (StatutsEntity s : p.getStatuts()) {
                statuts += "<div class=\"statuts\">";
                statuts += p.getPrenom() + " " + p.getNom();
                // Gestion des léger + lourd
                statuts += "<div class=\"statuts-texte\">";
                statuts += s.getTexte();
                statuts += "<br/>";
                TypeActions action = user.getAction(s);
                String link = "";
                switch (action) {
                    case noAction:
                        int nb = s.getNbLeger();
                        link = "<a href='leger.htm?id=" + s.getId() + "'>" + getQuantity(nb) + " Léger !</a>";
                        nb = s.getNbLourd();
                        link += "<a href='lourd.htm?id=" + s.getId() + "'>" + getQuantity(nb) + " T'es lourd !</a>";
                        break;
                    case leger:
                        link = "<a href='removeAction.htm?id=" + s.getId() + "'> Vous avez allégé le statut. </a>";
                        break;
                    case lourd:
                        link = "<a href='removeAction.htm?id=" + s.getId() + "'> Vous avez allourdi le statut. </a>";
                        break;
                    default:
                        break;
                }
                statuts += link; // Gestion de l'IMC
//                statuts += link + "<div>" + getQuantity(nb) + "</div>"; // Gestion de l'IMC
                statuts += "</div>";
                statuts += "</div>";
            }
        }
        return statuts;
    }

    private String getQuantity(int nb) {
        return nb > 0 ? " " + Integer.toString(nb) : "";
    }

    @Override
    public void addLeger(int idStatut, int idUser) {
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUser);

        statutDAO.addLeger(statut, user);
    }

    @Override
    public void addLourd(int idStatut, int idUser) {
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUser);

        statutDAO.addLourd(statut, user);
    }

    @Override
    public void removeAction(int idStatut, int idUser) {
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUser);

        TypeActions action = personneStatutDAO.removeAction(user, statut);
        switch (action) {
            case leger:
                statutDAO.removeLeger(statut, user);
                break;
            case lourd:
                statutDAO.removeLourd(statut, user);
                break;
            default:
                break;
        }
    }

}
