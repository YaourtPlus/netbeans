    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.CommentairesDAO;
import DAO.CommentairesEntity;
import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import DAO.PersonnesStatutsDAO;
import DAO.PersonnesStatutsEntity;
import DAO.StatutsDAO;
import DAO.StatutsEntity;
import Enumerations.TypeActions;
import Enumerations.TypeNotifications;
import java.util.Calendar;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olivier
 */
@Service
public class MurServiceImpl implements MurService {

    @Autowired
    NotificationsService notificationService;

    @Resource
    PersonnesDAO personneDAO;

    @Resource
    StatutsDAO statutDAO;

    @Resource
    PersonnesStatutsDAO personneStatutDAO;

    @Resource
    CommentairesDAO commentaireDAO;

    /**
     * Ajout d'un statut par l'utilisateur
     *
     * @param idUtilisateur id de l'utilisateur
     * @param statut texte du statut à ajouter
     * @return true si l'ajout est effectué correctement, false sinon (statut
     * vide)
     */
    @Override
    public int ajoutStatut(int idUtilisateur, String statut) {
        if (statut.length() == 0) { // Gestion d'un statut vide
            return 0;
        }
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);

        // Création du statut
        StatutsEntity newStatut = new StatutsEntity(statut, Calendar.getInstance().getTime());
        // Mise à jour de l'auteur du statut
        newStatut.setAuteur(user);
        newStatut.setDestinataire(user);
        // Ajout dans la BD
        int id = statutDAO.save(newStatut);

        // Ajout du statut
        personneDAO.ajoutStatutEmis(user, newStatut);
        personneDAO.ajoutStatutRecu(user, newStatut);
        return id;
    }

    /**
     * Ajout d'un statut par l'utilisateur sur le mur de la personne p
     *
     * @param idUtilisateur id de l'utilisateur
     * @param idPersonne id de la personne détentrice du mur
     * @param statut texte du statut à ajouter
     * @return true si l'ajout est effectué correctement, false sinon (statut
     * vide)
     */
    @Override
    public int posterStatut(int idUtilisateur, int idPersonne, String statut) {
        if (statut.length() == 0) { // Gestion d'un statut vide
            return 0;
        }
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        PersonnesEntity destinataire = personneDAO.find(idPersonne);

        // Création du statut
        StatutsEntity newStatut = new StatutsEntity(statut, Calendar.getInstance().getTime());
        // Mise à jour de l'auteur du statut
        newStatut.setAuteur(user);
        newStatut.setDestinataire(destinataire);
        // Ajout dans la BD
        int idStatut = statutDAO.save(newStatut);

        // Ajout du statut posté à l'utilisateur
        personneDAO.ajoutStatutEmis(user, newStatut);

        // Ajout du statut posté au destinataire
        personneDAO.ajoutStatutRecu(destinataire, newStatut);

        // Création d'une action sur le statut par l'utilisateur (post du statut)
        personneStatutDAO.addPost(idStatut, user);

        // Création d'une notification auprès du destinataire
        notificationService.createNotification(TypeNotifications.notifStatut, user, destinataire, idStatut);

        return idStatut;
    }

    /**
     * Ajout d'un commentaire à un statut
     *
     * @param idUtilisateur id de l'utilisateur
     * @param idStatut id du statut auquel on ajoute un commentaire
     * @param commentaire le texte du commentaire à ajouter
     * @return true si l'ajout est effectué correctement, false sinon
     * (commentaire vide)
     */
    @Override
    public boolean ajoutCommentaire(int idUtilisateur, int idStatut, String commentaire) {
        if (commentaire.length() == 0) {
            return false;
        }

        // Récupération du statut
        StatutsEntity statut = statutDAO.find(idStatut);

        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        /* Création du commentaire
         Un commentaire n'est rien d'autre qu'un statut en réponse à un autre statut
         */
        CommentairesEntity newCommentaire = new CommentairesEntity(commentaire, Calendar.getInstance().getTime());
        newCommentaire.setAuteur(user);
        // Ajout du statut commenté
        newCommentaire.setStatut(statut);

        commentaireDAO.save(newCommentaire);
        // Création d'une notification à l'auteur du statut
        notificationService.createNotification(TypeNotifications.notifCommentaire, user, statut.getDestinataire(), statut.getId());
        // Préparation de la notifications des personnes ayant commenté le statut
        for (PersonnesStatutsEntity ps : statut.getStatutsActeurs()) {
            // Création de la notification aux gens qui ont agit sur le statut
            if (ps.getStatut().equals(statut) && (ps.getCommentaire() || ps.getPost())) {
                notificationService.createNotification(TypeNotifications.notifCommentaire, user, ps.getPersonne(), statut.getId());
            }
        }
        statutDAO.addCommentaire(statut, user);

        return commentaireDAO.ajoutCommentaire(statut, newCommentaire);
    }

    /**
     * Ajoute un léger au statut
     *
     * @param idStatut id du statut sur lequel l'action est effectuée
     * @param idUser id de l'utilisateur
     */
    @Override
    public void addLeger(int idStatut, int idUser
    ) {
        // Récupération des entités
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUser);
        PersonnesEntity auteur = statut.getAuteur();
        statutDAO.addLeger(statut, user);

        // Création de la notification dans la BD
        notificationService.createNotification(TypeNotifications.notifLeger, user, auteur, statut.getId());
    }

    /**
     * Ajoute un lourd au statut
     *
     * @param idStatut id du statut sur lequel l'action est effectuée
     * @param idUser id de l'utilisateur
     */
    @Override
    public void addLourd(int idStatut, int idUser
    ) {
        // Récupération des entités
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUser);
        PersonnesEntity auteur = statut.getAuteur();

        // Ajout du léger sur le statut et l'utilisateur
        statutDAO.addLourd(statut, user);

        // Création de la notification dans la BD
        notificationService.createNotification(TypeNotifications.notifLourd, user, auteur, statut.getId());
    }

    /**
     * Annulation d'une action
     *
     * @param idStatut id du statut sur lequel on veut annuler l'action
     * @param idUser id de l'utilisateur
     */
    @Override
    public void removeAction(int idStatut, int idUser
    ) {
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
