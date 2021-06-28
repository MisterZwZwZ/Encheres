package fr.eni.servlets;
/**
 * Les codes disponibles sont entre 30000 et 39999
 */
public abstract class CodesErreurServlet {

    /**
     * Format id incorrect
     */
    public static final int FORMAT_ID_ARTICLE_ERREUR=30000;

    /**
     * Format donnees utilisateur incorrectes
     */
    public static final Integer PSEUDO_UTILISATEUR_OBLIGATOIRE = 30001;
    public static final Integer NOM_UTILISATEUR_OBLIGATOIRE = 30002;
    public static final Integer PRENOM_UTILISATEUR_OBLIGATOIRE = 30003;
    public static final Integer RUE_UTILISATEUR_OBLIGATOIRE = 30004;
    public static final Integer CP_UTILISATEUR_OBLIGATOIRE = 30005;
    public static final Integer VILLE_UTILISATEUR_OBLIGATOIRE = 30006;
    public static final Integer MDP_UTILISATEUR_OBLIGATOIRE = 30007;
    public static final Integer MDP_CONF_UTILISATEUR_OBLIGATOIRE = 30008;
    public static final Integer MDP_IDENTIQUES = 30009;
    public static final Integer EMAIL_UTILISATEUR_OBLIGATOIRE = 30010;
    public static final Integer CARAC_NON_VALIDES = 30011;

    /**
     * Format donnees articles incorrectes
     */
    public static final Integer NOM_ARTICLE_OBLIGATOIRE = 30031;
    public static final Integer DESCR_ARTICLE_OBLIGATOIRE = 30032;
    public static final Integer DATE_DEBUT_ENCHERE_ARTICLE_ERREUR = 30033;
    public static final Integer DATE_FIN_ENCHERE_ARTICLE_ERREUR = 30034;
    public static final Integer PRIX_INITIAL_ARTICLE_ERREUR = 30035;
    public static final Integer PRIX_VENTE_ARTICLE_ERREUR = 30036;
    public static final Integer CATEGORIE_ARTICLE_ERREUR = 30037;

    /**
     * Adresse retrait incorrecte
     */
    public static final Integer RUE_RETRAIT_ERREUR = 30040;
    public static final Integer CODE_POSTAL_RETRAIT_ERREUR = 30041;
    public static final Integer VILLE_RETRAIT_ERREUR = 30042;

    /**
     * Enchere incorrecte
     */
    public static final Integer ENCHERE_MONTANT_ERREUR = 30043;
    public static final Integer ENCHERE_DATE_ERREUR = 30044;
    public static final Integer ENCHERE_UTILISATEUR_ERREUR = 30045;
    public static final Integer ENCHERE_CREDIT_ERREUR = 30046;


    /**
     * Mot de passe connexion saisis incorrect
     */
    public static final Integer MDP_INCORRECT = 30012;
    public static final Integer UTILISATEUR_INEXISTANT = 30013;

    //

}
