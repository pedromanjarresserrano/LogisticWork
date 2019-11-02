/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.login;

import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
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
 * @author PedroD
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})

public class AuthorizationFilter implements Filter {

    public AuthorizationFilter() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {

            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            String reqURI = reqt.getRequestURI();
            if (reqURI.indexOf("/login.xhtml") >= 0
                    || (ses != null && ses.getAttribute("username") != null)
                    || reqURI.indexOf("/public/") >= 0
                    || reqURI.contains("javax.faces.resource")) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect(reqt.getContextPath() + "/faces/login.xhtml");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void destroy() {

    }

    @Override
    public boolean isLoggable(LogRecord record) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
