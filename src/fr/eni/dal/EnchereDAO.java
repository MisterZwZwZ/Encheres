package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Enchere;

import java.sql.SQLException;
import java.time.LocalDate;

public interface EnchereDAO {
    void creerEnchere(Enchere enchere) throws BusinessException;
    void updateEnchere(Enchere enchere) throws BusinessException;
    Enchere selectEnchereByNoUser(int noUtilisateur) throws BusinessException;

    Enchere selectEnchereByDate(LocalDate dateDebutEnchere) throws BusinessException;

    Enchere selectEnchereByNoArticle(int noArt) throws BusinessException;
    Enchere selectEncherePseudoByNoArticle(int noArt) throws BusinessException;
}
