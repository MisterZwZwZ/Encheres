package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Enchere;

import java.sql.SQLException;

public interface EnchereDAO {
    void creerEnchere(Enchere enchere) throws BusinessException;

    Enchere selectEnchereByNoUser(int noUtilisateur) throws BusinessException;
}
