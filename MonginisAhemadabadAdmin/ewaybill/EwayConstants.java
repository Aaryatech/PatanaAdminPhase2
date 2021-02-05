package com.ats.adminpanel.model.ewaybill;

public class EwayConstants {

	//SandBox Auth Token
	 //public static String getToken="http://testapi.taxprogsp.co.in/ewaybillapi/dec/v1.03/authenticate?action=ACCESSTOKEN&aspid=1629701119&password=pdMulani@123&gstin=24AAJCM0068N1Z8&username=MADHVIDAIR_API_EBL&ewbpwd=madhavi@2019";

	//Production Get ACCESSTOKEN
	public static String getToken="https://api.taxprogsp.co.in/v1.03/dec/authenticate?action=ACCESSTOKEN&aspid=1629701119&password=pdMulani@123&gstin=24AAJCM0068N1Z8&username=MADHVIDAIR_API_EBL&ewbpwd=madhavi@2019";
	
	//SandBox  GENEWAYBILL
	 //public static String genEwayGenUrl="http://testapi.taxprogsp.co.in/ewaybillapi/dec/v1.03/ewayapi?action=GENEWAYBILL&aspid=1629701119&password=pdMulani@123&gstin=05AAACG1625Q1ZK&username=05AAACG1625Q1ZK&authtoken=";
	
	//Production GENEWAYBILL
	public static String genEwayGenUrl="https://api.taxprogsp.co.in/v1.03/dec/ewayapi?action=GENEWAYBILL&aspid=1629701119&password=pdMulani@123&gstin=24AAJCM0068N1Z8&username=MADHVIDAIR_API_EBL&authtoken=";
	
	//Cancel EWB
	public static String cancelEWBUrl="https://api.taxprogsp.co.in/v1.03/dec/ewayapi?action=CANEWB&aspid=1629701119&password=pdMulani@123&gstin=24AAJCM0068N1Z8&username=MADHVIDAIR_API_EBL&authtoken=";
	
		
	 //MADHVIDAIR_API_EBL	24AAJCM0068N1Z8 madhavi@2019
	
	
	public static int GUJ_STATE_CODE=24;//05AAACG1625Q1ZK
	/*
	 SELECT t_bill_header.bill_no,t_bill_header.invoice_no,t_bill_header.bill_date,
t_bill_header.fr_id,t_bill_header.fr_code,t_bill_header.taxable_amt,t_bill_header.grand_total,
t_bill_header.sgst_sum,t_bill_header.cgst_sum,t_bill_header.igst_sum
FROM t_bill_header WHERE t_bill_header.bill_no IN(1)


SELECT t_bill_detail.bill_detail_no,t_bill_detail.bill_qty as quantity,
t_bill_detail.cgst_per as cgst_rate,
t_bill_detail.sgst_per as sgst_rate,
t_bill_detail.igst_per as igst_rate,
t_bill_detail.cess_per as cess_rate,
t_bill_detail.cess_rs as cess_non_advol,
t_bill_detail.taxable_amt,
m_item.item_name as product_name,
m_item.item_name as product_desc,
m_item_sup.item_hsncd as hsn_code,
m_item_sup.item_uom as qty_unit

from m_item_sup,m_item,t_bill_detail
WHERE m_item_sup.item_id=m_item.id AND m_item.id=t_bill_detail.item_id AND t_bill_detail.bill_no=1
	 */
}
