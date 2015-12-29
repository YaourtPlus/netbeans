/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
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
    PersonnesDAO pdao;

    @Override
    public int connexion(String login, String password) {
        PersonnesEntity p = pdao.find(login, password);
        if (p == null) {
            return -1;
        } else {
			// On a récupéré une personne candidate à la connexion.
            // On l'autorise et on met à jour sa date de Connexion
            Date dConnexion = new Date();
            p.setDateConnexion(dConnexion);
            pdao.update(p);
            return p.getId();
        }
    }
}
