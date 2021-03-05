package com.ats.adminpanel.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.adminpanel.commons.AccessControll;
import com.ats.adminpanel.commons.Constants;
import com.ats.adminpanel.commons.VpsImageUpload;
import com.ats.adminpanel.model.ErrorMessage;
import com.ats.adminpanel.model.ExportToExcel;
import com.ats.adminpanel.model.GetFrMenuExlPdf;
import com.ats.adminpanel.model.GetMenuIdAndType;
import com.ats.adminpanel.model.GetMenuShow;
import com.ats.adminpanel.model.MenuShow;
import com.ats.adminpanel.model.RouteAbcVal;
import com.ats.adminpanel.model.RouteMaster;
import com.ats.adminpanel.model.ShowFrMenuConfExlPdf;
import com.ats.adminpanel.model.accessright.ModuleJson;
import com.ats.adminpanel.model.item.CategoryListResponse;
import com.ats.adminpanel.model.item.MCategoryList;

@Controller
public class MenuController {

	List<MCategoryList> mCategoryList;
	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping(value = "/addNewMenu", method = RequestMethod.GET)
	public ModelAndView addNewMenu(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();

		ModelAndView mav = new ModelAndView("menu/addNewMenu");
		// ModelAndView model = new ModelAndView("orders/orders");
		Constants.mainAct = 1;
		Constants.subAct = 119;

		CategoryListResponse categoryListResponse;

		categoryListResponse = restTemplate.getForObject(Constants.url + "showAllCategory", CategoryListResponse.class);

		mCategoryList = categoryListResponse.getmCategoryList();

		mav.addObject("catList", mCategoryList);

		return mav;
	}

	@RequestMapping(value = "/addMenuShowProcess", method = RequestMethod.POST)
	public String addMenuShowProcess(HttpServletRequest request, HttpServletResponse response) {
		//@RequestParam("photo1") List<MultipartFile> file1, @RequestParam("photo2") List<MultipartFile> file2
		
		try {
			String menuTitle = request.getParameter("menuTitle");
			String menuDesc = request.getParameter("menuDesc");

			int isSameDayApplicable = Integer.parseInt(request.getParameter("isSameDayAppicable"));
			int catId = Integer.parseInt(request.getParameter("catId"));

		//	VpsImageUpload upload = new VpsImageUpload();

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			System.out.println(sdf.format(cal.getTime()));
			long lo = cal.getTimeInMillis();
			System.out.println(sdf.format(cal.getTime()));

			// msgImage = String.valueOf(lo);

			String curTimeStamp = String.valueOf(lo);

//			try {
//
//				upload.saveUploadedFiles(file1, Constants.MENU_IMAGE_TYPE,
//						curTimeStamp + "-" + file1.get(0).getOriginalFilename().replace(' ', '_'));
//				System.out.println("upload method called " + file1.toString());
//
//			} catch (IOException e) {
//
//				System.out.println("Exce in File Upload In Item Insert " + e.getMessage());
//				e.printStackTrace();
//			}
//
//			try {
//
//				upload.saveUploadedFiles(file2, Constants.MENU_IMAGE_TYPE,
//						curTimeStamp + "-" + file2.get(0).getOriginalFilename().replace(' ', '_'));
//				System.out.println("upload method called " + file2.toString());
//
//			} catch (IOException e) {
//
//				System.out.println("Exce in File Upload In Item Insert " + e.getMessage());
//				e.printStackTrace();
//			}

			RestTemplate rest = new RestTemplate();
			String menuImage = null;
			String selMenuImage = null;

			MenuShow menu = new MenuShow();
			
			if(catId==1) {
				menuImage = "icon1.png";
				selMenuImage = "icon1-h.png";
			}else if(catId==2) {
				menuImage = "icon2.png";
				selMenuImage = "icon2-h.png";
			}else if(catId==3) {
				menuImage = "icon3.png";
				selMenuImage = "icon3-h.png";
			}else if(catId==4) {
				menuImage = "icon4.png";
				selMenuImage = "icon4-h.png";
			}else if(catId==5) {
				menuImage = "icon5.png";
				selMenuImage = "icon5-h.png";
			}else if(catId==6) {
				menuImage = "icon6.png";
				selMenuImage = "icon6-h.png";
			}else {
				menuImage = "icon7.png";
				selMenuImage = "icon7-h.png";
			}

			menu.setCatId(catId);
			menu.setDelStatus(0);
			menu.setIsSameDayApplicable(isSameDayApplicable);
			menu.setMenuDesc(menuDesc);
			menu.setMenuTitle(menuTitle);
			menu.setMenuImage(menuImage);
			menu.setSelectedMenuImage(selMenuImage);
			
			MenuShow errorResponse = rest.postForObject(Constants.url + "saveNewMenu", menu, MenuShow.class);
			System.out.println(errorResponse.toString());

		} catch (Exception e) {
			System.out.println("exce in msg con: " + e.getMessage());
			e.printStackTrace();

		}

		return "redirect:/showMenus";

	}

