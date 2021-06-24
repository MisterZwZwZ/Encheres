package fr.eni.dal;

public abstract class DAOFactory {
    private static UtilisateurDAO utilisateurDao;

    public static UtilisateurDAO getUtilisateurDAO() {
        return new UtilisateurDAOJdbcImpl();
    }

    public static CategoriesDAO getCategorieDAO() {
        return new CategorieDAOJdbcImpl();
    }

    public static ArticleDAO getArticleDAO() {
        return new ArticleDAOJdbcImpl();
    }

    public static EnchereDAO getEncheresDAO() {

        return new EnchereDAOJdbcImpl();
    }

}
