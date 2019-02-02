package com.facture.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;






@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	
	@Autowired
	private DataSource dataSource;
	
	
	 //BCrypt.hashpw(password, BCrypt.gensalt())
	
//	public void jddgjs() { pour cree un utilisateur
//	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
//	bc.encode(passwor);
//	}
	
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().dataSource(dataSource)
	    .passwordEncoder(passwordEncoder())
	    .usersByUsernameQuery("Select id as principal, password as credentials, active from User where id=?")
	    .authoritiesByUsernameQuery("select id as principal, role as role from user_role where id=?").rolePrefix("ROLE_");
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
         
		   http.formLogin().loginPage("/login").defaultSuccessUrl("/default");  
		   http.authorizeRequests().antMatchers("/user/recovery","/user/remove","/user/selectedItem","/user/tranche"
				   ,"/user/tranche/remove").hasRole("ROOT");
           http.authorizeRequests().antMatchers("/home","/home/create","/fconsommation","/home/remove").hasRole("ADMIN");
           http.authorizeRequests().antMatchers("/user/profile","/user/client/info","/user/locale/info","/user/profile",
        		   "/user/changeMyPassword","/user/remove/account").hasRole("USER");
          
//           http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");
           http.logout().logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/login?logout");
           
           

	}
	
	
	
}
