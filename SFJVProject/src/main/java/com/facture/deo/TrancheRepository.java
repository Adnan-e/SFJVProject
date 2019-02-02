package com.facture.deo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facture.bean.Tranche;

public interface TrancheRepository extends JpaRepository<Tranche, String> {

	
	@Query("select t from Tranche t where LOWER(t.type) = LOWER(:t_type) ORDER BY t.montant ASC")
	public List<Tranche> findAllTrancheByType(@Param("t_type") String t_type);
	
}
