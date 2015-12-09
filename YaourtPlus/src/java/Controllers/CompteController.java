/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Service.ConnexionService;
import Service.InscriptionService;
import Service.ProfilService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
 * @author tbenoist
 */
@Controller
public class CompteController {
	
	@Autowired
    ConnexionService connexionService;
	@Autowired
	ProfilService profilService;
	@Autowired
    InscriptionService inscriptionService;

	
    @RequestMapping(value = "connexion", method = RequestMethod.GET)
    public String connexion(){
        return "connexion";
    }

	// Gestion de l'inscrition
    @RequestMapping(value = "inscription", method = RequestMethod.POST)
    public ModelAndView inscription(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String result;
        ModelAndView mv = new ModelAndView("connexion");

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String mail = request.getParameter("mail");
        String age = request.getParameter("ddn");
        if (age == null || age.length() == 0)
        {
            age = "0";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        
        // A faire : Vérification des paramètres.
        GregorianCalendar gcBirth = new GregorianCalendar(Integer.parseInt(age.split("/")[2]), Integer.parseInt(age.split("/")[1])-1, Integer.parseInt(age.split("/")[0]));
        GregorianCalendar now = new GregorianCalendar();
        long diff = now.getTimeInMillis() - gcBirth.getTimeInMillis();
        
        int yeardiff = now.get(Calendar.YEAR) - gcBirth.get(Calendar.YEAR);
        
        inscriptionService.add(nom, prenom, login, password, mail, yeardiff);
        result = "Vous vous êtes bien inscrits, veuillez vous connecter";
        mv.addObject("inscriptionMessage", result);
        return mv;
    }
	
	
	// Gestion de la connexion
	@RequestMapping(value = "connexion", method = RequestMethod.POST)
    public ModelAndView connection(HttpServletRequest request,
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
				mv  = new ModelAndView("redirect:/mur.htm");
			}
			else{	
				mv = new ModelAndView("connexion");
				mv.addObject("inscriptionMessage", "Login ou mot de passe incorrect");
			}
		}
		else{
			mv = new ModelAndView("connexion");
			mv.addObject("inscriptionMessage", "Veuillez vous connecter pour accéder à cette page");
		}
        return mv;
    }
}
