package com.ecommerce.entity;

public class CartItem {
	private String productName;
	private double price;
	private int quantity;
	
	private double subtotal;
	
	private int productId;

	


	public CartItem(String productName, double price, int quantity, double subtotal, int productId) {
		super();
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.subtotal = subtotal;
		this.productId = productId;
	}


	public int getProductId() {
		return productId;
	}


	public void setProductId(int productId) {
		this.productId = productId;
	}


	public CartItem() {
		super();
	}



	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public double getSubtotal() {
		return subtotal;
	}


	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}


	@Override
	public String toString() {
		return "CartItem [productName=" + productName + ", price=" + price + ", quantity=" + quantity + ", subtotal="
				+ subtotal + ", productId=" + productId + "]";
	}

	
}
