package Controllers;

import Enumerations.TypeActions;
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
    public ModelAndView afficheMur(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        HttpSession session = request.getSession(false);
        // Si la session n'existe pas, c'est qu'on ne vient pas du controller ConnexionController et qu'on a essayé d'accéder à la page sans être connecté
        if (session == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage", "Veuillez vous connecter pour accéder à cette page");
            return mv;
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        // Test d'existence de session
        int idUtilisateur = (int) session.getAttribute("idUtilisateur");
        if (idUtilisateur != -1) {
            mv = new ModelAndView("mur");
            String nomPersonne = profilService.getPersonne(idUtilisateur).getNom();
            String listFilous = profilService.getFilous(idUtilisateur);
            String statut = murService.getStatuts(idUtilisateur);
            // int nbNotif = profilService.getPersonne(idUtilisateur).getNotifNonLues();
            // mv.addObject("nbNotif", nbNotif == 0 ? "" : nbNotif);
            mv.addObject("listeAmi", listFilous);
            mv.addObject("nomPersonne", nomPersonne);
            mv.addObject("listStatuts", statut);
        } else {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage", "Login ou mot de passe incorrect");
        }

        return mv;
    }

    @RequestMapping(value = "ajoutStatut", method = RequestMethod.POST)
    public ModelAndView ajoutStatut(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        HttpSession session = request.getSession(false);
        // Si la session n'existe pas, c'est qu'on ne vient pas du controller ConnexionController et qu'on a essayé d'accéder à la page sans être connecté
        if (session == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage", "Veuillez vous connecter pour accéder à cette page");
            return mv;
        }

        int idUtilisateur = (int) session.getAttribute("idUtilisateur");
        String statut = request.getParameter("statut");

        profilService.ajoutStatut(idUtilisateur, statut);

        mv = this.afficheMur(request, response);
        return mv;
    }

    /*
     * /!\ Ajouter gestion d'un seul léget / un seul lourd /!\
     */
    @RequestMapping(value = "leger", method = RequestMethod.GET)
    public ModelAndView legerStatut(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        HttpSession session = request.getSession(false);
        // Si la session n'existe pas, c'est qu'on ne vient pas du controller ConnexionController et qu'on a essayé d'accéder à la page sans être connecté
        if (session == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage", "Veuillez vous connecter pour accéder à cette page");
            return mv;
        }

        int idUtilisateur = (int) session.getAttribute("idUtilisateur");
        int idStatut = Integer.parseInt(request.getParameter("id"));

        murService.addLeger(idStatut, idUtilisateur);

        mv = this.afficheMur(request, response);
        return mv;
    }

    @RequestMapping(value = "lourd", method = RequestMethod.GET)
    public ModelAndView lourdStatut(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        HttpSession session = request.getSession(false);
        // Si la session n'existe pas, c'est qu'on ne vient pas du controller ConnexionController et qu'on a essayé d'accéder à la page sans être connecté
        if (session == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage", "Veuillez vous connecter pour accéder à cette page");
            return mv;
        }

        int idUtilisateur = (int) session.getAttribute("idUtilisateur");
        int idStatut = Integer.parseInt(request.getParameter("id"));

        murService.addLourd(idStatut, idUtilisateur);

        mv = this.afficheMur(request, response);
        return mv;
    }

    @RequestMapping(value = "removeAction", method = RequestMethod.GET)
    public ModelAndView removeAction(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        HttpSession session = request.getSession(false);
        // Si la session n'existe pas, c'est qu'on ne vient pas du controller ConnexionController et qu'on a essayé d'accéder à la page sans être connecté
        if (session == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage", "Veuillez vous connecter pour accéder à cette page");
            return mv;
        }

        int idUtilisateur = (int) session.getAttribute("idUtilisateur");
        int idStatut = Integer.parseInt(request.getParameter("id"));

        murService.removeAction(idStatut, idUtilisateur);
        
        mv = this.afficheMur(request, response);
        return mv;
    }
}
