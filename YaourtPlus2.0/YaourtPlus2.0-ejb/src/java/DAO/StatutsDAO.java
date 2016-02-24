/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entities.FichiersEntity;
import Entities.PersonnesEntity;
import Entities.StatutsEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author tbenoist
 */
@Local
public interface StatutsDAO {

// Transaction de base =========================================================
    public int save(StatutsEntity s);

    public void update(StatutsEntity s);

    public void delete(StatutsEntity s);
// Transaction custom ==========================================================

    public void addLeger(StatutsEntity s, PersonnesEntity p);

    public void addLourd(StatutsEntity s, PersonnesEntity p);

    public void removeLeger(StatutsEntity s, PersonnesEntity p);

    public void removeLourd(StatutsEntity s, PersonnesEntity p);

    public void addFichier(StatutsEntity se, FichiersEntity fe);

    public void addCommentaire(StatutsEntity s, PersonnesEntity p);
        // Transaction read-only =======================================================

    public StatutsEntity find(int id);

    public List<StatutsEntity> findAll();

    public List<StatutsEntity> findByAuteur(int auteurId);
    
    public List<StatutsEntity> findByDestinataire(int destinataireId);
}
