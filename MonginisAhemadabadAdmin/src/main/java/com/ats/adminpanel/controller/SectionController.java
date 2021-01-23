package com.ats.adminpanel.controller;
//Akhlesh 202-01-20

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.adminpanel.commons.Constants;


import com.ats.adminpanel.model.ConfigureFrOldBeanListResp;
import com.ats.adminpanel.model.Info;
import com.ats.adminpanel.model.Section;
import com.ats.adminpanel.model.login.UserResponse;

@Controller
@Scope("session")
public class SectionController {
	
	
	@RequestMapping(value="/addNewSection",method=RequestMethod.GET)
	public ModelAndView addNewSection(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model=new ModelAndView("section/AddNewSection");
		Section section=new Section();
		section.setSectionId(0);
		System.err.println("in /addNewSection");
		RestTemplate restTemplate=new RestTemplate();
		ConfigureFrOldBeanListResp configFr=new ConfigureFrOldBeanListResp();
		List<Integer> menuIds=new ArrayList<>();
		try {
			configFr=restTemplate.getForObject(Constants.url+"getAllConfFr", ConfigureFrOldBeanListResp.class);
			model.addObject("frConfigList", configFr.getConfigureFrBean());
			model.addObject("sectionObj", section);
			model.addObject("menuIds", menuIds);
		} catch (Exception e) {
			
			// TODO: handle exception
			System.err.println("Exception Occuered In /addNewSection ");
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value="/addNewSectionSubmit",method=RequestMethod.POST)
	public String addNewSectionSubmit(HttpServletRequest request,HttpServletResponse response) {
		String model="redirect:/showSectionList";
		
		Section section=new Section();
		String mId="";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt=new Date();
		HttpSession session=request.getSession();
		RestTemplate restTemplate=new RestTemplate();
	try {
		UserResponse user =(UserResponse) session.getAttribute("UserDetail");
		int userId =user.getUser().getId();
		System.err.println("In /addNewSectionSubmit");
		
		
		int secId=Integer.parseInt(request.getParameter("secId"));
		String secName=request.getParameter("section_name");
		String[] secMenuIds=request.getParameterValues("section_mid");
		for(int i=0;i<secMenuIds.length;i++) {
			mId=mId+secMenuIds[i]+",";
		}
		mId=mId.substring(0, mId.length()-1);
		int secType=Integer.parseInt(request.getParameter("section_type"));;
		
		if(secId==0) {
			System.err.println("New Section");
			section.setMenuIds(mId);
			section.setSectionName(secName);
			section.setSecType(secType);
			section.setMakerDatetime(dateFormat.format(dt));
			section.setMakerUserId(userId);
			section.setDelStatus(0);
			
			Section secResp=restTemplate.postForObject(Constants.url+"addSection",section, Section.class);
			if(secResp.getSectionId()>0) {
				System.err.println("Section Added!!!");
			}else {
				System.err.println("Section Unable To Add!!!");
			}
			
		}else {
			System.err.println("Old Section");
			section.setSectionId(secId);
			section.setMenuIds(mId);
			section.setSectionName(secName);
			section.setSecType(secType);
			section.setMakerDatetime(dateFormat.format(dt));
			section.setMakerUserId(userId);
			section.setDelStatus(0);
			
			Section secResp=restTemplate.postForObject(Constants.url+"addSection",section, Section.class);
			if(secResp.getSectionId()>0) {
				System.err.println("Section Updated!!!");
			}else {
				System.err.println("Section Unable To Update!!!");
			}
		}
	
		
	} catch (Exception e) {
		// TODO: handle exception
		System.err.println("Exception Occuered In /addNewSectionSubmit");
		e.printStackTrace();
	}
		return model;
		
	}
	
	@RequestMapping(value="/showSectionList",method=RequestMethod.GET)
	public ModelAndView showSectionList() {
		System.err.println("in /showSectionList");
		ModelAndView model=new ModelAndView("section/sectionList");
		RestTemplate restTemplate=new RestTemplate();
		List<Section> sectionList=new ArrayList<>();
		
		try {
			Section[] secArr=restTemplate.getForObject(Constants.url+"getAllSection", Section[].class);
			sectionList=new ArrayList<>(Arrays.asList(secArr));
			model.addObject("secList", sectionList);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Exception Occuered in /showSectionList");
			e.printStackTrace();
		}
		return model;
	}
	
	
	
	
	@RequestMapping(value="/updateSection",method=RequestMethod.GET)
	public ModelAndView updateSection(HttpServletRequest request,HttpServletResponse response) {
		System.err.println("in /updateSection");
		ModelAndView model=new ModelAndView("section/AddNewSection");
	
		RestTemplate restTemplate=new RestTemplate();
		Section section=new Section();
		MultiValueMap<String, Object> map=new LinkedMultiValueMap<>();
		ConfigureFrOldBeanListResp configFr=new ConfigureFrOldBeanListResp();
		try {
			int secId=Integer.parseInt(request.getParameter("secId"));
			map.add("sectionId", secId);
			section=restTemplate.postForObject(Constants.url+"getSingleSection", map, Section.class);
			String mId=section.getMenuIds();
			String[] menuId=mId.split(",");
			
			List<Integer> menuIds=new ArrayList<>();
			for(int i=0;i<menuId.length;i++) {
			 menuIds.add(Integer.parseInt(menuId[i]));		
			}
		
			configFr=restTemplate.getForObject(Constants.url+"getAllConfFr", ConfigureFrOldBeanListResp.class);
			model.addObject("frConfigList", configFr.getConfigureFrBean());
			model.addObject("sectionObj", section);
			model.addObject("menuIds", menuIds);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Exception Occuered in /showSectionList");
			e.printStackTrace();
		}
		return model;
	}
	
	
	@RequestMapping(value="/deleteSection",method=RequestMethod.GET)
	public String deleteSection(HttpServletRequest request,HttpServletResponse response) {
		System.err.println("in /deleteSection");
		String model="redirect:/showSectionList";
		RestTemplate restTemplate=new RestTemplate();
		Info info=new Info();
		MultiValueMap<String,Object> map=new LinkedMultiValueMap<>();
		try {
			int secId=Integer.parseInt(request.getParameter("secId"));
			
			map.add("sectionId",secId);
			info=restTemplate.postForObject(Constants.url+"deleteSection", map, Info.class);
			if(!info.getError()) {
				System.err.println("Section Deleted!!!");
			}else {
				System.err.println("Unable To  Delete Section !!!");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Exception Occuered in /showSectionList");
			e.printStackTrace();
		}
		return model;
	}
	
	
	
	

}
