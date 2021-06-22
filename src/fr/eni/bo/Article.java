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

    public Article() {
    }

    public Article(String nomArticle, String description, LocalDate dateDebutEnchere, LocalDate dateFinEnchere, int prixInitial, int prixVente, Categorie categorie, Utilisateur vendeur) {
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEnchere = dateDebutEnchere;
        this.dateFinEnchere = dateFinEnchere;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.categorie = categorie;
        this.vendeur = vendeur;
    }

    public Article(int noArticle, String nomArticle, String description, LocalDate dateDebutEnchere, LocalDate dateFinEnchere, int prixInitial, int prixVente, Categorie categorie, Utilisateur vendeur) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEnchere = dateDebutEnchere;
        this.dateFinEnchere = dateFinEnchere;
        this.prixInitial = prixInitial;
        this.prixVente = prixVente;
        this.categorie = categorie;
        this.vendeur = vendeur;
    }

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebutEnchere() {
        return dateDebutEnchere;
    }

    public void setDateDebutEnchere(LocalDate dateDebutEnchere) {
        this.dateDebutEnchere = dateDebutEnchere;
    }

    public LocalDate getDateFinEnchere() {
        return dateFinEnchere;
    }

    public void setDateFinEnchere(LocalDate dateFinEnchere) {
        this.dateFinEnchere = dateFinEnchere;
    }

    public int getPrixInitial() {
        return prixInitial;
    }

    public void setPrixInitial(int prixInitial) {
        this.prixInitial = prixInitial;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Utilisateur getVendeur() {
        return vendeur;
    }

    public void setVendeur(Utilisateur vendeur) {
        this.vendeur = vendeur;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Article{");
        sb.append("noArticle=").append(noArticle);
        sb.append(", nomArticle='").append(nomArticle).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", dateDebutEnchere=").append(dateDebutEnchere);
        sb.append(", dateFinEnchere=").append(dateFinEnchere);
        sb.append(", prixInitial=").append(prixInitial);
        sb.append(", prixVente=").append(prixVente);
        sb.append(", categorie=").append(categorie);
        sb.append(", vendeur=").append(vendeur);
        sb.append('}');
        return sb.toString();
    }
}
