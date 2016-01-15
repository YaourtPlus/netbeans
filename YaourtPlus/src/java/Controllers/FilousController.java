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

// Gestion des requêtes GET ====================================================
    // Ajout d'un filou
    @RequestMapping(value = "ajout", method = RequestMethod.GET)
    public ModelAndView ajoutFilous(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        // Récupération de la session
        HttpSession session = request.getSession(false);

        // Accès sans être connecté
        if (session == null || session.getAttribute("idUtilisateur") == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage", "Veuillez vous connecter pour accéder à cette page");
            return mv;
        }

        // Récupération de l'id de l'utilisateur courant
        int idUtilisateur = (int) session.getAttribute("idUtilisateur");

        // Récupération de l'id du Filou à ajouter
        int idFilous = Integer.parseInt(request.getParameter("id"));

        // Ajout du filous
        filousService.ajoutFilous(idUtilisateur, idFilous);

        // Récupération de la liste des autres filous
        mv = this.listPersonnes(request, response);
        mv.addObject("ajoutFilous", "Filou ajouté");

        return mv;
    }

    // Suppression d'un filou
    @RequestMapping(value = "suppression", method = RequestMethod.GET)
    public ModelAndView suppresssionFilous(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        // Récupération de la session
        HttpSession session = request.getSession(false);

        // Accès sans être connecté
        if (session == null || session.getAttribute("idUtilisateur") == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage", "Veuillez vous connecter pour accéder à cette page");
            return mv;
        }

        // Récupération de l'id de l'utilisateur courant
        int idUtilisateur = (int) session.getAttribute("idUtilisateur");

        // Récupération de l'id du Filou à supprimer
        int idFilou = Integer.parseInt(request.getParameter("id"));

        // Suppression du Filou
        filousService.suppressionFilous(idUtilisateur, idFilou);

        // Affichage du mur
        return new ModelAndView("redirect:/mur.htm");
    }

// Gestion des méthodes POST ===================================================
// Gestion des méthodes mixtes==================================================
    // Affichage de tout les filous
    @RequestMapping(value = "filous", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView listPersonnes(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        // Récupération de la session
        HttpSession session = request.getSession(false);

        // Accès sans être connecté
        if (session == null || session.getAttribute("idUtilisateur") == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage", "Veuillez vous connecter pour accéder à cette page");

        } else {
            // Création du modelAndView pour afficher les Filous
            mv = new ModelAndView("filous");
            // Récupération de l'id de l'utilisateur courant
            int idUtilisateur = (int) session.getAttribute("idUtilisateur");
            // Affichage de la liste des filous
            
            mv.addObject("listPersonne", filousService.getFilous(idUtilisateur));
            mv.addObject("idPersonne", idUtilisateur);
            mv.addObject("idDestinataire", -1);
        }
        return mv;

    }

}
