package com.ats.adminpanel.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Scope;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.adminpanel.commons.AccessControll;
import com.ats.adminpanel.commons.Constants;
import com.ats.adminpanel.commons.DateConvertor;
import com.ats.adminpanel.commons.SetOrderDataCommon;
import com.ats.adminpanel.model.AllFrIdName;
import com.ats.adminpanel.model.AllFrIdNameList;
import com.ats.adminpanel.model.ConfigureFrBean;
import com.ats.adminpanel.model.ConfigureFrListResponse;
import com.ats.adminpanel.model.DumpOrderList;
import com.ats.adminpanel.model.GetDumpOrder;
import com.ats.adminpanel.model.GetDumpOrderList;
import com.ats.adminpanel.model.Info;
import com.ats.adminpanel.model.OrderData;
import com.ats.adminpanel.model.Orders;
import com.ats.adminpanel.model.Section;
import com.ats.adminpanel.model.accessright.ModuleJson;
import com.ats.adminpanel.model.franchisee.AllFranchiseeList;
import com.ats.adminpanel.model.franchisee.AllMenuResponse;
import com.ats.adminpanel.model.franchisee.FranchiseeList;
import com.ats.adminpanel.model.franchisee.Menu;
import com.ats.adminpanel.model.item.Item;

@Controller
@Scope("session")
public class DumpOrderController {

	public static AllFrIdNameList allFrIdNameList;
	List<Menu> menuList;// = new ArrayList<Menu>();
	public static List<Menu> selectedMenuList;
	List<String> selectedFrList;
	ArrayList<Integer> selectedFrIdList;
	public static List<Item> items;
	// public static List<GetDumpOrderList> getdumpOrderList;
	public static List<GetDumpOrder> getdumpOrder;
	List<DumpOrderList> dumpOrderList;
	GetDumpOrderList getdumpOrderList;
	int menuId;
	int selectedMainCatId;

