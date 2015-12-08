/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
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
    public void add(String nom, String prenom, String login, String password, String mail, int age) {
        PersonnesEntity p = new PersonnesEntity(nom, prenom, age, mail, login, password);
        pdao.save(p);
        
    }
    
    @Override
    public int getId(String nom, String prenom, String login, String password, String mail, int age)
    {
        PersonnesEntity p = new PersonnesEntity(nom, prenom, age, mail, login, password);
        int id = p.getId();
        pdao.save(p);
        return id;
    }
}
