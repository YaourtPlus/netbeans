/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Service.FilousService;
import Service.ProfilService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author tbenoist
 */
@Controller
public class FilousController {

    // Permet la récupération des gens pour ajout
    @Autowired
    FilousService filousService;

    // Permet de mettre à jour en fonction des gens ajoutés
    @Autowired
    ProfilService profilService;

    @RequestMapping(value = "filous", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView listPersonnes(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        HttpSession session = request.getSession(false);

        if (session == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage", "Veuillez vous connecter pour accéder à cette page");
            return mv;
        }

        mv = new ModelAndView("filous");
        int idUtilisateur = (int) session.getAttribute("idUtilisateur");
        mv.addObject("listFilous", filousService.getFilous(idUtilisateur));

        return mv;
    }
    
    @RequestMapping(value = "ajout", method = RequestMethod.GET)
    public ModelAndView ajoutAmi(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        HttpSession session = request.getSession(false);

        if (session == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage", "Veuillez vous connecter pour accéder à cette page");
            return mv;
        }

        mv = new ModelAndView("filous");
        int idUtilisateur = (int) session.getAttribute("idUtilisateur");
        mv.addObject("listFilous", request.getParameter("id"));

        return mv;
    }
}
