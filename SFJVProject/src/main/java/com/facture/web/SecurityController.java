package com.facture.web;




import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.facture.service.UserService;


@Controller
public class SecurityController {

	@Autowired
	UserService userService;
	
	
	
	 
	@RequestMapping(value="/login")
	public String login(Model model) {
		model.addAttribute("titel", "Login");
//		 String newUser = SecurityContextHolder.getContext().getAuthentication().getName();
//		 System.out.println("1" + newUser); 
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(auth.getAuthorities());	
		for (GrantedAuthority authorityGranter : authorities) {
    	 if(authorityGranter.getAuthority().equals("ROLE_ROOT") || authorityGranter.getAuthority().equals("ROLE_ADMIN") ||authorityGranter.getAuthority().equals("ROLE_USER")) {
    		 return "redirect:/default";
    	 }}
    return "login";
	}
	
	@RequestMapping(value="/")
	public String index (Model model, Principal principal) {
		String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("loginid", "Log in");
		////////////////////////////////
		 Boolean checked = false; 
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(auth.getAuthorities());
		 for (GrantedAuthority grantedAuthority : authorities) {
			if(grantedAuthority.getAuthority().equals("ROLE_USER") || grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				model.addAttribute("loginid", user_id);
				checked = true;
			}
		  }
		 model.addAttribute("checked", checked);
        /////////////////////////////////////////////
		return "index";		
	}
	
	@RequestMapping(value="/default" )
	public String fPage() {
		 int token = 0;
		 String username =  SecurityContextHolder.getContext().getAuthentication().getName();
		 Boolean res = userService.findUserRole(username, "ADMIN");
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(auth.getAuthorities());	
  		System.out.println("im working on it" + token);
 		 
		 if (res.equals(true)){
			for (GrantedAuthority grantedAuthorityCheck : authorities) {
				if(grantedAuthorityCheck.getAuthority().equals("ROLE_ADMIN")) {
					 token = 1;
						System.out.println("im admin in the DB ");
	
				}}
			System.out.println("im alomsot with it" + token);
			if(token == 0) {
			 authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			 Authentication uAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
			 SecurityContextHolder.getContext().setAuthentication(uAuth);
			 System.out.println("im with the admin authority" + token);
	     }}

		 for (GrantedAuthority authorityGranter : authorities) {
     	 if(authorityGranter.getAuthority().equals("ROLE_ROOT") || authorityGranter.getAuthority().equals("ROLE_ADMIN") ) {
     		 return "redirect:/home";}} 
		 
		 for (GrantedAuthority authorityGranter : authorities) {
     	 if(authorityGranter.getAuthority().equals("ROLE_USER")) {
     		System.out.println("i cant get the admin authority" + token);
     		 return "redirect:/user/profile";}}
		
		 return "redirect:/";
	}
	
}
