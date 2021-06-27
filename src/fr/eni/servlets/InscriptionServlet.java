package fr.eni.servlets;

import fr.eni.BusinessException;
import fr.eni.bll.UtilisateurManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/inscription.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        List<Integer> listeCodesErreur = new ArrayList<>();
        UtilisateurManager um = new UtilisateurManager();

        String pseudoUtilisateur = req.getParameter("pseudo");
        if (pseudoUtilisateur == null || pseudoUtilisateur.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.PSEUDO_UTILISATEUR_OBLIGATOIRE);
        } else {
            req.setAttribute("pseudo", pseudoUtilisateur);
        }

        String nomUtilisateur = req.getParameter("nom");
        if (nomUtilisateur == null || nomUtilisateur.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.NOM_UTILISATEUR_OBLIGATOIRE);
        } if(rechercheCaracSpecial(nomUtilisateur) || rechercheChiffre(nomUtilisateur)){
            listeCodesErreur.add(CodesErreurServlet.CARAC_NON_VALIDES);
        }else{
            req.setAttribute("nom", nomUtilisateur);
        }

        String prenomUtilisateur = req.getParameter("prenom");
        if (prenomUtilisateur == null || prenomUtilisateur.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.PRENOM_UTILISATEUR_OBLIGATOIRE);
        } if(rechercheCaracSpecial(prenomUtilisateur) || rechercheChiffre(prenomUtilisateur)){
            listeCodesErreur.add(CodesErreurServlet.CARAC_NON_VALIDES);
        }else {
            req.setAttribute("prenom", prenomUtilisateur);
        }

        String email = req.getParameter("email");
        if (email == null || email.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.EMAIL_UTILISATEUR_OBLIGATOIRE);
        } else {
            req.setAttribute("email", email);
        }

        String rue = req.getParameter("rue");
        if (rue == null || rue.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.RUE_UTILISATEUR_OBLIGATOIRE);
        } else {
            req.setAttribute("rue", rue);
        }

        String telephone = req.getParameter("telephone");
        req.setAttribute("telephone", telephone);

        String cp = req.getParameter("cp");
        if (cp == null || cp.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.CP_UTILISATEUR_OBLIGATOIRE);
        } else {
            req.setAttribute("cp", cp);

        }
        String ville = req.getParameter("ville");
        if (ville == null || ville.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.VILLE_UTILISATEUR_OBLIGATOIRE);
        } if(rechercheCaracSpecial(ville)||rechercheChiffre(ville)){
            listeCodesErreur.add(CodesErreurServlet.CARAC_NON_VALIDES);
        }else {
            req.setAttribute("ville", ville);

        }
        String password = req.getParameter("password");
        if (password == null || password.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.MDP_UTILISATEUR_OBLIGATOIRE);
        } else {
            req.setAttribute("password", password);

        }
        String passwordConf = req.getParameter("passwordConf");
        if (passwordConf == null || passwordConf.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.MDP_CONF_UTILISATEUR_OBLIGATOIRE);
        } else if (!password.equals(passwordConf)) {
            listeCodesErreur.add(CodesErreurServlet.MDP_IDENTIQUES);
        } else {
            req.setAttribute("passwordConf", passwordConf);
        }

        if (listeCodesErreur.size() > 0) {
            req.setAttribute("listeCodesErreur", listeCodesErreur);
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/inscription.jsp");
            rd.forward(req, resp);
        } else {
            try {
                um.insererUtilisateur(pseudoUtilisateur, nomUtilisateur, prenomUtilisateur, email, telephone, rue, cp, ville, password);
            } catch (BusinessException e) {
                e.printStackTrace();
                req.setAttribute("listeCodesErreur", e.getListeCodesErreur());
            }
        }
        RequestDispatcher rd = req.getRequestDispatcher("accueil");
        rd.forward(req, resp);
    }

    /**
     * Vérifier que le numéro de telephone fourni est dans un format validee
     * @param tel
     * @return
     */
    //TODO ne fonctionne pas
//    public boolean verificationTelephone(String tel){
//        String pattern = "(0|\\+33)[1-9](*[0-9]{2}){4}";
//        Boolean result = tel.matches(pattern);
//        return result;
//    }

    /**
     * Cette méthode vérifie que la saisie de l'utilisateur ne contient pas de caractère spécial.
     * @return true or false
     */
    //TODO "[^\\w]" fonctionne s'il n'y a qu'UN caractère spécial, pas s'il y en a plusieurs
    public Boolean rechercheCaracSpecial(String chaine){
        String p = "[^\\w]" ;
        Boolean result = chaine.matches(p);
        return result;
    }

    /**
     * Cette méthode vérifie que la saisie de l'utilisateur ne contient pas de chiffre.
     * @return true or false
     */
    public Boolean rechercheChiffre(String chaine){
        String p = " *\\d+.* ";
        Boolean result = chaine.matches(" *\\d+.*");
        return result;
    }

}
