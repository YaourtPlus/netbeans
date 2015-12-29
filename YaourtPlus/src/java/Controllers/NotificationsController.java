/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Service.ProfilService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Thomas
 */
@Controller
public class NotificationsController {
        
    @Autowired
    ProfilService profilService;
    
    @RequestMapping(value = "notifications", method = RequestMethod.GET)
    public ModelAndView listNotifications(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv;

        HttpSession session = request.getSession(false);

        if (session == null) {
            mv = new ModelAndView("connexion");
            mv.addObject("inscriptionMessage", "Veuillez vous connecter pour accéder à cette page");
            return mv;
        }

        mv = new ModelAndView("notifications");
        int idUtilisateur = (int) session.getAttribute("idUtilisateur");
        mv.addObject("listNotif", profilService.getNotifications(idUtilisateur));

        return mv;
    }
}
