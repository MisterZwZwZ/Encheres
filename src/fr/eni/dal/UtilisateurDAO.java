package fr.eni.dal;

import fr.eni.bo.Utilisateur;

public interface UtilisateurDAO {

    void insertUser(Utilisateur utilisateur) throws BusinessException;
    void insertUser(Utilisateur utilisateur) throws DalException;
    Utilisateur selectUserByEmail(String email) throws BusinessException, BusinessException;
}
