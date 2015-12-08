/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import Service.InscriptionService;
import java.sql.Date;
import java.util.Calendar;

/**
 *
 * @author Olivier
 */
@Controller
public class InscriptionController {
    
    @Autowired
    InscriptionService inscriptionService;

    

    @RequestMapping(value = "inscription", method = RequestMethod.POST)
    public ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String result;
        ModelAndView mv = new ModelAndView("connexion");
        
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String mail = request.getParameter("mail");
        String age = request.getParameter("age");
		if(age == null || age.length() == 0)
        {
            age = "0";
        }
        inscriptionService.add(nom, prenom, login, password, mail, Integer.parseInt(age));
        result = "Vous vous êtes bien inscrits, veuillez vous connecter";
        mv.addObject("inscriptionMessage", result);
        return mv;
    }
    
}
