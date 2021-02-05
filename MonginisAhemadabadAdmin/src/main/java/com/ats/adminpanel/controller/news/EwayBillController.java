package com.ats.adminpanel.controller.news;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ats.adminpanel.commons.Constants;
import com.ats.adminpanel.model.Info;
import com.ats.adminpanel.model.billing.Company;
import com.ats.adminpanel.model.ewaybill.BillHeadEwayBill;
import com.ats.adminpanel.model.ewaybill.CancelEWBModel;
import com.ats.adminpanel.model.ewaybill.CustomErrEwayBill;
import com.ats.adminpanel.model.ewaybill.EWBCancelError;
import com.ats.adminpanel.model.ewaybill.EWBCancelSuccess;
import com.ats.adminpanel.model.ewaybill.EwayBillJson;
import com.ats.adminpanel.model.ewaybill.EwayBillSuccess;
import com.ats.adminpanel.model.ewaybill.EwayConstants;
import com.ats.adminpanel.model.ewaybill.EwayItemList;
import com.ats.adminpanel.model.ewaybill.GetAuthToken;
import com.ats.adminpanel.model.ewaybill.NewEwayBillJson;
import com.ats.adminpanel.model.ewaybill.NewEwayBillJsonDisplay;
import com.ats.adminpanel.model.ewaybill.NewEwayItemList;
import com.ats.adminpanel.model.ewaybill.ReqEwayBill;
import com.ats.adminpanel.model.ewaybill.ResponseCode;
import com.ats.adminpanel.model.franchisee.FranchiseeList;
import com.ats.adminpanel.model.item.ErrorMessage;

@Controller
public class EwayBillController {

