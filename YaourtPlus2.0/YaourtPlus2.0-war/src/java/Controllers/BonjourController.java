/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author tbenoist
 */
@Controller
public class BonjourController {
    
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(){
        return "index";
    }
    
    @RequestMapping(value = "connexion", method = RequestMethod.POST)
    public ModelAndView bonjour(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ModelAndView mv = new ModelAndView("bonjour");

        mv.addObject("nom", "test");

        return mv;
    }
}
