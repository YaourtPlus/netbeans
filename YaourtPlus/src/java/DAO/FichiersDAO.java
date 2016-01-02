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
public interface FichiersDAO {

// Transaction de base =========================================================
    public void save(FichiersEntity f);

    public void update(FichiersEntity f);

    public void delete(FichiersEntity f);

// Transaction custom ==========================================================
// Transaction read-only =======================================================
    public FichiersEntity find(int id);

    public List<FichiersEntity> findAll();

    public List<FichiersEntity> findByStatuts(int statutId);

    public List<FichiersEntity> findByMessages(int messageId);
}

// Transaction de base =========================================================
// Transaction custom ==========================================================
// Transaction read-only =======================================================
