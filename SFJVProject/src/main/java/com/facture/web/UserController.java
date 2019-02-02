package com.facture.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.facture.bean.Client;
import com.facture.bean.Tranche;
import com.facture.bean.User;
import com.facture.service.ClientService;
import com.facture.service.LocaleService;
import com.facture.service.TrancheService;
import com.facture.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController {

	
	@Autowired
	UserService userService;
	@Autowired
	ClientService clientService;
	@Autowired
	LocaleService localeService;
	@Autowired
	TrancheService trancheService;
	
	private String user_id= "";
	
	 @RequestMapping("/register")	
	 public String signUpForm(Model model) {
		 model.addAttribute("titel", "SignUp");	 
		return "register";
	}
	 

	 @RequestMapping(value="/signup" , method = RequestMethod.POST) 
	 public String signUp(Model model,
			 @RequestParam( name="username", defaultValue= "") String username , 
			 @RequestParam( name="password", defaultValue= "") String password,
			 @RequestParam( name="email", defaultValue= "") String email,
			 @RequestParam( name="rPrivilegeToken", defaultValue= "") String rPrivilegeToken
			 ) {
		 
//		 model.addAttribute("username", username);
//		 model.addAttribute("password", password);
//		 model.addAttribute("email", email);

		
//		 System.out.println( username+ "  " + password+ "  " + email+ "  " +cin+ "  " +nom+ "  " + prenom + "  " + adresse);
		 
		 int res = userService.signUpCheck(username, password, email);
		 if (res == 1) {
			userService.saveUser(username, password, email,rPrivilegeToken);	
			return "redirect:/login";
		 }

		 return "redirect:/user/register?error";
	 }
	
	 
	 @RequestMapping(value="/client/info" , method = RequestMethod.POST)	
	 public String clientInfo(Model model,String email,
			 @RequestParam( name="cin", defaultValue= "") String cin,
			 String nom,String prenom,String adresse, 
			 @RequestParam( name="telephone", defaultValue= "0")  Long telephone) {
//		 user_id = principal.getName();
		 System.out.println(cin +" before "+nom +"  "+prenom +"  "+email +"  "+adresse+"  "+ telephone +"  "+  user_id);

		 System.out.println(cin +"  "+nom +"  "+prenom +"  "+email +"  "+adresse+"  "+ telephone +"  "+  user_id);
		 int res = clientService.signUpCheck(getUser_id(),cin, nom, prenom, adresse, email, telephone);
		 if (res == 2) {	

		    clientService.saveClient(cin, nom, prenom, adresse, email, getUser_id(), telephone);
//		    Locale c_locale = localeService.findLocaleByClientId(client.getId()).get(0);
//			if(c_locale != null) {
//			userService.upGradeUser(getUser_id());
//			return "redirect:/login";
//			}
			return "redirect:/user/profile";
		 }
		 if (res == 1) {	

			    Optional<Client> editedClient = clientService.findClient(getUser_id());
			    clientService.saveClient(editedClient.get().getId(), nom, prenom, adresse, email, getUser_id(), telephone);
				return "redirect:/user/profile";
			 }
		return "redirect:/user/profile?error";
	}

     
	 @RequestMapping(value="/locale/info" , method = RequestMethod.POST)
	 public String localeInfo(Model model, String n_contrat,int d_annee, int d_mois,
			 String l_adresse) {
	          
		Optional<Client> client_info = clientService.findClient(getUser_id());  
		if(client_info.isPresent()) {
	    int res =   localeService.saveLocale(n_contrat, d_annee, d_mois, l_adresse, client_info.get());
        if(res == 1) {
        	System.out.println("dfjkjkdf your here" );
        	userService.upGradeUser(getUser_id());
			return "redirect:/login";
	    }
	   
		}
		
		 System.out.println(n_contrat +"  "+d_annee +"  "+d_mois +"  "+l_adresse  );
         
		  
		 
		 return "redirect:/user/profile?error";
	 }
	 
	 
	 @RequestMapping(value="/remove" ,method = RequestMethod.POST)
	 public String removeUserByAdmin(String u_ser_id) {
		if(!u_ser_id.isEmpty() && u_ser_id != null ) {
			 userService.removeUser(u_ser_id);
		}
		
		return "redirect:/home";
	 }
	 
	 
	 @RequestMapping(value="/remove/account" ,method = RequestMethod.POST)
	 public String removeMyAccount() {
    	setUser_id(SecurityContextHolder.getContext().getAuthentication().getName());
		 System.out.println("account remove + id "+ user_id);;
		if(!getUser_id().isEmpty() && getUser_id() != null ) {
			 userService.removeUser(getUser_id());
			 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(auth.getAuthorities());
			 authorities.clear();
			 Authentication uAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
			 SecurityContextHolder.getContext().setAuthentication(uAuth);
		}
		
		return "redirect:/login?AccountDeleted";
	 }
	

	@RequestMapping(value="/recovery", method= RequestMethod.POST)
	 public String passwordRecovery(String username_ , String newPassword) {
		if(!username_.isEmpty() && username_ !=null && !newPassword.isEmpty() && newPassword != null) {
		userService.passwordRecovery(username_, newPassword,"");
		}
		return "redirect:/home";
	 }
	

	@RequestMapping(value="/changeMyPassword", method= RequestMethod.POST)
	 public String changePassword( String cemail, String copassword,String cnpassword) {
    System.out.println(cemail +" "+ copassword+" "+ cnpassword);
	if( getUser_id()!=null && !getUser_id().isEmpty()) {
	    System.out.println(cemail +" "+ copassword+" "+ cnpassword +getUser_id() );

           Boolean res = userService.bCryptCheckMatches(copassword, getUser_id());
		   System.out.println("your check res " + res);
		    System.out.println(cemail +" "+ copassword+" "+ cnpassword + " " +res );

		   if(res == true) {
			     userService.passwordRecovery(getUser_id(), cnpassword,cemail);
			     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(auth.getAuthorities());
				 authorities.clear();
				 Authentication uAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
				 SecurityContextHolder.getContext().setAuthentication(uAuth);
				 return "redirect:/login?passowrdChangedSuccessfully";
		   }
	}
		
		
		//		if(!username_.isEmpty() && username_ !=null && !newPassword.isEmpty() && newPassword != null) {
//		userService.passwordRecovery(username_, newPassword);
//		}
		return "redirect:/user/profile?active=Account Setting&Error";
	 }
	
	
	@RequestMapping("/profile")
	 public String myProfile(Model model,
			 @RequestParam( name="active", defaultValue="") String active) {

    	setUser_id(SecurityContextHolder.getContext().getAuthentication().getName());
		if(active.isEmpty() || active.equals(null)) {
			model.addAttribute("active", "Profile");
		}else {
			model.addAttribute("active", active);
		}
		
		/////////////////////////User Details///////////////////////////////////////////
		Optional<User> user_info_ = userService.getUserById(getUser_id());
		model.addAttribute("user_info_", user_info_.get());
		/////////////////////////////////////////////////////////////////////
		 Optional<Client> clientInfo = clientService.findClient(user_id);
		 if(clientInfo.isPresent()) {
		  model.addAttribute("client_Info", clientInfo.get());
		  model.addAttribute("clientInfo", clientInfo.get().getId());	 
		  model.addAttribute("userInfo",clientInfo.get().getNom().toUpperCase()
				  +" " +clientInfo.get().getPrenom().substring(0, 1).toUpperCase()+clientInfo.get().getPrenom().substring(1).toLowerCase());
		 
		 
		 }else {
			 model.addAttribute("clientInfo", " ");
		 }
		 //////////////////////////////////////////////////////////
		 
		 Optional<Client> client_1 = clientService.findClient(user_id);
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(auth.getAuthorities());	
		 System.out.println(authorities +" All my authorities");
//		 for (GrantedAuthority authorityGranter : authorities) {
//    	 if(authorityGranter.getAuthority().equals("ROLE_ROOT")  || authorityGranter.getAuthority().equals("ROLE_ADMIN")&& client_1.isPresent() ) {
//    		 System.out.println(authorityGranter);
//    		 System.out.println("im in the redirect home");
//    		 return "redirect:/home";
//    	 }
//		}
		 for (GrantedAuthority authorityGranter : authorities) {
		     Boolean rootIsPresent = false;
			 for (GrantedAuthority authority_Granter : authorities) {
				if(authority_Granter.getAuthority().equals("ROLE_ROOT")){
					rootIsPresent = true;
				 }
			   }
			 if(rootIsPresent == true){
				 model.addAttribute("clientInfo", "ROOT");
				 model.addAttribute("userInfo"," Adnan - E ");
			 }
			 if(authorityGranter.getAuthority().equals("ROLE_ADMIN")&& !client_1.isPresent() && rootIsPresent == false  ) {
	    		 userService.reduceGrade(user_id);
	    		 authorities.remove(authorityGranter);
				 Authentication uAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
				 SecurityContextHolder.getContext().setAuthentication(uAuth);
				 
	    	 }}
		 
		 List<Tranche> Wtranches = trancheService.findAllTrancheByType("Water");
		 model.addAttribute("Wtranches", Wtranches);
		 List<Tranche> Etranches = trancheService.findAllTrancheByType("Electricity");
		 model.addAttribute("Etranches", Etranches);
		
		 return "profile";
	 }
	 
//	@RequestMapping(value="/test" , method= RequestMethod.POST )
//	 public String myProfileee(Model model,String cin, String nom, String prenom) {
//		
//		System.out.println(cin + nom + prenom );
//
//		user_id = SecurityContextHolder.getContext().getAuthentication().getName();
//		 Optional<Client> clientInfo = clientService.findClient(user_id);
//		 if(clientInfo.isPresent()) {
//		  model.addAttribute("userInfo",clientInfo.get().getNom() +" " +clientInfo.get().getPrenom()); 
//		 }
//		
//		 return "profile";
//	 }
	 
	 @RequestMapping(value="/tranche" , method = RequestMethod.POST )
	public String AddOrEditTranche(@RequestParam( name="tranche_id", defaultValue="") String tranche_id,
			@RequestParam( name="t_type", defaultValue="") String t_type,
			@RequestParam( name="trancheMin", defaultValue="-1") Double trancheMin,
			@RequestParam( name="trancheMax", defaultValue="-1") Double trancheMax,
			@RequestParam( name="montant", defaultValue="-1") Double montant) {
		
		 System.out.println("your tranche id is 9857 " + tranche_id);
		 int res = trancheService.checkProcess(tranche_id, trancheMin, trancheMax, montant);
		if(res == 1) {
			System.out.println("im here 1");
			trancheService.editTranche(tranche_id, trancheMin, trancheMax, montant);
			return "redirect:/user/profile?active=Tranche Info";
		}
		if (res == 2) {
			System.out.println("im here 2");
			if(!t_type.isEmpty()) {
			trancheService.addTranche(tranche_id, t_type ,  trancheMin, trancheMax, montant);
			return "redirect:/user/profile?active=Tranche Info";
			}
		}
		
		
		return "redirect:/user/profile?active=Tranche Info&Error";
	}
	
	 
	 @RequestMapping("/selectedItem")
	 public String selectedItem(Model model,@RequestParam(name="selected", defaultValue="")String selected,
			 @RequestParam(name="selected", defaultValue="")String selectedFDisplay) {
		 
		 model.addAttribute("selected", selected);
		Optional<Tranche> selectedTranche = trancheService.findTranche(selected);
		if(selectedTranche.isPresent()) {
//			if(selectedTranche.get().getType().equalsIgnoreCase("Water")) {
//				model.addAttribute("selectedFDisplay", selected.substring(6));
//			}
//			if(selectedTranche.get().getType().equalsIgnoreCase("Electricity")) {
//				model.addAttribute("selectedFDisplay", selected.substring(12));
//			}
			model.addAttribute("selectedTranche", selectedTranche.get());
		    System.out.println("your tranche info are " + selectedTranche.get());
		}
		 return "forward:/user/profile?active=Tranche Info";
	 }
	 
	 
	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	 
	@RequestMapping(value="/tranche/remove" )
	 public String removeTranche(@RequestParam(name="idSelected" , defaultValue="") String idSelected) {
		 
		if (!idSelected.isEmpty()) {
			trancheService.removeTranche(idSelected);
			return "redirect:/user/profile?active=Tranche Info";
		}
		
		return "redirect:/user/profile?active=Tranche Info&Error";
	 }
	
	
}
