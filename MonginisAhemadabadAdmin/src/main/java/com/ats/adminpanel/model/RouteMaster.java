package com.ats.adminpanel.model;

public class RouteMaster {
	 
	private int routeId; 
	private String routeName; 
    private int delStatus; 
    private int abcType; 
    private int seqNo;
    private String shortName;
    private String routePrefix;
    private float maxKm;
    private float minKm;
    private int routeType;
    private int exInt1;
    private int exInt2;
    private String exVar1;
    private String exVar2;
    
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public int getAbcType() {
		return abcType;
	}
	public void setAbcType(int abcType) {
		this.abcType = abcType;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getRoutePrefix() {
		return routePrefix;
	}
	public void setRoutePrefix(String routePrefix) {
		this.routePrefix = routePrefix;
	}
	public float getMaxKm() {
		return maxKm;
	}
	public void setMaxKm(float maxKm) {
		this.maxKm = maxKm;
	}
	public float getMinKm() {
		return minKm;
	}
	public void setMinKm(float minKm) {
		this.minKm = minKm;
	}
	public int getRouteType() {
		return routeType;
	}
	public void setRouteType(int routeType) {
		this.routeType = routeType;
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
		return "RouteMaster [routeId=" + routeId + ", routeName=" + routeName + ", delStatus=" + delStatus
				+ ", abcType=" + abcType + ", seqNo=" + seqNo + ", shortName=" + shortName + ", routePrefix="
				+ routePrefix + ", maxKm=" + maxKm + ", minKm=" + minKm + ", routeType=" + routeType + ", exInt1="
				+ exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}
}
