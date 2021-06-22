package fr.eni.dal;

import fr.eni.bo.Article;
import fr.eni.bo.Enchere;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAOJdbcImpl implements ArticleDAO {

    String SELECT_ARTICLES_ENCHERISSABLES = ("SELECT (no_article, nom_article, description, date_debut_vente, date_de_fin_vente prix_initial, prix_vente) FROM ARTICLES WHERE date_debut_vente >= CURDATE()");

    //Select de tous les articles pouvant être encherris
    @Override
    public List<Article> selectArticlesEncherissables() {
        List<Article> listeArticlesEncherissables = new ArrayList<>();
        try(Connection cnx = ConnectionProvider.getConnection()) {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ARTICLES_ENCHERISSABLES);
            Article articleEncherissable = new Article();
            while(rs.next()){
                if(rs.getInt("no_article") != articleEncherissable.getNoArticle()){
                    articleEncherissable = new Article();
                    articleEncherissable.setNoArticle(rs.getInt("no_article"));
                    articleEncherissable.setNomArticle(rs.getString("nom_article"));
                    articleEncherissable.setDescription(rs.getString("description"));
                    articleEncherissable.setDateDebutEnchere(rs.getDate("date_debut_vente").toLocalDate());
                    articleEncherissable.setDateFinEnchere(rs.getDate("date_fin_vente").toLocalDate());
                    articleEncherissable.setPrixInitial(rs.getInt("prix_initial"));
                    articleEncherissable.setPrixVente(rs.getInt("prix_vente"));
                    listeArticlesEncherissables.add(articleEncherissable);
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        System.out.println(listeArticlesEncherissables);
        return listeArticlesEncherissables;
    }
}
