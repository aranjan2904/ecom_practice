package com.ecommerce.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.entity.Product;
import com.ecommerce.util.DBConnection;

public class ProductDAOImpl implements ProductDAO {

	@Override
	public void addProduct(Product product) {

		String query = "INSERT INTO product(name,price,quantity) VALUES (?,?,?)";

		try {

			Connection con = DBConnection.getConnection();

			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setString(1, product.getName());
			pstmt.setDouble(2, product.getPrice());
			pstmt.setInt(3, product.getQuantity());

			int rows = pstmt.executeUpdate();

			if (rows > 0) {
				System.out.println("Product added successfully!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Product> getAllProducts() {

		List<Product> products = new ArrayList<Product>();

		String query = "SELECT * FROM product";

		try {
			Connection con = DBConnection.getConnection();

			PreparedStatement pstmt = con.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				Product product = new Product();

				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getDouble("price"));
				product.setQuantity(rs.getInt("quantity"));

				products.add(product);

			}

		} catch (Exception e) {

		}
		return products;
	}

	@Override
	public Product getProductById(int id) {

		String query = "SELECT * FROM product WHERE id = ?";

		try {

			Connection con = DBConnection.getConnection();

			PreparedStatement pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Product product = new Product();

				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getDouble("price"));
				product.setQuantity(rs.getInt("quantity"));

				return product;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public boolean updateProduct(Product product) {
		
		String query = "UPDATE product SET name=?, price=?, quantity=? WHERE id=?";
		
		
		
		
		try {
			
			Connection con = DBConnection.getConnection();
			
			PreparedStatement pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, product.getName());
			pstmt.setDouble(2, product.getPrice());
			pstmt.setInt(3, product.getQuantity());
			pstmt.setInt(4, product.getId());
			
			int rows = pstmt.executeUpdate();
			
			if(rows > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	
	@Override
	public boolean deleteProduct(int id) {
		
		String query = "DELETE FROM product WHERE id = ?";
		
		
		
		try {
			
			Connection con = DBConnection.getConnection();
			
			PreparedStatement pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, id);
			
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
	public List<Product> searchProductByName(String name) {
		
		String query = "SELECT * FROM product WHERE name LIKE ?";
		
		List<Product> products = new ArrayList<>();
		
		try {
			Connection con = DBConnection.getConnection();
			
			PreparedStatement pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, "%" +name+ "%");
			
			 ResultSet rs = pstmt.executeQuery();
			 
			 while(rs.next()) {
				 Product product = new Product();
				 
				 product.setId(rs.getInt("id"));
				 product.setName(rs.getString("name"));
				 product.setPrice(rs.getDouble("price"));
				 product.setQuantity(rs.getInt("quantity"));
				 
				 products.add(product);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return products;
	}

}
