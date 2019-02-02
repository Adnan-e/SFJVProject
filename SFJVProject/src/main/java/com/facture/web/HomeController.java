package com.facture.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.facture.bean.Client;
import com.facture.bean.Facture;
import com.facture.bean.Locale;
import com.facture.bean.User;
import com.facture.deo.FactureRepository;
import com.facture.service.ClientService;
import com.facture.service.FactureService;
import com.facture.service.LocaleService;
import com.facture.service.TrancheConsommationService;
import com.facture.service.UserService;





@Controller
@RequestMapping(value="/home")
public class HomeController {
	
	
	 @Autowired
	 ClientService clientService;
	 @Autowired
	 UserService userService;
	
	 @Autowired
	 FactureService factureService;

	 @Autowired
	 FactureRepository factureRepository;
	 @Autowired
	 TrancheConsommationService trancheConsommationService;
	
	 
	 @Autowired
	 LocaleService localeService;

	 public String user_id;
	 Page<Facture> userFacturesImp;

    
	 @RequestMapping()
	public String homePage(Model model,
		
			@RequestParam( name="page", defaultValue="0") int page ,
			@RequestParam( name="size", defaultValue="7") int size,
			@RequestParam( name="page", defaultValue="0") int newpage ,
			@RequestParam( name="pageNp", defaultValue="0") int pageNp,
// Search field			
			@RequestParam( name="s_id", defaultValue="") String s_id,
		 @RequestParam( name="online", defaultValue="") String online,
		//// das soll string sein
		 @RequestParam( name="client_id_s", defaultValue="") String client_id_s,
		 @RequestParam( name="locale_id_s", defaultValue="") String locale_id_s,
		 @RequestParam( name="anneeMin_s", defaultValue="0") Integer anneeMin_s,
		 @RequestParam( name="anneeMax_s", defaultValue="0")  Integer anneeMax_s,
		 @RequestParam( name="mois_s", defaultValue="") String mois_s,
		 @RequestParam( name="consommation_s", defaultValue="0")  Double consommation_s
		 ){
		 //////////////////////////////////////////////////////////////////////
		 model.addAttribute("client_id_s", client_id_s);
		 model.addAttribute("locale_id_s", locale_id_s);
		 model.addAttribute("anneeMin_s", anneeMin_s);
		 model.addAttribute("anneeMax_s", anneeMax_s);
		 model.addAttribute("mois_s", mois_s);
		 model.addAttribute("consommation_s", consommation_s);
		 String findit=" ";
		 
//		 System.out.println( "try it vs" + "im her"+consommation + "im her" + client_id + "im her" + locale_id+ "im her" +anneeMin + "im her" + anneeMax+ "im her" + mois);
        
		/*
		 ||
			anneeMin != 0 || !anneeMin.equals(null)  || anneeMax != 0 || !anneeMax.equals(null)
			||  !mois.equals(null) || !mois.isEmpty() || consommation !=0  || !consommation.equals(null)
		 */
		 

		 if( !client_id_s.isEmpty() && !client_id_s.equals(null) ||  !locale_id_s.isEmpty() && !locale_id_s.equals(null) ||anneeMin_s != 0 && !anneeMin_s.equals(null) ||
				 anneeMax_s != 0 && !anneeMax_s.equals(null) || consommation_s !=0 && !consommation_s.equals(null) ||
				 !mois_s.isEmpty() && !mois_s.equals(null)) { 
			 findit = "search";
			// model.addAttribute("findit", findit );
			 System.out.println("im her" + findit);
		 }
		 
		///////////////////////////////////////////////////////////////////////
		  setUser_id(SecurityContextHolder.getContext().getAuthentication().getName());
		  Boolean res = userService.findUserRole(getUser_id(), "ROOT");

		  List<Facture> userFacturesL = new ArrayList<>();
//		  PageImpl<Facture> userFacturesImp = new PageImpl<Facture>(userFacturesL,new PageRequest(page, size),userFacturesL.size());
		  Page<Facture> userFactures = getUserFacturesImp(userFacturesL,new PageRequest(page, size));
//		  Page<Facture> userFactures = null;
		  
		  if (res.equals(true)) {
			  online = "ROOT";
			  model.addAttribute("online", online);  
			    model.addAttribute("titel", "home");
				model.addAttribute("brandtext", "ROOT");
				model.addAttribute("brandbig", "Dashboard");
				model.addAttribute("brandsmall", "RD");
		  }else {
			 Optional<Client> clientInfo = clientService.findClient(getUser_id());
			 if(clientInfo.isPresent()) {
			  model.addAttribute("userInfo",clientInfo.get().getNom() +" " +clientInfo.get().getPrenom()); 
				model.addAttribute("titel", "home");
				model.addAttribute("brandtext", "Facture");
				model.addAttribute("brandbig", "Dashboard");
				model.addAttribute("brandsmall", "FD");
			 }
		  }
		 //Search//////////////////////////////////////////////////////////////////
		  model.addAttribute("s_id", s_id);
		
		 
		  
		 if(res.equals(false) ) {	 
			    List<Locale> userLocales = localeService.findLocaleByClient(getUser_id());
			    model.addAttribute("userLocales", userLocales);
			    List<Locale> mylocales = localeService.findLocaleByClient(getUser_id());
		         model.addAttribute("mylocales", mylocales);
		         System.out.println(mylocales +"And this is your getUser_id()  "+getUser_id());
			 if (!s_id.equals("")){
			 userFactures = factureService.fSearch(s_id, getUser_id(), page, size);
			 }
		 if(findit.equals("search") && userFactures.getContent().isEmpty()){
		 Optional<Client> clientInfo_S = clientService.findClient(getUser_id());
        userFacturesL = factureService.findByCritaria(clientInfo_S.get().getId(), locale_id_s, anneeMin_s, anneeMax_s, mois_s, consommation_s);
		userFactures = getUserFacturesImp(userFacturesL, new PageRequest(page, size));
		 }
		 
		 if(userFactures.getContent().isEmpty() && s_id.equals("") && !findit.equals("search")) {
			 userFactures = factureService.findFactureByClient(getUser_id(),page,size); 
			  }
		 }
		 ////////////// ROOT //////////////////////////////////////////////////
         else {
			  System.out.println("bin hier auch " + findit);
//			  List<Locale> mylocales = localeService.find
//		         model.addAttribute("mylocales", mylocales);
			  
			  List<Locale> userLocales = localeService.findAll();
			  model.addAttribute("userLocales", userLocales);
			  /////////////////////////////////  
			    model.addAttribute("user_id", getUser_id());
			    List<User> myusers = userService.findAll();
				model.addAttribute("myusers", myusers);
				/////////////////////////
			     List<Client> myclients = clientService.findAll();
		         model.addAttribute("myclients", myclients);
		         if (!s_id.equals("")){
					 userFactures = factureService.fSearchRoot(s_id, page, size);
		         }
				 if(findit.equals("search") && userFactures.getContent().isEmpty()) {
		         userFacturesL = factureService.findByCritaria(client_id_s, locale_id_s, anneeMin_s, anneeMax_s, mois_s, consommation_s);
//			     model.addAttribute("factures", userFactures.getContent()); }
		         userFactures = getUserFacturesImp(userFacturesL, new PageRequest(page, size));
				 }
		         if (userFactures.getContent().isEmpty() && s_id.equals("") && !findit.equals("search")) {
					  System.out.println("bin hier auch in " + findit);
            
		         userFactures = factureService.findAll(page, size);
		         }
		         }
		 
    	 model.addAttribute("factures", userFactures.getContent());
    	 model.addAttribute("currentPage", page);
    	 int[] pages = new int [userFactures.getTotalPages()] ;
    	 int lastPage = userFactures.getTotalPages() -1 ;
    	 model.addAttribute("lastPage", lastPage);
    	 model.addAttribute("pages", pages);
		 /////////////////////////////////////////////////////////////////////
    	 Page<Facture> userFacturesNP;
    	 if (res.equals(false)) {userFacturesNP = factureService.findFactureNP(getUser_id(), pageNp, size);}
    	 else {userFacturesNP = factureService.findAllFactureNP(pageNp, size);}
    	 
    	 model.addAttribute("userFacturesNP", userFacturesNP);
    	 model.addAttribute("currentPageNp", pageNp);
    	 int[] pagesNp = new int [userFacturesNP.getTotalPages()] ;
    	 int lastPageNp = userFacturesNP.getTotalPages() -1 ;
    	 model.addAttribute("lastPageNp", lastPageNp);
    	 model.addAttribute("pagesNp", pagesNp);	 
    	 //////////////////////////////////////////////////////////////////////
	
		///////////////////////////////////////////////////////////////////////
	   
	    //////////////////////////////////////////////////////////////

		
	    
	    
//	    System.out.println("here at home " +idSelected);
		return "home";}
	
	 
	
	 
	 @RequestMapping(value="/create" ,method = RequestMethod.POST)
	 public String create(Model model,Facture facture, double consomation,int annee, String month, String localeAdresse,
			 @RequestParam(name="t_type",defaultValue="" ) String t_type,
			 @RequestParam(name="c_lient_id",defaultValue="" ) String c_lient_id) {
		 Optional<Client> client_1 = clientService.findClient(getUser_id());
	     Optional<Client> client_From_Admin = clientService.findClientByid(c_lient_id);
	     
		 if (!client_1.isPresent() && !client_From_Admin.isPresent()) {
			 return "redirect:/home/user/info";
		 }
//	     System.out.println( " your client details are :" + client_1.get());
	     
	     if(client_From_Admin.isPresent()) {
	     facture.setClient(client_From_Admin.get());
	     }
	     if(client_1.isPresent() && !client_From_Admin.isPresent() ) {
		     facture.setClient(client_1.get());
	     }
	     
	     System.out.println( " your client details after are :" + facture.getClient().getId());

//		 model.addAttribute("consomation",consomation);
//		 model.addAttribute("annee",annee);
//		 model.addAttribute("month", month);
//		 model.addAttribute("localeAdresse", localeAdresse);
		 Locale locale = localeService.findLocale(localeAdresse);
		 if(!localeAdresse.isEmpty()) {
			 facture.setLocale(locale);
			 }
		 
		 if(!month.isEmpty()) {
		 facture.setMois(month);
		 }
		 facture.setAnnee(annee);
		 facture.setConsomation(consomation);
         factureService.save(t_type,facture);
		 return "redirect:/home";
		
	 }
	 
	
	 
//	 @RequestMapping(value = "/pdf")
//	 public void pdfexport() throws JRException {
//		 Map<String, Object> params = (Map<String, Object>) factureService.factureReport(user_id);
//		 JasperPrint jP = JasperFillManager.fillReport("/reports/SFacture.jrxml", params, new JREmptyDataSource());
//		 JRPdfExporter je = new JRPdfExporter();
//		 je.setExporterInput(new SimpleExporterInput(jP));
//		 je.setExporterOutput(new SimpleOutputStreamExporterOutput("/reports/SFacture"));
//		 
//		 SimplePdfExporterConfiguration sc = new SimplePdfExporterConfiguration();
//		 sc.setAllowedPermissionsHint("PRINTING");
//		 
//		 SimplePdfReportConfiguration sr = new SimplePdfReportConfiguration();
//		 sr.setSizePageToContent(true);
//		 
//		 
//		 je.setConfiguration(sr);
//		 je.setConfiguration(sc);
//		 
//	 je.exportReport();
//		 
//	 }
	
//	 @RequestMapping(value = "/pdf")
//    public void pdfexport() throws JRException, FileNotFoundException {
//		 String userdirectory = System.getProperty("user.home");
//		 String link = userdirectory + File.separatorChar + "SFacture.pdf" ;
//		 Map<String, Object> params = (Map<String, Object>) factureService.factureReport(user_id);
//		 JasperPrint jP = JasperFillManager.fillReport("/reports/SFacture.jrxml", params, new JREmptyDataSource());
//		 OutputStream outputStream = new FileOutputStream(new File(link));
//         JasperExportManager.exportReportToPdfStream(jP, outputStream);
//         System.out.println("is compiled");
//	 }
	 
