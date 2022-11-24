package com.healthcare.model;
public class Address {
	
    private Long id;
	
	
	 
	
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


private String state ;
private String city;
private Long pin;
private String street;


public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public Long getPin() {
	return pin;
}
public void setPin(Long pin) {
	this.pin = pin;
}
@Override
public String toString() {
	return "Address [state=" + state + ", city=" + city + ", pin=" + pin + ", street=" + street + "]";
}
public String getStreet() {
	return street;
}
public void setStreet(String street) {
	this.street = street;
}


}