	@RequestMapping(value = "/deleteMenuShow/{menuId}", method = RequestMethod.GET)
	public String deleteMenuShow(@PathVariable int menuId) {

		ModelAndView mav = new ModelAndView("menu/listsMenu");

		RestTemplate rest = new RestTemplate();
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("menuId", menuId);

		ErrorMessage errorResponse = rest.postForObject(Constants.url + "deleteMenuShow", map, ErrorMessage.class);
		System.out.println(errorResponse.toString());

		if (errorResponse.isError()) {
			return "redirect:/showMenus";

		} else {
			return "redirect:/showMenus";

		}
	}

	@RequestMapping(value = "/showMenus", method = RequestMethod.GET)
	public ModelAndView showMenus(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Show Message Request");
		Constants.mainAct = 1;
		Constants.subAct = 119;
		ModelAndView mav = new ModelAndView("menu/listsMenu");
		
		RestTemplate restTemplate = new RestTemplate();
		
		GetFrMenuExlPdf[] messageResponse = restTemplate.getForObject(Constants.url + "/getAllFrMenusList",
				GetFrMenuExlPdf[].class);		

		List<GetFrMenuExlPdf> mesnuShowList = new ArrayList<GetFrMenuExlPdf>(Arrays.asList(messageResponse));

		mav.addObject("mesnuShowList", mesnuShowList);
		
		GetMenuIdAndType[] menuIdArr = restTemplate.getForObject(Constants.url + "/getAllSavedMenuIds",
				GetMenuIdAndType[].class);
		List<GetMenuIdAndType> menuIds = new ArrayList<GetMenuIdAndType>(Arrays.asList(menuIdArr));
		mav.addObject("menuIds", menuIds);
		System.out.println("List Of MenuIds:" + menuIds);

		mav.addObject("url", Constants.MENU_IMAGE_URL);
		return mav;
	}

	MenuShow menu = null;

	@RequestMapping(value = "/updateMenuShow/{menuId}", method = RequestMethod.GET)
	public ModelAndView updateMenuShow(@PathVariable int menuId) {
		ModelAndView mav = new ModelAndView("menu/editMenu");
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("menuId", menuId);
		menu = restTemplate.postForObject(Constants.url + "getFrMenuById", map, MenuShow.class, menuId);

		mav.addObject("menu", menu);

		int intisActive = menu.getIsSameDayApplicable();
		String isActive = String.valueOf(intisActive);

		CategoryListResponse categoryListResponse;

		categoryListResponse = restTemplate.getForObject(Constants.url + "showAllCategory", CategoryListResponse.class);

		mCategoryList = categoryListResponse.getmCategoryList();

		mav.addObject("catList", mCategoryList);

		mav.addObject("isActive", isActive);
		mav.addObject("url", Constants.MESSAGE_IMAGE_URL);

		return mav;

	}

	@RequestMapping(value = "/updateMenuShow/updateMenuProcess", method = RequestMethod.POST)

