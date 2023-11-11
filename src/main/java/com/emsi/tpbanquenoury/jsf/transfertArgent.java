package com.emsi.tpbanquenoury.jsf;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
import com.emsi.tpbanquenoury.entity.CompteBancaire;
import com.emsi.tpbanquenoury.service.GestionnaireCompte;

import com.emsi.tpbanquenoury.util.Util;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.io.Serializable;

/**
 *
 * @author mac
 *
 * bean CDI pour faire un transfert d'argent d'un compte à un autre.
 */
@Named(value = "transfertArgent")
@ViewScoped
public class transfertArgent implements Serializable {

    /**
     * Creates a new instance of transfertArgent
     */
    public transfertArgent() {
    }
    private Long source;
    private Long destination;
    private int montant;

    @Inject
    private GestionnaireCompte gestionnaire;

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public Long getDestination() {
        return destination;
    }

    public void setDestination(Long destination) {
        this.destination = destination;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    @Transactional
    public String transferer() {
        boolean erreur = false;
        CompteBancaire Csource = gestionnaire.getCompte(source);
        if (Csource == null) {
            Util.messageErreur("Aucun compte avec cet id",
                    "Aucun compte avec cet id",
                    "form:source");
            erreur = true;
        } else {
            if (Csource.getSolde() < montant) {
                Util.messageErreur("Solde du compte source insuffisant",
                        "Solde du compte source insuffisant",
                        "form:montant");
                erreur = true;
            }
        }
        CompteBancaire Cdestination = gestionnaire.getCompte(destination);
        if (Cdestination == null) {
            Util.messageErreur("Aucun compte avec cet id",
                    "Aucun compte avec cet id",
                    "form:destination");
            erreur = true;
        }
        if (erreur) {
            return null;
        }
        gestionnaire.transferer(Csource, Cdestination, montant);
        Util.addFlashInfoMessage("Transféré avec succès : Compte source : " + Csource.getNom()
                + " - Compte destination : " + Cdestination.getNom()
                + " - Montant = " + montant);
        return "listeComptes?faces-redirect=true";
    }


}
