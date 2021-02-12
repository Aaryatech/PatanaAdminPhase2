package com.ats.adminpanel.controller;

import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
//import java.util.Date;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Scope;
//import org.joda.time.DateTime;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.adminpanel.commons.AccessControll;
import com.ats.adminpanel.commons.Constants;
import com.ats.adminpanel.commons.DateConvertor;
import com.ats.adminpanel.commons.SetOrderDataCommon;
import com.ats.adminpanel.model.AllFrIdName;
import com.ats.adminpanel.model.AllFrIdNameList;
import com.ats.adminpanel.model.GenerateBill;
import com.ats.adminpanel.model.Info;
import com.ats.adminpanel.model.Order;
import com.ats.adminpanel.model.Orders;
import com.ats.adminpanel.model.Section;
import com.ats.adminpanel.model.accessright.ModuleJson;
import com.ats.adminpanel.model.franchisee.AllFranchiseeList;
import com.ats.adminpanel.model.franchisee.AllMenuResponse;
import com.ats.adminpanel.model.franchisee.FranchiseeList;
import com.ats.adminpanel.model.franchisee.Menu;
import com.ats.adminpanel.model.item.AllItemsListResponse;
import com.ats.adminpanel.model.item.Item;
import com.ats.adminpanel.model.pushorderdata.GetOrderDataForPushOrder;
import com.ats.adminpanel.model.pushorderdata.GetOrderDataForPushOrderList;
import com.ats.adminpanel.model.pushorderdata.PushOrderList;

@Controller
@Scope("session")
public class PushOrderController {
	AllFrIdNameList allFrIdNameList;
	List<Menu> menuList;// = new ArrayList<Menu>();
	List<String> selectedFrList;
	ArrayList<Integer> selectedFrIdList;
	List<Menu> selectedMenuList = new ArrayList<Menu>();
	public static List<Item> items;
	int menuId;
	int selectedMainCatId;
	List<GetOrderDataForPushOrder> pushOrderData;

