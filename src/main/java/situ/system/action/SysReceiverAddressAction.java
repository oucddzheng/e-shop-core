package situ.system.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;
import situ.FormatCalendar;
import situ.core.action.BaseAction;
import situ.system.model.SysBasicDataModel;
import situ.system.model.SysReceiverAddressModel;
import situ.system.service.SysBasicDataService;
import situ.system.service.SysReceiverAddressService;
@Controller("sysReceiverAddressAction")
@RequestMapping("/sysReceiverAddressAction")
public class SysReceiverAddressAction extends BaseAction {

	@Autowired
	private SysReceiverAddressService sysReceiverAddressService;
	@Autowired
	private SysBasicDataService sysBasicDataService;
	@RequestMapping("/addReceiverAddress.do")
	public void addReceiverAddress(SysReceiverAddressModel receiverAddressModelParameter , HttpServletRequest request , HttpServletResponse response) throws Exception {
		receiverAddressModelParameter.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		receiverAddressModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		receiverAddressModelParameter.setIsDelete(0);
		receiverAddressModelParameter.setIsEffect(1);
		sysReceiverAddressService.insert(receiverAddressModelParameter);
		response.getWriter().write("{\"isSuccess\":\"yes\"}");
	}
	@RequestMapping("/getUserAddressInformation.do")
	public void getUserAddressInformation(SysReceiverAddressModel receiverAddressModelParameter , HttpServletRequest request , HttpServletResponse response) throws Exception {
		
		ArrayList<SysReceiverAddressModel> rceiverAddressModelList =  (ArrayList<SysReceiverAddressModel>) sysReceiverAddressService.selectAll(receiverAddressModelParameter);		
		//获取字典表中地址的名字的详细信息 ， 因为在收货地址的表中保存的是id  还需要名字
		for (SysReceiverAddressModel sysReceiverAddressModelForEach : rceiverAddressModelList) {
			SysBasicDataModel sysBasicDataModel = 	(SysBasicDataModel) sysBasicDataService.selectId(sysReceiverAddressModelForEach.getProvinceId());
			sysReceiverAddressModelForEach.setProvinceName(sysBasicDataModel.getDistrictName());
			sysBasicDataModel = (SysBasicDataModel) sysBasicDataService.selectId(sysReceiverAddressModelForEach.getCityId() );
			sysReceiverAddressModelForEach.setCityName(sysBasicDataModel.getDistrictName());
			sysBasicDataModel = (SysBasicDataModel) sysBasicDataService.selectId(sysReceiverAddressModelForEach.getDistrictId() );
			sysReceiverAddressModelForEach.setDistrictName(sysBasicDataModel.getDistrictName());		
		}
		System.out.println("这个用户的收货地址的list集合的长度是"+rceiverAddressModelList.size());
	   //向前台页面返回收货地址的详细信息
		JSONArray rceiverAddressModelListResult = JSONArray.fromObject(rceiverAddressModelList);				
	    response.getWriter().write(rceiverAddressModelListResult.toString());
	
	}
	
}
