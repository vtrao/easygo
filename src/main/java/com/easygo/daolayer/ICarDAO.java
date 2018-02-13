package com.easygo.daolayer;

import java.util.List;

import com.easygo.model.Car;

public interface ICarDAO {
	public Car getCar(Long id) throws Exception;

	// branchid == -1 returns all cars, for branch id if homebranch==false
	// returns the ones in current branch
	public List<Car> getCars(Long branchid, boolean homebranch) throws Exception;

	public Car addCar(Car car) throws Exception;

	public int removeCar(Long id) throws Exception;

	public int updateCar(Car car) throws Exception;
	
	public List<String> getTypes();
}
