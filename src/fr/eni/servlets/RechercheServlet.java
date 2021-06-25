package fr.eni.servlets;

import fr.eni.BusinessException;
import fr.eni.bll.ArticleManager;
import fr.eni.bo.Article;
import fr.eni.bo.Utilisateur;
import org.apache.catalina.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/recherche")
public class RechercheServlet extends HttpServlet {
    ArticleManager am = new ArticleManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String btnAchatOuVente = request.getParameter("bouton");
        if (btnAchatOuVente.equals("achat")){
            request.setAttribute("coche", "achat");
        }
        if (btnAchatOuVente.equals("vente")){
            request.setAttribute("coche", "vente");
        }
        System.out.println("passage dans la servlet");
    request.getRequestDispatcher("WEB-INF/accueil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Article> listeArticles = new ArrayList<>();

        //récupération du numéro utilisateur
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        int noUtilisateur = utilisateur.getNoUtilisateur();

        //récupération du mot clef recherché
        String motclef = request.getParameter("rechercheParMotClef");

        //récupération de la catégorie choisie de l'utilisateur
        String categorie = request.getParameter("rechercheParcategorie");
        int noCategorie = 0;
        if (categorie != null && !categorie.equals("")) {
            noCategorie = Integer.parseInt(categorie);
        }
        //récupération choix "achat" ou "vente"
        String achatOuVente = request.getParameter("achatOuVente");
        System.out.println(achatOuVente);

        //Récupération des cases cochees
        String case1 = null;
        String case2 = null;
        String case3 = null;

        if (achatOuVente.equals("achat")){
            case1 = request.getParameter("encheresOuvertes");
            case2 = request.getParameter("mesEncheresEnCours");
            case3 = request.getParameter("encheresRemportees");
            System.out.println("case 1 : " + case1 + "case 2 : " + case2 + "case 3 : " + case3);
        }
        if (achatOuVente.equals("vente")){
            case1 = request.getParameter("ventesEnCours");
            case2 = request.getParameter("ventesNonDebutees");
            case3 = request.getParameter("ventesTerminees");
            System.out.println("case 1 : " + case1 + "case 2 : " + case2 + "case 3 : " + case3);

        }
            try {
                listeArticles = am.rechercheParfiltre(motclef, noCategorie, achatOuVente, case1, case2, case3, noUtilisateur);
            } catch (BusinessException e) {
                e.printStackTrace();
                //TODO CG gérer les erreurs
            }

            request.setAttribute("listeArticles",listeArticles );
            request.getRequestDispatcher("WEB-INF/accueil.jsp").forward(request, response);

    }
}
