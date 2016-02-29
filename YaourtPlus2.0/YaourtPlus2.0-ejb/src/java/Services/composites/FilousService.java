/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.composites;

import DAO.PersonnesDAO;
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
    
    @Override
    public List<PersonnesEntity> getFilous(int idUtilisateur) {
        PersonnesEntity user = personneService.getPersonne(idUtilisateur);
        return user.getListFilous();
    }

    @Override
    public int getFirstFilousId(int idUtilisateur){
        PersonnesEntity user = personneService.getPersonne(idUtilisateur);
        
        PersonnesEntity firstFilou = user.getListFilous().get(0);
        
        return firstFilou.getId();
    }
    
    @Override
    public List<PersonnesEntity> getFilousPossibles(int idUtilisateur) {

        PersonnesEntity user = personneService.getPersonne(idUtilisateur);

        // Récupération de toutes les personnes du site
        List<PersonnesEntity> gens = personneService.getPersonnes();

        List<PersonnesEntity> filous = new ArrayList();
        filous.addAll(gens);
        // Suppression des filous qu'on ne peut pas ajouter
        filous.remove(user);
        filous.removeAll(user.getListFilous());

        return filous;
    }

    @Override
    public boolean ajoutFilous(int idFilous, int idUtilisateur) {
        PersonnesEntity utilisateur = personneService.getPersonne(idUtilisateur);
        PersonnesEntity filous = personneService.getPersonne(idFilous);

        notificationService.createNotificationFilou(utilisateur, filous);
        
        return personneService.ajoutFilous(utilisateur, filous);
    }

    @Override
    public boolean suppressionFilous(int idFilous, int idUtilisateur) {
        PersonnesEntity utilisateur = personneService.getPersonne(idUtilisateur);
        PersonnesEntity filous = personneService.getPersonne(idFilous);

        return personneService.suppressionFilous(utilisateur, filous);
    }

}
