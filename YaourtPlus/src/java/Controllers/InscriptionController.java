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
        ModelAndView mv = new ModelAndView("inscription");
        result = "test";
        mv.addObject("inscriptionMessage", result);
        return mv;
    }
    
}