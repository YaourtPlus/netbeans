/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.CommentairesDAO;
import DAO.NotificationsDAO;
import DAO.NotificationsEntity;
import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import DAO.StatutsDAO;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tbenoist
 */
@Service
public class ProfilServiceImpl implements ProfilService {

    @Autowired
    ServletContext servletContext;

    @Resource
    PersonnesDAO personneDAO;

    @Resource
    StatutsDAO statutDAO;

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
                    + " <a href='" + servletContext.getContextPath() + "/suppression.htm?id=" + p.getId()
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
            afficheNotifications += n.toString() + " " + getUrl(n);
            afficheNotifications += "</div>";
        }
        // Remise à 0 des notifications non lues
        user.resetNotif();

        return afficheNotifications;
    }

    public String getUrl(NotificationsEntity n) {
        String url = "";
        if (n.getMessage() != null) {
            url = "<a href='" + servletContext.getContextPath() + "/vueNotif.htm?idObject=" + n.getMessage().getId() + "'> ici </a>";
        } else if (n.getStatut() != null) {
            url = "<a href='" + servletContext.getContextPath() + "/vueNotif.htm?idObject=" + n.getStatut().getId() + "'> ici </a>";
        }
        return url;
    }
}
