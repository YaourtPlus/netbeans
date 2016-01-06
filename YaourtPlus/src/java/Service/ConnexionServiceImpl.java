/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author tbenoist
 */
@Service
public class ConnexionServiceImpl implements ConnexionService {

    @Resource
    PersonnesDAO personneDAO;

    /**
     * Vérifie si le login et le password existent dans la base de données
     * @param login le login de la personne essayant de se connecter
     * @param password le password de la personne essayant de se connecter
     * @return -1 si le login et le password n'existenet pas, 
     * l'id de la personne sinon
     */
    @Override
    public int connexion(String login, String password) {
        PersonnesEntity p = personneDAO.find(login, password);
        if (p == null) {
            return -1;
        } else {
            // On a récupéré une personne candidate à la connexion.
            // On l'autorise et on met à jour sa date de Connexion
            Date dConnexion = Calendar.getInstance().getTime();
            p.setDateConnexion(dConnexion);
            personneDAO.update(p);
            return p.getId();
        }
    }
}
