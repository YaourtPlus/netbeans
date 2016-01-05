package Controllers;

import Service.ConnexionService;
import Service.FichierService;
import Service.FichierServiceImpl;
import Service.MurService;
import Service.NotificationsService;
import Service.ProfilService;
import Service.StatutsService;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    StatutsService statutsService;
    
// Gestion des requêtes GET ====================================================
    // Ajout d'un léger
    @RequestMapping(value = "{path}/leger", method = RequestMethod.GET)
    public ModelAndView legerStatut(HttpServletRequest request,
            HttpServletResponse response, @PathVariable String path) throws Exception {

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

            switch (path) {
                case "statut":
                    mv = this.afficheStatuts(request, response);
                    break;
                case "vueNotif":
                    mv = new ModelAndView("redirect:/vueNotif.htm?idObject=" + idStatut);
                    break;
                case "mur":
                default:
                    mv = this.afficheMur(request, response);
                    break;
            }
            return mv;
        }

    }

    // Ajout d'un lourd
    @RequestMapping(value = "{path}/lourd", method = RequestMethod.GET)
    public ModelAndView lourdStatut(HttpServletRequest request,
            HttpServletResponse response, @PathVariable String path) throws Exception {

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

        switch (path) {
            case "statut":
                mv = this.afficheStatuts(request, response);
                break;
            case "vueNotif":
                mv = new ModelAndView("redirect:/vueNotif.htm?idObject=" + idStatut);
                break;
            case "mur":
            default:
                mv = this.afficheMur(request, response);
                break;
        }

        return mv;
    }

    // Suppression d'un léger/lourd
    @RequestMapping(value = "{path}/removeAction", method = RequestMethod.GET)
    public ModelAndView removeAction(HttpServletRequest request,
            HttpServletResponse response, @PathVariable String path) throws Exception {

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
        switch (path) {
            case "statut":
                mv = this.afficheStatuts(request, response);
                break;
            case "vueNotif":
                mv = new ModelAndView("redirect:/vueNotif.htm?idObject=" + idStatut);
                break;
            case "mur":
            default:
                mv = this.afficheMur(request, response);
                break;
        }
        /*if (path.equals("statut")) {
         mv = this.afficheStatuts(request, response);
         } else {
         // Affichage du mur
         mv = this.afficheMur(request, response);
         } */
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

        int idStatut = murService.ajoutStatut(idUtilisateur, statut);
        
        if(p.getSize() != 0){
            fichierService.ajoutFichier(p, idStatut);
        }
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
            String statut = statutsService.getStatuts(idUtilisateur);

            // Affichage des différentes données récupérées précédemment
            mv.addObject("listeAmi", listFilous);
            mv.addObject("nomPersonne", nomPersonne);
            mv.addObject("listStatuts", statut);
            mv.addObject("user", Integer.toString(idUtilisateur));

            return mv;
        }

    }

    // Affichage des statuts d'un utilisateur
    @RequestMapping(value = "statuts", method = RequestMethod.GET)
    public ModelAndView afficheStatuts(HttpServletRequest request,
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
            mv = new ModelAndView("statuts");

            // Création de l'affichage
            // Récupération du nom de l'utilisauter
            String nomPersonne = profilService.getPersonne(idUtilisateur).getNom();

            String statuts = statutsService.getUtilisateurStatuts(idUtilisateur);

            // Affichage des différentes données récupérées précédemment
            mv.addObject("nomPersonne", nomPersonne);
            mv.addObject("listStatuts", statuts);

            return mv;
        }

    }

// Gestion des commentaires ====================================================
    @RequestMapping(value = "{path}/ajoutCommentaire", method = RequestMethod.POST)
    public ModelAndView ajoutCommentaire(HttpServletRequest request,
            HttpServletResponse response, @PathVariable String path) throws Exception {

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
        int idStatut = Integer.parseInt(request.getParameter("idStatut"));

        // Récupération du texte du statut posté
        String commentaire = request.getParameter("commentaire");
        // Ajout du statut
        murService.ajoutCommentaire(idUtilisateur, idStatut, commentaire);
        switch (path) {
            case "statut":
                mv = this.afficheStatuts(request, response);
                break;
            case "vueNotif":
                mv = new ModelAndView("redirect:/vueNotif.htm?idObject=" + idStatut);
                break;
            case "mur":
            default:
                mv = this.afficheMur(request, response);
                break;
        }
        return mv;
    }

}
