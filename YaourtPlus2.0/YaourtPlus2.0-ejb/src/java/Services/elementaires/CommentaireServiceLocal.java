/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.elementaires;

import javax.ejb.Local;

/**
 *
 * @author Thomas
 */
@Local
public interface CommentaireServiceLocal {

    public int ajoutCommentaire(String commentaire, int idStatut, int idUtilisateur);
}
