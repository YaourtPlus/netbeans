/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.CommentairesEntity;
import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import DAO.StatutsEntity;
import Enumerations.TypeActions;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tbenoist
 */
@Service
public class StatutsServiceImpl implements StatutsService {

    @Resource
    PersonnesDAO personneDAO;

    @Autowired
    ServletContext servletContext;

    /**
     * Mise en forme de la quantité de léger / lourd d'un statut
     *
     * @param nb quantité de léger / lourd
     * @return un string vide ou la quantité de léger / lourd
     */
    private String getQuantity(int nb) {
        return nb > 0 ? " " + Integer.toString(nb) : "";
    }

    /**
     * Récupération des statuts des filous pour affichage
     *
     * @param idUtilisateur id de l'utilisateur
     * @return Un string contenant les différents statuts
     */
    @Override
    public String getStatuts(int idUtilisateur) {
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);

        String statuts = "";
        // Parcours des filous de l'utilisateur
        for (PersonnesEntity p : user.getListFilous()) {
            // Parcours des statuts des filous
            // /!\ Récupération des statuts dans DAO selon la date /!\
            for (StatutsEntity s : p.getStatuts(new Date())) {//new Date())) {
                statuts += statutToString(s, user, "mur");
                // Mise en forme des statuts
                /*statuts += "<div class=\"statuts\">"; // Conteneur du statut
                statuts += p.getPrenom() + " " + p.getNom();
                statuts += "<div class=\"statuts-texte\">"; // Conteneur du texte du statut
                statuts += s.getTexte();
                statuts += "<br/>";

                // Récupération de l'action de l'utilisateur sur le statut
                TypeActions action = user.getAction(s);
                String link = "";
                // Gestion de l'action
                switch (action) {
                    case noAction: // Possibilité de Leger ou Lourd
                        int nb = s.getNbLeger();
                        link = "<a href='" + servletContext.getContextPath() + "/mur/leger.htm?id=" + s.getId() + "'>"
                                + getQuantity(nb) + " Léger !</a>";
                        nb = s.getNbLourd();
                        link += "<a href='" + servletContext.getContextPath() + "/mur/lourd.htm?id=" + s.getId() + "'>"
                                + getQuantity(nb) + " T'es lourd !</a>";
                        break;
                    case leger: // Possiblité d'annulation de léger
                        link = "<a href='" + servletContext.getContextPath() + "/mur/removeAction.htm?id=" + s.getId()
                                + "'> Vous avez allégé le statut. </a>";
                        break;
                    case lourd: // Possiblité d'annulation de lourd
                        link = "<a href='" + servletContext.getContextPath() + "/mur/removeAction.htm?id=" + s.getId()
                                + "'> Vous avez allourdi le statut. </a>";
                        break;
                    default:
                        break;
                } // Fin switch
                statuts += link;

                statuts += "</div>";
                statuts += "</div>";
                for (CommentairesEntity com : s.getCommentaires()) {
                    statuts += "<div class=\"statuts\">"; // Conteneur du commentaire
                    statuts += com.getAuteur().getPrenom() + " " + com.getAuteur().getNom();
                    statuts += "<div class=\"statuts-texte\">"; // Conteneur du texte du commentaire
                    statuts += com.getTexte();
                    statuts += "</div>";
                    statuts += "</div>";
                }
                // Création du commentaire de statut
                statuts += "<form Method='POST' action='" + servletContext.getContextPath() + "/mur/ajoutCommentaire.htm?idStatut=" + s.getId() + "> "
                        + "<textarea rows='3' cols='75' name='commentaire' "
                        + "id='commentaire' class='form-control pull-left' "
                        + "placeholder='Ajouter un commentaire' "
                        + "onfocus='this.placeholder = ''' "
                        + "onblur='this.placeholder = 'Ajouter un ptit statut''>"
                        + "</textarea> "
                        + "<div id='connectButton'> "
                        + "<input type='submit' value='Publier' name='submit'/> "
                        + "</div> "
                        + "</form>";
*/
            } // Fin parcours statuts
        } // Fin parcours filous
        return statuts;
    }

    @Override
    public String getUtilisateurStatuts(int idUtilisateur) {
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);

        String statuts = "";
        for (StatutsEntity s : user.getStatuts()) {
            statuts += statutToString(s, user, "statut");
            /*         statuts += "<div class=\"statuts\">"; // Conteneur du statut
             statuts += user.getPrenom() + " " + user.getNom();
             statuts += "<div class=\"statuts-texte\">"; // Conteneur du texte du statut
             statuts += s.getTexte();
             statuts += "<br/>";

             // Récupération de l'action de l'utilisateur sur le statut
             TypeActions action = user.getAction(s);
             String link = "";
             // Gestion de l'action
             switch (action) {
             case noAction: // Possibilité de Leger ou Lourd
             int nb = s.getNbLeger();
             link = "<a href='" + servletContext.getContextPath() + "/statut/leger.htm?id=" + s.getId() + "'>"
             + getQuantity(nb) + " Léger !</a>";
             nb = s.getNbLourd();
             link += "<a href='" + servletContext.getContextPath() + "/statut/lourd.htm?id=" + s.getId() + "'>"
             + getQuantity(nb) + " T'es lourd !</a>";
             break;
             case leger: // Possiblité d'annulation de léger
             link = "<a href='" + servletContext.getContextPath() + "/statut/removeAction.htm?id=" + s.getId()
             + "'> Vous avez allégé le statut. </a>";
             break;
             case lourd: // Possiblité d'annulation de lourd
             link = "<a href='" + servletContext.getContextPath() + "/statut/removeAction.htm?id=" + s.getId()
             + "'> Vous avez allourdi le statut. </a>";
             break;
             default:
             break;
             } // Fin switch
             statuts += link;
             statuts += "</div>";
             statuts += "</div>";
             for (CommentairesEntity c : s.getCommentaires()) {
             statuts += "<div class=\"commentaires\">"; // Conteneur du commentaire 
             statuts += c.getAuteur().getPrenom() + " " + c.getAuteur().getNom();
             statuts += "<div class=\"commentaire-texte\">"; // Conteneur du texte du commentaire
             statuts += c.getTexte();
             statuts += "</div>";
             statuts += "</div>";
             }
             // Création du commentaire de statut
             statuts += "<form Method='POST' action='" + servletContext.getContextPath() + "/statut/ajoutCommentaire.htm?idStatut=" + s.getId() + "'>"
             + "<textarea rows='3' cols='75' name='commentaire' "
             + "id='commentaire' class='form-control pull-left' "
             + "placeholder='Ajouter un commentaire' "
             + "onfocus='this.placeholder = ''' "
             + "onblur='this.placeholder = 'Ajouter un ptit statut''>"
             + "</textarea> "
             + "<div id='connectButton'> "
             + "<input type='submit' value='Publier' name='submit'/> "
             + "</div> "
             + "</form>";*/
             }

            return statuts;
        }
    
    
    
    @Override
    public String statutToString(StatutsEntity s, PersonnesEntity user, String path) {

        String statuts = "<div class=\"statuts\">"; // Conteneur du statut
        statuts += s.getAuteur().getPrenom() + " " + s.getAuteur().getNom();
        statuts += "<div class=\"statuts-texte\">"; // Conteneur du texte du statut
        statuts += s.getTexte();
        statuts += "<br/>";

        // Récupération de l'action de l'utilisateur sur le statut
        TypeActions action = user.getAction(s);
        String link = "";
        // Gestion de l'action
        switch (action) {
            case noAction: // Possibilité de Leger ou Lourd
                int nb = s.getNbLeger();
                link = "<a href='" + servletContext.getContextPath() + "/" + path + "/leger.htm?id=" + s.getId() + "'>"
                        + getQuantity(nb) + " Léger !</a>";
                nb = s.getNbLourd();
                link += "<a href='" + servletContext.getContextPath() + "/" + path + "/lourd.htm?id=" + s.getId() + "'>"
                        + getQuantity(nb) + " T'es lourd !</a>";
                break;
            case leger: // Possiblité d'annulation de léger
                link = "<a href='" + servletContext.getContextPath() + "/" + path + "/removeAction.htm?id=" + s.getId()
                        + "'> Vous avez allégé le statut. </a>";
                break;
            case lourd: // Possiblité d'annulation de lourd
                link = "<a href='" + servletContext.getContextPath() + "/" + path + "/removeAction.htm?id=" + s.getId()
                        + "'> Vous avez allourdi le statut. </a>";
                break;
            default:
                break;
        } // Fin switch
        statuts += link;
        statuts += "</div>";
        statuts += "</div>";
        for (CommentairesEntity c : s.getCommentaires()) {
            statuts += "<div class=\"commentaires\">"; // Conteneur du commentaire 
            statuts += c.getAuteur().getPrenom() + " " + c.getAuteur().getNom();
            statuts += "<div class=\"commentaire-texte\">"; // Conteneur du texte du commentaire
            statuts += c.getTexte();
            statuts += "</div>";
            statuts += "</div>";
        }
        // Création du commentaire de statut
        statuts += "<form Method='POST' action='" + servletContext.getContextPath() + "/" + path + "/ajoutCommentaire.htm?idStatut=" + s.getId() + "'>"
                + "<textarea rows='3' cols='75' name='commentaire' "
                + "id='commentaire' class='form-control pull-left' "
                + "placeholder='Ajouter un commentaire' "
                + "onfocus='this.placeholder = ''' "
                + "onblur='this.placeholder = 'Ajouter un ptit statut''>"
                + "</textarea> "
                + "<div id='connectButton'> "
                + "<input type='submit' value='Publier' name='submit'/> "
                + "</div> "
                + "</form>";

        return statuts;
    }
    
 
}
