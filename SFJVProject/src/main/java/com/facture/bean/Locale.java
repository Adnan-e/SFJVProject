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
public class Locale implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String adresse;
    private String numeroContrat;
    private int dernierAnnee;
    private int dernierMois;
    
//    @ManyToOne
//    private Quartier quartier;  
    @ManyToOne
    private Client client;
    

    public Locale() {
    }

    public Locale(String adresse, String numeroContrat, int dernierAnnee, int dernierMois) {
        this.adresse = adresse;
        this.numeroContrat = numeroContrat;
        this.dernierAnnee = dernierAnnee;
        this.dernierMois = dernierMois;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumeroContrat() {
        return numeroContrat;
    }

    public void setNumeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

//    public Quartier getQuartier() {
//        return quartier;
//    }
//
//    public void setQuartier(Quartier quartier) {
//        this.quartier = quartier;
//    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getDernierAnnee() {
        return dernierAnnee;
    }

    public void setDernierAnnee(int dernierAnnee) {
        this.dernierAnnee = dernierAnnee;
    }

    public int getDernierMois() {
        return dernierMois;
    }

    public void setDernierMois(int dernierMois) {
        this.dernierMois = dernierMois;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
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
		Locale other = (Locale) obj;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Locale [adresse=" + adresse + "]";
	}

    
    
    
  
  

  
    
}
