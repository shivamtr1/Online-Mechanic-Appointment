package com.amdocs.dao;

import java.sql.SQLException;
import java.util.List;

import com.amdocs.model.Customer;
import com.amdocs.exceptions.CustomerInvalidException;

public interface CustomerDao {
boolean insert(Customer customer) throws SQLException;
	
	boolean update(Customer customer) throws SQLException, CustomerInvalidException;
	
	boolean delete(int customerId) throws SQLException, CustomerInvalidException;
	
	Customer findById(int customerId) throws SQLException, CustomerInvalidException;
	
	List<Customer> findAll() throws SQLException;


}
