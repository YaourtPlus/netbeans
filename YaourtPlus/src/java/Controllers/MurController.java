package Controllers;

import Service.ConnexionService;
import Service.FichierService;
import Service.FichierServiceImpl;
import Service.MurService;
import Service.ProfilService;
import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
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
    
    @Autowired
    ServletContext servletContext;
    
    @Autowired
    FichierService fichierService;

// Gestion des requêtes GET ====================================================
    // Ajout d'un léger
    @RequestMapping(value = "leger", method = RequestMethod.GET)
    public ModelAndView legerStatut(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        // Récupération de la session
        HttpSession session = request.getSession(false);

        // Accès sans être connecté
        if (session == null || session.getAttribute("idUtilisateur") == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage",
                    "Veuillez vous connecter pour accéder à cette page");
            return mv;
        } else {
            // Récupération de l'id de l'utilisateur courant
            int idUtilisateur = (int) session.getAttribute("idUtilisateur");

            // Récupération de l'id du statut sur lequel l'action est effectuée
            int idStatut = Integer.parseInt(request.getParameter("id"));

            // Gestion de l'ajout de léger
            murService.addLeger(idStatut, idUtilisateur);

            // Affichage du mur
            mv = this.afficheMur(request, response);
            return mv;
        }

    }

    // Ajout d'un lourd
    @RequestMapping(value = "lourd", method = RequestMethod.GET)
    public ModelAndView lourdStatut(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        // Récupération de la session
        HttpSession session = request.getSession(false);

        // Accès sans être connecté
        if (session == null || session.getAttribute("idUtilisateur") == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage",
                    "Veuillez vous connecter pour accéder à cette page");
            return mv;
        }

        // Récupération de l'id de l'utilisateur courant
        int idUtilisateur = (int) session.getAttribute("idUtilisateur");

        // Récupération de l'id du statut sur lequel l'action est effectuée
        int idStatut = Integer.parseInt(request.getParameter("id"));

        // Gestion de l'ajout de lourd
        murService.addLourd(idStatut, idUtilisateur);

        // Affichage du mur
        mv = this.afficheMur(request, response);
        return mv;
    }

    // Suppression d'un léger/lourd
    @RequestMapping(value = "removeAction", method = RequestMethod.GET)
    public ModelAndView removeAction(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        // Récupération de la session
        HttpSession session = request.getSession(false);

        // Accès sans être connecté
        if (session == null || session.getAttribute("idUtilisateur") == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage",
                    "Veuillez vous connecter pour accéder à cette page");
            return mv;
        }

        // Récupération de l'id de l'utilisateur courant
        int idUtilisateur = (int) session.getAttribute("idUtilisateur");

        // Récupération de l'id du statut sur lequel l'action est effectuée
        int idStatut = Integer.parseInt(request.getParameter("id"));

        // Gestion de la suppression de l'action
        murService.removeAction(idStatut, idUtilisateur);

        // Affichage du mur
        mv = this.afficheMur(request, response);
        return mv;
    }

// Gestion des méthodes POST ===================================================
    // Ajout d'un statut
    @RequestMapping(value = "ajoutStatut", method = RequestMethod.POST)
    public ModelAndView ajoutStatut(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        // Récupération de la session
        HttpSession session = request.getSession(false);

        // Accès sans être connecté
        if (session == null || session.getAttribute("idUtilisateur") == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage",
                    "Veuillez vous connecter pour accéder à cette page");
            return mv;
        }
        Part p = request.getPart("file");
        // Récupération de l'id de l'utilisateur courant
        int idUtilisateur = (int) session.getAttribute("idUtilisateur");

        // Récupération du texte du statut posté
        String statut = request.getParameter("statut");

        // Ajout du statut
        int idStatut = profilService.ajoutStatut(idUtilisateur, statut);

        fichierService.ajoutFichier(p, idStatut, servletContext);
        // Affichage du mur
        mv = this.afficheMur(request, response);
        return mv;
    }

// Gestion des méthodes mixtes==================================================
    // Affichage du mur
    @RequestMapping(value = "mur", method = {RequestMethod.POST,
        RequestMethod.GET})
    public ModelAndView afficheMur(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        // Récupération de la session
        HttpSession session = request.getSession(false);

        // Accès sans être connecté
        if (session == null || session.getAttribute("idUtilisateur") == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage",
                    "Veuillez vous connecter pour accéder à cette page");
            return mv;
        } else {
            // Récupération de l'id de l'utilisateur
            int idUtilisateur = (int) session.getAttribute("idUtilisateur");

            // Création du modelAndView mur pour l'affichage
            mv = new ModelAndView("mur");

            // Création de l'affichage
            // Récupération du nom de l'utilisauter
            String nomPersonne = profilService.getPersonne(idUtilisateur).getNom();

            // Récupération des Filous de la personne
            String listFilous = profilService.getFilous(idUtilisateur);

            // Récupération des statuts des Filous
            String statut = murService.getStatuts(idUtilisateur);

            // Affichage des différentes données récupérées précédemment
            mv.addObject("listeAmi", listFilous);
            mv.addObject("nomPersonne", nomPersonne);
            mv.addObject("listStatuts", statut);

            return mv;
        }

    }

// Gestion des commentaires ====================================================
    @RequestMapping(value = "ajoutCommentaire", method = RequestMethod.POST)
    public ModelAndView ajoutCommentaire(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        // Récupération de la session
        HttpSession session = request.getSession(false);

        // Accès sans être connecté
        if (session == null || session.getAttribute("idUtilisateur") == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage",
                    "Veuillez vous connecter pour accéder à cette page");
            return mv;
        }

        // Récupération de l'id de l'utilisateur courant
        int idUtilisateur = (int) session.getAttribute("idUtilisateur");
        // Récupération de l'id du statut sur lequel on commente
        int idStatut = Integer.parseInt(request.getParameter("id"));

        // Récupération du texte du statut posté
        String commentaire = request.getParameter("commentaire");
        // Ajout du statut
        profilService.ajoutCommentaire(idUtilisateur, idStatut, commentaire);

        // Affichage du mur
        mv = this.afficheMur(request, response);
        return mv;
    }


}
