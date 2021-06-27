package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Categorie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAOJdbcImpl implements CategoriesDAO {

    private static final String SELECT_CATEGORIES = "SELECT no_categorie, libelle FROM CATEGORIES";


    public List<Categorie> selectAllCategories() throws BusinessException {
        List<Categorie> listeCategories = new ArrayList<>();
        try(Connection cnx = ConnectionProvider.getConnection()) {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT_CATEGORIES);
            Categorie cat = new Categorie();
            while(rs.next()){
                if(rs.getInt("no_categorie") != cat.getNoCategorie()){
                    cat = new Categorie();
                    cat.setNoCategorie(rs.getInt("no_categorie"));
                    cat.setLibelle(rs.getString("libelle"));
                    listeCategories.add(cat);
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            //TODO CG gestion erreur personnalisée
            throw new BusinessException();
        }
        return listeCategories;
    }


}
