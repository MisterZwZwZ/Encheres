package fr.eni.servlets;

import fr.eni.BusinessException;
import fr.eni.bll.ArticleManager;
import fr.eni.bll.CategorieManager;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    request.getRequestDispatcher("WEB-INF/accueil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //gère les problèmes d'encodage utf-8
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        List<Article> listeArticles = new ArrayList<>();

        //récupération du mot clef recherché
        String motclef = request.getParameter("rechercheParMotClef");
        if (motclef != null){
            request.setAttribute("motclef", motclef);
        }

        //récupération de la catégorie choisie de l'utilisateur
        String categorie = request.getParameter("rechercheParcategorie");
        //TODO CG en jsp, réafficher la possibilité de ne pas trier par catégorie

        int noCategorie = 0;
        if (categorie != null && !categorie.equals("")) {
            noCategorie = Integer.parseInt(categorie);
            //récupérer la liste des categories en application, retrouver la valeur grace à la hashmap et la renvoyer en jsp
            Map listeDesCategories = new HashMap();
            listeDesCategories = (Map) this.getServletContext().getAttribute("listeDesCategories");
            listeDesCategories.get(noCategorie);
            request.setAttribute("categorieChoisie", listeDesCategories.get(noCategorie));
            System.out.println(listeDesCategories.get(noCategorie));
        }

        //récupération du numéro utilisateur
        int noUtilisateur =0;
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur != null){
            noUtilisateur = utilisateur.getNoUtilisateur();

        //récupération choix "achat" ou "vente"
        String achatOuVente = request.getParameter("achatOuVente");

        //Récupération des cases cochees
        String case1 = null;
        String case2 = null;
        String case3 = null;

        if (achatOuVente.equals("achat")){
            case1 = request.getParameter("encheresOuvertes");
            case2 = request.getParameter("mesEncheresEnCours");
            case3 = request.getParameter("encheresRemportees");
        }
        if (achatOuVente.equals("vente")){
            case1 = request.getParameter("ventesEnCours");
            case2 = request.getParameter("ventesNonDebutees");
            case3 = request.getParameter("ventesTerminees");
        }

            try {
                listeArticles = am.rechercheParfiltre(motclef, noCategorie, achatOuVente, case1, case2, case3, noUtilisateur);
            } catch (BusinessException e) {
                e.printStackTrace();
                request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
            }

        }else{
            try {
                listeArticles = am.rechercheParfiltre(motclef, noCategorie, "achat", null, null, null, noUtilisateur);
            } catch (BusinessException e) {
                e.printStackTrace();
                request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
            }
        }


        request.setAttribute("listeArticles",listeArticles );
        request.getRequestDispatcher("WEB-INF/accueil.jsp").forward(request, response);
    }
}
