package fr.eni.servlets;

import fr.eni.BusinessException;
import fr.eni.bll.UtilisateurManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {
        "/monprofil",
        "/supp"
})

public class MonProfil extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //si on arrive sur la servlet via le bouton supprimer, supression du compte utilisateur +
        // renvoi vers la servlet accueil via l'url deconnexion
        if(request.getServletPath().equals("/supp")) {
            UtilisateurManager um = new UtilisateurManager();
           //on récupére l'id utilisateur à supprimer depuis la jsp
            int idUtilisateur = Integer.parseInt(request.getParameter("idUtilisateur"));
            try {
                um.supprimerUtilisateur(idUtilisateur);
                request.getRequestDispatcher("deconnexion").forward(request, response);
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher("WEB-INF/monprofil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
