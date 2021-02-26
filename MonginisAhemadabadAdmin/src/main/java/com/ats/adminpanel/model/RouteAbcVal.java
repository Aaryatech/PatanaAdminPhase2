package com.ats.adminpanel.model;

public class RouteAbcVal {
	
	private int abcId;
	private String abcVal;
	private int delStatus;
	private int exInt1;
	private String exVar1;

	public int getAbcId() {
		return abcId;
	}

	public void setAbcId(int abcId) {
		this.abcId = abcId;
	}

	public String getAbcVal() {
		return abcVal;
	}

	public void setAbcVal(String abcVal) {
		this.abcVal = abcVal;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	@Override
	public String toString() {
		return "RouteAbcVal [abcId=" + abcId + ", abcVal=" + abcVal + ", delStatus=" + delStatus + ", exInt1=" + exInt1
				+ ", exVar1=" + exVar1 + "]";
	}
	
	
}
