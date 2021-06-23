package fr.eni.bll;

import fr.eni.BusinessException;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.DAOFactory;
import fr.eni.dal.UtilisateurDAO;

import java.io.CharConversionException;
import java.util.List;

public class UtilisateurManager {

    private final UtilisateurDAO userDAO;

    public UtilisateurManager() {
        this.userDAO = DAOFactory.getUtilisateurDAO();
    }

    /**
     * Insere un utilisateur en base de données une fois les contrôles effectués sur les différents champs.
     */
    public void insererUtilisateur(String pseudo, String nom, String prenom, String email, String telephone,
                                   String rue, String codePostal, String ville, String motDePasse) throws BusinessException {

        BusinessException businessException = new BusinessException();
        this.validerDonneesUtilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse,
                businessException);

        if(!(businessException.hasErreurs()))
        {
            Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville,
                    motDePasse, 0, false); // Par défaut admin = false. Crédit = 0
            userDAO.insertUser(utilisateur);
        }
        else
        {
            throw businessException;
        }
    }

    /**
     * Vérifie les données saisies par l'utilisateur pour la création d'un compte.
     */
    public void validerDonneesUtilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville, String motDePasse, BusinessException businessException) throws BusinessException {

        if(  pseudo==null || pseudo.trim().length()>30  )
        {
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_PSEUDO_ERREUR);
        }
        //Vérifier si le pseudo n'existe pas déjà en bdd
        List<String> listeDesPseudosEnBase = userDAO.selectAllPseudo();
        if (listeDesPseudosEnBase != null) {
            for (String p :listeDesPseudosEnBase
                 ) {
                if (p.equals(pseudo)){
                    businessException.ajouterErreur(CodesErreurBll.REGLE_USER_PSEUDOUNIQUE_ERREUR);
                }
            }
        }

        //Vérifier que le pseudo ne contient que des caractères alphanumériques
        //vérification du mot de passe
        Boolean alphanum = pseudoValidation(pseudo);
        if (!alphanum) {
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_MDP_ERREUR);
        }

        if( nom==null || nom.trim().length()>30){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_NOM_ERREUR);
        }
        if( prenom==null || prenom.trim().length()>30 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_PRENOM_ERREUR);
        }
        if(  email==null || email.trim().length()>60 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_MAIL_ERREUR);
        }
        //Vérifier si l'email n'existe pas déjà en bdd
        List<String> listeDesEmailEnBase = userDAO.selectAllEmail();
        if (listeDesEmailEnBase != null) {
            for (String e :listeDesEmailEnBase
            ) {
                if (e.equals(email)){
                    businessException.ajouterErreur(CodesErreurBll.REGLE_USER_EMAIL_ERREUR);
                }
            }
        }
        //TODO : vérifier que le téléphone ne contient que des chiffres
        if(  telephone.trim().length()>15 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_TEL_ERREUR);
        }
        if(  rue==null || rue.trim().length()>30 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_RUE_ERREUR);
        }
        if(  codePostal==null || codePostal.trim().length()>10 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_CP_ERREUR);
        }
        if(  ville==null || ville.trim().length()>30 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_VILLE_ERREUR);
        }

        if(  motDePasse==null || motDePasse.trim().length()>100 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_LONGUEUR_MDP_ERREUR);
        }
        //vérification du mot de passe
        Boolean result = passwordValidation(motDePasse);
        if (!result) {
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_MDP_ERREUR);
        }
    }

    /**
     * Cette méthode vérifie que le pseudo choisi par l'utilisateur ne contient que des caractères alphanumériques
     * @param pseudo
     * @return
     */
    public Boolean pseudoValidation(String pseudo) {
        Boolean alphanum =  pseudo.matches("(\\w|-|_)+");;
        return alphanum;
    }

    /**
     * On impose des règles sur le mot de passe
     * (?=.*[0-9]) un chiffre doit apparaître au moins une fois
     * (?=.*[a-z]) une lettre minuscule doit apparaître au moins une fois
     * (?=.*[A-Z]) une lettre majuscule doit apparaître au moins une fois
     * (?=\\S+$) aucun espace n'est autorisé dans toute la chaîne
     * .{8,} au moins 8 caractères
     * @param motDePasse
     */
    public boolean passwordValidation(String motDePasse) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
        Boolean result =  motDePasse.matches(pattern);
        return result;
        }

    /**
     * Retourne un objet Utilisateur correspondant à l'email passé en argument
     * @param email
     * @return Utilisateur
     * @throws BusinessException
     */
    public Utilisateur retournerUtilisateurParEmail(String email) throws BusinessException {
        Utilisateur utilisateurTrouve = userDAO.selectUserByEmail(email);
        return  utilisateurTrouve;
    }

    /**
     * Verifie que l'email saisi par l'utilisateur correspond à celui du profil de connexion demandé
     * @param motDePasse
     * @param utilisateur
     * @return
     */
    public boolean MotDePasseCorrespond (String motDePasse, Utilisateur utilisateur){
        BusinessException businessException = new BusinessException();
        boolean mdpOk = true;
        if(!(motDePasse.equals(utilisateur.getMotDePasse()))) {
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_MDP_INCORRECT);
            mdpOk = false;
        }
        return mdpOk;
    }

    /**
     * Retourne un objet utilisateur correspond au pseudo passé en arugment.
     * @param pseudo
     * @throws BusinessException
     */
    public Utilisateur retournerUtilisateurParPseudo(String pseudo) throws BusinessException {
        BusinessException businessException = new BusinessException();
        if(  pseudo==null || pseudo.trim().length()>30  )
        {
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_PSEUDO_ERREUR);
        }
        //Vérifier si le pseudo n'existe pas déjà en bdd
        List<String> listeDesPseudosEnBase = userDAO.selectAllPseudo();
        if (listeDesPseudosEnBase != null) {
            for (String p :listeDesPseudosEnBase
            ) {
                if (!p.equals(pseudo)){
                    businessException.ajouterErreur(CodesErreurBll.PSEUDO_INEXISTANT);
                }
            }
        }
        Utilisateur utilisateurTrouve = userDAO.selectUserByPseudo(pseudo);
        return utilisateurTrouve;
    }

    //TODO : relier cette méthode à l'IHM
    public void supprimerUtilisateur(int id) throws BusinessException {
        userDAO.deleteUser(id);
    }
}
