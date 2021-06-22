package fr.eni.bll;

import fr.eni.BusinessException;
import fr.eni.bo.Article;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.DAOFactory;
import fr.eni.dal.DalException;
import fr.eni.dal.UtilisateurDAO;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurManager {

    private final UtilisateurDAO userDAO;

    public UtilisateurManager() {
        this.userDAO = DAOFactory.getUtilisateurDAO();;
    }


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

    private void validerDonneesUtilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville, String motDePasse, BusinessException businessException) {

        // TODO Personnaliser les constantes erreurs pour chaque cas
        if(  pseudo==null || pseudo.trim().length()>30  )
        {
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_PSEUDO_ERREUR);
        }

        //TODO vérifier que le pseudo n'est pas déjà présent en BDD

        if( nom==null || nom.trim().length()>30){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_NOM_ERREUR);
        }
        if( prenom==null || prenom.trim().length()>30 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_PRENOM_ERREUR);
        }
        if(  email==null || pseudo.trim().length()>60 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_MAIL_ERREUR);
        }
        if(  rue==null || email.trim().length()>30 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_RUE_ERREUR);
        }
        if(  codePostal==null || codePostal.trim().length()>10 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_CP_ERREUR);
        }
        if(  ville==null || ville.trim().length()>30 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_VILLE_ERREUR);
        }
        if(  motDePasse==null || motDePasse.trim().length()>100 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_USER_MDP_ERREUR);
        }
    }
}
