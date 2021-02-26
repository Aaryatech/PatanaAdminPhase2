
package com.ats.adminpanel.model;

import java.util.List;

public class AllRoutesListResponse {

	private List<RouteMaster> routeList;
	
    private List<Route> route = null;
    
    private Info info;

    public List<Route> getRoute() {
        return route;
    }

    public void setRoute(List<Route> route) {
        this.route = route;
    }

    public List<RouteMaster> getRouteList() {
		return routeList;
	}

	public void setRouteList(List<RouteMaster> routeList) {
		this.routeList = routeList;
	}

	public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

	@Override
	public String toString() {
		return "AllRoutesListResponse [routeList=" + routeList + ", route=" + route + ", info=" + info + "]";
	}

}
