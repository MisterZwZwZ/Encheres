package fr.eni.servlets;

import fr.eni.BusinessException;
import fr.eni.bll.ArticleManager;
import fr.eni.bll.CategorieManager;
import fr.eni.bo.Article;
import fr.eni.bo.Utilisateur;
import javafx.application.Application;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/vendre")
public class VenteServlet extends HttpServlet {
    private CategorieManager cm = new CategorieManager();
    private ArticleManager articleManager;

    @Override
    public void init() throws ServletException {
        this.articleManager = new ArticleManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            List<Article> articlesEncherissables = articleManager.afficherArticlesEncherissables();
            Map listeDesCategories = new HashMap();
            listeDesCategories = cm.afficherCategories();
            this.getServletContext().setAttribute("listeDesCategories", listeDesCategories);
            request.setAttribute("listeArticles", articlesEncherissables);

        } catch (BusinessException e) {
            e.printStackTrace();
            request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
        }
        request.getRequestDispatcher("WEB-INF/vente.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<Integer> listeCodesErreur = new ArrayList<>();
        Article article = new Article();
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        session.getAttribute("utilisateur");

        // récupération des paramètres utilisateur et vérifications d'eventuelles erreurs
        String nomArticle = request.getParameter("nomArticle");
        if (nomArticle == null || nomArticle.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.NOM_ARTICLE_OBLIGATOIRE);
        }
        request.setAttribute("nomArticle", nomArticle);

        String description = request.getParameter("description");
        if (description == null || description.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.DESCR_ARTICLE_OBLIGATOIRE);
        }
        request.setAttribute("description", description);

        int categorie = 0;
        if(request.getParameter("rechercheParcategorie") != null && !request.getParameter("rechercheParcategorie").equals("")){
            categorie = Integer.parseInt(request.getParameter("rechercheParcategorie"));

            Map listeDesCategories = new HashMap();
            listeDesCategories = (Map) this.getServletContext().getAttribute("listeDesCategories");

            String valeurCatePrecedente = (String) listeDesCategories.get(categorie);
            request.setAttribute("valeurCatePrecedente", valeurCatePrecedente);

            if(categorie <= 0){
                listeCodesErreur.add(CodesErreurServlet.CATEGORIE_ARTICLE_ERREUR);
            }
            request.setAttribute("rechercheParcategorie", categorie);
        }

        int prixInitial = 0;
        if (request.getParameter("prixInitial") != null && !request.getParameter("prixInitial").equals("")) {
            prixInitial = Integer.parseInt(request.getParameter("prixInitial"));
            if (prixInitial <= 0) {
                listeCodesErreur.add(CodesErreurServlet.PRIX_INITIAL_ARTICLE_ERREUR);
            }
            request.setAttribute("prixInitial", prixInitial);
        }

        LocalDate debutEnchere = null;
        if (request.getParameter("dateDebutEnchere") != null && !request.getParameter("dateDebutEnchere").equals("")) {
            debutEnchere = LocalDate.parse(request.getParameter("dateDebutEnchere"));
            request.setAttribute("dateDebutEnchere", debutEnchere);
        }
        if (debutEnchere == null) {
            listeCodesErreur.add(CodesErreurServlet.DATE_DEBUT_ENCHERE_ARTICLE_ERREUR);
        }

        LocalDate finEnchere = null;
        if (request.getParameter("dateFinEnchere") != null && !request.getParameter("dateFinEnchere").equals("")) {
            finEnchere = LocalDate.parse(request.getParameter("dateFinEnchere"));
            request.setAttribute("dateFinEnchere", finEnchere);
        }
        if (finEnchere == null) {
            listeCodesErreur.add(CodesErreurServlet.DATE_FIN_ENCHERE_ARTICLE_ERREUR);
        }

        String rue = request.getParameter("rue");
        if (rue == null || rue.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.RUE_RETRAIT_ERREUR);
        }
        request.setAttribute("rue", rue);

        String codePostal = request.getParameter("codePostal");
        if (codePostal == null || codePostal.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.CODE_POSTAL_RETRAIT_ERREUR);
        }
        request.setAttribute("codePostal", codePostal);

        String ville = request.getParameter("ville");
        if (ville == null || ville.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.VILLE_RETRAIT_ERREUR);
        }
        request.setAttribute("ville", ville);

        if (listeCodesErreur.size() > 0) {
            request.setAttribute("listeCodesErreur", listeCodesErreur);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/vente.jsp");
            rd.forward(request, response);
        } else {
            try {
                //On veut modifier un article
                if (request.getParameter("noArticle") != null && !request.getParameter("noArticle").equals("")){
                    int noArticle = Integer.parseInt(request.getParameter("noArticle"));
                    articleManager.insererArticle(noArticle, nomArticle, description, debutEnchere, finEnchere, prixInitial, categorie, utilisateur, rue, codePostal, ville);
                    String msgConfModifArticle = "La modification de l'article a bien été enregistrée";
                    request.setAttribute("msgConfirmationModif", msgConfModifArticle);
                    request.getRequestDispatcher("WEB-INF/confirmation.jsp").forward(request, response);

                }else{
                    //on veut créer un article
                    articleManager.insererArticle(0, nomArticle, description, debutEnchere, finEnchere, prixInitial, categorie, utilisateur, rue, codePostal, ville);
                    String msgConfCreationArticle = "Votre annonce a bien été enregistrée";
                    request.setAttribute("msgConfirmation", msgConfCreationArticle);
                    request.getRequestDispatcher("WEB-INF/vente.jsp").forward(request, response);
                }

            } catch (BusinessException e) {
                e.printStackTrace();
                request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
                request.getRequestDispatcher("WEB-INF/vente.jsp").forward(request, response);
//                doGet(request, response);
            }
        }
    }
}
