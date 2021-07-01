package fr.eni.servlets;

import fr.eni.BusinessException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.jar.Attributes;

@WebFilter(urlPatterns = {"/profil",
        "/monprofil",
        "/vendre",
        "/enchere"})
public class connexionFiltre implements Filter {
    public static final String ACCES_CONNEXION = "WEB-INF/login.jsp";
    public static final String ATT_SESSION_USER = "utilisateur";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /* Cast des objets request et response */
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession(false);

        /**
         * Si l'objet utilisateur n'existe pas dans la session en cours, alors
         * l'utilisateur n'est pas connecté.
         */
        try {
            if (session.getAttribute(ATT_SESSION_USER) == null) {
                /* Redirection vers la page publique */
                RequestDispatcher dispatcher = request.getRequestDispatcher(ACCES_CONNEXION);
                dispatcher.forward(request, response);
            } else {
                /* Affichage de la page restreinte */
                filterChain.doFilter(request, response);
            }
        }catch(Exception e){
            RequestDispatcher dispatcher = request.getRequestDispatcher(ACCES_CONNEXION);
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
