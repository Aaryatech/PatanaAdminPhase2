package com.ats.adminpanel.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
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
import com.ats.adminpanel.commons.DateConvertor;
import com.ats.adminpanel.model.AllFrIdName;
import com.ats.adminpanel.model.AllFrIdNameList;
import com.ats.adminpanel.model.AllRoutesListResponse;
import com.ats.adminpanel.model.CreditNoteReport;
import com.ats.adminpanel.model.ExportToExcel;
import com.ats.adminpanel.model.Info;
import com.ats.adminpanel.model.Route;
import com.ats.adminpanel.model.SalesVoucherList;
import com.ats.adminpanel.model.Setting;
import com.ats.adminpanel.model.accessright.ModuleJson;
import com.ats.adminpanel.model.billing.FrBillHeaderForPrint;
import com.ats.adminpanel.model.franchisee.FrNameIdByRouteId;
import com.ats.adminpanel.model.franchisee.FrNameIdByRouteIdResponse;
import com.ats.adminpanel.model.ggreports.GGReportByDateAndFr;
import com.ats.adminpanel.model.ggreports.GGReportGrpByFrId;
import com.ats.adminpanel.model.ggreports.GGReportGrpByItemId;
import com.ats.adminpanel.model.ggreports.GGReportGrpByMonthDate;
import com.ats.adminpanel.model.ggreports.PendingGrnGvnItemWise;
import com.ats.adminpanel.model.ggreports.PendingItemGrnGvn;
import com.ats.adminpanel.model.item.AllItemsListResponse;
import com.ats.adminpanel.model.item.CategoryListResponse;
import com.ats.adminpanel.model.item.Item;
import com.ats.adminpanel.model.item.MCategoryList;
import com.ats.adminpanel.model.mastexcel.TallyItem;
import com.ats.adminpanel.model.salesreport.SalesReportBillwise;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Phrase;

@Controller
@Scope("session")
public class GrnGvnReportController {

	public AllFrIdNameList allFrIdNameList = new AllFrIdNameList();

	public List<Route> routeList = new ArrayList<Route>();
	AllRoutesListResponse allRouteListResponse = new AllRoutesListResponse();

	AllFrIdNameList getFrNameId() {

		RestTemplate restTemplate = new RestTemplate();
		return allFrIdNameList = restTemplate.getForObject(Constants.url + "getAllFrIdName", AllFrIdNameList.class);
	}

	AllRoutesListResponse getAllRoute() {

		RestTemplate restTemplate = new RestTemplate();

		return allRouteListResponse = restTemplate.getForObject(Constants.url + "showRouteList",
				AllRoutesListResponse.class);

	}

	// r1
	@RequestMapping(value = "/showGGReportDateWise", method = RequestMethod.GET)
	public ModelAndView showGGReportDateWise(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showGGReportDateWise", "showGGReportDateWise", "1", "0", "0", "0",
				newModuleList);

