package com.amdocs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amdocs.dao.AppointmentDao;
import com.amdocs.exceptions.AppointmentInvalidException;
import com.amdocs.exceptions.CustomerInvalidException;
import com.amdocs.model.Appointment;
import com.amdocs.util.DbUtility;


public class AppoinmentDaoImpl implements AppointmentDao {
	private final static String SELECT_ALL = "SELECT * FROM APPOINTMENT";
	private final static String INSERT = "INSERT INTO APPOINTMENT("
			+ "mechanic_name, mechanic_contact, task, cutomer_id, date) values(?,?,?,?,?)";
	/*private final static String INSERT = "INSERT INTO APPOINTMENT("
			+ "mechanic_name, mechanic_contact, task, customer_id, date, time) values(?,?,?,?,?,?)";*/
	private final static String SELECT_BY_ID = "SELECT * FROM APPOINTMENT WHERE id=?";
	private final static String UPDATE = "UPDATE APPOINTMENT SET mechanic_name = ?, mechanic_contact = ?, task = ?, cutomer_id = ?, date = ?, time = ? WHERE id = ?";
	private final static String SELECT_ALL_BY_CUSTOMER_ID = "SELECT * FROM APPOINTMENT WHERE id = ?";
	private final static String DELETE = "DELETE FROM APPOINTMENT WHERE id = ?";
	
	
	private Connection connection = DbUtility.getConnection();

	public AppoinmentDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean insert(Appointment appointment) throws SQLException {
		boolean result=false;
		PreparedStatement stmt = connection.prepareStatement(INSERT);
		stmt.setString(1,appointment.getMechanicName());
		stmt.setInt(2, appointment.getMechanicContact());
		stmt.setString(3,appointment.getTask() );
		stmt.setInt(4, appointment.getCustomerId());
		stmt.setDate(5, new java.sql.Date(appointment.getDate().getTime()));
		//stmt.setDate(6, new java.sql.Time(appointment.getTime().valueOf(appointment.getTime().toString( ));
		
		if(stmt.executeUpdate()>0) {
			result = true;
		}
		stmt.close();
		return result;
	}

	@Override
	public boolean update(Appointment appointment) throws SQLException, AppointmentInvalidException {
		PreparedStatement stmt = connection.prepareStatement(UPDATE);
		stmt.setString(1, appointment.getMechanicName());
		stmt.setInt(2, appointment.getMechanicContact());
		stmt.setString(3, appointment.getTask());
		stmt.setInt(4, appointment.getCustomerId());
		stmt.setDate(5, new java.sql.Date(appointment.getDate().getTime()));
		stmt.setInt(6, appointment.getId());
		
		int rowsAffected = stmt.executeUpdate();
		System.out.println("Rows Affected  :" + rowsAffected);
		
		if(rowsAffected >= 1) {
			Appointment updatedAppointment = findById(appointment.getId());
			System.out.println("Updated Mechanic Appointment is: "+ updatedAppointment.toString());
		}else {
			throw new SQLException();
		}
		return true;
	}

	@Override
	public boolean delete(int id) throws SQLException, AppointmentInvalidException {
		PreparedStatement stmt = connection.prepareStatement(DELETE);
		stmt.setInt(1, id);
		
		int rowsAffected = stmt.executeUpdate();
		System.out.println("Rows Affected: " + rowsAffected);
		
		if(rowsAffected >= 1) {
			
		}else {
			throw new AppointmentInvalidException("Appointment Not Found With Id: " + id);
		}
		return true;
	}

	//find by the appointment idS
	@Override
	public Appointment findById(int id) throws SQLException, AppointmentInvalidException {
		Appointment appointment = null;
		PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		System.out.println("Rs  : "  + rs);
		
		if(rs.next()) {
			appointment = new Appointment();
			appointment.setId(rs.getInt("id"));
			appointment.setMechanicName(rs.getString("mechanic_name"));
			appointment.setMechanicContact(rs.getInt("mechanic_contact"));
			appointment.setTask(rs.getString("task"));
			appointment.setCustomerId(rs.getInt("cutomer_id"));
			appointment.setDate(new java.sql.Date(rs.getDate("date").getTime()));
		}else {
			throw new AppointmentInvalidException("Mechanic Appointment Not Found With Id: " + id);
		}
		rs.close();
		stmt.close();
		return appointment;
	}

	@Override
	public List<Appointment> findAll() throws SQLException {
		List<Appointment> appointments = new ArrayList<>();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SELECT_ALL);
		while (rs.next()) {
			Appointment appointment = new Appointment();
			appointment.setId(rs.getInt("id"));
			appointment.setMechanicName(rs.getString("mechanic_name"));
			appointment.setMechanicContact(rs.getInt("mechanic_contact"));
			appointment.setTask(rs.getString("task"));
			appointment.setCustomerId(rs.getInt("cutomer_id"));
			appointment.setDate(rs.getDate("date"));
			
			appointments.add(appointment);
		}
		rs.close();
		stmt.close();
		return appointments;
	}

	@Override
	public List<Appointment> findAllByCustomerId(int customerId) throws SQLException, CustomerInvalidException {
		List<Appointment> appointments = new ArrayList<>();
		PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_BY_CUSTOMER_ID);
		stmt.setInt(1, customerId);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Appointment appointment = new Appointment();
			appointment.setId(rs.getInt("id"));
			appointment.setMechanicName(rs.getString("mechanic_name"));
			appointment.setMechanicContact(rs.getInt("mechanic_contact"));
			appointment.setTask(rs.getString("task"));
			appointment.setCustomerId(rs.getInt("cutomer_id"));
			appointment.setDate(rs.getDate("date"));
			
			appointments.add(appointment);
		}
		rs.close();
		stmt.close();
		return appointments;
	}

}
