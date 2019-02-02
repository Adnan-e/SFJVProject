/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.facture.bean;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author A7-E
 */
@Entity
public class Quartier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String nom;
    @ManyToOne
    private Secteur secteur;

    public Quartier() {
    }

    

  

    public Quartier(String nom) {
		super();
		this.nom = nom;
	}





	public Quartier(String nom, Secteur secteur) {
		super();
		this.nom = nom;
		this.secteur = secteur;
	}





	public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}





	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quartier other = (Quartier) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}





	@Override
	public String toString() {
		return "Quartier [nom=" + nom + "]";
	}

    
    
    
  


}
