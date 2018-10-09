package com.apap.tutorial5.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial5.model.CarModel;

/**
 * CarService
 * @author Zaki Raihan
 *
 */
public interface CarService {
	void addCar(CarModel car);
	
	Optional<CarModel> getCarDetailById(Long id);
	
	void updateCar(Long carId, CarModel newCar);

	void deleteCarById(Long carId);
}


