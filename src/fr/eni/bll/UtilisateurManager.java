package fr.eni.bll;

import fr.eni.BusinessException;
import fr.eni.bll.util.Utilitaire;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.DAOFactory;
import fr.eni.dal.UtilisateurDAO;

import java.io.CharConversionException;
import java.util.List;

public class UtilisateurManager {

    private final UtilisateurDAO userDAO;
    private Utilitaire util;

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

        if(  pseudo==null || pseudo.trim().length()>30 )
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
        Boolean alphanum = util.pseudoValidation(pseudo);
        if (!alphanum) {
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_MDP_ERREUR);
        }

        //vérifications sur le nom et prénom
        if( nom==null || nom.trim().length()>30){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_NOM_ERREUR);
        }
        if( prenom==null || prenom.trim().length()>30 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_PRENOM_ERREUR);
        }

        Boolean nomValid = nomValidation(nom);
        if (!nomValid) {
            businessException.ajouterErreur(CodesErreurBll.CARACTERES_NON_VALIDES);
        }
        Boolean prenomValid = nomValidation(prenom);
        if (!prenomValid) {
            businessException.ajouterErreur(CodesErreurBll.CARACTERES_NON_VALIDES);
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
        //Vérifier le format de l'email
        Boolean emailvalid = util.emailValidation(email);
        if (!emailvalid) {
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_EMAIL_ERREUR);
        }

        if(  telephone.trim().length()>15 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_TEL_ERREUR);
        }
        //Vérifier le format du numéro de telephone
        Boolean telvalid = util.telValidation(telephone);
        if (!telvalid) {
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_TEL_ERREUR);
        }

        if(  rue==null || rue.trim().length()>30 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_RUE_ERREUR);
        }
        if(  codePostal==null || codePostal.trim().length()>10 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_CP_ERREUR);
        }

        //vérification sur la ville
        if(  ville==null || ville.trim().length()>30 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_VILLE_ERREUR);
        }
        Boolean villeValid = nomValidation(ville);
        if (!prenomValid) {
            businessException.ajouterErreur(CodesErreurBll.CARACTERES_NON_VALIDES);
        }

        if(  motDePasse==null || motDePasse.trim().length()>100 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_LONGUEUR_MDP_ERREUR);
        }
        //vérification du mot de passe
        Boolean result = util.passwordValidation(motDePasse);
        if (!result) {
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_MDP_ERREUR);
        }
    }

    /**
     * Cette méthode vérifie que le nom et le prénom saisis ne contiennent que des lettres (tirets acceptés)
     * @param chaine
     * @return
     */
    private Boolean nomValidation(String chaine) {
        String pattern = "^[a-zA-Z\\-]*$";
        Boolean result =  chaine.matches(pattern);
        return result;
    }

    /**
     * Cette méthode vérifie que la ville ne contient que des lettres (tirets et ' acceptés)
     * @param ville
     * @return
     */
    private Boolean villeValidation(String ville) {
        String pattern = "^[a-zA-Z\\-\\']*$";
        Boolean result =  ville.matches(pattern);
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
            businessException.ajouterErreur(CodesErreurBll.MOT_DE_PASSE_INCORRECT);
            mdpOk = false;
        }
        return mdpOk;
    }

    /**
     * Retourne un objet utilisateur correspond au pseudo passé en argument.
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

    /**
     * Supprime un compte utilisateur en base de données, à partir de son id
     * @param id
     * @throws BusinessException
     */
    public void supprimerUtilisateur(int id) throws BusinessException {
        BusinessException businessException = new BusinessException();
        if(  id==0  )
        {
            businessException.ajouterErreur(CodesErreurBll.ID_INEXISTANT);
        }
        if(!(businessException.hasErreurs()))
        {
            userDAO.deleteUser(id);
        }
        else
        {
            throw businessException;
        }

    }

    public void mettreAJourUtilisateur(int no_utilisateur, String pseudoUtilisateur, String email, String telephone, String rue, String cp, String ville, String password) throws BusinessException{
        BusinessException businessException = new BusinessException();
        // verif si no_utilisateur non null
        if(no_utilisateur == 0){
            businessException.ajouterErreur(CodesErreurBll.NO_UTILISATEUR_INEXISTANT);
        }
        //verif de la validité des autres champs
        this.validerNouvellesDonneesUtilisateur(pseudoUtilisateur, email, telephone, rue, cp, ville, password,
                businessException);
        if(!(businessException.hasErreurs())) {
            Utilisateur utilisateur = new Utilisateur(no_utilisateur, pseudoUtilisateur, email, telephone, rue, cp, ville, password);
            userDAO.updateUser(utilisateur);
        }else
        {
            throw businessException;
        }
    }

    /**
     * Vérifie les données saisies par l'utilisateur pour la modification d'un compte.
     */
    public void validerNouvellesDonneesUtilisateur(String pseudo, String email, String telephone, String rue, String codePostal, String ville, String motDePasse, BusinessException businessException) throws BusinessException {

        if(  pseudo==null || pseudo.trim().length()>30 )
        {
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_PSEUDO_ERREUR);
        }

        //Vérifier que le pseudo ne contient que des caractères alphanumériques
        Boolean alphanum = util.pseudoValidation(pseudo);
        if (!alphanum) {
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_MDP_ERREUR);
        }

        if(  email==null || email.trim().length()>60 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_MAIL_ERREUR);
        }

        //TODO : vérifier que le téléphone ne contient que des chiffres (utiliser la méthode util. )
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
        Boolean result = util.passwordValidation(motDePasse);
        if (!result) {
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_MDP_ERREUR);
        }
    }
}
