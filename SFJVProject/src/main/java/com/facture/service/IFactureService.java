package com.facture.service;

import org.springframework.data.domain.Page;

import com.facture.bean.Facture;

public interface IFactureService {

	public Page<Facture> findFactureByClient (String user_id, int page, int size);
	public Page<Facture> findFactureNP(String user_id, int page, int size);
	Page<Facture> findAll(int page, int size);
	Page<Facture> findAllFactureNP(int pageNp, int size);
	
}
