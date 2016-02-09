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
public interface FilousService {

    public List<PersonnesEntity> getFilous(int idUtilisateur);

    public boolean ajoutFilous(int idUtilisateur, int nouveauFilous);

    public boolean suppressionFilous(int idUtilisateur, int filou);

}
