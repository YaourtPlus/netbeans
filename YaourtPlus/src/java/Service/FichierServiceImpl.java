/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.FichiersDAO;
import DAO.FichiersEntity;
import DAO.StatutsDAO;
import DAO.StatutsEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olivier
 */
@Service
public class FichierServiceImpl implements FichierService {

    @Autowired
    ServletContext servletContext;

    @Resource
    StatutsDAO statutsDAO;

    @Resource
    FichiersDAO fichierDAO;

    @Override
    public boolean ajoutFichier(Part p, int statutsId) {
        
        StatutsEntity se = statutsDAO.find(statutsId);
        String nouveauNom = hashNomFichier(se.getAuteur().getLogin() + p.getSubmittedFileName());
        String[] split = p.getSubmittedFileName().split("\\.");
        String ext = split[split.length - 1];

        for (FichiersEntity fe : se.getListeFichiers()) {
            if (fe.getNom().equals(nouveauNom)) {
                return false;
            }
        }
        FichiersEntity fe = new FichiersEntity(ext, "empty", nouveauNom);
        fichierDAO.save(fe);

        statutsDAO.addFichier(se, fe);

        return false;
    }

    private static String hashNomFichier(String nomFichier) {
        byte[] uniqueKey = nomFichier.getBytes();
        byte[] hash = null;

        try {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        } catch (NoSuchAlgorithmException e) {
            throw new Error("No MD5 support in this VM.");
        }

        StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1) {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            } else {
                hashString.append(hex.substring(hex.length() - 2));
            }
        }
        return hashString.toString();
    }

    private boolean ajoutFichierInterne(Part p, String nom, ServletContext servletContext) throws IOException {
        String s = "";
        if (!"".equals(p.getSubmittedFileName())) {
            s += "bonjour";
            String separator = System.getProperty("file.separator");
            File f = new File(servletContext.getRealPath("/WEB-INF/files") + separator);
            File fdef = new File(f.getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath() + separator + "web" + separator + "WEB-INF" + separator + "files" + separator + nom);

            fdef.delete();
            fdef.createNewFile();
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
                return false;
            }
            return true;
        }
        return false;
    }

}
