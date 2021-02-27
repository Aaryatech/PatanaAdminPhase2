package com.ats.adminpanel.model.item;

public class GetItemUomAndSup {
	
	int id;

	private String itemId;

	private String itemName;

	private int itemGrp1;

	private int itemGrp2;

	private int itemGrp3;

	private float itemRate1;

	private float itemRate2;

	private float itemMrp1;

	private float itemMrp2;

	private float itemMrp3;
	
	private String itemImage;

	private float itemTax1;

	private float itemTax2;

	private float itemTax3;

	private int itemIsUsed;

	private float itemSortId;

	private int grnTwo;

	private int delStatus;
	
	private int minQty;

	private float itemRate3;

	private int shelfLife;
	
	private String itemHsncd;
	private String uom;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemGrp1() {
		return itemGrp1;
	}
	public void setItemGrp1(int itemGrp1) {
		this.itemGrp1 = itemGrp1;
	}
	public int getItemGrp2() {
		return itemGrp2;
	}
	public void setItemGrp2(int itemGrp2) {
		this.itemGrp2 = itemGrp2;
	}
	public int getItemGrp3() {
		return itemGrp3;
	}
	public void setItemGrp3(int itemGrp3) {
		this.itemGrp3 = itemGrp3;
	}
	public float getItemRate1() {
		return itemRate1;
	}
	public void setItemRate1(float itemRate1) {
		this.itemRate1 = itemRate1;
	}
	public float getItemRate2() {
		return itemRate2;
	}
	public void setItemRate2(float itemRate2) {
		this.itemRate2 = itemRate2;
	}
	public float getItemMrp1() {
		return itemMrp1;
	}
	public void setItemMrp1(float itemMrp1) {
		this.itemMrp1 = itemMrp1;
	}
	public float getItemMrp2() {
		return itemMrp2;
	}
	public void setItemMrp2(float itemMrp2) {
		this.itemMrp2 = itemMrp2;
	}
	public float getItemMrp3() {
		return itemMrp3;
	}
	public void setItemMrp3(float itemMrp3) {
		this.itemMrp3 = itemMrp3;
	}
	public String getItemImage() {
		return itemImage;
	}
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}
	public float getItemTax1() {
		return itemTax1;
	}
	public void setItemTax1(float itemTax1) {
		this.itemTax1 = itemTax1;
	}
	public float getItemTax2() {
		return itemTax2;
	}
	public void setItemTax2(float itemTax2) {
		this.itemTax2 = itemTax2;
	}
	public float getItemTax3() {
		return itemTax3;
	}
	public void setItemTax3(float itemTax3) {
		this.itemTax3 = itemTax3;
	}
	public int getItemIsUsed() {
		return itemIsUsed;
	}
	public void setItemIsUsed(int itemIsUsed) {
		this.itemIsUsed = itemIsUsed;
	}
	public float getItemSortId() {
		return itemSortId;
	}
	public void setItemSortId(float itemSortId) {
		this.itemSortId = itemSortId;
	}
	public int getGrnTwo() {
		return grnTwo;
	}
	public void setGrnTwo(int grnTwo) {
		this.grnTwo = grnTwo;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public int getMinQty() {
		return minQty;
	}
	public void setMinQty(int minQty) {
		this.minQty = minQty;
	}
	public float getItemRate3() {
		return itemRate3;
	}
	public void setItemRate3(float itemRate3) {
		this.itemRate3 = itemRate3;
	}
	public int getShelfLife() {
		return shelfLife;
	}
	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}
	public String getItemHsncd() {
		return itemHsncd;
	}
	public void setItemHsncd(String itemHsncd) {
		this.itemHsncd = itemHsncd;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	@Override
	public String toString() {
		return "GetItemUomAndSup [id=" + id + ", itemId=" + itemId + ", itemName=" + itemName + ", itemGrp1=" + itemGrp1
				+ ", itemGrp2=" + itemGrp2 + ", itemGrp3=" + itemGrp3 + ", itemRate1=" + itemRate1 + ", itemRate2="
				+ itemRate2 + ", itemMrp1=" + itemMrp1 + ", itemMrp2=" + itemMrp2 + ", itemMrp3=" + itemMrp3
				+ ", itemImage=" + itemImage + ", itemTax1=" + itemTax1 + ", itemTax2=" + itemTax2 + ", itemTax3="
				+ itemTax3 + ", itemIsUsed=" + itemIsUsed + ", itemSortId=" + itemSortId + ", grnTwo=" + grnTwo
				+ ", delStatus=" + delStatus + ", minQty=" + minQty + ", itemRate3=" + itemRate3 + ", shelfLife="
				+ shelfLife + ", itemHsncd=" + itemHsncd + ", uom=" + uom + "]";
	}

}
