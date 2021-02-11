package com.ats.adminpanel.model.ggreports;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PendingGrnGvnItemWise {

	private String uid;
	private String grngvnSrno;
	private String grngvnDate;
	private String itemName;
	private int grnGvnQty;
	private int grngvnStatus;
	private int grnGvnHeaderId;
	private int isGrn;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getGrngvnSrno() {
		return grngvnSrno;
	}
	public void setGrngvnSrno(String grngvnSrno) {
		this.grngvnSrno = grngvnSrno;
	}
	public String getGrngvnDate() {
		return grngvnDate;
	}
	public void setGrngvnDate(String grngvnDate) {
		this.grngvnDate = grngvnDate;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getGrnGvnQty() {
		return grnGvnQty;
	}
	public void setGrnGvnQty(int grnGvnQty) {
		this.grnGvnQty = grnGvnQty;
	}
	public int getGrngvnStatus() {
		return grngvnStatus;
	}
	public void setGrngvnStatus(int grngvnStatus) {
		this.grngvnStatus = grngvnStatus;
	}
	public int getGrnGvnHeaderId() {
		return grnGvnHeaderId;
	}
	public void setGrnGvnHeaderId(int grnGvnHeaderId) {
		this.grnGvnHeaderId = grnGvnHeaderId;
	}
	public int getIsGrn() {
		return isGrn;
	}
	public void setIsGrn(int isGrn) {
		this.isGrn = isGrn;
	}
	@Override
	public String toString() {
		return "PendingGrnGvnItemWise [uid=" + uid + ", grngvnSrno=" + grngvnSrno + ", grngvnDate=" + grngvnDate
				+ ", itemName=" + itemName + ", grnGvnQty=" + grnGvnQty + ", grngvnStatus=" + grngvnStatus
				+ ", grnGvnHeaderId=" + grnGvnHeaderId + ", isGrn=" + isGrn + "]";
	}
	
}
