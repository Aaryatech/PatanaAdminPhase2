
package com.ats.adminpanel.model.item;


public class SubCategory {

	  private int subCatId;
	    private int catId;
	    private String subCatName;
	    private int delStatus;
		private int seqNo;
		private String prefix;
		private int exInt1;
		private int exInt2;
		private String exVar1;
		private String exVar2;

		public int getSeqNo() {
			return seqNo;
		}
		public void setSeqNo(int seqNo) {
			this.seqNo = seqNo;
		}
		public int getSubCatId() {
			return subCatId;
		}
		public void setSubCatId(int subCatId) {
			this.subCatId = subCatId;
		}
		public int getCatId() {
			return catId;
		}
		public void setCatId(int catId) {
			this.catId = catId;
		}
		public String getSubCatName() {
			return subCatName;
		}
		public void setSubCatName(String subCatName) {
			this.subCatName = subCatName;
		}
		public int getDelStatus() {
			return delStatus;
		}
		public void setDelStatus(int delStatus) {
			this.delStatus = delStatus;
		}
		public String getPrefix() {
			return prefix;
		}
		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
		public int getExInt1() {
			return exInt1;
		}
		public void setExInt1(int exInt1) {
			this.exInt1 = exInt1;
		}
		public int getExInt2() {
			return exInt2;
		}
		public void setExInt2(int exInt2) {
			this.exInt2 = exInt2;
		}
		public String getExVar1() {
			return exVar1;
		}
		public void setExVar1(String exVar1) {
			this.exVar1 = exVar1;
		}
		public String getExVar2() {
			return exVar2;
		}
		public void setExVar2(String exVar2) {
			this.exVar2 = exVar2;
		}
		@Override
		public String toString() {
			return "SubCategory [subCatId=" + subCatId + ", catId=" + catId + ", subCatName=" + subCatName
					+ ", delStatus=" + delStatus + ", seqNo=" + seqNo + ", prefix=" + prefix + ", exInt1=" + exInt1
					+ ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
		}
	
}
