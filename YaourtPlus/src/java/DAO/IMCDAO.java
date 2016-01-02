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
public interface IMCDAO {

// Transaction de base =========================================================
    public void save(IMCEntity i);

    public void update(IMCEntity i);

    public void delete(IMCEntity i);

// Transaction custom ==========================================================
// Transaction read-only =======================================================
    public IMCEntity find(int id);

    public List<IMCEntity> findAll();

}
