package com.facture.deo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.facture.bean.User;

public interface UserRepository extends JpaRepository<User, String>{

	
	@Query("select u from User u where  LOWER(u.id)= LOWER(:userId) ")
	public User findUser (@Param("userId") String id ) ;
	
	
	
	
}
