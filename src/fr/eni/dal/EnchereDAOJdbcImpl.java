package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Article;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;

import java.sql.*;

public class EnchereDAOJdbcImpl implements EnchereDAO {

    private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur,no_article, date_enchere, montant_enchere) VALUES ( ?,?,?,? )";
    private static final String SELECT_ENCHERE_BY_ID_USER = "SELECT no_utilisateur,no_article, date_enchere, montant_enchere FROM ENCHERES WHERE no_utilisateur = ?";
    private static final String SELECT_ENCHERE_BY_NO_ARTICLE = "SELECT ENCHERES.no_utilisateur AS encherisseur, ENCHERES.no_article, date_enchere, montant_enchere, ARTICLES.no_utilisateur AS vendeur FROM ENCHERES INNER JOIN ARTICLES ON ENCHERES.no_article = ARTICLES.no_article INNER JOIN UTILISATEURS ON ENCHERES.no_utilisateur = UTILISATEURS.no_utilisateur WHERE no_article = ?";
    private static final String UPDATE_ENCHERE = "UPDATE ENCHERES SET no_utilisateur = ? AS encherisseur, date_enchere = ?, montant_enchere = ? WHERE no_article = ?";

    /**
     * Créé une enchère dans la BDD si aucune enchere sur l'article n'a déjà été faite.
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


    /**
     * Selectionne une enchère à partir du numéro d'utilisateur faisant l'enchère (= enchérisseur)
     * @param noUtilisateur
     * @return
     * @throws BusinessException
     */
    @Override
    public Enchere selectEnchereByNoUser(int noUtilisateur) throws BusinessException {
        Enchere enchere = new Enchere();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERE_BY_ID_USER);
            pstmt.setInt(1, noUtilisateur);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Utilisateur encherisseur = new Utilisateur(rs.getInt("no_utilisateur"));
                enchere.setEncherisseur(encherisseur);
                Article article = new Article(rs.getInt("no_article"));
                enchere.setArticle(article);
                enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
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

    /**
     * Selectionne une enchère (si elle existe) à partir d'un numéro d'article.
     * @param noArt
     * @return
     * @throws BusinessException
     */
    @Override
    public Enchere selectEnchereByNoArticle(int noArt) throws BusinessException {
        Enchere enchere = new Enchere();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_ENCHERE_BY_NO_ARTICLE);
            pstmt.setInt(1, noArt);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Utilisateur encherisseur = new Utilisateur(rs.getInt("encherisseur"));
                enchere.setEncherisseur(encherisseur);
                Article article = new Article(rs.getInt("no_article"));
                enchere.setArticle(article);
                enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
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

    /**
     * Modifie une enchere dans le cas où l'article a déjà fait l'objet d'une enchere.
     * @throws BusinessException
     */
    @Override
    public void updateEnchere(Enchere enchere) throws BusinessException {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            try {
                cnx.setAutoCommit(false);
                PreparedStatement pstmt;

                pstmt = cnx.prepareStatement(UPDATE_ENCHERE);
                pstmt.setInt(1, enchere.getEncherisseur().getNoUtilisateur());
                pstmt.setDate(2, Date.valueOf(enchere.getDateEnchere()));
                pstmt.setInt(3, enchere.getMontantEnchere());
                pstmt.setInt(4, enchere.getArticle().getNoArticle());

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