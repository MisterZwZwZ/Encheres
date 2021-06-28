package fr.eni.bll;

import fr.eni.BusinessException;
import fr.eni.bo.Article;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.DAOFactory;
import fr.eni.dal.EnchereDAO;

import java.time.LocalDate;

public class EnchereManager {

    private final EnchereDAO enchereDAO;


    public EnchereManager(){this.enchereDAO = DAOFactory.getEncheresDAO();}

    public void validerEnchere(int montantEnchere, Utilisateur encherisseur, Article article, BusinessException businessException) {
        //vérifier que le montant saisi est positif et supérieur au prix fixé et si le crédit de l'user est suffisant
        if(montantEnchere <= 0 || montantEnchere < article.getPrixInitial()){
            businessException.ajouterErreur(CodesErreurBll.REGLE_MONTANT_ENCHERE_ERREUR);
        }else if (montantEnchere < encherisseur.getCredit()){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ENCHERE_CREDIT_ERREUR);
        }
        //vérifier que l'enchère est bien liée au bon user
        if(article.getVendeur().getNoUtilisateur() != encherisseur.getNoUtilisateur()){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ENCHERE_UTILISATEUR_ERREUR);
        }
        //TODO vérifier que l'enchère est bien liée au bon article
    }

    public void creerEnchere(int montantEnchere, Utilisateur noUtilisateur, Article noArticle) throws BusinessException{
        BusinessException businessException = new BusinessException();
        this.validerEnchere(montantEnchere, noUtilisateur, noArticle, businessException);

        if(!businessException.hasErreurs()){

            Enchere enchere = new Enchere(LocalDate.now(), montantEnchere, noUtilisateur, noArticle);
            enchereDAO.creerEnchere(enchere);
        }
    }
}
