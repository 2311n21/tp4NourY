/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.emsi.tpbanquenoury.jsf;

import com.emsi.tpbanquenoury.entity.CompteBancaire;
import com.emsi.tpbanquenoury.service.GestionnaireCompte;
import com.emsi.tpbanquenoury.util.Util;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 *
 * @author mac
 *
 * créer un nouveau compte bancaire en donnant le nom du titulaire du compte et
 * le solde du compte
 */
@Named(value = "ajoutCompte")
@ViewScoped
public class AjoutCompte implements Serializable {

    private String nom;
    @PositiveOrZero
    private int montant;

    @Inject
    private GestionnaireCompte gestionnaire;

    /**
     * Creates a new instance of AjoutCompte
     */
    public AjoutCompte() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    @Transactional
    public String ajouter() {
        gestionnaire.persist(new CompteBancaire(nom, montant));
        Util.addFlashInfoMessage("Compte créé");
        return "listeComptes?faces-redirect=true";
    }

}
