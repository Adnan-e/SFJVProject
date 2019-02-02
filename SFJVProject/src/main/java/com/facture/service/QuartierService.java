package com.facture.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facture.bean.Quartier;
import com.facture.deo.QuartierRepository;

@Service
@Transactional
public class QuartierService implements IQuartierService{

	
	 @Autowired
 	 QuartierRepository  quartierRepository;
	
	 
	public Optional<Quartier> findQuartier (String quartier_id) {
		return quartierRepository.findById(quartier_id);
	}
	
	
	
}
