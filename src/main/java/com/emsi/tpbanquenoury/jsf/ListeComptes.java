/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.emsi.tpbanquenoury.jsf;

import com.emsi.tpbanquenoury.entity.CompteBancaire;
import com.emsi.tpbanquenoury.service.GestionnaireCompte;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author mac
 *
 * backing bean qui retourne la liste de tous les comptes
 */
@Named(value = "listeComptes")
@ViewScoped
public class ListeComptes implements Serializable {

    private List<CompteBancaire> allComptes;

    /**
     * Creates a new instance of ListeComptes
     */
    public ListeComptes() {
    }

    @Inject
    private GestionnaireCompte gestionnaire;

    public List<CompteBancaire> getAllComptes() {
        if (allComptes == null) {
            allComptes = gestionnaire.getAllComptes();
        }
        return allComptes;
    }

}
