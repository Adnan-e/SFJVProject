/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facture.bean;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author A7-E
 */
@Entity


public class Tranche implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private double trancheMin;
    private double trancheMax;
    private double montant;
    private String type;
//    // das soll nicht hier sein
//    @ManyToOne
//    private Facture facture;
    @OneToMany(mappedBy = "tranche")
    private List<TrancheConsommation> trancheConsomations;

    public Tranche() {
    }

    public Tranche(String id) {
        this.id = id;
    }

  
    
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Tranche(double trancheMin, double trancheMax, double montant) {
        this.trancheMin = trancheMin;
        this.trancheMax = trancheMax;
        this.montant = montant;
    }

    public double getTrancheMin() {
        return trancheMin;
    }

    public void setTrancheMin(double trancheMin) {
        this.trancheMin = trancheMin;
    }

    public double getTrancheMax() {
        return trancheMax;
    }

    public void setTrancheMax(double trancheMax) {
        this.trancheMax = trancheMax;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

//    public Facture getFacture() {
//        return facture;
//    }
//
//    public void setFacture(Facture facture) {
//        this.facture = facture;
//    }

    public List<TrancheConsommation> getTrancheConsomations() {
        return trancheConsomations;
    }

    public void setTrancheConsomations(List<TrancheConsommation> trancheConsomations) {
        this.trancheConsomations = trancheConsomations;
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
        if (!(object instanceof Tranche)) {
            return false;
        }
        Tranche other = (Tranche) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Tranche[ id=" + id + " ]";
    }
    
}
