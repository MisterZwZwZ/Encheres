package fr.eni.servlets;

import fr.eni.BusinessException;
import fr.eni.bll.UtilisateurManager;
import fr.eni.bo.Utilisateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.getRequestDispatcher("WEB-INF/login.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        List<Integer> listeCodesErreur = new ArrayList<>();

        //Récupération des paramettres
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");


        UtilisateurManager utilisateurManager = new UtilisateurManager();
        Utilisateur utilisateur = null;

        // Récupération de la session depuis la requête
        HttpSession session = request.getSession();

        try {
            //récupération de l'utilisateur associé à l'indentifiant en BDD
            utilisateur = utilisateurManager.retournerUtilisateur(email);
            //vérification de la coorespondance du mot de passe
            String motPasseBDD = utilisateur.getMotDePasse();
            if (motDePasse.equals(motPasseBDD)) {
                //envoi de l'utilisateur à la session
                session.setAttribute("utilisateur", utilisateur);
                request.getRequestDispatcher("WEB-INF/accueil.jsp").forward(request, response);

            } else {
                //renvoyer message d'erreur mot de passe sur la jsp login
                listeCodesErreur.add(CodesErreurServlet.MDP_INCORRECT);
                request.setAttribute("listeCodesErreur", listeCodesErreur);
                // On garde l'affichage email, fragment pour entête
                request.setAttribute("email", email);
                request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
            }
        } catch (BusinessException ex) {
            //renvoyer message d'erreur utilisateur inconnu sur la jsp login
            listeCodesErreur.add(CodesErreurServlet.UTILISATEUR_INEXISTANT);

            request.setAttribute("listeCodesErreur", listeCodesErreur);
            request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
            ex.printStackTrace();
        }
    }
}
