/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.PersonnesDAO;
import DAO.PersonnesDAOImpl;
import DAO.PersonnesEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olivier
 */
@Service
public class InscriptionServiceImpl implements InscriptionService {

    public InscriptionServiceImpl() {

    }

    @Override
    public void add(String nom, String prenom, String login, String password, String mail, int age) {
        PersonnesEntity pe = new PersonnesEntity(nom, prenom, age, mail, login, password);
        PersonnesDAO pdao = new PersonnesDAOImpl();
        pdao.save(pe);
    }
}
