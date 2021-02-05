package com.ats.adminpanel.model.ewaybill;

import java.util.ArrayList;

public class NewEwayBillJsonDisplay {

	String version;
	ArrayList<NewEwayBillJson> billLists;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ArrayList<NewEwayBillJson> getBillLists() {
		return billLists;
	}

	public void setBillLists(ArrayList<NewEwayBillJson> billLists) {
		this.billLists = billLists;
	}

	@Override
	public String toString() {
		return "NewEwayBillJsonDisplay [version=" + version + ", billLists=" + billLists + "]";
	}

}
