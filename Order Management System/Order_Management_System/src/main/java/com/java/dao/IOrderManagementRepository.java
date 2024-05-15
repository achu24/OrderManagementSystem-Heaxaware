package com.java.dao;

import com.java.model.*;
import com.java.exception.*;
import java.util.*;

public interface IOrderManagementRepository {
	
	void createOrder(User user, List<Product> products);

    void cancelOrder(int userId, int orderId)throws UserNotFoundException, OrderNotFoundException;

    void createProduct(User user, Product product);

    void createUser(User user);
    
    List<Product> getAllProducts();

    List<Product> getOrderByUser(User user);

}
