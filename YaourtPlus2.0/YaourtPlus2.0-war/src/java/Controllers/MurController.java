package Controllers;

import Entities.CommentairesEntity;
import Entities.PersonnesEntity;
import Entities.PersonnesStatutsEntity;
import Entities.StatutsEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Olivier
 */
@ManagedBean
@SessionScoped
public class MurController {

    private int idPersonne;

    public MurController() {
        idPersonne = -1;
    }

    public int getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }

    public List<StatutsEntity> getStatuts() {
        List<StatutsEntity> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            String iS = i + "";
            StatutsEntity s = new StatutsEntity("Statut" + i, new Date());
            s.addCommentaire(new CommentairesEntity("commentaire" + i, new Date()));
            s.addLeger();
            s.addLeger();
            s.addLeger();
            s.addLourd();
            PersonnesEntity p = new PersonnesEntity(iS, iS, i, iS, iS, iS);
            PersonnesEntity p1 = new PersonnesEntity(iS, iS, i, iS, iS, iS);
            p.setId(i);
            p1.setId(0);
            s.setId(i);
            s.addPersonnesStatuts(new PersonnesStatutsEntity(p, s, 0, false, true));
            s.addPersonnesStatuts(new PersonnesStatutsEntity(p1, s, 1, false, true));
            s.setAuteur(p);
            list.add(s);
        }
        return list;
    }

    public String goToMur(int idUtilisateur) {
        return "/mur?faces-redirect=true&idUtilisateur=" + idUtilisateur;
    }
}
