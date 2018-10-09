package com.apap.tutorial5.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.service.CarService;
import com.apap.tutorial5.service.DealerService;

/**
 * CarController
 * @author Zaki Raihan
 *
 */
@Controller
public class CarController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping(value = "/car/add/{dealerId}")
	private String add(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		model.addAttribute("headerTitle", "Add Car");
		DealerModel dealer = new DealerModel();
		dealer.setListCar(new ArrayList<CarModel>());
		dealer.getListCar().add(new CarModel());
		model.addAttribute("dealer", dealer);
		model.addAttribute("dealerId", dealerId);
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.POST, params={"addRow"})
	private String addRow(@PathVariable(value = "dealerId") Long dealerId,@ModelAttribute DealerModel dealer, Model model) {
		model.addAttribute("headerTitle", "Add Car");
		CarModel car = new CarModel();
		dealer.getListCar().add(car);
		model.addAttribute("dealer", dealer);
		model.addAttribute("dealerId", dealerId);
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.POST, params={"removeRow"})
	private String removeRow(@PathVariable(value = "dealerId") Long dealerId,@ModelAttribute DealerModel dealer, Model model, HttpServletRequest req) {
		Integer rowId =  Integer.valueOf(req.getParameter("removeRow")); 
		dealer.getListCar().remove(rowId.intValue());
		model.addAttribute("dealerId", dealerId);
		model.addAttribute("dealer", dealer);
		return "addCar";
	}
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.POST, params= {"save"})
	private String addCarSubmit(@PathVariable(value = "dealerId") Long dealerId, @ModelAttribute DealerModel dealer, ModelMap model) {
		model.addAttribute("headerTitle", "Add Car");
		DealerModel oldDealer = dealerService.getDealerDetailById(dealerId).get();
		model.addAttribute("dealerId", dealerId);
		for (CarModel car : dealer.getListCar()) {
			car.setDealer(oldDealer);
			carService.addCar(car);
		}
		model.clear();
		return "add";
	}
	
	@RequestMapping(value = "/car/update{carId}", method = RequestMethod.GET)
	private String update(@PathVariable(value = "carId") Long carId, Model model) {
		model.addAttribute("headerTitle", "Update Car");
		CarModel oldCar = carService.getCarDetailById(carId).get();
		model.addAttribute("oldCar", oldCar);
		model.addAttribute("newCar", new CarModel());
		return "updateCar";
	}
	
	@RequestMapping(value = "/car/update{carId}", method = RequestMethod.POST)
	private String updateDealerSubmit(@ModelAttribute CarModel newCar, @PathVariable(value = "carId") Long carId, Model model) {
		model.addAttribute("headerTitle", "Update Car");
		carService.updateCar(carId, newCar);
		return "update";
	}
	
	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
	private String updateDealerSubmit(@ModelAttribute DealerModel dealer, Model model) {
		model.addAttribute("headerTitle", "Delete Car");
		for (CarModel car : dealer.getListCar()) {
			carService.deleteCarById(car.getId());
		}
		return "delete";
	}
}
