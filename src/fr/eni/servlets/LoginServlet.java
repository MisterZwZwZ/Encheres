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

        if(email == null  || email.trim().equals("")){
            listeCodesErreur.add(CodesErreurServlet.EMAIL_UTILISATEUR_OBLIGATOIRE);
        }
        if(motDePasse == null || motDePasse.trim().equals("")){
            //On conserve l'email saisi précédement
            request.setAttribute("email", email);
            listeCodesErreur.add(CodesErreurServlet.MDP_UTILISATEUR_OBLIGATOIRE);
        }

        if(listeCodesErreur.size()>0) {
            request.setAttribute("listeCodesErreur",listeCodesErreur);
            request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
        }

        UtilisateurManager utilisateurManager = new UtilisateurManager();
        Utilisateur utilisateur = null;

        // Récupération de la session depuis la requête
        HttpSession session = request.getSession();

        try {
            //récupération de l'utilisateur associé à l'email en BDD & vérification de la coorespondance du mot de passe
            utilisateur = utilisateurManager.retournerUtilisateurParEmail(email);
            //l'utilisateur existe, on vérifie que le mot de passe correspond
            boolean mdpIsOk = utilisateurManager.MotDePasseCorrespond(motDePasse, utilisateur);

            if (mdpIsOk) {
                //envoi de l'utilisateur à la session
                session.setAttribute("utilisateur", utilisateur);
                request.getRequestDispatcher("accueil").forward(request, response);

            } else {
                //renvoyer message d'erreur mot de passe sur la jsp login
                listeCodesErreur.add(CodesErreurServlet.MDP_INCORRECT);
                request.setAttribute("listeCodesErreur", listeCodesErreur);
                // On garde l'affichage du dernier email
                request.setAttribute("email", email);
                request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
            }
        } catch (BusinessException ex) {
            //renvoyer messages d'erreur remontés par les autres couches
            request.setAttribute("listeCodesErreur", ex.getListeCodesErreur());
            // On garde l'affichage du dernier email
            request.setAttribute("email", email);
            request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
            ex.printStackTrace();
        }
    }
}
