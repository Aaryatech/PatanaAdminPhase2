package com.ats.adminpanel.model.billing;

public class PendingBillDetail {
	
	private int billDetailId;
	private int billId;
	private float taxableAmt;
	private float taxAmt;
	private float totalAmt;
	private String invoiceNoCrnNo;
	private int bilNoCrnId;
	private int type;
	private int delStatus;
	private String insertDatetime;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;

	public int getBillDetailId() {
		return billDetailId;
	}

	public void setBillDetailId(int billDetailId) {
		this.billDetailId = billDetailId;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public float getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public float getTaxAmt() {
		return taxAmt;
	}

	public void setTaxAmt(float taxAmt) {
		this.taxAmt = taxAmt;
	}

	public float getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(float totalAmt) {
		this.totalAmt = totalAmt;
	}
	
	public String getInvoiceNoCrnNo() {
		return invoiceNoCrnNo;
	}

	public void setInvoiceNoCrnNo(String invoiceNoCrnNo) {
		this.invoiceNoCrnNo = invoiceNoCrnNo;
	}

	public int getBilNoCrnId() {
		return bilNoCrnId;
	}

	public void setBilNoCrnId(int bilNoCrnId) {
		this.bilNoCrnId = bilNoCrnId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public String getInsertDatetime() {
		return insertDatetime;
	}

	public void setInsertDatetime(String insertDatetime) {
		this.insertDatetime = insertDatetime;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	@Override
	public String toString() {
		return "PendingBillDetail [billDetailId=" + billDetailId + ", billId=" + billId + ", taxableAmt=" + taxableAmt
				+ ", taxAmt=" + taxAmt + ", totalAmt=" + totalAmt + ", invoiceNoCrnNo=" + invoiceNoCrnNo
				+ ", bilNoCrnId=" + bilNoCrnId + ", type=" + type + ", delStatus=" + delStatus + ", insertDatetime="
				+ insertDatetime + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2="
				+ exVar2 + "]";
	}

}
