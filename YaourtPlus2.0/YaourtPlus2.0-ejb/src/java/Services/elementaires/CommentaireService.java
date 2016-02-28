/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.elementaires;

import DAO.CommentairesDAO;
import DAO.PersonnesDAO;
import DAO.StatutsDAO;
import Entities.CommentairesEntity;
import Entities.PersonnesEntity;
import Entities.PersonnesStatutsEntity;
import Entities.StatutsEntity;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Thomas
 */
@Stateless
public class CommentaireService implements CommentaireServiceLocal {

    @EJB
    StatutsDAO statutDAO;
    
    @EJB
    PersonnesDAO personneDAO;
    
    @EJB
    CommentairesDAO commentaireDAO;
    
    @EJB
    NotificationServiceLocal notificationService;
    
    @Override
    public int ajoutCommentaire(String commentaire, int idStatut, int idUtilisateur) {
        if (commentaire == null || commentaire.length() == 0) {
            return -1;
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

        int idCommentaire = commentaireDAO.save(newCommentaire);
        // Création d'une notification à l'auteur du statut
        //notificationService.createNotificationCommentaire(user, statut.getDestinataire(), statut.getId());
        // Préparation de la notifications des personnes ayant commenté le statut
        for (PersonnesStatutsEntity ps : statut.getStatutsActeurs()) {
            // Création de la notification aux gens qui ont agit sur le statut sauf la personne qui a posté le statut
            if (ps.getStatut().equals(statut)
                    && (ps.getCommentaire() || ps.getPost())
                    && !(ps.getPersonne().equals(user))) {
                notificationService.createNotificationCommentaire(user, ps.getPersonne(), statut.getId());
            }
        }
        statutDAO.addCommentaire(statut, user);

        commentaireDAO.ajoutCommentaire(statut, newCommentaire);
        return idCommentaire;
    }
}
