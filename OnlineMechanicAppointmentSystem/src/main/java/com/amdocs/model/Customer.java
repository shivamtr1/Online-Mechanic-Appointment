package com.amdocs.model;

public class Customer {
	private int cid;
	private String cname;
	private int cage;
	private int cphoneNumber;
	private String caddress;
	
	public Customer() {
		
	}

	public Customer(int cid, String cname, int cage, int cphoneNumber, String caddress) {
		this.cid = cid;
		this.cname = cname;
		this.cage = cage;
		this.cphoneNumber = cphoneNumber;
		this.caddress = caddress;
	}

	public Customer(String cname, int cage, int cphoneNumber, String caddress) {
		this.cname = cname;
		this.cage = cage;
		this.cphoneNumber = cphoneNumber;
		this.caddress = caddress;
	}

	
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public int getCage() {
		return cage;
	}

	public void setCage(int cage) {
		this.cage = cage;
	}

	public int getCphoneNumber() {
		return cphoneNumber;
	}

	public void setCphoneNumber(int cphoneNumber) {
		this.cphoneNumber = cphoneNumber;
	}

	public String getCaddress() {
		return caddress;
	}

	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}

	@Override
	public String toString() {
		return "Customer [id=" + cid + ", name=" + cname + ", cage=" + cage + ", mobileNumber=" + cphoneNumber + ", address=" + caddress
				+ "]";
	}
	


}
