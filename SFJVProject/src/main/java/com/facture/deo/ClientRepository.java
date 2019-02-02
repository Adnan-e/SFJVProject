package com.facture.deo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.facture.bean.Client;


public interface ClientRepository extends JpaRepository<Client, String> {

	
	@Query("select id from Client c where LOWER (c.user.id)= LOWER (:user_id)")
	public String findClient (@Param("user_id") String user_id) ;
//	@Query("select c from Client c where LOWER (c.id)= LOWER (:client_id)")
//	public Client findClientByid (@Param("client_id") String client_id) ;
	
}
