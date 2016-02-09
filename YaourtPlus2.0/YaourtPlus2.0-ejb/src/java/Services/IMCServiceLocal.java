/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import javax.ejb.Local;

/**
 *
 * @author Olivier
 */
@Local
public interface IMCServiceLocal {
    public void ajoutLeger(int idStatut, int idUtilisateur);
    public void ajoutLourd(int idStatut, int idUtilisateur);
    public void suppressionLeger(int idStatut, int idUtilisateur);
    public void suppressionLourd(int idStatut, int idUtilisateur);
}
