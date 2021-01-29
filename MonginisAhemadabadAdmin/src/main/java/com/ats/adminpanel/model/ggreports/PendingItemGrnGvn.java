package com.ats.adminpanel.model.ggreports;

public class PendingItemGrnGvn {
	
	private int itemId;
	private String itemName;
	private int grnGvnId;
	private int reqQty;
	private int aprQty;
	private float aprAmt;
	private float reqAmt;
	private int grnGvnHeaderId;
	private float totalAmt;
	private String grngvnSrno;
	private String grngvnDate;
	private int isGrn;
	private int isCreditNote;
	private int grngvnStatus;
	private int billNo;
	private String invoiceNo;
	private String billDate;
	private int frId;
	private float grnPer;
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getGrnGvnId() {
		return grnGvnId;
	}
	public void setGrnGvnId(int grnGvnId) {
		this.grnGvnId = grnGvnId;
	}
	public int getReqQty() {
		return reqQty;
	}
	public void setReqQty(int reqQty) {
		this.reqQty = reqQty;
	}
	public int getAprQty() {
		return aprQty;
	}
	public void setAprQty(int aprQty) {
		this.aprQty = aprQty;
	}
	public float getAprAmt() {
		return aprAmt;
	}
	public void setAprAmt(float aprAmt) {
		this.aprAmt = aprAmt;
	}
	public float getReqAmt() {
		return reqAmt;
	}
	public void setReqAmt(float reqAmt) {
		this.reqAmt = reqAmt;
	}
	public int getGrnGvnHeaderId() {
		return grnGvnHeaderId;
	}
	public void setGrnGvnHeaderId(int grnGvnHeaderId) {
		this.grnGvnHeaderId = grnGvnHeaderId;
	}
	public float getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(float totalAmt) {
		this.totalAmt = totalAmt;
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
	public int getIsGrn() {
		return isGrn;
	}
	public void setIsGrn(int isGrn) {
		this.isGrn = isGrn;
	}
	public int getIsCreditNote() {
		return isCreditNote;
	}
	public void setIsCreditNote(int isCreditNote) {
		this.isCreditNote = isCreditNote;
	}
	public int getGrngvnStatus() {
		return grngvnStatus;
	}
	public void setGrngvnStatus(int grngvnStatus) {
		this.grngvnStatus = grngvnStatus;
	}
	public int getBillNo() {
		return billNo;
	}
	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public int getFrId() {
		return frId;
	}
	public void setFrId(int frId) {
		this.frId = frId;
	}
	public float getGrnPer() {
		return grnPer;
	}
	public void setGrnPer(float grnPer) {
		this.grnPer = grnPer;
	}
	@Override
	public String toString() {
		return "PendingItemGrnGvn [itemId=" + itemId + ", itemName=" + itemName + ", grnGvnId=" + grnGvnId + ", reqQty="
				+ reqQty + ", aprQty=" + aprQty + ", aprAmt=" + aprAmt + ", reqAmt=" + reqAmt + ", grnGvnHeaderId="
				+ grnGvnHeaderId + ", totalAmt=" + totalAmt + ", grngvnSrno=" + grngvnSrno + ", grngvnDate="
				+ grngvnDate + ", isGrn=" + isGrn + ", isCreditNote=" + isCreditNote + ", grngvnStatus=" + grngvnStatus
				+ ", billNo=" + billNo + ", invoiceNo=" + invoiceNo + ", billDate=" + billDate + ", frId=" + frId
				+ ", grnPer=" + grnPer + "]";
	}
	
}
