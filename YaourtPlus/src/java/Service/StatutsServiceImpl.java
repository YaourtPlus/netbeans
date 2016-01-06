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
import java.util.Calendar;
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
            for (StatutsEntity s : p.getStatuts(Calendar.getInstance().getTime())) {
                statuts += statutToString(s, user, "mur");
            } // Fin parcours statuts
        } // Fin parcours filous
        return statuts;
    }

    /**
     * Récupération des statuts émis par une personne pour l'affichage
     *
     * @param idUtilisateur l'id de l'utilisateur courant
     * @param idPersonne l'id de la personne dont on veut les statuts
     * @return un string formant les statuts
     */
    @Override
    public String getPersonneStatutsEmis(int idUtilisateur, int idPersonne) {
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        PersonnesEntity personne = personneDAO.find(idPersonne);

        String statuts = "";
        for (StatutsEntity s : personne.getStatutsEmis()) {
            if(s.getDestinataire().equals(user)){
                statuts += statutToString(s, user, "statut");
            }
        }
        return statuts;
    }

    /**
     * Récupération des statuts postés sur le mur d'une personne pour
     * l'affichage
     *
     * @param idUtilisateur l'id de l'utilisateur courant
     * @param idPersonne l'id de la personne dont on veut les statuts
     * @return un string formant les statuts
     */
    @Override
    public String getPersonneStatutsRecu(int idUtilisateur, int idPersonne) {
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        PersonnesEntity personne = personneDAO.find(idPersonne);

        String statuts = "User : " + user.getLogin();
        statuts += "Personne : " + personne.getLogin();
        for (StatutsEntity s : personne.getStatutsRecu()) {
            if (!s.getAuteur().equals(personne)) {
                statuts += statutToString(s, user, "statut");
            }
        }
        return statuts;
    }

    /**
     * Met en forme un statut
     *
     * @param s le statut à mettre en forme
     * @param user l'utilisateur pour qui sera rendu le statut
     * @param path l'endroit ou se trouve la page
     * @return le statut mis en forme.
     */
    @Override
    public String statutToString(StatutsEntity s, PersonnesEntity user, String path) {

        String statuts = "<div class=\"statuts\">"; // Conteneur du statut
        statuts += s.getAuteur().getPrenom() + " " + s.getAuteur().getNom();
        statuts += "<div class=\"statuts-texte\">"; // Conteneur du texte du statut
        statuts += s.getTexte();
        statuts += "<br/>";

        // Récupération de l'action de l'utilisateur sur le statut
        TypeActions action = user.getAction(s);
        int idDestinataire;
        if (s.getDestinataire() == null) { // Si le destinataire est à null, c'est que c'est un statut que l'utilisateur à posté
            idDestinataire = s.getAuteur().getId();
        } else {
            idDestinataire = s.getDestinataire().getId();
        }
        String link = "";
        // Gestion de l'action
        switch (action) {
            case noAction: // Possibilité de Leger ou Lourd
                int nb = s.getNbLeger();
                link = "<a href='" + servletContext.getContextPath() + "/" + path + "/leger.htm?id=" + s.getId() + "&idPersonne=" + idDestinataire + "'>"
                        + getQuantity(nb) + " Léger !</a>";
                nb = s.getNbLourd();
                link += "<a href='" + servletContext.getContextPath() + "/" + path + "/lourd.htm?id=" + s.getId() + "&idPersonne=" + idDestinataire + "'>"
                        + getQuantity(nb) + " T'es lourd !</a>";
                break;
            case leger: // Possiblité d'annulation de léger
                link = "<a href='" + servletContext.getContextPath() + "/" + path + "/removeAction.htm?id=" + s.getId() + "&idPersonne=" + idDestinataire
                        + "'> Vous avez allégé le statut. </a>";
                break;
            case lourd: // Possiblité d'annulation de lourd
                link = "<a href='" + servletContext.getContextPath() + "/" + path + "/removeAction.htm?id=" + s.getId() + "&idPersonne=" + idDestinataire
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
        statuts += "<form Method='POST' action='" + servletContext.getContextPath() + "/" + path + "/ajoutCommentaire.htm?idStatut=" + s.getId() + "&idPersonne=" + idDestinataire + "'>"
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
