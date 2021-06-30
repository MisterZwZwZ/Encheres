package fr.eni.bll.util;
import fr.eni.bll.UtilisateurManager;

public class Utilitaire {

    //Vérifications en servlet.

    /**
     * Cette méthode vérifie que la saisie de l'utilisateur ne contient pas de caractère spécial.
     * @return true or false
     */
    public Boolean rechercheCaracSpecial(String chaine){
        String p = "^([a-zA-Z0-9]+)([%]{0,1}|[\\*]{0,1})([a-zA-Z0-9]*)$" ;
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


    //Vérifications en BLL.

    /**
     * Cette méthode vérifie que le pseudo choisi par l'utilisateur ne contient que des caractères alphanumériques
     * @param pseudo
     * @return
     */
    public Boolean pseudoValidation(String pseudo) {
        Boolean alphanum =  pseudo.matches("(\\w|-|_)+");;
        return alphanum;
    }

    /**
     * On impose des règles sur le mot de passe
     * (?=.*[0-9]) un chiffre doit apparaître au moins une fois
     * (?=.*[a-z]) une lettre minuscule doit apparaître au moins une fois
     * (?=.*[A-Z]) une lettre majuscule doit apparaître au moins une fois
     * (?=\\S+$) aucun espace n'est autorisé dans toute la chaîne
     * .{8,} au moins 8 caractères
     * @param motDePasse
     */
    public boolean passwordValidation(String motDePasse) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
        Boolean result =  motDePasse.matches(pattern);
        return result;
    }

    /**
     * On impose des règles sur l'email : structure a@a.a
     * @param email
     */
    public boolean emailValidation(String email) {
        String pattern = "^[a-zA-Z0-9\\p{P}]*@[a-zA-Z0-9\\p{P}]*\\.[a-zA-Z0-9\\p{P}]*$";
        Boolean result =  email.matches(pattern);
        return result;
    }

    /**
     * On impose des règles sur le numéro de téléphone : format +nombre. "^\\+[\\p{N}]*$"
     * @param telephone
     */
    public boolean telValidation(String telephone) {
        String pattern = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";
        Boolean result =  telephone.matches(pattern);
        return result;
    }

    /**
     * Cette méthode vérifie que le nom et le prénom saisis ne contiennent que des lettres (tirets et ' acceptés)
     * @param chaine
     * @return
     */
    public boolean nomValidation(String chaine) {
        String pattern = "^[a-zA-Z\\-\\é\\è\\ê\\ï\\']*";
        boolean result =  chaine.matches(pattern);
        return result;
    }

    /**
     * Cette méthode vérifie que la ville ne contient que des lettres (accents, tirets et ' acceptés)
     * @param ville
     * @return
     */
    public boolean villeValidation(String ville) {
        String pattern = "^[a-zA-Z\\-\\é\\è\\ê\\ï\\']*"; // "^[a-zA-Z\\-\\']*$";
        boolean result =  ville.matches(pattern);
        return result;
    }

}
