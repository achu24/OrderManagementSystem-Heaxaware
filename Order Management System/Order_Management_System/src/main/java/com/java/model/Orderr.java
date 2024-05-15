package com.java.model;

public class Orderr {
	
	int orderId;
    int userId;
    
    public Orderr(){
    	
    }
    
    public Orderr(int orderId,int userId){
    	this.orderId=orderId;
    	this.userId=userId;
    	
    }
    
    
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
    

}
