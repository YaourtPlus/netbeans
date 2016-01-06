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

// Transaction de base =========================================================
    public void save(PersonnesEntity p);

    public void update(PersonnesEntity p);

    public void delete(PersonnesEntity p);
// Transaction custom ==========================================================

    public boolean ajoutFilous(PersonnesEntity p1, PersonnesEntity p2);

    public boolean suppressionFilous(PersonnesEntity p1, PersonnesEntity p2);

    public boolean ajoutStatutEmis(PersonnesEntity p, StatutsEntity s);
    
    public boolean ajoutStatutRecu(PersonnesEntity p, StatutsEntity s);

    public boolean ajoutNotif(PersonnesEntity notifieur, PersonnesEntity destinataire, NotificationsEntity notif);
// Transaction read-only =======================================================

    public PersonnesEntity find(int id);

    public PersonnesEntity find(String login, String password);

    public PersonnesEntity findByLogin(String login);
	
	// A utiliser et passer en fetch.lazy partout
    public PersonnesEntity findFilous(int id);

    public List<PersonnesEntity> findAll();

    public List<PersonnesEntity> findByNom(String nom);

    public List<PersonnesEntity> findByPrenom(String prenom);

    public List<PersonnesEntity> findByMail(String mail);

}
