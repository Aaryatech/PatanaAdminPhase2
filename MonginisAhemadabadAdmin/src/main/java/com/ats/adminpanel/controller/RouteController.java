package com.ats.adminpanel.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.adminpanel.commons.AccessControll;
import com.ats.adminpanel.commons.Constants;
import com.ats.adminpanel.model.AllRoutesListResponse;
import com.ats.adminpanel.model.ExportToExcel;
import com.ats.adminpanel.model.SpecialCake;
import com.ats.adminpanel.model.accessright.ModuleJson;
import com.ats.adminpanel.model.Info;
import com.ats.adminpanel.model.Route;
import com.ats.adminpanel.model.RouteAbcVal;
import com.ats.adminpanel.model.RouteMaster;
import com.ats.adminpanel.model.SpCakeResponse;

@Controller
public class RouteController {

	@RequestMapping(value = "/addRouteProcess", method = RequestMethod.POST)
	public String addRouteProcess(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/route");
		RestTemplate rest = new RestTemplate();

		RouteMaster save = new RouteMaster();
		
		save.setRouteName(request.getParameter("route_name"));
		save.setAbcType(Integer.parseInt(request.getParameter("acbType")));
		save.setSeqNo(Integer.parseInt(request.getParameter("seqNo")));
		save.setDelStatus(0);
		save.setShortName(request.getParameter("short_name"));
		save.setRoutePrefix(request.getParameter("prefix"));
		save.setMaxKm(Float.parseFloat(request.getParameter("min_km")));
		save.setMinKm(Float.parseFloat(request.getParameter("max_km")));
		save.setRouteType(0);
		save.setExInt1(0);
		save.setExInt2(0);
		save.setExVar1("NA");
		save.setExVar2("NA");
		
		RouteMaster routeResponse = rest.postForObject(Constants.url + "/saveRoute", save, RouteMaster.class);
		model = new ModelAndView("masters/route");
		return "redirect:/addroute";
	}

	@RequestMapping(value = "/addroute", method = RequestMethod.GET)
	public ModelAndView addroute(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		HttpSession session = request.getSession();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showAddNewFranchisee", "showAddNewFranchisee", "1", "0", "0", "0",
				newModuleList);

