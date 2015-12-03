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
		public StatutsEntity find(int id);
		public List<StatutsEntity> findAll();
		public List<StatutsEntity> findByAuteur(int auteurId);
}
