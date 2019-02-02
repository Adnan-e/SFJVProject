package com.facture.service;




import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facture.bean.Client;
import com.facture.bean.Facture;
import com.facture.bean.TrancheConsommation;
import com.facture.deo.FactureRepository;
import com.facture.service.util.SearchUtil;



@Service
@Transactional
public class FactureService implements IFactureService{
    
	@PersistenceContext
    private EntityManager em;
	
    @Autowired
    TrancheService trancheService;
    @Autowired
    TrancheConsommationService trancheConsommationService;
	@Autowired
	ClientService clientService;
	@Autowired
	FactureRepository factureRepository;
//	@Autowired
//	TrancheConsommationRepository trancheConsommationRepository;
	
	PageImpl<Facture> pfacturesImp;
	
// for Jasper	
//	public List<Facture> findFactureByC(String user_id){
//		String client_id_ = clientRepository.findClient(user_id);
//		return factureRepository.findFactureByC(client_id_);
//	}
	
	@Override
	public Page<Facture> findFactureByClient(String user_id, int page, int size) {
		Optional<Client> client_id = clientService.findClient(user_id);
		if (!client_id.isPresent()) {
			return factureRepository.findFactureByClient("",new PageRequest(page, size));
		}
		return factureRepository.findFactureByClient(client_id.get().getId(),new PageRequest(page, size));

 	}
	
	public List<Facture> findFactureByClient_(String client_id){
		return factureRepository.findFactureByClient_(client_id);
	}
	
	@Override
	public Page<Facture> findFactureNP(String user_id, int page, int size) {
		Optional<Client> client_id = clientService.findClient(user_id);
		if (!client_id.isPresent()) {
			return factureRepository.findFactureNP("",new PageRequest(page, size));
 
		}
		return factureRepository.findFactureNP(client_id.get().getId(),new PageRequest(page, size));
 	}
	
	@Override
	public Page<Facture> findAll( int page, int size) {	
		return factureRepository.findAllWithSort(new PageRequest(page, size));
 	}
	@Override
	public Page<Facture> findAllFactureNP( int pageNp, int size) {	
		return factureRepository.findAllFactureNP(new PageRequest(pageNp, size));		
 	}
	

	 public Long generate(String beanName, String attributeName) {
	        String requete = "SELECT max(item." + attributeName + ") FROM " + beanName + " item";
	        List<Long> maxId = em.createQuery(requete).getResultList();
	        if (maxId == null || maxId.isEmpty() || maxId.get(0) == null) {
	            return 1L;
	        }
	        return maxId.get(0) + 1;
	    }
	
	 private double calcMontantFacture(List<TrancheConsommation> trancheConsommations) {
	        double montantHt = 0.0;
	        for (TrancheConsommation trancheConsommation : trancheConsommations) {
	            montantHt += trancheConsommation.getMontant();
	        }
	        return montantHt;
	    }
	 
	 public void save(String t_type,Facture facture) {
	        List<TrancheConsommation> trancheConsommations = trancheConsommationService.constructTrancheConsommation(t_type,facture.getConsomation());
	        facture.setMontantHt(calcMontantFacture(trancheConsommations));
	        generateId(t_type,facture);
	        facture.setEtat("NP");
	        facture.setMontantTva(50);
	        facture.setMontantTtc(facture.getMontantHt()+facture.getMontantTva());
	        factureRepository.save(facture);
	        trancheConsommationService.savetrancheConsommation(t_type,facture.getId());
	    }
	 
	 public void generateId(String t_type,Facture facture) {
	        int year = new Date().getYear() + 1900;
	        facture.setIndice(generate("Facture" , "indice"));
	        facture.setId("F-"+year+"-"+facture.getIndice()+"-"+t_type);
	        
	    }
	 
	 public Optional<Facture> selectedFacture(String facture_id) {
		 Optional<Facture> facture = factureRepository.findById(facture_id);
		return facture;
	 }

//	for Jasper 
//	public List<Map<String, Object>> factureReport (String user_id){
//		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
//		List<Facture> factures_ = findFactureByC(user_id);
//		for (Facture facture : factures_) {
//			Map<String, Object> items = new HashMap<String, Object>();		
//			items.put("name", facture.getName());
//			items.put("consomation", facture.getConsomation());
//			items.put("montantTtc", facture.getMontantTtc());
//			items.put("mois", facture.getMois());
//			items.put("annee", facture.getAnnee());
//			items.put("etat", facture.getEtat());
//			items.put("locale", facture.getLocale().getAdresse());
//			result.add(items);
//		}
//		
//		return result;
//	}
	 
	 public void removeAll(Facture facture) {
		 List<TrancheConsommation> trancheConsommations = trancheConsommationService.findFactureTransaction(facture.getId());
		 for (TrancheConsommation trancheConsommation : trancheConsommations) {
			 if(trancheConsommation!=null) {
				 trancheConsommationService.deleteTC(trancheConsommation);
			 }
		}
		 if(facture!=null) {
			 factureRepository.delete(facture);
		 }
	 }
	 