	@RequestMapping(value = "/genOutwardEwayBill", method = RequestMethod.POST)
	public @ResponseBody List<CustomErrEwayBill> checkIt(HttpServletRequest request, HttpServletResponse response) {
		List<CustomErrEwayBill> errorBillList = new ArrayList<CustomErrEwayBill>();
		RestTemplate restTemplate = new RestTemplate();
		try {
			ObjectMapper mapperObj = new ObjectMapper();
			String billList = new String();
			ResponseEntity<List<BillHeadEwayBill>> bRes = null;
			String[] selectedBills = request.getParameterValues("select_to_print");
			String vehNo = request.getParameter("vehNo");
			for (int i = 0; i < selectedBills.length; i++) {
				billList = selectedBills[i] + "," + billList;
			}

			billList = billList.substring(0, billList.length() - 1);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("billIdList", billList);

			ParameterizedTypeReference<List<BillHeadEwayBill>> typeRef1 = new ParameterizedTypeReference<List<BillHeadEwayBill>>() {
			};
			try {
				bRes = restTemplate.exchange(Constants.url + "getBillListForEwaybill", HttpMethod.POST,
						new HttpEntity<>(map), typeRef1);
			} catch (HttpClientErrorException e) {
				System.err.println("/getBillListForEwaybill Http Excep \n " + e.getResponseBodyAsString());
			}
			List<BillHeadEwayBill> billHeaderList = bRes.getBody();

			// System.err.println("billHeaderList " + billHeaderList.toString());

			GetAuthToken tokenRes = null; // = restTemplate.getForObject(EwayConstants.getToken, GetAuthToken.class);
			ResponseEntity<String> tokRes = null;

			ParameterizedTypeReference<String> typeRef2 = new ParameterizedTypeReference<String>() {
			};
			try {
				tokRes = restTemplate.exchange(EwayConstants.getToken, HttpMethod.GET, new HttpEntity<>(map), typeRef2);
				try {
					tokenRes = mapperObj.readValue(tokRes.getBody(), GetAuthToken.class);
					// System.err.println("Token Res " +tokenRes.toString());
				} catch (Exception e) {
					System.err.println("Inner try for getToken" + e.getMessage());
				}
			} catch (HttpClientErrorException e) {
				System.err.println("/getToken Http Excep \n " + e.getResponseBodyAsString());
			}

			// System.err.println("tokenRes " + tokenRes.toString());

			Company company = restTemplate.getForObject(Constants.url + "/getCompany", Company.class);
			System.err.println("company " + company.toString());

			for (int i = 0; i < billHeaderList.size(); i++) {

				BillHeadEwayBill bill = billHeaderList.get(i);

				FranchiseeList franchise = restTemplate.getForObject(Constants.url + "getFranchisee?frId={frId}",
						FranchiseeList.class, bill.getFrId());

				ReqEwayBill billReq = new ReqEwayBill();

				billReq.setActFromStateCode(company.getStateCode());
				billReq.setActToStateCode(company.getStateCode());

				billReq.setCessNonAdvolValue(00);
				billReq.setCessValue(0);

				billReq.setCgstValue(bill.getCgstSum());

				billReq.setDispatchFromGSTIN(company.getGstin());
				billReq.setDispatchFromTradeName(company.getCompName());

				String billDate = new String();// bill.getBillDate().replace('-', '/');

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				billDate = df.format(new Date());
				billReq.setDocDate(billDate);
				billReq.setDocNo(bill.getInvoiceNo());

				billReq.setFromAddr1(company.getFactAddress());
				billReq.setFromAddr2("");
				billReq.setFromGstin(company.getGstin());
				billReq.setFromPincode(company.getFromPinCode());
				billReq.setFromPlace(company.getFactAddress());
				billReq.setFromStateCode(company.getStateCode());
				billReq.setFromTrdName(company.getCompName());

				billReq.setIgstValue(0);
				billReq.setOtherValue(0);
				billReq.setSgstValue(bill.getSgstSum());

				// billReq.setShipToGSTIN("29ALSPR1722R1Z3");
				// billReq.setShipToTradeName("XYZ Traders");
				// billReq.setSubSupplyDesc("ppoo");

				billReq.setSupplyType("O"); // While Selling it is O-Outward

				if (franchise.getFrKg1() == 0) {
					billReq.setSubSupplyType("1");// while selling to Other Fr -Supply(1)
					billReq.setDocType("INV");
				} else {
					billReq.setSubSupplyType("8");// while selling to Own Fr -Others(8)
					billReq.setDocType("CHL");
					billReq.setSubSupplyDesc("SU Supply");
				}

				billReq.setTransactionType(1);

				billReq.setToAddr1(franchise.getFrAddress());
				billReq.setToAddr2("");
				billReq.setToGstin(franchise.getFrGstNo());

				String[] arr = bill.getPartyAddress().split("~~");

				System.err.println("ADDRESS - " + bill.getPartyAddress());

				String pincode = "";
				int pin = 0;
				String km = "";
				String add = bill.getPartyAddress();

				if (arr.length == 2) {
					pincode = arr[1];
					pin = Integer.parseInt(pincode);

					add = arr[0];

				} else if (arr.length == 3) {
					pincode = arr[1];
					km = arr[2];
					pin = Integer.parseInt(pincode);

					add = arr[0];

				}

				System.err.println("PINCODE - " + pincode + "          KM - " + km + "         Add - " + add);

				// billReq.setToPincode(franchise.getFrKg2());
				// billReq.setTransDistance(""+franchise.getFrKg3());

				billReq.setToPincode(pin);
				billReq.setTransDistance(km);

				billReq.setToPlace(add);
				billReq.setToStateCode(company.getStateCode());

				billReq.setTotalValue(bill.getTaxableAmt());
				billReq.setTotInvValue(bill.getGrandTotal());

				billReq.setToTrdName(franchise.getFrName());

				billReq.setTransMode("1");// Road/Rail/Air/Ship

				billReq.setTransDocDate("");
				billReq.setTransDocNo("");
				billReq.setTransporterId("");
				billReq.setTransporterName("");

				billReq.setVehicleNo(vehNo);
				billReq.setVehicleType("R");

				// New 19-02-2020
				billReq.setShipToTradeName(billHeaderList.get(i).getPartyName());
				billReq.setShipToGSTIN(billHeaderList.get(i).getPartyGstin());
				// billReq.setToAddr1(billHeaderList.get(i).getPartyAddress());
				billReq.setToAddr1(add);

				billReq.setToTrdName(billHeaderList.get(i).getExVarchar3());
				// billReq.setToAddr1(billHeaderList.get(i).getExVarchar5());
				billReq.setToAddr2("");

				if (billHeaderList.get(i).getExVarchar4().length() < 1) {
					billReq.setToGstin("URP");
				} else {
					billReq.setToGstin(billHeaderList.get(i).getExVarchar4());

				}

				billReq.setItemList(billHeaderList.get(i).getItemList());

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				// String jsonStr = mapperObj.writeValueAsString(billReq);
				System.err.println("billReq " + billReq.toString());

				EwayBillSuccess ewaySuccRes = null;
				ResponseCode ewayErrRes = null;
				ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
				};
				ResponseEntity<String> responseEntity = null;

				try {
					responseEntity = restTemplate.exchange(EwayConstants.genEwayGenUrl + "" + tokenRes.getAuthtoken(),
							HttpMethod.POST, new HttpEntity<>(billReq), typeRef);

					try {
						ewaySuccRes = mapperObj.readValue(responseEntity.getBody(), EwayBillSuccess.class);
						System.err.println("ewaySuccRes " + ewaySuccRes.toString());

						map = new LinkedMultiValueMap<String, Object>();
						map.add("ewayBillNo", ewaySuccRes.getEwayBillNo());
						map.add("billNo", bill.getBillNo());

						ErrorMessage updateEwayBillNo = restTemplate
								.postForObject(Constants.url + "/tally/updateEwayBillNo", map, ErrorMessage.class);
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println("Inner Try");
					}

				} catch (HttpClientErrorException e) {

					ewayErrRes = mapperObj.readValue(e.getResponseBodyAsString(), ResponseCode.class);
					System.err.println("ewayErrRes   " + ewayErrRes.toString());
					CustomErrEwayBill errRes = new CustomErrEwayBill();

					errRes.setBillNo(bill.getBillNo());
					errRes.setInvoiceNo(bill.getInvoiceNo());
					errRes.setTimeStamp("--");
					errRes.setErrorCode(ewayErrRes.getError().getError_cd());
					errRes.setMessage(ewayErrRes.getError().getMessage());

					errorBillList.add(errRes);
				}

			} // End of Bill Header For Loop

			/*
			 * ArrayList<EwayItemList> itemList = new ArrayList<EwayItemList>();
			 * 
			 * 
			 * EwayItemList item = new EwayItemList();
			 * 
			 * item.setCessNonAdvol(item.getCessNonAdvol());
			 * item.setCessRate(item.getCessRate()); item.setCgstRate(item.getCgstRate());
			 * item.setHsnCode(item.getHsnCode()); item.setIgstRate(item.getIgstRate());
			 * item.setProductDesc(item.getProductDesc());
			 * item.setProductName(item.getProductName());
			 * item.setQtyUnit(item.getQtyUnit()); item.setQuantity(item.getQuantity());
			 * item.setSgstRate(item.getSgstRate());
			 * item.setTaxableAmount(item.getTaxableAmount());
			 * 
			 * itemList.add(item);
			 * 
			 * billReq.setItemList(itemList);
			 */
			/*
			 * "required": [ "supplyType", "subSupplyType", "docType", "docNo", "docDate",
			 * "fromGstin", "fromPincode", "fromStateCode", "toGstin", "toPincode",
			 * "toStateCode", "transDistance", "itemList", "actToStateCode",
			 * "actFromStateCode" ]
			 */

			// System.err.println("jsonStr " +jsonStr.toString());

			/*
			 * HttpEntity<String> entity = new HttpEntity<String>(jsonStr, headers);
			 * 
			 * String answer=new String(); try { answer =
			 * restTemplate.postForObject(EwayConstants.genEwayGenUrl+""+tokenRes.
			 * getAuthtoken(), billReq, String.class); //System.err.println("In Catch "
			 * +answer.toString()); }catch (Exception e) { System.err.println("In Catch "
			 * +answer.toString()); e.printStackTrace();
			 * 
			 * }
			 */

			/*
			 * EwayBillSuccess ewaySuccRes = null; ResponseCode ewayErrRes = null;
			 * ParameterizedTypeReference<String> typeRef = new
			 * ParameterizedTypeReference<String>() { }; ResponseEntity<String>
			 * responseEntity = null; HttpStatus resStatus = null; try { responseEntity =
			 * restTemplate.exchange(EwayConstants.genEwayGenUrl + "" +
			 * tokenRes.getAuthtoken(), HttpMethod.POST, new HttpEntity<>(billReq),
			 * typeRef);
			 * 
			 * try { ewaySuccRes = mapperObj.readValue(responseEntity.getBody(),
			 * EwayBillSuccess.class); System.err.println("ewaySuccRes " +
			 * ewaySuccRes.toString()); } catch (Exception e) { e.printStackTrace();
			 * System.err.println("Inner Try"); }
			 * 
			 * } catch (HttpClientErrorException e) {
			 * 
			 * ewayErrRes = mapperObj.readValue(e.getResponseBodyAsString(),
			 * ResponseCode.class); System.err.println("ewayErrRes   " +
			 * ewayErrRes.toString()); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}

		return errorBillList;

	}

	// E-Way Bill Json Format--------------------------------------

	@RequestMapping(value = "/genOutwardEwayBillJsonOld", method = RequestMethod.GET)
	public @ResponseBody EwayBillJson genOutwardEwayBillJsonOld(HttpServletRequest request,
			HttpServletResponse response) {

		System.err.println("---------------genOutwardEwayBillJson-------------");

		EwayBillJson jsonBill = new EwayBillJson();

		RestTemplate restTemplate = new RestTemplate();

		ReqEwayBill billReq = new ReqEwayBill();

		try {
			ObjectMapper mapperObj = new ObjectMapper();
			String billList = new String();
			ResponseEntity<List<BillHeadEwayBill>> bRes = null;
			// String[] selectedBills = request.getParameterValues("select_to_print");
			String vehNo = request.getParameter("veh");
			// for (int i = 0; i < selectedBills.length; i++) {
			// billList = selectedBills[i] + "," + billList;
			// }

			// billList = billList.substring(0, billList.length() - 1);
			String billNo = request.getParameter("billNo");
			billList = billNo;
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("billIdList", billList);

			ParameterizedTypeReference<List<BillHeadEwayBill>> typeRef1 = new ParameterizedTypeReference<List<BillHeadEwayBill>>() {
			};
			try {
				bRes = restTemplate.exchange(Constants.url + "getBillListForEwaybill", HttpMethod.POST,
						new HttpEntity<>(map), typeRef1);
			} catch (HttpClientErrorException e) {
				System.err.println("/getBillListForEwaybill Http Excep \n " + e.getResponseBodyAsString());
			}
			List<BillHeadEwayBill> billHeaderList = bRes.getBody();

			System.err.println("billHeaderList " + billHeaderList.toString());

			GetAuthToken tokenRes = null; // = restTemplate.getForObject(EwayConstants.getToken, GetAuthToken.class);
//			ResponseEntity<String> tokRes = null;

//			ParameterizedTypeReference<String> typeRef2 = new ParameterizedTypeReference<String>() {
//			};
//			try {
//				tokRes = restTemplate.exchange(EwayConstants.getToken, HttpMethod.GET, new HttpEntity<>(map), typeRef2);
//				try {
//					tokenRes = mapperObj.readValue(tokRes.getBody(), GetAuthToken.class);
//					 System.err.println("Token Res " +tokenRes.toString());
//				} catch (Exception e) {
//					System.err.println("Inner try for getToken" + e.getMessage());
//				}
//			} catch (HttpClientErrorException e) {
//				System.err.println("/getToken Http Excep \n " + e.getResponseBodyAsString());
//			}

//			 System.err.println("tokenRes " + tokenRes.toString());

			Company company = restTemplate.getForObject(Constants.url + "/getCompany", Company.class);
			System.err.println("company " + company.toString());

			for (int i = 0; i < billHeaderList.size(); i++) {

				BillHeadEwayBill bill = billHeaderList.get(i);

				FranchiseeList franchise = restTemplate.getForObject(Constants.url + "getFranchisee?frId={frId}",
						FranchiseeList.class, bill.getFrId());

				// ReqEwayBill billReq = new ReqEwayBill();

				billReq.setActFromStateCode(company.getStateCode());
				billReq.setActToStateCode(company.getStateCode());

				billReq.setCessNonAdvolValue(00);
				billReq.setCessValue(0);

				billReq.setCgstValue(bill.getCgstSum());

				billReq.setDispatchFromGSTIN(company.getGstin());
				billReq.setDispatchFromTradeName(company.getCompName());

				String billDate = new String();// bill.getBillDate().replace('-', '/');

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				billDate = df.format(new Date());
				billReq.setDocDate(billDate);
				billReq.setDocNo(bill.getInvoiceNo());

				billReq.setFromAddr1(company.getFactAddress());
				billReq.setFromAddr2("");
				billReq.setFromGstin(company.getGstin());
				billReq.setFromPincode(company.getFromPinCode());
				billReq.setFromPlace(company.getFactAddress());
				billReq.setFromStateCode(company.getStateCode());
				billReq.setFromTrdName(company.getCompName());

				billReq.setIgstValue(0);
				billReq.setOtherValue(0);
				billReq.setSgstValue(bill.getSgstSum());

				// billReq.setShipToGSTIN("29ALSPR1722R1Z3");
				// billReq.setShipToTradeName("XYZ Traders");
				// billReq.setSubSupplyDesc("ppoo");

				billReq.setSupplyType("O"); // While Selling it is O-Outward

				if (franchise.getFrKg1() == 0) {
					billReq.setSubSupplyType("1");// while selling to Other Fr -Supply(1)
					billReq.setDocType("INV");
				} else {
					billReq.setSubSupplyType("8");// while selling to Own Fr -Others(8)
					billReq.setDocType("CHL");
					billReq.setSubSupplyDesc("SU Supply");
				}

				billReq.setTransactionType(1);

				billReq.setToAddr1(franchise.getFrAddress());
				billReq.setToAddr2("");
				billReq.setToGstin(franchise.getFrGstNo());

				String[] arr = bill.getPartyAddress().split("~~");

				System.err.println("ADDRESS - " + bill.getPartyAddress());

				String pincode = "";
				int pin = 0;
				String km = "";
				String add = bill.getPartyAddress();

				if (arr.length == 2) {
					pincode = arr[1];
					pin = Integer.parseInt(pincode);

					add = arr[0];

				} else if (arr.length == 3) {
					pincode = arr[1];
					km = arr[2];
					pin = Integer.parseInt(pincode);

					add = arr[0];

				}

				System.err.println("PINCODE - " + pincode + "          KM - " + km + "         Add - " + add);

				// billReq.setToPincode(franchise.getFrKg2());
				// billReq.setTransDistance(""+franchise.getFrKg3());

				billReq.setToPincode(pin);
				billReq.setTransDistance(km);

				billReq.setToPlace(add);
				billReq.setToStateCode(company.getStateCode());

				billReq.setTotalValue(bill.getTaxableAmt());
				billReq.setTotInvValue(bill.getGrandTotal());

				billReq.setToTrdName(franchise.getFrName());

				billReq.setTransMode("1");// Road/Rail/Air/Ship

				billReq.setTransDocDate("");
				billReq.setTransDocNo("");
				billReq.setTransporterId("");
				billReq.setTransporterName("");

				billReq.setVehicleNo(vehNo);
				billReq.setVehicleType("R");

				// New 19-02-2020
				billReq.setShipToTradeName(billHeaderList.get(i).getPartyName());
				billReq.setShipToGSTIN(billHeaderList.get(i).getPartyGstin());
				// billReq.setToAddr1(billHeaderList.get(i).getPartyAddress());
				billReq.setToAddr1(add);

				billReq.setToTrdName(billHeaderList.get(i).getExVarchar3());
				// billReq.setToAddr1(billHeaderList.get(i).getExVarchar5());
				billReq.setToAddr2("");

				if (billHeaderList.get(i).getExVarchar4().length() < 1) {
					billReq.setToGstin("URP");
				} else {
					billReq.setToGstin(billHeaderList.get(i).getExVarchar4());

				}

				billReq.setItemList(billHeaderList.get(i).getItemList());

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				System.err.println("billReq " + billReq.toString());

				ArrayList<ReqEwayBill> reqBillList = new ArrayList<>();
				reqBillList.add(billReq);

				jsonBill.setBillLists(reqBillList);
				jsonBill.setVersion("1.0.0219");

			} // End of Bill Header For Loop

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonBill;

	}

	// -------------------------------------------------------------

	// NEW JSON FORMAT

	@RequestMapping(value = "/genOutwardEwayBillJson", method = RequestMethod.GET)
	public @ResponseBody Info genOutwardEwayBillJson(HttpServletRequest request, HttpServletResponse response) {

		Info info=new Info();
	
		
		NewEwayBillJsonDisplay jsonBill = new NewEwayBillJsonDisplay();

		RestTemplate restTemplate = new RestTemplate();

		NewEwayBillJson billReq = new NewEwayBillJson();

		try {
			ObjectMapper mapperObj = new ObjectMapper();
			String billList = new String();
			ResponseEntity<List<BillHeadEwayBill>> bRes = null;
			String vehNo = request.getParameter("veh");
			String billNo = request.getParameter("billNo");
			billList = billNo;
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("billIdList", billList);

			ParameterizedTypeReference<List<BillHeadEwayBill>> typeRef1 = new ParameterizedTypeReference<List<BillHeadEwayBill>>() {
			};
			try {
				bRes = restTemplate.exchange(Constants.url + "getBillListForEwaybill", HttpMethod.POST,
						new HttpEntity<>(map), typeRef1);
			} catch (HttpClientErrorException e) {
				// System.err.println("/getBillListForEwaybill Http Excep \n " +
				// e.getResponseBodyAsString());
			}
			List<BillHeadEwayBill> billHeaderList = bRes.getBody();

			System.err.println("billHeaderList " + billHeaderList.toString());

			Company company = restTemplate.getForObject(Constants.url + "/getCompany", Company.class);
			System.err.println("company " + company.toString());

			for (int i = 0; i < billHeaderList.size(); i++) {

				BillHeadEwayBill bill = billHeaderList.get(i);

				FranchiseeList franchise = restTemplate.getForObject(Constants.url + "getFranchisee?frId={frId}",
						FranchiseeList.class, bill.getFrId());

				billReq.setActualFromStateCode(company.getStateCode());
				billReq.setActualToStateCode(company.getStateCode());

				billReq.setTotNonAdvolVal(00);
				billReq.setCessValue(0);

				billReq.setCgstValue(bill.getCgstSum());

				String billDate = new String();// bill.getBillDate().replace('-', '/');

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				billDate = df.format(new Date());
				billReq.setDocDate(billDate);
				billReq.setDocNo(bill.getInvoiceNo());

				billReq.setUserGstin(company.getGstin());
				
				billReq.setFromAddr1(company.getFactAddress());
				billReq.setFromAddr2("");
				billReq.setFromGstin(company.getGstin());
				billReq.setFromPincode(company.getFromPinCode());
				billReq.setFromPlace("");
				billReq.setFromStateCode(company.getStateCode());
				billReq.setFromTrdName(company.getCompName());

				billReq.setIgstValue(0);
				billReq.setOthValue(0);
				billReq.setSgstValue(bill.getSgstSum());

				billReq.setSupplyType("O"); // While Selling it is O-Outward

				if (franchise.getFrKg1() == 0) {
					billReq.setSubSupplyType("1");// while selling to Other Fr -Supply(1)
					billReq.setDocType("INV");
				} else {
					billReq.setSubSupplyType("8");// while selling to Own Fr -Others(8)
					billReq.setDocType("CHL");
				}

				billReq.setTransType(1);

				billReq.setToAddr1(franchise.getFrAddress());
				billReq.setToAddr2("");
				billReq.setToGstin(franchise.getFrGstNo());

				String[] arr = bill.getPartyAddress().split("~~");

				System.err.println("ADDRESS - " + bill.getPartyAddress());

				String pincode = "";
				int pin = 0;
				String km = "";
				String add = bill.getPartyAddress();

				if (arr.length == 2) {
					pincode = arr[1];
					pin = Integer.parseInt(pincode);

					add = arr[0];

				} else if (arr.length == 3) {
					pincode = arr[1];
					km = arr[2];
					pin = Integer.parseInt(pincode);

					add = arr[0];

				}

				// System.err.println("PINCODE - " + pincode + " KM - " + km + " Add - " + add);

				billReq.setToPincode(pin);
				billReq.setTransDistance(km);

				billReq.setToPlace("");
				billReq.setToStateCode(company.getStateCode());

				billReq.setTotalValue(bill.getTaxableAmt());
				billReq.setTotInvValue(bill.getGrandTotal());

				billReq.setToTrdName(franchise.getFrName());

				billReq.setTransMode("1");// Road/Rail/Air/Ship

				billReq.setTransDocDate(billDate);
				billReq.setTransDocNo("");
				billReq.setTransporterId("");
				billReq.setTransporterName("");

				billReq.setVehicleNo(vehNo);
				billReq.setVehicleType("R");

				billReq.setToAddr1(add);
				billReq.setToTrdName(billHeaderList.get(i).getExVarchar3());
				billReq.setToAddr2("");
				if (billHeaderList.get(i).getExVarchar4().length() < 1) {
					billReq.setToGstin("URP");
				} else {
					billReq.setToGstin(billHeaderList.get(i).getExVarchar4());
				}
				
				billReq.setMainHsnCode(404);
				
				ArrayList<NewEwayItemList> itemList=new ArrayList<>();

				if (billHeaderList.get(i).getItemList() != null) {
					if (billHeaderList.get(i).getItemList().size() > 0) {
						for (int j = 0; j < billHeaderList.get(i).getItemList().size(); j++) {
							NewEwayItemList item=new NewEwayItemList(j+1,billHeaderList.get(i).getItemList().get(j).getProductName(),String.valueOf(billHeaderList.get(i).getItemList().get(j).getHsnCode()),billHeaderList.get(i).getItemList().get(j).getHsnCode(),billHeaderList.get(i).getItemList().get(j).getQuantity(),billHeaderList.get(i).getItemList().get(j).getQtyUnit().toUpperCase(),billHeaderList.get(i).getItemList().get(j).getTaxableAmount(),billHeaderList.get(i).getItemList().get(j).getSgstRate(),billHeaderList.get(i).getItemList().get(j).getCgstRate(),billHeaderList.get(i).getItemList().get(j).getIgstRate(),billHeaderList.get(i).getItemList().get(j).getCessRate(),billHeaderList.get(i).getItemList().get(j).getCessNonAdvol());
							itemList.add(item);
						}
					}
				}

				billReq.setItemList(itemList);

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				

				ArrayList<NewEwayBillJson> reqBillList = new ArrayList<>();
				reqBillList.add(billReq);

				jsonBill.setBillLists(reqBillList);
				jsonBill.setVersion("1.0.0219");
				
			

			} // End of Bill Header For Loop
			
			ObjectMapper Obj = new ObjectMapper();
			String json = "";
			try {
				json = Obj.writeValueAsString(jsonBill);
			} catch (JsonGenerationException e1) {
				e1.printStackTrace();
			} catch (JsonMappingException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			if (json != null) {

				try {
					Writer output = null;
					File file = new File(
							Constants.TALLY_SAVE + "EwayBill.json");
					output = new BufferedWriter(new FileWriter(file));
					output.write(json.toString());
					output.close();

					String data = Constants.TALLY_VIEW + "EwayBill.zip";
					String fileName = Constants.TALLY_SAVE + "EwayBill.zip";
					String sourceFile = Constants.TALLY_SAVE + "EwayBill.json";
					
					FileOutputStream fos = new FileOutputStream(fileName);
					ZipOutputStream zipOut = new ZipOutputStream(fos);
					File fileToZip = new File(sourceFile);
					FileInputStream fis = new FileInputStream(fileToZip);
					ZipEntry zipEntry = new ZipEntry(fileToZip.getName());

					// System.err.println(fileToZip.getName());

					zipOut.putNextEntry(zipEntry);
					byte[] bytes = new byte[1024];
					int length;
					while ((length = fis.read(bytes)) >= 0) {
						zipOut.write(bytes, 0, length);
					}
					zipOut.close();
					fis.close();
					fos.close();

					info.setError(false);
					info.setMessage(data);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
					info.setError(true);
					info.setMessage("Download Failed!");

				} catch (Exception e) {
					e.printStackTrace();

					info.setError(true);
					info.setMessage("Download Failed!");
				}

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	

		return info;

	}

	// ------------------------------------------------------------

	// Anmol
	@RequestMapping(value = "/genInEwayBill", method = RequestMethod.POST)
	public @ResponseBody List<CustomErrEwayBill> genInEwayBill(HttpServletRequest request,
			HttpServletResponse response) {
		List<CustomErrEwayBill> errorBillList = new ArrayList<CustomErrEwayBill>();
		RestTemplate restTemplate = new RestTemplate();
		try {
			ObjectMapper mapperObj = new ObjectMapper();
			String billList = new String();
			ResponseEntity<List<BillHeadEwayBill>> bRes = null;
			String[] selectedBills = request.getParameterValues("select_to_print");
			String vehNo = request.getParameter("vehNo");
			for (int i = 0; i < selectedBills.length; i++) {
				billList = selectedBills[i] + "," + billList;
			}

			billList = billList.substring(0, billList.length() - 1);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("billIdList", billList);

			ParameterizedTypeReference<List<BillHeadEwayBill>> typeRef1 = new ParameterizedTypeReference<List<BillHeadEwayBill>>() {
			};
			try {
				bRes = restTemplate.exchange(Constants.url + "getGrnGvnListForEwaybill", HttpMethod.POST,
						new HttpEntity<>(map), typeRef1);
			} catch (HttpClientErrorException e) {
				System.err.println("/getBillListForEwaybill Http Excep \n " + e.getResponseBodyAsString());
			}
			List<BillHeadEwayBill> billHeaderList = bRes.getBody();

			// System.err.println("billHeaderList " + billHeaderList.toString());

			GetAuthToken tokenRes = null; // = restTemplate.getForObject(EwayConstants.getToken, GetAuthToken.class);
			ResponseEntity<String> tokRes = null;

			ParameterizedTypeReference<String> typeRef2 = new ParameterizedTypeReference<String>() {
			};
			try {
				tokRes = restTemplate.exchange(EwayConstants.getToken, HttpMethod.GET, new HttpEntity<>(map), typeRef2);
				try {
					tokenRes = mapperObj.readValue(tokRes.getBody(), GetAuthToken.class);
					// System.err.println("Token Res " +tokenRes.toString());
				} catch (Exception e) {
					System.err.println("Inner try for getToken" + e.getMessage());
				}
			} catch (HttpClientErrorException e) {
				System.err.println("/getToken Http Excep \n " + e.getResponseBodyAsString());
			}

			// System.err.println("tokenRes " + tokenRes.toString());

			Company company = restTemplate.getForObject(Constants.url + "/getCompany", Company.class);
			System.err.println("company " + company.toString());

			for (int i = 0; i < billHeaderList.size(); i++) {

				BillHeadEwayBill bill = billHeaderList.get(i);
				System.err.println("GRN ----------- " + bill);

				FranchiseeList franchise = restTemplate.getForObject(Constants.url + "getFranchisee?frId={frId}",
						FranchiseeList.class, bill.getFrId());

				ReqEwayBill billReq = new ReqEwayBill();

				billReq.setActFromStateCode(company.getStateCode());
				billReq.setActToStateCode(company.getStateCode());

				billReq.setCessNonAdvolValue(00);
				billReq.setCessValue(0);

				billReq.setCgstValue(bill.getCgstSum());

				billReq.setDispatchFromGSTIN(bill.getPartyGstin());
				billReq.setDispatchFromTradeName(bill.getPartyName());

				String billDate = new String();// bill.getBillDate().replace('-', '/');

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				billDate = df.format(new Date());
				billReq.setDocDate(billDate);
				billReq.setDocNo(bill.getInvoiceNo());

				String[] arr = bill.getPartyAddress().split("~~");

				System.err.println("ADDRESS - " + bill.getPartyAddress());

				String pincode = "";
				int pin = 0;
				String km = "";
				String add = bill.getPartyAddress();

				if (arr.length == 2) {
					pincode = arr[1];
					pin = Integer.parseInt(pincode);

					add = arr[0];

				} else if (arr.length == 3) {
					pincode = arr[1];
					km = arr[2];
					pin = Integer.parseInt(pincode);

					add = arr[0];

				}
				System.err.println("PINCODE - " + pincode + "          KM - " + km + "         Add - " + add);

				billReq.setFromAddr1(add);
				billReq.setFromAddr2("");
				billReq.setFromGstin(bill.getPartyGstin());
				billReq.setFromPincode(pin);
				billReq.setFromPlace(add);
				billReq.setFromStateCode(company.getStateCode());
				billReq.setFromTrdName(bill.getPartyName());

				billReq.setIgstValue(0);
				billReq.setOtherValue(0);
				billReq.setSgstValue(bill.getSgstSum());

				billReq.setSupplyType("I");

				if (franchise.getFrKg1() == 0) {
					billReq.setSubSupplyType("7");// while selling to Other Fr -Supply(7)
					billReq.setDocType("CHL");
				} else {
					billReq.setSubSupplyType("8");// while selling to Own Fr -Others(8)
					billReq.setDocType("CHL");
					billReq.setSubSupplyDesc("ReturnFromOwnOutlet");
				}

				billReq.setTransactionType(1);

				billReq.setToAddr1(company.getFactAddress());
				billReq.setToAddr2("");
				billReq.setToGstin(company.getGstin());

				// billReq.setToPincode(franchise.getFrKg2());
				// billReq.setTransDistance(""+franchise.getFrKg3());

				billReq.setToPincode(company.getFromPinCode());
				billReq.setTransDistance(km);

				billReq.setToPlace(company.getFactAddress());
				billReq.setToStateCode(company.getStateCode());

				billReq.setTotalValue(bill.getTaxableAmt());
				billReq.setTotInvValue(bill.getGrandTotal());

				billReq.setToTrdName(company.getCompName());

				billReq.setTransMode("1");// Road/Rail/Air/Ship

				billReq.setTransDocDate("");
				billReq.setTransDocNo("");
				billReq.setTransporterId("");
				billReq.setTransporterName("");

				billReq.setVehicleNo(vehNo);
				billReq.setVehicleType("R");

				// New 19-02-2020
				billReq.setShipToTradeName(company.getCompName());
				billReq.setShipToGSTIN(company.getGstin());
				billReq.setToAddr1(company.getFactAddress());

				billReq.setToTrdName(company.getCompName());
				billReq.setToAddr2("");

				if (billHeaderList.get(i).getExVarchar4().length() < 1) {
					billReq.setToGstin("URP");
				} else {
					billReq.setToGstin(company.getGstin());

				}

				System.err.println("LIST - " + billHeaderList.get(i).getItemList());

//				ArrayList<EwayItemList> itemList = new ArrayList<>();
//				if (billHeaderList.get(i).getItemList().size() > 0) {
//					for (int k = 0; k < billHeaderList.get(i).getItemList().size(); k++) {
//						EwayItemList item = new EwayItemList(
//								billHeaderList.get(i).getItemList().get(k).getBillDetailNo(),
//								billHeaderList.get(i).getItemList().get(k).getProductName(),
//								billHeaderList.get(i).getItemList().get(k).getProductDesc(),
//								billHeaderList.get(i).getItemList().get(k).getHsnCode(),
//								billHeaderList.get(i).getItemList().get(k).getQuantity(),
//								billHeaderList.get(i).getItemList().get(k).getQtyUnit(), 6, 6, 0, 0, 0,
//								billHeaderList.get(i).getItemList().get(k).getTaxableAmount());
//						itemList.add(item);
//					}
//				}

				billReq.setItemList(billHeaderList.get(i).getItemList());
				// billReq.setItemList(itemList);

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				// String jsonStr = mapperObj.writeValueAsString(billReq);
				System.err.println("billReq " + billReq.toString());

				EwayBillSuccess ewaySuccRes = null;
				ResponseCode ewayErrRes = null;
				ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
				};
				ResponseEntity<String> responseEntity = null;

				try {
					responseEntity = restTemplate.exchange(EwayConstants.genEwayGenUrl + "" + tokenRes.getAuthtoken(),
							HttpMethod.POST, new HttpEntity<>(billReq), typeRef);

					try {
						ewaySuccRes = mapperObj.readValue(responseEntity.getBody(), EwayBillSuccess.class);
						System.err.println("ewaySuccRes " + ewaySuccRes.toString());

						map = new LinkedMultiValueMap<String, Object>();
						map.add("ewayBillNo", ewaySuccRes.getEwayBillNo());
						map.add("billNo", bill.getBillNo());

						ErrorMessage updateEwayBillNo = restTemplate
								.postForObject(Constants.url + "/updateEwayBillNoForGrnGvn", map, ErrorMessage.class);
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println("Inner Try");
					}

				} catch (HttpClientErrorException e) {

					ewayErrRes = mapperObj.readValue(e.getResponseBodyAsString(), ResponseCode.class);
					System.err.println("ewayErrRes   " + ewayErrRes.toString());
					CustomErrEwayBill errRes = new CustomErrEwayBill();

					errRes.setBillNo(bill.getBillNo());
					errRes.setInvoiceNo(bill.getInvoiceNo());
					errRes.setTimeStamp("--");
					errRes.setErrorCode(ewayErrRes.getError().getError_cd());
					errRes.setMessage(ewayErrRes.getError().getMessage());

					errorBillList.add(errRes);
				}

			} // End of Bill Header For Loop

		} catch (Exception e) {
			e.printStackTrace();
		}

		return errorBillList;

	}

	@RequestMapping(value = "/genInwardEwayBill", method = RequestMethod.POST)
	public @ResponseBody List<CustomErrEwayBill> genInwardEwayBill(HttpServletRequest request,
			HttpServletResponse response) {
		List<CustomErrEwayBill> errorBillList = new ArrayList<CustomErrEwayBill>();
		RestTemplate restTemplate = new RestTemplate();
		try {
			ObjectMapper mapperObj = new ObjectMapper();
			String crnIdList = new String();
			ResponseEntity<List<BillHeadEwayBill>> bRes = null;
			String[] selectedCrns = request.getParameterValues("select_to_agree");
			String vehNo = request.getParameter("vehNo");
			for (int i = 0; i < selectedCrns.length; i++) {
				crnIdList = selectedCrns[i] + "," + crnIdList;
			}

			crnIdList = crnIdList.substring(0, crnIdList.length() - 1);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("crnIdList", crnIdList);

			ParameterizedTypeReference<List<BillHeadEwayBill>> typeRef1 = new ParameterizedTypeReference<List<BillHeadEwayBill>>() {
			};
			try {
				bRes = restTemplate.exchange(Constants.url + "getCreditListForEwaybill", HttpMethod.POST,
						new HttpEntity<>(map), typeRef1);
			} catch (HttpClientErrorException e) {
				System.err.println("/getBillListForEwaybill Http Excep \n " + e.getResponseBodyAsString());
			}
			List<BillHeadEwayBill> billHeaderList = bRes.getBody();

			// System.err.println("billHeaderList " + billHeaderList.toString());

			GetAuthToken tokenRes = null; // = restTemplate.getForObject(EwayConstants.getToken, GetAuthToken.class);
			ResponseEntity<String> tokRes = null;

			ParameterizedTypeReference<String> typeRef2 = new ParameterizedTypeReference<String>() {
			};
			try {
				tokRes = restTemplate.exchange(EwayConstants.getToken, HttpMethod.GET, new HttpEntity<>(map), typeRef2);
				try {
					tokenRes = mapperObj.readValue(tokRes.getBody(), GetAuthToken.class);
					// System.err.println("Token Res " +tokenRes.toString());
				} catch (Exception e) {
					System.err.println("Inner try for getToken" + e.getMessage());
				}
			} catch (HttpClientErrorException e) {
				System.err.println("/getToken Http Excep \n " + e.getResponseBodyAsString());
			}

			// System.err.println("tokenRes " + tokenRes.toString());

			Company company = restTemplate.getForObject(Constants.url + "/getCompany", Company.class);
			System.err.println("company " + company.toString());

			for (int i = 0; i < billHeaderList.size(); i++) {

				BillHeadEwayBill bill = billHeaderList.get(i);

				FranchiseeList franchise = restTemplate.getForObject(Constants.url + "getFranchisee?frId={frId}",
						FranchiseeList.class, bill.getFrId());

				ReqEwayBill billReq = new ReqEwayBill();

				billReq.setActFromStateCode(company.getStateCode());
				billReq.setActToStateCode(company.getStateCode());

				billReq.setCessNonAdvolValue(00);
				billReq.setCessValue(0);

				billReq.setDispatchFromGSTIN(franchise.getFrGstNo());
				billReq.setDispatchFromTradeName(franchise.getFrName());

				String billDate = new String();
				// bill.getBillDate().replace('-', '/');
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				billDate = df.format(new Date());
				billReq.setDocDate(billDate);
				billReq.setDocNo(bill.getInvoiceNo());

				billReq.setFromAddr1(franchise.getFrAddress());
				billReq.setFromAddr2("");
				billReq.setFromGstin(franchise.getFrGstNo());
				billReq.setFromPincode(franchise.getFrKg2());
				billReq.setFromPlace(franchise.getFrCity());
				billReq.setFromStateCode(company.getStateCode());
				billReq.setFromTrdName(franchise.getFrName());

				billReq.setIgstValue(bill.getIgstSum());
				billReq.setOtherValue(0);
				billReq.setCgstValue(bill.getCgstSum());
				billReq.setSgstValue(bill.getSgstSum());

				billReq.setSupplyType("I"); // While Return it is -Inward

				if (franchise.getFrKg1() == 0) {
					billReq.setSubSupplyType("7");// while RETURN FOR OTHER Fr CNT 7 FR GRN GVN
					billReq.setDocType("CHL");
				} else {
					billReq.setSubSupplyType("8");// while RETURN to Own Fr -CHL(8)
					billReq.setDocType("CHL");
					// billReq.setSubSupplyDesc("SU Supply");
				}

				billReq.setTransactionType(1); // need to discuss for Dairy Mart Sumit Sir

				billReq.setToAddr1(company.getFactAddress());
				billReq.setToAddr2("");
				billReq.setToGstin(company.getGstin());
				billReq.setToPincode(company.getFromPinCode());
				billReq.setToPlace(" ");
				billReq.setToStateCode(company.getStateCode());

				billReq.setTotalValue(bill.getTaxableAmt());
				billReq.setTotInvValue(bill.getGrandTotal());

				billReq.setToTrdName(company.getCompName());

				billReq.setTransMode("1");// Road/Rail/Air/Ship

				billReq.setTransDistance("" + franchise.getFrKg3());
				billReq.setTransDocDate("");
				billReq.setTransDocNo("");
				billReq.setTransporterId("");
				billReq.setTransporterName("");

				billReq.setVehicleNo(vehNo);
				billReq.setVehicleType("R");

				billReq.setItemList(billHeaderList.get(i).getItemList());

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				String jsonStr = mapperObj.writeValueAsString(billReq);
				System.err.println("billReq " + billReq.toString());

				EwayBillSuccess ewaySuccRes = null;
				ResponseCode ewayErrRes = null;
				ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
				};
				ResponseEntity<String> responseEntity = null;

				try {
					responseEntity = restTemplate.exchange(EwayConstants.genEwayGenUrl + "" + tokenRes.getAuthtoken(),
							HttpMethod.POST, new HttpEntity<>(billReq), typeRef);

					try {
						ewaySuccRes = mapperObj.readValue(responseEntity.getBody(), EwayBillSuccess.class);
						System.err.println("ewaySuccRes " + ewaySuccRes.toString());

						map = new LinkedMultiValueMap<String, Object>();
						map.add("ewayBillNo", ewaySuccRes.getEwayBillNo());
						map.add("crnId", bill.getBillNo());

						ErrorMessage updateEwayBillNo = restTemplate
								.postForObject(Constants.url + "/updateEwayBillNoInCNote", map, ErrorMessage.class);
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println("Inner Try");
					}

				} catch (HttpClientErrorException e) {

					ewayErrRes = mapperObj.readValue(e.getResponseBodyAsString(), ResponseCode.class);
					System.err.println("ewayErrRes   " + ewayErrRes.toString());
					CustomErrEwayBill errRes = new CustomErrEwayBill();

					errRes.setBillNo(bill.getBillNo());
					errRes.setInvoiceNo(bill.getInvoiceNo());
					errRes.setTimeStamp("--");
					errRes.setErrorCode(ewayErrRes.getError().getError_cd());
					errRes.setMessage(ewayErrRes.getError().getMessage());

					errorBillList.add(errRes);
				}

			} // End of Bill Header For Loop

		} catch (Exception e) {
			e.printStackTrace();
		}

		return errorBillList;

	}

	@RequestMapping(value = "/cancelEWB", method = RequestMethod.POST)
	public @ResponseBody List<CustomErrEwayBill> cancelEWB(HttpServletRequest request, HttpServletResponse response) {
		List<CustomErrEwayBill> errorBillList = new ArrayList<CustomErrEwayBill>();
		RestTemplate restTemplate = new RestTemplate();
		try {
			ObjectMapper mapperObj = new ObjectMapper();
			String billList = new String();
			ResponseEntity<List<BillHeadEwayBill>> bRes = null;
			String[] selectedBills = request.getParameterValues("select_to_print");
			for (int i = 0; i < selectedBills.length; i++) {
				billList = selectedBills[i] + "," + billList;
			}

			String remark = request.getParameter("cancelRemark");

			billList = billList.substring(0, billList.length() - 1);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("billIdList", billList);

			ParameterizedTypeReference<List<BillHeadEwayBill>> typeRef1 = new ParameterizedTypeReference<List<BillHeadEwayBill>>() {
			};
			try {
				bRes = restTemplate.exchange(Constants.url + "getBillListForEwaybill", HttpMethod.POST,
						new HttpEntity<>(map), typeRef1);
			} catch (HttpClientErrorException e) {
				System.err.println("/getBillListForEwaybill Http Excep \n " + e.getResponseBodyAsString());
			}
			List<BillHeadEwayBill> billHeaderList = bRes.getBody();

			// System.err.println("billHeaderList " + billHeaderList.toString());

			GetAuthToken tokenRes = null; // = restTemplate.getForObject(EwayConstants.getToken, GetAuthToken.class);
			ResponseEntity<String> tokRes = null;

			ParameterizedTypeReference<String> typeRef2 = new ParameterizedTypeReference<String>() {
			};
			try {
				tokRes = restTemplate.exchange(EwayConstants.getToken, HttpMethod.GET, new HttpEntity<>(map), typeRef2);
				try {
					tokenRes = mapperObj.readValue(tokRes.getBody(), GetAuthToken.class);
					// System.err.println("Token Res " +tokenRes.toString());
				} catch (Exception e) {
					System.err.println("Inner try for getToken" + e.getMessage());
				}
			} catch (HttpClientErrorException e) {
				System.err.println("/getToken Http Excep \n " + e.getResponseBodyAsString());
			}

			for (int i = 0; i < billHeaderList.size(); i++) {

				BillHeadEwayBill bill = billHeaderList.get(i);

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				// ------------------------------------------------------------

				CancelEWBModel cancelMod = new CancelEWBModel();
				cancelMod.setEwbNo(bill.getEwbNo());
				cancelMod.setCancelRsnCode(2);
				// cancelMod.setCancelRmrk("Cancelled the order");
				if (remark == null) {
					remark = "Cancelled the order";
				}
				cancelMod.setCancelRmrk(remark);

				System.err.println("CANCEL REQ " + cancelMod.toString());

				EWBCancelSuccess success = null;
				EWBCancelError ewayErrRes = null;

				// ------------------------------------------------------------

				ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
				};
				ResponseEntity<String> responseEntity = null;

				try {
					responseEntity = restTemplate.exchange(EwayConstants.cancelEWBUrl + "" + tokenRes.getAuthtoken(),
							HttpMethod.POST, new HttpEntity<>(cancelMod), typeRef);

					try {
						success = mapperObj.readValue(responseEntity.getBody(), EWBCancelSuccess.class);
						System.err.println("ewaySuccRes " + success.toString());

						map = new LinkedMultiValueMap<String, Object>();
						map.add("ewayBillNo", 0);
						// map.add("billNo", bill.getBillNo());
						map.add("billNo", bill.getBillNo());

						ErrorMessage updateEwayBillNo = restTemplate
								.postForObject(Constants.url + "/tally/updateEwayBillNo", map, ErrorMessage.class);
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println("Inner Try");
					}

				} catch (HttpClientErrorException e) {

					System.err.println("ewayErrRes   " + e.getResponseBodyAsString());
					e.printStackTrace();

					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(e.getResponseBodyAsString());
					System.err.println("JSON ERROR   " + json.get("error"));

					// ewayErrRes = mapperObj.readValue(e.getResponseBodyAsString(),
					// EWBCancelError.class);
					// System.err.println("ewayErrRes " + ewayErrRes.toString());
					CustomErrEwayBill errRes = new CustomErrEwayBill();

					errRes.setBillNo(bill.getBillNo());
					errRes.setInvoiceNo(bill.getInvoiceNo());
					errRes.setTimeStamp("--");
					errRes.setErrorCode(String.valueOf(json.get("error")));
					errRes.setMessage("error!");

					errorBillList.add(errRes);
				}

			} // End of Bill Header For Loop

		} catch (Exception e) {
			e.printStackTrace();
		}

		return errorBillList;

	}

	@RequestMapping(value = "/cancelEWBForGrnGvn", method = RequestMethod.POST)
	public @ResponseBody List<CustomErrEwayBill> cancelEWBForGrnGvn(HttpServletRequest request,
			HttpServletResponse response) {
		List<CustomErrEwayBill> errorBillList = new ArrayList<CustomErrEwayBill>();
		RestTemplate restTemplate = new RestTemplate();
		try {
			ObjectMapper mapperObj = new ObjectMapper();
			String billList = new String();
			ResponseEntity<List<BillHeadEwayBill>> bRes = null;
			String[] selectedBills = request.getParameterValues("select_to_print");
			for (int i = 0; i < selectedBills.length; i++) {
				billList = selectedBills[i] + "," + billList;
			}

			String remark = request.getParameter("cancelRemark");

			billList = billList.substring(0, billList.length() - 1);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("billIdList", billList);

			ParameterizedTypeReference<List<BillHeadEwayBill>> typeRef1 = new ParameterizedTypeReference<List<BillHeadEwayBill>>() {
			};
			try {
				bRes = restTemplate.exchange(Constants.url + "getGrnGvnListForEwaybill", HttpMethod.POST,
						new HttpEntity<>(map), typeRef1);
			} catch (HttpClientErrorException e) {
				System.err.println("/getGrnGvnListForEwaybill Http Excep \n " + e.getResponseBodyAsString());
			}
			List<BillHeadEwayBill> billHeaderList = bRes.getBody();

			// System.err.println("billHeaderList " + billHeaderList.toString());

			GetAuthToken tokenRes = null; // = restTemplate.getForObject(EwayConstants.getToken, GetAuthToken.class);
			ResponseEntity<String> tokRes = null;

			ParameterizedTypeReference<String> typeRef2 = new ParameterizedTypeReference<String>() {
			};
			try {
				tokRes = restTemplate.exchange(EwayConstants.getToken, HttpMethod.GET, new HttpEntity<>(map), typeRef2);
				try {
					tokenRes = mapperObj.readValue(tokRes.getBody(), GetAuthToken.class);
					// System.err.println("Token Res " +tokenRes.toString());
				} catch (Exception e) {
					System.err.println("Inner try for getToken" + e.getMessage());
				}
			} catch (HttpClientErrorException e) {
				System.err.println("/getToken Http Excep \n " + e.getResponseBodyAsString());
			}

			for (int i = 0; i < billHeaderList.size(); i++) {

				BillHeadEwayBill bill = billHeaderList.get(i);

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				// ------------------------------------------------------------

				CancelEWBModel cancelMod = new CancelEWBModel();
				cancelMod.setEwbNo(bill.getEwbNo());
				cancelMod.setCancelRsnCode(2);
				// cancelMod.setCancelRmrk("Cancelled the order");
				if (remark == null) {
					remark = "Cancelled the order";
				}
				cancelMod.setCancelRmrk(remark);

				System.err.println("CANCEL REQ " + cancelMod.toString());

				EWBCancelSuccess success = null;
				EWBCancelError ewayErrRes = null;

				// ------------------------------------------------------------

				ParameterizedTypeReference<String> typeRef = new ParameterizedTypeReference<String>() {
				};
				ResponseEntity<String> responseEntity = null;

				try {
					responseEntity = restTemplate.exchange(EwayConstants.cancelEWBUrl + "" + tokenRes.getAuthtoken(),
							HttpMethod.POST, new HttpEntity<>(cancelMod), typeRef);

					try {
						success = mapperObj.readValue(responseEntity.getBody(), EWBCancelSuccess.class);
						System.err.println("ewaySuccRes " + success.toString());

						map = new LinkedMultiValueMap<String, Object>();
						map.add("ewayBillNo", 0);
						// map.add("billNo", bill.getBillNo());
						map.add("billNo", bill.getBillNo());

						ErrorMessage updateEwayBillNo = restTemplate
								.postForObject(Constants.url + "/updateEwayBillNoForGrnGvn", map, ErrorMessage.class);
					} catch (Exception e) {
						e.printStackTrace();
						System.err.println("Inner Try");
					}

				} catch (HttpClientErrorException e) {

					System.err.println("ewayErrRes   " + e.getResponseBodyAsString());
					e.printStackTrace();

					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(e.getResponseBodyAsString());
					System.err.println("JSON ERROR   " + json.get("error"));

					// ewayErrRes = mapperObj.readValue(e.getResponseBodyAsString(),
					// EWBCancelError.class);
					// System.err.println("ewayErrRes " + ewayErrRes.toString());
					CustomErrEwayBill errRes = new CustomErrEwayBill();

					errRes.setBillNo(bill.getBillNo());
					errRes.setInvoiceNo(bill.getInvoiceNo());
					errRes.setTimeStamp("--");
					errRes.setErrorCode(String.valueOf(json.get("error")));
					errRes.setMessage("error!");

					errorBillList.add(errRes);
				}

			} // End of Bill Header For Loop

		} catch (Exception e) {
			e.printStackTrace();
		}

		return errorBillList;

	}

}
