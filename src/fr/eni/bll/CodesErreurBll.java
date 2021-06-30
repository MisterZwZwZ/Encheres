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
    public static final int REGLE_USER_PSEUDOUNIQUE_ERREUR = 20010;
    /**
     * Echec le mail ne respecte pas les règles définies
     */
    public static final int REGLE_USER_EMAIL_ERREUR = 20011;

    /**
     * Echec le mot de passe ne respecte pas les règles définies (longueur max)
     */
    public static final int REGLE_USER_LONGUEUR_MDP_ERREUR = 20012;

    /**
     * Echec le mot de passe ne respecte pas les règles définies (complexité)
     */
    public static final int REGLE_USER_MDP_ERREUR = 20013;
    /**
     * Echec le pseudo ne correspond à aucun compte en bdd
     */
    public static final int PSEUDO_INEXISTANT = 20018;

    /**
     *
     */
    public static final int MOT_DE_PASSE_INCORRECT = 20014;

    /**
     * Echec aucun utilisateur identifié sur la session (no_utilisateur inexistant)
     */
    public static final int NO_UTILISATEUR_INEXISTANT = 20016;

    /**
     * les caractères saisis ne sont pas valides dans le champ concernés. Par exemple : le nom et le prénom
     * ne peuvent pas contenir de caractères spéciaux ou de chiffre.
     */
    public static final int CARACTERES_NON_VALIDES = 20017;

    /**
     * ID utilisateur non valide
     */
    public static final int ID_INEXISTANT = 20015;


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
    /**
     * La catégorie de l'article n'est pas choisie
     */
    public static final int REGLE_ART_CATEGORIE_ERREUR = 20037;

    /**
     * La date de l'enchère est dépassée
     */
    public static final int REGLE_DATE_ENCHERE_ERREUR = 20038;

    /**
     * Le montant de l'enchere est invalide
     */
    public static final int REGLE_MONTANT_ENCHERE_ERREUR = 20039;

    /**
     * Encherisseur introuvable ou n'est pas connecté
     */
    public static final int REGLE_ENCHERE_UTILISATEUR_ERREUR = 20040;

    /**
     * Crédit encherisseur insuffisant
     */
    public static final int REGLE_ENCHERE_CREDIT_ERREUR = 20041;

    /**
     * Erreur article sur enchere
     */
    public static final int REGLE_ENCHERE_ARTICLE_ERREUR = 20042;

}