		if (view.getError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("masters/route");
			System.out.println("route disp");

			Constants.mainAct = 1;
			Constants.subAct = 8;
			RestTemplate restTemplate = new RestTemplate();
			try {

				RouteMaster[] allRouteListResponse = restTemplate.getForObject(Constants.url + "/showRouteListNew",
						RouteMaster[].class);

				List<RouteMaster> routeList = new ArrayList<RouteMaster>(Arrays.asList(allRouteListResponse));

				
				RouteAbcVal[] valArr = restTemplate.getForObject(Constants.url + "/showRouteAbcValList",
						RouteAbcVal[].class);

				List<RouteAbcVal> valList = new ArrayList<RouteAbcVal>(Arrays.asList(valArr));
				
				
				List<Integer> routeIds = restTemplate.getForObject(Constants.url + "/getFrRouteItList",
						List.class);		
 
				model.addObject("routeList", routeList);
				model.addObject("valList", valList);
				model.addObject("routeIds", routeIds);
				
				
				
				
			} catch (Exception e) {
				System.out.println("Error in route list display" + e.getMessage());
				e.printStackTrace();
			}
		}
		return model;
	}

	@RequestMapping(value = "/deleteRoute/{routeId}", method = RequestMethod.GET)
	public String deleteRoute(@PathVariable String[] routeId) {
		ModelAndView model = new ModelAndView("masters/route");
		RestTemplate rest = new RestTemplate();
		String strRouteIds = new String();
		for (int i = 0; i < routeId.length; i++) {
			strRouteIds = strRouteIds + "," + routeId[i];
		}
		strRouteIds = strRouteIds.substring(1);

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("routeId", strRouteIds);
		Info info = rest.postForObject(Constants.url + "deleteRoute", map, Info.class);
		if (info.getError()) {
			return "redirect:/addroute";
		} else {
			return "redirect:/addroute";

		}
	}

	@RequestMapping(value = "/updateRoute/{routeId}")
	public ModelAndView updateRoute(@PathVariable int routeId, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView("masters/editRoute");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("routeId", routeId);
		RestTemplate restTemplate = new RestTemplate();
		try {
			RouteMaster route = restTemplate.getForObject("" + Constants.url + "getRouteNew?routeId={routeId}",
					RouteMaster.class, routeId);

			RouteMaster[] allRouteListResponse = restTemplate.getForObject(Constants.url + "/showRouteListNew",
					RouteMaster[].class);

			List<RouteMaster> routeList = new ArrayList<RouteMaster>(Arrays.asList(allRouteListResponse));
			
			
			RouteAbcVal[] valArr = restTemplate.getForObject(Constants.url + "/showRouteAbcValList",
					RouteAbcVal[].class);

			List<RouteAbcVal> valList = new ArrayList<RouteAbcVal>(Arrays.asList(valArr));
			
			List<Integer> routeIds = restTemplate.getForObject(Constants.url + "/getFrRouteItList",
					List.class);		


			model.addObject("valList", valList);
			model.addObject("routeList", routeList);
			model.addObject("route", route);
			model.addObject("routeIds", routeIds);
			
		} catch (Exception e) {
			System.out.println("Exception In updateRoute:" + e.getMessage());
		}

		return model;

	}

	List<RouteMaster> printRouteList = new ArrayList<RouteMaster>();
	List<Long> routeIds = new ArrayList<Long>();
	@RequestMapping(value = "/getRoutePrintIds", method = RequestMethod.GET)
	public @ResponseBody List<RouteMaster> getCompanyPrintIds(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		try {
			HttpSession session = request.getSession();		
					
			String selctId = request.getParameter("elemntIds");

			selctId = selctId.substring(1, selctId.length() - 1);
			selctId = selctId.replaceAll("\"", "");
			
			RestTemplate restTemplate = new RestTemplate();

			RouteMaster[] allRouteListResponse = restTemplate.getForObject(Constants.url + "/showRouteListNew",
					RouteMaster[].class);

			printRouteList = new ArrayList<RouteMaster>(Arrays.asList(allRouteListResponse));

			routeIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			
			
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr No.");
			for (int i = 0; i < routeIds.size(); i++) {
								
				if(routeIds.get(i)==1)
				rowData.add("Route");
				
				if(routeIds.get(i)==2)
				rowData.add("Prefix");
				
				if(routeIds.get(i)==3)
				rowData.add("Short Name");
				
				if(routeIds.get(i)==4)
				rowData.add("Min Km.");
				
				if(routeIds.get(i)==5)
				rowData.add("Max Km.");
				
				if(routeIds.get(i)==6)
				rowData.add("ABC Type");
				
				if(routeIds.get(i)==7)
				rowData.add("Sequence No");
								
				
			}
			expoExcel.setRowData(rowData);
			
			exportToExcelList.add(expoExcel);
			int srno = 1;
			for (int i = 0; i < printRouteList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				
				rowData.add(" "+srno);
				for (int j = 0; j < routeIds.size(); j++) {		
					
					if(routeIds.get(j)==1)
					rowData.add(" " + printRouteList.get(i).getRouteName());
					
					if(routeIds.get(j)==2)
					rowData.add(" " + printRouteList.get(i).getRoutePrefix());
					
					if(routeIds.get(j)==3)
					rowData.add(" " + printRouteList.get(i).getShortName());
					
					if(routeIds.get(j)==4)
					rowData.add(" " + printRouteList.get(i).getMinKm());
					
					if(routeIds.get(j)==5)
					rowData.add(" " + printRouteList.get(i).getMaxKm());
					
					if(routeIds.get(j)==6)
					rowData.add(" " + printRouteList.get(i).getAbcType());
						
					if(routeIds.get(j)==7)
					rowData.add(" " + printRouteList.get(i).getSeqNo());				
					
				}
				srno = srno + 1;
				
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "Route List");
			session.setAttribute("reportNameNew", "Route List");
			session.setAttribute("searchByNew", "All Company");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");
			session.setAttribute("excelName", "Route Excel");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return printRouteList;
	}
	
	
	@RequestMapping(value = "pdf/getRouteListPdf/{selctId}", method = RequestMethod.GET)
	public ModelAndView getCompanyListPdf(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String selctId) {
		ModelAndView model = new ModelAndView("masters/masterPdf/routePdf");
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			RestTemplate restTemplate = new RestTemplate();
			RouteMaster[] allRouteListResponse = restTemplate.getForObject(Constants.url + "/showRouteListNew",
					RouteMaster[].class);

			printRouteList = new ArrayList<RouteMaster>(Arrays.asList(allRouteListResponse));

			routeIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			
			
				model.addObject("printRouteList", printRouteList);
				model.addObject("routeIds", routeIds);
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model;
		
	}
	
	@RequestMapping(value = "/updateRoute/editRouteProcess", method = RequestMethod.POST)
	public String editRouteProcess(HttpServletRequest request, HttpServletResponse response) {		

		RestTemplate restTemplate = new RestTemplate();

		RouteMaster save = new RouteMaster();
		save.setRouteId(Integer.parseInt(request.getParameter("route_id")));
		save.setRouteName(request.getParameter("route_name"));
		save.setAbcType(Integer.parseInt(request.getParameter("acbType")));
		save.setSeqNo(Integer.parseInt(request.getParameter("seqNo")));
		save.setDelStatus(0);
		save.setShortName(request.getParameter("short_name"));
		save.setRoutePrefix(request.getParameter("prefix"));
		save.setMaxKm(Float.parseFloat(request.getParameter("min_km")));
		save.setMinKm(Float.parseFloat(request.getParameter("max_km")));
		save.setRouteType(0);
		save.setExInt1(0);
		save.setExInt2(0);
		save.setExVar1("NA");
		save.setExVar2("NA");

		RouteMaster routeResponse = restTemplate.postForObject("" + Constants.url + "saveRoute", save,
				RouteMaster.class);

		return "redirect:/addroute";

	}
	
	@RequestMapping(value = "/chkUnqRoutePrefix", method = RequestMethod.GET)
	public @ResponseBody Info chkUnqRoutePrefix(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("In chkUnqRoutePrefix");
		RestTemplate rest = new RestTemplate();
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		RouteMaster res= new RouteMaster();
		Info info = new Info();
		try {
			
			map.add("routeId", Integer.parseInt(request.getParameter("routeId")));
			map.add("routePrefix", request.getParameter("routePrefix"));
			System.out.println("MAP============"+map);
			res = rest.postForObject(Constants.url + "validateRoutePrefix", map, RouteMaster.class);			
			if (res!=null) {
				info.setError(true);
				info.setMessage("Prefix Found");
			} else {			
				info.setError(false);
				info.setMessage("Prefix Not Found");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
	
	@RequestMapping(value = "/chkUnqRouteShortName", method = RequestMethod.GET)
	public @ResponseBody Info chkUnqRouteShortName(HttpServletRequest request, HttpServletResponse response) {
		
		RestTemplate rest = new RestTemplate();
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		RouteMaster res= new RouteMaster();
		Info info = new Info();
		try {
			map.add("routeId", Integer.parseInt(request.getParameter("routeId")));
			map.add("shortName", request.getParameter("shortName"));
			
			res = rest.postForObject(Constants.url + "validateRouteShortName", map, RouteMaster.class);			
			if (res!=null) {
				info.setError(true);
				info.setMessage("Short Name Found");
			} else {			
				info.setError(false);
				info.setMessage("Short Name Not Found");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

}
