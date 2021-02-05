package com.ats.adminpanel.model.ewaybill;

public class GetAuthToken {

	private String status;
	private String authtoken;
	private String sek;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAuthtoken() {
		return authtoken;
	}
	public void setAuthtoken(String authtoken) {
		this.authtoken = authtoken;
	}
	public String getSek() {
		return sek;
	}
	public void setSek(String sek) {
		this.sek = sek;
	}
	@Override
	public String toString() {
		return "GetAuthToken [status=" + status + ", authtoken=" + authtoken + ", sek=" + sek + "]";
	}
	
	
	
	
}
