/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAO.PersonnesDAO;
import Entities.PersonnesEntity;
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
    PersonnesDAO personneDAO;

    @EJB
    PersonnesServiceLocal personneService;

    @EJB
    FilousServiceLocal filouService;

    @Override
    public List<PersonnesEntity> getFilous(int idUtilisateur) {
        PersonnesEntity user = personneService.getPersonne(idUtilisateur);
        
        List<PersonnesEntity> list = new ArrayList();
        list.addAll(user.getListFilous());
        return list;
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

        return personneDAO.ajoutFilous(utilisateur, filous);
    }

    @Override
    public void suppressionFilous(int idFilous, int idUtilisateur) {
        PersonnesEntity utilisateur = personneService.getPersonne(idUtilisateur);
        PersonnesEntity filous = personneService.getPersonne(idFilous);

        personneDAO.suppressionFilous(utilisateur, filous);
    }

}
