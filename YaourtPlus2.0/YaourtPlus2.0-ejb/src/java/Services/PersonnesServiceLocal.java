/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.MessagesEntity;
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
    
    public PersonnesEntity getPersonne(int idPersonne);

    public List<PersonnesEntity> findFilous(int idUtilisateur);

    public void ajoutMessageEnvoi(PersonnesEntity sender, MessagesEntity newMessagesEntity);

    public void ajoutMessageRecu(PersonnesEntity dest, MessagesEntity newMessagesEntity);

}
