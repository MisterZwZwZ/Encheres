package fr.eni.servlets;

import fr.eni.bll.ArticleManager;
import fr.eni.bo.Article;
import org.eclipse.jdt.internal.compiler.env.ISourceType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {
                            "/accueil",
                            "/deconnexion"
                        })
public class AccueilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //si on arrive sur la servlet via le bouton deconnexion, supression de la session en cours avant l'affichage
        if(request.getServletPath().equals("/deconnexion")) {
            HttpSession session = request.getSession();
            session.invalidate();
        }
        //TODO affichage quand connecté ?

//        affichage de la liste des articles encherissables sans être connecté (ou connecté)

        ArticleManager articleManager = new ArticleManager();
        try{
            List<Article> articlesEncherissables = articleManager.afficherArticlesEncherissables();
            request.setAttribute("listeArticles", articlesEncherissables);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("WEB-INF/accueil.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
