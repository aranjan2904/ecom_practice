package com.ecommerce.dao;

import java.util.List;

import com.ecommerce.entity.Product;

public interface ProductDAO {
	
	void addProduct(Product product);
	
	List<Product> getAllProducts();
	
	Product getProductById(int id);
	
	boolean updateProduct(Product product);
	
	boolean deleteProduct(int id);
	
	List<Product> searchProductByName(String name);
}