		if (view.getError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("reports/grnGvn/ggByDate");

			try {

				ZoneId z = ZoneId.of("Asia/Calcutta");

				LocalDate date = LocalDate.now(z);
				DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-uuuu");
				String todaysDate = date.format(formatters);

				allFrIdNameList = getFrNameId();

				allRouteListResponse = getAllRoute();

				model.addObject("routeList", allRouteListResponse.getRoute());
				model.addObject("todaysDate", todaysDate);
				model.addObject("unSelectedFrList", allFrIdNameList.getFrIdNamesList());

			} catch (Exception e) {
				System.out.println("Exce inshowGGReportDateWise " + e.getMessage());
				e.printStackTrace();
			}
		}
		return model;
	}
	// consume R1 web Service

	@RequestMapping(value = "/getGrnGvnByDatewise", method = RequestMethod.GET)
	@ResponseBody
	public List<GGReportByDateAndFr> getGrnGvnByDatewise(HttpServletRequest request, HttpServletResponse response) {

		List<GGReportByDateAndFr> grnGvnByDateList = new ArrayList<>();

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			RestTemplate restTemplate = new RestTemplate();

			String routeId = "0";
			String frIdString = "";

			System.out.println("inside getGrnGvnByDatewise ajax call");

			frIdString = request.getParameter("fr_id_list");
			String fromDate = request.getParameter("from_date");
			String toDate = request.getParameter("to_date");
			routeId = request.getParameter("route_id");

			String isGrn = request.getParameter("is_grn");
			String grnType;
			if (isGrn.equalsIgnoreCase("2")) {

				System.err.println("Is Grn =2");
				grnType = "1" + "," + "0";

				map.add("isGrn", grnType);
			} else {
				System.err.println("Is Grn not =2");
				grnType = isGrn;
				map.add("isGrn", isGrn);

			}

			System.out.println("fromDate= " + fromDate);

			boolean isAllFrSelected = false;

			frIdString = frIdString.substring(1, frIdString.length() - 1);
			frIdString = frIdString.replaceAll("\"", "");

			List<String> franchIds = new ArrayList();
			franchIds = Arrays.asList(frIdString);

			System.out.println("fr Id ArrayList " + franchIds.toString());

			if (franchIds.contains("-1")) {
				isAllFrSelected = true;

			}

			if (!routeId.equalsIgnoreCase("0")) {

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

			map = new LinkedMultiValueMap<String, Object>();
			if (isAllFrSelected) {

				System.out.println("Inside IF  is All fr Selected " + isAllFrSelected);

				map.add("frIdList", 0);
				// model.addObject("billHeadersList",billHeadersListForPrint);

			} else { // few franchisee selected

				System.out.println("Inside Else: Few Fr Selected ");
				map.add("frIdList", frIdString);

			}

			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("isGrn", grnType);

			ParameterizedTypeReference<List<GGReportByDateAndFr>> typeRef = new ParameterizedTypeReference<List<GGReportByDateAndFr>>() {
			};
			ResponseEntity<List<GGReportByDateAndFr>> responseEntity = restTemplate
					.exchange(Constants.url + "getgGReportByDate", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			grnGvnByDateList = responseEntity.getBody();

			System.err.println("List " + grnGvnByDateList.toString());

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No.");
			rowData.add("Date");
			rowData.add("Type");
			rowData.add("GrnGvn SrNo");
			rowData.add("Franchise Name");
			rowData.add("Req Qty");
			rowData.add("Req Value");
			rowData.add("Apr Qty");
			rowData.add("Apr Value");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			List<GGReportByDateAndFr> excelItems = grnGvnByDateList;
			for (int i = 0; i < excelItems.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				rowData.add("" + (i + 1));
				rowData.add("" + excelItems.get(i).getGrngvnDate());

				String type;
				if (excelItems.get(i).getIsGrn() == 1) {
					type = "GRN";

				} else {
					type = "GVN";
				}
				rowData.add(type);
				rowData.add(excelItems.get(i).getGrngvnSrno());
				rowData.add(excelItems.get(i).getFrName());
				rowData.add("" + excelItems.get(i).getReqQty());
				rowData.add("" + excelItems.get(i).getTotalAmt());
				rowData.add("" + excelItems.get(i).getAprQty());

				rowData.add("" + excelItems.get(i).getAprGrandTotal());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "grnGvnReport");

		} catch (Exception e) {

			System.out.println("Ex in getting /getgGReportByDate List  Ajax call" + e.getMessage());
			e.printStackTrace();
		}

		return grnGvnByDateList;

	}
	// showGGreportByDate PDF

	@RequestMapping(value = "pdf/showGGreportByDate/{fDate}/{tDate}/{selectedFr}/{routeId}/{isGrn}", method = RequestMethod.GET)
	public ModelAndView showGGreportByDatePdf(@PathVariable String fDate, @PathVariable String tDate,
			@PathVariable String selectedFr, @PathVariable String routeId, @PathVariable int isGrn,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("reports/grnGvn/pdf/r1");

		List<GGReportByDateAndFr> grnGvnByDateList = new ArrayList<>();

		System.err.println("Inside PDF mapping");

		boolean isAllFrSelected = false;

		try {

			if (!routeId.equalsIgnoreCase("0")) {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

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
				selectedFr = strFrIdRouteWise.substring(0, strFrIdRouteWise.length() - 1);
				System.out.println("fr Id Route WISE = " + selectedFr);

			} // end of if

			if (selectedFr.equalsIgnoreCase("-1")) {
				isAllFrSelected = true;
			}

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			RestTemplate restTemplate = new RestTemplate();

			if (isAllFrSelected) {

				System.out.println("Inside If all fr Selected ");
				map.add("frIdList", 0);

			} else {
				System.out.println("Inside else Few fr Selected ");

				map.add("frIdList", selectedFr);

			}

			if (isGrn == 2) {
				System.err.println("Is Grn ==2");

				map.add("isGrn", "1" + "," + "0");
			} else {
				System.err.println("Is Grn  not Eq 2");

				map.add("isGrn", isGrn);

			}

			map.add("fromDate", DateConvertor.convertToYMD(fDate));
			map.add("toDate", DateConvertor.convertToYMD(tDate));
			// map.add("isGrn", isGrn);

			ParameterizedTypeReference<List<GGReportByDateAndFr>> typeRef = new ParameterizedTypeReference<List<GGReportByDateAndFr>>() {
			};
			ResponseEntity<List<GGReportByDateAndFr>> responseEntity = restTemplate
					.exchange(Constants.url + "getgGReportByDate", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			grnGvnByDateList = responseEntity.getBody();

			System.err.println("List---- " + grnGvnByDateList.toString());

		} catch (Exception e) {
			System.err.println("Exc in GRN PDF report 2");
			e.printStackTrace();
		}

		model.addObject("fromDate", fDate);

		model.addObject("toDate", tDate);

		model.addObject("report", grnGvnByDateList);
		
		Setting showHead = SalesReportController.isHeadAllow();
		if(showHead.getSettingValue()==1) {
			model.addObject("FACTORYNAME", Constants.FACTORYNAME);
			model.addObject("FACTORYADDRESS", Constants.FACTORYADDRESS);
		}

		return model;
	}

	// r2
	@RequestMapping(value = "/showGGReportGrpByFr", method = RequestMethod.GET)
	public ModelAndView showGGReportGrpByFr(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showAddNewFranchisee", "showAddNewFranchisee", "1", "0", "0", "0",
				newModuleList);

		if (view.getError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("reports/grnGvn/ggGrpByFr");

			try {

				ZoneId z = ZoneId.of("Asia/Calcutta");

				LocalDate date = LocalDate.now(z);
				DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-uuuu");
				String todaysDate = date.format(formatters);

				allFrIdNameList = getFrNameId();

				allRouteListResponse = getAllRoute();

				model.addObject("routeList", allRouteListResponse.getRoute());
				model.addObject("todaysDate", todaysDate);
				model.addObject("unSelectedFrList", allFrIdNameList.getFrIdNamesList());

			} catch (Exception e) {
				System.out.println("Exce inshowGGReportDateWise " + e.getMessage());
				e.printStackTrace();
			}
		}

		return model;
	}

	// consume r2 web service Ajax call
	@RequestMapping(value = "/getGrnGvnByGrpByFr", method = RequestMethod.GET)
	@ResponseBody
	public List<GGReportGrpByFrId> getGrnGvnByGrpByFr(HttpServletRequest request, HttpServletResponse response) {

		List<GGReportGrpByFrId> grnGvnGrpByFrList = new ArrayList<>();

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			RestTemplate restTemplate = new RestTemplate();

			String routeId = "0";
			String frIdString = "";

			System.out.println("inside getGrnGvnByDatewise ajax call");

			frIdString = request.getParameter("fr_id_list");
			String fromDate = request.getParameter("from_date");
			String toDate = request.getParameter("to_date");
			routeId = request.getParameter("route_id");
			String isGrn = request.getParameter("is_grn");

			String grnType;
			if (isGrn.equalsIgnoreCase("2")) {

				System.err.println("Is Grn =2");
				grnType = "1" + "," + "0";

				map.add("isGrn", grnType);
			} else {
				System.err.println("Is Grn not =2");
				grnType = isGrn;
				map.add("isGrn", isGrn);

			}

			System.out.println("fromDate= " + fromDate);

			boolean isAllFrSelected = false;

			frIdString = frIdString.substring(1, frIdString.length() - 1);
			frIdString = frIdString.replaceAll("\"", "");

			List<String> franchIds = new ArrayList();
			franchIds = Arrays.asList(frIdString);

			System.out.println("fr Id ArrayList " + franchIds.toString());

			if (franchIds.contains("-1")) {
				isAllFrSelected = true;

			}

			if (!routeId.equalsIgnoreCase("0")) {

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

			map = new LinkedMultiValueMap<String, Object>();
			if (isAllFrSelected) {

				System.out.println("Inside IF  is All fr Selected " + isAllFrSelected);

				map.add("frIdList", 0);
				// model.addObject("billHeadersList",billHeadersListForPrint);

			} else { // few franchisee selected

				System.out.println("Inside Else: Few Fr Selected ");
				map.add("frIdList", frIdString);

			}

			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("isGrn", grnType);

			ParameterizedTypeReference<List<GGReportGrpByFrId>> typeRef = new ParameterizedTypeReference<List<GGReportGrpByFrId>>() {
			};
			ResponseEntity<List<GGReportGrpByFrId>> responseEntity = restTemplate
					.exchange(Constants.url + "gGReportGrpByFrId", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			grnGvnGrpByFrList = responseEntity.getBody();

			System.err.println("List " + grnGvnGrpByFrList.toString());

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No.");
			rowData.add("Type");
			rowData.add("Franchise Name");
			rowData.add("Req Qty");
			rowData.add("Req Value");
			rowData.add("Apr Qty");
			rowData.add("Apr Value");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			List<GGReportGrpByFrId> excelItems = grnGvnGrpByFrList;
			for (int i = 0; i < excelItems.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				rowData.add("" + (i + 1));

				String type;
				if (excelItems.get(i).getIsGrn() == 1) {
					type = "GRN";

				} else {
					type = "GVN";
				}
				rowData.add(type);
				rowData.add(excelItems.get(i).getFrName());
				rowData.add("" + excelItems.get(i).getReqQty());
				rowData.add("" + excelItems.get(i).getTotalAmt());
				rowData.add("" + excelItems.get(i).getAprQty());

				rowData.add("" + excelItems.get(i).getAprGrandTotal());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "grnGvnReport");

		} catch (Exception e) {

			System.out.println("Ex in getting /getGrnGvnByGrpByFr List  Ajax call" + e.getMessage());
			e.printStackTrace();
		}

		return grnGvnGrpByFrList;

	}

	// r2 PDFshowGGreportGrpByFr

	@RequestMapping(value = "pdf/showGGreportGrpByFr/{fDate}/{tDate}/{selectedFr}/{routeId}/{isGrn}", method = RequestMethod.GET)
	public ModelAndView showSaleReportByDatePdf(@PathVariable String fDate, @PathVariable String tDate,
			@PathVariable String selectedFr, @PathVariable String routeId, @PathVariable int isGrn,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("reports/grnGvn/pdf/r2");

		List<GGReportGrpByFrId> grnGvnGrpByFrList = new ArrayList<>();

		System.err.println("Inside PDF mapping");

		boolean isAllFrSelected = false;

		try {

			if (!routeId.equalsIgnoreCase("0")) {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

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
				selectedFr = strFrIdRouteWise.substring(0, strFrIdRouteWise.length() - 1);
				System.out.println("fr Id Route WISE = " + selectedFr);

			} // end of if

			if (selectedFr.equalsIgnoreCase("-1")) {
				isAllFrSelected = true;
			}

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			RestTemplate restTemplate = new RestTemplate();

			if (isAllFrSelected) {

				System.out.println("Inside If all fr Selected ");
				map.add("frIdList", 0);

			} else {
				System.out.println("Inside else Few fr Selected ");

				map.add("frIdList", selectedFr);

			}

			if (isGrn == 2) {
				System.err.println("Is Grn ==2");

				map.add("isGrn", "1" + "," + "0");
			} else {
				System.err.println("Is Grn  not Eq 2");

				map.add("isGrn", isGrn);

			}

			map.add("fromDate", DateConvertor.convertToYMD(fDate));
			map.add("toDate", DateConvertor.convertToYMD(tDate));
			// map.add("isGrn", isGrn);

			ParameterizedTypeReference<List<GGReportGrpByFrId>> typeRef = new ParameterizedTypeReference<List<GGReportGrpByFrId>>() {
			};
			ResponseEntity<List<GGReportGrpByFrId>> responseEntity = restTemplate
					.exchange(Constants.url + "gGReportGrpByFrId", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			grnGvnGrpByFrList = responseEntity.getBody();

			System.err.println("List---- " + grnGvnGrpByFrList.toString());

		} catch (Exception e) {
			System.err.println("Exc in GRN PDF report 2");
			e.printStackTrace();
		}

		model.addObject("fromDate", fDate);

		model.addObject("toDate", tDate);

		model.addObject("report", grnGvnGrpByFrList);
		
		model.addObject("isGrn", isGrn);
		
		Setting showHead = SalesReportController.isHeadAllow();
		if(showHead.getSettingValue()==1) {
			model.addObject("FACTORYNAME", Constants.FACTORYNAME);
			model.addObject("FACTORYADDRESS", Constants.FACTORYADDRESS);
		}
		return model;
	}

	// r3

	@RequestMapping(value = "/showGGReportGrpByDate", method = RequestMethod.GET)
	public ModelAndView showGGReportGrpByDate(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showGGReportGrpByDate", "showGGReportGrpByDate", "1", "0", "0", "0",
				newModuleList);

		if (view.getError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("reports/grnGvn/grnGvnGrpByDate");

			try {

				ZoneId z = ZoneId.of("Asia/Calcutta");

				LocalDate date = LocalDate.now(z);
				DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-uuuu");
				String todaysDate = date.format(formatters);

				allFrIdNameList = getFrNameId();

				allRouteListResponse = getAllRoute();

				model.addObject("routeList", allRouteListResponse.getRoute());
				model.addObject("todaysDate", todaysDate);
				model.addObject("unSelectedFrList", allFrIdNameList.getFrIdNamesList());

			} catch (Exception e) {
				System.out.println("Exce inshowGGReportDateWise " + e.getMessage());
				e.printStackTrace();
			}
		}

		return model;
	}

	// consume r3 web service ajax call
	//

	@RequestMapping(value = "/getGrnGvnByGrpByDate", method = RequestMethod.GET)
	@ResponseBody
	public List<GGReportGrpByMonthDate> getGrnGvnByGrpByDate(HttpServletRequest request, HttpServletResponse response) {

		List<GGReportGrpByMonthDate> grnGvnGrpByDateList = new ArrayList<>();

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			RestTemplate restTemplate = new RestTemplate();

			String routeId = "0";
			String frIdString = "";

			System.out.println("inside getGrnGvnByDatewise ajax call");

			frIdString = request.getParameter("fr_id_list");
			String fromDate = request.getParameter("from_date");
			String toDate = request.getParameter("to_date");
			routeId = request.getParameter("route_id");
			String isGrn = request.getParameter("is_grn");

			String grnType;
			if (isGrn.equalsIgnoreCase("2")) {

				System.err.println("Is Grn =2");
				grnType = "1" + "," + "0";

				map.add("isGrn", grnType);
			} else {
				System.err.println("Is Grn not =2");
				grnType = isGrn;
				map.add("isGrn", isGrn);

			}

			System.out.println("fromDate= " + fromDate);

			boolean isAllFrSelected = false;

			frIdString = frIdString.substring(1, frIdString.length() - 1);
			frIdString = frIdString.replaceAll("\"", "");

			List<String> franchIds = new ArrayList();
			franchIds = Arrays.asList(frIdString);

			System.out.println("fr Id ArrayList " + franchIds.toString());

			if (franchIds.contains("-1")) {
				isAllFrSelected = true;

			}

			if (!routeId.equalsIgnoreCase("0")) {

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

			map = new LinkedMultiValueMap<String, Object>();
			if (isAllFrSelected) {

				System.out.println("Inside IF  is All fr Selected " + isAllFrSelected);

				map.add("frIdList", 0);
				// model.addObject("billHeadersList",billHeadersListForPrint);

			} else { // few franchisee selected

				System.out.println("Inside Else: Few Fr Selected ");
				map.add("frIdList", frIdString);

			}

			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("isGrn", grnType);
			map.add("frIdList", 0);

			ParameterizedTypeReference<List<GGReportGrpByMonthDate>> typeRef = new ParameterizedTypeReference<List<GGReportGrpByMonthDate>>() {
			};
			ResponseEntity<List<GGReportGrpByMonthDate>> responseEntity = restTemplate
					.exchange(Constants.url + "getGGReportGrpByDate", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			grnGvnGrpByDateList = responseEntity.getBody();

			System.err.println("List " + grnGvnGrpByDateList.toString());

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No.");
			rowData.add("Date");

			rowData.add("Type");
			rowData.add("Req Qty");
			rowData.add("Req Value");
			rowData.add("Apr Qty");
			rowData.add("Apr Value");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			List<GGReportGrpByMonthDate> excelItems = grnGvnGrpByDateList;
			for (int i = 0; i < excelItems.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				rowData.add("" + (i + 1));

				String type;
				if (excelItems.get(i).getIsGrn() == 1) {
					type = "GRN";

				} else {
					type = "GVN";
				}
				rowData.add(excelItems.get(i).getGrnGvnDate());
				rowData.add(type);
				rowData.add("" + excelItems.get(i).getReqQty());
				rowData.add("" + excelItems.get(i).getTotalAmt());
				rowData.add("" + excelItems.get(i).getAprQty());

				rowData.add("" + excelItems.get(i).getAprGrandTotal());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "grnGvnReport");

		} catch (Exception e) {

			System.out.println("Ex in getting /getGrnGvnByGrpByDate List  Ajax call" + e.getMessage());
			e.printStackTrace();
		}

		return grnGvnGrpByDateList;

	}

	// showGGreportGrpByDate r3 PDF

	@RequestMapping(value = "pdf/showGGreportGrpByDate/{fDate}/{tDate}/{selectedFr}/{routeId}/{isGrn}", method = RequestMethod.GET)
	public ModelAndView showGGreportGrpByDatePdf(@PathVariable String fDate, @PathVariable String tDate,
			@PathVariable String selectedFr, @PathVariable String routeId, @PathVariable int isGrn,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("reports/grnGvn/pdf/r3");

		List<GGReportGrpByMonthDate> grnGvnGrpByDateList = new ArrayList<>();

		System.err.println("Inside PDF mapping");

		boolean isAllFrSelected = false;

		try {

			if (!routeId.equalsIgnoreCase("0")) {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

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
				selectedFr = strFrIdRouteWise.substring(0, strFrIdRouteWise.length() - 1);
				System.out.println("fr Id Route WISE = " + selectedFr);

			} // end of if

			if (selectedFr.equalsIgnoreCase("-1")) {
				isAllFrSelected = true;
			}

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			RestTemplate restTemplate = new RestTemplate();

			if (isAllFrSelected) {

				System.out.println("Inside If all fr Selected ");
				map.add("frIdList", 0);

			} else {
				System.out.println("Inside else Few fr Selected ");

				map.add("frIdList", selectedFr);

			}

			if (isGrn == 2) {
				System.err.println("Is Grn ==2");

				map.add("isGrn", "1" + "," + "0");
			} else {
				System.err.println("Is Grn  not Eq 2");

				map.add("isGrn", isGrn);

			}

			map.add("fromDate", DateConvertor.convertToYMD(fDate));
			map.add("toDate", DateConvertor.convertToYMD(tDate));
			// map.add("isGrn", isGrn);
			map.add("frIdList", 0);

			ParameterizedTypeReference<List<GGReportGrpByMonthDate>> typeRef = new ParameterizedTypeReference<List<GGReportGrpByMonthDate>>() {
			};
			ResponseEntity<List<GGReportGrpByMonthDate>> responseEntity = restTemplate
					.exchange(Constants.url + "getGGReportGrpByDate", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			grnGvnGrpByDateList = responseEntity.getBody();

			System.err.println("List---- " + grnGvnGrpByDateList.toString());

		} catch (Exception e) {
			System.err.println("Exc in GRN PDF report 2");
			e.printStackTrace();
		}

		model.addObject("fromDate", fDate);

		model.addObject("toDate", tDate);

		model.addObject("report", grnGvnGrpByDateList);
		model.addObject("isGrn", isGrn);
		Setting showHead = SalesReportController.isHeadAllow();
		if(showHead.getSettingValue()==1) {
			model.addObject("FACTORYNAME", Constants.FACTORYNAME);
			model.addObject("FACTORYADDRESS", Constants.FACTORYADDRESS);
		}

		return model;
	}

	// r4

	@RequestMapping(value = "/showGGReportGrpByMonth", method = RequestMethod.GET)
	public ModelAndView showGGReportGrpByMonth(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		HttpSession session = request.getSession();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showGGReportGrpByMonth", "showGGReportGrpByMonth", "1", "0", "0", "0",
				newModuleList);

		if (view.getError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("reports/grnGvn/gGGrpByMonth");

			try {

				ZoneId z = ZoneId.of("Asia/Calcutta");

				LocalDate date = LocalDate.now(z);
				DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-uuuu");
				String todaysDate = date.format(formatters);

				allFrIdNameList = getFrNameId();

				allRouteListResponse = getAllRoute();

				model.addObject("routeList", allRouteListResponse.getRoute());
				model.addObject("todaysDate", todaysDate);
				model.addObject("unSelectedFrList", allFrIdNameList.getFrIdNamesList());

			} catch (Exception e) {
				System.out.println("Exce inshowGGReportDateWise " + e.getMessage());
				e.printStackTrace();
			}
		}

		return model;
	}

	// r4 consume web service

	@RequestMapping(value = "/getGrnGvnByGrpByMonth", method = RequestMethod.GET)
	@ResponseBody
	public List<GGReportGrpByMonthDate> getGrnGvnByGrpByMonth(HttpServletRequest request,
			HttpServletResponse response) {

		List<GGReportGrpByMonthDate> grnGvnGrpByMonthList = new ArrayList<>();

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			RestTemplate restTemplate = new RestTemplate();

			String routeId = "0";
			String frIdString = "";

			System.out.println("inside getGrnGvnByDatewise ajax call");

			frIdString = request.getParameter("fr_id_list");
			String fromDate = request.getParameter("from_date");
			String toDate = request.getParameter("to_date");
			routeId = request.getParameter("route_id");
			String isGrn = request.getParameter("is_grn");

			String grnType;
			if (isGrn.equalsIgnoreCase("2")) {

				System.err.println("Is Grn =2");
				grnType = "1" + "," + "0";

				map.add("isGrn", grnType);
			} else {
				System.err.println("Is Grn not =2");
				grnType = isGrn;
				map.add("isGrn", isGrn);

			}

			System.out.println("fromDate= " + fromDate);

			boolean isAllFrSelected = false;

			frIdString = frIdString.substring(1, frIdString.length() - 1);
			frIdString = frIdString.replaceAll("\"", "");

			List<String> franchIds = new ArrayList();
			franchIds = Arrays.asList(frIdString);

			System.out.println("fr Id ArrayList " + franchIds.toString());

			if (franchIds.contains("-1")) {
				isAllFrSelected = true;

			}

			if (!routeId.equalsIgnoreCase("0")) {

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

			map = new LinkedMultiValueMap<String, Object>();
			if (isAllFrSelected) {

				System.out.println("Inside IF  is All fr Selected " + isAllFrSelected);

				map.add("frIdList", 0);
				// model.addObject("billHeadersList",billHeadersListForPrint);

			} else { // few franchisee selected

				System.out.println("Inside Else: Few Fr Selected ");
				map.add("frIdList", frIdString);

			}

			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("isGrn", grnType);
			map.add("frIdList", 0);

			ParameterizedTypeReference<List<GGReportGrpByMonthDate>> typeRef = new ParameterizedTypeReference<List<GGReportGrpByMonthDate>>() {
			};
			ResponseEntity<List<GGReportGrpByMonthDate>> responseEntity = restTemplate
					.exchange(Constants.url + "getGGReportGrpByMonth", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			grnGvnGrpByMonthList = responseEntity.getBody();

			System.err.println("List " + grnGvnGrpByMonthList.toString());

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No.");
			rowData.add("Month");

			rowData.add("Req Qty");
			rowData.add("Req Value");
			rowData.add("Apr Qty");
			rowData.add("Apr Value");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			List<GGReportGrpByMonthDate> excelItems = grnGvnGrpByMonthList;
			for (int i = 0; i < excelItems.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				rowData.add("" + (i + 1));

				rowData.add(excelItems.get(i).getMonth());
				rowData.add("" + excelItems.get(i).getReqQty());
				rowData.add("" + excelItems.get(i).getTotalAmt());
				rowData.add("" + excelItems.get(i).getAprQty());

				rowData.add("" + excelItems.get(i).getAprGrandTotal());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "grnGvnReport");

		} catch (Exception e) {

			System.out.println("Ex in getting /getGrnGvnByGrpByDate List  Ajax call" + e.getMessage());
			e.printStackTrace();
		}

		return grnGvnGrpByMonthList;

	}

	// showGGreportGrpByMonth r4 PDF

	@RequestMapping(value = "pdf/showGGreportGrpByMonth/{fDate}/{tDate}/{selectedFr}/{routeId}/{isGrn}", method = RequestMethod.GET)
	public ModelAndView showGGreportGrpByMonthPdf(@PathVariable String fDate, @PathVariable String tDate,
			@PathVariable String selectedFr, @PathVariable String routeId, @PathVariable int isGrn,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("reports/grnGvn/pdf/r4");

		List<GGReportGrpByMonthDate> grnGvnGrpByDateList = new ArrayList<>();

		System.err.println("Inside PDF mapping");

		boolean isAllFrSelected = false;

		try {

			if (!routeId.equalsIgnoreCase("0")) {

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

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
				selectedFr = strFrIdRouteWise.substring(0, strFrIdRouteWise.length() - 1);
				System.out.println("fr Id Route WISE = " + selectedFr);

			} // end of if

			if (selectedFr.equalsIgnoreCase("-1")) {
				isAllFrSelected = true;
			}

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			RestTemplate restTemplate = new RestTemplate();

			if (isAllFrSelected) {

				System.out.println("Inside If all fr Selected ");
				map.add("frIdList", 0);

			} else {
				System.out.println("Inside else Few fr Selected ");

				map.add("frIdList", selectedFr);

			}

			map.add("fromDate", DateConvertor.convertToYMD(fDate));
			map.add("toDate", DateConvertor.convertToYMD(tDate));

			if (isGrn == 2) {
				System.err.println("Is Grn ==2");

				map.add("isGrn", "1" + "," + "0");
			} else {
				System.err.println("Is Grn  not Eq 2");

				map.add("isGrn", isGrn);

			}
			// map.add("isGrn", isGrn);
			map.add("frIdList", 0);

			ParameterizedTypeReference<List<GGReportGrpByMonthDate>> typeRef = new ParameterizedTypeReference<List<GGReportGrpByMonthDate>>() {
			};
			ResponseEntity<List<GGReportGrpByMonthDate>> responseEntity = restTemplate
					.exchange(Constants.url + "getGGReportGrpByMonth", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			grnGvnGrpByDateList = responseEntity.getBody();

			System.err.println("List---- " + grnGvnGrpByDateList.toString());

		} catch (Exception e) {
			System.err.println("Exc in GRN PDF report 2");
			e.printStackTrace();
		}

		model.addObject("fromDate", fDate);

		model.addObject("toDate", tDate);

		model.addObject("report", grnGvnGrpByDateList);
		model.addObject("isGrn", isGrn);
		
		Setting showHead = SalesReportController.isHeadAllow();
		if(showHead.getSettingValue()==1) {
			model.addObject("FACTORYNAME", Constants.FACTORYNAME);
			model.addObject("FACTORYADDRESS", Constants.FACTORYADDRESS);
		}
		return model;
	}

	@RequestMapping(value = "/showGGReportGrpByItem", method = RequestMethod.GET)
	public ModelAndView showGGReportGrpByItem(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		HttpSession session = request.getSession();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showGGReportGrpByItem", "showGGReportGrpByItem", "1", "0", "0", "0",
				newModuleList);

		if (view.getError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("reports/grnGvn/ggByItem");

			try {
				RestTemplate restTemplate = new RestTemplate();

				ZoneId z = ZoneId.of("Asia/Calcutta");

				LocalDate date = LocalDate.now(z);
				DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-uuuu");
				String todaysDate = date.format(formatters);

				allFrIdNameList = getFrNameId();

				allRouteListResponse = getAllRoute();

				List<Item> itemList = new ArrayList<Item>();
				AllItemsListResponse itemListResponse = restTemplate.getForObject(Constants.url + "getAllItems",
						AllItemsListResponse.class);
				itemList = itemListResponse.getItems();

				CategoryListResponse categoryListResponse = restTemplate.getForObject(Constants.url + "showAllCategory",
						CategoryListResponse.class);
				List<MCategoryList> categoryList;
				categoryList = categoryListResponse.getmCategoryList();

				model.addObject("catList", categoryList);

				model.addObject("routeList", allRouteListResponse.getRoute());
				model.addObject("itemList", itemList);
				model.addObject("todaysDate", todaysDate);
				model.addObject("unSelectedFrList", allFrIdNameList.getFrIdNamesList());

			} catch (Exception e) {
				System.out.println("Exce inshowGGReportDateWise " + e.getMessage());
				e.printStackTrace();
			}
		}

		return model;
	}

	List<GGReportGrpByItemId> grnGvnGrpByFrList = new ArrayList<>();

	@RequestMapping(value = "/getGrnGvnByGrpByItem", method = RequestMethod.GET)
	@ResponseBody
	public List<GGReportGrpByItemId> getGrnGvnByGrpByItem(HttpServletRequest request, HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			RestTemplate restTemplate = new RestTemplate();

			String frIdString = "";
			String itemIdString = "";
			String subCatId = "";

			System.out.println("inside getGrnGvnByDatewise ajax call");

			frIdString = request.getParameter("fr_id_list");
			itemIdString = request.getParameter("item_id_list");
			String fromDate = request.getParameter("from_date");
			String toDate = request.getParameter("to_date");
			subCatId = request.getParameter("subCatId");
			String isGrn = request.getParameter("is_grn");

			String grnType;
			if (isGrn.equalsIgnoreCase("2")) {

				System.err.println("Is Grn =2");
				grnType = "1" + "," + "0";

				map.add("isGrn", grnType);
			} else {
				System.err.println("Is Grn not =2");
				grnType = isGrn;
				map.add("isGrn", isGrn);

			}

			System.out.println("fromDate= " + fromDate);

			boolean isAllFrSelected = false;

			frIdString = frIdString.substring(1, frIdString.length() - 1);
			frIdString = frIdString.replaceAll("\"", "");

			List<String> franchIds = new ArrayList();
			franchIds = Arrays.asList(frIdString);

			itemIdString = itemIdString.substring(1, itemIdString.length() - 1);
			itemIdString = itemIdString.replaceAll("\"", "");
			
			subCatId = subCatId.substring(1, subCatId.length() - 1);
			subCatId = subCatId.replaceAll("\"", "");

			/*
			 * List<String> itemIds = new ArrayList(); itemIds =
			 * Arrays.asList(itemIdString);
			 * 
			 * System.out.println("fr Id ArrayList " + franchIds.toString());
			 * 
			 * System.out.println("Item Id ArrayList " + itemIds.toString());
			 */

			if (franchIds.contains("-1")) {
				isAllFrSelected = true;

			}

			if (itemIdString.contains("-1")) {

				CategoryListResponse categoryListResponse = restTemplate.getForObject(Constants.url + "showAllCategory",
						CategoryListResponse.class);
				List<MCategoryList> categoryList;
				categoryList = categoryListResponse.getmCategoryList();

				StringBuilder sbForCatId = new StringBuilder();
				for (int i = 0; i < categoryList.size(); i++) {

					sbForCatId = sbForCatId.append(categoryList.get(i).getCatId().toString() + ",");

				}

				String sbForCatIdWise = sbForCatId.toString();
				itemIdString = sbForCatIdWise.substring(0, sbForCatIdWise.length() - 1);
				System.out.println("fr Id Route WISE = " + sbForCatIdWise);

			} // end of if

			map = new LinkedMultiValueMap<String, Object>();
			if (isAllFrSelected) {

				System.out.println("Inside IF  is All fr Selected " + isAllFrSelected);

				map.add("frIdList", -1);
				map.add("catIdList", -1);
				map.add("subCatId", subCatId);
				// model.addObject("billHeadersList",billHeadersListForPrint);

			} else { // few franchisee selected

				System.out.println("Inside Else: Few Fr Selected ");
				map.add("frIdList", frIdString);
				map.add("catIdList", itemIdString);
				map.add("subCatId", subCatId);
			}

			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("isGrn", grnType);

			ParameterizedTypeReference<List<GGReportGrpByItemId>> typeRef = new ParameterizedTypeReference<List<GGReportGrpByItemId>>() {
			};
			ResponseEntity<List<GGReportGrpByItemId>> responseEntity = restTemplate
					.exchange(Constants.url + "gGReportGrpByItemId", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			grnGvnGrpByFrList = responseEntity.getBody();

			System.err.println("List " + grnGvnGrpByFrList.toString());

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No.");
			rowData.add("Type");
			rowData.add("Item Name");
			rowData.add("Req Qty");
			rowData.add("Req Value");
			rowData.add("Apr Qty");
			rowData.add("Apr Value");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			List<GGReportGrpByItemId> excelItems = grnGvnGrpByFrList;
			for (int i = 0; i < excelItems.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				rowData.add("" + (i + 1));

				String type;
				if (excelItems.get(i).getIsGrn() == 1) {
					type = "GRN";

				} else {
					type = "GVN";
				}
				rowData.add(type);
				rowData.add(excelItems.get(i).getItemName());
				rowData.add("" + excelItems.get(i).getReqQty());
				rowData.add("" + excelItems.get(i).getTotalAmt());
				rowData.add("" + excelItems.get(i).getAprQty());

				rowData.add("" + excelItems.get(i).getAprGrandTotal());

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "grnGvnReport");

		} catch (Exception e) {

			System.out.println("Ex in getting /getGrnGvnByGrpByFr List  Ajax call" + e.getMessage());
			e.printStackTrace();
		}

		return grnGvnGrpByFrList;

	}

	@RequestMapping(value = "pdf/showGrnGvnItemwiseReportPdf/{fromDate}/{toDate}/{selectFr}/{isGrn}/{selectedCat}/{subCatId}", method = RequestMethod.GET)
	public ModelAndView showGrnGvnItemwiseReportPdf(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate, @PathVariable("selectFr") String selectFr, @PathVariable("isGrn") String isGrn,
			@PathVariable("selectedCat") String selectedCat, @PathVariable("subCatId") String subCatId,
			HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		
		ModelAndView model = new ModelAndView("reports/sales/pdf/grnGvnItemReportPdf");
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			RestTemplate restTemplate = new RestTemplate();
			
			String grnType;
			if (isGrn.equalsIgnoreCase("2")) {

				System.err.println("Is Grn =2");
				grnType = "1" + "," + "0";

				map.add("isGrn", grnType);
			} else {
				System.err.println("Is Grn not =2");
				grnType = isGrn;
				map.add("isGrn", isGrn);

			}

			System.out.println("fromDate= " + fromDate);

			boolean isAllFrSelected = false;
			
			if (selectFr.contains("-1")) {
				isAllFrSelected = true;
			}
			
			if (selectedCat.contains("-1")) {

				CategoryListResponse categoryListResponse = restTemplate.getForObject(Constants.url + "showAllCategory",
						CategoryListResponse.class);
				List<MCategoryList> categoryList;
				categoryList = categoryListResponse.getmCategoryList();

				StringBuilder sbForCatId = new StringBuilder();
				for (int i = 0; i < categoryList.size(); i++) {

					sbForCatId = sbForCatId.append(categoryList.get(i).getCatId().toString() + ",");

				}

				String sbForCatIdWise = sbForCatId.toString();
				selectedCat = sbForCatIdWise.substring(0, sbForCatIdWise.length() - 1);
				System.out.println("fr Id Route WISE = " + sbForCatIdWise);

			} // end of if

			map = new LinkedMultiValueMap<String, Object>();
			if (isAllFrSelected) {

				System.out.println("Inside IF  is All fr Selected " + isAllFrSelected);

				map.add("frIdList", -1);
				map.add("catIdList", -1);
				map.add("subCatId", subCatId);
				

			} else { // few franchisee selected

				System.out.println("Inside Else: Few Fr Selected ");
				map.add("frIdList", selectFr);
				map.add("catIdList", selectedCat);
				map.add("subCatId", subCatId);
			}

			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("isGrn", grnType);

			ParameterizedTypeReference<List<GGReportGrpByItemId>> typeRef = new ParameterizedTypeReference<List<GGReportGrpByItemId>>() {
			};
			ResponseEntity<List<GGReportGrpByItemId>> responseEntity = restTemplate
					.exchange(Constants.url + "gGReportGrpByItemId", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			grnGvnGrpByFrList = responseEntity.getBody();

			System.err.println("List " + grnGvnGrpByFrList.toString());
			
			
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);
			model.addObject("report", grnGvnGrpByFrList);
			
			map = new LinkedMultiValueMap<String, Object>();
			
			map.add("stKey", "showPdfHead");
			Setting allowHead = SalesReportController.isHeadAllow();		
			if (allowHead.getSettingValue() == 1) {
				model.addObject("FACTORYNAME", Constants.FACTORYNAME);
				model.addObject("FACTORYADDRESS", Constants.FACTORYADDRESS);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		
//		BufferedOutputStream outStream = null;
//		System.out.println("Inside Pdf showPOReportPdf");
//		Document document = new Document(PageSize.A4);
//
//		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//		Calendar cal = Calendar.getInstance();
//
//		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
//		String FILE_PATH = Constants.REPORT_SAVE;
//		File file = new File(FILE_PATH);
//
//		PdfWriter writer = null;
//
//		FileOutputStream out = new FileOutputStream(FILE_PATH);
//		try {
//			writer = PdfWriter.getInstance(document, out);
//		} catch (DocumentException e) {
//
//			e.printStackTrace();
//		}
//
//		PdfPTable table = new PdfPTable(7);
//		try {
//			System.out.println("Inside PDF Table try");
//			table.setWidthPercentage(100);
//			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f, 3.2f });
//			Font headFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
//			Font headFont1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
//			headFont1.setColor(BaseColor.WHITE);
//			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);
//
//			PdfPCell hcell = new PdfPCell();
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			hcell.setPadding(3);
//			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase("Type", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase("Item Name", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase("Req Qty", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase("Req Value ", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase("Apr Qty", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//			hcell = new PdfPCell(new Phrase("Apr Value", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//			int index = 0;
//			float totalReqQty = 0;
//			float totalReqAmt = 0;
//			float totalAprQty = 0;
//			float totalAprValue = 0;
//			for (GGReportGrpByItemId work : grnGvnGrpByFrList) {
//				index++;
//				PdfPCell cell;
//
//				totalReqQty = totalReqQty + work.getReqQty();
//				totalReqAmt = totalReqAmt + work.getTotalAmt();
//				totalAprQty = totalAprQty + work.getAprQty();
//				totalAprValue = totalAprValue + work.getAprGrandTotal();
//
//				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell.setPadding(3);
//				cell.setPaddingRight(2);
//				table.addCell(cell);
//
//				String isGrnGvn;
//
//				if (work.getIsGrn() == 1) {
//					isGrnGvn = "GRN";
//
//				} else if (work.getIsGrn() == 0) {
//					isGrnGvn = "GVN";
//				} else {
//					isGrnGvn = "Cust Complaint";
//				}
//
//				cell = new PdfPCell(new Phrase("" + isGrnGvn, headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell.setPaddingRight(2);
//				cell.setPadding(3);
//				table.addCell(cell);
//
//				cell = new PdfPCell(new Phrase("" + work.getItemName(), headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				cell.setPaddingRight(2);
//				cell.setPadding(3);
//				table.addCell(cell);
//
//				cell = new PdfPCell(new Phrase("" + work.getReqQty(), headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				cell.setPaddingRight(2);
//				cell.setPadding(3);
//				table.addCell(cell);
//
//				cell = new PdfPCell(new Phrase("" + work.getTotalAmt(), headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				cell.setPaddingRight(2);
//				cell.setPadding(3);
//				table.addCell(cell);
//
//				cell = new PdfPCell(new Phrase("" + work.getAprQty(), headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				cell.setPaddingRight(2);
//				cell.setPadding(3);
//				table.addCell(cell);
//
//				cell = new PdfPCell(new Phrase("" + work.getAprGrandTotal(), headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				cell.setPaddingRight(2);
//				cell.setPadding(3);
//				table.addCell(cell);
//
//			}
//
//			hcell = new PdfPCell();
//
//			hcell.setPadding(3);
//			hcell = new PdfPCell(new Phrase("", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase("", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase("Total", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase(roundUp(totalReqQty) + "", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase(roundUp(totalReqAmt) + "", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase(roundUp(totalAprQty) + "", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//			hcell = new PdfPCell(new Phrase(roundUp(totalAprValue) + "", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			document.open();
//			Paragraph name = new Paragraph("Monginis Patana\n", f);
//			name.setAlignment(Element.ALIGN_CENTER);
//			document.add(name);
//			document.add(new Paragraph(" "));
//			Paragraph company = new Paragraph("Itemwise Grn/Gvn Report\n", f);
//			company.setAlignment(Element.ALIGN_CENTER);
//			document.add(company);
//			document.add(new Paragraph(" "));
//
//			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
//			String reportDate = DF.format(new Date());
//
//			document.add(table);
//
//			int totalPages = writer.getPageNumber();
//
//			System.out.println("Page no " + totalPages);
//
//			document.close();
//
//			if (file != null) {
//
//				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
//
//				if (mimeType == null) {
//
//					mimeType = "application/pdf";
//
//				}
//
//				response.setContentType(mimeType);
//
//				response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));
//
//				response.setContentLength((int) file.length());
//
//				BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//
//				try {
//					FileCopyUtils.copy(inputStream, response.getOutputStream());
//				} catch (IOException e) {
//					System.out.println("Excep in Opening a Pdf File");
//					e.printStackTrace();
//				}
//			}
//
//		} catch (DocumentException ex) {
//
//			System.out.println("Pdf Generation Error: " + ex.getMessage());
//
//			ex.printStackTrace();
//
//		}
		return model;

	}

	@RequestMapping(value = "/creditNoteReportBetweenDate", method = RequestMethod.GET)
	public String creditNoteReportBetweenDate(HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();

		/*
		 * List<ModuleJson> newModuleList = (List<ModuleJson>)
		 * session.getAttribute("newModuleList"); Info view =
		 * AccessControll.checkAccess("showAddNewFranchisee", "showAddNewFranchisee",
		 * "1", "0", "0", "0", newModuleList);
		 * 
		 * if (view.getError() == true) {
		 * 
		 * model = new ModelAndView("accessDenied");
		 * 
		 * } else {
		 */
		String mav = "reports/grnGvn/creditNoteReportBetweenDate";

		try {

			allFrIdNameList = getFrNameId();

			model.addAttribute("unSelectedFrList", allFrIdNameList.getFrIdNamesList());

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			RestTemplate restTemplate = new RestTemplate();

			if (fromDate != null && toDate != null) {

				int frId = Integer.parseInt(request.getParameter("selectFr"));

				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				map.add("frId", frId);
				map.add("fromDate", DateConvertor.convertToYMD(fromDate));
				map.add("toDate", DateConvertor.convertToYMD(toDate));
				CreditNoteReport[] creditNoteReport = restTemplate
						.postForObject(Constants.url + "creditNoteReportBetweenDate", map, CreditNoteReport[].class);

				List<CreditNoteReport> list = new ArrayList<>(Arrays.asList(creditNoteReport));
				model.addAttribute("list", list);
				model.addAttribute("toDate", toDate);
				model.addAttribute("fromDate", fromDate);
				model.addAttribute("frId", frId);
			}

		} catch (Exception e) {
			System.out.println("Exce inshowGGReportDateWise " + e.getMessage());
			e.printStackTrace();
		}
		// }

		return mav;
	}

	@RequestMapping(value = "/creditNoteReportBetweenDatePdf ", method = RequestMethod.GET)
	public void creditNoteReportBetweenDatePdf(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showPOReportPdf");
		Document document = new Document(PageSize.A4);
		document.setMargins(5, 5, 5, 5);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		String FILE_PATH = Constants.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		document.open();
		PdfPTable table = new PdfPTable(10);
		try {

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			RestTemplate restTemplate = new RestTemplate();

			System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
			int frId = Integer.parseInt(request.getParameter("selectedFr"));

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frId);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			System.out.println(map);
			CreditNoteReport[] creditNoteReport = restTemplate
					.postForObject(Constants.url + "creditNoteReportBetweenDate", map, CreditNoteReport[].class);

			List<CreditNoteReport> list = new ArrayList<>(Arrays.asList(creditNoteReport));

			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 1.0f, 3.0f, 2.0f, 7.5f, 1.0f, 1.2f, 1.5f, 1.5f, 1.5f, 2.2f });
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
			headFont1.setColor(BaseColor.WHITE);

			Font totalFont = new Font(FontFamily.TIMES_ROMAN, 9, Font.BOLD, BaseColor.BLACK);

			Font f = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.UNDERLINE, BaseColor.BLUE);

			/*Paragraph name = new Paragraph("Siddarth Foods\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);*/

			Paragraph company = new Paragraph("Itemwise Grn/Gvn Report Date : " + fromDate + " To " + toDate , f);
			company.setAlignment(Element.ALIGN_CENTER); 
			document.add(company);
			
			/*Paragraph date = new Paragraph("Date : " + fromDate + " To " + toDate + "\n", f);
			date.setAlignment(Element.ALIGN_CENTER);
			document.add(date);*/
			
			 
			if (list.size() > 0) {

				String frnchiname = list.get(0).getFrName();

				Paragraph frName = new Paragraph(frnchiname + "\n", f);
				frName.setAlignment(Element.ALIGN_RIGHT);
				document.add(frName);
				document.add(new Paragraph(" "));

				PdfPCell hcell = new PdfPCell();

				hcell = new PdfPCell(new Phrase("-", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				//hcell.setPadding(5);
				hcell.setBorder(0);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Doc No", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				//hcell.setPadding(5);
				hcell.setBorder(0);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Date", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				//hcell.setPadding(5);
				hcell.setBorder(0);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Item Description", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				//hcell.setPadding(5);
				hcell.setBorder(0);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("UOM", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				hcell.setBorder(0);//hcell.setPadding(5);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("QTY", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				hcell.setBorder(0);//hcell.setPadding(5);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Rate", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				hcell.setBorder(0);//hcell.setPadding(5);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Penalty AMT", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				hcell.setBorder(0);//hcell.setPadding(5);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Credit AMT", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				hcell.setBorder(0);//hcell.setPadding(5);
				table.addCell(hcell);

				hcell = new PdfPCell(new Phrase("Ref Inv No", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);
				hcell.setBorder(0);//hcell.setPadding(5);
				table.addCell(hcell);

				int index = 0;

				float totalPanalty = 0;
				float totalCredit = 0;
				float finaltotalPanalty = 0;
				float finaltotalCredit = 0;

				DecimalFormat df = new DecimalFormat("#.00");

				for (int i = 0; i < list.size(); i++) {

					CreditNoteReport row = list.get(i);

					index++;
					PdfPCell cell;

					cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
					cell.setPaddingRight(2);
					cell.setBorder(0);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + row.getCrnNo(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(2); 
					cell.setBorder(0);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + row.getCrnDate(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(2); 
					cell.setBorder(0);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + row.getItemName(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(2); 
					cell.setBorder(0);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(" "+row.getItemUom(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(2);
					cell.setBorder(0);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + row.getGrnGvnQty(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2); 
					cell.setBorder(0);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + row.getBaseRate(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2); 
					cell.setBorder(0);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + row.getPeneltyAmt(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2); 
					cell.setBorder(0);
					table.addCell(cell);
					totalPanalty = totalPanalty + row.getPeneltyAmt();

					cell = new PdfPCell(new Phrase("" + row.getGrnGvnAmt(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2); 
					cell.setBorder(0);
					table.addCell(cell);
					totalCredit = totalCredit + row.getGrnGvnAmt();

					cell = new PdfPCell(new Phrase("" + row.getRefInvoiceNo(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setPaddingRight(2); 
					cell.setBorder(0);
					table.addCell(cell);

					int istotal = 0;
					try {

						if (list.get(i + 1).getCrnId() != row.getCrnId()) {
							istotal = 1;
						}

					} catch (Exception e) {
						istotal = 1;
					}

					if (istotal == 1) {

						cell = new PdfPCell(new Phrase("Total ", totalFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						/*cell.setPaddingRight(2);
						cell.setPadding(3);*/
						cell.setBorder(0);
						cell.setColspan(7);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + df.format(totalPanalty), totalFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						/*cell.setPaddingRight(2);
						cell.setPadding(3);*/
						cell.setBorder(0);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + df.format(totalCredit), totalFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						/*cell.setPaddingRight(2);
						cell.setPadding(3);*/
						cell.setBorder(0);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("-", totalFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						/*cell.setPaddingRight(2);
						cell.setPadding(3);*/
						cell.setBorder(0);
						table.addCell(cell);

						finaltotalPanalty = finaltotalPanalty + totalPanalty;
						finaltotalCredit = finaltotalCredit + totalCredit;

						totalPanalty = 0;
						totalCredit = 0;

					}

					int isnextpage = 0;
					try {

						if (list.get(i + 1).getFrId() != row.getFrId()) {
							isnextpage = 1;
							frnchiname = list.get(i + 1).getFrName();
						}

					} catch (Exception e) {
						// isnextpage=1;

						cell = new PdfPCell(new Phrase("Final Total ", totalFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						/*cell.setPaddingRight(2);
						cell.setPadding(3);*/
						cell.setBorder(0);
						cell.setColspan(7);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + df.format(finaltotalPanalty), totalFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						/*cell.setPaddingRight(2);
						cell.setPadding(3);*/
						cell.setBorder(0);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + df.format(finaltotalCredit), totalFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						/*cell.setPaddingRight(2);
						cell.setPadding(3);*/
						cell.setBorder(0);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("-", totalFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						/*cell.setPaddingRight(2);
						cell.setPadding(3);*/
						cell.setBorder(0);
						table.addCell(cell);
						document.add(table);
						finaltotalPanalty = 0;
						finaltotalCredit = 0;

					}

					if (isnextpage == 1) {

						cell = new PdfPCell(new Phrase("Final Total ", totalFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						/*cell.setPaddingRight(2);
						cell.setPadding(3);*/
						cell.setBorder(0);
						cell.setColspan(7);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + df.format(finaltotalPanalty), totalFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						/*cell.setPaddingRight(2);
						cell.setPadding(3);*/
						cell.setBorder(0);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("" + df.format(finaltotalCredit), totalFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						/*cell.setPaddingRight(2);
						cell.setPadding(3);*/
						cell.setBorder(0);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase("-", totalFont));
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						/*cell.setPaddingRight(2);
						cell.setPadding(3);*/
						cell.setBorder(0);
						table.addCell(cell);
						document.add(table);
						finaltotalPanalty = 0;
						finaltotalCredit = 0;

						// System.out.println("next paage");
						document.newPage();
						table = new PdfPTable(10);
						table.setWidthPercentage(100);
						table.setWidths(new float[] { 1.0f, 3.0f, 2.0f, 7.5f, 1.0f, 1.2f, 1.5f, 1.5f, 1.5f, 2.2f });
						index=0;
						hcell = new PdfPCell();

						hcell = new PdfPCell(new Phrase("-", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);
						//hcell.setPadding(5);
						hcell.setBorder(0);
						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Doc No", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);
						hcell.setBorder(0);
						//hcell.setPadding(5);
						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Date", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);
						//hcell.setPadding(5);
						hcell.setBorder(0);
						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Item Description", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);
						//hcell.setPadding(5);
						hcell.setBorder(0);
						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("UOM", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);
						//hcell.setPadding(5);
						hcell.setBorder(0);
						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("QTY", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);
						hcell.setBorder(0);//hcell.setPadding(5);
						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Rate", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);
						//hcell.setPadding(5);
						hcell.setBorder(0);
						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Penalty AMT", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);
						hcell.setBorder(0);//hcell.setPadding(5);
						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Credit AMT", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);
						//hcell.setPadding(5);
						hcell.setBorder(0);
						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Ref Inv No", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);
						//hcell.setPadding(5);
						hcell.setBorder(0);
						table.addCell(hcell);

						//document.add(name);
						document.add(company);
						//document.add(date);
						frName = new Paragraph(frnchiname + "\n", f);
						frName.setAlignment(Element.ALIGN_RIGHT);
						document.add(frName);
						document.add(new Paragraph(" "));

					}

				}

				/*
				 * Paragraph name = new Paragraph("Siddarth Foods\n", f);
				 * name.setAlignment(Element.ALIGN_CENTER); document.add(name); document.add(new
				 * Paragraph(" ")); Paragraph company = new
				 * Paragraph("Itemwise Grn/Gvn Report\n", f);
				 * company.setAlignment(Element.ALIGN_CENTER); document.add(company);
				 * document.add(new Paragraph(" "));
				 */

				DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				String reportDate = DF.format(new Date());

			}
			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();

			if (file != null) {

				String mimeType = URLConnection.guessContentTypeFromName(file.getName());

				if (mimeType == null) {

					mimeType = "application/pdf";

				}

				response.setContentType(mimeType);

				response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

				response.setContentLength((int) file.length());

				BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				try {
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (IOException e) {
					System.out.println("Excep in Opening a Pdf File");
					e.printStackTrace();
				}
			}

		} catch (DocumentException ex) {

			System.out.println("Pdf Generation Error: " + ex.getMessage());

			ex.printStackTrace();

		}

	}
	
	@RequestMapping(value = "/showGrnGvnPendingItemReport", method = RequestMethod.GET)
	public ModelAndView showGrnGvnPendingItemReport(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		HttpSession session = request.getSession();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showGrnGvnPendingItemReport", "showGrnGvnPendingItemReport", "1", "0", "0", "0",
				newModuleList);

		if (view.getError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("reports/grnGvn/grnGvnPendndItem");

			try {
				RestTemplate restTemplate = new RestTemplate();

				ZoneId z = ZoneId.of("Asia/Calcutta");

				LocalDate date = LocalDate.now(z);
				DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-uuuu");
				String todaysDate = date.format(formatters);

				allFrIdNameList = getFrNameId();

				allRouteListResponse = getAllRoute();

				model.addObject("todaysDate", todaysDate);
				model.addObject("unSelectedFrList", allFrIdNameList.getFrIdNamesList());

			} catch (Exception e) {
				System.out.println("Exce showGrnGvnPendingItemReport " + e.getMessage());
				e.printStackTrace();
			}
		}

		return model;
	}
	
	
	List<PendingItemGrnGvn> grnGvnList = new ArrayList<PendingItemGrnGvn>();

	@RequestMapping(value = "/getGrnGvnPendingItems", method = RequestMethod.GET)
	@ResponseBody
	public List<PendingItemGrnGvn> getGrnGvnPendingItems(HttpServletRequest request, HttpServletResponse response) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			RestTemplate restTemplate = new RestTemplate();
			
			System.out.println("inside getGrnGvnPendingItems ajax call");

			int frId = Integer.parseInt(request.getParameter("fr_id_list"));
			String isCrn = request.getParameter("isCrn");
			String fromDate = request.getParameter("from_date");
			String toDate = request.getParameter("to_date");
			String isGrn = request.getParameter("is_grn");
			int grnStatus = Integer.parseInt(request.getParameter("grnStatus"));

			map = new LinkedMultiValueMap<String, Object>();
			
			String grnType, crnType = null;
			if (isGrn.equalsIgnoreCase("2")) {
				grnType = "1" + "," + "0";

				map.add("isGrn", grnType);
			} else {
				System.err.println("Is Grn not =2");
				grnType = isGrn;
				map.add("isGrn", grnType);
			}
			

			if (isCrn.equalsIgnoreCase("2")) {	
				crnType = "1" + "," + "0";
				map.add("isCrnGen", crnType);
			} else {
				crnType = isCrn;
				map.add("isCrnGen", crnType);

			}					
			
			map.add("frId", frId);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate",  DateConvertor.convertToYMD(toDate));
			map.add("status", grnStatus);

			ParameterizedTypeReference<List<PendingItemGrnGvn>> typeRef = new ParameterizedTypeReference<List<PendingItemGrnGvn>>() {
			};
			ResponseEntity<List<PendingItemGrnGvn>> responseEntity = null;
			try {
			responseEntity = restTemplate
					.exchange(Constants.url + "getPendingGrnGvnItemReport", HttpMethod.POST, new HttpEntity<>(map), typeRef);
			}catch (HttpClientErrorException e) {
				System.out.println(e.getResponseBodyAsString());
			}
			grnGvnList = responseEntity.getBody();
			
//			PendingItemGrnGvn[] grnArr= restTemplate.postForObject(Constants.url + "getPendingGrnGvnItemReport", map, PendingItemGrnGvn[].class);
//			grnGvnList = new ArrayList<PendingItemGrnGvn>(Arrays.asList(grnArr));
//			System.err.println("List " + grnGvnGrpByFrList.toString());

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No.");
			rowData.add("GRN No.");
			rowData.add("Invoice No.");
			rowData.add("Item Name");
			rowData.add("GRN%");
			rowData.add("Req Qty");
		//	rowData.add("Req Value");
			rowData.add("Apr Qty");
		//	rowData.add("Apr Value");
			rowData.add("GRN Generated");
			rowData.add("Status");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			List<PendingItemGrnGvn> excelItems = grnGvnList;
			for (int i = 0; i < excelItems.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				rowData.add("" + (i + 1));

				
				rowData.add(excelItems.get(i).getGrngvnSrno()+"~"+excelItems.get(i).getGrngvnDate());
				rowData.add(excelItems.get(i).getInvoiceNo()+"~"+excelItems.get(i).getBillDate());
				rowData.add(excelItems.get(i).getItemName());
				rowData.add("" + excelItems.get(i).getGrnPer());
				rowData.add("" + excelItems.get(i).getReqQty());
			//	rowData.add("" + excelItems.get(i).getTotalAmt());
				rowData.add("" + excelItems.get(i).getAprQty());
			//	rowData.add("" + excelItems.get(i).getAprAmt());
				rowData.add(excelItems.get(i).getIsCreditNote() == 1 ? "Yes" : "No");
				
				String grnGvnstatus = null;
				if (excelItems.get(i).getGrngvnStatus() == 1)
					grnGvnstatus = "Pending";
				else if (excelItems.get(i).getGrngvnStatus() == 2)
					grnGvnstatus = "Approved By Gate";
				else if (excelItems.get(i).getGrngvnStatus() == 3)
					grnGvnstatus = "Reject By Gate";
				else if (excelItems.get(i).getGrngvnStatus() == 4)
					grnGvnstatus = "Approved By Store";
				else if (excelItems.get(i).getGrngvnStatus() == 5)
					grnGvnstatus = "Reject By Store";
				else if (excelItems.get(i).getGrngvnStatus() == 6)
					grnGvnstatus = "Approved By Acc";
				else
					grnGvnstatus = "Reject By Acc";

				rowData.add(grnGvnstatus);
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "grnGvnReport");

		} catch (Exception e) {

			System.out.println("Ex in getting /getGrnGvnPendingItems List  Ajax call" + e.getMessage());
			e.printStackTrace();
		}

		return grnGvnList;

	}
	
	@RequestMapping(value = "pdf/showGrnGvnPendingItemReportPdf/{fromDate}/{toDate}/{frId}/{isCrn}/{isGrn}/{grnStatus}", method = RequestMethod.GET)
	public ModelAndView showGrnGvnPendingItemReportPdf(@PathVariable("fromDate") String fromDate,
			@PathVariable("toDate") String toDate, @PathVariable("frId") int frId, @PathVariable("isCrn") String isCrn,
			@PathVariable("isGrn") String isGrn, @PathVariable("grnStatus") int grnStatus, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		ModelAndView model = new ModelAndView("reports/grnGvn/pdf/r5");
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			RestTemplate restTemplate = new RestTemplate();
			String grnType, crnType = null;
			if (isGrn.equalsIgnoreCase("2")) {
				grnType = "1" + "," + "0";

				map.add("isGrn", grnType);
			} else {
				System.err.println("Is Grn not =2");
				grnType = isGrn;
				map.add("isGrn", grnType);
			}
			

			if (isCrn.equalsIgnoreCase("2")) {	
				crnType = "1" + "," + "0";
				map.add("isCrnGen", crnType);
			} else {
				crnType = isCrn;
				map.add("isCrnGen", crnType);

			}					
			
			map.add("frId", frId);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate",  DateConvertor.convertToYMD(toDate));
			map.add("status", grnStatus);

			ParameterizedTypeReference<List<PendingItemGrnGvn>> typeRef = new ParameterizedTypeReference<List<PendingItemGrnGvn>>() {
			};
			ResponseEntity<List<PendingItemGrnGvn>> responseEntity = null;
			try {
			responseEntity = restTemplate
					.exchange(Constants.url + "getPendingGrnGvnItemReport", HttpMethod.POST, new HttpEntity<>(map), typeRef);
			}catch (HttpClientErrorException e) {
				System.out.println(e.getResponseBodyAsString());
			}
			grnGvnList = responseEntity.getBody();
			model.addObject("report", grnGvnList);
		}catch (Exception e) {
			// TODO: handle exception
		}
		

		model.addObject("fromDate", fromDate);
		model.addObject("toDate", toDate);
		model.addObject("isCrn", isCrn);
		model.addObject("isGrn", isGrn);
		
		
		Setting showHead = SalesReportController.isHeadAllow();
		if(showHead.getSettingValue()==1) {
			model.addObject("FACTORYNAME", Constants.FACTORYNAME);
			model.addObject("FACTORYADDRESS", Constants.FACTORYADDRESS);
		}
		return model;
		
//		BufferedOutputStream outStream = null;
//		System.out.println("Inside Pdf showPOReportPdf");
//		Document document = new Document(PageSize.A4);
//
//		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//		Calendar cal = Calendar.getInstance();
//
//		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
//		String FILE_PATH = Constants.REPORT_SAVE;
//		File file = new File(FILE_PATH);
//
//		PdfWriter writer = null;
//
//		FileOutputStream out = new FileOutputStream(FILE_PATH);
//		try {
//			writer = PdfWriter.getInstance(document, out);
//		} catch (DocumentException e) {
//
//			e.printStackTrace();
//		}
//
//		PdfPTable table = new PdfPTable(9);
//		try {
//			System.out.println("Inside PDF Table try");
//			table.setWidthPercentage(100);
//			table.setWidths(new float[] { 2.4f, 3.2f, 3.2f, 3.2f, 2.0f, 2.0f, 2.0f,  2.0f, 3.2f });
//			Font headFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
//			Font headFont1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
//			headFont1.setColor(BaseColor.WHITE);
//			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);
//
//			PdfPCell hcell = new PdfPCell();
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			hcell.setPadding(3);
//			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase("GRN No.", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//			
//			hcell = new PdfPCell(new Phrase("Invoice No.", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase("Item Name", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//			
//			hcell = new PdfPCell(new Phrase("GRN %", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase("Req Qty", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
////			hcell = new PdfPCell(new Phrase("Req Value ", headFont1));
////			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
////			hcell.setBackgroundColor(BaseColor.PINK);
////
////			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase("Apr Qty", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//			
////			hcell = new PdfPCell(new Phrase("Apr Value", headFont1));
////			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
////			hcell.setBackgroundColor(BaseColor.PINK);
////
////			table.addCell(hcell);
//			
//			hcell = new PdfPCell(new Phrase("GRN Generated", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//			
//			
//			hcell = new PdfPCell(new Phrase("Status", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//			int index = 0;
//			float totalReqQty = 0;
//			float totalReqAmt = 0;
//			float totalAprQty = 0;
//			float totalAprValue = 0;
//			for (PendingItemGrnGvn work : grnGvnList) {
//				index++;
//				PdfPCell cell;
//
//				totalReqQty = totalReqQty + work.getReqQty();
//				totalReqAmt = totalReqAmt + work.getTotalAmt();
//				totalAprQty = totalAprQty + work.getAprQty();
//				totalAprValue = totalAprValue + work.getAprAmt();
//
//				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell.setPadding(3);
//				cell.setPaddingRight(2);
//				table.addCell(cell);
//
//				cell = new PdfPCell(new Phrase("" + work.getGrngvnSrno()+"~"+work.getGrngvnDate(), headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell.setPaddingRight(2);
//				cell.setPadding(3);
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Phrase("" + work.getInvoiceNo()+"~"+work.getBillDate(), headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell.setPaddingRight(2);
//				cell.setPadding(3);
//				table.addCell(cell);
//
//				cell = new PdfPCell(new Phrase("" + work.getItemName(), headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//				cell.setPaddingRight(2);
//				cell.setPadding(3);
//				table.addCell(cell);
//				
//				cell = new PdfPCell(new Phrase("" + work.getGrnPer(), headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//				cell.setPaddingRight(2);
//				cell.setPadding(3);
//				table.addCell(cell);
//
//				cell = new PdfPCell(new Phrase("" + work.getReqQty(), headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				cell.setPaddingRight(2);
//				cell.setPadding(3);
//				table.addCell(cell);
//
////				cell = new PdfPCell(new Phrase("" + work.getTotalAmt(), headFont));
////				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
////				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
////				cell.setPaddingRight(2);
////				cell.setPadding(3);
////				table.addCell(cell);
//
//				cell = new PdfPCell(new Phrase("" + work.getAprQty(), headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				cell.setPaddingRight(2);
//				cell.setPadding(3);
//				table.addCell(cell);
//
////				cell = new PdfPCell(new Phrase("" + work.getAprAmt(), headFont));
////				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
////				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
////				cell.setPaddingRight(2);
////				cell.setPadding(3);
////				table.addCell(cell);
//				
//				cell = new PdfPCell(new Phrase(work.getIsCreditNote() == 1 ? "Yes" : "No", headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				cell.setPaddingRight(2);
//				cell.setPadding(3);
//				table.addCell(cell);
//				
//				String grnGvnstatus = null;
//				if (work.getGrngvnStatus() == 1)
//					grnGvnstatus = "Pending";
//				else if (work.getGrngvnStatus() == 2)
//					grnGvnstatus = "Approved By Gate";
//				else if (work.getGrngvnStatus() == 3)
//					grnGvnstatus = "Reject By Gate";
//				else if (work.getGrngvnStatus() == 4)
//					grnGvnstatus = "Approved By Store";
//				else if (work.getGrngvnStatus() == 5)
//					grnGvnstatus = "Reject By Store";
//				else if (work.getGrngvnStatus() == 6)
//					grnGvnstatus = "Approved By Acc";
//				else
//					grnGvnstatus = "Reject By Acc";
//
//				cell = new PdfPCell(new Phrase(grnGvnstatus, headFont));
//				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				cell.setPaddingRight(2);
//				cell.setPadding(3);
//				table.addCell(cell);
//
//
//			}
//
//			hcell = new PdfPCell();
//
//			hcell.setPadding(3);
//			hcell = new PdfPCell(new Phrase("", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase("", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase("Total", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase(roundUp(totalReqQty) + "", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase(roundUp(totalReqAmt) + "", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			hcell = new PdfPCell(new Phrase(roundUp(totalAprQty) + "", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//			hcell = new PdfPCell(new Phrase(roundUp(totalAprValue) + "", headFont1));
//			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			hcell.setBackgroundColor(BaseColor.PINK);
//
//			table.addCell(hcell);
//
//			document.open();
//			Paragraph name = new Paragraph("Monginis Patana\n", f);
//			name.setAlignment(Element.ALIGN_CENTER);
//			document.add(name);
//			document.add(new Paragraph(" "));
//			Paragraph company = new Paragraph("Itemwise Grn/Gvn Report\n", f);
//			company.setAlignment(Element.ALIGN_CENTER);
//			document.add(company);
//			document.add(new Paragraph(" "));
//
//			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
//			String reportDate = DF.format(new Date());
//
//			document.add(table);
//
//			int totalPages = writer.getPageNumber();
//
//			System.out.println("Page no " + totalPages);
//
//			document.close();
//
//			if (file != null) {
//
//				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
//
//				if (mimeType == null) {
//
//					mimeType = "application/pdf";
//
//				}
//
//				response.setContentType(mimeType);
//
//				response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));
//
//				response.setContentLength((int) file.length());
//
//				BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//
//				try {
//					FileCopyUtils.copy(inputStream, response.getOutputStream());
//				} catch (IOException e) {
//					System.out.println("Excep in Opening a Pdf File");
//					e.printStackTrace();
//				}
//			}
//
//		} catch (DocumentException ex) {
//
//			System.out.println("Pdf Generation Error: " + ex.getMessage());
//
//			ex.printStackTrace();
//
//		}

	}
	
	@RequestMapping(value = "/showAccPndngItmReport", method = RequestMethod.GET)
	public ModelAndView showAccPendingItemReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();

		List<ModuleJson> newModuleList = (List<ModuleJson>) session.getAttribute("newModuleList");
		Info view = AccessControll.checkAccess("showAccPndngItmReport", "showAccPndngItmReport", "1", "0", "0", "0",
				newModuleList);

		if (view.getError() == true) {

			model = new ModelAndView("accessDenied");

		} else {
			model = new ModelAndView("reports/grnGvn/pndItmGenGvnAprv");

			try {

				ZoneId z = ZoneId.of("Asia/Calcutta");

				LocalDate date = LocalDate.now(z);
				DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-uuuu");
				String todaysDate = date.format(formatters);

				allFrIdNameList = getFrNameId();

				allRouteListResponse = getAllRoute();
				
				model.addObject("todaysDate", todaysDate);
				model.addObject("unSelectedFrList", allFrIdNameList.getFrIdNamesList());

			} catch (Exception e) {
				System.out.println("Exce showAccPndngItmReport " + e.getMessage());
				e.printStackTrace();
			}
		}
		return model;
	}
	
	@RequestMapping(value = "/getAllFrPndngItm", method = RequestMethod.GET)
	public @ResponseBody List<AllFrIdName> getAllFrListForSalesReportAjax(HttpServletRequest request,
			HttpServletResponse response) {

		return allFrIdNameList.getFrIdNamesList();

	}

	
	List<PendingGrnGvnItemWise> grnAcGvnList = new ArrayList<PendingGrnGvnItemWise>();
	@RequestMapping(value = "/getAccPendingItemsGrnGvn", method = RequestMethod.GET)
	@ResponseBody
	public List<PendingGrnGvnItemWise> getAccPendingItemsGrnGvn(HttpServletRequest request, HttpServletResponse response) {
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			RestTemplate restTemplate = new RestTemplate();
			
			System.out.println("inside getAccPendingItemsGrnGvn ajax call");

			String selectedFr = request.getParameter("fr_id_list");
			String fromDate = request.getParameter("from_date");
			String toDate = request.getParameter("to_date");
			String isGrn = request.getParameter("is_grn");
			int aprvBy = Integer.parseInt(request.getParameter("apprvBy"));
		//	int grnStatus = Integer.parseInt(request.getParameter("grnStatus"));
			int grnStatus = 6;

			map = new LinkedMultiValueMap<String, Object>();
			
			String grnType = null;
			if (isGrn.equalsIgnoreCase("2")) {
				grnType = "1" + "," + "0";

				map.add("isGrn", grnType);
			} else {
				System.err.println("Is Grn not =2");
				grnType = isGrn;
				map.add("isGrn", grnType);
			}
			
			selectedFr = selectedFr.substring(1, selectedFr.length() - 1);
			selectedFr = selectedFr.replaceAll("\"", "");
			
			map.add("frIdList", selectedFr);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate",  DateConvertor.convertToYMD(toDate));
			map.add("grnStatus", grnStatus);
			map.add("aprvBy", aprvBy);
			
		//	System.out.println(selectedFr+" / "+fromDate+" / "+toDate+" / "+grnStatus+" / "+aprvBy+" / "+grnType);
			
			
			ParameterizedTypeReference<List<PendingGrnGvnItemWise>> typeRef = new ParameterizedTypeReference<List<PendingGrnGvnItemWise>>() {
			};
			ResponseEntity<List<PendingGrnGvnItemWise>> responseEntity = null;
			try {
			responseEntity = restTemplate
					.exchange(Constants.url + "getAcPendingItemGrnGvnReport", HttpMethod.POST, new HttpEntity<>(map), typeRef);
			}catch (HttpClientErrorException e) {
				System.out.println(e.getResponseBodyAsString());
			}
			grnAcGvnList = responseEntity.getBody();
			

			//System.err.println("A/c Pending grnGvnList ------------------- " + grnAcGvnList);

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No.");
			rowData.add("GRN GVN SrNo.");
			rowData.add("Type");
			rowData.add("GRN GVN Date");
			rowData.add("Item Name");
			rowData.add("Qty");	
			rowData.add("GRN GVN Status");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			List<PendingGrnGvnItemWise> excelItems = grnAcGvnList;
			for (int i = 0; i < excelItems.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				rowData.add("" + (i + 1));				
				
				rowData.add(excelItems.get(i).getGrngvnDate());
				rowData.add(excelItems.get(i).getIsGrn()==1 ? "GRN" : "GVN");
				rowData.add("" + excelItems.get(i).getGrngvnDate());
				rowData.add(excelItems.get(i).getItemName());
				rowData.add("" + excelItems.get(i).getGrnGvnQty());
				
				String grnGvnstatus = null;
				if (excelItems.get(i).getGrngvnStatus() == 1)
					grnGvnstatus = "Pending";
				else if (excelItems.get(i).getGrngvnStatus() == 2)
					grnGvnstatus = "Approved By Gate";
				else if (excelItems.get(i).getGrngvnStatus() == 3)
					grnGvnstatus = "Reject By Gate";
				else if (excelItems.get(i).getGrngvnStatus() == 4)
					grnGvnstatus = "Approved By Store";
				else if (excelItems.get(i).getGrngvnStatus() == 5)
					grnGvnstatus = "Reject By Store";
				else if (excelItems.get(i).getGrngvnStatus() == 6)
					grnGvnstatus = "Approved By Acc";
				else
					grnGvnstatus = "Reject By Acc";

				rowData.add(grnGvnstatus);
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "A/c Pending Item Wise Grn Gvn");

		} catch (Exception e) {

			System.out.println("Ex in getting /getGrnGvnPendingItems List  Ajax call" + e.getMessage());
			e.printStackTrace();
		}

		return grnAcGvnList;

	}
	
	@RequestMapping(value = "pdf/showPndItemGrnGvnReportPdf/{fromDate}/{toDate}/{frIds}/{aprvBy}/{isGrn}", method = RequestMethod.GET)
	public ModelAndView showPndItemGrnGvnReportPdf(@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			@PathVariable("frIds") String frIds,
			@PathVariable("aprvBy") int aprvBy , @PathVariable("isGrn") String isGrn, HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {
		ModelAndView model = new ModelAndView("reports/grnGvn/pdf/r6");
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			RestTemplate restTemplate = new RestTemplate();
			String grnType=null;
			if (isGrn.equalsIgnoreCase("2")) {
				grnType = "1" + "," + "0";

				map.add("isGrn", grnType);
			} else {
				System.err.println("Is Grn not =2");
				grnType = isGrn;
				map.add("isGrn", grnType);
			}
			int grnStatus = 6;
			
			map.add("frIdList", frIds);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate",  DateConvertor.convertToYMD(toDate));
			map.add("grnStatus", grnStatus);
			map.add("aprvBy", aprvBy);

			ParameterizedTypeReference<List<PendingGrnGvnItemWise>> typeRef = new ParameterizedTypeReference<List<PendingGrnGvnItemWise>>() {
			};
			ResponseEntity<List<PendingGrnGvnItemWise>> responseEntity = null;
			try {
			responseEntity = restTemplate
					.exchange(Constants.url + "getAcPendingItemGrnGvnReport", HttpMethod.POST, new HttpEntity<>(map), typeRef);
			}catch (HttpClientErrorException e) {
				System.out.println(e.getResponseBodyAsString());
			}
			grnAcGvnList = responseEntity.getBody();
			
			model.addObject("report", grnAcGvnList);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		model.addObject("report", grnAcGvnList);
		model.addObject("fromDate", fromDate);
		model.addObject("toDate", toDate);
		model.addObject("isGrn", isGrn);
		
		
		Setting showHead = SalesReportController.isHeadAllow();
		if(showHead.getSettingValue()==1) {
			model.addObject("FACTORYNAME", Constants.FACTORYNAME);
			model.addObject("FACTORYADDRESS", Constants.FACTORYADDRESS);
		}
		return model;
		
	}

	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}

}
