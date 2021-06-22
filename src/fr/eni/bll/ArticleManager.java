package fr.eni.bll;

import fr.eni.bo.Article;
import fr.eni.dal.ArticleDAO;
import fr.eni.dal.DAOFactory;
import sun.security.krb5.internal.crypto.Aes128;

import java.util.List;

public class ArticleManager {

    public List<Article> articlesEncherissables(){
        ArticleDAO aDAO = DAOFactory.getArticleDAO();
       List<Article> listeArticlesEncherissables = aDAO.selectArticlesEncherissables();
       return listeArticlesEncherissables;
    }
}
