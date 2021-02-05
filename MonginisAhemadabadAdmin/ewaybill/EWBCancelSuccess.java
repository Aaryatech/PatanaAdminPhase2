package com.ats.adminpanel.model.ewaybill;

public class EWBCancelSuccess {

	private String ewayBillNo;
	private String cancelDate;

	public String getEwayBillNo() {
		return ewayBillNo;
	}

	public void setEwayBillNo(String ewayBillNo) {
		this.ewayBillNo = ewayBillNo;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	@Override
	public String toString() {
		return "ewbCancelSuccess [ewayBillNo=" + ewayBillNo + ", cancelDate=" + cancelDate + "]";
	}

}
