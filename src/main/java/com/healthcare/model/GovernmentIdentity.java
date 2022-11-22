package com.healthcare.model;
public class GovernmentIdentity {

	private String identityType;
	private String identityNo;
	private long id;
	

	
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	public String getIdentityNo() {
		return identityNo;
	}
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "GovernmentIdentity [identityType=" + identityType + ", identityNo=" + identityNo + ", id=" + id + "]";
	}
	
}
