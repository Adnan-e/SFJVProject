package com.facture.deo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.facture.bean.Locale;



public interface LocaleRepository extends JpaRepository<Locale, String> {
	
	
	
	@Query("select l from Locale l where LOWER(l.client.id) = LOWER(:client_id) Order by l.id DESC ")
	public List<Locale> findLocaleByClient (@Param("client_id") String client_id ) ;
	
	@Query("select l from Locale l where l.adresse= :adresse ")
	public Locale findLocale (@Param("adresse") String adresse ) ;


}
