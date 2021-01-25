package com.ats.adminpanel.model.billing;

public class PendingBillCreditNote {
	private int crnId;
	private int crnNo;
	private float crnTaxableAmt;
	private float crnTotalTax;
	private float crnGrandTotal;
	private int frId;
	private int crType;
	
	public int getCrnId() {
		return crnId;
	}
	public void setCrnId(int crnId) {
		this.crnId = crnId;
	}
	public int getCrnNo() {
		return crnNo;
	}
	public void setCrnNo(int crnNo) {
		this.crnNo = crnNo;
	}
	public float getCrnTaxableAmt() {
		return crnTaxableAmt;
	}
	public void setCrnTaxableAmt(float crnTaxableAmt) {
		this.crnTaxableAmt = crnTaxableAmt;
	}
	public float getCrnTotalTax() {
		return crnTotalTax;
	}
	public void setCrnTotalTax(float crnTotalTax) {
		this.crnTotalTax = crnTotalTax;
	}
	public float getCrnGrandTotal() {
		return crnGrandTotal;
	}
	public void setCrnGrandTotal(float crnGrandTotal) {
		this.crnGrandTotal = crnGrandTotal;
	}
	public int getFrId() {
		return frId;
	}
	public void setFrId(int frId) {
		this.frId = frId;
	}
	public int getCrType() {
		return crType;
	}
	public void setCrType(int crType) {
		this.crType = crType;
	}
	@Override
	public String toString() {
		return "PendingBillCreditNote [crnId=" + crnId + ", crnNo=" + crnNo + ", crnTaxableAmt=" + crnTaxableAmt
				+ ", crnTotalTax=" + crnTotalTax + ", crnGrandTotal=" + crnGrandTotal + ", frId=" + frId + ", crType="
				+ crType + "]";
	}
	
}
