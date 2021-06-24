package fr.eni.servlets;

import fr.eni.BusinessException;
import fr.eni.bll.UtilisateurManager;
import fr.eni.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/private/profil")
public class ProfilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UtilisateurManager um = new UtilisateurManager();
        String pseudo = req.getParameter("pseudo");

        //On récupère l'utilisateur par son pseudo car il est unique
        try {
            Utilisateur utilisateurTrouve = um.retournerUtilisateurParPseudo(pseudo);
            System.out.println(utilisateurTrouve.getNom());
            req.setAttribute("utilisateur", utilisateurTrouve);
        } catch (BusinessException e) {
            e.printStackTrace();
            //TODO : gérer les erreurs
        }

        req.getRequestDispatcher("WEB-INF/profil.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
