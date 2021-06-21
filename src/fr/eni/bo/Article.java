package fr.eni.bo;

import javax.swing.*;
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
    private Utilisateur vendeur;
    private Retrait retrait; // TODO modifier la structure de la bdd avec adresse ?

    public Article() {
    }

}
