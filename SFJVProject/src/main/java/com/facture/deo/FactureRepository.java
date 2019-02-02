package com.facture.deo;




import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.facture.bean.Facture;


public interface FactureRepository extends JpaRepository<Facture, String>{

	
	
//	@Query("select facture from Facture f where f.annee=5")
//	public Facture findFactureByClient () ;
//	@Query("select facture from Facture f where LOWER(f.name) = LOWER(:factureName)")
//	public Facture findFactureByname(@Param("factureName") String facture_name);
	
	@Query("select f from Facture f Order by f.id DESC")
	public Page<Facture> findAllWithSort(Pageable pageable);
	
	@Query("select f from Facture f where LOWER(f.client.id) = LOWER(:client_id) Order by f.id DESC ")
	public Page<Facture> findFactureByClient (@Param("client_id") String client_id , Pageable pageable) ;
	
	@Query("select f from Facture f where LOWER(f.client.id) = LOWER(:client_id_) ")
	public List<Facture> findFactureByClient_ (@Param("client_id_") String client_id_ ) ;
	
	@Query("select f from Facture f where f.etat = 'NP' And LOWER(f.client.id) = LOWER(:client_id) Order by f.id DESC")
	public Page<Facture> findFactureNP (@Param("client_id") String client_id, Pageable pageable);
	
	@Query("select f from Facture f where f.etat = 'NP' Order by f.client.id ASC")
	public Page<Facture> findAllFactureNP ( Pageable pageable);

	
// for Jasper	
//	@Query("select f from Facture f where LOWER(f.client.id) = LOWER(:client_id) Order by f.id DESC ")
//	public List<Facture> findFactureByC (@Param("client_id") String client_id) ;
	
	
	// Search

	
	////////////
	@Query("select f from Facture f where LOWER(f.client.id) = LOWER(:client_id) AND (f.id like %:factureInfo% OR f.mois like %:factureInfo% OR f.montantTtc like %:factureInfo% Or f.montantHt like %:factureInfo% OR f.montantTva like %:factureInfo% OR f.consomation like %:factureInfo% OR f.annee like %:factureInfo%) Order by f.id DESC")
	public Page<Facture> findFactureByString(@Param("factureInfo") String factureInfo,@Param("client_id") String client_id , Pageable pageable);
	
	
	@Query("select f from Facture f where LOWER(f.client.id) = LOWER(:client_id) AND (f.montantTtc >= :factureInfo ) Order by f.id DESC")
	public Page<Facture> findFactureByMontantTtc(@Param("factureInfo") Double factureInfo,@Param("client_id") String client_id , Pageable pageable);
	@Query("select f from Facture f where LOWER(f.client.id) = LOWER(:client_id) AND ( f.montantTtc <= :factureInfo) Order by f.id DESC")
	public Page<Facture> findFactureBy_MontantTtc(@Param("factureInfo") Double factureInfo,@Param("client_id") String client_id , Pageable pageable);
	
	@Query("select f from Facture f where LOWER(f.client.id) = LOWER(:client_id) AND ( f.montantHt >= :factureInfo  ) Order by f.id DESC")
	public Page<Facture> findFactureByMontantHt(@Param("factureInfo") Double factureInfo,@Param("client_id") String client_id , Pageable pageable);
	@Query("select f from Facture f where LOWER(f.client.id) = LOWER(:client_id) AND ( f.montantHt <= :factureInfo  ) Order by f.id DESC")
	public Page<Facture> findFactureBy_MontantHt(@Param("factureInfo") Double factureInfo,@Param("client_id") String client_id , Pageable pageable);
	
	@Query("select f from Facture f where LOWER(f.client.id) = LOWER(:client_id) AND (  f.montantTva  >= :factureInfo  ) Order by f.id DESC")
	public Page<Facture> findFactureByMontantTva(@Param("factureInfo") Double factureInfo,@Param("client_id") String client_id , Pageable pageable);
	@Query("select f from Facture f where LOWER(f.client.id) = LOWER(:client_id) AND (  f.montantTva  <= :factureInfo ) Order by f.id DESC")
	public Page<Facture> findFactureBy_MontantTva(@Param("factureInfo") Double factureInfo,@Param("client_id") String client_id , Pageable pageable);
	
