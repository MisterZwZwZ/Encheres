package fr.eni.bll;

import fr.eni.BusinessException;
import fr.eni.bo.Article;
import fr.eni.bo.Categorie;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.ArticleDAO;
import fr.eni.dal.DAOFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ArticleManager {

    private final ArticleDAO articleDAO;

    public ArticleManager(){
        this.articleDAO = DAOFactory.getArticleDAO();
    }

    /**
     * Vérifie les données saisies par l'utilisateur lors de l'ajout d'un article
     */
    public void validerArticle(String nomArticle, String description, LocalDate dateDebutEnchere, LocalDate dateFinEnchere, int prixInitial, int prixVente, Categorie categorie, BusinessException businessException) throws BusinessException{
        if(nomArticle == null || nomArticle.trim().length()>30){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ART_NOM_ERREUR);
        }
        if(description == null || description.trim().length()>300){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ART_DESCR_ERREUR);
        }
        if(dateDebutEnchere == null || dateDebutEnchere.isBefore(LocalDate.now())){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ART_DATEDEBUT_ERREUR);
        }
        if(dateFinEnchere == null || dateFinEnchere.isBefore(Objects.requireNonNull(dateDebutEnchere))){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ART_DATEFIN_ERREUR);
        }
        if(prixInitial <= 0 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ART_PRIXINITIAL_ERREUR);
        }
        if(prixVente <= 0){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ART_PRIXFINAL_ERREUR);
        }
        if(categorie == null){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ART_CATEGORIE_ERREUR);
        }
    }

    /**
     * Insere un article en BDD une fois les contrôles effectués
     */
    //TODO connection à l'IHM
    public void insererArticle(String nomArticle, String description, LocalDate dateDebutEnchere, LocalDate dateFinEnchere, int prixInitial, int prixVente, Categorie categorie, Utilisateur vendeur) throws BusinessException {

        BusinessException businessException = new BusinessException();
        this.validerArticle(nomArticle,description, dateDebutEnchere,dateFinEnchere, prixInitial, prixVente, categorie, businessException);

        if(!(businessException.hasErreurs())){
            Article article = new Article(nomArticle, description, dateDebutEnchere, dateFinEnchere, prixInitial, prixVente, categorie, vendeur);
            articleDAO.insertArticle(article);
        }
        else {
            throw businessException;
        }
    }


    /**
     * Renvoie la liste des articles encherissables
     */
    public List<Article> AfficherArticlesEncherissables() throws BusinessException {
       List<Article> ArticlesEncherissables = articleDAO.selectArticlesEncherissables();
       return ArticlesEncherissables;
    }

    /**
     * Renvoie la liste des articles par catégorie
     */
    public List<Article> AfficherArticleParCategorie(String nomCategorie) throws BusinessException {
        List<Article> listeArticlesParCategorie = new ArrayList<>();
        //selectionner toutes les catégories par nom et numéro
        List<Categorie> listeDescategories = articleDAO.selectAllCategories();
        System.out.println(listeDescategories);
        //boucler sur la liste des catégories pour retrouver celle sélectionnée à partir de son libelle
        for (Categorie c: listeDescategories
             ) {
            if (c.getLibelle().equals(nomCategorie)){
                listeArticlesParCategorie = articleDAO.selectArticlesByCategorie(c.getNoCategorie());
            }
        }
      return listeArticlesParCategorie;
    }

    //TODO connection à l'IHM
    public void supprimerArticle(int id) throws BusinessException {
        articleDAO.deleteArticle(id);
    }

}
