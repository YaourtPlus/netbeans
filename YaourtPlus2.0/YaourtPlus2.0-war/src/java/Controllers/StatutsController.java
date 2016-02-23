/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.StatutsEntity;
import Services.StatutServiceLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Thomas
 */
@ManagedBean
@ViewScoped
public class StatutsController {

    private Integer filouId;
    
    @EJB
    StatutServiceLocal statutsService;
    /**
     * Creates a new instance of StatutsController
     */
    public StatutsController() {
    }

    public Integer getFilouId() {
        return filouId;
    }

    public void setFilouId(Integer filouId) {
        this.filouId = filouId;
    }
    
    
    public List<StatutsEntity> getStatutEmis(int idPersonne){
        List<StatutsEntity> list = statutsService.getStatutsEmis(1);
        if(list.isEmpty()){
            list = statutsService.getStatuts(1);
        }
        return list;
    }
    
    public List<StatutsEntity> getStatutsRecu(int idPersonne){
        return statutsService.getStatutsRecus(1);
    }
}
