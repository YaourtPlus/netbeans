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
    
    public String getStatuts(int id);

    public void addLeger(int idStatut);
    
    public void addLourd(int idStatut);
}
