package com.ats.adminpanel.model.billing;

public class PendingBills {
	private int billNo;
	private String invoiceNo;
	private String billDate;
	private int frId;
	private float grandTotal;
	private float taxableAmt;
	private float totalTax;
	private int pendingBill;
	private int billType;
	
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
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public int getFrId() {
		return frId;
	}
	public void setFrId(int frId) {
		this.frId = frId;
	}
	public float getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
	}
	public float getTaxableAmt() {
		return taxableAmt;
	}
	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}
	public float getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(float totalTax) {
		this.totalTax = totalTax;
	}
	public int getPendingBill() {
		return pendingBill;
	}
	public void setPendingBill(int pendingBill) {
		this.pendingBill = pendingBill;
	}
	public int getBillType() {
		return billType;
	}
	public void setBillType(int billType) {
		this.billType = billType;
	}
	@Override
	public String toString() {
		return "PendingBills [billNo=" + billNo + ", invoiceNo=" + invoiceNo + ", billDate=" + billDate + ", frId="
				+ frId + ", grandTotal=" + grandTotal + ", taxableAmt=" + taxableAmt + ", totalTax=" + totalTax
				+ ", pendingBill=" + pendingBill + ", billType=" + billType + "]";
	}
}
