package com.ats.adminpanel.model.stock;

import java.util.List;

import com.ats.adminpanel.model.AllFrIdName;

public class CurrentStockResponse {

	private boolean isMonthClosed;
	private  List<GetCurrentStockDetails> currentStockDetailList;
	private int frId;
	
	public boolean isMonthClosed() {
		return isMonthClosed;
	}
	public void setMonthClosed(boolean isMonthClosed) {
		this.isMonthClosed = isMonthClosed;
	}
	public List<GetCurrentStockDetails> getCurrentStockDetailList() {
		return currentStockDetailList;
	}
	public void setCurrentStockDetailList(List<GetCurrentStockDetails> currentStockDetailList) {
		this.currentStockDetailList = currentStockDetailList;
	}
	public int getFrId() {
		return frId;
	}
	public void setFrId(int frId) {
		this.frId = frId;
	}
	@Override
	public String toString() {
		return "CurrentStockResponse [isMonthClosed=" + isMonthClosed + ", currentStockDetailList="
				+ currentStockDetailList + ", frId=" + frId + "]";
	}
	
}
