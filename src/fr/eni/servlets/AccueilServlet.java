package fr.eni.servlets;

import fr.eni.BusinessException;
import fr.eni.bll.ArticleManager;
import fr.eni.bll.CategorieManager;
import fr.eni.bll.EnchereManager;
import fr.eni.bo.Article;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;
import org.eclipse.jdt.internal.compiler.env.ISourceType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {
                            "/accueil",
                            "/deconnexion"
                        })
public class AccueilServlet extends HttpServlet {
    CategorieManager cm = new CategorieManager();
    ArticleManager articleManager = new ArticleManager();
    EnchereManager enchereManager = new EnchereManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //si on arrive sur la servlet via le bouton deconnexion, supression de la session en cours avant l'affichage
        if(request.getServletPath().equals("/deconnexion")) {
            HttpSession session = request.getSession();
            session.invalidate();
        }

        // affichage de la liste des articles encherissables et de la liste des catégories qui est passée en attribut de portée application
        try{

            //Si l'utilisateur est connecté, les articles ne sont pas affichés : il faut
            HttpSession session = request.getSession();
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            if (utilisateur != null) {
            //TODO faire un choix : qu'affiche t-on si l'utilisateur est connecté ? scahant que les boutons cliqués sont gardés en mémoire

            }
            else{ // si l'utilisateur n'est pas connecté, on affiche les articles ouverts à la vente.
                List<Article> articlesEncherissables = articleManager.afficherArticlesEncherissables();
                Map listeDesCategories = new HashMap();
                listeDesCategories = cm.afficherCategories();
                this.getServletContext().setAttribute("listeDesCategories", listeDesCategories);
                request.setAttribute("listeArticles", articlesEncherissables);
            }

        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
        }
        request.getRequestDispatcher("WEB-INF/accueil.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
