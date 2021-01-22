package com.ats.adminpanel.model;

import java.util.Date;



public class RegCakeAsSpDispatchReport {
	
	
		private int rspId;
	
	private int frId;
	
	private int itemId;
	
	private Date rspDeliveryDt;
	
	private String fromDate;
	private String toDate;
	

	private String frName;
	
	int qty;
	
	String itemName;

	public int getRspId() {
		return rspId;
	}

	public void setRspId(int rspId) {
		this.rspId = rspId;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public Date getRspDeliveryDt() {
		return rspDeliveryDt;
	}

	public void setRspDeliveryDt(Date rspDeliveryDt) {
		this.rspDeliveryDt = rspDeliveryDt;
	}

	public String getFrName() {
		return frName;
	}

	public void setFrName(String frName) {
		this.frName = frName;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "RegCakeAsSpDispatchReport [rspId=" + rspId + ", frId=" + frId + ", itemId=" + itemId
				+ ", rspDeliveryDt=" + rspDeliveryDt + ", fromDate=" + fromDate + ", toDate=" + toDate + ", frName="
				+ frName + ", qty=" + qty + ", itemName=" + itemName + "]";
	}


}
