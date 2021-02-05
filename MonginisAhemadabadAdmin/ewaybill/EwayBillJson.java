package com.ats.adminpanel.model.ewaybill;

import java.util.ArrayList;

public class EwayBillJson {

	String version;
	ArrayList<ReqEwayBill> billLists;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ArrayList<ReqEwayBill> getBillLists() {
		return billLists;
	}

	public void setBillLists(ArrayList<ReqEwayBill> billLists) {
		this.billLists = billLists;
	}

	@Override
	public String toString() {
		return "EwayBillJson [version=" + version + ", billLists=" + billLists + "]";
	}

}
