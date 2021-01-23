package com.ats.adminpanel.model.dashboard;

public class FrFdaOwnerDtl {

	private int frId;
	private String frName;
	private String frCode;
	private String fdaExpiryDate;
	private String frAgreementDate;
	private String ownerBirthDate;
	private int currFdaDateDiff;
	private long dayDiffDob;
	private int currAgrmntDateDiff;	

	public int getFrId() {
		return frId;
	}
	public void setFrId(int frId) {
		this.frId = frId;
	}
	public String getFrName() {
		return frName;
	}
	public void setFrName(String frName) {
		this.frName = frName;
	}
	
	public String getFrCode() {
		return frCode;
	}
	public void setFrCode(String frCode) {
		this.frCode = frCode;
	}
	
	public String getFdaExpiryDate() {
		return fdaExpiryDate;
	}
	public void setFdaExpiryDate(String fdaExpiryDate) {
		this.fdaExpiryDate = fdaExpiryDate;
	}
	
	public String getFrAgreementDate() {
		return frAgreementDate;
	}
	public void setFrAgreementDate(String frAgreementDate) {
		this.frAgreementDate = frAgreementDate;
	}
	public String getOwnerBirthDate() {
		return ownerBirthDate;
	}
	public void setOwnerBirthDate(String ownerBirthDate) {
		this.ownerBirthDate = ownerBirthDate;
	}
	public int getCurrFdaDateDiff() {
		return currFdaDateDiff;
	}
	public void setCurrFdaDateDiff(int currFdaDateDiff) {
		this.currFdaDateDiff = currFdaDateDiff;
	}
	public long getDayDiffDob() {
		return dayDiffDob;
	}
	public void setDayDiffDob(long dayDiffDob) {
		this.dayDiffDob = dayDiffDob;
	}
	public int getCurrAgrmntDateDiff() {
		return currAgrmntDateDiff;
	}
	public void setCurrAgrmntDateDiff(int currAgrmntDateDiff) {
		this.currAgrmntDateDiff = currAgrmntDateDiff;
	}
	@Override
	public String toString() {
		return "FrFdaOwnerDtl [frId=" + frId + ", frName=" + frName + ", frCode=" + frCode + ", fdaExpiryDate="
				+ fdaExpiryDate + ", frAgreementDate=" + frAgreementDate + ", ownerBirthDate=" + ownerBirthDate
				+ ", currFdaDateDiff=" + currFdaDateDiff + ", dayDiffDob=" + dayDiffDob + ", currAgrmntDateDiff="
				+ currAgrmntDateDiff + "]";
	}

}
