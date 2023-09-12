package com.amdocs.model;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
	private int id;
	private String mechanicName;
	private int mechanicContact;
	private String task;
	private int customerId;
	private Date date;
	private Time time;
	
	
	public Appointment(int id, String mechanicName, int mechanicContact, String task, int customerId, Date date,
			Time time) {
		super();
		this.id = id;
		this.mechanicName = mechanicName;
		this.mechanicContact = mechanicContact;
		this.task = task;
		this.customerId = customerId;
		this.date = date;
		this.time = time;
	}


	public Appointment(String mechanicName, int mechanicContact, String task, int customerId, Date date, Time time) {
		super();
		this.mechanicName = mechanicName;
		this.mechanicContact = mechanicContact;
		this.task = task;
		this.customerId = customerId;
		this.date = date;
		this.time = time;
	}


	public Appointment() {
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMechanicName() {
		return mechanicName;
	}


	public void setMechanicName(String mechanicName) {
		this.mechanicName = mechanicName;
	}


	public int getMechanicContact() {
		return mechanicContact;
	}


	public void setMechanicContact(int mechanicContact) {
		this.mechanicContact = mechanicContact;
	}


	public String getTask() {
		return task;
	}


	public void setTask(String task) {
		this.task = task;
	}


	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Time getTime() {
		return time;
	}


	public void setTime(Time time) {
		this.time = time;
	}


	@Override
	public String toString() {
		return "Appointment [id=" + id + ", mechanicName=" + mechanicName + ", mechanicContact=" + mechanicContact
				+ ", task=" + task + ", customerId=" + customerId + ", date=" + date + ", time=" + time + "]";
	}
	
	
	

}
