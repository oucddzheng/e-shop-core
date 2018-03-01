package situ.system.model;

import situ.core.model.BaseModel;

public class SysShoppingCarModel extends BaseModel {
	
   private Integer userId;
   private Integer commodityId;
   private Integer commodityNumber;
   private CommodityInformationModel commodityModdel = null;
   private Double singleOrderTotalPrice;
   
   
public Double getSingleOrderTotalPrice() {
	return singleOrderTotalPrice;
}
public void setSingleOrderTotalPrice(Double singleOrderTotalPrice) {
	this.singleOrderTotalPrice = singleOrderTotalPrice;
}
public CommodityInformationModel getCommodityModdel() {
	return commodityModdel;
}
public void setCommodityModdel(CommodityInformationModel commodityModdel) {
	this.commodityModdel = commodityModdel;
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
public Integer getCommodityNumber() {
	return commodityNumber;
}
public void setCommodityNumber(Integer commodityNumber) {
	this.commodityNumber = commodityNumber;
}
}
