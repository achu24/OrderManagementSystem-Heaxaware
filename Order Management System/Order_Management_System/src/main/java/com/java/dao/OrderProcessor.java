package com.java.dao;

import java.sql.*;
import java.util.*;
import com.java.exception.OrderNotFoundException;
import com.java.exception.UserNotFoundException;
import com.java.model.*;
import com.java.util.*;
public class OrderProcessor implements IOrderManagementRepository {
	
	private Connection conn;
	PreparedStatement stmt;
	
	public OrderProcessor() {
	conn=DBUtil.getConnect();
	}

	public void createOrder(User user, List<Product> products) {
		try {
           
            if (!isUserExists(user.getUserId())) {
                createUser(user);
            }

            int orderId = createOrderInDatabase(user.getUserId());

            addOrderItems(orderId, products);
        } 
		catch (SQLException e) {
	        System.out.println(e);
        }
    }
	private boolean isUserExists(int userId) throws SQLException{
        stmt = conn.prepareStatement("select * from User where userId = ?");
        stmt.setInt(1, userId);
        ResultSet rs=stmt.executeQuery();
        return rs.next(); 
    }
    public int createOrderInDatabase(int userId) throws SQLException{
            stmt = conn.prepareStatement("insert into Orderr(userId) valus(?)");
            stmt.setInt(1, userId);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } 
            else {
                throw new SQLException("Failed to create order, no ID obtained.");
            }
        	
        }
    public void addOrderItems(int orderId, List<Product> products) throws SQLException {
    	OrderItem oi=new OrderItem();
        stmt = conn.prepareStatement("insert into OrderItem values (?, ?, ?)");
        for (Product product : products) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, product.getProductId());
            stmt.setInt(3, oi.getQuantity());
            stmt.executeUpdate();
        }
    }
    
	
    
    
    
	public void cancelOrder(int userId, int orderId) throws UserNotFoundException, OrderNotFoundException {
		  try {
		        if (!isUserExists(userId)) {
		            throw new UserNotFoundException("User not found with userId: " + userId);
		        }

		        if (!isOrderExists(orderId)) {
		            throw new OrderNotFoundException("Order not found with orderId: " + orderId);
		        }		        
		        deleteOrder(orderId);
		        System.out.println("Order canceled successfully!");
		    } 
		  catch (SQLException e) {
		        System.out.println(e);
		    }	
	}
	private boolean isOrderExists(int orderId) throws SQLException {
	    stmt = conn.prepareStatement("select * from Orderr where orderId = ?");
	    stmt.setInt(1, orderId);
	    ResultSet rs = stmt.executeQuery();
	    return rs.next();
	}
	private void deleteOrder(int orderId) throws SQLException {
	    
	    stmt = conn.prepareStatement("delete from OrderItem where orderId = ?");
	    stmt.setInt(1, orderId);
	    stmt.executeUpdate();

	    stmt = conn.prepareStatement("DELETE FROM Orderr WHERE orderId = ?");
	    stmt.setInt(1, orderId);
	    stmt.executeUpdate();
	}

	
	
	
	
	public void createProduct(User user, Product product) {
		try {
	        stmt = conn.prepareStatement("insert into Product values (?, ?, ?, ?, ?, ?)");
	        
	        stmt.setInt(1, product.getProductId());
	        stmt.setString(2, product.getProductName());
	        stmt.setString(3, product.getDescription());
	        stmt.setDouble(4, product.getPrice());
	        stmt.setInt(5, product.getQuantityInStock());
	        stmt.setString(6, product.getType());
	        stmt.executeUpdate();
	        System.out.println("Product created successfully!");
	    } 
		catch (SQLException e) {
	        System.out.println(e);
	    }
		
	}

	
	
	
	
	public void createUser(User user) {
		try {
		
		stmt=conn.prepareStatement("insert into User values(?,?,?,?)");
		stmt.setInt(1, user.getUserId());
		stmt.setString(2,user.getUsername());
		stmt.setString(3,user.getPassword());
		stmt.setString(4,user.getRole());
		stmt.executeUpdate();
		System.out.println("User added");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	
	public List<Product> getAllProducts() {
		List<Product> productList = new ArrayList<>();
	    try {
	        stmt = conn.prepareStatement("SELECT * FROM Product");
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Product product = new Product(
	                rs.getInt("productId"),
	                rs.getString("productName"),
	                rs.getString("description"),
	                rs.getDouble("price"),
	                rs.getInt("quantityInStock"),
	                rs.getString("type")
	            );
	            productList.add(product);
	        }
	    } 
	    catch (SQLException e) {
			System.out.println(e.getMessage());

	    }
	    return productList;	
	}
	
	
	
	
	
	public List<Product> getOrderByUser(User user) {
		
		List<Product> orderedProducts = new ArrayList<>();
	    try {
	        stmt = conn.prepareStatement("select * from OrderItem where orderId in (select orderId from Orderr where userId = ?)");
	        stmt.setInt(1, user.getUserId());
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            int productId = rs.getInt("productId");
	           
	            Product product = getProductById(productId);
	            if (product != null) {
	                orderedProducts.add(product);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return orderedProducts;
	}
	private Product getProductById(int productId) throws SQLException {
		stmt = conn.prepareStatement("SELECT * FROM Product WHERE productId = ?");
	    stmt.setInt(1, productId);
	    ResultSet rs = stmt.executeQuery();
	    Product product = null;
	    if (rs.next()) {
	        product = new Product(
	            rs.getInt("productId"),
	            rs.getString("productName"),
	            rs.getString("description"),
	            rs.getDouble("price"),
	            rs.getInt("quantityInStock"),
	            rs.getString("type")
	        );
	    }
	    return product;
		
	}

}
