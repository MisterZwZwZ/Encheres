package fr.eni.bo;

import java.time.LocalDate;

public class Article {
    private int noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEnchere;
    private LocalDate dateFinEnchere;
    private int prixInitial;
    private int prixVente;
    private Categorie categorie;
    private Retrait retrait;
    private Utilisateur vendeur;

    //TODO relations avec utilisateur : 1 article à 1 vendeur

    public Article() {
    }

}
