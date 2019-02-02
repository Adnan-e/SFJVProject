/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facture.bean;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author A7-E
 */
@Entity
public class Employe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //CNI
    private String id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String fonction;
    private String telephone;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateNaissance;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datePriseFonction;
    private String employeRole;
    @OneToOne
    private User user;
    private int dernierMoisPaiement;
    private int dernierAnneePaiement;

    public Employe(String id) {
        this.id = id;
    }

    public Employe() {
    }
    

    public Employe(String nom, String prenom, String adresse, String email, String fonction, String telephone, Date dateNaissance, Date datePriseFonction, String employeRole, int dernierMoisPaiement, int dernierAnneePaiement) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.fonction = fonction;
        this.telephone = telephone;
        this.dateNaissance = dateNaissance;
        this.datePriseFonction = datePriseFonction;
        this.employeRole = employeRole;
        this.dernierMoisPaiement = dernierMoisPaiement;
        this.dernierAnneePaiement = dernierAnneePaiement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Date getDatePriseFonction() {
        return datePriseFonction;
    }

    public void setDatePriseFonction(Date datePriseFonction) {
        this.datePriseFonction = datePriseFonction;
    }

    public String getEmployeRole() {
        return employeRole;
    }

    public void setEmployeRole(String employeRole) {
        this.employeRole = employeRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getDernierMoisPaiement() {
        return dernierMoisPaiement;
    }

    public void setDernierMoisPaiement(int dernierMoisPaiement) {
        this.dernierMoisPaiement = dernierMoisPaiement;
    }

    public int getDernierAnneePaiement() {
        return dernierAnneePaiement;
    }

    public void setDernierAnneePaiement(int dernierAnneePaiement) {
        this.dernierAnneePaiement = dernierAnneePaiement;
    }

    
    
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employe)) {
            return false;
        }
        Employe other = (Employe) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Employe[ id=" + id + " ]";
    }
    
}
