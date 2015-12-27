/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import DAO.PersonnesDAO;
import DAO.PersonnesEntity;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olivier
 */
@Service
public class MurServiceImpl implements MurService{

    @Resource
    PersonnesDAO personnesDAO;
    
    public MurServiceImpl() {
    
    }
    
    @Override
    public String getStatuts(int id){
        PersonnesEntity user = personnesDAO.find(id);
        
        return "";
    }
    
    
}
