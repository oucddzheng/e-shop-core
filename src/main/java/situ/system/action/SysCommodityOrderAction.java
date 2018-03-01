package situ.system.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import situ.FormatCalendar;
import situ.FormatEmpty;
import situ.core.action.BaseAction;
import situ.system.model.CommodityInformationModel;
import situ.system.model.SysBasicDataModel;
import situ.system.model.SysCommodityOrderModel;
import situ.system.model.SysCommodityPictureModel;
import situ.system.model.SysReceiverAddressModel;
import situ.system.model.SysShoppingCarModel;
import situ.system.model.SysUserModel;
import situ.system.service.SysBasicDataService;
import situ.system.service.SysCommodityInformationService;
import situ.system.service.SysCommodityOrderService;
import situ.system.service.SysCommodityPictureService;
import situ.system.service.SysReceiverAddressService;
import situ.system.service.SysShoppingCarService;
@Controller("sysCommodityOrderAction")
@RequestMapping("/sysCommodityOrderAction")
public class SysCommodityOrderAction extends BaseAction {
	@Autowired
	private SysCommodityOrderService sysCommodityOrderService;
	@Autowired
	private SysCommodityInformationService<CommodityInformationModel> sysCommodityInformationService;
	@Autowired
	private SysCommodityPictureService<SysCommodityPictureModel> sysCommodityPictureService;
	@Autowired
	private SysReceiverAddressService sysReceiverAddressService;
	@Autowired
	private SysBasicDataService sysBasicDataService;
	@Autowired
	private SysShoppingCarService sysShoppingCarService;
	@RequestMapping("/insertCommodityOrder.do")
   public void insertCommodityOrder(SysCommodityOrderModel commodityOrderModelParameter , HttpServletRequest request , HttpServletResponse response) throws Exception {
	    HttpSession session =   request.getSession();
	    SysUserModel userModel =  (SysUserModel) session.getAttribute("user");
	    if(userModel == null) {
	    	System.out.println("�û�û�е�½�����û���½");
	    	return;
	    }	   
	    commodityOrderModelParameter.setUserId(userModel.getId());
	    commodityOrderModelParameter.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
	    commodityOrderModelParameter.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
	    commodityOrderModelParameter.setCreator(userModel.getUserAccount());
	    commodityOrderModelParameter.setUpdater(userModel.getUserAccount());
	    commodityOrderModelParameter.setIsDelete(0);
	    commodityOrderModelParameter.setIsEffect(1);
	    if(commodityOrderModelParameter.getReceivingAddressId()==null) {
	    	System.out.println("�û����ջ���ַΪ�գ���ѡ���ջ���ַ");
	    	return;
	    }	    
	    ArrayList<SysShoppingCarModel>  commodityInShopCarCheckedList = (ArrayList<SysShoppingCarModel>) session.getAttribute("commodityInShopCarCheckedList");  //commodityOrderModelList
             if(FormatEmpty.isEmpty(commodityInShopCarCheckedList)) {
            	 System.out.println("û�л�ȡ����Ʒ");
            	 response.getWriter().write("no"); 
            	 return;
             }	    
	     for (SysShoppingCarModel sysShoppingCarModelForEach : commodityInShopCarCheckedList ) {
            	   commodityOrderModelParameter.setCommodityId(sysShoppingCarModelForEach.getCommodityModdel().getId());
            	   commodityOrderModelParameter.setAmountMoney(sysShoppingCarModelForEach.getSingleOrderTotalPrice());
            	   commodityOrderModelParameter.setCommodityNumber(sysShoppingCarModelForEach.getCommodityNumber());
            	   commodityOrderModelParameter.setOrderStates(1);           	   
            	   sysCommodityOrderService.insert(commodityOrderModelParameter);
            	   sysShoppingCarService.delete(sysShoppingCarModelForEach.getId());            	   
               }
         response.getWriter().write("yes");	
	}
    //	�÷������û�����ҵĶ�����ʱ�򣬴����ݿ��л���û������ж�����Ϣ
	
		@RequestMapping("/loadMyCommodityOrderList.do")
		public void loadMyCommodityOrderList(SysCommodityOrderModel commodityOrderModel ,HttpServletRequest request , HttpServletResponse response) throws Exception {
		  Integer userId = commodityOrderModel.getUserId();
		  if(userId== null) {
			  System.out.println("û�л�õ��û���Id ");
			  return;
		  }		  
		  ArrayList<SysCommodityOrderModel> commodityOrderModelList = (ArrayList<SysCommodityOrderModel>) sysCommodityOrderService.selectAll(commodityOrderModel);	
		  if(FormatEmpty.isEmpty(commodityOrderModelList)) {
			  System.out.println("����û���¹�����");
			  return;
		  }
		  for (SysCommodityOrderModel commodityOrderModelForEach : commodityOrderModelList) {
			 
			  //����Ĵ����ǲ�ѯ����Ʒ����Ϣ��Ȼ��ŵ�commodityOrderModelForEach���model��
			  Integer commodityId =   commodityOrderModelForEach.getCommodityId();
			  CommodityInformationModel commodityInformationModel = sysCommodityInformationService.selectId(commodityId);
			  SysCommodityPictureModel  sysCommodityPictureModel = new SysCommodityPictureModel();
			  sysCommodityPictureModel.setCommodityInformationId(commodityInformationModel.getId());
			  ArrayList<SysCommodityPictureModel> sysCommodityPictureList  =	(ArrayList<SysCommodityPictureModel>) sysCommodityPictureService.selectAll(sysCommodityPictureModel);
			  commodityInformationModel.setCommodityPictureList(sysCommodityPictureList);	 
			  //����ѯ��������Ʒ��Ϣmodel���뵽����model�� commodityOrderModelForEach
			  commodityOrderModelForEach.setCommodityModdel(commodityInformationModel);
			//����Ĵ����ǲ�ѯ��ַ
			  Integer receiverAddressId = commodityOrderModelForEach.getReceivingAddressId();
			  SysReceiverAddressModel sysReceiverAddressModel = (SysReceiverAddressModel) sysReceiverAddressService.selectId(receiverAddressId);
			  SysBasicDataModel sysBasicDataModel = (SysBasicDataModel) sysBasicDataService.selectId(sysReceiverAddressModel.getProvinceId());
			  sysReceiverAddressModel.setProvinceName(sysBasicDataModel.getDistrictName());
			  sysBasicDataModel = (SysBasicDataModel) sysBasicDataService.selectId(sysReceiverAddressModel.getCityId() );
			  sysReceiverAddressModel.setCityName(sysBasicDataModel.getDistrictName());
			  sysBasicDataModel = (SysBasicDataModel) sysBasicDataService.selectId(sysReceiverAddressModel.getDistrictId() );
			  sysReceiverAddressModel.setDistrictName(sysBasicDataModel.getDistrictName());
			  commodityOrderModelForEach.setSysReceiverAddressModel(sysReceiverAddressModel);   		  
		      if(commodityOrderModelForEach.getOrderStates()==1) {
		    	  commodityOrderModelForEach.setOrderStatesName("��ǩ��");
		      }else {
		    	  commodityOrderModelForEach.setOrderStatesName("δǩ��");
		      }
		  
		  }
		   JSONArray commodityOrderModelListResult = JSONArray.fromObject(commodityOrderModelList);		  		  
		    response.getWriter().write(commodityOrderModelListResult.toString());
		}	
}
