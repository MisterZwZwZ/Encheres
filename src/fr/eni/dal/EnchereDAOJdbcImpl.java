package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Article;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;

import java.sql.*;

public class EnchereDAOJdbcImpl implements EnchereDAO {

    private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur,no_article, date_enchere, montant_enchere) VALUES ( ?,?,?,? )";
    private static final String SELECT_ENCHERE_BY_NO_USER = "SELECT no_utilisateur,no_article, date_enchere, montant_enchere FROM ENCHERES WHERE no_utilisateur = ?";
    private static final String UPDATE_ENCHERE = "UPDATE ENCHERES SET no_utilisateur = ?, no_article = ?, date_enchere = ?, montant_enchere = ?";

    /**
     * Créer un objet enchère dans la BDD
     */
    @Override
    public void creerEnchere(Enchere enchere) throws BusinessException {
        if (enchere == null) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.INSERT_OBJET_NULL);
            throw businessException;
        }
        try (Connection cnx = ConnectionProvider.getConnection()) {
            cnx.setAutoCommit(false);
            try (PreparedStatement pstmt = cnx.prepareStatement(INSERT_ENCHERE)) {
                pstmt.setInt(1, enchere.getEncherisseur().getNoUtilisateur());
                pstmt.setInt(2, enchere.getArticle().getNoArticle());
                pstmt.setDate(3, Date.valueOf(enchere.getDateEnchere()));
                pstmt.setInt(4, enchere.getMontantEnchere());
                pstmt.executeUpdate();
                pstmt.close();
                cnx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                cnx.rollback();
                throw e;
            }

        } catch (Exception e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.INSERT_OBJET_ECHEC);
            throw businessException;
        }
    }


    @Override
    public Enchere selectEnchereByNoUser(int noUtilisateur) throws BusinessException {
        Enchere enchere = new Enchere();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERE_BY_NO_USER);
            pstmt.setInt(1, noUtilisateur);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Utilisateur encherisseur = new Utilisateur(rs.getInt("no_utilisateur"));
                enchere.setEncherisseur(encherisseur);
                Article article = new Article(rs.getInt("no_article"));
                enchere.setArticle(article);
                enchere.setDateEnchere(rs.getDate("date-enchere").toLocalDate());
                enchere.setMontantEnchere(rs.getInt("montant_enchere"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_ENCHERE_ECHEC);
            throw businessException;
        }
        return enchere;
    }

    public void updateEnchere(Enchere enchere) throws BusinessException {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            try {
                cnx.setAutoCommit(false);
                PreparedStatement pstmt;

                pstmt = cnx.prepareStatement(UPDATE_ENCHERE);
                pstmt.setInt(1, enchere.getEncherisseur().getNoUtilisateur());
                pstmt.setInt(2, enchere.getArticle().getNoArticle());
                pstmt.setDate(3, Date.valueOf(enchere.getDateEnchere()));
                pstmt.setInt(4, enchere.getMontantEnchere());
                pstmt.executeUpdate();
                pstmt.close();

                cnx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                cnx.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.UPDATE_OBJET_ECHEC);
            throw businessException;
        }

    }
}