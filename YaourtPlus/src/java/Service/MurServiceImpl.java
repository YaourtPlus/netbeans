    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.CommentairesDAO;
import DAO.CommentairesEntity;
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
        StatutsEntity newStatut = new StatutsEntity(statut, new Date());
        // Mise à jour de l'auteur du statut
        newStatut.setAuteur(user);
        // Ajout dans la BD
        int id = statutDAO.save(newStatut);

        // Ajout du statut
        personneDAO.ajoutStatut(user, newStatut);
        return id;
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
        PersonnesEntity auteur = statut.getAuteur();
        /* Création du commentaire
         Un commentaire n'est rien d'autre qu'un statut en réponse à un autre statut
         */
        CommentairesEntity newCommentaire = new CommentairesEntity(commentaire, new Date());
        newCommentaire.setAuteur(user);
        // Ajout du statut commenté
        newCommentaire.setStatut(statut);
        
        commentaireDAO.save(newCommentaire);
        
        // Création de la notification dans la BD
        notificationService.createNotification(TypeNotifications.notifCommentaire, user, auteur, statut);
        
        return commentaireDAO.ajoutCommentaire(statut, newCommentaire);
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
        PersonnesEntity auteur = statut.getAuteur();
        // Ajout du léger sur le statut et l'utilisateur
        statutDAO.addLeger(statut, user);

        NotificationsEntity notifLeger = new NotificationsEntity(new Date(),
                TypeNotifications.notifLeger.getId());
        notifLeger.setNotifieur(user);

        // Création de la notification dans la BD
        notificationService.createNotification(TypeNotifications.notifLeger, user, auteur, statut);
        
        // Ajout de la notification au nouveau filou
        personneDAO.ajoutNotif(user, auteur, notifLeger);

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
        PersonnesEntity auteur = statut.getAuteur();

        // Ajout du Lourd sur le statut et l'utilisateur
        statutDAO.addLourd(statut, user);

        NotificationsEntity notifLourd = new NotificationsEntity(new Date(),
                TypeNotifications.notifLourd.getId());
        notifLourd.setNotifieur(user);

        // Création de la notification dans la BD
        notificationService.createNotification(TypeNotifications.notifLourd, user, auteur, statut);

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
