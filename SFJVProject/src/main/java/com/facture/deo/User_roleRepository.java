package com.facture.deo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facture.bean.User_role;

public interface User_roleRepository extends JpaRepository<User_role,String>{

	
	@Query("Select u from User_role u where LOWER(u.id)= LOWER(:user_Id)")
	public List<User_role> getAllByUserId(@Param("user_Id") String id ); 
	
	@Query("Select u from User_role u where LOWER(u.id)= LOWER(:user_Id) AND UPPER(u.role)= UPPER(:role_id) ")
	public  Optional<User_role> getUserByRoleAndUsername(@Param("user_Id") String id,@Param("role_id") String role_id);
}
