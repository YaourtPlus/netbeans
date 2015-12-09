package Controllers;

import Service.ConnexionService;
import Service.MurService;
import Service.ProfilService;
import java.util.Date;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Olivier
 */
@Controller
public class MurController {

    @Autowired
    MurService murService;
	
	@Autowired
	ProfilService profilService;

    @Autowired
    ConnexionService connexionService;
    

    @RequestMapping(value = "mur", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		
        ModelAndView mv;
		
		
		HttpSession session = request.getSession(false); 
		// Si la session n'existe pas, c'est qu'on ne vient pas du controller ConnexionController et qu'on a essayé d'accéder à la page sans être connecté
		if(session == null){
			mv = new ModelAndView("connexion");
			mv.addObject("inscriptionMessage", "Veuillez vous connecter pour accéder à cette page");
			return mv;
		}
		
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		// Test d'existence de session
		int idPersonne = (int) session.getAttribute("idUtilisateur");
		if(idPersonne != -1){
			mv  = new ModelAndView("mur");
			String nomPersonne = profilService.getPersonne(idPersonne).getNom();
			String mur = "";
			mv.addObject("nomPersonne", nomPersonne);
			mv.addObject("murMessage", mur);
		}
		else{	
			mv = new ModelAndView("connexion");
			mv.addObject("inscriptionMessage", "Login ou mot de passe incorrect");
		}
		
        return mv;
    }
}
