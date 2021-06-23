package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Article;
import fr.eni.bo.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAOJdbcImpl implements ArticleDAO {

    private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES(nom_article, description, date_debut_vente, date_fin_vente, prix_initial, no_utilisateur, no_categorie) VALUES(?,?,?,?,?,?,?)";
    private static final String SELECT_ARTICLE_BY_CATEGORIE = "SELECT no_article, nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente, ARTICLES.no_utilisateur, CATEGORIES.no_categorie, pseudo FROM ARTICLES " +
            "INNER JOIN UTILISATEURS ON ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur " +
            "INNER JOIN CATEGORIES ON ARTICLES.no_categorie = CATEGORIES.no_categorie" +
            "WHERE CATEGORIES.no_categorie = ?";
    private static final String SELECT_ARTICLES_ENCHERISSABLES = "SELECT no_article, nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente, ARTICLES.no_utilisateur, pseudo FROM ARTICLES INNER JOIN UTILISATEURS ON ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur WHERE date_debut_vente <= GETDATE()";

    @Override
    public void insertArticle(Article article) throws BusinessException {
        if(article == null){
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.INSERT_OBJET_NULL);
            throw businessException;
        }

    }

    //Select de tous les articles pouvant être encherris lié à leur vendeur
    @Override
    public List<Article> selectArticlesEncherissables() throws BusinessException {
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
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            //TODO gestion erreur personnalisée
            throw new BusinessException();
        }
        System.out.println(listeArticlesEncherissables);
        return listeArticlesEncherissables;
    }

    @Override
    public List<Article> selectArticlesByCategorie(int noCategorie) throws BusinessException {
        List<Article> listeArticleCategorie = new ArrayList<>();
        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLE_BY_CATEGORIE);
            pstmt.setInt(1, noCategorie);
            ResultSet rs = pstmt.executeQuery();
            Article article = new Article();
            while (rs.next()){
                article.setNoArticle(rs.getInt("no_article"));
                article.setNomArticle(rs.getString("nom_article"));
                article.setDescription(rs.getString("description"));
                article.setDateDebutEnchere(rs.getDate("date_debut_vente").toLocalDate());
                article.setDateFinEnchere(rs.getDate("date_fin_vente").toLocalDate());
                article.setPrixInitial(rs.getInt("prix_initial"));
                article.setPrixVente(rs.getInt("prix_vente"));
                listeArticleCategorie.add(article);
            }
            Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"));
            article.setVendeur(utilisateur);

        }catch (SQLException e) {
            System.err.println(e.getMessage());
            //TODO gestion erreur personnalisée
            throw new BusinessException();
        }
        return listeArticleCategorie;
    }
}
