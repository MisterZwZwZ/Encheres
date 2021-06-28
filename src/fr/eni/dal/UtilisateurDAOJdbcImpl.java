package fr.eni.dal;

import fr.eni.BusinessException;
import fr.eni.bo.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

    private static final String INSERT_USER = "insert into UTILISATEURS(pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, administrateur) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SELECT_USER_BY_EMAIL = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE email=?";
    private static final String SELECT_USER_BY_PSEUDO = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville FROM UTILISATEURS WHERE pseudo=?";
    private static final String SELECT_ALL_PSEUDO = "SELECT pseudo FROM UTILISATEURS";
    private static final String SELECT_ALL_EMAIL = "SELECT email FROM UTILISATEURS";
    private static final String DELETE_USER = "DELETE FROM UTILISATEURS WHERE no_utilisateur=?";
    private static final String UPDATE_USER = "UPDATE UTILISATEURS SET pseudo= ?, email=?, telephone=?, rue=?, code_postal=?, ville=?, mot_de_passe=? WHERE no_utilisateur=?";

    @Override
    public Utilisateur insertUser(Utilisateur utilisateur) throws BusinessException {
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
                    //TODO utiliser des labels pour identifier les colones ?
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
        return utilisateur;
    }

    @Override
    public List<String> selectAllPseudo() throws BusinessException {
        List<String> listeDesPseudos = new ArrayList<>();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            Statement pstmt = cnx.createStatement();
            ResultSet rs = pstmt.executeQuery(SELECT_ALL_PSEUDO);
            while (rs.next()) {
                listeDesPseudos.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_UTILISATEUR_ECHEC);
            throw businessException;
        }
        return listeDesPseudos;
    }

    @Override
    public List<String> selectAllEmail() throws BusinessException {
        List<String> listeDesEmail = new ArrayList<>();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            Statement pstmt = cnx.createStatement();
            ResultSet rs = pstmt.executeQuery(SELECT_ALL_EMAIL);
            while (rs.next()) {
                listeDesEmail.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_UTILISATEUR_ECHEC);
            throw businessException;
        }
        return listeDesEmail;
    }

    @Override
    public void deleteUser(int id) throws BusinessException {
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(DELETE_USER);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.SUPPRESSION_UTILISATEUR_ERREUR);
            throw businessException;
        }
    }

    /**
     * Retourne un objet Utilisateur correspondant à l'email passé en argument
     * @param email
     * @return Utilisateur
     * @throws BusinessException
     */
    @Override
    public Utilisateur selectUserByEmail(String email) throws BusinessException {
        Utilisateur utilisateur = new Utilisateur();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_USER_BY_EMAIL);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
                    utilisateur.setPseudo(rs.getString("pseudo"));
                    utilisateur.setNom(rs.getString("nom"));
                    utilisateur.setPrenom(rs.getString("prenom"));
                    utilisateur.setEmail(rs.getString("email"));
                    utilisateur.setTelephone(rs.getString("telephone"));
                    utilisateur.setRue(rs.getString("rue"));
                    utilisateur.setCodePostal(rs.getString("code_postal"));
                    utilisateur.setVille(rs.getString("ville"));
                    utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
                    utilisateur.setCredit(rs.getInt("credit"));
                    utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
                }
            rs.close();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            //gérer erreur SQL
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_UTILISATEUR_ECHEC);
            throw businessException;
        }
        if (utilisateur.getNoUtilisateur() == 0) {
            //gérer utilisateur inexistant
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_UTILISATEUR_INEXISTANT);
            throw businessException;
        }

        return utilisateur;
    }


    /**
     * Retourne un objet Utilisateur correspondant au pseudo passé en argument
     * @param pseudo
     * @return Utilisateur
     * @throws BusinessException
     */
    @Override
    public Utilisateur selectUserByPseudo(String pseudo) throws BusinessException {
        Utilisateur utilisateur = new Utilisateur();
        try (Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement pstmt = cnx.prepareStatement(SELECT_USER_BY_PSEUDO);
            pstmt.setString(1, pseudo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
                utilisateur.setPseudo(rs.getString("pseudo"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setTelephone(rs.getString("telephone"));
                utilisateur.setRue(rs.getString("rue"));
                utilisateur.setCodePostal(rs.getString("code_postal"));
                utilisateur.setVille(rs.getString("ville"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            //gérer erreur SQL
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_UTILISATEUR_ECHEC);
            throw businessException;
        }
        if (utilisateur.getNoUtilisateur() == 0) {
            //gérer utilisateur inexistant
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.LECTURE_UTILISATEUR_INEXISTANT);
            throw businessException;
        }

        return utilisateur;
    }

    @Override
    public void updateUser(Utilisateur utilisateur) throws BusinessException{
        try (Connection cnx = ConnectionProvider.getConnection()) {
            try {
                cnx.setAutoCommit(false);
                PreparedStatement pstmt;

                    pstmt = cnx.prepareStatement(UPDATE_USER);
                    //TODO utiliser des labels pour identifier les colones ?
                    pstmt.setString(1, utilisateur.getPseudo());
                    pstmt.setString(2, utilisateur.getEmail());
                    pstmt.setString(3, utilisateur.getTelephone());
                    pstmt.setString(4, utilisateur.getRue());
                    pstmt.setString(5, utilisateur.getCodePostal());
                    pstmt.setString(6, utilisateur.getVille());
                    pstmt.setString(7, utilisateur.getMotDePasse());
                    pstmt.setInt(8, utilisateur.getNoUtilisateur());
                    pstmt.executeUpdate();

                    pstmt.close();

                cnx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                cnx.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            BusinessException businessException = new BusinessException();
            businessException.ajouterErreur(CodesErreurDal.UPDATE_OBJET_ECHEC);
            throw businessException;
        }
    }
}

