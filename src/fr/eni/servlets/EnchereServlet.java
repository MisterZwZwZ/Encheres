package fr.eni.servlets;

import fr.eni.BusinessException;
import fr.eni.bll.EnchereManager;
import fr.eni.bo.Article;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/encherir")
public class EnchereServlet extends HttpServlet {

    private EnchereManager enchereManager;
    Enchere enchere = new Enchere();
    Article article = new Article();

    @Override
    public void init() throws ServletException {
        this.enchereManager = new EnchereManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        session.getAttribute("utilisateur");
        request.getRequestDispatcher("WEB-INF/enchere.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<Integer> listeCodesErreur = new ArrayList<>();


//      TODO récupérer l'enchère et la conserver au cas ou l'utilisateur perd l'enchère
        Enchere ancienneEnchere = enchere;
//      TODO faire les vérifications : liaison enchère/Article, enchère/Utilisateur, enchère/Date, enchère/Crédit


        //récupération de la proposition de l'utilisateur
        int prix = Integer.parseInt(request.getParameter("prix"));
        if(prix <= 0 || prix <= enchere.getMontantEnchere()) {
            listeCodesErreur.add(CodesErreurServlet.ENCHERE_MONTANT_ERREUR);
        }else{
            request.setAttribute("prix", prix);
        }

        //vérifier que l'enchère est liée à un article





        doGet(request,response);
    }

}