	 @RequestMapping(value="/remove" )
	 public String remove(@RequestParam(name="idSelected" , defaultValue= "") String idSelected , Model model,
			 @RequestParam(name="lastPage" , defaultValue= "0")int lastPage,
			 @RequestParam(name="page" , defaultValue= "0") int page)  {
        if(idSelected != null && !idSelected.isEmpty()) {
         Optional<Facture> facture = factureService.selectedFacture(idSelected);
         factureService.removeAll(facture.get());
         model.addAttribute("lastPage", lastPage);
		 return "forward:/home";}
        return "redirect:/home";
	 }
//	 @RequestMapping("/search")
//	 public String searchBycritiria(
//				@RequestParam( name="page", defaultValue="0") int page ,
//				@RequestParam( name="size", defaultValue="7") int size,
//			 String e, Model model) {
//		 model.addAttribute("e",e);
//		Page<Facture> facture = factureRepository.findFactureByname(e, user_id, new PageRequest(page, size));
//		
//		model.addAttribute("factures",facture );
//		
//		/////
//		
//		 model.addAttribute("currentPage", page);
//    	 int[] pages = new int [facture.getTotalPages()] ;
//    	 int lastPage = facture.getTotalPages() -1 ;
//    	 model.addAttribute("lastPage", lastPage);
//    	 model.addAttribute("pages", pages);
//		 
//		 return "home";
//	 }
	 
//	 @RequestMapping("/user/info")
//	 public String info() {
//		 Optional<Client> client_1 = clientService.findClient(user_id);
//
//		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(auth.getAuthorities());	
//		 System.out.println(authorities +" All my authorities");
//		 for (GrantedAuthority authorityGranter : authorities) {
//    	 if(authorityGranter.getAuthority().equals("ROLE_ROOT")  || authorityGranter.getAuthority().equals("ROLE_ADMIN")&& client_1.isPresent() ) {
//    		 System.out.println(authorityGranter);
//    		 System.out.println("im in the redirect home");
//    		 return "redirect:/home";
//    	 }
//		}
//		 for (GrantedAuthority authorityGranter : authorities) {
//	    	 if(authorityGranter.getAuthority().equals("ROLE_ADMIN")&& !client_1.isPresent() ) {
//	    		 userService.reduceGrade(user_id);
//	    		 authorities.remove(authorityGranter);
//				 Authentication uAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
//				 SecurityContextHolder.getContext().setAuthentication(uAuth);
//				 
//	    	 }}
//		 
//		 System.out.println(" im going to return info");
//
//		 return "info";
//	 }
//



