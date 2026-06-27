package com.ecommerce.ui;

import java.util.List;
import java.util.Scanner;

import com.ecommerce.dao.CartDAO;
import com.ecommerce.dao.CustomerDAO;
import com.ecommerce.dao.OrderDAO;
import com.ecommerce.dao.ProductDAO;
import com.ecommerce.daoimpl.CartDAOImpl;
import com.ecommerce.daoimpl.CustomerDAOImpl;
import com.ecommerce.daoimpl.OrderDAOImpl;
import com.ecommerce.daoimpl.ProductDAOImpl;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Customer;
import com.ecommerce.entity.Product;

public class Menu {

	private ProductDAO productDAO = new ProductDAOImpl();
	private CustomerDAO customerDAO = new CustomerDAOImpl();
	private CartDAO cartDAO = new CartDAOImpl();
	private OrderDAO orderDAO = new OrderDAOImpl();

	private Customer loggedInCustomer = null;

	private Scanner sc = new Scanner(System.in);

	public void start() {

		while (true) {

			if (loggedInCustomer == null) {
				showGuestMenu();
			} else {
				showCustomerMenu();
			}

		}

	}

	// ================= HELPER FOR READING INPUT =================

	private String readLine(String prompt) {
		System.out.print(prompt);
		// System.out.flush(); // ensures the prompt appears immediately
		return sc.nextLine();
	}

	private int readInt(String prompt) {
		while (true) {
			try {
				String line = readLine(prompt);
				return Integer.parseInt(line.trim());
			} catch (NumberFormatException e) {
				System.out.println("Invalid number. Please enter a valid integer.");
			}
		}
	}

	// ================= GUEST MENU =================

	private void showGuestMenu() {

		System.out.println("\n====================================");
		System.out.println("       E-COMMERCE SYSTEM");
		System.out.println("====================================");

		System.out.println("1. View Products");
		System.out.println("2. Search Products");
		System.out.println("3. Register");
		System.out.println("4. Login");
		System.out.println("5. Exit");

		int choice = readInt("\nEnter your choice : ");

		switch (choice) {

		case 1:
			viewProducts();
			break;

		case 2:
			searchProducts();
			break;

		case 3:
			registerCustomer();
			break;

		case 4:
			loginCustomer();
			break;

		case 5:
			System.out.println("\nThank you for using E-Commerce System.");
			System.exit(0);

		default:
			System.out.println("\nInvalid Choice!");

		}

	}

	// ================= CUSTOMER MENU =================

	private void showCustomerMenu() {

		System.out.println("\n====================================");
		System.out.println("Welcome " + loggedInCustomer.getName());
		System.out.println("====================================");

		System.out.println("1. View Products");
		System.out.println("2. Search Products");
		System.out.println("3. Add To Cart");
		System.out.println("4. View Cart");
		System.out.println("5. Place Order");
		System.out.println("6. Order History");
		System.out.println("7. Logout");
		System.out.println("8. Exit");

		int choice = readInt("\nEnter your choice : ");

		switch (choice) {

		case 1:
			viewProducts();
			break;

		case 2:
			searchProducts();
			break;

		case 3:
			addToCart();
			break;

		case 4:
			viewCart();
			break;

		case 5:
			placeOrder();
			break;

		case 6:
			System.out.println("\nOrder History - Coming Soon...");
			break;

		case 7:
			loggedInCustomer = null;
			System.out.println("\nLogged out Successfully.");
			break;

		case 8:
			System.out.println("\nThank you for using E-Commerce System.");
			System.exit(0);

		default:
			System.out.println("\nInvalid Choice!");

		}

	}

	// ================= VIEW PRODUCTS =================

	private void viewProducts() {

		List<Product> products = productDAO.getAllProducts();

		if (products.isEmpty()) {

			System.out.println("\nNo Products Available.");

		} else {

			System.out.println("\n============= PRODUCT LIST =============");

			for (Product product : products) {
				System.out.println(product);
			}

			System.out.println("========================================");

		}

	}

