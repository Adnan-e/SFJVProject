package com.facture.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facture.bean.TrancheConsommation;
import com.facture.deo.TrancheConsommationRepository;
import com.facture.bean.Facture;
import com.facture.bean.Tranche;

@Service
@Transactional
public class TrancheConsommationService implements ITrancheConsommationService{
   
	 @Autowired
	 TrancheService trancheService;
	 @Autowired
	 FactureService factureService;
	
	 
	 @Autowired
	 TrancheConsommationRepository trancheConsommationRepository;
	
	 public List<TrancheConsommation> constructTrancheConsommation (String t_type,double consommation) {
	        double somme = consommation;
	        List<TrancheConsommation> trancheConsommations = new ArrayList<>();
	        List<Tranche> tranches = trancheService.findAllTrancheByType(t_type);
	        for (int i = 0; i < tranches.size(); i++) {
	            Tranche myTranche = tranches.get(i);
	            TrancheConsommation trancheConsommation = new TrancheConsommation();
	            
	            if (somme >= myTranche.getTrancheMin()&& somme <= myTranche.getTrancheMax() && i == 0) {
	                somme -= consommation; 
	                trancheConsommation.setConsommation(consommation);
	                trancheConsommation.setTranche(myTranche);
	                consommation = 0;
	            } else if (somme > myTranche.getTrancheMax()&& i == 0) {
	                consommation = myTranche.getTrancheMax();
	                somme -= consommation;
	                trancheConsommation.setConsommation(consommation);
	                trancheConsommation.setTranche(myTranche);
	                // ------------------------------------------------------------------------------       
	            }
	            if (somme >= myTranche.getTrancheMin()&& somme <= myTranche.getTrancheMax()&& i > 0 && i != tranches.size() - 1) {
	                consommation = (myTranche.getTrancheMax()- myTranche.getTrancheMin());
	                somme -= consommation; 
	                trancheConsommation.setConsommation(consommation);
	                trancheConsommation.setTranche(myTranche);
	            } else if (somme > myTranche.getTrancheMax()&& i > 0 && i != tranches.size() - 1) {
	                consommation = (myTranche.getTrancheMax()- myTranche.getTrancheMin());
	                somme -= consommation; 
	                trancheConsommation.setConsommation(consommation);
	                trancheConsommation.setTranche(myTranche);
	            } else if (somme > 0 && somme <= ((myTranche.getTrancheMax()- myTranche.getTrancheMin())) && i > 0 && i != tranches.size() - 1) {
	                trancheConsommation.setConsommation(consommation);
	                trancheConsommation.setTranche(myTranche);
	                 consommation = 0;
	            } else if (somme > 0 && i > 0 && i != tranches.size() - 1) {
	                somme -= consommation;
	                consommation = (myTranche.getTrancheMax()- myTranche.getTrancheMin());
	                trancheConsommation.setConsommation(consommation);
	                trancheConsommation.setTranche(myTranche);
	                
	                //---------------------------------------------------------------------------------------------                    
	            }
	            if (somme > 0 && i == tranches.size() - 1) {
	                trancheConsommation.setConsommation(somme);
	                trancheConsommation.setTranche(myTranche);
	            }
	            if (trancheConsommation.getConsommation() > 0){
	            trancheConsommation.setMontant(myTranche.getMontant() * trancheConsommation.getConsommation());
	            trancheConsommations.add(trancheConsommation);
	            }
	        }
	        return trancheConsommations;
	    }
	    
	    
	    public List<TrancheConsommation> findAllById (){    
	        return trancheConsommationRepository.findAllTcById();
	     } 
	    
	    public List<TrancheConsommation> findFactureTransaction(String f_id){
	    	return trancheConsommationRepository.findFactureTransaction(f_id);
	    }
	    
	    
	    public void savetrancheConsommation(String t_type,String id) {
	    	Optional<Facture> facture = factureService.findByid(id);
	    	System.out.println(facture.get().getId() + " your facture id after ");
	    	if(facture.isPresent()) {
	    	List<TrancheConsommation> trancheConsommations = constructTrancheConsommation(t_type,facture.get().getConsomation());
	    	if(trancheConsommations!=null) {
	        for (int i = 0; i < trancheConsommations.size(); i++) {
            TrancheConsommation trancheConsomation = trancheConsommations.get(i);
            trancheConsomation.setFacture(facture.get());
            trancheConsommationRepository.save(trancheConsomation);}
	    	} }
	    }
	
	    public void deleteTC (TrancheConsommation trancheConsommation) {
	    	if (trancheConsommation != null) {
	    	trancheConsommationRepository.delete(trancheConsommation);
	    	}}
	    
}
