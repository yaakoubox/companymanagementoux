package com.hedjerciyakoub.companymanagementwebapp.services;

import java.util.List;



public interface MainServices {
	
	public void save(Object object);
	
	public void deleteById(int id);
	
	public List<Object> findAll();
	
	public Object findById(int id);
	
	public List<Object> searchBy(String theName);
}
