package com.ats.adminpanel.model;

public class GetFrMenuExlPdf {
	
	private int menuId;
	private String menuTitle;
	private String menuDesc;
	private String menuImage;
	private String selectedMenuImage;
	private int isSameDayApplicable;
	private int mainCatId;
	private int delStatus;
	
	private String catName;
	private String frName;

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuImage() {
		return menuImage;
	}

	public void setMenuImage(String menuImage) {
		this.menuImage = menuImage;
	}

	public String getSelectedMenuImage() {
		return selectedMenuImage;
	}

	public void setSelectedMenuImage(String selectedMenuImage) {
		this.selectedMenuImage = selectedMenuImage;
	}

	public int getIsSameDayApplicable() {
		return isSameDayApplicable;
	}

	public void setIsSameDayApplicable(int isSameDayApplicable) {
		this.isSameDayApplicable = isSameDayApplicable;
	}

	public int getMainCatId() {
		return mainCatId;
	}

	public void setMainCatId(int mainCatId) {
		this.mainCatId = mainCatId;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getFrName() {
		return frName;
	}

	public void setFrName(String frName) {
		this.frName = frName;
	}
}
