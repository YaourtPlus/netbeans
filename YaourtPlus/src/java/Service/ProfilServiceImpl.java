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
import DAO.StatutsDAO;
import DAO.StatutsEntity;
import Enumerations.TypeNotifications;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tbenoist
 */
@Service
public class ProfilServiceImpl implements ProfilService {

    @Resource
    PersonnesDAO personneDAO;

    @Resource
    StatutsDAO statutDAO;
    
    @Autowired
    FichierService fichierService;
    
    @Resource
    NotificationsDAO notificationDAO;

    @Override
    public PersonnesEntity getPersonne(int idUtilisateur) {
        return personneDAO.find(idUtilisateur);
    }

    /**
     * Liste les différents filous de l'utilisateur
     *
     * @param idUtilisateur id de l'utilisateur
     * @return un string contenant les différents filous
     */
    @Override
    public String getFilous(int idUtilisateur) {
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);

        // Création de la liste
        String listAmis = "<ul class=\"list-unstyled\">";

        // Parcours de la liste de filous de l'utilisateur
        for (PersonnesEntity p : user.getListFilous()) {
            listAmis += "<li>";
            listAmis += p.getPrenom() + " " + p.getNom()
                    + " <a href='suppression.htm?id=" + p.getId()
                    + "' > Supprimer </a> ";
            listAmis += "</li>";
        }
        listAmis += "</ul>";
        return listAmis;
    }

    /**
     * Test d'existence d'un login
     *
     * @param login le login à tester
     * @return true si le login existe dans la base de données, false sinon
     */
    @Override
    public boolean exists(String login) {
        return personneDAO.findByLogin(login) != null;
    }

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
        
        // Ajout dans la BD
        statutDAO.save(newStatut);

        // Ajout du statut
         return personneDAO.ajoutStatut(user, newStatut).getId();
         
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

        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);

        // Récupération du statut
        StatutsEntity statut = statutDAO.find(idStatut);
        /* Création du commentaire
         Un commentaire n'est rien d'autre qu'un statut en réponse à un autre statut
         */
        StatutsEntity newCommentaire = new StatutsEntity(commentaire, new Date());
        newCommentaire.setAuteur(user);
        statutDAO.save(newCommentaire);
        
        NotificationsEntity notifCom = new NotificationsEntity(new Date(),
                TypeNotifications.notifCommentaire.getId());
        notifCom.setNotifieur(user);

        // Création de la notification dans la BD
        notificationDAO.save(notifCom);

        // Ajout de la notification a l'auteur du statut
        personneDAO.ajoutNotif(user, statut.getAuteur(), notifCom);
        
        return statutDAO.ajoutCommentaire(statut, newCommentaire);
    }

    /**
     * Récupération de la liste des notification d'un utilisateur
     *
     * @param idUtilisateur id de l'utilisateur
     * @return un string contenant les différentes notifications pour
     * l'utilisateur
     */
    @Override
    public String getNotifications(int idUtilisateur) {
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idUtilisateur);

        String afficheNotifications = "";
        // Parcours de la liste de notification reçues par l'utilisateur
        for (NotificationsEntity n : user.getNotificationRecues()) {
            afficheNotifications += "<div class=\"col-lg-offset-1 col-lg-10\">";
            afficheNotifications += n.toString();
            afficheNotifications += "</div>";
        }
        // Remise à 0 des notifications non lues
        user.resetNotif();

        return afficheNotifications;
    }
}
