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
public interface MessageService {
    
    public void envoyerMessage(int idUser, int idDestinataire, String message);
    
    public String getMessages(int idUser);
    
}
