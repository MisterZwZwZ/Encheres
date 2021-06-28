package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Article;
import fr.eni.bo.Categorie;
import fr.eni.bo.Retrait;

import java.util.List;

public interface ArticleDAO {

    void insertArticle(Article article) throws BusinessException;
    void insertRetrait(Retrait retrait) throws BusinessException;
    List<Article> selectArticlesEncherissables() throws BusinessException;
    List<Article> selectArticlesByCategorie(int noCategorie) throws BusinessException;
    List<Article> selectArticlesParMotClef(String chaine) throws BusinessException;
    Article selectArticleById(int idArt) throws BusinessException;
    void deleteArticle(int id) throws BusinessException;

    /**
     * Selectionne les articles dont la date de début de vente est antérieure ou égale à la date en cours
     * Par Id vendeur
     */
    List<Article> selectAllVentesByIdUser(int idUser) throws BusinessException;

    /**
     * Selectionne les articles dont la date de début de vente est postérieure à la date en cours
     * Par Id vendeur
     */
    List<Article> selectVentesNonDebuteesByIdUser(int idUser) throws BusinessException;

    /**
     * selectionne les articles sont la date de fin de vente est dépassée
     * Par Id vendeur
     */
    List<Article> selectVentesTermineesByIdUser(int idUser) throws BusinessException;

    List<Article> selectArticlesParFiltre(String recherche, int noCategorie, String case1,
                                          String case2, String case3, int noUtilisateur) throws BusinessException;

    List<Article> selectVentesParFiltre(String recherche, int noCategorie, String case1,
                                          String case2, String case3, int noUtilisateur) throws BusinessException;

    List<Article> selectEnModeDeconnecte(String recherche, int noCategorie) throws BusinessException;

}