	public String updateAlbum(HttpServletRequest request, HttpServletResponse response) {
		//@RequestParam("photo1") List<MultipartFile> file1, @RequestParam("photo2") List<MultipartFile> file2
		
		try {

			RestTemplate restTemplate = new RestTemplate();

			String menuTitle = request.getParameter("menuTitle");
			String menuDesc = request.getParameter("menuDesc");

			int isSameDayApplicable = Integer.parseInt(request.getParameter("isSameDayAppicable"));
			int catId = Integer.parseInt(request.getParameter("catId"));
			int menuId = Integer.parseInt(request.getParameter("menuId"));

			String photo1 = request.getParameter("photo1");
			String photo2 = request.getParameter("photo2");

//			if (!file1.get(0).getOriginalFilename().equalsIgnoreCase("")) {
//
//				System.out.println("Empty image");
//				// msgImage= ImageS3Util.uploadMessageImage(file);
//
//				VpsImageUpload upload = new VpsImageUpload();
//
//				Calendar cal = Calendar.getInstance();
//				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//
//				long lo = cal.getTimeInMillis();
//				System.out.println(sdf.format(cal.getTime()));
//
//				photo1 = String.valueOf(lo);
//
//				try {
//
//					upload.saveUploadedFiles(file1, Constants.MENU_IMAGE_TYPE,
//							photo1 + "-" + file1.get(0).getOriginalFilename().replace(' ', '_'));
//					System.out.println("upload method called " + file1.toString());
//
//				} catch (IOException e) {
//
//					System.out.println("Exce in File Upload In Item Insert " + e.getMessage());
//					e.printStackTrace();
//				}
//			}
//
//			if (!file2.get(0).getOriginalFilename().equalsIgnoreCase("")) {
//
//				System.out.println("Empty image");
//				// msgImage= ImageS3Util.uploadMessageImage(file);
//
//				VpsImageUpload upload = new VpsImageUpload();
//
//				Calendar cal = Calendar.getInstance();
//				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//
//				long lo = cal.getTimeInMillis();
//				System.out.println(sdf.format(cal.getTime()));
//
//				photo2 = String.valueOf(lo);
//
//				try {
//
//					upload.saveUploadedFiles(file2, Constants.MENU_IMAGE_TYPE,
//							photo2 + "-" + file2.get(0).getOriginalFilename().replace(' ', '_'));
//					System.out.println("upload method called " + file1.toString());
//
//				} catch (IOException e) {
//
//					System.out.println("Exce in File Upload In Item Insert " + e.getMessage());
//					e.printStackTrace();
//				}
//			}
			String menuImage = null;
			String selMenuImage = null;
			
			if(catId==1) {
				menuImage = "icon1.png";
				selMenuImage = "icon1-h.png";
			}else if(catId==2) {
				menuImage = "icon2.png";
				selMenuImage = "icon2-h.png";
			}else if(catId==3) {
				menuImage = "icon3.png";
				selMenuImage = "icon3-h.png";
			}else if(catId==4) {
				menuImage = "icon4.png";
				selMenuImage = "icon4-h.png";
			}else if(catId==5) {
				menuImage = "icon5.png";
				selMenuImage = "icon5-h.png";
			}else if(catId==6) {
				menuImage = "icon6.png";
				selMenuImage = "icon6-h.png";
			}else {
				menuImage = "icon7.png";
				selMenuImage = "icon7-h.png";
			}


			menu.setCatId(catId);
			menu.setDelStatus(0);
			menu.setIsSameDayApplicable(isSameDayApplicable);
			menu.setMenuId(menuId);
			menu.setMenuImage(menuImage);
			menu.setSelectedMenuImage(selMenuImage);
			menu.setMenuTitle(menuTitle);

			menu.setMenuDesc(menuDesc);

			System.out.println("menu" + menu.toString());
			MenuShow errorResponse = restTemplate.postForObject(Constants.url + "saveNewMenu", menu, MenuShow.class);
			System.out.println(errorResponse.toString());

		} catch (Exception e) {

			System.out.println("error in msg update" + e.getMessage());
		}
		return "redirect:/showMenus";

	}
	
	
	
