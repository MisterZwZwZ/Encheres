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
    private ArticleManager articleManager = new ArticleManager();
    private UtilisateurManager userManager = new UtilisateurManager();

    public EnchereManager(){this.enchereDAO = DAOFactory.getEncheresDAO();}

    public void validerEnchere(int montantEnchere, Utilisateur encherisseur, Article article, BusinessException businessException) throws BusinessException {

        //vérifier que le montant saisi est positif et supérieur au prix fixé
        if(montantEnchere <= 0 || montantEnchere <= article.getPrixInitial()){
            businessException.ajouterErreur(CodesErreurBll.REGLE_MONTANT_ENCHERE_ERREUR);
        }
        //Vérifier si le crédit de l'utilisateur est suffisant
        if (montantEnchere > encherisseur.getCredit()){
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

        //on récupère l'article sur lequel est faite l'enchere. Ne peut pas être null
        Article articleEncheri = new Article();
        articleEncheri = articleManager.afficherArticleParNo(noArticle);

        //on vérifie la validité de l'enchère
        this.validerEnchere(montantEnchere, utilisateur, articleEncheri, businessException);

        //on vérifie si l'article contient déjà une enchere
        enchereTrouvee = afficherEnchereParNoArticle(noArticle);
            // si l'enchere n'a pas de date : elle est nulle - il n'a pas encore d'enchere sur cet article.
        if (enchereTrouvee.getDateEnchere() != null){
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
            if (enchereTrouvee.getDateEnchere() == null){
                //il n'y avait pas d'enchere sur cet article. On en créé une nouvelle.
                enchereDAO.creerEnchere(enchere);
            }
            else{
                //une enchere a déjà été faite sur cet article. On modifie l'enchere existante sur cet article
                //TODO CG: méthode update sur une enchere ou sur un NoArticle ? (car 1 article = 1 enchere en bdd)
                enchereDAO.updateEnchere(enchere);
            }
            //puis déduire le montant de l'enchere du credit de l'encherisseur (utilisateur connecté). Et on enregistre la modif en bdd
            utilisateur.setCredit(utilisateur.getCredit()-montantEnchere);
            userManager.modifierCreditUtilisateur(utilisateur);

            //on  recrédite le dernier encherisseur du montant de l'enchere précédente
            if (enchereTrouvee.getDateEnchere() != null){
                enchereTrouvee.getEncherisseur().setCredit(enchereTrouvee.getEncherisseur().getCredit()+enchereTrouvee.getMontantEnchere());
                userManager.modifierCreditUtilisateur(enchereTrouvee.getEncherisseur());
            }
        }
    }

    public Enchere afficherEnchereParNoArticle(int noArt) throws BusinessException{
        Enchere enchereTrouvee = enchereDAO.selectEnchereByNoArticle(noArt);
        //modifier l'enchere avec un utilisateur complet
        Utilisateur utilisateur = userManager.retournerUtilisateurParId(enchereTrouvee.getEncherisseur().getNoUtilisateur());
        enchereTrouvee.setEncherisseur(utilisateur);
        return enchereTrouvee;

    }
}
