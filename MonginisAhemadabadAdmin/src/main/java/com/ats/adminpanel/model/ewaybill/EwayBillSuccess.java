package com.ats.adminpanel.model.ewaybill;

public class EwayBillSuccess {

	private String ewayBillNo;
	private String ewayBillDate;
	private String validUpto;
	private String alert;
	public String getEwayBillNo() {
		return ewayBillNo;
	}
	public void setEwayBillNo(String ewayBillNo) {
		this.ewayBillNo = ewayBillNo;
	}
	public String getEwayBillDate() {
		return ewayBillDate;
	}
	public void setEwayBillDate(String ewayBillDate) {
		this.ewayBillDate = ewayBillDate;
	}
	public String getValidUpto() {
		return validUpto;
	}
	public void setValidUpto(String validUpto) {
		this.validUpto = validUpto;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	@Override
	public String toString() {
		return "EwayBillSuccess [ewayBillNo=" + ewayBillNo + ", ewayBillDate=" + ewayBillDate + ", validUpto="
				+ validUpto + ", alert=" + alert + "]";
	}
}
