package com.ats.adminpanel.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.adminpanel.commons.AccessControll;
import com.ats.adminpanel.commons.Constants;
import com.ats.adminpanel.model.AllFrIdName;
import com.ats.adminpanel.model.AllFrIdNameList;
import com.ats.adminpanel.model.AllRoutesListResponse;
import com.ats.adminpanel.model.CategoryList;
import com.ats.adminpanel.model.ExportToExcel;
import com.ats.adminpanel.model.FrMenu;
import com.ats.adminpanel.model.Franchisee;
import com.ats.adminpanel.model.GetFrMenus;
import com.ats.adminpanel.model.Info;
import com.ats.adminpanel.model.MCategory;
import com.ats.adminpanel.model.Route;
import com.ats.adminpanel.model.Setting;
import com.ats.adminpanel.model.accessright.ModuleJson;
import com.ats.adminpanel.model.franchisee.FrNameIdByRouteId;
import com.ats.adminpanel.model.franchisee.FrNameIdByRouteIdResponse;
import com.ats.adminpanel.model.ggreports.GGReportByDateAndFr;
import com.ats.adminpanel.model.item.CategoryListResponse;
import com.ats.adminpanel.model.item.Item;
import com.ats.adminpanel.model.item.MCategoryList;
import com.ats.adminpanel.model.item.SubCategory;
import com.ats.adminpanel.model.stock.CurrentStockResponse;
import com.ats.adminpanel.model.stock.FrachisesCurrStocksList;
import com.ats.adminpanel.model.stock.GetCurrentStockDetails;
import com.ats.adminpanel.model.stock.PostFrItemStockCommon;
import com.ats.adminpanel.model.stock.PostFrItemStockDetail;
import com.ats.adminpanel.model.stock.PostFrItemStockHeader;

@Controller
@Scope("session")
public class StockController {

	private static final Logger logger = LoggerFactory.getLogger(StockController.class);
	List<FrMenu> filterFrMenus = new ArrayList<FrMenu>();
	List<Item> itemList;
	String frId;
	String menuId = "0";
	List<PostFrItemStockDetail> detailList = new ArrayList<PostFrItemStockDetail>();

	@RequestMapping(value = "/showFrOpeningStock")
	public ModelAndView showFrOpeningStock(HttpServletRequest request, HttpServletResponse response) {

		logger.info("/showFrOpeningStock request mapping.");

		ModelAndView model = null;
		HttpSession session = request.getSession();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showFrOpeningStock", "showFrOpeningStock", "1", "0", "0", "0",
				newModuleList);

