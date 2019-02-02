package com.facture.service;

import java.util.List;



import com.facture.bean.Locale;
import com.facture.bean.Quartier;
import com.facture.bean.Secteur;


public interface ILocaleService {

	List<Locale> findLocaleByClient(String user_id);

	Locale findLocale(String adresse);

	List<Locale> findByCritaria(Quartier quartier, Secteur secteur, int anneMin, int anneMax, int moisMin, int moisMax);

}
