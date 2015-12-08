/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Service.MurService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import Service.InscriptionService;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
        String age = request.getParameter("ddn");
        if (age == null || age.length() == 0) {
            age = "0";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        
        
        GregorianCalendar gcBirth = new GregorianCalendar(Integer.parseInt(age.split("/")[2]), Integer.parseInt(age.split("/")[1])-1, Integer.parseInt(age.split("/")[0]));
        GregorianCalendar now = new GregorianCalendar();
        long diff = now.getTimeInMillis() - gcBirth.getTimeInMillis();
        
        int yeardiff = now.get(Calendar.YEAR) - gcBirth.get(Calendar.YEAR);
        
        inscriptionService.add(nom, prenom, login, password, mail, yeardiff);
        result = "Vous vous êtes bien inscrits, veuillez vous connecter";
        mv.addObject("inscriptionMessage", result);
        return mv;
    }

}
