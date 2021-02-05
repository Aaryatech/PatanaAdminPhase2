package com.ats.adminpanel.model.ewaybill;

public class Error {

	private String error_cd;
	private String message;
	
	
	public String getError_cd() {
		return error_cd;
	}
	public void setError_cd(String error_cd) {
		this.error_cd = error_cd;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Error [error_cd=" + error_cd + ", message=" + message + "]";
	}
	
	
	
}
