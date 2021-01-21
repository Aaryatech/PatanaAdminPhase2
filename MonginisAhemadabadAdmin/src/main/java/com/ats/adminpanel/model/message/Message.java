package com.ats.adminpanel.model.message;




public class Message {

  
    private int msgId;
 
    private String msgFrdt;
  
    private String msgTodt;
  
    private String msgImage;
  
    private String msgHeader;
   
    private String msgDetails;
 
    private int isActive;
 
    private int delStatus;
    
    private String makerDatetime;
	
	private String applicableFr;
	
	private int exInt1,exInt2;
	
	private String exVar1,exVar2;
	
	
	
    

	public String getMakerDatetime() {
		return makerDatetime;
	}

	public void setMakerDatetime(String makerDatetime) {
		this.makerDatetime = makerDatetime;
	}

	public String getApplicableFr() {
		return applicableFr;
	}

	public void setApplicableFr(String applicableFr) {
		this.applicableFr = applicableFr;
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

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public String getMsgFrdt() {
		return msgFrdt;
	}

	public void setMsgFrdt(String msgFrdt) {
		this.msgFrdt = msgFrdt;
	}

	public String getMsgTodt() {
		return msgTodt;
	}

	public void setMsgTodt(String msgTodt) {
		this.msgTodt = msgTodt;
	}

	public String getMsgImage() {
		return msgImage;
	}

	public void setMsgImage(String msgImage) {
		this.msgImage = msgImage;
	}

	public String getMsgHeader() {
		return msgHeader;
	}

	public void setMsgHeader(String msgHeader) {
		this.msgHeader = msgHeader;
	}

	public String getMsgDetails() {
		return msgDetails;
	}

	public void setMsgDetails(String msgDetails) {
		this.msgDetails = msgDetails;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	@Override
	public String toString() {
		return "Message [msgId=" + msgId + ", msgFrdt=" + msgFrdt + ", msgTodt=" + msgTodt + ", msgImage=" + msgImage
				+ ", msgHeader=" + msgHeader + ", msgDetails=" + msgDetails + ", isActive=" + isActive + ", delStatus="
				+ delStatus + ", makerDatetime=" + makerDatetime + ", applicableFr=" + applicableFr + ", exInt1="
				+ exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + "]";
	}



  
}