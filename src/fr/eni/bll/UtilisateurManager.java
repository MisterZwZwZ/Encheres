package fr.eni.bll;

import fr.eni.bo.Article;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.DAOFactory;
import fr.eni.dal.DalException;
import fr.eni.dal.UtilisateurDAO;

import java.rmi.UnexpectedException;

public class UtilisateurManager {

    private final UtilisateurDAO userDAO;

    public UtilisateurManager(UtilisateurDAO userDAO) {
        this.userDAO = DAOFactory.getUtilisateurDAO();;
    }


    public void insererUtilisateur(String pseudo, String nom, String prenom, String email, String telephone,
                                   String rue, String codePostal, String ville, String motDePasse) throws DalException {
        // Par défaut admin = false
        Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville,
                motDePasse, 0, false);
        userDAO.insertUser(utilisateur);
    }
}
