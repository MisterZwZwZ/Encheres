package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

    private static final String INSERT_USER = "insert into UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, administrateur) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    @Override
    public void insertUser(Utilisateur utilisateur) throws BusinessException {
        if (utilisateur == null) {
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.INSERT_OBJET_NULL);
            throw businessException;
        }

        try (Connection cnx = ConnectionProvider.getConnection()) {
            try {
                cnx.setAutoCommit(false);
                PreparedStatement pstmt;
                ResultSet rs;
                if (utilisateur.getNoUtilisateur() == 0) {
                    pstmt = cnx.prepareStatement(INSERT_USER, PreparedStatement.RETURN_GENERATED_KEYS);
                    pstmt.setString(1, utilisateur.getPseudo());
                    pstmt.setString(2, utilisateur.getNom());
                    pstmt.setString(3, utilisateur.getPrenom());
                    pstmt.setString(4, utilisateur.getEmail());
                    if (utilisateur.getTelephone() != null){
                        pstmt.setString(5, utilisateur.getTelephone());
                    }
                    pstmt.setString(6, utilisateur.getRue());
                    pstmt.setString(7, utilisateur.getCodePostal());
                    pstmt.setString(8, utilisateur.getVille());
                    pstmt.setString(9, utilisateur.getMotDePasse());
                    pstmt.setBoolean(10, utilisateur.isAdministrateur());
                    pstmt.executeUpdate();

                    rs = pstmt.getGeneratedKeys();
                    if (rs.next()) {
                        utilisateur.setNoUtilisateur(rs.getInt(1));
                    }
                    rs.close();
                    pstmt.close();
                }

                cnx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                cnx.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.INSERT_OBJET_ECHEC);
            throw businessException;
        }
    }
}
