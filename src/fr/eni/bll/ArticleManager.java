package fr.eni.bll;

import fr.eni.BusinessException;
import fr.eni.bo.Article;
import fr.eni.bo.Categorie;
import fr.eni.bo.Retrait;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.ArticleDAO;
import fr.eni.dal.DAOFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class ArticleManager {

    private final ArticleDAO articleDAO;

    public ArticleManager(){
        this.articleDAO = DAOFactory.getArticleDAO();
    }

    /**
     * Vérifie les données saisies par l'utilisateur lors de l'ajout d'un article
     */
    public void validerArticle(String nomArticle, String description, LocalDate dateDebutEnchere, LocalDate dateFinEnchere, int prixInitial, int noCategorie, BusinessException businessException) throws BusinessException{
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
        if(noCategorie <= 0){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ART_CATEGORIE_ERREUR);
        }
    }

    /**
     * Insere un article et son addresse de retrait en BDD une fois les contrôles effectués
     */
    public void insererArticle(String nomArticle, String description, LocalDate dateDebutEnchere, LocalDate dateFinEnchere, int prixInitial, int noCategorie, Utilisateur vendeur, String rue, String codePostal, String ville) throws BusinessException {

        BusinessException businessException = new BusinessException();
        //vérifications des données de l'article
        this.validerArticle(nomArticle,description, dateDebutEnchere,dateFinEnchere, prixInitial, noCategorie, businessException);

        if(!(businessException.hasErreurs())){
            //créer la catégorie
            Categorie categorie = new Categorie(noCategorie);
            //créer l'article
            Article article = new Article(nomArticle, description, dateDebutEnchere, dateFinEnchere, prixInitial, categorie, vendeur);
            //créer le retrait
            Retrait retrait = new Retrait(rue, codePostal, ville, article);
            articleDAO.insertArticle(article);
            articleDAO.insertRetrait(retrait);
        }
        else {
            throw businessException;
        }
    }


    /**
     * Renvoie la liste des articles encherissables
     */
    public List<Article> afficherArticlesEncherissables() throws BusinessException {
       List<Article> ArticlesEncherissables = articleDAO.selectArticlesEncherissables();
       return ArticlesEncherissables;
    }

    /**
     * Renvoie la liste des articles en vente par catégorie
     */
    public List<Article> afficherArticleParCategorie(int noCategorie) throws BusinessException {
        List<Article> listeArticlesParCategorie ;
        listeArticlesParCategorie = articleDAO.selectArticlesByCategorie(noCategorie);
          //TODO CG gstion des erreurs
      return listeArticlesParCategorie;
    }

    public List<Article> afficherLesVentesDunUtilisateur(int idUser) throws BusinessException {
        List<Article> listeDesVentesParUtilisateur ;
        listeDesVentesParUtilisateur = articleDAO.selectArticlesByCategorie(idUser);
        //TODO CG gstion des erreurs // supprimer cette méthode ??? est utilisée ?
        return listeDesVentesParUtilisateur;
    }

    /**
     *  TODO test à supprimer/ Permet de tester la requete SQL - Rechercher par mot clef.
     * @return
     * @throws BusinessException
     */
    public List<Article> afficherParMotClef(String motclef) throws BusinessException {
        List<Article> listeparMotClef ;
        listeparMotClef = articleDAO.selectArticlesParMotClef(motclef);
        return listeparMotClef;
    }

    public List<Article> rechercheParfiltre(String recherche, int noCategorie, String choixAchatOuVente, String case1,
            String case2, String case3, int noUtilisateur) throws BusinessException {
        List<Article> listeVentesParFiltres = new ArrayList<>();
        String motclef = recherche.toLowerCase(Locale.ROOT);
        //redirection dans l'une ou l'autre méthode de la DAL selon si "achat" ou "vente" sélectionnée
        if (choixAchatOuVente.equals("achat")){
            listeVentesParFiltres = articleDAO.selectArticlesParFiltre(motclef, noCategorie, case1, case2, case3, noUtilisateur);
        }
        if (choixAchatOuVente.equals("vente")){
            listeVentesParFiltres = articleDAO.selectVentesParFiltre(motclef, noCategorie, case1, case2, case3, noUtilisateur);
        }
        //TODO CG gestion des erreurs
        return listeVentesParFiltres;
    }

    //TODO connection à l'IHM - TL
    public void supprimerArticle(int id) throws BusinessException {
        articleDAO.deleteArticle(id);
    }

}
