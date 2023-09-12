package com.amdocs;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.amdocs.dao.CustomerDao;
import com.amdocs.dao.AppointmentDao;
import com.amdocs.dao.impl.CustomerDaoImpl;
import com.amdocs.entity.MechanicAppointment;
import com.amdocs.exceptions.AppointmentInvalidException;
import com.amdocs.exceptions.CustomerInvalidException;
import com.amdocs.exceptions.CustomerNotFoundException;
import com.amdocs.exceptions.MechanicAppointmentNotFoundException;
import com.amdocs.model.Appointment;
import com.amdocs.model.Customer;
import com.amdocs.dao.impl.AppoinmentDaoImpl;


/**
 * Hello world!
 *
 */
public class OnlineMechanicAppointmentSystem 
{
private static CustomerDao dao = new CustomerDaoImpl();
	
	private static AppointmentDao mDao = new AppoinmentDaoImpl();
	
	private static Scanner sc = new Scanner(System.in);
	
    public static void main( String[] args )
    {
    	System.out.println("ONLINE MECHANIC APPOINTMENT");

		while (true) {
			
			System.out.println("\n************Please enter Your choice************");
			System.out.println("1. Customer");
			System.out.println("2. Appointment");
			System.out.println("0. Stop");
			
			String str = sc.nextLine();
			if(str.trim().isEmpty()) continue;
			
			int ch = Integer.parseInt(str);
			
			
			switch (ch) {
			case 1:
				
				boolean customer_present = true;
				
				while(customer_present) {
					System.out.println("\n************Again enter Your choice************");
					System.out.println("1. Register Customer");
					System.out.println("2. Modify Customer Details");
					System.out.println("3. Delete Customer Record");
					System.out.println("4. View Single Record");
					System.out.println("5. View All Records");
					System.out.println("0. Exit");
					
					int ch2 = Integer.parseInt(sc.nextLine());
					
					switch (ch2) {
					
					case 1:
						addCustomer();
						break;
						
					case 2:
						updateCustomer();
						break;
						
					case 3: 
						deleteCustomer();
						break;
						
					case 4:
						findCustomerById();
						break;
						
					case 5:
						findAllCustomers();
						break;
						
					default:
						System.out.println("Exiting");
						customer_present = false;
						break;		
						
					}
				}
				
				break;
				
			case 2:
				
				boolean mechanic_present = true;
				
				while(mechanic_present) {
					
					System.out.println("\n************Again enter Your choice************");
					System.out.println("1. Book an appointment");
					System.out.println("2. Modify appointment details");
					System.out.println("3. Delete an appointment");
					System.out.println("4. View Single Record");
					System.out.println("5. View All Records");
					System.out.println("6. View Customer's Appointment History");
					System.out.println("0. Exit");
					
					
					int ch3 = Integer.parseInt(sc.nextLine());
					
					switch (ch3) {
					
					case 1:
						bookAppointment();
						break;
						
					case 2:
						modifyAppointment();
						break;
						
					case 3: 
						
						deleteAppointment();
						break;
						
					case 4:
						findAppointmentById();
						break;
						
					case 5:
						findAllAppointments();
						break;
						
					case 6:
						
						viewAppointmentHistory();
						break;
						
					default:

						System.out.println("Exiting");
						mechanic_present = false;
						break;		
						
					}
				}

				break;
			
			default:
				System.out.println("PROGRAM TERMINATED");
				System.exit(0);
			}

		}
    }
    
