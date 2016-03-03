/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entities.IMCEntity;
import Entities.PersonnesEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author tbenoist
 */
@Local
public interface IMCDAO {

// Transaction de base =========================================================
    public void save(IMCEntity i);

    public void update(IMCEntity i);

    public void delete(IMCEntity i);

// Transaction custom ==========================================================
    
    public void addIMC(PersonnesEntity auteur, PersonnesEntity allegeur);
    public void removeIMC(PersonnesEntity auteur, PersonnesEntity allourdeur);
// Transaction read-only =======================================================
    public IMCEntity find(int id);

    public List<IMCEntity> findAll();

}
