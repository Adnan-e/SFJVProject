/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facture.bean;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author A7-E
 */
@Entity
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    private double consomation ;
    
    private String etat;
    private int annee;
    private String mois;
    private double montantHt;
    private double montantTtc;
    private double montantTva;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long indice;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Locale locale;

    public Facture() {
    }

    public Facture(String id) {
        this.id = id;
    }

    public Facture(String id, double consomation, String etat, int annee, String mois, double montantHt, double montantTtc, double montantTva, Long indice) {
        this.id = id;
        this.consomation = consomation;
        this.etat = etat;
        this.annee = annee;
        this.mois = mois;
        this.montantHt = montantHt;
        this.montantTtc = montantTtc;
        this.montantTva = montantTva;
        
    }

    public double getConsomation() {
        return consomation;
    }

    public void setConsomation(double consomation) {
        this.consomation = consomation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public double getMontantHt() {
        return montantHt;
    }

    public void setMontantHt(double montantHt) {
        this.montantHt = montantHt;
    }

    public double getMontantTtc() {
        return montantTtc;
    }

    public void setMontantTtc(double montantTtc) {
        this.montantTtc = montantTtc;
    }

    public double getMontantTva() {
        return montantTva;
    }

    public void setMontantTva(double montantTva) {
        this.montantTva = montantTva;
    }

    public Client getClient() {
        if(client==null){
            client= new Client();
        }
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Locale getLocale() {
        if(locale== null){
            locale= new Locale();
        }
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    
    
    
    
    
    
 
	

	public Long getIndice() {
		return indice;
	}

	public void setIndice(Long indice) {
		this.indice = indice;
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
        if (!(object instanceof Facture)) {
            return false;
        }
        Facture other = (Facture) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Facture[ id=" + id + " ]";
    }
    
}
