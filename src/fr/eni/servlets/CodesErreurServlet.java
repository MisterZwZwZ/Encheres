package fr.eni.servlets;
/**
 * Les codes disponibles sont entre 30000 et 39999
 */
public abstract class CodesErreurServlet {

    /**
     * Format id liste course incorrect
     */
    public static final int FORMAT_ID_ARTICLE_ERREUR=30000;
    /**
     * Format article incorrect
     */
    public static final Integer NOM_ARTICLE_OBLIGATOIRE = 30001;
    public static final Integer DESCR_ARTICLE_OBLIGATOIRE = 30002;
    public static final Integer DATE_DEBUT_ARTICLE_ERREUR = 30003;
    public static final Integer DATE_FIN_ARTICLE_ERREUR = 30004;
    public static final Integer PRIX_INITIAL_ARTICLE_ERREUR = 30005;
    public static final Integer PRIX_VENTE_ARTICLE_ERREUR = 30006;

}
