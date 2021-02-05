package com.ats.adminpanel.model.ewaybill;

import java.util.ArrayList;

public class NewEwayBillJson {

	private String userGstin;
	private String supplyType;
	private String subSupplyType;
	private String docType; // "enum": [ "INV", "CHL", "BIL","BOE","CNT","OTH" ],
	private String docNo; // maxLength": 16,"description": "Document Number (Alphanumeric with / and - are
							// allowed)"
	private String docDate; // "pattern": "[0-3][0-9]/[0-1][0-9]/[2][0][1-2][0-9]",
	private int transType; // regular ,billto ship to
	private String fromGstin; // "maxLength": 15, "minLength": 15,"pattern": "[0-9]{2}[0-9|A-Z]{13}",
	private String fromTrdName; // "maxLength": 100,
	private String fromAddr1; // "maxLength": 120,
	private String fromAddr2; // maxLength": 120,
	private String fromPlace; // "maxLength": 50,
	private int fromPincode; // "maximum": 999999,"minimum": 100000,
	private int fromStateCode;// "maximum": 99,
	private int actualFromStateCode;// "maximum": 99,
	private String toGstin;// "maxLength": 15, "minLength": 15, "pattern": "[0-9]{2}[0-9|A-Z]{13}",
	private String toTrdName; // "maxLength": 100,
	private String toAddr1;// "maxLength": 120,
	private String toAddr2;// "maxLength": 120,
	private String toPlace;// "maxLength": 50,
	private int toPincode; //
	private int toStateCode;
	private int actualToStateCode;
	private double totalValue;
	private double cgstValue;
	private double sgstValue;
	private double igstValue;
	private double cessValue;
	private double TotNonAdvolVal;
	private double OthValue;
	private double totInvValue;
	private String transMode;
	private String transDistance;
	private String transporterName;
	private String transporterId;
	private String transDocNo;
	private String transDocDate;
	private String vehicleNo;
	private String vehicleType;
	private int mainHsnCode;

	ArrayList<NewEwayItemList> itemList;

	public String getUserGstin() {
		return userGstin;
	}

	public void setUserGstin(String userGstin) {
		this.userGstin = userGstin;
	}

	public String getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(String supplyType) {
		this.supplyType = supplyType;
	}

	public String getSubSupplyType() {
		return subSupplyType;
	}

	public void setSubSupplyType(String subSupplyType) {
		this.subSupplyType = subSupplyType;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getDocDate() {
		return docDate;
	}

	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}

	public int getTransType() {
		return transType;
	}

	public void setTransType(int transType) {
		this.transType = transType;
	}

	public String getFromGstin() {
		return fromGstin;
	}

	public void setFromGstin(String fromGstin) {
		this.fromGstin = fromGstin;
	}

	public String getFromTrdName() {
		return fromTrdName;
	}

	public void setFromTrdName(String fromTrdName) {
		this.fromTrdName = fromTrdName;
	}

	public String getFromAddr1() {
		return fromAddr1;
	}

	public void setFromAddr1(String fromAddr1) {
		this.fromAddr1 = fromAddr1;
	}

	public String getFromAddr2() {
		return fromAddr2;
	}

