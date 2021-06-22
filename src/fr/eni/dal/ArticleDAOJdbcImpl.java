package fr.eni.dal;

import fr.eni.bo.Article;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAOJdbcImpl implements ArticleDAO {

    String SELECT_ARTICLES_ENCHERISSABLES = ("SELECT no_article, nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente, ARTICLES.no_utilisateur, pseudo FROM ARTICLES INNER JOIN UTILISATEURS ON ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur WHERE date_debut_vente <= GETDATE()");

    //Select de tous les articles pouvant être encherris lié à leur vendeur
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
                Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"));
                    articleEncherissable.setVendeur(utilisateur);
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        System.out.println(listeArticlesEncherissables);
        return listeArticlesEncherissables;
    }
}
