/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

/**
 *
 * @author tbenoist
 */
public interface ConnexionService {
	// Retourne l'idée de la personne où -1 si elle n'a pas trouvé la personne
	public int connexion(String login, String password);
}