	@Query("select f from Facture f where LOWER(f.client.id) = LOWER(:client_id) AND (f.consomation >= :factureInfo ) Order by f.id DESC")
	public Page<Facture> findFactureByConsommation(@Param("factureInfo") Double factureInfo,@Param("client_id") String client_id , Pageable pageable);
	@Query("select f from Facture f where LOWER(f.client.id) = LOWER(:client_id) AND (f.consomation <= :factureInfo ) Order by f.id DESC")
	public Page<Facture> findFactureBy_Consommation(@Param("factureInfo") Double factureInfo,@Param("client_id") String client_id , Pageable pageable);

	@Query("select f from Facture f where LOWER(f.client.id) = LOWER(:client_id) AND (f.annee >= :factureInfo ) Order by f.id DESC")
	public Page<Facture> findFactureByAnnee(@Param("factureInfo") Integer factureInfo,@Param("client_id") String client_id , Pageable pageable);
	@Query("select f from Facture f where LOWER(f.client.id) = LOWER(:client_id) AND (f.annee <= :factureInfo ) Order by f.id DESC")
	public Page<Facture> findFactureBy_Annee(@Param("factureInfo") Integer factureInfo,@Param("client_id") String client_id , Pageable pageable);
	
	
	
	
	/////////////////////////////// ROOT
	
	
	@Query("select f from Facture f where f.id like %:factureInfo% OR f.mois like %:factureInfo% OR f.montantTtc like %:factureInfo% Or f.montantHt like %:factureInfo% OR f.montantTva like %:factureInfo% OR f.consomation like %:factureInfo% OR f.annee like %:factureInfo% Order by f.id DESC")
	public Page<Facture> findFactureByStringRoot(@Param("factureInfo") String factureInfo, Pageable pageable);
	
	
	@Query("select f from Facture f where f.montantTtc >= :factureInfo Order by f.id DESC")
	public Page<Facture> findFactureByMontantTtcRoot(@Param("factureInfo") Double factureInfo, Pageable pageable);
	@Query("select f from Facture f where f.montantTtc <= :factureInfo Order by f.id DESC")
	public Page<Facture> findFactureBy_MontantTtcRoot(@Param("factureInfo") Double factureInfo, Pageable pageable);
	
	@Query("select f from Facture f where f.montantHt >= :factureInfo  Order by f.id DESC")
	public Page<Facture> findFactureByMontantHtRoot(@Param("factureInfo") Double factureInfo, Pageable pageable);
	@Query("select f from Facture f where  f.montantHt <= :factureInfo  Order by f.id DESC")
	public Page<Facture> findFactureBy_MontantHtRoot(@Param("factureInfo") Double factureInfo , Pageable pageable);
	
	@Query("select f from Facture f where   f.montantTva  >= :factureInfo   Order by f.id DESC")
	public Page<Facture> findFactureByMontantTvaRoot(@Param("factureInfo") Double factureInfo , Pageable pageable);
	@Query("select f from Facture f where   f.montantTva  <= :factureInfo  Order by f.id DESC")
	public Page<Facture> findFactureBy_MontantTvaRoot(@Param("factureInfo") Double factureInfo, Pageable pageable);
	
	@Query("select f from Facture f where f.consomation >= :factureInfo  Order by f.id DESC")
	public Page<Facture> findFactureByConsommationRoot(@Param("factureInfo") Double factureInfo, Pageable pageable);
	@Query("select f from Facture f where f.consomation <= :factureInfo  Order by f.id DESC")
	public Page<Facture> findFactureBy_ConsommationRoot(@Param("factureInfo") Double factureInfo, Pageable pageable);

	@Query("select f from Facture f where f.annee >= :factureInfo  Order by f.id DESC")
	public Page<Facture> findFactureByAnneeRoot(@Param("factureInfo") Integer factureInfo, Pageable pageable);
	@Query("select f from Facture f where f.annee <= :factureInfo  Order by f.id DESC")
	public Page<Facture> findFactureBy_AnneeRoot(@Param("factureInfo") Integer factureInfo, Pageable pageable);

}
