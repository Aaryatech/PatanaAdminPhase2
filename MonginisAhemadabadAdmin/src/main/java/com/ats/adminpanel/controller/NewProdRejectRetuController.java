package com.ats.adminpanel.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.Synthesizer;

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
import com.ats.adminpanel.model.Info;
import com.ats.adminpanel.model.accessright.ModuleJson;
import com.ats.adminpanel.model.item.AllItemsListResponse;
import com.ats.adminpanel.model.item.CategoryListResponse;
import com.ats.adminpanel.model.item.Item;
import com.ats.adminpanel.model.item.MCategoryList;
import com.ats.adminpanel.model.login.UserResponse;
import com.ats.adminpanel.model.production.PostProdPlanHeader;
import com.ats.adminpanel.model.production.PostProductionPlanDetail;
import com.ats.adminpanel.model.rejection.RejectionDetail;
import com.ats.adminpanel.model.rejection.RejectionHeader;
import com.ats.adminpanel.model.stock.FinishedGoodStock;
import com.ats.adminpanel.model.stock.FinishedGoodStockDetail;

//Akhilesh 2021-01-23
@Controller
public class NewProdRejectRetuController {
	
	List<MCategoryList> filteredCatList;
	AllItemsListResponse allItemsListResponse = new AllItemsListResponse();
	List<FinishedGoodStockDetail> showFinStockDetail = new ArrayList<FinishedGoodStockDetail>();
	FinishedGoodStock showStockHeader;

	String todaysDate;
	
	ArrayList<Item> itemList = new ArrayList<>();
	
	@RequestMapping(value = "/showRejectiontionHeaderDetail", method = RequestMethod.GET)
	public ModelAndView showRejectiontionHeaderDetail(HttpServletRequest request, HttpServletResponse response) {
		System.err.println("in  /showRejectiontionHeaderDetail");
		ModelAndView model = null;
		HttpSession session = request.getSession();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showFinishedGoodStock", "showFinishedGoodStock", "1", "0", "0", "0",
				newModuleList);

