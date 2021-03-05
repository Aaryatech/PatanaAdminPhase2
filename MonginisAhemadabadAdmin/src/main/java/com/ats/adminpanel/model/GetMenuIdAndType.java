package com.ats.adminpanel.model;

public class GetMenuIdAndType {
	
	private int menuId;
	private int type;
	
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "GetMenuIdAndType [menuId=" + menuId + ", type=" + type + "]";
	}
	
	
}
