package com.facture.service;


import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facture.bean.Client;
import com.facture.bean.Locale;
import com.facture.bean.Quartier;
import com.facture.bean.Secteur;
import com.facture.deo.LocaleRepository;
import com.facture.service.util.SearchUtil;

@Service
@Transactional
public class LocaleService implements ILocaleService {

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	LocaleRepository localeRepository;
    
	@Autowired
	ClientService clientService;
	@Autowired
	QuartierService quartierService;
	 
	
	 @Override
	  public List<Locale> findByCritaria(Quartier quartier, Secteur secteur, int anneMin, int anneMax, int moisMin, int moisMax) {
	        String request = contructQuerybyCritaria(quartier, secteur, anneMin, anneMax, moisMin, moisMax);
	        return em.createQuery(request).getResultList();
	    }
	  
	    private String contructQuerybyCritaria(Quartier quartier, Secteur secteur, Integer anneMin, Integer anneMax, Integer moisMin, Integer moisMax) {
	        String request = "SELECT local from Locale local where 1=1";
	        if (quartier != null) {
	            request += SearchUtil.addConstraint("local", "quartier.id", "=", quartier.getNom());
	        }
	        if (secteur != null) {
	            request += SearchUtil.addConstraint("local", "quartier.secteur.id", "=", secteur.getNom());
	        }
	        request += SearchUtil.addConstraintMinMax("local", "dernierAnnee", anneMin, anneMax);
	        request += SearchUtil.addConstraintMinMax("local", "dernierMois", moisMin, moisMax);
	        return request;
	    }
	
	    
	    @Override
		public List<Locale> findLocaleByClient(String user_id) {
			Optional<Client> client_ = clientService.findClient(user_id); 
			if (!client_.isPresent()) {
				return localeRepository.findLocaleByClient("");
			}
			return localeRepository.findLocaleByClient(client_.get().getId());
			}
	    

	    
		public List<Locale> findLocaleByClientId(String client_id) {
			if (!client_id.isEmpty() && !client_id.equals(null)) {
				return localeRepository.findLocaleByClient(client_id);
			}

			return null;
			}
	    
	    
	    @Override
	    public Locale findLocale ( String adresse ) {
	    return localeRepository.findLocale(adresse);}
	    
	    
	   public void removeLocale (Locale locale) {
		   if (locale !=null) {
			   localeRepository.delete(locale);
		   }
	   }
	   
	   public List<Locale> findAll () {
		   return localeRepository.findAll();
	   }
	   
	   public int saveLocale (String n_contrat,int d_annee, int d_mois,
				 String l_adresse,Client client) {
		   System.out.println(n_contrat +" "+ d_annee  +" "+ d_mois +" "+ l_adresse +" "+client.getId() );
		   
		   if (n_contrat.equals(null)||n_contrat.isEmpty()) {
		   return -1;
		   }
		   if (d_annee == 0 ) {
		   return -2;
		   }
		   if (d_mois == 0) {
		   return -3;
		   }
		   if (l_adresse.equals(null)||l_adresse.isEmpty()) {
		   return -4;
		   }
		   
		   if (client.getId().isEmpty() || client.getId().equals(null)) {
			   return -6;
		   }
			   
		   System.out.println( " im here " +n_contrat +" "+ d_annee  +" "+ d_mois +" "+ l_adresse +" "+client.getId() );

		   Optional<Locale> Locale_ = localeRepository.findById(n_contrat);
		   System.out.println( " here can too " +n_contrat +" "+ d_annee  +" "+ d_mois +" "+ l_adresse +" "+client.getId() );

		   if(Locale_.isPresent()) {
			   return 2;}
		   Locale locale = new Locale();
		   locale.setNumeroContrat(n_contrat);
		   locale.setAdresse(l_adresse);
		   locale.setDernierAnnee(d_annee);
		   locale.setDernierMois(d_mois);
		   locale.setClient(client);
		   localeRepository.save(locale);
		   System.out.println( " im almost done " +n_contrat +" "+ d_annee  +" "+ d_mois +" "+ l_adresse +" "+client.getId() );

		   return 1;
		   }
		   
		   
		   
		   
	   }
	   
