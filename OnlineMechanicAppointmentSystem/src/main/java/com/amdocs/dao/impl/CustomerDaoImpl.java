package com.amdocs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amdocs.dao.CustomerDao;
import com.amdocs.exceptions.CustomerInvalidException;
import com.amdocs.model.Customer;
import com.amdocs.util.DbUtility;

public class CustomerDaoImpl implements CustomerDao {
	
	private final static String SELECT_ALL = "SELECT * FROM CUSTOMER";
	private final static String SELECT_BY_ID = "SELECT * FROM CUSTOMER WHERE id=?";
	private final static String INSERT = "INSERT INTO CUSTOMER(name,age,mobile_number,address) values(?,?,?,?)";
	private final static String UPDATE = "UPDATE CUSTOMER SET name = ?,age = ?, mobile_number = ?, address = ? WHERE id = ?";
	private final static String DELETE = "DELETE FROM CUSTOMER WHERE id = ?";
	
	private Connection connection = DbUtility.getConnection();

	public CustomerDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean insert(Customer customer) throws SQLException {
		boolean result=false;
		PreparedStatement stmt = connection.prepareStatement(INSERT);
		stmt.setString(1,customer.getCname() );
		stmt.setLong(2,customer.getCage() );
		stmt.setInt(3, customer.getCphoneNumber());
		stmt.setString(4,customer.getCaddress() );
		if(stmt.executeUpdate()>0) {
			result = true;
		}
		stmt.close();
		return result;
	}

	@Override
	public boolean update(Customer customer) throws SQLException, CustomerInvalidException {
		PreparedStatement stmt = connection.prepareStatement(UPDATE);
		stmt.setString(1,customer.getCname() );
		stmt.setLong(2,customer.getCage() );
		stmt.setInt(3, customer.getCphoneNumber());
		stmt.setString(4,customer.getCaddress() );
		stmt.setInt(5, customer.getCid());
		
		int rowsAffected = stmt.executeUpdate();
		System.out.println("Rows Affected  :" + rowsAffected);
		
		if(rowsAffected >= 1) {
			Customer updatedCustomer = findById(customer.getCid());
			System.out.println("Updated Customer is: "+ updatedCustomer.toString());
		}else {
			throw new SQLException();
		}
		return true;
	}

	@Override
	public boolean delete(int customerId) throws SQLException, CustomerInvalidException {
		PreparedStatement stmt = connection.prepareStatement(DELETE);
		stmt.setInt(1, customerId);
		
		int rowsAffected = stmt.executeUpdate();
		System.out.println("Rows Affected: " + rowsAffected);
		
		if(rowsAffected >= 1) {
			
		}else {
			throw new CustomerInvalidException("Customer Not Found With Id: " + customerId);
		}
		return true;
	}

	@Override
	public Customer findById(int customerId) throws SQLException, CustomerInvalidException {
		Customer customer = null;
		PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID);
		stmt.setInt(1, customerId);
		ResultSet rs = stmt.executeQuery();
		System.out.println("Rs  : "  + rs);
		if(rs.next()) {
			customer = new Customer();
			customer.setCid(rs.getInt("id"));
			customer.setCname(rs.getString("name"));
			customer.setCphoneNumber(rs.getInt("mobile_number"));
			customer.setCaddress(rs.getString("address"));			
		}else {
			throw new CustomerInvalidException("Customer Not Found With Id: " + customerId);
		}
		rs.close();
		stmt.close();
		return customer;
	}

	@Override
	public List<Customer> findAll() throws SQLException {
		List<Customer> customers = new ArrayList<>();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SELECT_ALL);
		while (rs.next()) {
			Customer customer = new Customer();
			customer.setCid(rs.getInt("id"));
			customer.setCname(rs.getString("name"));
			customer.setCphoneNumber(rs.getInt("mobile_number"));
			customer.setCaddress(rs.getString("address"));
			// Adding to the list
			customers.add(customer);
		}
		rs.close();
		stmt.close();
		return customers;
	}	

}
