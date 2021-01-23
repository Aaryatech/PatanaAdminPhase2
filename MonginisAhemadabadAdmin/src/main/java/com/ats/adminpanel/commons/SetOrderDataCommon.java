package com.ats.adminpanel.commons;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ats.adminpanel.model.ConfigureFrBean;
import com.ats.adminpanel.model.FrMenu;
import com.ats.adminpanel.model.FrMenuConfigure;
import com.ats.adminpanel.model.Orders;
import com.ats.adminpanel.model.item.Item;

public class SetOrderDataCommon {

	public MultiValueMap<String, Object> map;

	public Orders setOrderData(Orders order,int menuId,int frId,int orderQty,HttpServletRequest request,
			String methodOrderDate) {
		System.err.println("in setOrderData methodOrderDate" +methodOrderDate); 
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("id", order.getItemId());

		Item item = restTemplate.postForObject("" + Constants.url + "getItem", map, Item.class);
		// FrMenu menu=new FrMenu();
		try {
			
		order.setOrderType(item.getItemGrp1());//ie catId
		order.setOrderSubType(item.getItemGrp2());//ie subCatId
		order.setOrderQty(orderQty);
		ConfigureFrBean menu=null;
	
		map = new LinkedMultiValueMap<String, Object>();
		map.add("menuId", order.getMenuId());
	
		try {
			menu = restTemplate.postForObject(
				Constants.url + "getFrMenuConfigureByMenuFrId", map, ConfigureFrBean.class);
		}catch (HttpClientErrorException e) {
			System.err.println("getFrConfUpdate" +e.getResponseBodyAsString());
		}
	
		String fromTime = menu.getFromTime();
		String toTime = menu.getToTime();

		String orderDate = "";
		String productionDate = "";
		String deliveryDate = "";
		
		ZoneId z = ZoneId.of("Asia/Calcutta");
		LocalTime now = LocalTime.now(z); // Explicitly specify the desired/expected time zone.

		LocalTime fromTimeLocalTime = LocalTime.parse(fromTime);
		LocalTime toTimeLocalTIme = LocalTime.parse(toTime);
		
		String todaysDate =methodOrderDate;
		LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		if (fromTimeLocalTime.isBefore(toTimeLocalTIme)) {
			orderDate = todaysDate;
			//productionDate = todaysDate;
			productionDate = incrementDate(todaysDate,menu.getProdDays());
			deliveryDate = incrementDate(todaysDate, menu.getDelDays());
		} else {
			if (now.isAfter(fromTimeLocalTime)) {
				orderDate = todaysDate;
				productionDate = incrementDate(todaysDate, menu.getProdDays()+1);
				deliveryDate = incrementDate(todaysDate, menu.getDelDays()+1);
			} else {
				orderDate = todaysDate;
				productionDate = incrementDate(todaysDate,menu.getProdDays());
				deliveryDate = incrementDate(todaysDate, menu.getDelDays());
			}
		}
		int rateCat=menu.getRateSettingType();
		float mrp=0;
		float profitPer=0;
		
		if (rateCat == 1) {
			mrp=(float) item.getItemMrp1();
		}else if(rateCat == 2) {
			mrp=(float) item.getItemMrp2();
		}else {
			mrp=(float) item.getItemMrp3();
		}
		profitPer=menu.getProfitPer();
		float rate=(mrp-(mrp*profitPer)/100);      
		order.setDeliveryDate(stringToSqlDate(deliveryDate));
		order.setOrderDate(stringToSqlDate(orderDate));
		order.setOrderDatetime(todaysDate);
		order.setProductionDate(stringToSqlDate(productionDate));
		
		order.setOrderMrp(mrp);
		order.setOrderRate(rate);
		order.setGrnType(menu.getGrnPer());
		order.setIsPositive((int)menu.getDiscPer());//set discPer
		System.err.println("order Here " +order.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return order;
		
	}
	
	public String incrementDate(String date, int day) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(date));

		} catch (ParseException e) {
			// System.out.println("Exception while incrementing date " + e.getMessage());
			e.printStackTrace();
		}
		c.add(Calendar.DATE, day); // number of days to add

		date = sdf.format(c.getTime());

		return date;

	}
	public static java.sql.Date stringToSqlDate(String date) {
		java.sql.Date sqlDate = null;
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date utilDate;

			utilDate = sdf1.parse(date);
			sqlDate = new java.sql.Date(utilDate.getTime());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqlDate;

	}
}
