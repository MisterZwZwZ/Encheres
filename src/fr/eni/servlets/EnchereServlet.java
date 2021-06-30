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
        int noArticle = 0;
        if (request.getParameter("noarticle") != null && !request.getParameter("noarticle").equals("")){
            noArticle = Integer.parseInt(request.getParameter("noarticle"));
        }

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
        }else if(LocalDate.now().isAfter(datedebutEnchere) && (LocalDate.now().isBefore(dateFinEnchere) || LocalDate.now().equals(dateFinEnchere))) {
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
            enchereAAfficher = enchereManager.afficherEncherePseudoParNoArticle(articleAAfficher.getNoArticle());
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
        } else if( meilleurEncherisseur != null && utilisateur.getNoUtilisateur() == meilleurEncherisseur.getNoUtilisateur() && etatVente.equals("terminee")){
            //l'utilisateur a remporté l'enchère
            statutUtilisateur = "acquereur";
        } else if( meilleurEncherisseur != null && utilisateur.getNoUtilisateur() == meilleurEncherisseur.getNoUtilisateur()){
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
            request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
        }

        //renvoyer la requête à la jsp qui gére l'affichage en fonction de ces éléments
        request.getRequestDispatcher("WEB-INF/enchere.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<Integer> listeCodesErreur = new ArrayList<>();

        //récupération du numéro utilisateur
        int noUtilisateur = 0;
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        if (utilisateur != null) {
            noUtilisateur = utilisateur.getNoUtilisateur();
        }

        //Récupération de l'objet concerné par l'enchère
        int noArticle = Integer.parseInt(request.getParameter("noarticle"));

        //récupération de la proposition d'enchere de l'utilisateur et vérification par rapport à son crédit
        int montantEnchere = 0;
        if (request.getParameter("prix") != null && !request.getParameter("prix").equals("")) {
            montantEnchere = Integer.parseInt(request.getParameter("prix"));
            request.setAttribute("montantEnchere", montantEnchere);
            if (montantEnchere <= 0) { //|| montantEnchere <= enchere.getMontantEnchere()
                listeCodesErreur.add(CodesErreurServlet.ENCHERE_MONTANT_ERREUR);
            }
            if (montantEnchere > utilisateur.getCredit()) {
                listeCodesErreur.add(CodesErreurServlet.ENCHERE_CREDIT_ERREUR);
            }
        }

        //S'il y a des erreurs, les afficher.
        if (listeCodesErreur.size() > 0) {
            request.setAttribute("listeCodesErreur", listeCodesErreur);
            doGet(request, response);

        //Sinon, on passe l'enchère au manager qui va effectuer les contrôles et l'envoyer en bdd.
        } else {
            try {
                enchereManager.faireUneEnchere(montantEnchere, utilisateur, noArticle);
                String messageConfirmationEnchere = "Votre enchère a bien été prise en compte";
                request.setAttribute("messageConf", messageConfirmationEnchere);
                doGet(request, response);
            } catch (BusinessException e) {
                e.printStackTrace();
                request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
            }
        }
    }
}


