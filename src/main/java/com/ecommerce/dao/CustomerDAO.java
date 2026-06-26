package com.ecommerce.dao;

import com.ecommerce.entity.Customer;

public interface CustomerDAO {
	
	boolean emailExists(String email);
	
	boolean registerCustomer(Customer customer);
	
	Customer login(String email, String password);
	
}
