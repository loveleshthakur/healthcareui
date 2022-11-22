package com.healthcare.model;

import java.util.Date;
import java.util.List;
import java.util.Set;




public class Patient {

	

	
    private int patientId;
    
	private String name;
    private Date dob ;
   
    
  
    private List<Phone> phone;
   
  
    private List<GovernmentIdentity> govtIds;
    
    
	private List<Address> addresses;

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public List<Phone> getPhone() {
		return phone;
	}

	public void setPhone(List<Phone>phone) {
		this.phone = phone;
	}

	public List<GovernmentIdentity> getGovtIds() {
		return govtIds;
	}

	public void setGovtIds(List<GovernmentIdentity> govtIds) {
		this.govtIds = govtIds;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
    
	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", name=" + name + ", dob=" + dob + ", phone=" + phone + ", govtIds="
				+ govtIds + ", addresses=" + addresses + "]";
	}
	
		}
