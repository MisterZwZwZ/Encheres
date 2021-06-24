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
    List<Categorie> selectAllCategories() throws BusinessException;
    List<Article> selectArticlesByCategorie(int noCategorie) throws BusinessException;
    List<Article> selectArticlesParMotClef(String chaine) throws BusinessException;
    void deleteArticle(int id) throws BusinessException;

    //TODO : à placer dans EncheresDAO ?
    List<Article> selectEncheresByIdUser(int idUser) throws BusinessException;
    List<Article> selectEncheresTermineesByIdUser(int idUser) throws BusinessException;

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

}
