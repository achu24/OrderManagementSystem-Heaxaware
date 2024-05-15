package com.java.util;

import java.sql.*;

public class DBUtil {
	
	private static Connection conn = null;
	private  DBUtil() {
     try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
 
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Order_Management_System", "root", "root");
 
            System.out.println("Connected to the database");
           
        }
        catch(Exception e)
        {
        	
        	System.out.println(e);
        }
 
	}
 
	public static Connection getConnect()
	 {
		DBUtil dbu= new DBUtil();
		 return conn;
		
	 }
	
}

