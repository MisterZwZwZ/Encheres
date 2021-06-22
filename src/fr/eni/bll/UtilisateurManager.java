package fr.eni.bll;

import fr.eni.BusinessException;
import fr.eni.bo.Article;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.DAOFactory;
import fr.eni.dal.DalException;
import fr.eni.dal.UtilisateurDAO;

import java.rmi.UnexpectedException;

public class UtilisateurManager {

    private final UtilisateurDAO userDAO;

    public UtilisateurManager() {
        this.userDAO = DAOFactory.getUtilisateurDAO();;
    }


    public void insererUtilisateur(String pseudo, String nom, String prenom, String email, String telephone,
                                   String rue, String codePostal, String ville, String motDePasse) throws DalException {
        BusinessException businessException = new BusinessException();

        this.validerDonneesUtilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, 
                businessException);

        // Par défaut admin = false
        Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville,
                motDePasse, 0, false);
        userDAO.insertUser(utilisateur);
    }

    private void validerDonneesUtilisateur(String pseudo, String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville, String motDePasse, BusinessException businessException) {
       // TODO Personnaliser les constantes erreurs pour chaque cas
        if(  pseudo==null || pseudo.trim().length()>30  )
        {
            businessException.ajouterErreur(CodesErreurBll.REGLE_ARTICLE_NOM_ERREUR);
        }
        if( nom==null || nom.trim().length()>30){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ARTICLE_NOM_ERREUR);
        }
        if( prenom==null || prenom.trim().length()>30 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ARTICLE_NOM_ERREUR);
        }
        if(  email==null || pseudo.trim().length()>60 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ARTICLE_NOM_ERREUR);
        }
        if(  rue==null || email.trim().length()>30 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ARTICLE_NOM_ERREUR);
        }
        if(  codePostal==null || codePostal.trim().length()>10 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ARTICLE_NOM_ERREUR);
        }
        if(  ville==null || ville.trim().length()>30 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ARTICLE_NOM_ERREUR);
        }
        if(  motDePasse==null || motDePasse.trim().length()>100 ){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ARTICLE_NOM_ERREUR);
        }
    }
}
