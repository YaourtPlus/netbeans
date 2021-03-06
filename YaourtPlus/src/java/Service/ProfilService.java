/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.PersonnesEntity;
import java.util.List;

/**
 *
 * @author tbenoist
 */
public interface ProfilService {

    public PersonnesEntity getPersonne(int idUtilisateur);
    
    public List<PersonnesEntity> getFilous(int idUtilisateur);

    public boolean exists(String login);
    
    public String getNotifications(int idUtilisateur);
    
    public String getSelectUserList(int idUtilisateur);
}
