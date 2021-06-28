package fr.eni.servlets;

import fr.eni.BusinessException;
import fr.eni.bll.UtilisateurManager;

import fr.eni.BusinessException;
import fr.eni.bll.UtilisateurManager;
import fr.eni.bo.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {
        "/monprofil",
        "/supp"
})

public class MonProfilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //si on arrive sur la servlet via le bouton supprimer, supression du compte utilisateur +
        // renvoi vers la servlet accueil via l'url deconnexion
        if(request.getServletPath().equals("/supp")) {
            UtilisateurManager um = new UtilisateurManager();
           //on récupére l'id de l'utilisateur connecté
            HttpSession session = request.getSession();
            Utilisateur utilisateurConnecté = (Utilisateur) session.getAttribute("utilisateur");
            int idUtilisateur = utilisateurConnecté.getNoUtilisateur();
            try {
                um.supprimerUtilisateur(idUtilisateur);
                request.getRequestDispatcher("deconnexion").forward(request, response);
            } catch (BusinessException e) {
                e.printStackTrace();
                request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
            }
        }

        request.getRequestDispatcher("WEB-INF/monprofil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        List<Integer> listeCodesErreur = new ArrayList<>();
        UtilisateurManager utilisateurManager = new UtilisateurManager();

        //réqupération des paramettres de l'utilisateur
        HttpSession session = request.getSession(false);
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
        int noUtilisateur = utilisateur.getNoUtilisateur();

        String pseudoUtilisateur = request.getParameter("pseudo");
        if (pseudoUtilisateur == null || pseudoUtilisateur.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.PSEUDO_UTILISATEUR_OBLIGATOIRE);
        } else {
            request.setAttribute("pseudo", pseudoUtilisateur);
        }

        String email = request.getParameter("email");
        if (email == null || email.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.EMAIL_UTILISATEUR_OBLIGATOIRE);
        } else {
            request.setAttribute("email", email);
        }

        String rue = request.getParameter("rue");
        if (rue == null || rue.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.RUE_UTILISATEUR_OBLIGATOIRE);
        } else {
            request.setAttribute("rue", rue);
        }

        //TODO : verif sur le telephone ?
        String telephone = request.getParameter("telephone");
        request.setAttribute("telephone", telephone);


        String cp = request.getParameter("cp");
        if (cp == null || cp.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.CP_UTILISATEUR_OBLIGATOIRE);
        } else {
            request.setAttribute("cp", cp);

        }
        String ville = request.getParameter("ville");
        if (ville == null || ville.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.VILLE_UTILISATEUR_OBLIGATOIRE);
        } if(rechercheCaracSpecial(ville)||rechercheChiffre(ville)){
            listeCodesErreur.add(CodesErreurServlet.CARAC_NON_VALIDES);
        }else {
            request.setAttribute("ville", ville);

        }
        String password = request.getParameter("password");
        if (password == null || password.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.MDP_UTILISATEUR_OBLIGATOIRE);
        } else {
            request.setAttribute("password", password);

        }
        String passwordConf = request.getParameter("passwordConf");
        if (passwordConf == null || passwordConf.trim().equals("")) {
            listeCodesErreur.add(CodesErreurServlet.MDP_CONF_UTILISATEUR_OBLIGATOIRE);
        } else if (!password.equals(passwordConf)) {
            listeCodesErreur.add(CodesErreurServlet.MDP_IDENTIQUES);
        } else {
            request.setAttribute("passwordConf", passwordConf);
        }

        if (listeCodesErreur.size() > 0) {
            request.setAttribute("listeCodesErreur", listeCodesErreur);
            request.getRequestDispatcher("WEB-INF/monprofil.jsp").forward(request, response);
        } else {
            try {
                utilisateurManager.mettreAJourUtilisateur(noUtilisateur, pseudoUtilisateur, email, telephone, rue, cp, ville, password);
                //mise à jour de la session utilisateur avec les informations à jour
                utilisateur = utilisateurManager.retournerUtilisateurParEmail(email);
                session.setAttribute("utilisateur", utilisateur);
                String messageConfirmation = "Votre profil a bien été mis à jour";
                session.setAttribute("message", messageConfirmation);
                request.getRequestDispatcher("WEB-INF/monprofil.jsp").forward(request, response);
            } catch (BusinessException e) {
                e.printStackTrace();
                request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
                request.getRequestDispatcher("WEB-INF/monprofil.jsp").forward(request, response);
            }
        }
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
