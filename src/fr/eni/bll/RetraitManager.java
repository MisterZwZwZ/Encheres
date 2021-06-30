package fr.eni.bll;

import fr.eni.BusinessException;
import fr.eni.bll.util.Utilitaire;
import fr.eni.bo.Retrait;
import fr.eni.dal.DAOFactory;
import fr.eni.dal.RetraitDAO;
import fr.eni.dal.UtilisateurDAO;

public class RetraitManager {
    private final RetraitDAO retraitDAO;
    private Utilitaire util = new Utilitaire();

    public RetraitManager() {
        this.retraitDAO = DAOFactory.getRetraitDAO();
    }

    public Retrait afficherRetraitParNoArticle(int noArt) throws BusinessException {
        Retrait retrait = retraitDAO.selectRetraitById(noArt);
        return retrait;
    }

    public void modifierRetrait(Retrait retrait) throws BusinessException {
        retraitDAO.updateRetrait(retrait);
    }


}
