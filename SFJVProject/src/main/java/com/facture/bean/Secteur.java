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
public class Secteur implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String nom;
    @OneToMany(mappedBy = "secteur")
    private List<Quartier> quartiers;
   
    
    
    public Secteur() {
    }

  
    public Secteur(String nom) {
		super();
		this.nom = nom;
	}


	public Secteur(String nom, List<Quartier> quartiers) {
		super();
		this.nom = nom;
		this.quartiers = quartiers;
	}


	
	
	public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    
  

    public List<Quartier> getQuartiers() {
        return quartiers;
    }

    public void setQuartiers(List<Quartier> quartiers) {
        this.quartiers = quartiers;
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
		Secteur other = (Secteur) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Secteur [nom=" + nom + ", quartiers=" + quartiers + "]";
	}

   

  

    
}
