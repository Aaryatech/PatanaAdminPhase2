package com.ats.adminpanel.model;

public class Shape {

	private int shapeId;
	
	private String shapeName;
	
	private String shapeDesc;
	
	private Integer extInt1,extInt2;
	
	private String extVar1,extVar2;
	
	private int delStatus;
	
	
	
	

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getShapeId() {
		return shapeId;
	}

	public void setShapeId(int shapeId) {
		this.shapeId = shapeId;
	}

	public String getShapeName() {
		return shapeName;
	}

	public void setShapeName(String shapeName) {
		this.shapeName = shapeName;
	}

	public String getShapeDesc() {
		return shapeDesc;
	}

	public void setShapeDesc(String shapeDesc) {
		this.shapeDesc = shapeDesc;
	}

	public Integer getExtInt1() {
		return extInt1;
	}

	public void setExtInt1(Integer extInt1) {
		this.extInt1 = extInt1;
	}

	public Integer getExtInt2() {
		return extInt2;
	}

	public void setExtInt2(Integer extInt2) {
		this.extInt2 = extInt2;
	}

	public String getExtVar1() {
		return extVar1;
	}

	public void setExtVar1(String extVar1) {
		this.extVar1 = extVar1;
	}

	public String getExtVar2() {
		return extVar2;
	}

	public void setExtVar2(String extVar2) {
		this.extVar2 = extVar2;
	}

	@Override
	public String toString() {
		return "Shape [shapeId=" + shapeId + ", shapeName=" + shapeName + ", shapeDesc=" + shapeDesc + ", extInt1="
				+ extInt1 + ", extInt2=" + extInt2 + ", extVar1=" + extVar1 + ", extVar2=" + extVar2 + ", delStatus="
				+ delStatus + "]";
	}

	
	
	
	
	
	
	
	
	
}
