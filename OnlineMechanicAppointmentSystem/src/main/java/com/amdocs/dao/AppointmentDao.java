package com.amdocs.dao;

import java.sql.SQLException;
import java.util.List;

import com.amdocs.exceptions.AppointmentInvalidException;
import com.amdocs.exceptions.CustomerInvalidException;
import com.amdocs.model.Appointment;

public interface AppointmentDao {

	boolean insert(Appointment appointment) throws SQLException;
	
	boolean update(Appointment appointment) throws SQLException, AppointmentInvalidException;
	
	boolean delete(int id) throws SQLException, AppointmentInvalidException;
	
	Appointment findById(int id) throws SQLException, AppointmentInvalidException;
	
	List<Appointment> findAll() throws SQLException;
	
	List<Appointment> findAllByCustomerId(int customerId) throws SQLException, CustomerInvalidException;

}