	@RequestMapping(value = "/showdumporders", method = RequestMethod.GET)
	public ModelAndView showDumpOrder(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showdumporders", "showdumporders", "1", "0", "0", "0", newModuleList);

		if (view.getError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("orders/dumporders");
			Constants.mainAct = 4;
			Constants.subAct = 31;

			RestTemplate restTemplate = new RestTemplate();
			try {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("sectionId", Constants.DUMP_ORDER_SECTION_ID);
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
				menuList = menuResponse.getMenuConfigurationPage();

			} catch (Exception e) {
				System.out.println("Exception in getAllFrIdName" + e.getMessage());
				e.printStackTrace();

			}

			model.addObject("todayDate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

			model.addObject("unSelectedMenuList", menuList);
			//model.addObject("unSelectedFrList", allFrIdNameList.getFrIdNamesList());
		}
		return model;
	}

	@RequestMapping(value = "/getNonOrderFrList", method = RequestMethod.GET)
	public @ResponseBody List<AllFrIdName> getNonOrderFrList(HttpServletRequest request, HttpServletResponse response) {

		int menu_id = Integer.parseInt(request.getParameter("menu_id"));
		String formOrderDate=request.getParameter("orderDate");
		System.err.println("order date selected In getNonOrderFrList "+formOrderDate);
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date date = new java.sql.Date(utilDate.getTime());
		String orderDate = date.toString();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("orderDate", DateConvertor.convertToYMD(formOrderDate));
		map.add("menuId", menu_id);
		RestTemplate restTemplate = new RestTemplate();
		try {
			allFrIdNameList = restTemplate.postForObject(Constants.url + "getNonOrderFr", map, AllFrIdNameList.class);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return allFrIdNameList.getFrIdNamesList();
	}
	// Ajax Call

	@RequestMapping(value = "/getOrderItemList", method = RequestMethod.GET)
	public @ResponseBody List<DumpOrderList> generateItemOrder(HttpServletRequest request,
			HttpServletResponse response) {

		// int selectedMainCatId=0;
		String selectOrderDate = request.getParameter("preOrder_Date");
		String searchBy = request.getParameter("searchBy");
		String selectedFr = request.getParameter("fr_id_list");
		selectedFr = selectedFr.substring(1, selectedFr.length() - 1);
		selectedFr = selectedFr.replaceAll("\"", "");

		String selectedMenu = request.getParameter("menu_id");
		menuId = Integer.parseInt(selectedMenu);
		System.out.println("here");

		System.out.println("Selected Franchisee Ids" + selectedFr);
		// selectedFr = selectedFr.substring(1, selectedFr.length() - 1);
		// selectedFr = selectedFr.replaceAll("\"", "");
		selectedFrList = new ArrayList<>();
		selectedFrList = Arrays.asList(selectedFr.split(","));
		selectedFrIdList = new ArrayList();
		List<AllFrIdName> allFrList = allFrIdNameList.getFrIdNamesList();
		System.out.println("Selected Franchisee");
		for (int i = 0; i < allFrList.size(); i++) {
			for (int j = 0; j < selectedFrList.size(); j++) {
				if ((allFrList.get(i).getFrId()) == Integer.parseInt(selectedFrList.get(j))) {
					//System.out.println(allFrList.get(i).getFrName());

					selectedFrIdList.add(allFrList.get(i).getFrId());
				}
			}

		}

		for (int i = 0; i < menuList.size(); i++) {
			if (menuList.get(i).getMenuId() == menuId) {
				selectedMainCatId = menuList.get(i).getMainCatId();
			}
		}

		System.out.println("Before Rest of Items   and mennu id is  :  " + selectedMenu);

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("menuId", menuId);
		RestTemplate restTemplate = new RestTemplate();
		try {

			ParameterizedTypeReference<List<Item>> typeRef = new ParameterizedTypeReference<List<Item>>() {
			};
			ResponseEntity<List<Item>> responseEntity = restTemplate.exchange(
					Constants.url + "getItemAvailByMenuId", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			items = responseEntity.getBody();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		MultiValueMap<String, Object> map2 = new LinkedMultiValueMap<String, Object>();

		map2.add("date", selectOrderDate);
		map2.add("menuId", selectedMenu);
		map2.add("searchBy", searchBy);
		map2.add("frId", selectedFr);
		// GetDumpOrderList getdumpOrderList;
		List<GetDumpOrder> OrderList = new ArrayList<GetDumpOrder>();
		try {
			getdumpOrderList = restTemplate.postForObject(Constants.url + "getOrderListForDumpOrder", map2,
					GetDumpOrderList.class);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		OrderList = getdumpOrderList.getGetDumpOrder();
		//System.out.println("List  " + OrderList.toString());
		
		// dumpOrderList=new ArrayList<DumpOrderList>();
		dumpOrderList = new ArrayList<>();
/*
 select 0 as order_id,
m_franchisee.fr_id,
0 as menu_id,
m_fr_item_stock.reorder_qty as order_qty,
m_fr_item_stock.item_id
FROM m_fr_item_stock,m_franchisee,m_fr_configure
WHERE m_franchisee.fr_id in (156,111) and m_franchisee.stock_type=m_fr_item_stock.type
and find_in_set(m_fr_item_stock.item_id,m_fr_configure.item_show) and m_fr_configure.menu_id=103
 */
		System.err.println("A items " +items);
		System.err.println("B OrderList " +OrderList);
		
		for (int i = 0; i < items.size(); i++) {
			System.out.println("Item ID  " + items.get(i).getId());
			DumpOrderList dumpOrder = new DumpOrderList();

			List<OrderData> orderDataList = new ArrayList<OrderData>();

			dumpOrder.setItemId(String.valueOf(items.get(i).getId()));
			dumpOrder.setItemName(items.get(i).getItemName());

			for (int j = 0; j < OrderList.size(); j++) {
				//System.err.println("OrderList item Id " +OrderList.get(j).getItemId());
				if (items.get(i).getId() == Integer.parseInt((OrderList.get(j).getItemId()))) {
					System.err.println("Match " +OrderList.get(j).getItemId());
					OrderData orderData = new OrderData();
					orderData.setFrId(OrderList.get(j).getFrId());

					orderData.setOrderQty(OrderList.get(j).getOrderQty());
					orderDataList.add(orderData);

					System.out.println(
							"FR  " + OrderList.get(j).getFrId() + " Item QTY  " + OrderList.get(j).getOrderQty());
					dumpOrder.setOrderData(orderDataList);
					//break;
				}

			}
			//System.out.println("List of orders   " + dumpOrder.toString());
			dumpOrderList.add(dumpOrder);
		}
		System.out.println("Final List  :  " + dumpOrderList.toString());
		System.out.println("Count  " + dumpOrderList.size());

		System.out.println("After Rest of Items   and mennu id is  :");

		System.out.println("Item List: " + items.toString());
		

		return dumpOrderList;
	}

	// After submit order

	@RequestMapping(value = "/submitDumpOrder", method = RequestMethod.POST)
	public String submitDumpOrders(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		ModelAndView model = new ModelAndView("orders/dumporders");
		Orders order = new Orders();

		String todaysDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		//-------------------Get Prod And Del Date From Jsp----------------
		String dateStr = request.getParameter("date");
		String delDateStr = request.getParameter("deldate");
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date udate = sdf1.parse(dateStr);
		java.util.Date udeldate = sdf1.parse(delDateStr);
		java.sql.Date sqlCurrDate = new java.sql.Date(udate.getTime());
		java.sql.Date deliveryDate = new java.sql.Date(udeldate.getTime());
		System.err.println("deliveryDate" + deliveryDate + "sqlCurrDate" + sqlCurrDate);
		
		SimpleDateFormat yydate = new SimpleDateFormat("yyyy-MM-dd");
		dateStr=DateConvertor.convertToYMD(dateStr);
		java.util.Date orderDateSel=yydate.parse(dateStr);
		
		RestTemplate restTemplate = new RestTemplate();

		System.out.println("Before Fr Rest call");
		AllFranchiseeList allFranchiseeList = restTemplate.getForObject(Constants.url + "getAllFranchisee",
				AllFranchiseeList.class);

		System.out.println("Aftr Fr Rest call");
		List<FranchiseeList> franchaseeList = new ArrayList<FranchiseeList>();
		franchaseeList = allFranchiseeList.getFranchiseeList();
		SimpleDateFormat ymdSDF = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("Items   " + items.toString());
		for (int j = 0; j < items.size(); j++) {
			System.out.println("Items   " + items.get(j).getItemName());

			
			float discPer =Float.parseFloat(request.getParameter("disc_per" + items.get(j).getId()));
			// System.out.println(items.get(j).getId());
			for (int i = 0; i < selectedFrIdList.size(); i++) {
				System.out.println("FR   " + selectedFrIdList.get(i));
				System.out.println(items.get(j).getId());

				String quantity = request
						.getParameter("itemId" + items.get(j).getId() + "orderQty" + selectedFrIdList.get(i));
				// String quantity=request.getParameter("ggg");
				System.out.println("Quantity  " + quantity);
				int qty = Integer.parseInt(quantity);
				// System.out.println("For Fr and item
				// id"+items.get(j).getId()+"orderQty"+selectedFrIdList.get(i)+" : "+quantity);

				if (qty != 0) {
					List<Orders> oList = new ArrayList<>();

					order.setOrderDatetime(todaysDate);
					order.setFrId(selectedFrIdList.get(i));
					order.setRefId(0);
					order.setItemId(String.valueOf(items.get(j).getId()));
					order.setOrderQty(qty);
					order.setEditQty(qty);
					order.setProductionDate(sqlCurrDate);
					order.setOrderDate(sqlCurrDate);
					order.setDeliveryDate(deliveryDate);
					//order.setGrnType(3);
					order.setOrderSubType(items.get(j).getItemGrp2());
					order.setIsEdit(0);
					order.setMenuId(menuId);
					order.setOrderType(selectedMainCatId);
					order.setIsPositive(discPer);

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
								order.setGrnType(franchaseeList.get(k).getGrnTwo());//new
							}
						}
					}
					String orderDateSelected = ymdSDF.format(orderDateSel);
					SetOrderDataCommon setOrdData = new SetOrderDataCommon();
					order = setOrdData.setOrderData(order, menuId, order.getFrId(), order.getOrderQty(),
							request, orderDateSelected);
					oList.add(order);
					PlaceOrder(oList);

				}

			}
		}
		model.addObject("unSelectedFrList", allFrIdNameList.getFrIdNamesList());
		model.addObject("unSelectedMenuList", selectedMenuList);

		return "redirect:/showdumporders";
	}

	void PlaceOrder(List<Orders> oList) {
//		RestTemplate restTemplate = new RestTemplate();
		System.out.println("Order list  :   " + oList.toString());

		String url = Constants.url + "placePushDumpOrder";

		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = null;

		try {
			jsonStr = mapperObj.writeValueAsString(oList);
			System.out.println("Converted JSON: " + jsonStr);
		} catch (IOException e) {
			System.out.println("Excep converting java 2 json " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Before Order place");
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<String>(jsonStr, headers);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> orderListResponse = restTemplate.exchange(url, HttpMethod.POST, entity,
					String.class);

			System.out.println("Place Order Response" + orderListResponse.toString());

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
