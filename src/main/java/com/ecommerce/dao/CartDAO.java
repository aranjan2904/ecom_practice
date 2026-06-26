package com.ecommerce.dao;

import java.util.List;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;

public interface CartDAO {
	
	boolean addToCart(Cart cart);
	
	List<CartItem> viewCart(int customerId);

}
