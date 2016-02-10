/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import javax.ejb.Local;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

/**
 *
 * @author Olivier
 */
@Local
public interface FichierServiceLocal {
    
    public String ajoutFichier(Part p, String path, int idStatut);
    
}
