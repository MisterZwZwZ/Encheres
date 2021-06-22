package fr.eni.bll;
/**
 * Les codes disponibles sont entre 20000 et 29999
 */
public abstract class CodesErreurBll {

    /**
     * Echec le pseudo de l'utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_USER_PSEUDO_ERREUR=20000;
    /**
     * Echec le nom de l'utilisateur  ne respecte pas les règles définies
     */
    public static final int REGLE_USER_NOM_ERREUR = 20001;
    /**
     * Echec le prenom de l'utilisateur  ne respecte pas les règles définies
     */
    public static final int REGLE_USER_PRENOM_ERREUR = 20002;
    /**
     * Echec le mail de l'utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_USER_MAIL_ERREUR = 20003;
    /**
     * Echec le numéro de téléphone de l'utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_USER_TEL_ERREUR = 20004;
    /**
     * Echec le code postal de l'utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_USER_RUE_ERREUR = 20005;
    /**
     * Echec la ville de l'utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_USER_CP_ERREUR = 20006;
    /**
     * Echec la rue de l'utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_USER_VILLE_ERREUR = 20007;
    /**
     * Echec le credit de l'utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_USER_CREDIT_ERREUR = 20008;
    /**
     * Echec le statut de l'utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_USER_ADMIN_ERREUR = 20009;
    /**
     * Echec le statut de l'utilisateur ne respecte pas les règles définies
     */
    public static final int REGLE_USER_IDUNIQUE_ERREUR = 20010;
    /**
     * Echec le mot de passe ne respecte pas les règles définies
     */
    public static final int REGLE_USER_MDP_ERREUR = 20011;


    /**
     * Echec le nom de l'article ne respecte pas les règles définies
     */
    public static final int REGLE_ART_NOM_ERREUR = 20031;
    /**
     * Echec la description de l'article ne respecte pas les règles définies
     */
    public static final int REGLE_ART_DESCR_ERREUR = 20032;
    /**
     * Echec la date de début de vente de l'article ne respecte pas les règles définies
     */
    public static final int REGLE_ART_DATEDEBUT_ERREUR = 20033;
    /**
     * Echec la date de fin de vente de l'article  ne respecte pas les règles définies
     */
    public static final int REGLE_ART_DATEFIN_ERREUR = 20034;
    /**
     * Echec le prix initial de l'article  ne respecte pas les règles définies
     */
    public static final int REGLE_ART_PRIXINITIAL_ERREUR = 20035;
    /**
     * Echec le prix final de l'article  ne respecte pas les règles définies
     */
    public static final int REGLE_ART_PRIXFINAL_ERREUR = 20036;


}
