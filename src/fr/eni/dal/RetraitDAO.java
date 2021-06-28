package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Retrait;

public interface RetraitDAO {
    Retrait selectRetraitById(int idArt) throws BusinessException;
}
