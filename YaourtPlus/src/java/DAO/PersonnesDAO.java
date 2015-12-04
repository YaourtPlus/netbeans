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
public interface PersonnesDAO {
		public void save(PersonnesEntity p);
		public void update(PersonnesEntity p);
		public void delete(PersonnesEntity p);
		public PersonnesEntity find(int id);
		public List<PersonnesEntity> findAll();
		public List<PersonnesEntity> findByNom(String nom);
		public List<PersonnesEntity> findByPrenom(String prenom);
		public List<PersonnesEntity> findByMail(String mail);
}