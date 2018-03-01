package situ.system.model;

import situ.core.model.BaseModel;

public class SysCommodityOrderModel extends BaseModel {

	private Integer userId;
	private Integer commodityId;
	private Integer commodityNumber;
	private Double  amountMoney;
	private Integer receivingAddressId;
	private Integer orderStates;
	private String orderStatesName;
	private CommodityInformationModel commodityModdel = null;
	private SysReceiverAddressModel sysReceiverAddressModel = null;
	
	
	
	public String getOrderStatesName() {
		return orderStatesName;
	}
	public void setOrderStatesName(String orderStatesName) {
		this.orderStatesName = orderStatesName;
	}
	public SysReceiverAddressModel getSysReceiverAddressModel() {
		return sysReceiverAddressModel;
	}
	public void setSysReceiverAddressModel(SysReceiverAddressModel sysReceiverAddressModel) {
		this.sysReceiverAddressModel = sysReceiverAddressModel;
	}
	public Integer getCommodityNumber() {
		return commodityNumber;
	}
	public void setCommodityNumber(Integer commodityNumber) {
		this.commodityNumber = commodityNumber;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}
	public Double getAmountMoney() {
		return amountMoney;
	}
	public void setAmountMoney(Double amountMoney) {
		this.amountMoney = amountMoney;
	}
	public Integer getReceivingAddressId() {
		return receivingAddressId;
	}
	public void setReceivingAddressId(Integer receivingAddressId) {
		this.receivingAddressId = receivingAddressId;
	}
	public Integer getOrderStates() {
		return orderStates;
	}
	public void setOrderStates(Integer orderStates) {
		this.orderStates = orderStates;
	}
	public CommodityInformationModel getCommodityModdel() {
		return commodityModdel;
	}
	public void setCommodityModdel(CommodityInformationModel commodityModdel) {
		this.commodityModdel = commodityModdel;
	}
	
}
