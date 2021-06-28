package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Article;
import fr.eni.bo.Retrait;
import fr.eni.bo.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RetraitDAOJdbcImpl implements RetraitDAO{

    private static final String SELECT_RETRAIT_BY_ID = "SELECT no_article, rue, code_postal, ville FROM RETRAITS WHERE no_article=?";

    @Override
    /**
     * Selectionne un retrait en fonction de son no_article
     *return : Retrait
     */
    public Retrait selectRetraitById(int idArt) throws BusinessException {
        Retrait retrait = new Retrait();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_RETRAIT_BY_ID);
            pstmt.setInt(1, idArt);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Article article = new Article(rs.getInt("no_utilisateur"));
                retrait.setArticle(article);
                retrait.setRue(rs.getString("rue"));
                retrait.setCodePostal(rs.getString("code_postal"));
                retrait.setVille(rs.getString("ville"));
            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            //gérer erreur SQL
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_RETRAIT_ECHEC);
            throw businessException;
        }
        if (retrait.getArticle() == null) {
            //gérer Article inexistant
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_ARTICLE_INEXISTANT);
            throw businessException;
        }

        return retrait;
    }
}
