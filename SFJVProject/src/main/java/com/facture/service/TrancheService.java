package com.facture.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facture.bean.Tranche;
import com.facture.deo.TrancheRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TrancheService implements ITrancheService {

     @Autowired
     TrancheRepository trancheRepository;
	
	 public List<Tranche> findAllTrancheByType(String t_type) {
	    return  trancheRepository.findAllTrancheByType(t_type);
	    }
	 
	 
	 public Optional<Tranche> findTranche(String tranche_id) {
		 return trancheRepository.findById(tranche_id);}
		 
	 
	     
	 
	 public void addTranche(String id,String t_type,Double trancheMin, Double trancheMax,Double montant) {
			    Tranche tranche = new Tranche();
			    tranche.setId(t_type+"_"+id);
			    tranche.setType(t_type);
			    tranche.setTrancheMin(trancheMin);
			    tranche.setTrancheMax(trancheMax);
			    tranche.setMontant(montant);
				trancheRepository.save(tranche);}

	 public void editTranche(String id,Double trancheMin, Double trancheMax,Double montant) {
		 Optional<Tranche> oldTranche = trancheRepository.findById(id);
			if(oldTranche.isPresent()) {
				int cmp=0;
				if(trancheMin != oldTranche.get().getTrancheMin() && trancheMin !=null && trancheMin >= 0) {
					oldTranche.get().setTrancheMin(trancheMin);
					++cmp;}
				if(trancheMax != oldTranche.get().getTrancheMax() && trancheMax !=null && trancheMax >= 0) {
					oldTranche.get().setTrancheMax(trancheMax);
					++cmp;}
				if(montant != oldTranche.get().getMontant() && montant !=null && montant >= 0) {
					oldTranche.get().setMontant(montant);
					++cmp;}
				if(cmp != 0) {
				trancheRepository.save(oldTranche.get());}}}
	 
	  public int checkProcess(String id,Double trancheMin, Double trancheMax,Double montant) {
			        Optional<Tranche> oldTranche = trancheRepository.findById(id);
				    if(id.isEmpty() || id.equals(null) || id.equals(" ")) {return -1;}
			        if(oldTranche.isPresent()) {return 1;}
					if(trancheMin == null || trancheMin < 0) {return -2;}
					if(trancheMax == null || trancheMax < 0 ) {return -3;}
					if(montant ==null || montant < 0) {return -4;}
			        return 2;}
	 
	 
	 /*
	  public void saveClient (String cin,String nom,String prenom,String adresse,String email,String username,Long telephone) {

			}else {
		Client client = new Client();
		User LoadedUser = userRepository.findUser(username);
		client.setId(cin);
		client.setPrenom(prenom);
		client.setAdresse(adresse);
		client.setNom(nom);
		client.setEmail(email);
		client.setTelephone(telephone);
		
		client.setUser(LoadedUser);
		clientRepository.save(client);
		}
	  */
	 
	
	  
	 public void removeTranche(String tranche_id) {
     if(!tranche_id.isEmpty() && tranche_id != null ) {
	 Optional<Tranche> tranche = trancheRepository.findById(tranche_id);
	 if (tranche.isPresent()) {trancheRepository.delete(tranche.get());}}}
	 
		
	
}
