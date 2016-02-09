/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.PersonnesEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Olivier
 */
@Local
public interface PersonnesServiceLocal {
    public List<PersonnesEntity> getPersonnes();
}
