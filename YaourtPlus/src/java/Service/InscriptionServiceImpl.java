/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.IMCEntity;
import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olivier
 */
@Service
public class InscriptionServiceImpl implements InscriptionService {

    @Resource
    PersonnesDAO pdao;

    @Override
    public void add(String nom, String prenom, String login, String password, 
            String mail, Integer age) {
        // Création de la date d'inscription
        Date dateInscription = Calendar.getInstance().getTime();
        
        // Création de la personne à ajouter
        PersonnesEntity newPers = new PersonnesEntity(nom, prenom, age, mail, login, password);
        newPers.setDateInscription(dateInscription);
        
        // Création d'un IMC
        newPers.setImc(new IMCEntity());
        
        // Sauvegarde dans la BD
        pdao.save(newPers);
    }
}