	public void setFromAddr2(String fromAddr2) {
		this.fromAddr2 = fromAddr2;
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public int getFromPincode() {
		return fromPincode;
	}

	public void setFromPincode(int fromPincode) {
		this.fromPincode = fromPincode;
	}

	public int getFromStateCode() {
		return fromStateCode;
	}

	public void setFromStateCode(int fromStateCode) {
		this.fromStateCode = fromStateCode;
	}

	public int getActualFromStateCode() {
		return actualFromStateCode;
	}

	public void setActualFromStateCode(int actualFromStateCode) {
		this.actualFromStateCode = actualFromStateCode;
	}

	public String getToGstin() {
		return toGstin;
	}

	public void setToGstin(String toGstin) {
		this.toGstin = toGstin;
	}

	public String getToTrdName() {
		return toTrdName;
	}

	public void setToTrdName(String toTrdName) {
		this.toTrdName = toTrdName;
	}

	public String getToAddr1() {
		return toAddr1;
	}

	public void setToAddr1(String toAddr1) {
		this.toAddr1 = toAddr1;
	}

	public String getToAddr2() {
		return toAddr2;
	}

	public void setToAddr2(String toAddr2) {
		this.toAddr2 = toAddr2;
	}

	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	public int getToPincode() {
		return toPincode;
	}

	public void setToPincode(int toPincode) {
		this.toPincode = toPincode;
	}

	public int getToStateCode() {
		return toStateCode;
	}

	public void setToStateCode(int toStateCode) {
		this.toStateCode = toStateCode;
	}

	public int getActualToStateCode() {
		return actualToStateCode;
	}

	public void setActualToStateCode(int actualToStateCode) {
		this.actualToStateCode = actualToStateCode;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	public double getCgstValue() {
		return cgstValue;
	}

	public void setCgstValue(double cgstValue) {
		this.cgstValue = cgstValue;
	}

	public double getSgstValue() {
		return sgstValue;
	}

	public void setSgstValue(double sgstValue) {
		this.sgstValue = sgstValue;
	}

	public double getIgstValue() {
		return igstValue;
	}

	public void setIgstValue(double igstValue) {
		this.igstValue = igstValue;
	}

	public double getCessValue() {
		return cessValue;
	}

	public void setCessValue(double cessValue) {
		this.cessValue = cessValue;
	}

	public double getTotNonAdvolVal() {
		return TotNonAdvolVal;
	}

	public void setTotNonAdvolVal(double totNonAdvolVal) {
		TotNonAdvolVal = totNonAdvolVal;
	}

	public double getOthValue() {
		return OthValue;
	}

	public void setOthValue(double othValue) {
		OthValue = othValue;
	}

	public double getTotInvValue() {
		return totInvValue;
	}

	public void setTotInvValue(double totInvValue) {
		this.totInvValue = totInvValue;
	}

	public String getTransMode() {
		return transMode;
	}

	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}

	public String getTransDistance() {
		return transDistance;
	}

	public void setTransDistance(String transDistance) {
		this.transDistance = transDistance;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public String getTransporterId() {
		return transporterId;
	}

	public void setTransporterId(String transporterId) {
		this.transporterId = transporterId;
	}

	public String getTransDocNo() {
		return transDocNo;
	}

	public void setTransDocNo(String transDocNo) {
		this.transDocNo = transDocNo;
	}

	public String getTransDocDate() {
		return transDocDate;
	}

	public void setTransDocDate(String transDocDate) {
		this.transDocDate = transDocDate;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public int getMainHsnCode() {
		return mainHsnCode;
	}

	public void setMainHsnCode(int mainHsnCode) {
		this.mainHsnCode = mainHsnCode;
	}

	public ArrayList<NewEwayItemList> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<NewEwayItemList> itemList) {
		this.itemList = itemList;
	}

	@Override
	public String toString() {
		return "NewEwayBillJson [userGstin=" + userGstin + ", supplyType=" + supplyType + ", subSupplyType="
				+ subSupplyType + ", docType=" + docType + ", docNo=" + docNo + ", docDate=" + docDate + ", transType="
				+ transType + ", fromGstin=" + fromGstin + ", fromTrdName=" + fromTrdName + ", fromAddr1=" + fromAddr1
				+ ", fromAddr2=" + fromAddr2 + ", fromPlace=" + fromPlace + ", fromPincode=" + fromPincode
				+ ", fromStateCode=" + fromStateCode + ", actualFromStateCode=" + actualFromStateCode + ", toGstin="
				+ toGstin + ", toTrdName=" + toTrdName + ", toAddr1=" + toAddr1 + ", toAddr2=" + toAddr2 + ", toPlace="
				+ toPlace + ", toPincode=" + toPincode + ", toStateCode=" + toStateCode + ", actualToStateCode="
				+ actualToStateCode + ", totalValue=" + totalValue + ", cgstValue=" + cgstValue + ", sgstValue="
				+ sgstValue + ", igstValue=" + igstValue + ", cessValue=" + cessValue + ", TotNonAdvolVal="
				+ TotNonAdvolVal + ", OthValue=" + OthValue + ", totInvValue=" + totInvValue + ", transMode="
				+ transMode + ", transDistance=" + transDistance + ", transporterName=" + transporterName
				+ ", transporterId=" + transporterId + ", transDocNo=" + transDocNo + ", transDocDate=" + transDocDate
				+ ", vehicleNo=" + vehicleNo + ", vehicleType=" + vehicleType + ", mainHsnCode=" + mainHsnCode
				+ ", itemList=" + itemList + "]";
	}

}