	 public Optional<Facture> findByid(String id) {
		 Optional<Facture> facture = factureRepository.findById(id);
		return facture;
		 
	 }
	 

	
	 public Page<Facture> fSearchRoot( String s_id,int page, int size) {
		  
		  Page<Facture> userFactures = factureRepository.findFactureByStringRoot(s_id, new PageRequest(page, size));
		  s_id=s_id.replaceAll("\\s+", "").toLowerCase();
		  
		    if(s_id.startsWith("consommation") && !s_id.substring(13).isEmpty())  {
				  s_id = s_id.substring(12);
//				  System.out.println(" your S_id " +s_id + "your user_id " +user_id);
				  if(s_id.startsWith(">"))   {
//					  System.out.println(" your S_id after " +s_id + "your user_id " +user_id);
					  userFactures = factureRepository.findFactureByConsommationRoot( Double.valueOf(s_id.substring(1)), new PageRequest(page, size)); 
				  }
				  if(s_id.startsWith("<")) {
					   userFactures = factureRepository.findFactureBy_ConsommationRoot( Double.valueOf(s_id.substring(1)), new PageRequest(page, size)); 
						  }  
			  }
			  
			  if(s_id.startsWith("montantht")&& !s_id.substring(10).isEmpty()) {
				  s_id = s_id.substring(9);
				  if(s_id.startsWith(">")) {
					   userFactures = factureRepository.findFactureByMontantHtRoot( Double.valueOf(s_id.substring(1)),new PageRequest(page, size)); 
				  }
						  if(s_id.startsWith("<")) {
							   userFactures = factureRepository.findFactureBy_MontantHtRoot( Double.valueOf(s_id.substring(1)), new PageRequest(page, size)); 
								  }  
			  }
			  if(s_id.startsWith("montantttc") && !s_id.substring(11).isEmpty()) {
				  s_id = s_id.substring(10);
				  if(s_id.startsWith(">")) {
					   userFactures = factureRepository.findFactureByMontantTtcRoot( Double.valueOf(s_id.substring(1)), new PageRequest(page, size)); 
				  }
						  if(s_id.startsWith("<")) {
							   userFactures = factureRepository.findFactureBy_MontantTtcRoot( Double.valueOf(s_id.substring(1)),  new PageRequest(page, size)); 
								  }  
			  }
			  if(s_id.startsWith("montanttva")&& !s_id.substring(11).isEmpty()) {
				  s_id = s_id.substring(10);
				  if(s_id.startsWith(">")) {
					   userFactures = factureRepository.findFactureByMontantTvaRoot( Double.valueOf(s_id.substring(1)), new PageRequest(page, size)); 
						  }
				if(s_id.startsWith("<")) {
							   userFactures = factureRepository.findFactureBy_MontantTvaRoot( Double.valueOf(s_id.substring(1)), new PageRequest(page, size)); 
								  }  
			  }
			  
			  if(s_id.startsWith("annee")&& !s_id.substring(6).isEmpty()) {
				  s_id = s_id.substring(5);
				  if(s_id.startsWith(">")) {
					   userFactures = factureRepository.findFactureByAnneeRoot( Integer.valueOf(s_id.substring(1)), new PageRequest(page, size)); 
						  }
				if(s_id.startsWith("<")) {
							   userFactures = factureRepository.findFactureBy_AnneeRoot( Integer.valueOf(s_id.substring(1)), new PageRequest(page, size)); 
								  }  
			  }
  
			return userFactures;
	 }
	 
	 
	 ////////////////////////////////////////////////////Root
	 public Page<Facture> fSearch( String s_id, String user_id,int page, int size) {
		  Optional<Client> client_id = clientService.findClient(user_id);
		  Page<Facture> userFactures = factureRepository.findFactureByString(s_id, client_id.get().getId(), new PageRequest(page, size));
		  s_id=s_id.replaceAll("\\s+", "").toLowerCase();
		  
		    if(s_id.startsWith("consommation") && !s_id.substring(13).isEmpty())  {
				  s_id = s_id.substring(12);
//				  System.out.println(" your S_id " +s_id + "your user_id " +user_id);
				  if(s_id.startsWith(">"))   {
//					  System.out.println(" your S_id after " +s_id + "your user_id " +user_id);
					  userFactures = factureRepository.findFactureByConsommation( Double.valueOf(s_id.substring(1)), client_id.get().getId(), new PageRequest(page, size)); 
				  }
				  if(s_id.startsWith("<")) {
					   userFactures = factureRepository.findFactureBy_Consommation( Double.valueOf(s_id.substring(1)), client_id.get().getId(), new PageRequest(page, size)); 
						  }  
			  }
			  
			  if(s_id.startsWith("montantht")&& !s_id.substring(10).isEmpty()) {
				  s_id = s_id.substring(9);
				  if(s_id.startsWith(">")) {
					   userFactures = factureRepository.findFactureByMontantHt( Double.valueOf(s_id.substring(1)), client_id.get().getId(), new PageRequest(page, size)); 
				  }
						  if(s_id.startsWith("<")) {
							   userFactures = factureRepository.findFactureBy_MontantHt( Double.valueOf(s_id.substring(1)), client_id.get().getId(), new PageRequest(page, size)); 
								  }  
			  }
			  if(s_id.startsWith("montantttc") && !s_id.substring(11).isEmpty()) {
				  s_id = s_id.substring(10);
				  if(s_id.startsWith(">")) {
					   userFactures = factureRepository.findFactureByMontantTtc( Double.valueOf(s_id.substring(1)), client_id.get().getId(), new PageRequest(page, size)); 
				  }
						  if(s_id.startsWith("<")) {
							   userFactures = factureRepository.findFactureBy_MontantTtc( Double.valueOf(s_id.substring(1)), client_id.get().getId(), new PageRequest(page, size)); 
								  }  
			  }
			  if(s_id.startsWith("montanttva")&& !s_id.substring(11).isEmpty()) {
				  s_id = s_id.substring(10);
				  if(s_id.startsWith(">")) {
					   userFactures = factureRepository.findFactureByMontantTva( Double.valueOf(s_id.substring(1)), client_id.get().getId(), new PageRequest(page, size)); 
						  }
				if(s_id.startsWith("<")) {
							   userFactures = factureRepository.findFactureBy_MontantTva( Double.valueOf(s_id.substring(1)), client_id.get().getId(), new PageRequest(page, size)); 
								  }  
			  }
			  
			  if(s_id.startsWith("annee")&& !s_id.substring(6).isEmpty()) {
				  s_id = s_id.substring(5);
				  if(s_id.startsWith(">")) {
					   userFactures = factureRepository.findFactureByAnnee( Integer.valueOf(s_id.substring(1)), client_id.get().getId(), new PageRequest(page, size)); 
						  }
				if(s_id.startsWith("<")) {
							   userFactures = factureRepository.findFactureBy_Annee( Integer.valueOf(s_id.substring(1)), client_id.get().getId(), new PageRequest(page, size)); 
								  }  
			  }
 
			return userFactures;
	 }
	 /////////////////////////////////////////////////////////
	 
	 
	 
	 
	   public List<Facture> findByCritaria(String client_id, String locale_id, Integer anneeMin,Integer anneeMax, String mois,Double consommation) {
		 System.out.println("im her" + consommation + "im her" +anneeMin + "im her" + anneeMax+ "im her" + mois);
		   String request = contructQuerybyCritaria(client_id, locale_id, anneeMin, anneeMax, mois,consommation);
	        List<Facture> lFactures = em.createQuery(request).getResultList();
//	       PageImpl<Facture> pfacturesImp = new PageImpl<>(lFactures, pageable, lFactures.size());
//	        Page<Facture> pfactures = getPfacturesImp(pageable, lFactures);
	        return lFactures ;
	     }
	     
