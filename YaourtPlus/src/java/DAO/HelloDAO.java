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
public interface HelloDAO {

		public void save(HelloEntity h);
		public void update(HelloEntity h);
		public void delete(HelloEntity h);
		public HelloEntity find(long id);
		public List<HelloEntity> findAll();
		public List<HelloEntity> findByName(String nom);
}
