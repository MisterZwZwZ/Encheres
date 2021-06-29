package fr.eni.bll;

import fr.eni.BusinessException;
import fr.eni.bo.Article;
import fr.eni.bo.Enchere;
import fr.eni.bo.Utilisateur;
import fr.eni.dal.ArticleDAO;
import fr.eni.dal.DAOFactory;
import fr.eni.dal.EnchereDAO;

import java.time.LocalDate;

public class EnchereManager {

    private final EnchereDAO enchereDAO;
    private ArticleManager articleManager;


    public EnchereManager(){this.enchereDAO = DAOFactory.getEncheresDAO();}

    public void validerEnchere(int montantEnchere, Utilisateur encherisseur, Article article, BusinessException businessException) throws BusinessException {

        //vérifier que le montant saisi est positif et supérieur au prix fixé
        if(montantEnchere <= 0 || montantEnchere <= article.getPrixInitial()){
            businessException.ajouterErreur(CodesErreurBll.REGLE_MONTANT_ENCHERE_ERREUR);
        }
        //Vérifier si le crédit de l'utilisateur est suffisant
        if (montantEnchere < encherisseur.getCredit()){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ENCHERE_CREDIT_ERREUR);
        }
        //vérifier que l'encherisseur n'est pas le propriétaire de l'article
        if(article.getVendeur().getNoUtilisateur() == encherisseur.getNoUtilisateur()){
            businessException.ajouterErreur(CodesErreurBll.REGLE_ENCHERE_UTILISATEUR_ERREUR);
        }
    }

    public void faireUneEnchere(int montantEnchere, Utilisateur utilisateur, int noArticle) throws BusinessException{
        BusinessException businessException = new BusinessException();
        Enchere enchereTrouvee = new Enchere();

        //on récupère l'article sur lequel est faire l'enchere. Ne peut pas être null
        Article articleEncheri = new Article();
        articleEncheri = articleManager.afficherArticleParNo(noArticle);

        //on vérifie la validité de l'enchère
        this.validerEnchere(montantEnchere, utilisateur, articleEncheri, businessException);

        //on vérifie si l'article contient déjà une enchere
        enchereTrouvee = afficherEnchereParNoArticle(noArticle);
            // si l'enchere est null : il n'a pas encore d'enchere.
        if (enchereTrouvee != null){
            //si l'article n'est pas null : il y a déjà une enchère dessus
            //vérifier que l'encherisseur n'est pas le dernier encherisseur
            if(articleEncheri.getVendeur().getNoUtilisateur() == utilisateur.getNoUtilisateur()){
                businessException.ajouterErreur(CodesErreurBll.REGLE_ENCHERE_UTILISATEUR_ERREUR);
            }
            //vérifier que le montant proposé est supérieur à l'enchère précédente
            if(montantEnchere <= enchereTrouvee.getMontantEnchere()){
                businessException.ajouterErreur(CodesErreurBll.REGLE_MONTANT_ENCHERE_ERREUR);
            }
        }

        if(!businessException.hasErreurs()){
            //on créé une enchere s'il n'y a pas d'erreurs
            Enchere enchere = new Enchere(LocalDate.now(), montantEnchere, utilisateur, articleEncheri);
            if (enchereTrouvee == null){
                //il n'y avait pas d'enchere sur cet article. On en créé une nouvelle.
                enchereDAO.creerEnchere(enchere);
            }
            else{
                //une enchere a déjà été faite sur cet article. On modifie l'enchere existante sur cet article
                //TODO : méthode update sur une enchere ou sur un NoArticle ? (car 1 article = 1 enchere en bdd)
                enchereDAO.updateEnchere(enchere);
            }
        }
    }

    public Enchere afficherEnchereParNoArticle(int noArt) throws BusinessException{
        Enchere enchereTrouvée = enchereDAO.selectEnchereByNoArticle(noArt);
        return enchereTrouvée;

    }

    public Enchere afficherEncherePseudoParNoArticle(int noArt) throws BusinessException{
        Enchere enchereTrouvee = enchereDAO.selectEncherePseudoByNoArticle(noArt);
        return enchereTrouvee;

    }

}
