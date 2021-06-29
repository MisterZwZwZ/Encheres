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
    List<Article> selectAllVentesByIdUser(int idUser) throws BusinessException;
    Article selectArticleById(int idArt) throws BusinessException;
    Article updateArticle(Article article) throws BusinessException;
    void deleteArticle(int id) throws BusinessException;

    List<Article> selectArticlesParFiltre(String recherche, int noCategorie, String case1,
                                          String case2, String case3, int noUtilisateur) throws BusinessException;

    List<Article> selectVentesParFiltre(String recherche, int noCategorie, String case1,
                                          String case2, String case3, int noUtilisateur) throws BusinessException;

    List<Article> selectEnModeDeconnecte(String recherche, int noCategorie) throws BusinessException;

}
