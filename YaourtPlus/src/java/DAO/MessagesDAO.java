/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.Date;
import java.util.List;

/**
 *
 * @author tbenoist
 */
public interface MessagesDAO {

// Transaction de base =========================================================
    public int save(MessagesEntity m);

    public void update(MessagesEntity m);

    public void delete(MessagesEntity m);

// Transaction custom ==========================================================
// Transaction read-only =======================================================
    public MessagesEntity find(int id);

    public List<MessagesEntity> findAll();

    public List<MessagesEntity> findByAuteur(int auteurId);

    public List<MessagesEntity> findByDate(Date date);

    public List<MessagesEntity> findByPersonne(int auteurId, int destinataireId);

    public List<MessagesEntity> findByDestinataire(int destinataireId);
}
