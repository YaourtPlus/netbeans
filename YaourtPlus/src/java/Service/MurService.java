/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

/**
 *
 * @author Olivier
 */
public interface MurService {

    public boolean ajoutStatut(int idUtilisateur, String statut);
    
    public boolean ajoutCommentaire(int idUtilisateur, int idStatut, String commentaire);

    public void addLeger(int idStatut, int idUser);

    public void addLourd(int idStatut, int idUser);

    public void removeAction(int idStatut, int idUser);
}
