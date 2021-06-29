package fr.eni.servlets;

import fr.eni.BusinessException;
import fr.eni.bll.ArticleManager;
import fr.eni.bll.EnchereManager;
import fr.eni.bll.RetraitManager;
import fr.eni.bo.Article;
import fr.eni.bo.Enchere;
import fr.eni.bo.Retrait;
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

@WebServlet("/enchere")
public class EnchereServlet extends HttpServlet {

    private EnchereManager enchereManager;
    private ArticleManager articleManager;
    private RetraitManager retraitManager;
    Enchere enchere = new Enchere();
    Article article = new Article();

    @Override
    public void init() throws ServletException {
        this.enchereManager = new EnchereManager();
        this.articleManager = new ArticleManager();
        this.retraitManager = new RetraitManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        List<Integer> listeCodesErreur = new ArrayList<>();


        //Récupérer le numero de l'article que l'on veut afficher
        Article articleAAfficher = null;
        int noArticle = Integer.parseInt(request.getParameter("noarticle"));
        if (noArticle == 0) {
            listeCodesErreur.add(CodesErreurServlet.CLIC_ARTICLE_ERREUR);
        } else {
            //récupérer l'objet article correspondant
            try {
                articleAAfficher = articleManager.afficherArticleParNo(noArticle);
                //Renvoyer cet article à la jsp
                request.setAttribute("article", articleAAfficher);
            } catch (BusinessException e) {
                e.printStackTrace();
                request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
            }
        }

        // en déduire l'état de l'enchère(pas débutée, en cours, terminée)
        LocalDate datedebutEnchere = articleAAfficher.getDateDebutEnchere();
        LocalDate dateFinEnchere = articleAAfficher.getDateFinEnchere();

        String etatVente = "";

        if(LocalDate.now().isBefore(datedebutEnchere)){
            etatVente = "pas demarree";
        }else if(LocalDate.now().isAfter(datedebutEnchere) && LocalDate.now().isBefore(dateFinEnchere)) {
            etatVente = "en cours";
        } else if(LocalDate.now().isAfter(dateFinEnchere)){
            etatVente = "terminee";
        }

        //Renvoyer l'état de l'enchère à la jsp
        request.setAttribute("etatVente", etatVente);

        //récupérer l'utilisateur souhaitant afficher l'enchère
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

        //récupérer l'enchère éventuelle liée à l'article
        Enchere enchereAAfficher = null;
        try {
            enchereAAfficher = enchereManager.afficherEnchereParNoArticle(articleAAfficher.getNoArticle());
            request.setAttribute("enchere", enchereAAfficher);
        } catch (BusinessException e) {
            e.printStackTrace();
        }
        //récupérer le meilleur encherisseur
        Utilisateur meilleurEncherisseur = enchereAAfficher.getEncherisseur();

        //en déduire le rôle de l'utilisateur(vendeur, encherisseur, meilleur encherisseur, acquereur )
        String statutUtilisateur = "";
        if(utilisateur.getNoUtilisateur() == articleAAfficher.getVendeur().getNoUtilisateur()){
            //l'utilisateur est le vendeur de l'article
            statutUtilisateur = "vendeur";
        } else if(utilisateur.getNoUtilisateur() == meilleurEncherisseur.getNoUtilisateur() && etatVente.equals("terminee")){
            //l'utilisateur a remporté l'enchère
            statutUtilisateur = "acquereur";
        } else if(utilisateur.getNoUtilisateur() == meilleurEncherisseur.getNoUtilisateur()){
            //l'utilisateur est le meilleur enchereur actuel et ne peut pas surencherir
            statutUtilisateur = "meilleurEncherisseur";
        } else {
            statutUtilisateur = "encherisseur";
        }
        // envoyer le statut de l'utilisateur à la jsp
        request.setAttribute("statutUtilisateur", statutUtilisateur);

        //récupérer le Retrait
        Retrait retraitAAfficher = null;
        try {
            retraitAAfficher = retraitManager.afficherRetraitParNoArticle(articleAAfficher.getNoArticle());
            request.setAttribute("retrait", retraitAAfficher);
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        //renvoyer la requête à la jsp qui gére l'affichage en fonction de ces éléments
        request.getRequestDispatcher("WEB-INF/enchere.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<Integer> listeCodesErreur = new ArrayList<>();

        //récupération de la proposition de l'utilisateur
        int montantEnchere = Integer.parseInt(request.getParameter("prix"));
        if (montantEnchere <= 0 || montantEnchere <= enchere.getMontantEnchere()) {
            listeCodesErreur.add(CodesErreurServlet.ENCHERE_MONTANT_ERREUR);
        } else {
            request.setAttribute("montantEnchere", montantEnchere);
        }

//      TODO stocker l'enchère au cas ou l'utilisateur perd l'enchère pour lui rendre les credits

//      TODO faire les vérifications : liaison enchère/Article, enchère/Utilisateur, enchère/Crédit

        //récupération du numéro utilisateur
        int noUtilisateur = 0;
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur != null) {
            noUtilisateur = utilisateur.getNoUtilisateur();
        }

        //Vérification du crédit
        int credit = 0;
        if(enchere.getMontantEnchere() < utilisateur.getCredit()){
            listeCodesErreur.add(CodesErreurServlet.ENCHERE_MONTANT_ERREUR);
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
//            doGet(request, response);
        }
    }
}


