package fr.eni.servlets;

import fr.eni.bll.ArticleManager;
import fr.eni.bo.Article;
import org.eclipse.jdt.internal.compiler.env.ISourceType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("coucou");
//        affichage de la liste des articles encherissables
        ArticleManager articleManager = new ArticleManager();
        try{
            List<Article> articlesEncherissables = articleManager.AfficherArticlesEncherissables();
            request.setAttribute("ArticlesEncherissables", articlesEncherissables);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("WEB-INF/accueil.jsp").forward(request, response);
    }
}
