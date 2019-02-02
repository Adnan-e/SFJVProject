package com.facture.service;

import java.util.Optional;

import com.facture.bean.Client;

public interface IClientService{

	Optional<Client> findClient(String user_id);
	

	
}
