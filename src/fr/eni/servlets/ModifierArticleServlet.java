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

@WebServlet("/modifierVente")
public class ModifierArticleServlet extends HttpServlet {
    private ArticleManager articleManager;

    @Override
    public void init() throws ServletException {
        this.articleManager = new ArticleManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //TODO récupération des données liées à l'article
        //Si on veut modifier un article et non le créer
        if(request.getParameter("noArticle") != null || !request.getParameter("noArticle").equals("")){
            int noArticleAModifier = Integer.parseInt(request.getParameter("noArticle"));

            Article articleAAfficher = null;
            //récupérer l'objet article correspondant à partir du numéro d'article
            try {
                //TODO : récupérer l'ensemble des données de la JSP ? et construire un nouvel article ?
                articleAAfficher = articleManager.afficherArticleParNo(noArticleAModifier);
                //TODO CG: appeler la méthode update article
                Article articleModifie = articleManager.modifierArticle(articleAAfficher);
                //FIXME null pinter ex ici


                //Renvoyer cet article à la jsp
                String msgConfModifArticle = "La modification de l'article a bien été enregistrée";
                request.setAttribute("msgConfModifArticle", msgConfModifArticle);

                request.setAttribute("articleAModifier", articleModifie);
                request.getRequestDispatcher("WEB-INF/vente.jsp").forward(request, response);

            } catch (BusinessException e) {
                e.printStackTrace();
                request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
