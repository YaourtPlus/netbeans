/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import javax.servlet.http.Part;

/**
 *
 * @author Olivier
 */
public interface FichierService {
    public boolean ajoutFichier(Part p, int statutsId);
}
