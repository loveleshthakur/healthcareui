package com.healthcare.model;


public class Phone {
    private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private long phoenumber;
	
	public long getPhoenumber() {
		return phoenumber;
	}
	public void setPhoenumber(long phoenumber) {
		this.phoenumber = phoenumber;
	}
	@Override
	public String toString() {
		return "Phone [id=" + id + ", phoenumber=" + phoenumber + "]";
	}
	
}
