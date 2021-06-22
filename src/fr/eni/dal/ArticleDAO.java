package fr.eni.dal;

import fr.eni.bo.Article;

import java.util.List;

public interface ArticleDAO {

    List<Article> selectArticlesEncherissables();
}
