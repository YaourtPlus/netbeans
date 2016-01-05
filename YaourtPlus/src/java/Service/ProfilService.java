/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.PersonnesEntity;

/**
 *
 * @author tbenoist
 */
public interface ProfilService {

    public PersonnesEntity getPersonne(int id);

    public String getFilous(int id);

    public boolean exists(String login);

    public String getNotifications(int idUtilisateur);

    public int ajoutStatut(int idUser, String statut);

}
