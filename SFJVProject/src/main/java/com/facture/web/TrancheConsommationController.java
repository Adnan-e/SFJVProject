package com.facture.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.facture.bean.TrancheConsommation;
import com.facture.service.FactureService;
import com.facture.service.TrancheConsommationService;

@Controller
public class TrancheConsommationController {

	@Autowired
	FactureService factureService;
	@Autowired
	TrancheConsommationService trancheConsommationService;
	
	
	@RequestMapping("/fconsommation")
	public String findConsommationPerFcature(@RequestParam(name="fSelected" , defaultValue= "") String fSelected,
			Model model,@RequestParam(name="lastPageNp" , defaultValue= "0")int lastPageNp,
			@RequestParam(name="pageNp" , defaultValue= "0") int pageNp,
			@RequestParam(name="lastPage" , defaultValue= "0")int lastPage,
			@RequestParam(name="page" , defaultValue= "0") int page) {

		if (fSelected != null && !fSelected.isEmpty()) {
//		 trancheConsommations = new ArrayList<>();
		 System.out.println(fSelected +" your facture id 142");
		 model.addAttribute("fSelected", fSelected);
//		if(!fSelected.equals(null) && !fSelected.isEmpty()) {
		 List<TrancheConsommation>  trancheConsommations = trancheConsommationService.findFactureTransaction(fSelected);
//		}
		 System.out.println(trancheConsommations +" your facture cons 142");
		model.addAttribute("trancheConsommations", trancheConsommations);
		 TrancheConsommation factureName = trancheConsommations.get(0);
		 model.addAttribute("factureName", factureName);
		
		model.addAttribute("lastPageNp", lastPageNp);
		model.addAttribute("lastPage", lastPage);
   	    
		return "forward:/home";
		}
		return "redirect:/home";
	}
	
	
}
