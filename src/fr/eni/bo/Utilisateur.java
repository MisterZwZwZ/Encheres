package fr.eni.bo;

public class Utilisateur {
    private int noUtilisateur;
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String rue;
    private String codePostal;
    private String ville;
    private String motDePasse;
    private int credit;
    private boolean administrateur;

    //TODO relation avec article : un utilisateur a une liste d'articles ?

    public Utilisateur() {
    }

}
