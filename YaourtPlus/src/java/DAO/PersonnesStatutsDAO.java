/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Enumerations.TypeActions;
import java.util.List;

/**
 *
 * @author Thomas
 */
public interface PersonnesStatutsDAO {
    
		public void save(PersonnesStatutsEntity ps);
		public void update(PersonnesStatutsEntity ps);
		public void delete(PersonnesStatutsEntity ps);
                        
                public TypeActions removeAction(PersonnesEntity p, StatutsEntity s);
                
                public PersonnesStatutsEntity find(PersonnesEntity p, StatutsEntity s);
		public List<PersonnesStatutsEntity> findAll();
}
