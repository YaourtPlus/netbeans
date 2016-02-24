/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.StatutsEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Thomas
 */
@Local
public interface StatutServiceLocal {

    public List<StatutsEntity> getStatutsByAuteur(int idPersonne);

    public List<StatutsEntity> getStatutsByDestinataire(int idPersonne);

    public int ajoutStatut(String statut, int idAuteur, int idDestinataire);

    public int ajoutStatut(String statut, int idAuteur);
    
    public int postStatut(String statut, int idAuteur, int idDestinataire);

    public int ajoutCommentaire(String commentaire, int idStatut, int idUtilisateur);

    public void ajoutLeger(int idStatut, int idUtilisateur);

    public void ajoutLourd(int idStatut, int idUtilisateur);

    public void removeAction(int idStatut, int idUtilisateur);
}