    private static void deleteAppointment() {
		System.out.println("\nEnter Appointment Id");
		int id = sc.nextInt();
		sc.nextLine();
		
		try {
			mDao.delete(id);
			System.out.println("Appointment record is successfully deleted!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AppointmentInvalidException e) {
			e.printStackTrace();
		}
		
	}
    
	private static void viewAppointmentHistory() {
			
			System.out.println("Please enter customer ID");
			int custID = sc.nextInt();
			sc.nextLine();
			
			try {
				List<Appointment> appointments = mDao.findAllByCustomerId(custID);
				
				if(appointments.size()==0) {
					System.out.println("No Record Found");
				}else {
					System.out.println("Complete appointment history for given customer is as follows:");
					for(Appointment a : appointments) {
						System.out.println(a.toString());
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (CustomerInvalidException e1) {
				e1.printStackTrace();
			}
		}
	
	private static void findAllAppointments() {
		try {
			List<Appointment> appointments = mDao.findAll();
			
			if(appointments.size()==0) {
				System.out.println("No Record Found");
			}else {
				System.out.println("Complete appointment record is as follows:");
				for(Appointment a : appointments) {
					System.out.println(a.toString());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void findAppointmentById() {
			
		System.out.println("\nEnter Appointment Id");
		int id = sc.nextInt();
		sc.nextLine();
		
		try {
			Appointment appointment = mDao.findById(id);
			System.out.println("Requested appointment details are:");
			System.out.println(appointment.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AppointmentInvalidException e) {
			e.printStackTrace();
		}
	}
	
private static void modifyAppointment() {
		
		System.out.println("\nEnter Appointment ID");
		int id = sc.nextInt();
		sc.nextLine();
		
		try {
			Appointment appointment = mDao.findById(id);
			
			System.out.println("\nEnter New Mechanic Name:");
			appointment.setMechanicName(sc.nextLine());
			
			System.out.println("\nEnter New Mobile Number:");
			appointment.setMechanicContact(sc.nextInt());
			sc.nextLine();
			
			System.out.println("\nEnter New Specialization:");
			appointment.setTask(sc.nextLine());
			
			System.out.println("\nEnter New Customer Id");
			int custId = sc.nextInt();
			appointment.setCustomerId(custId);
			sc.nextLine();
			
			try {
				dao.findById(custId);
			} catch (SQLException e2) {
				e2.printStackTrace();
			} catch (CustomerInvalidException e2) {
				System.out.println("\nCannot Modify Appointment. Reason: Customer Not Found");
				e2.printStackTrace();
				return;
			}
			
			
			System.out.println("\nEnter Date (YYYY/MM/DD)");
			String dateStr = sc.nextLine();  
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date date;
			try {
				date = sdf.parse(dateStr);
			} catch (ParseException e1) {
				e1.printStackTrace();
				return;
			} catch (InputMismatchException e) {
				e.printStackTrace();
				return;
			}
			
			appointment.setDate(new java.sql.Date(date.getTime()));
			
			mDao.update(appointment);
			System.out.println("Appointment details are modified successfully!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AppointmentInvalidException e) {
			System.out.println("Mechanic Appointment Not Found with given ID");
			e.printStackTrace();
		}
		
	}

	private static void bookAppointment() {
		
		System.out.println("\nEnter Mechanic Name:");
		String mechanicName = sc.nextLine();
		
		System.out.println("\nEnter Mobile Number:");
		int mechanicContact = sc.nextInt();
		sc.nextLine();
		
		System.out.println("\nEnter task:");
		String task = sc.nextLine();
		
		System.out.println("\nEnter Customer Id");
		int custId = sc.nextInt();
		sc.nextLine();
		
		try {
			dao.findById(custId);
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (CustomerInvalidException e2) {
			System.out.println("\nCannot Make Appointment. Reason: Customer Not Found");
			e2.printStackTrace();
			return;
		}
		
		
		System.out.println("\nEnter Date (YYYY/MM/DD)");
		String dateStr = sc.nextLine();  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e1) {
			e1.printStackTrace();
			return;
		}
		
		Appointment appointment = new Appointment(mechanicName, mechanicContact, task, custId, new java.sql.Date(date.getTime()), new Time(00,00,00));
		
		try {
			if(mDao.insert(appointment)) {
				System.out.println("Your appointment with the mechanic is successfully booked!!");
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		
	}
	
	private static void findAllCustomers() {
		try {
			List<Customer> customers = dao.findAll();
			
			if(customers.size()==0) {
				System.out.println("No Record Found");
			}else {
				System.out.println("Complete customer record is as follows:");
				for( Customer c : customers) {
					System.out.println(c.toString());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void deleteCustomer() {
		
		System.out.println("\nEnter Customer Id");
		int id = sc.nextInt();
		sc.nextLine();
		
		try {
			dao.delete(id);
			System.out.println("Customer record is successfully deleted!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CustomerInvalidException e) {
			e.printStackTrace();
		}
	
	}
	
	private static void findCustomerById() {
		
		System.out.println("\nEnter Customer Id");
		int id = sc.nextInt();
		sc.nextLine();
		
		try {
			Customer customer = dao.findById(id);
			System.out.println("Requested customer details are:");
			System.out.println(customer.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CustomerInvalidException e) {
			e.printStackTrace();
		}
			
	}
	
	private static void updateCustomer() {
		
		System.out.println("\nEnter Customer ID");
		int id = sc.nextInt();
		sc.nextLine();
		
		try {
			Customer customer = dao.findById(id);
			System.out.println("\nEnter new name:");
			customer.setCname(sc.nextLine());
			System.out.println("\nEnter new mobile number:");
			customer.setCphoneNumber(sc.nextInt());
			sc.nextLine();
			System.out.println("\nEnter new address:");
			customer.setCaddress(sc.nextLine());
			
			dao.update(customer);
			System.out.println("Customer details are modified successfully!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CustomerInvalidException e) {
			System.out.println("Customer Not Found with given ID");
			e.printStackTrace();
		}
		
	}
	
	private static void addCustomer() {

		System.out.println("\nEnter Name:");
		String cname = sc.nextLine();
		
		System.out.println("\nEnter Age:");
		int cage = sc.nextInt();
		
		
		System.out.println("\nEnter Mobile Number:");
		int cphoneNumber = sc.nextInt();
		sc.nextLine();
		
		System.out.println("\nEnter Address:");
		String caddress = sc.nextLine();
		
		Customer customer = new Customer(cname,cage, cphoneNumber, caddress);
		
		try {
			if(dao.insert(customer)) {
				System.out.println("Customer is successfully registered!!");
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		
	}

}