	@RequestMapping(value = "/showpushorders", method = RequestMethod.GET)
	public ModelAndView showPushOrder(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		try {
		HttpSession session = request.getSession();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showpushorders", "showpushorders", "1", "0", "0", "0", newModuleList);

		if (view.getError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("orders/pushorders");
			Constants.mainAct = 4;
			Constants.subAct = 30;

			RestTemplate restTemplate = new RestTemplate();

			/*
			 * AllMenuResponse allMenuResponse = restTemplate.getForObject(Constants.url +
			 * "getAllMenu", AllMenuResponse.class);
			 * 
			 * menuList = allMenuResponse.getMenuConfigurationPage(); allFrIdNameList = new
			 * AllFrIdNameList(); try {
			 * 
			 * selectedMenuList = new ArrayList<Menu>();
			 * 
			 * for (int i = 0; i < menuList.size(); i++) {
			 * 
			 * if (menuList.get(i).getMainCatId() != 5) {
			 * selectedMenuList.add(menuList.get(i)); } }
			 * 
			 * } catch (Exception e) { System.out.println("Exception in getAllFrIdName" +
			 * e.getMessage()); e.printStackTrace();
			 * 
			 * }
			 */
			// allFrIdNameList = restTemplate.getForObject(Constants.url + "getAllFrIdName",
			// AllFrIdNameList.class);

			allFrIdNameList = restTemplate.getForObject(Constants.url + "getAllFrIdName",
					AllFrIdNameList.class);

			List<AllFrIdName> selectedFrListAll = new ArrayList();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("sectionId", Constants.PUSH_ORDER_SECTION_ID);
			Section section = restTemplate.postForObject(Constants.url + "getSingleSection", map, Section.class);
			String mId = section.getMenuIds();
			String[] menuId = mId.split(",");

			List<Integer> menuIds = new ArrayList<>();
			for (int i = 0; i < menuId.length; i++) {
				menuIds.add(Integer.parseInt(menuId[i]));
			}

			map = new LinkedMultiValueMap<String, Object>();
			map.add("menuIds", mId);
			AllMenuResponse menuResponse = restTemplate.postForObject(Constants.url + "getMenuListByMenuIds", map,
					AllMenuResponse.class);

			selectedMenuList = new ArrayList<Menu>();
			selectedMenuList = menuResponse.getMenuConfigurationPage();

			System.out.println(" Fr " + allFrIdNameList.getFrIdNamesList());
			java.util.Date utilDate = new java.util.Date();
			model.addObject("unSelectedMenuList", selectedMenuList);
			model.addObject("unSelectedFrList", allFrIdNameList.getFrIdNamesList());
			model.addObject("date", new SimpleDateFormat("dd-MM-yyyy").format(utilDate));
		}
		}catch (HttpClientErrorException e) {
			System.err.println("Http Error At push order " +e.getResponseBodyAsString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	// Sachin 25-01-2021
	@RequestMapping(value = "/getAllFrIdNameByMenuIdConfigured", method = RequestMethod.POST)
	public @ResponseBody List<AllFrIdName> getAllFrIdNameByMenuIdConfigured(HttpServletRequest request,
			HttpServletResponse response) {

		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("menuId", request.getParameter("menuId"));

		allFrIdNameList = restTemplate.postForObject(Constants.url + "getAllFrIdNameByMenuIdConfigured",
				map,AllFrIdNameList.class);
		return allFrIdNameList.getFrIdNamesList();

	}
	// Ajax call

	@RequestMapping(value = "/getItemList", method = RequestMethod.GET)
	public @ResponseBody List<PushOrderList> generateItemList(HttpServletRequest request,
			HttpServletResponse response) {

		RestTemplate restTemplate = new RestTemplate();

		List<AllFrIdName> selectedFrListAll = new ArrayList();

		List<PushOrderList> pushOrdeList = new ArrayList<PushOrderList>();

		selectedFrListAll = allFrIdNameList.getFrIdNamesList();

		String selectedMenu = request.getParameter("menu_id");
		menuId = Integer.parseInt(selectedMenu);

		String selectedFr = request.getParameter("fr_id_list");

		System.out.println("Selected Franchisee Ids" + selectedFr);

		selectedFr = selectedFr.substring(1, selectedFr.length() - 1);
		selectedFr = selectedFr.replaceAll("\"", "");

		selectedFrList = new ArrayList<>();

		selectedFrList = Arrays.asList(selectedFr.split(","));

		selectedFrIdList = new ArrayList();
		List<AllFrIdName> allFrList = allFrIdNameList.getFrIdNamesList();

		for (int i = 0; i < selectedMenuList.size(); i++) {
			if (selectedMenuList.get(i).getMenuId() == menuId) {
				selectedMainCatId = selectedMenuList.get(i).getMainCatId();
			}
		}

		System.out.println("Before Rest of Items   and mennu id is  :  " + selectedMenu);

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		//map.add("itemGrp1", selectedMainCatId);
		map.add("menuId", menuId);
		try {

			ParameterizedTypeReference<List<Item>> typeRef = new ParameterizedTypeReference<List<Item>>() {
			};
			ResponseEntity<List<Item>> responseEntity = restTemplate.exchange(
					Constants.url + "getItemAvailByMenuId", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			items = responseEntity.getBody();

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}
		System.out.println("After Rest of Items   and mennu id is  :");

		/*
		 * Sac comm map = new LinkedMultiValueMap<String, Object>();
		 * 
		 * String strFrId = selectedFr; System.out.println("strFrId =" +
		 * strFrId.toString()); map.add("frIdList", strFrId);
		 * 
		 * GetOrderDataForPushOrderList pushOrderDataList = restTemplate
		 * .postForObject(Constants.url + "getOrderDataForPushOrder", map,
		 * GetOrderDataForPushOrderList.class);
		 * 
		 * pushOrderData = pushOrderDataList.getOrderDataForPushOrder();
		 */

		System.out.println("Item List: " + items.toString());

		List<GetOrderDataForPushOrder> prevPushOrderList = new ArrayList<GetOrderDataForPushOrder>();
		GetOrderDataForPushOrder prevOrderData = null;
		PushOrderList pushOrder = null;
		for (int j = 0; j < items.size(); j++) {

			//System.out.println("Inside First For ");

			pushOrder = new PushOrderList();

			pushOrder.setItemId(items.get(j).getId());
			pushOrder.setItemName(items.get(j).getItemName());

			/*
			 * sac comm if (pushOrderData != null) {
			 * 
			 * for (int k = 0; k < pushOrderData.size(); k++) {
			 * 
			 * prevOrderData = new GetOrderDataForPushOrder();
			 * 
			 * if (pushOrderData.get(k).getItemId() == items.get(j).getId()) { x = x + 1;
			 * System.out.println("matched " + x);
			 * 
			 * prevOrderData.setFrId(pushOrderData.get(k).getFrId());
			 * prevOrderData.setItemId(pushOrderData.get(k).getItemId());
			 * prevOrderData.setOrderId(pushOrderData.get(k).getOrderId());
			 * prevOrderData.setOrderQty(pushOrderData.get(k).getOrderQty());
			 * 
			 * System.out.println("prev Order Dat " + prevOrderData.toString());
			 * 
			 * prevPushOrderList.add(prevOrderData);
			 * 
			 * pushOrder.setGetOrderDataForPushOrder(prevPushOrderList);
			 * 
			 * System.out.println("prev Order Dat List " + prevPushOrderList.toString());
			 * 
			 * }
			 * 
			 * } // pushOrder.setGetOrderDataForPushOrder(prevPushOrderList);
			 * 
			 * }
			 */

			pushOrdeList.add(pushOrder);

		}

		System.out.println("to String push Order List " + pushOrdeList.toString());

		for (int i = 0; i < allFrList.size(); i++) {

			for (int j = 0; j < selectedFrList.size(); j++) {
				// System.out.println("Current Fr"+selectedFrList.get(j));

				if ((allFrList.get(i).getFrId()) == Integer.parseInt(selectedFrList.get(j))) {
					//System.out.println(allFrList.get(i).getFrName());

					selectedFrIdList.add(allFrList.get(i).getFrId());
				}
			}

		}

		// return items;
		System.out.println("Final List :" + pushOrdeList.toString());
		return pushOrdeList;
	}

	// After submit order

	@RequestMapping(value = "/submitPushOrder", method = RequestMethod.POST)
	public String submitPushOrders(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		ModelAndView model = new ModelAndView("orders/pushorders");

		Orders order = new Orders();

		// List<Orders> oList=new ArrayList<>();
		String todaysDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		java.util.Date utilDate = new java.util.Date();
		System.out.println(dateFormat.format(utilDate)); // 2016/11/16 12:08:43

		java.sql.Date date = new java.sql.Date(utilDate.getTime());
		// java.sql.Date deliveryDate = new java.sql.Date(tomarrow().getTime());
		// java.sql.Date deliveryDate=new java.sql.Date(tomarrow1().getTime());
		// --------------------------Date
		// Added--------------------------------------------------
		String dateStr = request.getParameter("date");
		String delDateStr = request.getParameter("deldate");
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date udate = sdf1.parse(dateStr);
		java.util.Date udeldate = sdf1.parse(delDateStr);
		java.sql.Date sqlCurrDate = new java.sql.Date(udate.getTime());
		java.sql.Date deliveryDate = new java.sql.Date(udeldate.getTime());
		System.err.println("deliveryDate" + deliveryDate + "sqlCurrDate" + sqlCurrDate);
		// -----------------------------------------------------------------------------
		// get all Franchisee details
		RestTemplate restTemplate = new RestTemplate();

		AllFranchiseeList allFranchiseeList = restTemplate.getForObject(Constants.url + "getAllFranchisee",
				AllFranchiseeList.class);

		List<FranchiseeList> franchaseeList = new ArrayList<FranchiseeList>();
		franchaseeList = allFranchiseeList.getFranchiseeList();
		boolean pushItem = false;

		if (pushOrderData != null) {

			pushItem = true;
		}

		if (pushItem) {
			System.out.println("push Order Length" + pushOrderData.size());

			for (int j = 0; j < items.size(); j++) {

				// if (pushOrderData.get(m).getItemId() != items.get(j).getId()) {
				float discPer = Float.parseFloat(request.getParameter("disc_per" + items.get(j).getId()));
				// System.out.println(items.get(j).getId());
				for (int i = 0; i < selectedFrIdList.size(); i++) {

					boolean isSameItem = false;
					for (int m = 0; m < pushOrderData.size(); m++) {
						if (pushOrderData.get(m).getItemId() == items.get(j).getId()) {

							isSameItem = true;

						}
					}
					if (isSameItem == false) {

						System.out.println(items.get(j).getId());

						String quantity = request
								.getParameter("itemId" + items.get(j).getId() + "orderQty" + selectedFrIdList.get(i));
						int qty = Integer.parseInt(quantity);

						if (qty != 0) {

							List<Orders> oList = new ArrayList<>();

							order.setOrderDatetime(todaysDate);
							order.setFrId(selectedFrIdList.get(i));
							order.setOrderSubType(items.get(j).getItemGrp2());
							order.setItemId(String.valueOf(items.get(j).getId()));
							order.setOrderQty(qty);
							order.setEditQty(qty);
							order.setProductionDate(sqlCurrDate);// date var removed
							order.setOrderDate(sqlCurrDate);// date var removed
							order.setDeliveryDate(deliveryDate);
							// order.setGrnType(4);
							order.setIsEdit(0);
							order.setRefId(1);
							order.setIsPositive(discPer);
							order.setMenuId(menuId);
							order.setOrderType(selectedMainCatId);

							for (int l = 0; l < selectedFrIdList.size(); l++) {
								for (int k = 0; k < franchaseeList.size(); k++) {
									if (selectedFrIdList.get(l) == franchaseeList.get(k).getFrId()) {
										if (franchaseeList.get(k).getFrRateCat() == 1) {
											order.setOrderRate(items.get(j).getItemRate1());
											order.setOrderMrp(items.get(j).getItemMrp1());
										} else if (franchaseeList.get(k).getFrRateCat() == 3) {
											order.setOrderRate(items.get(j).getItemRate3());
											order.setOrderMrp(items.get(j).getItemMrp3());
										}
										order.setGrnType(franchaseeList.get(k).getGrnTwo());// new
									}
								}
							}

							oList.add(order);
							PlaceOrder(oList);
							System.out.println("oList Ganesh = " + oList.toString());

						}

					} // end of else

					else {

						isSameItem = false;
					}
				}
	 			// } // end of if pushOrderData
			}
			// for }
		} // end of if pushItem
		SimpleDateFormat yydate = new SimpleDateFormat("yyyy-MM-dd");
		dateStr = DateConvertor.convertToYMD(dateStr);
		delDateStr = DateConvertor.convertToYMD(delDateStr);
		SimpleDateFormat ymdSDF = new SimpleDateFormat("yyyy-MM-dd");

		if (pushItem == false) {
			System.err.println("Here new");
			for (java.util.Date d = yydate.parse(dateStr); d.compareTo(yydate.parse(delDateStr)) <= 0;) {

				for (int j = 0; j < items.size(); j++) {

					float discPer = Float.parseFloat(request.getParameter("disc_per" + items.get(j).getId()));
					// System.out.println(items.get(j).getId());
					for (int i = 0; i < selectedFrIdList.size(); i++) {

						System.out.println(items.get(j).getId());

						String quantity = request
								.getParameter("itemId" + items.get(j).getId() + "orderQty" + selectedFrIdList.get(i));
						// System.out.println("qtyb " + quantity);
						int qty = Integer.parseInt(quantity);

						if (qty != 0) {
							List<Orders> oList = new ArrayList<>();

							order.setOrderDatetime(todaysDate);
							order.setFrId(selectedFrIdList.get(i));
							order.setRefId(1);
							order.setItemId(String.valueOf(items.get(j).getId()));
							order.setOrderQty(qty);
							order.setEditQty(qty);
							order.setProductionDate(sqlCurrDate);// date var removed
							order.setOrderDate(sqlCurrDate);// date var removed
							order.setDeliveryDate(deliveryDate);
							order.setOrderSubType(items.get(j).getItemGrp2());
							// order.setMenuId(0);
							// order.setGrnType(4);
							order.setIsPositive(discPer);
							order.setIsEdit(0);
							order.setMenuId(menuId);
							order.setOrderType(selectedMainCatId);

							for (int l = 0; l < selectedFrIdList.size(); l++) {
								for (int k = 0; k < franchaseeList.size(); k++) {
									if (selectedFrIdList.get(l) == franchaseeList.get(k).getFrId()) {
										if (franchaseeList.get(k).getFrRateCat() == 1) {
											order.setOrderRate(items.get(j).getItemRate1());
											order.setOrderMrp(items.get(j).getItemMrp1());
										} else if (franchaseeList.get(k).getFrRateCat() == 3) {
											order.setOrderRate(items.get(j).getItemRate3());
											order.setOrderMrp(items.get(j).getItemMrp3());
										}
										order.setGrnType(franchaseeList.get(k).getGrnTwo());// new
									}
								}
							}


							String convertedDate = ymdSDF.format(d);
							SetOrderDataCommon setOrdData = new SetOrderDataCommon();
							order = setOrdData.setOrderData(order, menuId, order.getFrId(), order.getOrderQty(),
									request, convertedDate);
							oList.add(order);

							PlaceOrder(oList);
							System.out.println("oList Ganesh = " + oList.toString());
						} // end of if qty!=0

					}
				} // end items for loop

				d.setTime(d.getTime() + 1000 * 60 * 60 * 24);
			} // End of Date For Loop-Sachin added 23-01-2021

		} // end of not pushItem

		model.addObject("unSelectedMenuList", menuList);
		model.addObject("unSelectedFrList", allFrIdNameList.getFrIdNamesList());

		return "redirect:/showpushorders";
	}

	int x = 0;

	void PlaceOrder(List<Orders> oList) {
		// RestTemplate restTemplate = new RestTemplate();
		x = x + 1;

		System.out.println("Order number  :   " + x);
		String url = Constants.url + "placePushDumpOrder";

		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = null;

		try {
			jsonStr = mapperObj.writeValueAsString(oList);
			// System.out.println("Converted JSON: " + jsonStr);
		} catch (IOException e) {
			System.out.println("Excep converting java 2 json " + e.getMessage());
			e.printStackTrace();
		}
		// System.out.println("Before Order place");
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<String>(jsonStr, headers);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> orderListResponse = restTemplate.exchange(url, HttpMethod.POST, entity,
					String.class);

			// System.out.println("Place Order Response" + orderListResponse.toString());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public java.util.Date tomarrow() {

		java.util.Date dt = new java.util.Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		return dt;
	}

	public java.util.Date tomarrowDate(java.util.Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		java.util.Date dateRes = c.getTime();
		return dateRes;
	}
	/*
	 * public java.util.Date tomarrow1() {
	 * 
	 * 
	 * java.util.Date dt = new java.util.Date(); Calendar c =
	 * Calendar.getInstance(); c.setTime(dt); c.add(Calendar.DATE, 2); dt =
	 * c.getTime(); return dt; }
	 */

}