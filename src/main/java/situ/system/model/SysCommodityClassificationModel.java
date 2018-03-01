package situ.system.model;

import java.util.ArrayList;
import java.util.List;

import situ.core.model.BaseModel;

public class SysCommodityClassificationModel extends BaseModel {
    private String classificationName = null;
    private String classificationCode = null;
    private String parentCode = null;
    private List<SysCommodityClassificationModel> list = new ArrayList<>();
	public List<SysCommodityClassificationModel> getList() {
		return list;
	}
	public void setList(List<SysCommodityClassificationModel> list) {
		this.list = list;
	}
	public String getClassificationName() {
		return classificationName;
	}
	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}
	public String getClassificationCode() {
		return classificationCode;
	}
	public void setClassificationCode(String classificationCode) {
		this.classificationCode = classificationCode;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
    
}
