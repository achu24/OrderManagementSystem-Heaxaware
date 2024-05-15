package com.java.service;

import java.util.*;

import com.java.dao.*;
import com.java.exception.*;
import com.java.model.*;

public class service {
	
	OrderProcessor op;
	Scanner sc;
	
	public service(){
		op=new OrderProcessor();
		sc= new Scanner(System.in);
	}
	
	public void createOrder(){
		User user = new User();
		System.out.println("Enter User Details:");
		
		System.out.print("User ID: ");
		user.setUserId(sc.nextInt());
		
	    System.out.print("User Name: ");
	    sc.nextLine();
	    user.setUsername(sc.nextLine());
	    
	    System.out.print("Password: ");
	    sc.nextLine();
	    user.setPassword(sc.nextLine());
	    
	    System.out.print("Role: ");
	    sc.nextLine();
	    user.setRole(sc.nextLine());
	    
	    System.out.println("Enter Products to Order :");
	    
	    List<Product> productstoOrder = new ArrayList<>();
	    
	    while(true) {
	    	Product product = new Product();
	    	 
	    	System.out.println("Enter Product Details");
	    	sc.nextLine();
	    	
	    	System.out.print("Product ID: ");
	    	product.setProductId(sc.nextInt());
	    	
	    	System.out.print("Description: ");
	    	sc.nextLine();
	    	product.setDescription(sc.nextLine());
	    	
	    	System.out.print("Price: ");
	    	product.setPrice(sc.nextDouble());
	    	
	    	System.out.print("Quantity in Stock: ");
	    	product.setQuantityInStock(sc.nextInt());
	    	
	    	System.out.print("Type (Electronics/Clothing): ");
	    	sc.nextLine();
	    	product.setType(sc.nextLine()); 
	    	
	    	System.out.print("Add another product? (yes/no): ");
	    	sc.nextLine();
	    	String response=sc.nextLine();
	    	if(response.equals("no")) {
	    		break;
	    	}
	    	
	    }
	    
	    op.createOrder(user, productstoOrder);
	}

	public void cancelOrder() {
		User user=new User();
		Orderr order=new Orderr();
		
		System.out.print("Enter User ID: ");
		int userId=sc.nextInt();
		
		System.out.print("Enter Order ID: ");
		int orderId=sc.nextInt();
		
		try {
            op.cancelOrder(userId, orderId);
        } 
		catch (UserNotFoundException | OrderNotFoundException e) {
            System.out.println(e);
        }
	}
	public void getAllProducts() {
		List<Product> products = op.getAllProducts();
		 for (Product product : products) {
	            System.out.println(product);
	        }
	}
	public void getOrderByUser() {
		User user=new User();
		op.getOrderByUser(user);
	}
}
