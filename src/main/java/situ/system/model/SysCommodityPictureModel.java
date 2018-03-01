package situ.system.model;

import situ.core.model.BaseModel;

public class SysCommodityPictureModel extends BaseModel {
	private Integer commodityInformationId;
	private String url;
	public Integer getCommodityInformationId() {
		return commodityInformationId;
	}
	public void setCommodityInformationId(Integer commodityInformationId) {
		this.commodityInformationId = commodityInformationId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
