/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Security;

import Controllers.SessionController;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thomas
 */
@WebFilter("/faces/secured/*")
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);

        SessionController sessionController = (SessionController) request.getSession(false).getAttribute("sessionController");

        String loginUrl = request.getContextPath() + "/faces/connexion.xhtml";

        boolean loggedIn = (session != null) && (sessionController.getIdUtilisateur() != -1);
        boolean loginRequest = request.getRequestURI().equals(loginUrl);

        if (loggedIn || loginRequest) {
            chain.doFilter(req, res);
        } else {
            response.sendRedirect(loginUrl);
        }

    }

    @Override
    public void destroy() {
    }

}
