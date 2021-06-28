package fr.eni.servlets;

import fr.eni.bll.ArticleManager;
import fr.eni.bll.EnchereManager;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/encherir")
public class EnchereServlet extends HttpServlet {

    private EnchereManager enchereManager;
    private ArticleManager articleManager;
    Enchere enchere = new Enchere();

    @Override
    public void init() throws ServletException {
        this.enchereManager = new EnchereManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (!(session.getAttribute("utilisateur") == null)) {
            request.getRequestDispatcher("WEB-INF/enchere.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<Integer> listeCodesErreur = new ArrayList<>();

//      TODO stocker l'enchère au cas ou l'utilisateur perd l'enchère pour lui rendre les credits

//      TODO faire les vérifications : liaison enchère/Article, enchère/Utilisateur, enchère/Crédit

        //récupération du numéro utilisateur
        int noUtilisateur = 0;
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur != null) {
            noUtilisateur = utilisateur.getNoUtilisateur();
        }

        //TODO récupération du numéro d'article sur lequel l'enchère est réalisée



        //récupération de la proposition de l'utilisateur
        int montantEnchere = Integer.parseInt(request.getParameter("prix"));
        if (montantEnchere <= 0 || montantEnchere <= enchere.getMontantEnchere()) {
            listeCodesErreur.add(CodesErreurServlet.ENCHERE_MONTANT_ERREUR);
        } else {
            request.setAttribute("montantEnchere", montantEnchere);
        }

        // TODO si article déjà enchéri, afficher la dernière offre et verifier que la nouvelle enchère est + élevée

        if (listeCodesErreur.size() > 0) {
            request.setAttribute("listeCodesErreur", listeCodesErreur);
            String test = "test";
            request.setAttribute("test", test);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/vente.jsp");
            rd.forward(request, response);
//       On insère l'enchère
        } else {
//            try {
//
//            enchereManager.creerEnchere(dateEnchere, montantEnchere, noUtilisateur, noArticle);
//
//            } catch (BusinessException e) {
//                e.printStackTrace();
//                request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
            doGet(request, response);
        }
    }
}

