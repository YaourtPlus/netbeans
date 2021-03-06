package Controllers;

import DAO.StatutsEntity;
import Service.ConnexionService;
import Service.FichierService;
import Service.MessageService;
import Service.MurService;
import Service.ProfilService;
import Service.StatutsService;
import java.util.List;
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

    @Autowired
    MessageService messagesService;

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
        }
        // Récupération de l'id de l'utilisateur courant
        int idUtilisateur = (int) session.getAttribute("idUtilisateur");
        int idPersonne = Integer.parseInt(request.getParameter("idPersonne"));

        // Récupération de l'id du statut sur lequel l'action est effectuée
        int idStatut = Integer.parseInt(request.getParameter("id"));

        // Gestion de l'ajout de léger
        murService.addLeger(idStatut, idUtilisateur);

        mv = getRedirect(path, idPersonne, idStatut);
        return mv;
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
        int idPersonne = Integer.parseInt(request.getParameter("idPersonne"));

        // Récupération de l'id du statut sur lequel l'action est effectuée
        int idStatut = Integer.parseInt(request.getParameter("id"));

        // Gestion de l'ajout de lourd
        murService.addLourd(idStatut, idUtilisateur);

        mv = getRedirect(path, idPersonne, idStatut);
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
        int idPersonne = Integer.parseInt(request.getParameter("idPersonne"));

        // Récupération de l'id du statut sur lequel l'action est effectuée
        int idStatut = Integer.parseInt(request.getParameter("id"));

        // Gestion de la suppression de l'action
        murService.removeAction(idStatut, idUtilisateur);

        mv = getRedirect(path, idPersonne, idStatut);
        return mv;
    }

// Gestion des méthodes POST ===================================================
    // Ajout d'un statut
    @RequestMapping(value = "{path}/ajoutStatut", method = RequestMethod.POST)
    public ModelAndView ajoutStatut(HttpServletRequest request,
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
        Part p = request.getPart("file");
        // Récupération de l'id de l'utilisateur courant
        int idUtilisateur = (int) session.getAttribute("idUtilisateur");

        int idPersonne = Integer.parseInt(request.getParameter("idPersonne"));

        // Récupération du texte du statut posté
        String statut = request.getParameter("statut");

        // Ajout du statut
        int idStatut = 0;
        if (idPersonne == idUtilisateur) {
            idStatut = murService.ajoutStatut(idUtilisateur, statut);
        } else {
            idStatut = murService.posterStatut(idUtilisateur, idPersonne, statut);
        }

        if (p.getSize() != 0 && idStatut != 0) {
            fichierService.ajoutFichier(p, idStatut);
        }

        mv = getRedirect(path, idPersonne, idStatut);
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
            // Récupération des statuts des Filous
            List<StatutsEntity> statut = statutsService.getStatuts(idUtilisateur);

            String selectUserList = profilService.getSelectUserList(idUtilisateur);

            // Affichage des différentes données récupérées précédemment
            mv.addObject("listFilous", profilService.getFilous(idUtilisateur));
            mv.addObject("nomPersonne", nomPersonne);
            mv.addObject("listStatuts", statut);
            mv.addObject("selectUserList", selectUserList);
            mv.addObject("user", Integer.toString(idUtilisateur));
            mv.addObject("idPersonne", idUtilisateur);
            mv.addObject("idDestinataire", -1);

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

            int idPersonne = Integer.parseInt(request.getParameter("idPersonne"));

            // Création du modelAndView mur pour l'affichage
            mv = new ModelAndView("statuts");

            // Création de l'affichage
            // Récupération du nom de l'utilisateur
            String nomPersonne = profilService.getPersonne(idPersonne).getNom();

            String statutsEmis = statutsService.getPersonneStatutsEmis(idUtilisateur, idPersonne);
            String statutsRecu = statutsService.getPersonneStatutsRecu(idUtilisateur, idPersonne);

            // Affichage des différentes données récupérées précédemment
            mv.addObject("nomPersonne", nomPersonne);
            mv.addObject("listStatutsEmis", statutsEmis);
            mv.addObject("sizeStatutsEmis", statutsEmis.length());
            mv.addObject("listStatutsRecu", statutsRecu);
            mv.addObject("sizeStatutsRecu", statutsRecu.length());
            mv.addObject("idPersonne", idUtilisateur);;
            mv.addObject("idProprietaire", idPersonne);
            mv.addObject("idDestinataire", -1);

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
        int idPersonne = Integer.parseInt(request.getParameter("idPersonne"));
        // Récupération de l'id du statut sur lequel on commente
        int idStatut = Integer.parseInt(request.getParameter("idStatut"));

        // Récupération du texte du statut posté
        String commentaire = request.getParameter("commentaire");
        // Ajout du statut
        murService.ajoutCommentaire(idUtilisateur, idStatut, commentaire);
        mv = getRedirect(path, idPersonne, idStatut);
        return mv;
    }

    //Gestion des messages
    @RequestMapping(value = "message", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView afficheMessage(HttpServletRequest request,
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

        // Récupération de l'id du destinataire
        int idDest;
        switch (request.getMethod()) {
            case "POST":
                idDest = Integer.parseInt(request.getParameter("idDestinataire"));
                break;
            case "GET":
            default:
                idDest = -1;
                break;
        }
        /*       if(request.getParameter("idDestinataire").length() != 0){
         int idDest = Integer.parseInt(request.getParameter("idDestinataire"));*/

        //String listFilous = profilService.getFilous();
        mv = new ModelAndView("messages");
        mv.addObject("idPersonne", idUtilisateur);
        mv.addObject("listFilous", profilService.getSelectUserList(idUtilisateur));
        if (idDest != -1) {
            mv.addObject("filou", profilService.getFilous(idUtilisateur));
            mv.addObject("listMessages", messagesService.getMessagesSinglePersonne(idUtilisateur, idDest));
        }
        mv.addObject("idDestinataire", idDest);
        // Affichage de la page
        return mv;
    }

    //Gestion des messages
    @RequestMapping(value = "{path}/ajoutMessage", method = RequestMethod.POST)
    public ModelAndView ajoutMessage(HttpServletRequest request,
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

        int idDest = Integer.parseInt(request.getParameter("idDestinataire"));

        // Récupération du texte du statut posté
        String message = request.getParameter("message");

        // Ajout du statut
        messagesService.envoyerMessage(idUtilisateur, idDest, message);

        // Affichage du mur
        mv = getRedirect(path, idDest, -1);
        return mv;
    }

    // Gestion de la redirection des pages
    private ModelAndView getRedirect(String path, int idPersonne, int idStatut) {
        switch (path) {
            case "statut":
                return new ModelAndView("redirect:/statuts.htm?idPersonne=" + idPersonne);
            case "vueNotif":
                return new ModelAndView("redirect:/vueNotif.htm?idObject=" + idStatut);
            case "messages":
                return new ModelAndView("redirect:/message.htm?idDestinataire=" + idPersonne);
            case "mur":
            default:
                return new ModelAndView("redirect:/mur.htm");
        }
    }

}
