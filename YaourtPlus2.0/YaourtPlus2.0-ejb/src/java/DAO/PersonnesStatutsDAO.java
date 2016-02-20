/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entities.PersonnesEntity;
import Entities.PersonnesStatutsEntity;
import Entities.StatutsEntity;
import Enumerations.TypeActions;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Thomas
 */
@Local
public interface PersonnesStatutsDAO {

// Transaction de base =========================================================
    public void save(PersonnesStatutsEntity ps);

    public void update(PersonnesStatutsEntity ps);

    public void delete(PersonnesStatutsEntity ps);
// Transaction custom ==========================================================

    public void addPost(int idStatut, PersonnesEntity p);

    public TypeActions removeAction(PersonnesEntity p, StatutsEntity s);
// Transaction read-only =======================================================

    public PersonnesStatutsEntity find(PersonnesEntity p, StatutsEntity s);

    public List<PersonnesStatutsEntity> findAll();

    public boolean exist(PersonnesEntity p, StatutsEntity s);
}
