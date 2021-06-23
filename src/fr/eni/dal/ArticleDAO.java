package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Article;
import fr.eni.bo.Utilisateur;

import java.util.List;

public interface ArticleDAO {

    void insertArticle(Article article) throws BusinessException;
    List<Article> selectArticlesEncherissables() throws BusinessException;
    List<Article> selectArticlesByCategorie(int noCategorie) throws BusinessException;
    void deleteArticle(int id) throws BusinessException;
}
