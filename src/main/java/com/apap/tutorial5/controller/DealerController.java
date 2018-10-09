package com.apap.tutorial5.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.service.CarService;
import com.apap.tutorial5.service.DealerService;

/**
 * DealerController
 * @author Zaki Raihan
 *
 */
@Controller
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("headerTitle", "Home");
		return "home";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("headerTitle", "Add Dealer");
		model.addAttribute("dealer", new DealerModel());
		return "addDealer";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer, Model model) {
		model.addAttribute("headerTitle", "Add Dealer");
		dealerService.addDealer(dealer);
		return "add";
	}
	
	@RequestMapping(value = "/dealer/view", method = RequestMethod.GET)
	private String viewDealer(@RequestParam(value = "dealerId", required = true) Long dealerId, Model model) {
		DealerModel dealer = null;
		List<CarModel> listCar = null;
		String title = "View Dealer: Tidak Ditemukan";
		if (dealerService.getDealerDetailById(dealerId).isPresent()) {
			dealer = dealerService.getDealerDetailById(dealerId).get();
			listCar = dealer.getListCar();
			Collections.sort(listCar);
			title = "View Dealer: " + Long.toString(dealer.getId());
		}
		model.addAttribute("dealer", dealer);
		model.addAttribute("headerTitle", title);
		return "view-dealer";
	}
	
	@RequestMapping(value = "/dealer/update{dealerId}", method = RequestMethod.GET)
	private String update(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		model.addAttribute("headerTitle", "Update Dealer");
		DealerModel oldDealer = dealerService.getDealerDetailById(dealerId).get();
		model.addAttribute("oldDealer", oldDealer);
		model.addAttribute("newDealer", new DealerModel());
		return "updateDealer";
	}
	
	@RequestMapping(value = "/dealer/update{dealerId}", method = RequestMethod.POST)
	private String updateDealerSubmit(@ModelAttribute DealerModel newDealer, @PathVariable(value = "dealerId") Long dealerId, Model model) {
		model.addAttribute("headerTitle", "Update Dealer");
		dealerService.updateDealer(dealerId, newDealer);
		return "update";
	}
	
	@RequestMapping(value = "/dealer/view-all", method = RequestMethod.GET)
	private String viewDealerAll(Model model) {
		List<DealerModel> listDealer = null;
		if (!dealerService.getAllDealer().isEmpty()) {
			listDealer = dealerService.getAllDealer();
		}
		model.addAttribute("listDealer", listDealer);
		model.addAttribute("headerTitle", "View All Dealer");
		return "viewAll-dealer";
	}
	
	@RequestMapping(value = "/dealer/delete", method = RequestMethod.GET)
	private String updateDealerSubmit(@RequestParam(value = "dealerId", required = true) Long dealerId, Model model) {
		model.addAttribute("headerTitle", "Delete Dealer");
		dealerService.deleteDealerById(dealerId);
		return "delete";
	}
	
	

}
