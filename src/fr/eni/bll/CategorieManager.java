package fr.eni.bll;

import fr.eni.BusinessException;
import fr.eni.bo.Categorie;
import fr.eni.dal.CategorieDAOJdbcImpl;
import fr.eni.dal.CategoriesDAO;
import fr.eni.dal.DAOFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategorieManager {

    private final CategoriesDAO categorieDAO;
    Map hmap = new HashMap();

    public CategorieManager() {
        this.categorieDAO = DAOFactory.getCategorieDAO();
    }

    /**
     * cette méthode récupère la liste des Catégories en BDD et renvoie une Map sous forme de clé-valeur.
     * @return
     * @throws BusinessException
     */
    public Map afficherCategories() throws BusinessException {
        List<Categorie> listeDesCategories = new ArrayList<>();
        listeDesCategories = categorieDAO.selectAllCategories();
        for (Categorie c : listeDesCategories
             ) {
            hmap.put(c.getNoCategorie(), c.getLibelle());
        }
        return hmap;
    }


}
