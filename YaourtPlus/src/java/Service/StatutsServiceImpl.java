/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.CommentairesEntity;
import DAO.FichiersDAO;
import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import DAO.StatutsEntity;
import Enumerations.TypeActions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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

    @Resource
    FichiersDAO fichierDAO;

    @Autowired
    FichierService fichierService;

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
        List<StatutsEntity> statutsFilous = new ArrayList<>();

        for (PersonnesEntity p : user.getListFilous()) {
            // Parcours des statuts des filous
            // /!\ Récupération des statuts dans DAO selon la date /!\
            for (StatutsEntity s : p.getStatuts(Calendar.getInstance().getTime())) {
                statutsFilous.add(s);
            } // Fin parcours statuts
        } // Fin parcours filous
        Collections.sort(statutsFilous, new Comparator<StatutsEntity>() {

            @Override
            public int compare(StatutsEntity o1, StatutsEntity o2) {
                return o2.getNbLeger().compareTo(o1.getNbLeger());
            }
        });
        for (StatutsEntity s : statutsFilous) {
            statuts += statutToString(s, user, "mur");
        }
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
            // On recherche dans les statuts émis, les statuts dont 
            // l'utilisateur est le destinataire
            if (s.getDestinataire().equals(user)) {
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

        String statuts = "";
        for (StatutsEntity s : personne.getStatutsRecu()) {
            // On recherche les statuts dans les statuts reçu où 
            // l'auteur n'est pas le propriétaire du mur
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

        SimpleDateFormat statutDate = new SimpleDateFormat("dd,MM,yyyy 'a' HH:mm:ss ");
        String date;
        date = statutDate.format(s.getDate());

        String statuts = "<div class=\"statuts\">"; // Conteneur du statut

        statuts += "<div class=\"row\">";

        statuts += "<div class=\"pull-left statut-left\">";
        statuts += s.getAuteur().getPrenom() + " " + s.getAuteur().getNom();
        statuts += "</div>";

        statuts += "<div class=\"pull-right statut-right\">";
        statuts += date;
        statuts += "</div>";

        statuts += "</div>";

        statuts += "<div class=\"statuts-texte\">"; // Conteneur du texte du statut
        statuts += s.getTexte();
        statuts += "</div>";
        statuts += "<div class=\"vote\">";

        if (s.getListeFichiers().size() > 0) {
            statuts += fichierService.afficherFichier(s.getListeFichiers().get(0));
            statuts += "<br />";
        }

        // Récupération de l'action de l'utilisateur sur le statut
        TypeActions action = user.getAction(s);
        int idDestinataire;
        if (s.getDestinataire() == null) {
            idDestinataire = s.getAuteur().getId();
        } else {
            idDestinataire = s.getDestinataire().getId();
        }

        int nbLeger = s.getNbLeger();
        int nbLourd = s.getNbLourd();

        String link = "";
        String leger = " Léger";
        if (nbLeger > 1) {
            leger += "s";
        }
        String lourd = " Lourd";
        if (nbLourd > 1) {
            lourd += "s";
        }
        statuts += "<div class=\"row\">";
        statuts += Integer.toString(nbLeger) + leger + " ";

        statuts += Integer.toString(nbLourd) + lourd;
        statuts += "</div>";
        // Gestion de l'action
        switch (action) {
            case noAction: // Possibilité de Leger ou Lourd
                link += "<a href='" + servletContext.getContextPath() + "/" + path + "/leger.htm?id=" + s.getId() + "&idPersonne=" + idDestinataire + "'>"
                        + "Léger!</a> ";
                link += "<a href='" + servletContext.getContextPath() + "/" + path + "/lourd.htm?id=" + s.getId() + "&idPersonne=" + idDestinataire + "'>"
                        + "T'es lourd!</a>";
                break;
            case leger: // Possiblité d'annulation de léger
                link += "<a href='" + servletContext.getContextPath() + "/" + path + "/removeAction.htm?id=" + s.getId() + "&idPersonne=" + idDestinataire
                        + "'> Vous avez allégé le statut. </a>";
                break;
            case lourd: // Possiblité d'annulation de lourd
                link += "<a href='" + servletContext.getContextPath() + "/" + path + "/removeAction.htm?id=" + s.getId() + "&idPersonne=" + idDestinataire
                        + "'> Vous avez allourdi le statut </a>";
                break;
            default:
                break;
        } // Fin switch

        statuts += link;
        statuts += "</div>";
        statuts += "</div>";
        for (CommentairesEntity c : s.getCommentaires()) {
            date = statutDate.format(c.getDate());

            statuts += "<div class=\"commentaires\">"; // Conteneur du commentaire 
            statuts += c.getAuteur().getPrenom() + " " + c.getAuteur().getNom();
            statuts += "<div class=\"commentaire-texte\">"; // Conteneur du texte du commentaire
            statuts += c.getTexte();
            statuts += "</div>";
            statuts += "<div>";
            statuts += date;
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
