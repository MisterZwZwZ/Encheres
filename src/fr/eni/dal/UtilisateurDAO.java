package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Utilisateur;

public interface UtilisateurDAO {

    void insertUser(Utilisateur utilisateur) throws DalException;
    Utilisateur selectUserByEmail(String email) throws BusinessException, BusinessException;
}
