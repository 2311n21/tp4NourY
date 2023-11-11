/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emsi.tpbanquenoury.config;

import com.emsi.tpbanquenoury.entity.CompteBancaire;
import com.emsi.tpbanquenoury.service.GestionnaireCompte;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;

/**
 *
 * @author mac
 * 
 * Bean CDI pour créer 4 comptes au démarrage de l'application si la table des comptes est vide. 
 */


public class Init {
    
    @Inject
    private GestionnaireCompte compte;
    
    public void init(
            @Observes
            @Initialized(ApplicationScoped.class) ServletContext context) {
        if (compte.nbComptes() == 0) {
            compte.persist(new CompteBancaire("John Lennon", 150000));
            compte.persist(new CompteBancaire("Paul McCartney", 950000));
            compte.persist(new CompteBancaire("Ringo Starr", 20000));
            compte.persist(new CompteBancaire("George Harrison", 100000));
        }
    }
    
    
}
