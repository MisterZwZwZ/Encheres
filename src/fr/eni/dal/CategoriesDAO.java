package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Categorie;

import java.util.List;

public interface CategoriesDAO {

    List<Categorie> selectAllCategories() throws BusinessException;

}
