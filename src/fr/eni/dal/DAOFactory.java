package fr.eni.dal;

public abstract class DAOFactory {
    private static UtilisateurDAO utilisateurDao;

    public static UtilisateurDAO getUtilisateurDAO() {
        return new UtilisateurDAOJdbcImpl();
    }

    public static CategoriesDAO getCategorieDAO() {
        return new CategoriesDAOJdbcImpl();
    }

    public static ArticleDAO getArticleVenduDAO() {
        return new ArticleDAOJdbcImpl();
    }

    public static EncheresDAO getEncheresDAO() {

        return new EncheresDAOJdbcImpl();
    }

    public static RetraitDAO getRetraitDAO() {

        return new RetraitDAOJdbcImpl();
    }

}
