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
public interface InscriptionService {
    public void add(String nom, String prenom, String login, String password, String mail, Integer age);
}