		if (view.getError() == true) {

			model = new ModelAndView("rejection/rejectionHeaderDetail");

		} else {
			model = new ModelAndView("rejection/rejectionHeaderDetail");
			try {
				Constants.mainAct = 4;
				Constants.subAct = 40;

				RestTemplate restTemplate = new RestTemplate();

				CategoryListResponse allCategoryResponse = restTemplate.getForObject(Constants.url + "showAllCategory",
						CategoryListResponse.class);

				List<MCategoryList> catList = allCategoryResponse.getmCategoryList();

				filteredCatList = new ArrayList<MCategoryList>();
				System.out.println("catList :" + catList.toString());

				for (MCategoryList mCategory : catList) {
					if (mCategory.getCatId() != 5 && mCategory.getCatId() != 3) {
						filteredCatList.add(mCategory);

					}
				}

				model.addObject("catList", filteredCatList);

				ZoneId z = ZoneId.of("Asia/Calcutta");

				LocalDate date = LocalDate.now(z);
				DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-uuuu");
				todaysDate = date.format(formatters);

				model.addObject("todaysDate", todaysDate);

			} catch (Exception e) {

				System.out.println("Exe in showing add Fin good Stock Page " + e.getMessage());
				e.printStackTrace();
			}
		}
		return model;

	}
	
	
	List<Item> selItems=new ArrayList<>();
	//Akhlesh 2021-01-23 Get Selcted Item From Itemlist By Id
	@RequestMapping(value="/getselItemByid",method=RequestMethod.GET)
	public @ResponseBody List<Item>   getselItemByid(HttpServletRequest request,HttpServletResponse response) {
		
		System.err.println("in /getselItemByid");
		try {
			int itemId=Integer.parseInt(request.getParameter("itemIds"));
			int qty=Integer.parseInt(request.getParameter("qty"));
			System.err.println("Item Id-->"+itemId);
			for(Item item: itemList) {
				if(item.getId()==itemId) {
					Item i=new Item();
					i=item;
					i.setMinQty(qty);
					selItems.add(i);
					
					
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Exception Occuered In /getItemByid");
			e.printStackTrace();
		}
		
		return selItems;
	}
	
	
	
	@RequestMapping(value = "/getRejItemBySubCatAndCatId", method = RequestMethod.GET)
	public @ResponseBody List<Item> getRejItemBySubCatAndCatId(HttpServletRequest request, HttpServletResponse response) {
		// Constants.mainAct =1;
		// Constants.subAct =4;
		selItems.clear();
		System.err.println("In /getRejItemBySubCatAndCatId");
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			RestTemplate restTemplate = new RestTemplate();

			int catId = Integer.parseInt(request.getParameter("item_grp1"));
			int subCatId = Integer.parseInt(request.getParameter("item_grp2"));
			// CategoryListResponse

			map.add("subCatId", subCatId);
			Item[] itemsList = restTemplate.postForObject(Constants.url + "getItemsBySubCatId", map, Item[].class);

			itemList = new ArrayList<Item>(Arrays.asList(itemsList));

		} catch (Exception e) {

			System.err.println("Exce in getSubCateListByCatId Ajax " + e.getMessage());
			e.printStackTrace();

		}
		return itemList;
	}
	
	
	
	@RequestMapping(value = "/insertRejectiontionHeaderDetail", method = RequestMethod.POST)
	public String insertRejectiontionHeaderDetail(HttpServletRequest request, HttpServletResponse response) {
		List<RejectionDetail> rejDetailList=new ArrayList<>();
		RejectionHeader rejHead=new RejectionHeader();
		RestTemplate restTemplate = new RestTemplate();
		System.err.println("global item list in /insertRejectiontionHeaderDetail " + selItems.toString());
		HttpSession session=request.getSession();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String date = simpleDateFormat.format(new Date());
		
		try {
			UserResponse userResponse = (UserResponse) session.getAttribute("UserDetail");
			
			

			int catId = Integer.parseInt(request.getParameter("item_grp1"));
			System.err.println("catId" + catId);
			String fromDate = request.getParameter("fromDate");
			System.err.println("fromDate" + fromDate);
			//int remarId=Integer.parseInt(request.getParameter("remark"));
			int remarId=Integer.parseInt(request.getParameter("remarkId"));
			String remarkStr=request.getParameter("remark2");
			System.err.println("Remaks Details-->"+remarId+"\t"+remarkStr);

			String status = request.getParameter("status");

			System.out.println("statusstatusstatusstatusstatus" + status);
			for(Item items : selItems) {
				RejectionDetail detail=new RejectionDetail();
				detail.setRejDetailId(0);
				detail.setRejDetailId(0);
				detail.setDelStatus(0);
				detail.setItemId(Integer.parseInt(items.getItemId()));
				detail.setQty(items.getMinQty());
				detail.setExInt1(0);
				detail.setExInt2(0);
				rejDetailList.add(detail);
				
				
			}
			rejHead.setRejectId(0);
			rejHead.setMakerDate(date);
			rejHead.setType(Integer.parseInt(status));
			rejHead.setRemarkId(remarId);
			rejHead.setRemark(remarkStr);
			rejHead.setEmpId(userResponse.getUser().getId());
			rejHead.setEmpId(userResponse.getUser().getRoleId());
			rejHead.setExInt1(0);
			rejHead.setExInt2(0);
			rejHead.setDetailList(rejDetailList);
			
			
			System.err.println("Final Header-->"+rejHead.toString());
			
			RejectionHeader res=restTemplate.postForObject(Constants.url +"addNewRejectionHeader",rejHead,RejectionHeader.class); 
			
			if(res.getRejectId()>0) {
				System.err.println("Rejection Header Added Successfully");
				
			}else {
				System.err.println("Rejection Header Unbale To Add");
			}
			

			//Info info = restTemplate.postForObject(Constants.url + "postProductionPlanForRejRet", postProductionHeader,
			//		Info.class);
			//System.out.println(info.toString());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return "redirect:/showRejectiontionHeaderDetail";

	}
	
	
	

}
