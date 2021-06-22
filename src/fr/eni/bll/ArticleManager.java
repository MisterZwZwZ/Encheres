package fr.eni.bll;

import fr.eni.bo.Article;
import fr.eni.dal.ArticleDAO;
import fr.eni.dal.DAOFactory;

import java.util.List;

public class ArticleManager {

    public List<Article> AfficherArticlesEncherissables(){
        ArticleDAO aDAO = DAOFactory.getArticleDAO();
       List<Article> ArticlesEncherissables = aDAO.selectArticlesEncherissables();
       return ArticlesEncherissables;
    }
}
