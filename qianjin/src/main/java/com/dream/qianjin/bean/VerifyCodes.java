package com.dream.qianjin.bean;

public class VerifyCodes {

	private String ID;
	private String userID;
	private String isUsed;

	public String getUserID() {
		return userID;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}

}
