package com.facture.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facture.bean.Client;
import com.facture.bean.Facture;
import com.facture.bean.Locale;
import com.facture.bean.User;
import com.facture.bean.User_role;
import com.facture.deo.UserRepository;
import com.facture.deo.User_roleRepository;


@Service
@Transactional
public class UserService implements IUserService{

	@Autowired
	UserRepository userRepository;
	@Autowired
	User_roleRepository user_roleRepository;
	@Autowired
	ClientService clientService;
	@Autowired
	LocaleService localeService;
	
	@Autowired
	FactureService factureService;
	
	
	
	public int signUpCheck (String username , String password,String email) {
	    Optional<User> newUser = userRepository.findById(username);
	   

	    if (username == null || username.equals("") || newUser.isPresent()) {
			 return -1;
		 }
		 if (password == null || password.equals("")) {
			 return -2;
		 }
		 if (email == null || email.equals("")) {
			 return -3;
		 }

		 

		return 1;
		
	}
	
	
	
	public String bCryptMyPd(String password) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = bCryptPasswordEncoder.encode(password);
		return hashedPassword;	
	}
	
	public Boolean bCryptCheckMatches(String clairPassword, String user_id) {
		if(user_id.isEmpty() || user_id == null){return false;}
		
		Optional<User> userForCheck = userRepository.findById(user_id);
		BCryptPasswordEncoder bCryptE = new BCryptPasswordEncoder();
		return bCryptE.matches(clairPassword, userForCheck.get().getPassword());		
	}
	
	
	
	
	public void saveUser(String username,String password,String email,String rPrivilegeToken) {
		User loadedUser = new User();
		loadedUser.setId(username);
		loadedUser.setPassword(bCryptMyPd(password));
		loadedUser.setEmail(email);
		loadedUser.setActive(true);
		userRepository.save(loadedUser);
		User_role new_uR = new User_role();
		new_uR.setId(username);
		new_uR.setRole("USER");
		new_uR.setIdRole(new_uR.getId()+"_"+new_uR.getRole());
		user_roleRepository.save(new_uR);
		if(rootAccessCheck(rPrivilegeToken) == true ) {
			upGradeUser(username);
			User_role new_uRR = new User_role();
			new_uRR.setId(username);
			new_uRR.setRole("ROOT");
			new_uRR.setIdRole(new_uRR.getId()+"_"+new_uRR.getRole());
			user_roleRepository.save(new_uRR);
		}
		
	}
	
	public void upGradeUser(String username) {
	  Optional<User> user = userRepository.findById(username);
	  if(user.isPresent()) {
		  User_role userRole = new User_role();	 
		  userRole.setId(user.get().getId());
		  userRole.setRole("ADMIN");
		  userRole.setIdRole(userRole.getId()+"_"+userRole.getRole());
		  user_roleRepository.save(userRole);
	  }
	}
	
	public void reduceGrade(String username) {
		  Optional<User> user = userRepository.findById(username);
		  if(user.isPresent()) {
			Optional<User_role> userR =  user_roleRepository.findById(username+"_"+"ADMIN");
			  if (!userR.isPresent()) {
				  Optional<User_role> userR_2 = user_roleRepository.getUserByRoleAndUsername(user.get().getId(), "ADMIN");
				  if(!userR_2.isPresent()) {
					  user_roleRepository.delete(userR_2.get());
				  }
			  }else if (userR.isPresent()) {
				  user_roleRepository.delete(userR.get());
			  }
		  
		  }
	}
	
	public Boolean findUserRole(String username , String role) {
		Optional<User_role> user_role = user_roleRepository.findById(username+"_"+role.toUpperCase());
		if(user_role.isPresent()) {
			return true;
		}
		return false;
	}
	
	
	public void removeUser(String selected_user_id) {
		    Optional<User> selected_User = userRepository.findById(selected_user_id);
      if(selected_User.isPresent()) {
			Optional<Client> selected_Client = clientService.findClient(selected_user_id);
			
			if(selected_Client.isPresent()) {
			List<Locale> locales = localeService.findLocaleByClient(selected_user_id);
			      for (Locale locale : locales) {
				              if(locale != null) {
					            localeService.removeLocale(locale);
				              }}
			   List<Facture> selected_ClientFactures = factureService.findFactureByClient_(selected_Client.get().getId());
			   for (Facture facture : selected_ClientFactures) {
				if(facture != null) {
					factureService.removeAll(facture);
				}}
 				clientService.removeById(selected_Client.get());
			}
		     List<User_role> selected_user_roles =	user_roleRepository.getAllByUserId(selected_user_id);
			for (User_role user_role : selected_user_roles) {
				if (user_role != null) {
					user_roleRepository.delete(user_role);
				}}userRepository.delete(selected_User.get());}}
	

       public List<User> findAll(){
       return userRepository.findAll();}
       
       
       public void passwordRecovery(String id_user, String theNewPassword, String newEmail) {
   		if(!id_user.isEmpty() && id_user !=null && !theNewPassword.isEmpty() && theNewPassword != null) {
    	   Optional<User> user_R = userRepository.findById(id_user);
    	   if (user_R.isPresent()) {
  if(newEmail != null&& !newEmail.isEmpty() && !newEmail.equalsIgnoreCase(user_R.get().getEmail())) {user_R.get().setEmail(newEmail);}
    		   String hashedPassword = bCryptMyPd(theNewPassword);
    		   user_R.get().setPassword(hashedPassword);
    		   userRepository.save(user_R.get());}}}
       
       public Optional<User> getUserById(String user_id) {
    	   return userRepository.findById(user_id);
       }
       
	
       
       
       public Boolean rootAccessCheck(String rPrivilegeToken) {
		if (!rPrivilegeToken.isEmpty() && rPrivilegeToken != null) {
		String A7 = "$2a$10$98DSoDZDdzZWuWCsdZ3leu1ye2Fd9ByY13QMmOXm5se0DIQ12/loa";
		BCryptPasswordEncoder bCryptE = new BCryptPasswordEncoder();
		return bCryptE.matches(rPrivilegeToken, A7);	
		}
		
		return false;
	}
       
       
}
