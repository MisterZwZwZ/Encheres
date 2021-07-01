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

    private static final String SELECT_ARTICLES_ENCHERISSABLES = "SELECT no_article, nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente, ARTICLES.no_utilisateur, pseudo FROM ARTICLES INNER JOIN UTILISATEURS ON ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur WHERE DATEDIFF(day, getdate(), date_fin_vente)>=0 AND DATEDIFF(day, date_debut_vente, GETDATE())>=0";
    private static final String SELECT_ARTICLES_BY_ID = "SELECT ARTICLES.no_article, ARTICLES.nom_article, ARTICLES.description, ARTICLES.date_debut_vente, ARTICLES.date_fin_vente, ARTICLES.prix_initial, ARTICLES.prix_vente, ARTICLES.no_utilisateur, UTILISATEURS.pseudo, UTILISATEURS.telephone, ARTICLES.no_categorie, c.libelle FROM ARTICLES INNER JOIN UTILISATEURS ON ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES C ON ARTICLES.no_categorie = C.no_categorie WHERE ARTICLES.no_article=?";
    private static final String INSERT_ARTICLE = "INSERT INTO ARTICLES (nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES ( ?,?,?,?,?,?,?,? )";
    private static final String INSERT_RETRAIT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES ( ?,?,?,? )";
    private static final String UPDATE_ARTICLE = "UPDATE ARTICLES SET nom_article= ?, description=?, date_debut_vente=?, date_fin_vente=?, prix_initial=?, prix_vente=?, no_utilisateur=?, no_categorie=? WHERE no_article=?";
    private static final String DELETE_ARTICLE = "DELETE FROM ARTICLES WHERE ID=?";

    //Requete SQL dynamique pour la recherche
    private String selectArticles = "SELECT ARTICLES.no_article, nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente,\n" +
            "       ARTICLES.no_utilisateur, CATEGORIES.no_categorie, pseudo FROM ARTICLES INNER JOIN UTILISATEURS ON\n" +
            "           ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES.no_categorie =\n" +
            "          CATEGORIES.no_categorie WHERE ARTICLES.nom_article LIKE  '%'+ ? +'%' "; // comprend la recherche par mot clef
    private String selectEncheres = "SELECT ARTICLES.no_article, nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente,\n" +
            "       ARTICLES.no_utilisateur, CATEGORIES.no_categorie, pseudo FROM ARTICLES INNER JOIN UTILISATEURS ON\n" +
            "           ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES.no_categorie =\n" +
            "          CATEGORIES.no_categorie INNER JOIN ENCHERES ON ARTICLES.no_article = ENCHERES.no_article WHERE ARTICLES.nom_article LIKE '%'+ ? +'%'"; // comprend la recherche par mot clef
    private String selectArticlesJointureEncheres = "SELECT ARTICLES.no_article, nom_article, description, date_debut_vente, date_fin_vente, prix_initial, prix_vente,\n" +
            "       ARTICLES.no_utilisateur, CATEGORIES.no_categorie, pseudo FROM ARTICLES INNER JOIN UTILISATEURS ON\n" +
            "           ARTICLES.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES.no_categorie =\n" +
            "          CATEGORIES.no_categorie LEFT JOIN ENCHERES ON ARTICLES.no_article = ENCHERES.no_article WHERE ARTICLES.nom_article LIKE '%'+ ? +'%'"; // comprend la recherche par mot clef
    private String parCate = " ARTICLES.no_categorie = ? ";
    private String and = " AND ";
    private String or = " OR ";
    private String achatByIdUser = " ARTICLES.no_utilisateur = ? ";
    private String enchereByIdUser = " ENCHERES.no_utilisateur = ? ";
    private String enCours = " DATEDIFF(day, getdate(), date_fin_vente)>=0 AND DATEDIFF(day, date_debut_vente, GETDATE())>=0 ";
    private String programme = " date_debut_vente > GETDATE() ";
    private String termine = " date_fin_vente < GETDATE() ";
    private String finApresDateJour = " DATEDIFF(day, GETDATE(), date_fin_vente)>=0 ";
    private String debutAvantDateJour = " DATEDIFF(day, date_debut_vente, GETDATE())>=0 ";



    /**
     * ACHATS. Cette méthode sélectionne les articles selon les filtres appliqués.
     * La requete sql est construite "sur mesure" suivant le contenu des paramètres en entrée.
     */
    @Override
    public List<Article> selectArticlesParFiltre(String recherche, int noCategorie, String case1,
                                                 String case2, String case3, int noUtilisateur) throws BusinessException {
        List<Article> listeArticlesFiltres = new ArrayList<>();
        Article articleEncours = new Article();

        StringBuffer sb = null;
        sb = new StringBuffer();

        //On construit la requete sql sur mesure selon les parametres choisis
        if (recherche == null) {
            recherche = "";
        }

        if (case1 != null && case1.equals("on") && case3 != null && case3.equals("on") && case2 == null) {
            sb.append(selectArticlesJointureEncheres);
            sb.append(and);
            sb.append(enCours);
            if (noCategorie != 0) {
                sb.append(and);
                sb.append(parCate);
            }
            sb.append(or);
            sb.append(enchereByIdUser);
            sb.append(and);
            sb.append(debutAvantDateJour);
            if (noCategorie != 0) {
                sb.append(and);
                sb.append(parCate);
            }
        } else if ((case1 != null && case1.equals("on") && case2 == null && case3 == null) || (case1 != null && case1.equals("on") && case2 != null && case2.equals("on") && case3 == null)) {
            sb.append(selectArticles);
            sb.append(and);
            sb.append(enCours);
            if (noCategorie != 0) {
                sb.append(and);
                sb.append(parCate);
            }
        } else {

            if(case1 != null && case1.equals("on") && case2 != null && case2.equals("on") && case3 != null && case3.equals("on")){
                sb.append(selectArticlesJointureEncheres);
                sb.append(and);
                sb.append("(");
                sb.append(enCours);
                sb.append(")");
                sb.append(or);
                sb.append("(");
                sb.append(enchereByIdUser);
                sb.append(and);
                sb.append(termine);
                sb.append(")");

            }else
            {
                sb.append(selectEncheres);
                sb.append(and);
                sb.append(enchereByIdUser);
                if (noCategorie != 0) {
                    sb.append(and);
                    sb.append(parCate);
                }

                if (case2 != null && case2.equals("on") && case1 == null && case3 == null) {
                    sb.append(and);
                    sb.append(enCours);
                }

                if (case3 != null && case3.equals("on") && case1 == null && case2 == null) {
                    sb.append(and);
                    sb.append(termine);
                }

                if (case2 != null && case2.equals("on") && case3 != null && case3.equals("on")) {
                    sb.append(and);
                    sb.append(debutAvantDateJour);
                }
            }

        }

        sb.toString();
        System.out.println("affihage de la requete exécutée pour test " + sb.toString());

        //on lance la connexion et on exécute la requete
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(sb.toString());
            pstmt.setString(1, (recherche));

            if (case1 != null && case1.equals("on") && case3 != null && case3.equals("on") && case2 == null) {
                pstmt.setInt(2, noUtilisateur);
                if (noCategorie != 0) {
                    pstmt.setInt(3, noCategorie);
                    pstmt.setInt(4, noCategorie);
                }
            }
            if ((case2 != null && case2.equals("on") && case1 == null) || (case3 != null && case3.equals("on")) || (case1 != null && case1.equals("on") && case3 != null && case3.equals("on") && case2 == null)) {
                pstmt.setInt(2, noUtilisateur);
                if (noCategorie != 0) {
                    pstmt.setInt(3, noCategorie);
                }
            }
            if (noCategorie != 0) {
                pstmt.setInt(2, noCategorie);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) != articleEncours.getNoArticle()) {
                    articleEncours = new Article();
                    articleEncours.setNoArticle(rs.getInt("no_article"));
                    articleEncours.setNomArticle(rs.getString("nom_article"));
                    articleEncours.setDescription(rs.getString("description"));
                    articleEncours.setDateDebutEnchere(rs.getDate("date_debut_vente").toLocalDate());
                    articleEncours.setDateFinEnchere(rs.getDate("date_fin_vente").toLocalDate());
                    articleEncours.setPrixInitial(rs.getInt("prix_initial"));
                    articleEncours.setPrixVente(rs.getInt("prix_vente"));

                    Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"));
                    articleEncours.setVendeur(utilisateur);

                    Categorie categorie = new Categorie(rs.getInt("no_categorie"));
                    articleEncours.setCategorie(categorie);

                    listeArticlesFiltres.add(articleEncours);
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_ARTICLES_ECHEC);
            throw businessException;
        }
        return listeArticlesFiltres;
    }


    /**
     * MES VENTES. Cette méthode sélectionne les ventes selon les filtres appliqués.
     * La requete sql est construite "sur mesure" suivant le contenu des paramètres en entrée.
     */
    @Override
    public List<Article> selectVentesParFiltre(String recherche, int noCategorie, String case1,
                                               String case2, String case3, int noUtilisateur) throws BusinessException {
        List<Article> listeVentesFiltrees = new ArrayList<>();
        Article articleEncours = new Article();
        StringBuffer sb = null;

        //On construit la requete sql sur mesure selon les parametres choisis
        if (recherche == null) {
            recherche = "";
        }

        //Choix parmi mes ventes selon critères choisis par l'utilisateur
        sb = new StringBuffer();
        sb.append(selectArticles);
        sb.append(and);
        sb.append(achatByIdUser);

        if (case1 != null && case1.equals("on") && case2 == null && case3 == null) {
            sb.append(and);
            sb.append(enCours);
        }
        if (case2 != null && case2.equals("on") && case1 == null && case3 == null) {
            sb.append(and);
            sb.append(programme);
        }
        if (case3 != null && case3.equals("on") && case1 == null && case2 == null) {
            sb.append(and);
            sb.append(termine);
        }
        if (case1 != null && case1.equals("on") && (case2 != null && case2.equals("on")) && case3 == null) {
            sb.append(and);
            sb.append(finApresDateJour);
        }
        if (case1 != null && case1.equals("on") && (case3 != null && case3.equals("on")) && case2 == null) {
            sb.append(and);
            sb.append(debutAvantDateJour);
        }
        if (case2 != null && case2.equals("on") && (case3 != null && case3.equals("on")) && case1 == null) {
            sb.append(and);
            sb.append(programme);
            sb.append(or);
            sb.append(termine);
        }

        if (noCategorie != 0) {
            sb.append(and);
            sb.append(parCate);
        }

        //Test affichage
        System.out.println("requete SQL exécutée : " + sb.toString());

        //on lance la connexion et on exécute la requete
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(sb.toString());
            pstmt.setString(1, recherche);
            pstmt.setInt(2, noUtilisateur);
            if (noCategorie != 0) {
                pstmt.setInt(3, noCategorie);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) != articleEncours.getNoArticle()) {
                    articleEncours = new Article();
                    articleEncours.setNoArticle(rs.getInt("no_article"));
                    articleEncours.setNomArticle(rs.getString("nom_article"));
                    articleEncours.setDescription(rs.getString("description"));
                    articleEncours.setDateDebutEnchere(rs.getDate("date_debut_vente").toLocalDate());
                    articleEncours.setDateFinEnchere(rs.getDate("date_fin_vente").toLocalDate());
                    articleEncours.setPrixInitial(rs.getInt("prix_initial"));
                    articleEncours.setPrixVente(rs.getInt("prix_vente"));

                    Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"));
                    articleEncours.setVendeur(utilisateur);

                    Categorie categorie = new Categorie(rs.getInt("no_categorie"));
                    articleEncours.setCategorie(categorie);

                    listeVentesFiltrees.add(articleEncours);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_ARTICLES_ECHEC);
            throw businessException;
        }
        return listeVentesFiltrees;
    }

    /**
     * Insere un article en base de données.
     * @param article
     * @throws BusinessException
     */
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
            try (PreparedStatement pstmt = cnx.prepareStatement(INSERT_ARTICLE, PreparedStatement.RETURN_GENERATED_KEYS)) {
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
     * Insère un retrait dans la BDD à l'ajout d'un article
     */
    @Override
    public void insertRetrait(Retrait retrait) throws BusinessException {
        if (retrait == null) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.INSERT_OBJET_NULL);
            throw businessException;
        }
        try (Connection cnx = ConnectionProvider.getConnection()) {
            cnx.setAutoCommit(false);
            try (PreparedStatement pstmt = cnx.prepareStatement(INSERT_RETRAIT)) {
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
        } catch (Exception e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.INSERT_OBJET_ECHEC);
            throw businessException;
        }
    }

    /**
     * Select de tous les articles pouvant être encherris : dont la date de début est antérieure ou égale à la date du jour,
     * dont la date de fin est égale ou postérieure à la date du jour
     */
    @Override
    public List<Article> selectArticlesEncherissables() throws BusinessException {
        List<Article> listeArticlesEncherissables = new ArrayList<>();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_ARTICLES_ENCHERISSABLES);
            Article articleEncherissable = new Article();
            while (rs.next()) {
                if (rs.getInt("no_article") != articleEncherissable.getNoArticle()) {
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
        return listeArticlesEncherissables;
    }

    /**
     * Selectionne un article en fonction de son no_Article
     */
    @Override
    public Article selectArticleById(int idArt) throws BusinessException {
        Article article = new Article();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_ARTICLES_BY_ID);
            pstmt.setInt(1, idArt);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                article.setNoArticle(rs.getInt("no_article"));
                article.setNomArticle(rs.getString("nom_article"));
                article.setDescription(rs.getString("description"));
                article.setDateDebutEnchere(rs.getDate("date_debut_vente").toLocalDate());
                article.setDateFinEnchere(rs.getDate("date_fin_vente").toLocalDate());
                article.setPrixInitial(rs.getInt("prix_initial"));
                article.setPrixVente(rs.getInt("prix_vente"));

                Utilisateur vendeur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"));
                vendeur.setTelephone(rs.getString("telephone"));
                article.setVendeur(vendeur);
                Categorie categorie = new Categorie(rs.getInt("no_categorie"), rs.getString("libelle"));
                article.setCategorie(categorie);

            }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            //gérer erreur SQL
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_UTILISATEUR_ECHEC);
            throw businessException;
        }
        if (article.getNoArticle() == 0) {
            //gérer Article inexistant
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_ARTICLE_INEXISTANT);
            throw businessException;
        }
        return article;
    }

    @Override
    public Article updateArticle(Article article) throws BusinessException {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            try {
                cnx.setAutoCommit(false);
                PreparedStatement pstmt;

                pstmt = cnx.prepareStatement(UPDATE_ARTICLE);
                pstmt.setString(1, article.getNomArticle());
                pstmt.setString(2, article.getDescription());
                pstmt.setDate(3, java.sql.Date.valueOf(article.getDateDebutEnchere()));
                pstmt.setDate(4, java.sql.Date.valueOf(article.getDateFinEnchere()));
                pstmt.setInt(5, article.getPrixInitial());
                pstmt.setInt(6, article.getPrixVente());
                pstmt.setInt(7, article.getVendeur().getNoUtilisateur());
                pstmt.setInt(8, article.getCategorie().getNoCategorie());
                pstmt.setInt(9, article.getNoArticle());
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
        return article;
    }

    @Override
    /**
     * Affiche la liste des articles ouverts à la vente, selon les critères de recherche choisis par l'utilisateur.
     * @param recherche
     * @param noCategorie
     * @return
     * @throws BusinessException
     */
    public List<Article> selectEnModeDeconnecte(String recherche, int noCategorie) throws BusinessException {
        List<Article> listeArticles = new ArrayList<>();
        Article articleEncours = new Article();
        StringBuffer sb = new StringBuffer();
        sb.append(selectArticles);
        sb.append(and);
        sb.append(enCours);

        if (recherche == null) {
            recherche = "";
        }
        if (noCategorie != 0) {
            sb.append(and);
            sb.append(parCate);
        }

        //on lance la connexion et on exécute la requete
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(sb.toString());
            pstmt.setString(1, (recherche));
            if (noCategorie != 0) {
                pstmt.setInt(2, noCategorie);
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(1) != articleEncours.getNoArticle()) {
                    articleEncours = new Article();
                    articleEncours.setNoArticle(rs.getInt("no_article"));
                    articleEncours.setNomArticle(rs.getString("nom_article"));
                    articleEncours.setDescription(rs.getString("description"));
                    articleEncours.setDateDebutEnchere(rs.getDate("date_debut_vente").toLocalDate());
                    articleEncours.setDateFinEnchere(rs.getDate("date_fin_vente").toLocalDate());
                    articleEncours.setPrixInitial(rs.getInt("prix_initial"));
                    articleEncours.setPrixVente(rs.getInt("prix_vente"));

                    Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"), rs.getString("pseudo"));
                    articleEncours.setVendeur(utilisateur);

                    Categorie categorie = new Categorie(rs.getInt("no_categorie"));
                    articleEncours.setCategorie(categorie);

                    listeArticles.add(articleEncours);
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_ARTICLES_ECHEC);
            throw businessException;
        }
        return listeArticles;

    }

    /**
     * Supprime un article par son id.
     * @param id
     * @throws BusinessException
     */
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
}
