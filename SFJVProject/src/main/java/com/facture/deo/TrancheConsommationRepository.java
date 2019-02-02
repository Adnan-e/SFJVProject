package com.facture.deo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facture.bean.TrancheConsommation;

public interface TrancheConsommationRepository extends JpaRepository<TrancheConsommation, Long> {

	@Query("SELECT t FROM TrancheConsommation t ORDER BY t.id ASC")
	public List<TrancheConsommation> findAllTcById ();
	
	@Query("SELECT t from TrancheConsommation t where LOWER(t.facture.id)= LOWER(:f_id)")
	public List<TrancheConsommation> findFactureTransaction(@Param("f_id") String f_id);
	
}
