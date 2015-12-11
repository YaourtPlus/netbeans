/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.IMCEntity;
import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
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
    
    public InscriptionServiceImpl() {

    }

    @Override
    public void add(String nom, String prenom, String login, String password, String mail, Integer age) {
        // Création de la date d'inscription
		Date dateInscription = new Date();
        PersonnesEntity newPers = new PersonnesEntity(nom, prenom, age, mail, login, password);
		newPers.setDateInscription(dateInscription);
		newPers.setImc(new IMCEntity());
		pdao.save(newPers);
    }
}
