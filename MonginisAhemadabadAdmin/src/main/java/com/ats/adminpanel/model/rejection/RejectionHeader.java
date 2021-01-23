package com.ats.adminpanel.model.rejection;

import java.util.List;



public class RejectionHeader {
	
	private int rejectId;
	
	private String makerDate;
	
	private int type;
	
	private int remarkId;
	
	private String remark;
	
	private int empId;
	
	private int userId;
	
	private int delStatus;
	
	private int exInt1,exInt2;
	
	private String exVar1,exVar2;
	
	
	List<RejectionDetail> detailList;

	public int getRejectId() {
		return rejectId;
	}

	public void setRejectId(int rejectId) {
		this.rejectId = rejectId;
	}

	public String getMakerDate() {
		return makerDate;
	}

	public void setMakerDate(String makerDate) {
		this.makerDate = makerDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(int remarkId) {
		this.remarkId = remarkId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public List<RejectionDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<RejectionDetail> detailList) {
		this.detailList = detailList;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	@Override
	public String toString() {
		return "RejectionHeader [rejectId=" + rejectId + ", makerDate=" + makerDate + ", type=" + type + ", remarkId="
				+ remarkId + ", remark=" + remark + ", empId=" + empId + ", userId=" + userId + ", delStatus="
				+ delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2
				+ ", detailList=" + detailList + "]";
	}

	


	
	
	
	

}
