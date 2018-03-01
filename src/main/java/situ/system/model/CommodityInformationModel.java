package situ.system.model;

import java.util.ArrayList;
import java.util.List;

import situ.core.model.BaseModel;

public class CommodityInformationModel extends BaseModel {
	
	private String  CommodityName;  //注意：在定义变量的时候首字母要小写，因为在JSONArry转换成json字符串时，会强制性转换成小写的，所以为了统一，开头全部用小写
	private String  CommodityCode;  //注意：在定义变量的时候首字母要小写，因为在JSONArry转换成json字符串时，会强制性转换成小写的，所以为了统一，开头全部用小写
	private Double  CommodityPrice; //注意：在定义变量的时候首字母要小写，因为在JSONArry转换成json字符串时，会强制性转换成小写的，所以为了统一，开头全部用小写
	private String  CommodityDescr;  //注意：在定义变量的时候首字母要小写，因为在JSONArry转换成json字符串时，会强制性转换成小写的，所以为了统一，开头全部用小写
	private Integer CommodityStock; //注意：在定义变量的时候首字母要小写，因为在JSONArry转换成json字符串时，会强制性转换成小写的，所以为了统一，开头全部用小写
	private Integer SellerId;       //注意：在定义变量的时候首字母要小写，因为在JSONArry转换成json字符串时，会强制性转换成小写的，所以为了统一，开头全部用小写
	private String  commodityState;
	private Integer firstCommodityClassificationId;
	private Integer secondCommodityClassificationId;
	private String  firstCommodityClassificationCode;	
	private String  seconCommodityClassificationCode;
	private List<SysCommodityPictureModel> commodityPictureList = null;
	
	public List<SysCommodityPictureModel> getCommodityPictureList() {
		return commodityPictureList;
	}
	public void setCommodityPictureList(List<SysCommodityPictureModel> commodityPictureList) {
		this.commodityPictureList = commodityPictureList;
	}
   //数据库中的字段
   /*commodity_name, commodity_code, commodity_price, commodity_descr, commodity_stock,
   seller_id, commodity_state, first_commodity_classification_id, second_commodity_
   classification_id, first_commodity_classification_code, second_commodity_classification_code, 
   createtime, updatetime, creator, updater, isdelete, iseffect*/
	 public Double getCommodityPrice() {
			return CommodityPrice;
		}
		public void setCommodityPrice(Double commodityPrice) {
			CommodityPrice = commodityPrice;
		}
		
	public String getCommodityName() {
		return CommodityName;
	}
	public void setCommodityName(String commodityName) {
		CommodityName = commodityName;
	}
	public String getCommodityCode() {
		return CommodityCode;
	}
	public void setCommodityCode(String commodityCode) {
		CommodityCode = commodityCode;
	}
	
	public String getCommodityDescr() {
		return CommodityDescr;
	}
	public void setCommodityDescr(String commodityDescr) {
		CommodityDescr = commodityDescr;
	}
	
	
	public String getCommodityState() {
		return commodityState;
	}
	public void setCommodityState(String commodityState) {
		this.commodityState = commodityState;
	}
	
	public Integer getCommodityStock() {
		return CommodityStock;
	}
	public void setCommodityStock(Integer commodityStock) {
		CommodityStock = commodityStock;
	}
	public Integer getSellerId() {
		return SellerId;
	}
	public void setSellerId(Integer sellerId) {
		SellerId = sellerId;
	}
	public Integer getFirstCommodityClassificationId() {
		return firstCommodityClassificationId;
	}
	public void setFirstCommodityClassificationId(Integer firstCommodityClassificationId) {
		this.firstCommodityClassificationId = firstCommodityClassificationId;
	}
	public Integer getSecondCommodityClassificationId() {
		return secondCommodityClassificationId;
	}
	public void setSecondCommodityClassificationId(Integer secondCommodityClassificationId) {
		this.secondCommodityClassificationId = secondCommodityClassificationId;
	}
	public String getFirstCommodityClassificationCode() {
		return firstCommodityClassificationCode;
	}
	public void setFirstCommodityClassificationCode(String firstCommodityClassificationCode) {
		this.firstCommodityClassificationCode = firstCommodityClassificationCode;
	}
	public String getSeconCommodityClassificationCode() {
		return seconCommodityClassificationCode;
	}
	public void setSeconCommodityClassificationCode(String seconCommodityClassificationCode) {
		this.seconCommodityClassificationCode = seconCommodityClassificationCode;
	}
	
	
	

}
