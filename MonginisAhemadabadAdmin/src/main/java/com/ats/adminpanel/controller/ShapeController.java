package com.ats.adminpanel.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
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
import com.ats.adminpanel.model.Info;
import com.ats.adminpanel.model.RouteMaster;
import com.ats.adminpanel.model.Shape;
import com.ats.adminpanel.model.SpecialCake;
import com.ats.adminpanel.model.accessright.ModuleJson;


@Controller
@Scope("session")
public class ShapeController {
	
	
	@RequestMapping(value = "/addShape", method = RequestMethod.GET)
	public ModelAndView addShape(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		HttpSession session = request.getSession();

//		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
//		Info view = AccessControll.checkAccess("addShape", "addShape", "1", "0", "0", "0",
//				newModuleList);
//
//		if (false)//view.getError() == true
//			{
//
//			model = new ModelAndView("accessDenied");
//
//		} else {
			model = new ModelAndView("masters/shape");
			System.out.println("shape disp");
			Shape respShape=new Shape();
		
			RestTemplate restTemplate = new RestTemplate();
			try {

				Shape[] AllShapeArr = restTemplate.getForObject(Constants.url + "/getAllChef",
						Shape[].class);

				List<Shape> shapeList = new ArrayList<Shape>(Arrays.asList(AllShapeArr));
				model.addObject("shapeFlag", 0);
				model.addObject("shapeList", shapeList);
				model.addObject("shape", respShape);
			} catch (Exception e) {
				System.out.println("Error in Shape list display" + e.getMessage());
				e.printStackTrace();
			}
//		}
		return model;
	}
	
	
	@RequestMapping(value = "/updateShape/{shapeId}")
	public ModelAndView updateShape(@PathVariable int shapeId, HttpServletRequest request,
			HttpServletResponse response) {
		Shape respShape=new Shape();
		ModelAndView model = new ModelAndView("masters/shape");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("shapeId", shapeId);
		RestTemplate restTemplate = new RestTemplate();
		try {
		respShape =restTemplate.postForObject(Constants.url+"getAllChefById", map, Shape.class);	
			
		model.addObject("shape", respShape);
		
		Shape[] AllShapeArr = restTemplate.getForObject(Constants.url + "/getAllChef",
				Shape[].class);

		List<Shape> shapeList = new ArrayList<Shape>(Arrays.asList(AllShapeArr));

		model.addObject("shapeList", shapeList);
		
		model.addObject("shapeFlag", 0);
		} catch (Exception e) {
			System.out.println("Exception In updateShape:" + e.getMessage());
		}

	return model;
	}
	
	
	//addShapeProcess
	
	@RequestMapping(value = "/addShapeProcess", method = RequestMethod.POST)
	public String addShapeProcess(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("masters/shape");
		RestTemplate rest = new RestTemplate();

		try {
			
			int shapeId = Integer.parseInt(request.getParameter("shape_id"));
			String shapeName = request.getParameter("shape_name");
			String shapeDesc = request.getParameter("shape_desc");
			Shape saveShape=new Shape();
			
			saveShape.setShapeId(shapeId);
			saveShape.setShapeName(shapeName);
			saveShape.setShapeDesc(shapeDesc);
			saveShape.setExtInt1(0);
			saveShape.setExtInt2(0);
			saveShape.setDelStatus(0);
			
			
			Shape shapeResponse = rest.postForObject(Constants.url + "/saveItemChef", saveShape, Shape.class);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("Exception In /String routeName = request.getParameter(\"route_name\");");
		}
		
		

		

		
		return "redirect:/addShape";
	}
	
	
	
	@RequestMapping(value = "/deleteShape/{shapeId}")
	public String deleteShape(@PathVariable String shapeId, HttpServletRequest request,
			HttpServletResponse response) {
		Shape respShape=new Shape();
		ModelAndView model = new ModelAndView("masters/shape");
		Info info=new Info();
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("shapeId", shapeId);
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			
	SpecialCake[] spcakes =restTemplate.postForObject(Constants.url+"getSpCakeByShapeId", map, SpecialCake[].class);
	
	List<SpecialCake> spList=new ArrayList<>(Arrays.asList(spcakes));	
	System.err.println("Shape Search Resp-->"+spList.toString());
	if(spList.size()==0) {
info =restTemplate.postForObject(Constants.url+"deleteShape", map, Info.class);	
			
			if(!info.getError()) {
				System.err.println("Shape Deleted");
			}else {
				System.err.println("Unable To Delete Shape");
			}
			model.addObject("shapeFlag", 0);
		}else {
			System.err.println("Shape Is In Use Cant Delete Shape");
			model.addObject("shapeFlag", 1);
			
		}
			
			
	
		model.addObject("shape", respShape);
		
		Shape[] AllShapeArr = restTemplate.getForObject(Constants.url + "/getAllChef",
				Shape[].class);

		List<Shape> shapeList = new ArrayList<Shape>(Arrays.asList(AllShapeArr));

		model.addObject("shapeList", shapeList);
		
		
		} catch (Exception e) {
			System.out.println("Exception In updateShape:" + e.getMessage());
		}

	return "redirect:/addShape";
	}
	
	

	
	
	
	
	

}
