/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.PersonnesEntity;
import DAO.StatutsEntity;

/**
 *
 * @author tbenoist
 */
public interface StatutsService {

    public String getStatuts(int idUtilisateur);

    public String getUtilisateurStatuts(int idUtilisateur);

    public String statutToString(StatutsEntity s, PersonnesEntity user, String path);
}
