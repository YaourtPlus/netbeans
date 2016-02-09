/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.PersonnesEntity;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author tbenoist
 */
@ManagedBean
@RequestScoped
public class FilousController {

    
    public FilousController() {
    }
    
    public List<PersonnesEntity> getFilous(){
        List<PersonnesEntity> list = new ArrayList();
        for(int i = 0; i < 10; i++){
            String iS = i + "";
            list.add(new PersonnesEntity(iS, iS, i, iS, iS, iS));
        }
        return list;
    }
    
}
