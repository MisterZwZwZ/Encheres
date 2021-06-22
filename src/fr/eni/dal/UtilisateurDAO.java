package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDAO {

    void insertUser(Utilisateur utilisateur) throws BusinessException;
    List<String> selectAllPseudo() throws BusinessException;
    List<String> selectAllEmail() throws BusinessException;
    void deleteUser(int id) throws BusinessException;
    Utilisateur selectUserByEmail(String email) throws BusinessException;
}
