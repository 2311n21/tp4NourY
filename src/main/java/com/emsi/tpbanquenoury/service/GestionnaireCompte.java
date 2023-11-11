/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.emsi.tpbanquenoury.service;

import com.emsi.tpbanquenoury.entity.CompteBancaire;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.util.List;

/**
 *
 * @author mac
 *
 * Création bean CDI GestionnaireCompte pour gérer les comptes bancaires dans la
 * base de données
 *
 */
@DataSourceDefinition(
        className = "com.mysql.cj.jdbc.MysqlDataSource",
        name = "java:app/jdbc/banque",
        serverName = "localhost",
        portNumber = 3306,
        user = "nour",
        password = "nour123",
        databaseName = "banque",
        properties = {
            "useSSL=false",
            "allowPublicKeyRetrieval=true",
            "driverClass=com.mysql.cj.jdbc.Driver"
        }
)

@RequestScoped
public class GestionnaireCompte {

    @PersistenceContext(unitName = "banquePU")
    private EntityManager em;

    @Transactional
    public void persist(CompteBancaire compte) {
        em.persist(compte);
    }

    public CompteBancaire getCompte(Long id) {
        return em.find(CompteBancaire.class, id);
    }

    public List<CompteBancaire> getAllComptes() {
        Query query = em.createNamedQuery("CompteBancaire.findAll");
        return query.getResultList();
    }

    public long nbComptes() {
        Query query = em.createNamedQuery("CompteBancaire.count");
        return (long) query.getSingleResult();
    }

    @Transactional
    public void transferer(CompteBancaire source, CompteBancaire destination, int montant) {
        source.retirer(montant);
        destination.deposer(montant);
        update(source);
        update(destination);
    }

    @Transactional
    public CompteBancaire update(CompteBancaire compteBancaire) {
        return em.merge(compteBancaire);
    }

    /**
     * Dépôt d'argent sur un compte bancaire.
     *
     * @param compteBancaire
     * @param montant
     */
    @Transactional
    public void deposer(CompteBancaire compteBancaire, int montant) {
        compteBancaire.deposer(montant);
        update(compteBancaire);
    }

    /**
     * Retrait d'argent sur un compte bancaire.
     *
     * @param compteBancaire
     * @param montant
     */
    @Transactional
    public void retirer(CompteBancaire compteBancaire, int montant) {
        compteBancaire.retirer(montant);
        update(compteBancaire);
    }

    @Transactional
    public void supprimerCompte(CompteBancaire compte) {
        em.remove(em.merge(compte));
    }

}
