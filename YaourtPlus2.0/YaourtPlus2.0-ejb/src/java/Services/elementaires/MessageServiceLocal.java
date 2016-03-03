/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.elementaires;

import Entities.MessagesEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author tbenoist
 */
@Local
public interface MessageServiceLocal {

    public void ajoutMessage(String message, int idUser, int idDestinataire);

    public List<MessagesEntity> getMessagesSinglePersonne(int idUser, int idPersonne);

}
