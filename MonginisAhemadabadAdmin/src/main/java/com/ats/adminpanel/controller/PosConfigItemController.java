package com.ats.adminpanel.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.adminpanel.commons.Constants;
import com.ats.adminpanel.model.Info;
import com.ats.adminpanel.model.PosConfigItem;
import com.ats.adminpanel.model.item.AllItemsListResponse;
import com.ats.adminpanel.model.item.Item;
import com.ats.adminpanel.model.modules.ErrorMessage;


@Controller
public class PosConfigItemController {
	AllItemsListResponse allItemsListResponse;
	
	@RequestMapping(value ="/PosConfigItem", method = RequestMethod.GET)
	public ModelAndView PosConfigItem(HttpServletRequest request, HttpServletResponse response) {
        System.err.println("hi ds");
		ModelAndView model = new ModelAndView("franchisee/PosConfigItem");

		RestTemplate restTemplate = new RestTemplate();
		
				
				PosConfigItem[] array = restTemplate.getForObject(Constants.url + "/getPosConfigItem",
						PosConfigItem[].class);
				List<PosConfigItem> stationList = new ArrayList<PosConfigItem>(Arrays.asList(array));
				model.addObject("stationList", stationList);
				System.out.println(stationList);
				
		

	   return model;
	}
		
	
	
	@RequestMapping(value = "/postPosConfigItem", method = RequestMethod.POST)
	public ModelAndView postPosConfigItem(HttpServletRequest request, HttpServletResponse response) {
      List<PosConfigItem> list = new ArrayList<PosConfigItem>();
      
      PosConfigItem pc=new PosConfigItem();
      PosConfigItem pos=new PosConfigItem();
		ModelAndView mav = new ModelAndView("franchisee/PosConfigItem");
		try {
       System.out.println("df");
			RestTemplate restTemplate = new RestTemplate();
			

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			
			String[] itemId = request.getParameterValues("item_name[]");
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < itemId.length; i++) {
				sb = sb.append(itemId[i] + ",");

			}
			String itemId1 = sb.toString();
			itemId1 = itemId1.substring(0, itemId1.length() - 1);
			String configName = request.getParameter("config_name");
	       // String itemId = request.getParameter("item_name");
	        
	        pc.setConfigName(configName);
	        pc.setItemIds(itemId1);
	        pc.setExtInt1(0);
	        pc.setExtInt2(0);
	       
            map.add("configName",configName);
            map.add("itemId",itemId1); 
            System.out.println("map"+map);
            
			Info info = restTemplate.postForObject(Constants.url + "insertPosConfigItem", pc, Info.class);
			
			
	} catch (Exception e) {
			System.out.println("HomeController Home Request Page Exception:  " + e.getMessage());
			e.printStackTrace();
		}
  		return mav;
      }

	
		@RequestMapping(value = "/deletePosConfigItem/{idList}", method = RequestMethod.GET)
	public String deleteItem(@PathVariable String[] idList) {

		// String id=request.getParameter("id");
		try {
			ModelAndView mav = new ModelAndView("franchisee/PosConfigItem");

			RestTemplate rest = new RestTemplate();

			String strItemIds = new String();
			for (int i = 0; i < idList.length; i++) {
				strItemIds = strItemIds + "," + idList[i];
			}
			strItemIds = strItemIds.substring(1);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("id", strItemIds);

			ErrorMessage errorResponse = rest.postForObject("" + Constants.url + "deletePosConfigItem", map, ErrorMessage.class);
			System.out.println(errorResponse.toString());

		

			if (errorResponse.getError()) {
				return "franchisee/PosConfigItem";

			} else {
				return "franchisee/PosConfigItem";

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "franchisee/PosConfigItem";
	}


}
