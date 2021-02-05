package com.ats.adminpanel.model.ewaybill;

public class CancelEWBModel {

	private String ewbNo;
	private int cancelRsnCode;
	private String cancelRmrk;

	public String getEwbNo() {
		return ewbNo;
	}

	public void setEwbNo(String ewbNo) {
		this.ewbNo = ewbNo;
	}

	public int getCancelRsnCode() {
		return cancelRsnCode;
	}

	public void setCancelRsnCode(int cancelRsnCode) {
		this.cancelRsnCode = cancelRsnCode;
	}

	public String getCancelRmrk() {
		return cancelRmrk;
	}

	public void setCancelRmrk(String cancelRmrk) {
		this.cancelRmrk = cancelRmrk;
	}

	@Override
	public String toString() {
		return "CancelEWBModel [ewbNo=" + ewbNo + ", cancelRsnCode=" + cancelRsnCode + ", cancelRmrk=" + cancelRmrk
				+ "]";
	}

}
