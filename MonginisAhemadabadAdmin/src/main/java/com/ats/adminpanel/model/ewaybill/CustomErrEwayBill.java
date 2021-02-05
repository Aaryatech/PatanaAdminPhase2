package com.ats.adminpanel.model.ewaybill;


public class CustomErrEwayBill {

	int billNo;
	String invoiceNo;
	String message;
	String errorCode;
	String timeStamp;
	
	
	
	public int getBillNo() {
		return billNo;
	}
	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@Override
	public String toString() {
		return "CustomErrEwayBill [billNo=" + billNo + ", invoiceNo=" + invoiceNo + ", message=" + message
				+ ", errorCode=" + errorCode + ", timeStamp=" + timeStamp + "]";
	}
	
}
