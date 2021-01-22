package com.ats.adminpanel.model;

public class DispatchSpCake {
	
	private int spOrderNo;
	private int spId;
	private String spName;
	private String spCode;
	private String spfName;
	private String frOrderNo;
	private float noOfKgs;
	private float spBackendRate;
	public int getSpOrderNo() {
		return spOrderNo;
	}
	public void setSpOrderNo(int spOrderNo) {
		this.spOrderNo = spOrderNo;
	}
	public int getSpId() {
		return spId;
	}
	public void setSpId(int spId) {
		this.spId = spId;
	}
	public String getSpName() {
		return spName;
	}
	public void setSpName(String spName) {
		this.spName = spName;
	}
	public String getSpCode() {
		return spCode;
	}
	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}
	public String getSpfName() {
		return spfName;
	}
	public void setSpfName(String spfName) {
		this.spfName = spfName;
	}
	public String getFrOrderNo() {
		return frOrderNo;
	}
	public void setFrOrderNo(String frOrderNo) {
		this.frOrderNo = frOrderNo;
	}
	public float getNoOfKgs() {
		return noOfKgs;
	}
	public void setNoOfKgs(float noOfKgs) {
		this.noOfKgs = noOfKgs;
	}
	public float getSpBackendRate() {
		return spBackendRate;
	}
	public void setSpBackendRate(float spBackendRate) {
		this.spBackendRate = spBackendRate;
	}
	@Override
	public String toString() {
		return "DispatchSpCake [spOrderNo=" + spOrderNo + ", spId=" + spId + ", spName=" + spName + ", spCode=" + spCode
				+ ", spfName=" + spfName + ", frOrderNo=" + frOrderNo + ", noOfKgs=" + noOfKgs + ", spBackendRate="
				+ spBackendRate + "]";
	}
	
	
}
