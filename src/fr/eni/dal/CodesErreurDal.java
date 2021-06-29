package fr.eni.dal;

/**
 * Les codes disponibles sont entre 10000 et 19999
 */
public abstract class CodesErreurDal {

        /**
         * Echec général quand tentative d'ajouter un objet null
         */
        public static final int INSERT_OBJET_NULL=10000;

        /**
         * Echec général quand erreur non gérée à l'insertion
         */
        public static final int INSERT_OBJET_ECHEC=10001;

        /**
         * Echec général quand erreur non gérée à l'insertion
         */
        public static final int UPDATE_OBJET_ECHEC=10004;


        //TODO personnaliser les erreurs DAL

        // codes erreurs articles
        /**
         * Echec de la lecture des articles
         */
        public static final int LECTURE_ARTICLES_ECHEC = 10032;
        /**
         * Echec de la lecture des articles par filtres
         */
        public static final int LECTURE_ARTICLES_FILTRES_ECHEC = 10033;
        /**
         * Liste d'articles inexistante
         */
        public static final int LECTURE_ARTICLE_INEXISTANT = 10034;
        /**
         * Erreur à la suppression d'un article
         */
        public static final int SUPPRESSION_ARTICLE_ERREUR = 10035;


        // codes erreurs utilisateurs
        /**
         * utilisateur inexistant inexistante
         */
        public static final int LECTURE_UTILISATEUR_INEXISTANT = 10002;

        /**
         * Erreur lecture utilisateur en BDD
         */
        public static final int LECTURE_UTILISATEUR_ECHEC = 10003;

        /**
         * Erreur à la suppression d'un utilisateur
         */
        public static final int SUPPRESSION_UTILISATEUR_ERREUR = 10006;

        /**
         * Erreur lecture enchère
         */
        public static final int LECTURE_ENCHERE_ECHEC = 10007;


        // codes erreurs Retraits
        /**
         * Erreur lecture Retrait
         */
        public static final int LECTURE_RETRAIT_ECHEC = 10008;
}
