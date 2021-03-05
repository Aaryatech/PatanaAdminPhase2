package com.ats.adminpanel.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ats.adminpanel.commons.AccessControll;
import com.ats.adminpanel.commons.Constants;
import com.ats.adminpanel.commons.VpsImageUpload;
import com.ats.adminpanel.model.ErrorMessage;
import com.ats.adminpanel.model.GetFrMenuExlPdf;
import com.ats.adminpanel.model.GetMenuIdAndType;
import com.ats.adminpanel.model.GetMenuShow;
import com.ats.adminpanel.model.MenuShow;
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
		ModelAndView mav = new ModelAndView("menu/addNewMenu");

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

			System.out.println("menumenumenumenumenumenumenu" + menu.toString());
			MenuShow errorResponse = rest.postForObject(Constants.url + "saveNewMenu", menu, MenuShow.class);
			System.out.println(errorResponse.toString());

		} catch (Exception e) {
			System.out.println("exce in msg con: " + e.getMessage());
			e.printStackTrace();

		}

		return "redirect:/addNewMenu";

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

		RestTemplate restTemplate = new RestTemplate();
		GetFrMenuExlPdf[] messageResponse = restTemplate.getForObject(Constants.url + "/getAllFrMenusList",
				GetFrMenuExlPdf[].class);

		ModelAndView mav = new ModelAndView("menu/listsMenu");

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
		System.out.println("HI");
		try {

			ModelAndView model = new ModelAndView("menu/listsMenus");

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

}
