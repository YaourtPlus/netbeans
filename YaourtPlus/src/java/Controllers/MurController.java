package Controllers;

import Service.ConnexionService;
import Service.MurService;
import Service.ProfilService;
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
    

    @RequestMapping(value = "mur", method = RequestMethod.POST)
    public ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView mv;
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		// Création de la session
		HttpSession session = request.getSession(true); 
		
		if(session != null){
			
			session.setAttribute("idPersonne", connexionService.connexion(login, password));
			int idPersonne = (int) session.getAttribute("idPersonne");
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
		}
		else{
			mv = new ModelAndView("connexion");
			mv.addObject("inscriptionMessage", "Login ou mot de passe incorrect");
		}
        return mv;
    }
}
