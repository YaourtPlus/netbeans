/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.composites;

import Entities.PersonnesEntity;
import Services.elementaires.NotificationServiceLocal;
import Services.elementaires.PersonnesServiceLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Olivier
 */
@Stateless
public class FilousService implements FilousServiceLocal {

    @EJB
    PersonnesServiceLocal personneService;

    @EJB
    NotificationServiceLocal notificationService;
    
    
    /**
     * Récupération des filous d'une personne
     * 
     * @param idUtilisateur id de l'utilisateur dont on veut récupérer les filous
     * @return la liste des filous de la personne
     */
    @Override
    public List<PersonnesEntity> getFilous(int idUtilisateur) {
        PersonnesEntity user = personneService.getPersonne(idUtilisateur);
        return user.getListFilous();
    }

    /**
     * Récupération de l'id de la première personne ajoutée par l'utilisateur
     * 
     * @param idUtilisateur l'id de l'utilisateur dont on veut récupérer les filous
     * @return le premier filous de l'utilisateur
     */
    @Override
    public int getFirstFilousId(int idUtilisateur){
        PersonnesEntity user = personneService.getPersonne(idUtilisateur);
        
        PersonnesEntity firstFilou = user.getListFilous().get(0);
        
        return firstFilou.getId();
    }
    
    
    /**
     * Récupération des filous que l'utilisateur peut ajouter.
     * 
     * @param idUtilisateur l'id de l'utilisateur pour récupérer les filous
     * @return la liste des filous que l'utilisateur peut ajouter
     */
    @Override
    public List<PersonnesEntity> getFilousPossibles(int idUtilisateur) {

        PersonnesEntity user = personneService.getPersonne(idUtilisateur);

        // Récupération de toutes les personnes du site
        List<PersonnesEntity> gens = personneService.getPersonnes();

        List<PersonnesEntity> filous = new ArrayList();
        
        // On remplit la liste à partir de tous les utilisateur de Yaout+
        filous.addAll(gens);
        
        // On enlève l'utilisateur de cette liste
        filous.remove(user);
        // On enlève les personnes que l'utilisateur à déjà en ami
        filous.removeAll(user.getListFilous());

        return filous;
    }

    
    /**
     * Ajout d'un filou à l'utilisateur
     * 
     * @param idFilous id du filou à ajouter
     * @param idUtilisateur id de l'utilisateur
     * 
     * @return true si l'ajout s'est correctement effectué, false sinon
     */
    @Override
    public boolean ajoutFilous(int idFilous, int idUtilisateur) {
        // Récupération des entités
        PersonnesEntity utilisateur = personneService.getPersonne(idUtilisateur);
        PersonnesEntity filous = personneService.getPersonne(idFilous);

        // Création d'une notification à l'intention du filou
        notificationService.createNotificationFilou(utilisateur, filous);
        
        // Ajout du filou
        return personneService.ajoutFilous(utilisateur, filous);
    }

    
    /**
     * Suppression d'une personne des filous de l'utilisateur
     * @param idFilous id du filou à supprimer
     * @param idUtilisateur id de l'utilisateur
     *
     * @return true si la suppression s'est correctement effectuée, false sinon
     */
    @Override
    public boolean suppressionFilous(int idFilous, int idUtilisateur) {
        PersonnesEntity utilisateur = personneService.getPersonne(idUtilisateur);
        PersonnesEntity filous = personneService.getPersonne(idFilous);

        return personneService.suppressionFilous(utilisateur, filous);
    }

}
