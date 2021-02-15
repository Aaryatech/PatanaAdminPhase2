package com.ats.adminpanel.model.ggreports;


//Sac 15Feb2021
public class GGProdWiseReport {
private String uid;
private int itemId;

private int grnGvnQty;
private int frId;

private String itemName;

public String getUid() {
	return uid;
}

public void setUid(String uid) {
	this.uid = uid;
}

public int getItemId() {
	return itemId;
}

public void setItemId(int itemId) {
	this.itemId = itemId;
}

public int getGrnGvnQty() {
	return grnGvnQty;
}

public void setGrnGvnQty(int grnGvnQty) {
	this.grnGvnQty = grnGvnQty;
}

public int getFrId() {
	return frId;
}

public void setFrId(int frId) {
	this.frId = frId;
}

public String getItemName() {
	return itemName;
}

public void setItemName(String itemName) {
	this.itemName = itemName;
}

@Override
public String toString() {
	return "GGProdWiseReport [uid=" + uid + ", itemId=" + itemId + ", grnGvnQty=" + grnGvnQty + ", frId=" + frId
			+ ", itemName=" + itemName + "]";
}


	
}
