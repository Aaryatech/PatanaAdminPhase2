package com.ats.adminpanel.model.ewaybill;

public class ResponseCode {

	private String status_cd;
	private Error error;
	
	public String getStatus_cd() {
		return status_cd;
	}
	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	@Override
	public String toString() {
		return "ResponseCode [status_cd=" + status_cd + ", error=" + error + "]";
	}
	
}
