/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Service.ConnexionService;
import Service.ProfilService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Olivier
 */
@Controller
public class ConnexionController {
	
    @Autowired
    ConnexionService connexionService;
	
	@Autowired
	ProfilService profilService;

    @RequestMapping(value = "connexion", method = RequestMethod.GET)
    public String connexion(){
        return "connexion";
    }

}
