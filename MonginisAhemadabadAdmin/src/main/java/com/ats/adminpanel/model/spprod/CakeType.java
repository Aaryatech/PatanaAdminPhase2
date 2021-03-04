package com.ats.adminpanel.model.spprod;

public class CakeType {
	
	private int cakeTypeId;	
	private String typeName;
	private int extraFieldApplicable;
	private int typeCondition;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	private int delStatus;
	private int isActive;

	public int getCakeTypeId() {
		return cakeTypeId;
	}

	public void setCakeTypeId(int cakeTypeId) {
		this.cakeTypeId = cakeTypeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getExtraFieldApplicable() {
		return extraFieldApplicable;
	}

	public void setExtraFieldApplicable(int extraFieldApplicable) {
		this.extraFieldApplicable = extraFieldApplicable;
	}

	public int getTypeCondition() {
		return typeCondition;
	}

	public void setTypeCondition(int typeCondition) {
		this.typeCondition = typeCondition;
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

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "CakeType [cakeTypeId=" + cakeTypeId + ", typeName=" + typeName + ", extraFieldApplicable="
				+ extraFieldApplicable + ", typeCondition=" + typeCondition + ", exInt1=" + exInt1 + ", exInt2="
				+ exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", delStatus=" + delStatus + ", isActive="
				+ isActive + "]";
	}
	
	
}
