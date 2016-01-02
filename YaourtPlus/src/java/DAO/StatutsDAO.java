/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;

/**
 *
 * @author tbenoist
 */
public interface StatutsDAO {
		public void save(StatutsEntity s);
		public void update(StatutsEntity s);
		public void delete(StatutsEntity s);
                
                public void addLeger(StatutsEntity s, PersonnesEntity p);
                public void addLourd(StatutsEntity s, PersonnesEntity p);
                public void removeLeger(StatutsEntity s, PersonnesEntity p);
                public void removeLourd(StatutsEntity s, PersonnesEntity p);
                        
                public StatutsEntity find(int id);
		public List<StatutsEntity> findAll();
		public List<StatutsEntity> findByAuteur(int auteurId);
}
