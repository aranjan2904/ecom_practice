package com.ecommerce.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ecommerce.dao.CustomerDAO;
import com.ecommerce.entity.Customer;
import com.ecommerce.util.DBConnection;

public class CustomerDAOImpl implements CustomerDAO {

	@Override
	public boolean emailExists(String email) {
		String query = "SELECT * FROM customer WHERE email = ?";

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setString(1, email);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean registerCustomer(Customer customer) {
		
		if(emailExists(customer.getEmail())) {
			return false;
		}
		
		String query = "INSERT INTO customer(name, email, password, mobile, address) VALUES(?,?,?,?,?)";
		
		try {
			Connection con = DBConnection.getConnection();
			
			PreparedStatement pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, customer.getName());
			pstmt.setString(2, customer.getEmail());
			pstmt.setString(3, customer.getPassword());
			pstmt.setString(4, customer.getMobile());
			pstmt.setString(5, customer.getAddress());
			
			 long rows = pstmt.executeLargeUpdate();
			 
			 if(rows > 0) {
				 return true;
			 }
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return false;
	}

	@Override
	public Customer login(String email, String password) {
		
		String query = "SELECT * FROM customer WHERE email =? AND password = ?";
		
		try {
			Connection con = DBConnection.getConnection();
			
			PreparedStatement pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Customer customer = new Customer();
				
				customer.setId(rs.getInt("id"));
				customer.setName(rs.getString("name"));
				customer.setEmail(rs.getString("email"));
				customer.setPassword(rs.getString("password"));
				customer.setMobile(rs.getString("mobile"));
				customer.setAddress(rs.getString("address"));
				return customer;
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
}
