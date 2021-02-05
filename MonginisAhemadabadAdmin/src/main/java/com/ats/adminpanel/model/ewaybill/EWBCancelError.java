package com.ats.adminpanel.model.ewaybill;


public class EWBCancelError {

	private String status;
	private Error error;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ewbCancelError [status=" + status + ", error=" + error + "]";
	}

}