	// ================= SEARCH PRODUCT =================

	private void searchProducts() {

		System.out.println("\n============ SEARCH PRODUCT=============\n");

		String name = readLine("Enter product Name: ");

		List<Product> products = productDAO.searchProductByName(name);

		if (products.isEmpty()) {
			System.out.println("\nNo Product Found!");
		} else {
			System.out.println("\n=========== SEARCH RESULT ==========\n");

			for (Product product : products) {
				System.out.println(product);
			}
		}

	}

	// ================= REGISTER =================

	private void registerCustomer() {

		System.out.println("\n========== CUSTOMER REGISTRATION ==========\n");

		String name = readLine("Enter Name     : ");
		String email = readLine("Enter Email    : ");
		String password = readLine("Enter Password : ");
		String mobile = readLine("Enter Mobile   : ");
		String address = readLine("Enter Address  : ");

		Customer customer = new Customer();

		customer.setName(name);
		customer.setEmail(email);
		customer.setPassword(password);
		customer.setMobile(mobile);
		customer.setAddress(address);

		boolean registered = customerDAO.registerCustomer(customer);

		if (registered) {

			System.out.println("\nRegistration Successful!");

		} else {

			System.out.println("\nEmail already exists!");

		}

	}

	// ================= LOGIN =================

	private void loginCustomer() {

		System.out.println("\n=============== LOGIN ===============\n");

		String email = readLine("Enter Email    : ");
		String password = readLine("Enter Password : ");

		Customer customer = customerDAO.login(email, password);

		if (customer != null) {

			loggedInCustomer = customer;

			System.out.println("\nWelcome " + customer.getName() + " 😊");
			System.out.println("Logged In Customer ID : " + loggedInCustomer.getId());

		} else {

			System.out.println("\nInvalid Email or Password.");
			
		}
		
	}

//===================== ADD TO CART ===============================

	private void addToCart() {
		System.out.println("\n============== ADD TO CART ===========\n");
		
		int productId = readInt("Enter Product ID: ");
		
		int quantity = readInt("Enter Quantity : ");
		
		Product product = productDAO.getProductById(productId);

		if (product == null) {
		    System.out.println("\nInvalid Product ID.");
		    return;
		}
		
		Cart cart = new Cart();
		
		cart.setCustomerId(loggedInCustomer.getId());
		cart.setProductId(productId);
		cart.setQuantity(quantity);
		
		boolean added = cartDAO.addToCart(cart);
		
		if(added) {
			System.out.println("\nProduct Added To Cart Successfully.");
		}else {
			System.out.println("\nUnable To Add Product.");
		}
		
		
	}
	
	
	//=========================== VIEW CART ==============================
	
	private void viewCart() {

	    System.out.println("\n========== MY CART ==========\n");

	    List<CartItem> cartItems = cartDAO.viewCart(loggedInCustomer.getId());

	    if (cartItems.isEmpty()) {
	        System.out.println("Your cart is empty.");
	        return;
	    }

	    double grandTotal = 0;

	    for (CartItem item : cartItems) {

	        System.out.println("Product  : " + item.getProductName());
	        System.out.println("Price    : ₹" + item.getPrice());
	        System.out.println("Quantity : " + item.getQuantity());
	        System.out.println("Subtotal : ₹" + item.getSubtotal());

	        System.out.println("---------------------------------------");

	        grandTotal += item.getSubtotal();
	    }

	    System.out.println("Grand Total : ₹" + grandTotal);
	}
	
	
	//==================== PLACE ORDER ====================
	private void placeOrder() {

	    System.out.println("\n========== PLACE ORDER ==========\n");

	    boolean placed = orderDAO.placeOrder(loggedInCustomer.getId());

	    if (placed) {
	        System.out.println("🎉 Order Placed Successfully!");
	    } else {
	        System.out.println("❌ Unable to Place Order.");
	    }
	}
	

}