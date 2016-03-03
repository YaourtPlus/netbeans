/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
import javax.ejb.Local;
import Entities.CommentairesEntity;
import Entities.StatutsEntity;

/**
 *
 * @author tbenoist
 */
@Local
public interface CommentairesDAO {
    

// Transaction de base =========================================================
    public int save(CommentairesEntity c);

    public void update(CommentairesEntity c);

    public void delete(CommentairesEntity c);
// Transaction custom ==========================================================
    
    public boolean ajoutCommentaire(StatutsEntity s, CommentairesEntity c);
// Transaction read-only =======================================================

    public CommentairesEntity find(int id);

    public List<CommentairesEntity> findAll();
}
