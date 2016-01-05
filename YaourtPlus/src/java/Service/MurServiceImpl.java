    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.NotificationsDAO;
import DAO.NotificationsEntity;
import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import DAO.PersonnesStatutsDAO;
import DAO.StatutsDAO;
import DAO.StatutsEntity;
import Enumerations.TypeActions;
import Enumerations.TypeNotifications;
import java.util.Date;
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

    @Resource
    NotificationsDAO notificationDAO;

    /**
     * Récupération des statuts des filous pour affichage
     *
     * @param id id de l'utilisateur
     * @return Un string contenant les différents statuts
     */
    @Override
    public String getStatuts(int id) {
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(id);

        String statuts = "";
        // Parcours des filous de l'utilisateur
        for (PersonnesEntity p : user.getListFilous()) {
            // Parcours des statuts des filous
            // /!\ Récupération des statuts dans DAO selon la date /!\
            for (StatutsEntity s : p.getStatuts()) {
                // Mise en forme des statuts
                statuts += "<div class=\"statuts\">";
                statuts += p.getPrenom() + " " + p.getNom();
                statuts += "<div class=\"statuts-texte\">";
                statuts += s.getTexte();
                statuts += "<br/>";

                // Récupération de l'action de l'utilisateur sur le statut
                TypeActions action = user.getAction(s);
                String link = "";
                // Gestion de l'action
                switch (action) {
                    case noAction: // Possibilité de Leger ou Lourd
                        int nb = s.getNbLeger();
                        link = "<a href='leger.htm?id=" + s.getId() + "'>"
                                + getQuantity(nb) + " Léger !</a>";
                        nb = s.getNbLourd();
                        link += "<a href='lourd.htm?id=" + s.getId() + "'>"
                                + getQuantity(nb) + " T'es lourd !</a>";
                        break;
                    case leger: // Possiblité d'annulation de léger
                        link = "<a href='removeAction.htm?id=" + s.getId()
                                + "'> Vous avez allégé le statut. </a>";
                        break;
                    case lourd: // Possiblité d'annulation de lourd
                        link = "<a href='removeAction.htm?id=" + s.getId()
                                + "'> Vous avez allourdi le statut. </a>";
                        break;
                    default:
                        break;
                } // Fin switch
                statuts += link; // Gestion de l'IMC
                statuts += "</div>";
                statuts += "</div>";
            } // Fin parcours statuts
        } // Fin parcours filous
        return statuts;
    }

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
     * Ajoute un léger au statut
     *
     * @param idStatut id du statut sur lequel l'action est effectuée
     * @param idUser id de l'utilisateur
     */
    @Override
    public void addLeger(int idStatut, int idUser) {
        // Récupération des entités
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUser);

        // Ajout du léger sur le statut et l'utilisateur
        statutDAO.addLeger(statut, user);

        NotificationsEntity notifLeger = new NotificationsEntity(new Date(),
                TypeNotifications.notifLeger.getId());
        notifLeger.setNotifieur(user);

        // Création de la notification dans la BD
        notificationDAO.save(notifLeger);

        // Ajout de la notification au nouveau filou
        personneDAO.ajoutNotif(user, statut.getAuteur(), notifLeger);

    }

    /**
     * Ajoute un lourd au statut
     *
     * @param idStatut id du statut sur lequel l'action est effectuée
     * @param idUser id de l'utilisateur
     */
    @Override
    public void addLourd(int idStatut, int idUser) {
        // Récupération des entités
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUser);

        // Ajout du Lourd sur le statut et l'utilisateur
        statutDAO.addLourd(statut, user);

        NotificationsEntity notifLourd = new NotificationsEntity(new Date(),
                TypeNotifications.notifLourd.getId());
        notifLourd.setNotifieur(user);

        // Création de la notification dans la BD
        notificationDAO.save(notifLourd);

        // Ajout de la notification au nouveau filou
        personneDAO.ajoutNotif(user, statut.getAuteur(), notifLourd);
    }

    /**
     * Annulation d'une action
     *
     * @param idStatut id du statut sur lequel on veut annuler l'action
     * @param idUser id de l'utilisateur
     */
    @Override
    public void removeAction(int idStatut, int idUser) {
        // Récupération des entités
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUser);

        // Récuoération du type d'action effectuée par l'utilisateur
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
        } // Fin switch
    }

}
