package com.ats.adminpanel.model.stock;

import java.util.List;

import com.ats.adminpanel.model.AllFrIdName;

public class FrachisesCurrStocksList {
	List<CurrentStockResponse> frStockList;
	List<AllFrIdName> frList;
	List<Integer> routeFrId;
	List<Integer> itemIdList;
	
	public List<CurrentStockResponse> getFrStockList() {
		return frStockList;
	}
	public void setFrStockList(List<CurrentStockResponse> frStockList) {
		this.frStockList = frStockList;
	}
	public List<AllFrIdName> getFrList() {
		return frList;
	}
	public void setFrList(List<AllFrIdName> frList) {
		this.frList = frList;
	}
	public List<Integer> getItemIdList() {
		return itemIdList;
	}
	public void setItemIdList(List<Integer> itemIdList) {
		this.itemIdList = itemIdList;
	}
	public List<Integer> getRouteFrId() {
		return routeFrId;
	}
	public void setRouteFrId(List<Integer> routeFrId) {
		this.routeFrId = routeFrId;
	}
	@Override
	public String toString() {
		return "FrachisesCurrStocksList [frStockList=" + frStockList + ", frList=" + frList + ", routeFrId=" + routeFrId
				+ ", itemIdList=" + itemIdList + "]";
	}
	
}