	      private String contructQuerybyCritaria(String client_id, String locale_id, Integer anneeMin,Integer anneeMax, String mois,Double consommation) {
	        String request = "SELECT facture from Facture facture where 1=1";
	             if (consommation != null && consommation != 0) {
	        request += SearchUtil.addConstraint("facture", "consomation",">=", consommation);
	          }
	         if (anneeMin != null && anneeMin !=0 || anneeMax != null && anneeMax !=0) {
	        request += SearchUtil.addConstraintMinMax("facture", "annee", anneeMin, anneeMax);
	         }
	          if (!mois.equals(null) && !mois.isEmpty()) {
	        	  System.out.println("im inside");
	        request += SearchUtil.addConstraint("facture", "mois","=", mois);
	          }
	          if (!client_id.equals(null) && !client_id.isEmpty()) {
		            request += SearchUtil.addConstraint("facture", "client.id", "=", client_id);
		        }
		        if (!locale_id.equals(null) && !locale_id.isEmpty()) {
		            request += SearchUtil.addConstraint("facture", "locale.adresse", "=", locale_id);
		        }
	        return request;
	    }

	      
	      
	      
//		public PageImpl<Facture> getPfacturesImp(Pageable pageable,List<Facture> lFactures) {
//			if(pfacturesImp. ) {
//				pfacturesImp = new PageImpl<>(lFactures, pageable, lFactures.size());
//			}
//			
//			return pfacturesImp;
//		}
//
//		public void setPfacturesImp(PageImpl<Facture> pfacturesImp) {
//			this.pfacturesImp = pfacturesImp;
//		}

	 
	 
	 
}
