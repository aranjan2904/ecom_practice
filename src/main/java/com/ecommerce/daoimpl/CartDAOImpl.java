package com.ecommerce.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.dao.CartDAO;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.util.DBConnection;

public class CartDAOImpl implements CartDAO {

	@Override
	public boolean addToCart(Cart cart) {
		
		String query = "INSERT INTO cart(customer_id, product_id, quantity) VALUES(?,?,?)";
		
		try {
			
			Connection con = DBConnection.getConnection();
			
			PreparedStatement pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, cart.getCustomerId());
			pstmt.setInt(2, cart.getProductId());
			pstmt.setInt(3, cart.getQuantity());
			
			 int rows = pstmt.executeUpdate();
			 
			 if(rows > 0) {
				 return true;
			 }
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	
	@Override
	public List<CartItem> viewCart(int customerId) {

	    List<CartItem> cartItems = new ArrayList<>();

	    String query = "SELECT p.id, p.name, p.price, c.quantity "
	             + "FROM cart c "
	             + "INNER JOIN product p "
	             + "ON c.product_id = p.id "
	             + "WHERE c.customer_id = ?";

	    try {

	        Connection con = DBConnection.getConnection();

	        PreparedStatement pstmt = con.prepareStatement(query);

	        pstmt.setInt(1, customerId);

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {

	        	CartItem item = new CartItem();

	        	item.setProductId(rs.getInt("id"));      // NEW
	        	item.setProductName(rs.getString("name"));
	        	item.setPrice(rs.getDouble("price"));
	        	item.setQuantity(rs.getInt("quantity"));

	            // Calculate subtotal
	            double subtotal = item.getPrice() * item.getQuantity();
	            item.setSubtotal(subtotal);

	            cartItems.add(item);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return cartItems;
	}
	

}