		if (view.getError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("stock/fropeningstock");
			Constants.mainAct = 2;
			Constants.subAct = 18;

			RestTemplate restTemplate = new RestTemplate();

			AllFrIdNameList allFrIdNameList = new AllFrIdNameList();
			try {

				allFrIdNameList = restTemplate.getForObject(Constants.url + "getAllFrIdName", AllFrIdNameList.class);

			} catch (Exception e) {
				System.out.println("Exception in getAllFrIdName" + e.getMessage());
				e.printStackTrace();

			}
			// ---------------------------------4-jan-2019------------------------------------
			CategoryListResponse itemsWithCategoryResponseList = restTemplate
					.getForObject(Constants.url + "showAllCategory", CategoryListResponse.class);

			List<MCategoryList> itemsWithCategoriesList = itemsWithCategoryResponseList.getmCategoryList();

			for (int i = 0; i < itemsWithCategoriesList.size(); i++) {

				// System.out.println("cat id== " + itemsWithCategoriesList.get(i).getCatId());
				if (itemsWithCategoriesList.get(i).getCatId() == 5) {

					itemsWithCategoriesList.remove(i);

				}

			}

			for (int i = 0; i < itemsWithCategoriesList.size(); i++) {

				if (itemsWithCategoriesList.get(i).getCatId() == 6) {

					itemsWithCategoriesList.remove(i);

				}

			}

			model.addObject("catList", itemsWithCategoriesList);
			// ---------------------------------4-jan-2019------------------------------------
			model.addObject("frList", allFrIdNameList.getFrIdNamesList());
		}
		return model;
	}

	// AJAX Call for menu
	/*
	 * @RequestMapping(value = "/getMenuListByFr", method = RequestMethod.GET)
	 * public @ResponseBody List<FrMenu> getMenuListByFr(HttpServletRequest request,
	 * HttpServletResponse response) {
	 * 
	 * logger.info("/getMenuListByFr AJAX Call mapping."); try { frId =
	 * request.getParameter("fr_id");
	 * 
	 * RestTemplate restTemplate = new RestTemplate();
	 * 
	 * MultiValueMap<String, Object> menuMap = new LinkedMultiValueMap<String,
	 * Object>(); menuMap.add("frId", frId);
	 * 
	 * GetFrMenus getFrMenus = restTemplate.postForObject(Constants.url +
	 * "getFrConfigMenus", menuMap, GetFrMenus.class);
	 * 
	 * filterFrMenus = new ArrayList<FrMenu>();
	 * 
	 * for (int i = 0; i < getFrMenus.getFrMenus().size(); i++) {
	 * 
	 * FrMenu frMenu = getFrMenus.getFrMenus().get(i);
	 * 
	 * if (frMenu.getMenuId() == 26 || frMenu.getMenuId() == 31 ||
	 * frMenu.getMenuId() == 33 || frMenu.getMenuId() == 34 || frMenu.getMenuId() ==
	 * 49) {
	 * 
	 * filterFrMenus.add(frMenu);
	 * 
	 * }
	 * 
	 * } System.err.println("Menus " +filterFrMenus); }catch (Exception e) {
	 * System.err.println("Exc in "); System.err.println("dvld");
	 * e.printStackTrace(); } return filterFrMenus; }
	 */
	// AJAX Call for Items
	@RequestMapping(value = "/getItemListById", method = RequestMethod.GET)
	public @ResponseBody List<PostFrItemStockDetail> getItems(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			logger.info("/getItemListById AJAX Call mapping.");

			/*
			 * menuId = request.getParameter("menu_id");//catId from jsp
			 */
			int catId = Integer.parseInt(request.getParameter("menu_id")); // catId from jsp
			System.out.println("req param menuId " + catId);
			int frId = Integer.parseInt(request.getParameter("frId")); // catId from jsp'
			System.out.println("req param frId " + frId);
			RestTemplate restTemplate = new RestTemplate();

			/*
			 * String itemShow = null; int catId = 0;
			 * 
			 * for (int i = 0; i < filterFrMenus.size(); i++) {
			 * 
			 * if (filterFrMenus.get(i).getMenuId() == Integer.parseInt(menuId)) {
			 * 
			 * catId = filterFrMenus.get(i).getCatId(); itemShow =
			 * filterFrMenus.get(i).getItemShow();
			 * 
			 * System.out.println("Item Show List is: " + itemShow);
			 * 
			 * break; }
			 * 
			 * }
			 */

			MultiValueMap<String, Object> menuMap = new LinkedMultiValueMap<String, Object>();
			/* menuMap.add("itemIdList", itemShow); */
			menuMap.add("frId", frId);
			menuMap.add("catId", catId);

			ParameterizedTypeReference<List<PostFrItemStockDetail>> typeRef = new ParameterizedTypeReference<List<PostFrItemStockDetail>>() {
			};
			ResponseEntity<List<PostFrItemStockDetail>> responseEntity = restTemplate
					.exchange(Constants.url + "getCurrentOpStock", HttpMethod.POST, new HttpEntity<>(menuMap), typeRef);
			detailList = responseEntity.getBody();

			System.out.println("Item List " + detailList.toString());
		} catch (HttpClientErrorException e) {
			System.err.println("http Cli " + e.getResponseBodyAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detailList;
	}

	// Save item opening stock

	@RequestMapping(value = "/saveFrOpeningStockProcess", method = RequestMethod.POST)
	public String saveOpeningStock(HttpServletRequest request, HttpServletResponse response) {

		logger.info("/showFrOpeningStock request mapping.");

		ModelAndView model = new ModelAndView("stock/fropeningstock");

		for (int i = 0; i < detailList.size(); i++) {

			String stockQty = request.getParameter("stockQty" + detailList.get(i).getItemId());
			System.out.println("new qty " + stockQty);

			detailList.get(i).setRegOpeningStock(Integer.parseInt(stockQty));

		}

		RestTemplate restTemplate = new RestTemplate();

		List<PostFrItemStockDetail> info = restTemplate.postForObject(Constants.url + "postFrOpStockDetailList",
				detailList, List.class);

		return "redirect:/showFrOpeningStock";
	}

	AllFrIdNameList allFrIdNameList = new AllFrIdNameList();

	AllFrIdNameList getFrNameId() {
		RestTemplate restTemplate = new RestTemplate();
		return allFrIdNameList = restTemplate.getForObject(Constants.url + "getAllFrIdName", AllFrIdNameList.class);
	}

	List<MCategoryList> mAllCategoryList = new ArrayList<MCategoryList>();
	CategoryList categoryList;

	@RequestMapping(value = "/showCurrentStockDetail")
	public ModelAndView showStockDetail(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("stock/showCrrentStock");
		try {
			RestTemplate restTemplate = new RestTemplate();

			CategoryListResponse categoryListResponse = restTemplate.getForObject(Constants.url + "showAllCategory",
					CategoryListResponse.class);
			mAllCategoryList = categoryListResponse.getmCategoryList();

			allFrIdNameList = restTemplate.getForObject(Constants.url + "getAllFrIdName", AllFrIdNameList.class);

			AllRoutesListResponse allRouteListResponse = restTemplate.getForObject(Constants.url + "showRouteList",
					AllRoutesListResponse.class);

			model.addObject("routeList", allRouteListResponse.getRoute());
			model.addObject("unSelectedFrList", allFrIdNameList.getFrIdNamesList());
			model.addObject("catList", mAllCategoryList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/getAllCurrentStockFr", method = RequestMethod.GET)
	public @ResponseBody List<AllFrIdName> getAllFrListForSalesReportAjax(HttpServletRequest request,
			HttpServletResponse response) {

		return allFrIdNameList.getFrIdNamesList();

	}

	Integer runningMonth = 0;
	String catId = null;
	List<GetCurrentStockDetails> currentStockDetailList = new ArrayList<GetCurrentStockDetails>();

	@RequestMapping(value = "/getStockDetails", method = RequestMethod.GET)
	public @ResponseBody FrachisesCurrStocksList getMenuListByFr(HttpServletRequest request,
			HttpServletResponse response) {
		

		boolean isMonthCloseApplicable = false;
		HttpSession session = request.getSession();
		int selectRate = 0;

		FrachisesCurrStocksList frStockList = new FrachisesCurrStocksList();
		List<CurrentStockResponse> stock = new ArrayList<CurrentStockResponse>();
		CurrentStockResponse currentStockResponse = new CurrentStockResponse();
		
		List<Integer> frIdsList = new ArrayList<Integer>();
		List<Integer> itemIdList = new ArrayList<Integer>();
		String frIdString = null;
		try {
			String itemIds = request.getParameter("itemsIds");
			int catId = Integer.parseInt(request.getParameter("catId"));
			String showOption = "1";// request.getParameter("show_option");
			selectRate = 1;// Integer.parseInt(request.getParameter("selectRate"));

			frIdString = request.getParameter("fr_id_list");
			String routeId = request.getParameter("route_id");

			frIdString = frIdString.substring(1, frIdString.length() - 1);
			frIdString = frIdString.replaceAll("\"", "");

			itemIds = itemIds.substring(1, itemIds.length() - 1);
			itemIds = itemIds.replaceAll("\"", "");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			
			if (!routeId.equalsIgnoreCase("0")) {

				map = new LinkedMultiValueMap<String, Object>();

				RestTemplate restTemplate = new RestTemplate();

				map.add("routeId", routeId);

				FrNameIdByRouteIdResponse frNameId = restTemplate.postForObject(Constants.url + "getFrNameIdByRouteId",
						map, FrNameIdByRouteIdResponse.class);

				List<FrNameIdByRouteId> frNameIdByRouteIdList = frNameId.getFrNameIdByRouteIds();

				System.out.println("route wise franchisee " + frNameIdByRouteIdList.toString());

				StringBuilder sbForRouteFrId = new StringBuilder();
				for (int i = 0; i < frNameIdByRouteIdList.size(); i++) {

					sbForRouteFrId = sbForRouteFrId.append(frNameIdByRouteIdList.get(i).getFrId().toString() + ",");

				}

				String strFrIdRouteWise = sbForRouteFrId.toString();
				frIdString = strFrIdRouteWise.substring(0, strFrIdRouteWise.length() - 1);
				System.out.println("fr Id Route WISE = " + frIdString);

			} // end of if
			
			
			//System.out.println("params---------" + itemIds + " / " + catId + " / " + frIdString);

			itemIdList = Stream.of(itemIds.split(",")).map(Integer::parseInt)
					.collect(Collectors.toList());

			frIdsList = Stream.of(frIdString.split(",")).map(Integer::parseInt)
					.collect(Collectors.toList());

			for (int i = 0; i < frIdsList.size(); i++) {

				//System.out.println("Fr id In Loop----------" + frIdsList.get(i));

				currentStockResponse = new CurrentStockResponse();

				int menuId = 0;
				map = new LinkedMultiValueMap<String, Object>();

				map.add("frId", frIdsList.get(i));
				RestTemplate restTemplate = new RestTemplate();

				ParameterizedTypeReference<List<PostFrItemStockHeader>> typeRef1 = new ParameterizedTypeReference<List<PostFrItemStockHeader>>() {
				};
				ResponseEntity<List<PostFrItemStockHeader>> responseEntity1 = restTemplate.exchange(
						Constants.url + "getCurrentMonthOfCatId", HttpMethod.POST, new HttpEntity<>(map), typeRef1);
				List<PostFrItemStockHeader> list = responseEntity1.getBody();
				// System.out.println("## catId" + intCatId);

				map = new LinkedMultiValueMap<String, Object>();
				map.add("frId", frIdsList.get(i));
				map.add("catId", catId);

				for (int j = 0; j < list.size(); j++) {
					PostFrItemStockHeader postheader = list.get(j);
					if (postheader.getCatId() == catId) {

						runningMonth = postheader.getMonth();
					}
				}

				// System.err.println("ITEM LIST - " + itemShow);

				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				DateFormat yearFormat = new SimpleDateFormat("yyyy");

				Date todaysDate = new Date();
				// System.out.println(dateFormat.format(todaysDate));

				Calendar cal = Calendar.getInstance();
				cal.setTime(todaysDate);

				cal.set(Calendar.DAY_OF_MONTH, 1);

				Date firstDay = cal.getTime();

				// System.out.println("First Day of month " + firstDay);

				String strFirstDay = dateFormat.format(firstDay);

				// System.out.println("Year " + yearFormat.format(todaysDate));

				if (showOption.equals("1")) {
					map = new LinkedMultiValueMap<String, Object>();

					DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
					Date date = new Date();
					// System.out.println(dateFormat1.format(date));

					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(date);

					int dayOfMonth = cal1.get(Calendar.DATE);

					int calCurrentMonth = cal1.get(Calendar.MONTH) + 1;

					if (runningMonth < calCurrentMonth) {

						isMonthCloseApplicable = true;
						// System.out.println("Day Of Month End ......");

					} else if (runningMonth == 12 && calCurrentMonth == 1) {
						isMonthCloseApplicable = true;
					}

					if (isMonthCloseApplicable) {
						// System.err.println("### Inside iMonthclose app");
						String strDate;
						int year;
						if (runningMonth == 12) {

							year = (Calendar.getInstance().getWeekYear() - 1);

						} else {
							year = Calendar.getInstance().getWeekYear();
						}

						strDate = year + "/" + runningMonth + "/01";

						map.add("fromDate", strDate);
					} else {

						map.add("fromDate", dateFormat.format(firstDay));

					}

					map.add("frId", frIdsList.get(i));
					map.add("frStockType", 6);
					// map.add("fromDate", dateFormat1.format(firstDay));
					map.add("toDate", dateFormat.format(todaysDate));
					map.add("currentMonth", String.valueOf(runningMonth));
					map.add("year", yearFormat.format(todaysDate));
					map.add("catId", catId);
					map.add("itemIdList", itemIds);

					System.out.println(map);
					ParameterizedTypeReference<List<GetCurrentStockDetails>> typeRef2 = new ParameterizedTypeReference<List<GetCurrentStockDetails>>() {
					};
					ResponseEntity<List<GetCurrentStockDetails>> responseEntity2 = restTemplate.exchange(
							Constants.url + "getAdminCurrentStock", HttpMethod.POST, new HttpEntity<>(map), typeRef2);

					currentStockDetailList = responseEntity2.getBody();
				//	System.out.println("Current Stock Details : " + currentStockDetailList.toString());

				} else {

					// System.out.println("inside get stock between dates");

					String fromDate = request.getParameter("fromDate");

					String toDate = request.getParameter("toDate");

					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
					String fr = null;
					String to = null;
					try {
						fr = sdf1.format(sdf2.parse(fromDate));

						to = sdf1.format(sdf2.parse(toDate));
					} catch (ParseException e) {

						e.printStackTrace();
					}
					// System.out.println("FromDate " + fr);

					// System.out.println("toDate " + to);
					map = new LinkedMultiValueMap<String, Object>();
					map.add("frId", frIdsList.get(i));
					map.add("fromDate", fr);
					map.add("toDate", to);
					map.add("itemIdList", itemIds);
					map.add("catId", catId);
					map.add("frStockType", 6);

					try {
						ParameterizedTypeReference<List<GetCurrentStockDetails>> typeRef = new ParameterizedTypeReference<List<GetCurrentStockDetails>>() {
						};
						ResponseEntity<List<GetCurrentStockDetails>> responseEntity = restTemplate.exchange(
								Constants.url + "/getStockBetweenDates", HttpMethod.POST, new HttpEntity<>(map),
								typeRef);

						currentStockDetailList = responseEntity.getBody();
					//	System.out.println("Current Stock Details Monthwise : " + currentStockDetailList.toString());

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				currentStockResponse.setMonthClosed(isMonthCloseApplicable);
				currentStockResponse.setCurrentStockDetailList(currentStockDetailList);
				currentStockResponse.setFrId(frIdsList.get(i));
				stock.add(currentStockResponse);
			}
			List<AllFrIdName> frList = allFrIdNameList.getFrIdNamesList();
			frStockList.setFrList(frList);
			frStockList.setRouteFrId(frIdsList);
			frStockList.setItemIdList(itemIdList);
			frStockList.setFrStockList(stock);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		try {
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();
		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr No");
		rowData.add("Item Id");
		rowData.add("Item Name");
		for (int j = 0; j < frIdsList.size(); j++) {
			for (int k = 0; k < frStockList.getFrList().size(); k++) {
				if(frStockList.getFrList().get(k).getFrId()==frIdsList.get(j)) {
					rowData.add(frStockList.getFrList().get(k).getFrName());
				}
			}
		}	

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		FrachisesCurrStocksList excelItems = frStockList;
		for (int i = 0; i < excelItems.getItemIdList().size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			rowData.add("" + (i + 1));
			int flag = 0;
			rowData.add(" "+excelItems.getItemIdList().get(i));
			
				for (int k = 0; k < frStockList.getFrStockList().size(); k++) {
					for (int j = 0; j <  frIdsList.size() ; j++) {				
					if(frIdsList.get(j)==frStockList.getFrStockList().get(k).getFrId()) {
					
						List<GetCurrentStockDetails> curStockResp = frStockList.getFrStockList().get(k).getCurrentStockDetailList();
						
						for (int l = 0; l < curStockResp.size(); l++) {
							int regCurrentStock = curStockResp.get(l).getCurrentRegStock();
							int reOrderQty =  curStockResp.get(l).getReOrderQty();
							
							if(curStockResp.get(l).getId()==excelItems.getItemIdList().get(i)) {
								if (flag == 0) {
									rowData.add(curStockResp.get(l).getItemName());	
									
									flag = 1;
								}
								
								if (regCurrentStock < 0) {
									rowData.add(" "+0);
								}else {
									rowData.add(" "+regCurrentStock);	
								}
								
							}
						}
					}
				}
				
			}
			
			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}
		session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "Stock Detail");
		}catch (Exception e) {
			System.out.println("Excel Exception : "+e.getMessage());
			e.printStackTrace();
		}
		return frStockList;
	}
	
	@RequestMapping(value = "pdf/showCurrentStockPdf/{selectedFr}/{routeId}/{catId}/{itemsIds}/{selectRate}", method = RequestMethod.GET)
	public ModelAndView showCurrentStockPdf(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String selectedFr, @PathVariable String routeId, @PathVariable int catId, @PathVariable String itemsIds,
			@PathVariable int selectRate) {
		System.out.println("In getStockDetails Pdf");
		
		ModelAndView model = new ModelAndView("stock/pdf/currentStock");
		
		boolean isMonthCloseApplicable = false;
		HttpSession session = request.getSession();
		
		//int selectRate = 0;

		FrachisesCurrStocksList frStockList = new FrachisesCurrStocksList();
		List<CurrentStockResponse> stock = new ArrayList<CurrentStockResponse>();
		CurrentStockResponse currentStockResponse = new CurrentStockResponse();
		
		List<Integer> frIdsList = new ArrayList<Integer>();
		List<Integer> itemIdList = new ArrayList<Integer>();
		String frIdString = null;
		try {
			String itemIds = itemsIds;			
			String showOption = "1";// request.getParameter("show_option");
			selectRate = 1;// Integer.parseInt(request.getParameter("selectRate"));

			frIdString = selectedFr;		

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			
			if (!routeId.equalsIgnoreCase("0")) {

				map = new LinkedMultiValueMap<String, Object>();

				RestTemplate restTemplate = new RestTemplate();

				map.add("routeId", routeId);

				FrNameIdByRouteIdResponse frNameId = restTemplate.postForObject(Constants.url + "getFrNameIdByRouteId",
						map, FrNameIdByRouteIdResponse.class);

				List<FrNameIdByRouteId> frNameIdByRouteIdList = frNameId.getFrNameIdByRouteIds();

				StringBuilder sbForRouteFrId = new StringBuilder();
				for (int i = 0; i < frNameIdByRouteIdList.size(); i++) {

					sbForRouteFrId = sbForRouteFrId.append(frNameIdByRouteIdList.get(i).getFrId().toString() + ",");

				}

				String strFrIdRouteWise = sbForRouteFrId.toString();
				frIdString = strFrIdRouteWise.substring(0, strFrIdRouteWise.length() - 1);

			} // end of if
			
			itemIdList = Stream.of(itemIds.split(",")).map(Integer::parseInt)
					.collect(Collectors.toList());

			frIdsList = Stream.of(frIdString.split(",")).map(Integer::parseInt)
					.collect(Collectors.toList());

			for (int i = 0; i < frIdsList.size(); i++) {

				currentStockResponse = new CurrentStockResponse();

				int menuId = 0;
				map = new LinkedMultiValueMap<String, Object>();

				map.add("frId", frIdsList.get(i));
				RestTemplate restTemplate = new RestTemplate();

				ParameterizedTypeReference<List<PostFrItemStockHeader>> typeRef1 = new ParameterizedTypeReference<List<PostFrItemStockHeader>>() {
				};
				ResponseEntity<List<PostFrItemStockHeader>> responseEntity1 = restTemplate.exchange(
						Constants.url + "getCurrentMonthOfCatId", HttpMethod.POST, new HttpEntity<>(map), typeRef1);
				List<PostFrItemStockHeader> list = responseEntity1.getBody();
				// System.out.println("## catId" + intCatId);

				map = new LinkedMultiValueMap<String, Object>();
				map.add("frId", frIdsList.get(i));
				map.add("catId", catId);

				for (int j = 0; j < list.size(); j++) {
					PostFrItemStockHeader postheader = list.get(j);
					if (postheader.getCatId() == catId) {

						runningMonth = postheader.getMonth();
					}
				}

				

				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				DateFormat yearFormat = new SimpleDateFormat("yyyy");

				Date todaysDate = new Date();
				

				Calendar cal = Calendar.getInstance();
				cal.setTime(todaysDate);

				cal.set(Calendar.DAY_OF_MONTH, 1);

				Date firstDay = cal.getTime();

				// System.out.println("First Day of month " + firstDay);

				String strFirstDay = dateFormat.format(firstDay);

				// System.out.println("Year " + yearFormat.format(todaysDate));

				if (showOption.equals("1")) {
					map = new LinkedMultiValueMap<String, Object>();

					DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
					Date date = new Date();
					// System.out.println(dateFormat1.format(date));

					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(date);

					int dayOfMonth = cal1.get(Calendar.DATE);

					int calCurrentMonth = cal1.get(Calendar.MONTH) + 1;

					if (runningMonth < calCurrentMonth) {

						isMonthCloseApplicable = true;
						// System.out.println("Day Of Month End ......");

					} else if (runningMonth == 12 && calCurrentMonth == 1) {
						isMonthCloseApplicable = true;
					}

					if (isMonthCloseApplicable) {
						// System.err.println("### Inside iMonthclose app");
						String strDate;
						int year;
						if (runningMonth == 12) {

							year = (Calendar.getInstance().getWeekYear() - 1);

						} else {
							year = Calendar.getInstance().getWeekYear();
						}

						strDate = year + "/" + runningMonth + "/01";

						map.add("fromDate", strDate);
					} else {

						map.add("fromDate", dateFormat.format(firstDay));

					}

					map.add("frId", frIdsList.get(i));
					map.add("frStockType", 6);
					// map.add("fromDate", dateFormat1.format(firstDay));
					map.add("toDate", dateFormat.format(todaysDate));
					map.add("currentMonth", String.valueOf(runningMonth));
					map.add("year", yearFormat.format(todaysDate));
					map.add("catId", catId);
					map.add("itemIdList", itemIds);

					System.out.println(map);
					ParameterizedTypeReference<List<GetCurrentStockDetails>> typeRef2 = new ParameterizedTypeReference<List<GetCurrentStockDetails>>() {
					};
					ResponseEntity<List<GetCurrentStockDetails>> responseEntity2 = restTemplate.exchange(
							Constants.url + "getAdminCurrentStock", HttpMethod.POST, new HttpEntity<>(map), typeRef2);

					currentStockDetailList = responseEntity2.getBody();
					System.out.println("Current Stock Details : " + currentStockDetailList.toString());

				} else {

					// System.out.println("inside get stock between dates");

					String fromDate = request.getParameter("fromDate");

					String toDate = request.getParameter("toDate");

					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
					String fr = null;
					String to = null;
					try {
						fr = sdf1.format(sdf2.parse(fromDate));

						to = sdf1.format(sdf2.parse(toDate));
					} catch (ParseException e) {

						e.printStackTrace();
					}
					// System.out.println("FromDate " + fr);

					// System.out.println("toDate " + to);
					map = new LinkedMultiValueMap<String, Object>();
					map.add("frId", frIdsList.get(i));
					map.add("fromDate", fr);
					map.add("toDate", to);
					map.add("itemIdList", itemIds);
					map.add("catId", catId);
					map.add("frStockType", 6);

					try {
						ParameterizedTypeReference<List<GetCurrentStockDetails>> typeRef = new ParameterizedTypeReference<List<GetCurrentStockDetails>>() {
						};
						ResponseEntity<List<GetCurrentStockDetails>> responseEntity = restTemplate.exchange(
								Constants.url + "/getStockBetweenDates", HttpMethod.POST, new HttpEntity<>(map),
								typeRef);

						currentStockDetailList = responseEntity.getBody();
						System.out.println("Current Stock Details Monthwise : " + currentStockDetailList.toString());

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				currentStockResponse.setMonthClosed(isMonthCloseApplicable);
				currentStockResponse.setCurrentStockDetailList(currentStockDetailList);
				currentStockResponse.setFrId(frIdsList.get(i));
				stock.add(currentStockResponse);
				
				model.addObject("currStockResp", currentStockResponse);
			}
			AllFrIdNameList fr = getFrNameId();
			List<AllFrIdName> frList = fr.getFrIdNamesList();
			
			frStockList.setFrList(frList);
			frStockList.setRouteFrId(frIdsList);
			frStockList.setItemIdList(itemIdList);
			frStockList.setFrStockList(stock);
			
			model.addObject("frStockList", frStockList);
			model.addObject("frIdsList", frIdsList);
			model.addObject("frList", frList);
			model.addObject("curStkFrList", frStockList.getFrStockList());
			model.addObject("currStkItem", frStockList.getItemIdList());
			
			Setting showHead = SalesReportController.isHeadAllow();
			if(showHead.getSettingValue()==1) {
				model.addObject("FACTORYNAME", Constants.FACTORYNAME);
				model.addObject("FACTORYADDRESS", Constants.FACTORYADDRESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return model;
	}


	@RequestMapping(value = "/getItemsBySubCatIdAjax", method = RequestMethod.GET)
	public @ResponseBody List<Item> getItemsBySubCatIdAjax(HttpServletRequest request, HttpServletResponse response) {

		List<Item> itemsList = new ArrayList<Item>();
		String selectedCat = request.getParameter("subCatId");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		RestTemplate restTemplate = new RestTemplate();

		boolean isAllCatSelected = false;
		if (selectedCat.contains("-1")) {
			isAllCatSelected = true;

			map.add("catId", "-1");
		} else {
			
			  selectedCat = selectedCat.substring(1, selectedCat.length() - 1); 
			  selectedCat
			  = selectedCat.replaceAll("\"", "");
			 
			System.out.println("selectedCat" + selectedCat.toString());

			map.add("subCatId", selectedCat);
		}

		System.out.println("CatIds ------------- " + selectedCat);

		ParameterizedTypeReference<List<Item>> typeRef2 = new ParameterizedTypeReference<List<Item>>() {
		};

		ResponseEntity<List<Item>> responseEntity2 = restTemplate.exchange(Constants.url + "getItemsBySubCatIdList",
				HttpMethod.POST, new HttpEntity<>(map), typeRef2);

		itemsList = responseEntity2.getBody();
		System.out.println("List Found-------------------" + itemsList);

		return itemsList;
	}

	@RequestMapping(value = "/getAllItemsFrCutStock", method = RequestMethod.GET)
	public @ResponseBody List<Item> getAllItemsFrCutStock(HttpServletRequest request, HttpServletResponse response) {

		List<Item> itemsList = new ArrayList<Item>();
		String selectedCat = request.getParameter("subCatId");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		RestTemplate restTemplate = new RestTemplate();

		boolean isAllCatSelected = false;
		if (selectedCat.contains("-1")) {
			isAllCatSelected = true;

			map.add("catId", "-1");
		} else {
			selectedCat = selectedCat.substring(1, selectedCat.length() - 1);
			selectedCat = selectedCat.replaceAll("\"", "");
			System.out.println("selectedCat" + selectedCat.toString());

			map.add("subCatId", selectedCat);
		}

		ParameterizedTypeReference<List<Item>> typeRef2 = new ParameterizedTypeReference<List<Item>>() {
		};

		ResponseEntity<List<Item>> responseEntity2 = restTemplate.exchange(Constants.url + "getItemsBySubCatIdList",
				HttpMethod.POST, new HttpEntity<>(map), typeRef2);

		itemsList = responseEntity2.getBody();
		System.out.println("All Items List Found-------------------" + itemsList);

		return itemsList;

	}

	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}
}
