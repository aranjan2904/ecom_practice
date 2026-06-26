package com.ecommerce.entity;

import com.google.protobuf.Timestamp;

public class Order {
	private int id;
	private int customerId;
	private double totalAmount;
	private String status;
	private Timestamp orderDate;
	
	public Order() {
		super();
	}

	public Order(int id, int customerId, double totalAmount, String status, Timestamp orderDate) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.totalAmount = totalAmount;
		this.status = status;
		this.orderDate = orderDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerId=" + customerId + ", totalAmount=" + totalAmount + ", status=" + status
				+ ", orderDate=" + orderDate + "]";
	}
	
	

}
