package com.ats.adminpanel.model.billing;

import java.util.List;

public class PendingBillsAndCreditNotesList {

	List<PendingBills> billList;
	List<PendingBillCreditNote> crNoteList;
	
	public List<PendingBills> getBillList() {
		return billList;
	}
	public void setBillList(List<PendingBills> billList) {
		this.billList = billList;
	}
	public List<PendingBillCreditNote> getCrNoteList() {
		return crNoteList;
	}
	public void setCrNoteList(List<PendingBillCreditNote> crNoteList) {
		this.crNoteList = crNoteList;
	}
	@Override
	public String toString() {
		return "PendingBillsAndCreditNotesList [billList=" + billList + ", crNoteList=" + crNoteList + "]";
	}
}
