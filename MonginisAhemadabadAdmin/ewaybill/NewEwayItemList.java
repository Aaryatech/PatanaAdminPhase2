package com.ats.adminpanel.model.ewaybill;

public class NewEwayItemList {

	private int itemNo;
	private String productName;
	private String productDesc;
	private int hsnCode;
	private double quantity;
	private String qtyUnit; // maxLen 3 char
	private double taxableAmount;
	private double sgstRate;
	private double cgstRate;
	private double igstRate;
	private double cessRate;
	private double cessNonAdvol;
	
	

	public NewEwayItemList() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public NewEwayItemList(int itemNo, String productName, String productDesc, int hsnCode, double quantity,
			String qtyUnit, double taxableAmount, double sgstRate, double cgstRate, double igstRate, double cessRate,
			double cessNonAdvol) {
		super();
		this.itemNo = itemNo;
		this.productName = productName;
		this.productDesc = productDesc;
		this.hsnCode = hsnCode;
		this.quantity = quantity;
		this.qtyUnit = qtyUnit;
		this.taxableAmount = taxableAmount;
		this.sgstRate = sgstRate;
		this.cgstRate = cgstRate;
		this.igstRate = igstRate;
		this.cessRate = cessRate;
		this.cessNonAdvol = cessNonAdvol;
	}



	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public int getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(int hsnCode) {
		this.hsnCode = hsnCode;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getQtyUnit() {
		return qtyUnit;
	}

	public void setQtyUnit(String qtyUnit) {
		this.qtyUnit = qtyUnit;
	}

	public double getTaxableAmount() {
		return taxableAmount;
	}

	public void setTaxableAmount(double taxableAmount) {
		this.taxableAmount = taxableAmount;
	}

	public double getSgstRate() {
		return sgstRate;
	}

	public void setSgstRate(double sgstRate) {
		this.sgstRate = sgstRate;
	}

	public double getCgstRate() {
		return cgstRate;
	}

	public void setCgstRate(double cgstRate) {
		this.cgstRate = cgstRate;
	}

	public double getIgstRate() {
		return igstRate;
	}

	public void setIgstRate(double igstRate) {
		this.igstRate = igstRate;
	}

	public double getCessRate() {
		return cessRate;
	}

	public void setCessRate(double cessRate) {
		this.cessRate = cessRate;
	}

	public double getCessNonAdvol() {
		return cessNonAdvol;
	}

	public void setCessNonAdvol(double cessNonAdvol) {
		this.cessNonAdvol = cessNonAdvol;
	}

	@Override
	public String toString() {
		return "NewEwayItemList [itemNo=" + itemNo + ", productName=" + productName + ", productDesc=" + productDesc
				+ ", hsnCode=" + hsnCode + ", quantity=" + quantity + ", qtyUnit=" + qtyUnit + ", taxableAmount="
				+ taxableAmount + ", sgstRate=" + sgstRate + ", cgstRate=" + cgstRate + ", igstRate=" + igstRate
				+ ", cessRate=" + cessRate + ", cessNonAdvol=" + cessNonAdvol + "]";
	}

}
