package com.ats.adminpanel.model;

import java.util.List;

//Akhilesh	2021-01-21
public class ConfigureFrOldBeanListResp {
	
	List<ConfigureFrOldBean> configureFrBean= null;
	
	private Object info;

	

	public List<ConfigureFrOldBean> getConfigureFrBean() {
		return configureFrBean;
	}

	public void setConfigureFrBean(List<ConfigureFrOldBean> configureFrBean) {
		this.configureFrBean = configureFrBean;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "ConfigureFrOldBeanListResp [configureFrBean=" + configureFrBean + ", info=" + info + "]";
	}

	
	
	
	

}
