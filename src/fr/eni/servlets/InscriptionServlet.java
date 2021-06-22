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

@WebServlet("/inscription")
public class InscriptionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.getRequestDispatcher("WEB-INF/inscription.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Integer> listeCodesErreur=new ArrayList<>();
        UtilisateurManager um = new UtilisateurManager();

        String pseudoUtilisateur = req.getParameter("pseudo");

        if(pseudoUtilisateur==null || pseudoUtilisateur.trim().equals(""))
        {
            listeCodesErreur.add(CodesErreurServlet.PSEUDO_UTILISATEUR_OBLIGATOIRE);
        }else{
            req.setAttribute("pseudo", pseudoUtilisateur);
        }
        String nomUtilisateur = req.getParameter("nom");
        if(nomUtilisateur==null || nomUtilisateur.trim().equals(""))
        {
            listeCodesErreur.add(CodesErreurServlet.NOM_UTILISATEUR_OBLIGATOIRE);
        }else{
            req.setAttribute("nom", nomUtilisateur);
        }
        String prenomUtilisateur = req.getParameter("prenom");
        if(prenomUtilisateur==null || prenomUtilisateur.trim().equals(""))
        {
            listeCodesErreur.add(CodesErreurServlet.PRENOM_UTILISATEUR_OBLIGATOIRE);
        }else {
            req.setAttribute("prenom", prenomUtilisateur);
        }
        String email = req.getParameter("email");
        if(email==null || email.trim().equals(""))
        {
            listeCodesErreur.add(CodesErreurServlet.EMAIL_UTILISATEUR_OBLIGATOIRE);
        }else{
            req.setAttribute("email", email);
        }
        String rue = req.getParameter("email");
        if(rue==null || rue.trim().equals(""))
        {
            listeCodesErreur.add(CodesErreurServlet.RUE_UTILISATEUR_OBLIGATOIRE);
        }else{
            req.setAttribute("rue", rue);
        }
        //TODO : verif sur le telephone ?
        String telephone = req.getParameter("telephone");
        req.setAttribute("email", email);

        String cp = req.getParameter("cp");
        if(cp==null || cp.trim().equals(""))
        {
            listeCodesErreur.add(CodesErreurServlet.CP_UTILISATEUR_OBLIGATOIRE);
        }else{
            req.setAttribute("cp", cp);
        }
        String ville = req.getParameter("cp");
        if(ville==null || ville.trim().equals(""))
        {
            listeCodesErreur.add(CodesErreurServlet.VILLE_UTILISATEUR_OBLIGATOIRE);
        }else{
            req.setAttribute("ville", ville);
        }
        String password = req.getParameter("password");
        if(password==null || password.trim().equals(""))
        {
            listeCodesErreur.add(CodesErreurServlet.MDP_UTILISATEUR_OBLIGATOIRE);
        }else{
            req.setAttribute("password", password);
        }
        String passwordConf = req.getParameter("passwordConf");
        if(passwordConf==null || passwordConf.trim().equals(""))
        {
            listeCodesErreur.add(CodesErreurServlet.MDP_CONF_UTILISATEUR_OBLIGATOIRE);
        }
        else if (!password.equals(passwordConf)){
            listeCodesErreur.add(CodesErreurServlet.MDP_IDENTIQUES);
        }else{
            req.setAttribute("passwordConf", passwordConf);
        }

        if(listeCodesErreur.size()>0)
        {
            req.setAttribute("listeCodesErreur",listeCodesErreur);
            System.out.println(listeCodesErreur);
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/inscription.jsp");
            rd.forward(req, resp);
        }
        else {
            try {
                um.insererUtilisateur(pseudoUtilisateur, nomUtilisateur, prenomUtilisateur, email, telephone, rue, cp, ville, password);
            } catch (BusinessException e) {
                e.printStackTrace();
                req.setAttribute("listeCodesErreur", e.getListeCodesErreur());
            }
        }

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/accueil.jsp");
        rd.forward(req, resp);
    }
}
