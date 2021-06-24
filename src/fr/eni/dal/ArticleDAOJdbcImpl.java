package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Article;
import fr.eni.bo.Retrait;
import fr.eni.bo.Categorie;
import fr.eni.bo.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAOJdbcImpl implements ArticleDAO {

    private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente, no_utilisateur, no_categorie VALUES (?,?,?,?,?,?,?,?)";
    private static final String SELECT_ARTICLE_BY_CATEGORIE = "SELECT no_article, nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente,\n" +
            "       ARTICLES.no_utilisateur, CATEGORIES.no_categorie FROM ARTICLES INNER JOIN UTILISATEURS ON\n" +
            "           ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES.no_categorie =\n" +
            "          CATEGORIES.no_categorie WHERE date_debut_vente <= GETDATE() AND date_fin_vente >= GETDATE() AND ARTICLES.no_categorie = ?";
    private static final String SELECT_ARTICLES_ENCHERISSABLES = "SELECT no_article, nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente, ARTICLES.no_utilisateur, pseudo FROM ARTICLES INNER JOIN UTILISATEURS ON ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur WHERE date_debut_vente <= GETDATE() AND date_fin_vente >= GETDATE()";
    private static final String SELECT_ARTICLES_ENCHERISSABLES_BY_ID = "SELECT no_article, nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente, ARTICLES.no_utilisateur, pseudo FROM ARTICLES INNER JOIN UTILISATEURS ON ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur WHERE date_debut_vente <= GETDATE() AND date_fin_vente >= GETDATE() AND no_utilisateur=?";
    private static final String SELECT_ARTICLES_ENCHERISSABLES_PAR_MOTCLEF = "SELECT no_article, nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente, ARTICLES.no_utilisateur, pseudo FROM ARTICLES INNER JOIN UTILISATEURS ON ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur WHERE date_debut_vente <= GETDATE() AND date_fin_vente >= GETDATE() AND ARTICLES.nom_article LIKE ('''%?%'''') ";

    private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES (nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES ( ?,?,?,?,?,?,?,? )";
    private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES ( ?,?,?,? )";
    private static final String SELECT_ARTICLE_BY_CATEGORIE = "SELECT no_article, nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente, ARTICLES.no_utilisateur, CATEGORIES.no_categorie, pseudo FROM ARTICLES " +
            "INNER JOIN UTILISATEURS ON ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur " +
            "INNER JOIN CATEGORIES ON ARTICLES.no_categorie = CATEGORIES.no_categorie" +
            "WHERE CATEGORIES.no_categorie = ?";
    private static final String SELECT_ARTICLES_ENCHERISSABLES = "SELECT no_article, nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente, ARTICLES.no_utilisateur, pseudo FROM ARTICLES INNER JOIN UTILISATEURS ON ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur WHERE date_debut_vente <= GETDATE()";
    private static final String SELECT_CATEGORIES = "SELECT no_categorie, libelle FROM CATEGORIES";
    private static final String DELETE_ARTICLE = "DELETE FROM ARTICLES WHERE ID=?";

    @Override
    public void insertArticle(Article article) throws BusinessException {
        if (article == null) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.INSERT_OBJET_NULL);
            throw businessException;
        }
        try (Connection cnx = ConnectionProvider.getConnection()) {
            cnx.setAutoCommit(false);
            ResultSet rs;
                try (PreparedStatement pstmt = cnx.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS)){
                    pstmt.setString(1, article.getNomArticle());
                    pstmt.setString(2, article.getDescription());
                    pstmt.setDate(3, java.sql.Date.valueOf(article.getDateDebutEnchere()));
                    pstmt.setDate(4, java.sql.Date.valueOf(article.getDateFinEnchere()));
                    pstmt.setInt(5, article.getPrixInitial());
                    pstmt.setInt(6, article.getPrixVente());
                    pstmt.setInt(7, article.getVendeur().getNoUtilisateur());
                    pstmt.setInt(8, article.getCategorie().getNoCategorie());
                    pstmt.executeUpdate();
                    rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        article.setNoArticle(rs.getInt(1));
                    }
                rs.close();
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
     * Insère un retrait dans la BDD a l'ajout d'un article
     */
    @Override
    public void insertRetrait(Retrait retrait) throws BusinessException{
        if (retrait == null) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.INSERT_OBJET_NULL);
            throw businessException;
        }
        try (Connection cnx = ConnectionProvider.getConnection()) {
            cnx.setAutoCommit(false);
            try (PreparedStatement pstmt = cnx.prepareStatement(INSERT_RETRAIT)){
                pstmt.setInt(1, retrait.getArticle().getNoArticle());
                pstmt.setString(2, retrait.getRue());
                pstmt.setString(3, retrait.getCodePostal());
                pstmt.setString(4, retrait.getVille());
                pstmt.executeUpdate();
                pstmt.close();
                cnx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.INSERT_OBJET_ECHEC);
            throw businessException;
        }
    }

    @Override
    /**
     * Select de tous les articles pouvant être encherris : dont la date de début est antérieure ou égale à la date du jour,
     * dont la date de fin est égale ou postérieure à la date du jour
     */
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
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_ARTICLES_ECHEC);
            throw businessException;
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

                Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"));
                article.setVendeur(utilisateur);

                Categorie categorie = new Categorie(rs.getInt("no_categorie"));
                article.setCategorie(categorie);
            }


        }catch (SQLException e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_ARTICLES_ECHEC);
            throw businessException;
        }
        return listeArticleCategorie;
    }

    @Override
    public void deleteArticle(int id) throws BusinessException {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(DELETE_ARTICLE);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.SUPPRESSION_ARTICLE_ERREUR);
            throw businessException;
        }
    }

    //TODO CG : continuer dans la couche bll etc
    @Override
    public List<Article> selectArticlesParMotClef(String chaine) throws BusinessException {
        List<Article> listeArticleEnVenteParMotClef = new ArrayList<>();
        //TODO CG  méthode à tester
        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLES_ENCHERISSABLES_PAR_MOTCLEF);
            pstmt.setString(1, chaine);
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
                listeArticleEnVenteParMotClef.add(article);

                Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"));
                article.setVendeur(utilisateur);

                Categorie categorie = new Categorie(rs.getInt("no_categorie"));
                article.setCategorie(categorie);
            }

        }catch (SQLException e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_ARTICLES_ECHEC);
            throw businessException;
        }
        return listeArticleEnVenteParMotClef;
    }

    @Override
    public List<Article> selectEncheresByIdUser(int idUser) throws BusinessException {
        return null;
    }

    @Override
    public List<Article> selectEncheresTermineesByIdUser(int idUser) throws BusinessException {
        return null;
    }

    @Override
    /**
     * Selectionne les articles dont la date de début de vente est antérieure ou égale à la date en cours,
     * dont la date de fin de vente est égale ou postérieure à la date en cours,
     * Par Id vendeur
     */
    public List<Article> selectAllVentesByIdUser(int idUser) throws BusinessException {
        List<Article> listeArticleEnVenteParVendeur = new ArrayList<>();
        //TODO CG  méthode à tester
        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLES_ENCHERISSABLES_BY_ID);
            pstmt.setInt(1, idUser);
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
                listeArticleEnVenteParVendeur.add(article);

                Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"));
                article.setVendeur(utilisateur);

                Categorie categorie = new Categorie(rs.getInt("no_categorie"));
                article.setCategorie(categorie);
            }

        }catch (SQLException e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_ARTICLES_ECHEC);
            throw businessException;
        }
        return listeArticleEnVenteParVendeur;
    }

    @Override
    /**
     * Selectionne les articles dont la date de début de vente est postérieure à la date en cours
     * Par Id vendeur
     */
    public List<Article> selectVentesNonDebuteesByIdUser(int idUser) throws BusinessException {
        return null;
    }

    @Override
    /**
     * selectionne les articles sont la date de fin de vente est dépassée
     * Par Id vendeur
     */
    public List<Article> selectVentesTermineesByIdUser(int idUser) throws BusinessException {
        return null;
    }

    public List<Categorie> selectAllCategories() throws BusinessException{
        List<Categorie> listeCategories = new ArrayList<>();
        try(Connection cnx = ConnectionProvider.getConnection()) {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_CATEGORIES);
            Categorie cat = new Categorie();
            while(rs.next()){
                if(rs.getInt("no_categorie") != cat.getNoCategorie()){
                    cat = new Categorie();
                    cat.setNoCategorie(rs.getInt("no_categorie"));
                    cat.setLibelle(rs.getString("libelle"));
                    listeCategories.add(cat);
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            //TODO CG gestion erreur personnalisée
            throw new BusinessException();
        }
        return listeCategories;
    }
}
