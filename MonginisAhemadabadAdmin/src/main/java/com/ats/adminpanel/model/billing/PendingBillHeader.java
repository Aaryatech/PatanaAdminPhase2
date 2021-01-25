package com.ats.adminpanel.model.billing;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PendingBillHeader {
	
	private int billId;
	private int frId;
	private String processDate;
	private float taxableAmt;
	private float taxAmt;
	private float grandTotal;
	private String remark;
	private int paymentMode;
	private int status;
	private int delStatus;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	private String insertDatetime;
	
	List<PendingBillDetail> billDetailList;

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}
	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
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

	public float getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(int paymentMode) {
		this.paymentMode = paymentMode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getExInt2() {
		return exInt2;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
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

	public List<PendingBillDetail> getBillDetailList() {
		return billDetailList;
	}

	public void setBillDetailList(List<PendingBillDetail> billDetailList) {
		this.billDetailList = billDetailList;
	}

	public String getInsertDatetime() {
		return insertDatetime;
	}

	public void setInsertDatetime(String insertDatetime) {
		this.insertDatetime = insertDatetime;
	}

	@Override
	public String toString() {
		return "PendingBillHeader [billId=" + billId + ", frId=" + frId + ", processDate=" + processDate
				+ ", taxableAmt=" + taxableAmt + ", taxAmt=" + taxAmt + ", grandTotal=" + grandTotal + ", remark="
				+ remark + ", paymentMode=" + paymentMode + ", status=" + status + ", delStatus=" + delStatus
				+ ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", insertDatetime=" + insertDatetime + ", billDetailList=" + billDetailList + "]";
	}

}
