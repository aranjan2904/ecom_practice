package com.ecommerce.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Statement;

import com.ecommerce.dao.CartDAO;
import com.ecommerce.dao.OrderDAO;
import com.ecommerce.entity.CartItem;
import com.ecommerce.util.DBConnection;

public class OrderDAOImpl implements OrderDAO {

	@Override
	public boolean placeOrder(int customerId) {

	    Connection con = null;

	    try {

	        // Step 1
	        con = DBConnection.getConnection();
	        con.setAutoCommit(false);

	        // Step 2
	        CartDAO cartDAO = new CartDAOImpl();
	        List<CartItem> cartItems = cartDAO.viewCart(customerId);

	        if (cartItems.isEmpty()) {
	            return false;
	        }

	        // Step 3
	        double totalAmount = 0;

	        for (CartItem item : cartItems) {
	            totalAmount += item.getSubtotal();
	        }

	        // Step 4
	        String query = "INSERT INTO orders(customer_id,total_amount,status) VALUES(?,?,?)";

	        PreparedStatement pstmt =
	                con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

	        pstmt.setInt(1, customerId);
	        pstmt.setDouble(2, totalAmount);
	        pstmt.setString(3, "PLACED");

	        int rows = pstmt.executeUpdate();

	        if (rows == 0) {
	            return false;
	        }

	        ResultSet rs = pstmt.getGeneratedKeys();

	        int orderId = 0;

	        if (rs.next()) {
	            orderId = rs.getInt(1);
	        }

	        // ================= WRITE THE NEW CODE HERE =================

	        String itemQuery =
	                "INSERT INTO order_item(order_id, product_id, quantity, price) VALUES(?,?,?,?)";

	        PreparedStatement itemPstmt = con.prepareStatement(itemQuery);

	        for (CartItem item : cartItems) {

	            itemPstmt.setInt(1, orderId);
	            itemPstmt.setInt(2, item.getProductId());
	            itemPstmt.setInt(3, item.getQuantity());
	            itemPstmt.setDouble(4, item.getPrice());

	            itemPstmt.executeUpdate();
	        }

	        // Next we'll delete the cart and commit
	        String deleteQuery = "DELETE FROM cart WHERE customer_id = ?";

	        PreparedStatement deletePstmt = con.prepareStatement(deleteQuery);

	        deletePstmt.setInt(1, customerId);

	        deletePstmt.executeUpdate();
	        
	        System.out.println("order placed successfully");
	        con.commit();
	        
	        return true;

	    } catch (SQLException e) {

	        try {

	            if (con != null) {
	                con.rollback();
	            }

	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }

	        e.printStackTrace();
	    }
	    finally {

	        try {

	            if (con != null) {

	                con.setAutoCommit(true);

	            }

	        } catch (SQLException e) {

	            e.printStackTrace();

	        }

	    }

	    return false;
	}

}
