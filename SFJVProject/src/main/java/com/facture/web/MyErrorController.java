package com.facture.web;


import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController{

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@RequestMapping(value="/error")
	public String error(HttpServletRequest http, Model model){
		
		Object status = http.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	     
	        if(statusCode == HttpStatus.NOT_FOUND.value() || statusCode == HttpStatus.METHOD_NOT_ALLOWED.value() ) {
	        	model.addAttribute("pic", "/others/img/404.png");
	        	model.addAttribute("titel", "Error 404" );
	        	model.addAttribute("code", "404" );
	        	model.addAttribute("statue", "Page not found - The page that you're locking for is not available ");
	            return "errors";
	        }
	        if(statusCode == HttpStatus.FORBIDDEN.value()) {
	        	model.addAttribute("pic", "/others/img/403.png");
	        	model.addAttribute("titel", "Error 403" );
	        	model.addAttribute("code", "403" );
	        	model.addAttribute("statue", "Access Denied - Your not allowed to Access this Resource");
	            return "errors";
	        }
	        if(statusCode == HttpStatus.BAD_REQUEST.value()) {
	        	model.addAttribute("pic", "/others/img/400.png");
	        	model.addAttribute("titel", "Error 400" );
	        	model.addAttribute("code", "400" );
	        	model.addAttribute("statue", "Bad Request - Please make sure that all information you entered are correct");
	            return "errors";
	        }
	        if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	        	model.addAttribute("pic", "/others/img/500.png");
	        	model.addAttribute("titel", "Error 500" );
	        	model.addAttribute("code", "500" );
	        	model.addAttribute("statue", "Oops, Something went wrong at our end. Dont worry, it's not you, it's us. sorry about that. please try again later");
	            return "errors";
	        }
	        
		
		}
		
		return null;
	}
	
	
//	@Override
//	public String getErrorPath() {
//		// TODO Auto-generated method stub
//		return "/error";//	}
//
//	
//	@RequestMapping("/error")
//	public String handleError (HttpServletRequest http, Model model) {
//		Object status = http.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//		
//		if (status != null) {
//	        Integer statusCode = Integer.valueOf(status.toString());
//	     
//	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
//	        	model.addAttribute("code", "404" );
//	        	model.addAttribute("statue", "Page not found");
//	            return "error";
//	        }
////	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
////	            return "error";
////	        }
//	      
//	    }
//		return null;
//	}
//	
}
