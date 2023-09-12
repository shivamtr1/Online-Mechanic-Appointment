package com.amdocs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtility {

	public DbUtility() {
		// TODO Auto-generated constructor stub
	}
	
	private final static String URL = "jdbc:mysql://localhost:3306/amdocs_db";
	private final static String USERNAME = "root";
	private final static String PASSWORD ="mysql";
	
	private static Connection connection = null;
	
	public static Connection getConnection() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Connection to database successful");
		} catch (SQLException e) {
			System.err.println("Connection was not established...");
			System.err.print(e);
		}
		return connection;
	}


}