	public Page<Facture> getUserFacturesImp( List<Facture> userFacturesL,Pageable pageable ) {
//		int start =  (int) pageable.getOffset();
//		int end = (start + pageable.getPageSize()) > userFacturesL.size() ?userFacturesL.size():(start + pageable.getPageSize());
//		 int sizeof = userFacturesL.size();
//		 System.out.println(sizeof + "the size of the list " );
		userFacturesImp = new PageImpl<Facture>(userFacturesL,pageable,7);
		return userFacturesImp;
	}




	public void setUserFacturesImp(Page<Facture> userFacturesImp) {
		this.userFacturesImp = userFacturesImp;
	}




	public String getUser_id() {
		return user_id;
	}




	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}




	




	

	 
	 
	 
	 
	 
	 /////////////////////////////////////////////////////
//	 @RequestMapping("/search")
//	 public String search(Model model,Client client, Locale locale,
//			 Integer anneeMin,Integer anneeMax, String mois,Double consommation, 
//			 @RequestParam( name="page", defaultValue="0") int page ,
//			 @RequestParam( name="size", defaultValue="7") int size
//			 ) {
//    ///////////////////////////////////////////////////////////////////////		 
//		 model.addAttribute("myclient", client);
//		 model.addAttribute("mylocale", locale);
//		 model.addAttribute("anneeMin", anneeMin);
//		 model.addAttribute("anneeMax", anneeMax);
//		 model.addAttribute("mois", mois);
//		 model.addAttribute("consommation", consommation);
//	     Page<Facture> userFactures = factureService.findByCritaria(client, locale, anneeMin, anneeMax, mois, consommation,new PageRequest(page, size));
//	     model.addAttribute("newfactures", userFactures.getContent());
//   /////////////////////////////////////////////////////////////////////	
//		
//	 return "forward:/home";
//	 }
//	 
	 
	 
	 
	 
}
