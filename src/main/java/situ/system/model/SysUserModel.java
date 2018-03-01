package situ.system.model;

import situ.core.model.BaseModel;

public class SysUserModel extends BaseModel {
	private String userAccount = null;   //�˻��ĵ�¼��
	private String userPassword = null;  //�˻��ĵ�½����
	private String userName = null;  	//�û�������   		
	private Integer buyerOrSaller = null;  //�����һ������
    private Integer loginCount = null; // ��¼����
	private String  loginTime =null;  // ��¼ʱ��
	private String  descr = null; //�û���������
	
	
	
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getBuyerOrSaller() {
		return buyerOrSaller;
	}
	public void setBuyerOrSaller(Integer buyerOrSaller) {
		this.buyerOrSaller = buyerOrSaller;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
}
