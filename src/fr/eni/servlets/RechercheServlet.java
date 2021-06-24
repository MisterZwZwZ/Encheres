package fr.eni.servlets;

import fr.eni.BusinessException;
import fr.eni.bll.ArticleManager;
import fr.eni.bo.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        //récupération du choix de la catégorie choisie de l'utilisateur
        String categorie = request.getParameter("rechercheParcategorie");
        if (categorie != null && !categorie.equals("")){
            try {
                List<Article> listeArticlesParCategorie = am.AfficherArticleParCategorie(categorie);
                request.setAttribute("listeArticlesParCategorie", listeArticlesParCategorie);
            } catch (BusinessException e) {
                e.printStackTrace();
                //TODO CG : gérer les erreurs
            }
        }

        //récupération du mot clef recherché // TODO CG : à terminer
        String objetARechercher = request.getParameter("rechercheParMotClef");

        request.getRequestDispatcher("WEB-INF/accueil.jsp").forward(request, response);

    }
}