	@RequestMapping(value = "/getFrMenuPrintIds", method = RequestMethod.GET)
	public @ResponseBody List<GetFrMenuExlPdf> getCompanyPrintIds(HttpServletRequest request,
			HttpServletResponse response) {
		List<GetFrMenuExlPdf> menuList = new ArrayList<GetFrMenuExlPdf>();
		List<Long> menuIds = new ArrayList<Long>();
		
		try {
			HttpSession session = request.getSession();		
					
			String selctId = request.getParameter("elemntIds");

			selctId = selctId.substring(1, selctId.length() - 1);
			selctId = selctId.replaceAll("\"", "");
			
			RestTemplate restTemplate = new RestTemplate();
			
			GetFrMenuExlPdf[] messageResponse = restTemplate.getForObject(Constants.url + "/getAllFrMenusList",
					GetFrMenuExlPdf[].class);		

			menuList = new ArrayList<GetFrMenuExlPdf>(Arrays.asList(messageResponse));


			menuIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			
			
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr No.");
			for (int i = 0; i < menuIds.size(); i++) {
								
				if(menuIds.get(i)==1)
				rowData.add("Menu");
				
				if(menuIds.get(i)==2)
				rowData.add("Description");
				
				if(menuIds.get(i)==3)
				rowData.add("Category");
				
				if(menuIds.get(i)==4)
				rowData.add("Type");
				
			}
			expoExcel.setRowData(rowData);
			
			exportToExcelList.add(expoExcel);
			int srno = 1;
			
			for (int i = 0; i < menuList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				
				rowData.add(" "+srno);
				for (int j = 0; j < menuIds.size(); j++) {		
					
					
					if(menuIds.get(j)==1)
					rowData.add(" " + menuList.get(i).getMenuTitle());
					
					if(menuIds.get(j)==2)
					rowData.add(" " + menuList.get(i).getMenuDesc());
					
					if(menuIds.get(j)==3)
					rowData.add(" " + menuList.get(i).getCatName());
					
					if(menuIds.get(j)==4)
					rowData.add(menuList.get(i).getIsSameDayApplicable()==0 ? "Regular" :
						menuList.get(i).getIsSameDayApplicable()==1 ? "Same Day Regular" :
							menuList.get(i).getIsSameDayApplicable()==2 ? "Regular with limit" :
								menuList.get(i).getIsSameDayApplicable()==3 ? "Regular cake As SP Order" : "Delivery And Production Date");			
					
				}
				srno = srno + 1;
				
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "Franchise Menu List");
			session.setAttribute("reportNameNew", "Franchise Menu  List");
			session.setAttribute("", "");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");
			session.setAttribute("excelName", "Franchise Menu  Excel");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuList;
	}
	
	
	@RequestMapping(value = "pdf/getFrMenuListPdf/{selctId}", method = RequestMethod.GET)
	public ModelAndView getCompanyListPdf(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String selctId) {
		ModelAndView model = new ModelAndView("masters/masterPdf/frMenuPdf");
		List<Long> menuIds = new ArrayList<Long>();
		try {
			
			RestTemplate restTemplate = new RestTemplate();
			
			GetFrMenuExlPdf[] messageResponse = restTemplate.getForObject(Constants.url + "/getAllFrMenusList",
					GetFrMenuExlPdf[].class);		

			List<GetFrMenuExlPdf> menuList = new ArrayList<GetFrMenuExlPdf>(Arrays.asList(messageResponse));


			menuIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			
			
			model.addObject("menuList", menuList);
			model.addObject("menuIds", menuIds);
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model;
		
	}
	
	//---------------------------------------------------------------------
	@RequestMapping(value = "/showMenusFrConfiguratnList", method = RequestMethod.GET)
	public ModelAndView showMenusFrConfiguratnList(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("menu/frMenuConfigratnList");
		
		RestTemplate restTemplate = new RestTemplate();
		
		ShowFrMenuConfExlPdf[] messageResponse = restTemplate.getForObject(Constants.url + "/getFrMenuCogigDetails",
				ShowFrMenuConfExlPdf[].class);		

		List<ShowFrMenuConfExlPdf> mesnuShowList = new ArrayList<ShowFrMenuConfExlPdf>(Arrays.asList(messageResponse));

		mav.addObject("mesnuShowList", mesnuShowList);
		return mav;
	}

	@RequestMapping(value = "/getFrMenuConfigPrintIds", method = RequestMethod.GET)
	public @ResponseBody List<RouteMaster> getFrMenuConfigPrintIds(HttpServletRequest request,
			HttpServletResponse response) {
		List<RouteMaster> printRouteList = new ArrayList<RouteMaster>();
		List<Long> menuIds = new ArrayList<Long>();
		
		try {
			HttpSession session = request.getSession();		
					
			String selctId = request.getParameter("elemntIds");

			selctId = selctId.substring(1, selctId.length() - 1);
			selctId = selctId.replaceAll("\"", "");
			
			RestTemplate restTemplate = new RestTemplate();
			
			ShowFrMenuConfExlPdf[] messageResponse = restTemplate.getForObject(Constants.url + "/getFrMenuCogigDetails",
					ShowFrMenuConfExlPdf[].class);		

			List<ShowFrMenuConfExlPdf> menuList = new ArrayList<ShowFrMenuConfExlPdf>(Arrays.asList(messageResponse));


			menuIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			
			
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr No.");
			for (int i = 0; i < menuIds.size(); i++) {
								
				if(menuIds.get(i)==1)
					rowData.add("Menu");
				
				if(menuIds.get(i)==2)
					rowData.add("Category");
				
				if(menuIds.get(i)==3)
					rowData.add("Type");
				
				if(menuIds.get(i)==4)
					rowData.add("Profit%");
				
				if(menuIds.get(i)==5)
					rowData.add("GRN%");
				
				if(menuIds.get(i)==6)
					rowData.add("Discount%");
				
			}
			expoExcel.setRowData(rowData);
			
			exportToExcelList.add(expoExcel);
			int srno = 1;
			
			for (int i = 0; i < menuList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				
				rowData.add(" "+srno);
				for (int j = 0; j < menuIds.size(); j++) {		
					
					
					if(menuIds.get(j)==1)
					rowData.add(" " + menuList.get(i).getMenuTitle());
					
					if(menuIds.get(j)==2)
					rowData.add(" " + menuList.get(i).getCatName());					
					
					if(menuIds.get(j)==3)
					rowData.add(menuList.get(i).getType()==0 ? "Regular" :
						menuList.get(i).getType()==1 ? "Same Day Regular" :
							menuList.get(i).getType()==2 ? "Regular with limit" :
								menuList.get(i).getType()==3 ? "Regular cake As SP Order" : "Delivery And Production Date");
					
					if(menuIds.get(j)==4)
						rowData.add(" " + menuList.get(i).getProfitPer());		
					
					if(menuIds.get(j)==5)
						rowData.add(" " + menuList.get(i).getGrnPer());		
					
					if(menuIds.get(j)==6)
						rowData.add(" " + menuList.get(i).getDiscPer());		
					
				}
				srno = srno + 1;
				
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "Franchise Menu Configuration List");
			session.setAttribute("reportNameNew", "Franchise Menu Configuration List");
			session.setAttribute("", "");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");
			session.setAttribute("excelName", "Franchise Menu Configuration List Excel");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return printRouteList;
	}
	
	@RequestMapping(value = "pdf/getFrMenuConfigListPdf/{selctId}", method = RequestMethod.GET)
	public ModelAndView getFrMenuConfigListPdf(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String selctId) {
		ModelAndView model = new ModelAndView("masters/masterPdf/frMenuConfigPdf");
		List<Long> menuIds = new ArrayList<Long>();
		try {
			
			RestTemplate restTemplate = new RestTemplate();
			
			ShowFrMenuConfExlPdf[] messageResponse = restTemplate.getForObject(Constants.url + "/getFrMenuCogigDetails",
					ShowFrMenuConfExlPdf[].class);		

			List<ShowFrMenuConfExlPdf> menuList = new ArrayList<ShowFrMenuConfExlPdf>(Arrays.asList(messageResponse));

			menuIds =  Stream.of(selctId.split(","))
			        .map(Long::parseLong)
			        .collect(Collectors.toList());
			
			
			model.addObject("menuList", menuList);
			model.addObject("menuIds", menuIds);
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		return model;
		
	}
	
}
