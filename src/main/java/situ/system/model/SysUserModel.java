package situ.system.model;

import situ.core.model.BaseModel;

public class SysUserModel extends BaseModel {
	private String userAccount = null;   //账户的登录名
	private String userPassword = null;  //账户的登陆密码
	private String userName = null;  	//用户的名字   		
	private Integer buyerOrSaller = null;  //是卖家还是买家
    private Integer loginCount = null; // 登录次数
	private String  loginTime =null;  // 登录时间
	private String  descr = null; //用户自我描述
	
	
	
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
