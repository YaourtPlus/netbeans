/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services.elementaires;

import DAO.StatutsDAO;
import Entities.FichiersEntity;
import Entities.StatutsEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.Part;

/**
 *
 * @author Olivier
 */
@Stateless
public class FichierService implements FichierServiceLocal {

    @EJB
    StatutsDAO statutsDAO;
    
    @EJB
    HashServiceLocal hashService;
    
    
    /**
     * Ajout d'un fichier à un statut
     * 
     * @param p part du fichier à ajouter
     * @param path chemin du fichier qu'on veut enregistrer
     * @param idStatut id du statut auquel on ajoute un fichier
     * @return Le path du fichier ajouté
     */
    @Override
    public String ajoutFichier(Part p, String path, int idStatut) {
        // Récupération du statut
        StatutsEntity se = statutsDAO.find(idStatut);
        
        // Création du nom du fichier
        String nouveauNom = (se.getAuteur().getLogin()+ p.getSubmittedFileName());
        String[] split = p.getSubmittedFileName().split("\\.");
        String ext = split[split.length - 1];
        nouveauNom = hashService.hash(nouveauNom)+ "." + ext;
        String s = nouveauNom;
        try {
            // Ajout du fichier
            s = ajoutFichierInterne(p, nouveauNom, path);
        } catch (IOException ex) {
            // Exception retournée
            s += ex.toString();
            Logger.getLogger(FichierService.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Création de l'entité fichier (url)
        FichiersEntity fe = new FichiersEntity(ext, p.getSubmittedFileName(), nouveauNom);
        
        // Ajout de l'url au statut
        statutsDAO.addFichier(se, fe);
        return s;
        
    }
    
    
    /**
     * Ajout du fichier sur le serveur
     * 
     * @param p part du fichier à ajouter
     * @param nom nom du fichier à ajouter
     * @param path path du fichier à ajouter
     * @return le nom du fichier
     * @throws IOException 
     */
    private String ajoutFichierInterne(Part p, String nom, String path) throws IOException {
        String s = "";
        if (!"".equals(p.getSubmittedFileName())) {
            s += "bonjour";
            String separator = System.getProperty("file.separator");
            File f = new File(path + separator);
           // File fdef = new File(f.getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath() + separator + "web" + separator + "files" + separator + nom);
            File fdef = new File(path + separator + nom);
            fdef.delete();
            fdef.createNewFile();
            
            //TODO go to a link not destroy after an app clean
            
            try {
                s += "[[[[" + fdef.getAbsolutePath() + "]]]";
                InputStream in = p.getInputStream();
                OutputStream out = new FileOutputStream(fdef);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();

            } catch (Exception e) {
                return "nul on n'y arrive pas";
            }
            return "[||||||"+fdef.getAbsolutePath()+"]";
        }
        return "nom de fichier vide";
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
