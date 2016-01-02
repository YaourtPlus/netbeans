/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.NotificationsEntity;
import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import DAO.StatutsDAO;
import DAO.StatutsEntity;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author tbenoist
 */
@Service
public class ProfilServiceImpl implements ProfilService {

    @Resource
    PersonnesDAO personnesDAO;

    @Resource
    StatutsDAO statutDAO;

    @Override
    public PersonnesEntity getPersonne(int id) {
        return personnesDAO.find(id);
    }

    /**
     * Liste les différents filous de l'utilisateur
     *
     * @param id id de l'utilisateur
     * @return un string contenant les différents filous
     */
    @Override
    public String getFilous(int id) {
        // Récupération de l'utilisateur
        PersonnesEntity user = getPersonne(id);

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
        return personnesDAO.findByLogin(login) != null;
    }

    /**
     * Ajout d'un statut par l'utilisateur
     *
     * @param idUser id de l'utilisateur
     * @param statut id du statut à ajouter
     * @return true si l'ajout est effectué correctement, false sinon (statut
     * vide)
     */
    @Override
    public boolean ajoutStatut(int idUser, String statut) {
        if (statut.length() == 0) { // Gestion d'un statut vide
            return false;
        }
        // Récupération de l'utilisateur
        PersonnesEntity user = getPersonne(idUser);

        // Création du statut
        StatutsEntity newStatut = new StatutsEntity(statut, new Date());
        // Ajout dans la BD
        statutDAO.save(newStatut);

        // Ajout du statut
        return personnesDAO.ajoutStatut(user, newStatut);
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
        PersonnesEntity user = getPersonne(idUtilisateur);

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
