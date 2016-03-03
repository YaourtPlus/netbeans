/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.elementaires;

import DAO.CommentairesDAO;
import DAO.PersonnesDAO;
import DAO.PersonnesStatutsDAO;
import DAO.StatutsDAO;
import Entities.PersonnesEntity;
import Entities.StatutsEntity;
import Enumerations.TypeActions;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Thomas
 */
@Stateless
public class StatutService implements StatutServiceLocal {

    @EJB
    StatutsDAO statutDAO;

    @EJB
    PersonnesDAO personneDAO;

    @EJB
    PersonnesStatutsDAO personneStatutDAO;

    @EJB
    CommentairesDAO commentaireDAO;

    @EJB
    PersonnesServiceLocal personneService;

    @EJB
    NotificationServiceLocal notificationService;

    @Override
    public StatutsEntity getStatut(int idStatut){
        return statutDAO.find(idStatut);
    }
    /**
     *
     * @param idPersonne
     * @return
     */
    @Override
    public List<StatutsEntity> getStatutsByAuteur(int idPersonne) {
        return statutDAO.findByAuteur(idPersonne);
    }

    /**
     *
     * @param idPersonne
     * @return
     */
    @Override
    public List<StatutsEntity> getStatutsByDestinataire(int idPersonne) {
        return statutDAO.findByDestinataire(idPersonne);
    }

    @Override
    public int ajoutStatut(String statut, int idAuteur, int idDestinataire) {
        if (statut.length() == 0) { // Gestion d'un statut vide
            return -1;
        }
        // Récupération de l'utilisateur
        PersonnesEntity user = personneDAO.find(idAuteur);
        PersonnesEntity destinataire = personneDAO.find(idDestinataire);

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
        if(user != destinataire){
            notificationService.createNotificationStatut(user, destinataire, idStatut);
        }
        return idStatut;
    }

    @Override
    public int ajoutStatut(String statut, int idAuteur) {
        return ajoutStatut(statut, idAuteur, idAuteur);
    }

    @Override
    public int postStatut(String statut, int idAuteur, int idDestinataire) {
        return ajoutStatut(statut, idAuteur, idDestinataire);
    }

    @Override
    public void ajoutLeger(int idStatut, int idUtilisateur) {
        // Récupération des entités
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        PersonnesEntity auteur = statut.getAuteur();
        statutDAO.addLeger(statut, user);

        // Création de la notification dans la BD
        if(!auteur.equals(user))
            notificationService.createNotificationLeger(user, auteur, statut.getId());
    }

    @Override
    public void ajoutLourd(int idStatut, int idUtilisateur) {
        // Récupération des entités
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUtilisateur);
        PersonnesEntity auteur = statut.getAuteur();
        statutDAO.addLourd(statut, user);

        // Création de la notification dans la BD
        if(!auteur.equals(user))
            notificationService.createNotificationLourd(user, auteur, statut.getId());
    }

    /**
     * Annulation d'une action
     *
     * @param idStatut id du statut sur lequel on veut annuler l'action
     * @param idUtilisateur id de l'utilisateur
     */
    @Override
    public void removeAction(int idStatut, int idUtilisateur) {
        // Récupération des entités
        StatutsEntity statut = statutDAO.find(idStatut);
        PersonnesEntity user = personneDAO.find(idUtilisateur);

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
