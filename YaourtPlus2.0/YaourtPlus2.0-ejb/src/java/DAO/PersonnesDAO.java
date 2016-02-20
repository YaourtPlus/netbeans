/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entities.MessagesEntity;
import Entities.NotificationsEntity;
import Entities.PersonnesEntity;
import Entities.StatutsEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author tbenoist
 */
@Local
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

    public boolean ajoutMessageEnvoi(PersonnesEntity sender, MessagesEntity newMessagesEntity);

    public boolean ajoutMessageRecu(PersonnesEntity dest, MessagesEntity newMessagesEntity);
// Transaction read-only =======================================================

    public PersonnesEntity find(int id);

    public PersonnesEntity find(String login, String password);

    public PersonnesEntity findByLogin(String login);

    public List<PersonnesEntity> findFilous(int id);

    public List<PersonnesEntity> findAll();

    public List<PersonnesEntity> findByNom(String nom);

    public List<PersonnesEntity> findByPrenom(String prenom);

    public List<PersonnesEntity> findByMail(String mail);

}
