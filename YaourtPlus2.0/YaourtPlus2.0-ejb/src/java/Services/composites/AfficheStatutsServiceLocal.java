/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.composites;

import Entities.StatutsEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Thomas
 */
@Local
public interface AfficheStatutsServiceLocal {
    public List<StatutsEntity> afficheMurStatuts(int idUtilisateur);
    public List<StatutsEntity> afficheStatutsEmis(int idPersonne);
    public List<StatutsEntity> afficheStatutsRecus(int idPersonne);
    
}
