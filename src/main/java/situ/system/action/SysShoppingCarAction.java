package situ.system.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import situ.FormatCalendar;
import situ.FormatEmpty;
import situ.core.action.BaseAction;
import situ.system.model.CommodityInformationModel;
import situ.system.model.SysCommodityPictureModel;
import situ.system.model.SysShoppingCarModel;
import situ.system.service.SysCommodityInformationService;
import situ.system.service.SysCommodityPictureService;
import situ.system.service.SysShoppingCarService;
@Controller("sysShoppingCarAction")
@RequestMapping("/sysShoppingCarAction")
public class SysShoppingCarAction extends BaseAction {
	@Autowired
	private SysShoppingCarService sysShoppingCarService;
	@Autowired
	private SysCommodityInformationService<CommodityInformationModel> sysCommodityInformationService; //
	@Autowired
	private SysCommodityPictureService<SysCommodityPictureModel> sysCommodityPictureService;
	@RequestMapping("/addCommodityToShoppingCar.do")	
	public void addCommodityToShoppingCar(SysShoppingCarModel sscModel , HttpServletRequest request , HttpServletResponse response) throws Exception {		
		
		/*int i = sysShoppingCarService.selectCount(null);
		System.out.println(i);*/
		if(sscModel.getUserId()==null) {
			System.out.println("�û���id����Ϊ��");
			return;
		}
		if(sscModel.getCommodityId()==null) {
			System.out.println("��Ʒ��id����Ϊ��");
			return;
		}
		if(sscModel.getCommodityNumber()==null) {
			System.out.println("��Ʒ������������Ϊ��");
			return;
		}
		if(FormatEmpty.isEmpty(sscModel.getCreator())) {
			System.out.println("creator����Ϊ��");
			return;
		}
		if(FormatEmpty.isEmpty(sscModel.getUpdater())) {
			System.out.println("updater����Ϊ��");
			return;
		}
		//�������ﳵ�������Ʒ��ʱ��ͬһ���û��������ͬ��Ʒʱ��ֻ�Ǹı���Ʒ�������������Ӽ�¼
		SysShoppingCarModel shoppingCarModelIsExist = new SysShoppingCarModel();
		shoppingCarModelIsExist.setUserId(sscModel.getUserId());
		shoppingCarModelIsExist.setCommodityId(sscModel.getCommodityId());
		ArrayList<SysShoppingCarModel> shoppingCarModelList = (ArrayList<SysShoppingCarModel>) sysShoppingCarService.selectAll(shoppingCarModelIsExist);
		if(FormatEmpty.isEmpty(shoppingCarModelList)) {
			sscModel.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
			sscModel.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
			sscModel.setIsDelete(1);
			sscModel.setIsEffect(0);  		
			sysShoppingCarService.insert(sscModel);	
		}else {
			sscModel.setId(shoppingCarModelList.get(0).getId());
			sscModel.setCommodityNumber(sscModel.getCommodityNumber()+shoppingCarModelList.get(0).getCommodityNumber());
			sscModel.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
			sscModel.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
			sscModel.setIsDelete(1);
			sscModel.setIsEffect(0); 
			sysShoppingCarService.updateActive(sscModel);
		}
					
	}
	@RequestMapping(value="/selectCommodityFromShoppingCarByUserId.do" , produces = "application/json;charset=utf-8")
	@ResponseBody
	public String selectCommodityFromShoppingCarByUserId(SysShoppingCarModel shoppingCarModel ,HttpServletRequest request , HttpServletResponse response) throws Exception { 
		
		if(shoppingCarModel.getUserId()==null) {
			System.out.println("�û���id��ϢΪ�գ����ִ����޷����в�ѯ");
			return "" ;
		}
		//�������Ҫ���ﳵ�����Ϣ������Ҫ��Ʒ���е���Ϣ ��ȡ�û����ﳵ�е���Ϣ������ù��ﳵ����Ʒ����Ϣ
		ArrayList<SysShoppingCarModel> commodityInShopCarList = null;
		commodityInShopCarList = (ArrayList<SysShoppingCarModel>) sysShoppingCarService.selectAll(shoppingCarModel);
		//����洢��ϸ��Ʒ��Ϣ�ļ���
		ArrayList<CommodityInformationModel> commodityInformationModelList  = new ArrayList();
		//������Ʒ��Ϣ��Model������ֵ��
		CommodityInformationModel commodityInformationModel = null;
		//������Ʒ��Ϣ��Model��������
		CommodityInformationModel commodityInformationModelParameter = new CommodityInformationModel();
		SysCommodityPictureModel  sysCommodityPictureModel = new SysCommodityPictureModel();
		for(SysShoppingCarModel sscModel : commodityInShopCarList) {
			commodityInformationModelParameter.setId(sscModel.getCommodityId());
			commodityInformationModel = sysCommodityInformationService.selectId(commodityInformationModelParameter);
			sysCommodityPictureModel.setCommodityInformationId(commodityInformationModel.getId());
			ArrayList<SysCommodityPictureModel> sysCommodityPictureList  =	(ArrayList<SysCommodityPictureModel>) sysCommodityPictureService.selectAll(sysCommodityPictureModel);			
			commodityInformationModel.setCommodityPictureList(sysCommodityPictureList);
			commodityInformationModelList.add(commodityInformationModel);
		}
		HashMap<String , Object> commodityInShoppingCarAndInformationMap = new HashMap();
		commodityInShoppingCarAndInformationMap.put("commodityInShopCarList", commodityInShopCarList);
		commodityInShoppingCarAndInformationMap.put("commodityInformationModelList", commodityInformationModelList);
		JSONObject result = JSONObject.fromObject(commodityInShoppingCarAndInformationMap);
		System.out.println("�ڹ��ﳵ����Ʒ�ĳ�����"+commodityInShopCarList.size());
		System.out.println("���ݹ��ﳵ�е���Ʒ������Ʒ��Ϣ���в������Ʒ�ĳ�����"+commodityInformationModelList.size());
		return result.toString();
	}
	//�ⷽ��������������Ʒ�����ı仯
	@RequestMapping("/commodityNumberChange.do")
	public void commodityNumberChange(SysShoppingCarModel shoppingCarModel ,HttpServletRequest request , HttpServletResponse response) throws Exception {
		
		if(shoppingCarModel.getId()==null) {
			System.out.println("idΪ�գ��޷����и���");
			return;
		}
		if(shoppingCarModel.getCommodityNumber()==null) {
			System.out.println("��Ʒ��������Ϊ��");
			return;
		}
		sysShoppingCarService.updateActive(shoppingCarModel);
		//�������Ʒ���ܵļ۸�
		SysShoppingCarModel shoppingCarModelForNumberChange = (SysShoppingCarModel) sysShoppingCarService.selectId(shoppingCarModel);
		CommodityInformationModel commodityInformationModelParameter = new CommodityInformationModel();
		commodityInformationModelParameter.setId(shoppingCarModelForNumberChange.getCommodityId());
		CommodityInformationModel commodityInformationModel = sysCommodityInformationService.selectId(commodityInformationModelParameter);
		Double singleCommodityTotalPrice = commodityInformationModel.getCommodityPrice()*shoppingCarModelForNumberChange.getCommodityNumber();		
		response.getWriter().write(singleCommodityTotalPrice.toString());
	}
	@RequestMapping("/allCommodityTotalPrice.do")  
	public void allCommodityTotalPrice(String allCommodityIdchecked , HttpServletRequest request , HttpServletResponse response) throws Exception {
        //		allCommodityId ���������ǹ��ﳵ��ÿ����Ʒ��id  �����ﳵ��ÿ����¼��id
		Double allCommodityTotalPrice = 0.00;
		if(FormatEmpty.isEmpty(allCommodityIdchecked)) {
			response.getWriter().write(allCommodityTotalPrice.toString());
			return;
		}			
		//����������һ���ַ�����Ҫ���ַ������д���
		String[] allCommodityIdArray = allCommodityIdchecked.substring(1).split(",");
		SysShoppingCarModel shoppingCarModel = new SysShoppingCarModel();
		for(String shoppingCarId : allCommodityIdArray) {
			shoppingCarModel.setId(Integer.valueOf(shoppingCarId));
			SysShoppingCarModel shoppingCarModelForallCommodityTotalPrice = (SysShoppingCarModel) sysShoppingCarService.selectId(shoppingCarModel);
			CommodityInformationModel commodityInformationModelParameter = new CommodityInformationModel();
			commodityInformationModelParameter.setId(shoppingCarModelForallCommodityTotalPrice.getCommodityId());
			CommodityInformationModel commodityInformationModel = sysCommodityInformationService.selectId(commodityInformationModelParameter);
			allCommodityTotalPrice +=  commodityInformationModel.getCommodityPrice()*shoppingCarModelForallCommodityTotalPrice.getCommodityNumber();
		}
		/*System.out.println("����ѡ�е���Ʒ���ܼ۸���"+allCommodityTotalPrice);*/
		response.getWriter().write(allCommodityTotalPrice.toString());
		return;
	}
	@RequestMapping("/deletCommodityFromShoppingCar.do")
	public void deletCommodityFromShoppingCar(String deletedCommodityId , HttpServletRequest request , HttpServletResponse response) throws NumberFormatException, Exception {		
		sysShoppingCarService.delete(Integer.valueOf(deletedCommodityId));
		System.out.println("ɾ������Ʒ��id��  "+deletedCommodityId);
		response.getWriter().write("ɾ���ɹ�");
	}
	//�ú������������ݿ��л���Ѿ�ѡ�е�׼�����˵Ĺ��ﳵ��Ʒ
	@RequestMapping("/settleAccounts.do")
	public ModelAndView settleAccounts(String allCommodityIdchecked , HttpServletRequest request , HttpServletResponse response) throws Exception {
		if(FormatEmpty.isEmpty(allCommodityIdchecked)) {
			return null;
		}
		String[] allCommodityIdArray = allCommodityIdchecked.substring(1).split(",");
		ArrayList<SysShoppingCarModel> commodityInShopCarList = new ArrayList();
		Double allCommodityTotalPriceInShoppingCar = 0.00;
		SysShoppingCarModel shoppingCarModelParameter = new SysShoppingCarModel();
		CommodityInformationModel commodityInformationModelParameter = new CommodityInformationModel();
		SysCommodityPictureModel  sysCommodityPictureModelParameter = new SysCommodityPictureModel();
		for(String shoppingCarIdCheckedForEach : allCommodityIdArray) {
			shoppingCarModelParameter.setId(Integer.valueOf(shoppingCarIdCheckedForEach));
			SysShoppingCarModel shoppingCarModelChecked = (SysShoppingCarModel) sysShoppingCarService.selectId(shoppingCarModelParameter);			
			commodityInformationModelParameter.setId(shoppingCarModelChecked.getCommodityId());
			CommodityInformationModel commodityInformationModelChecked = sysCommodityInformationService.selectId(commodityInformationModelParameter);
			sysCommodityPictureModelParameter.setCommodityInformationId(commodityInformationModelChecked.getId());
			ArrayList<SysCommodityPictureModel> sysCommodityPictureList  =	(ArrayList<SysCommodityPictureModel>) sysCommodityPictureService.selectAll(sysCommodityPictureModelParameter);			
			commodityInformationModelChecked.setCommodityPictureList(sysCommodityPictureList);
			shoppingCarModelChecked.setCommodityModdel(commodityInformationModelChecked);
			shoppingCarModelChecked.setSingleOrderTotalPrice(shoppingCarModelChecked.getCommodityNumber()*commodityInformationModelChecked.getCommodityPrice());						
			commodityInShopCarList.add(shoppingCarModelChecked);
			allCommodityTotalPriceInShoppingCar += shoppingCarModelChecked.getSingleOrderTotalPrice(); 
		}
		HttpSession session = request.getSession();
		
		//���Ѿ�ѡ�е��ڹ��ﳵ�е���Ʒ���б��ŵ�session���У����ύ������ʱ��ȡ����
		session.setAttribute("commodityInShopCarCheckedList", commodityInShopCarList);
		HashMap<String , Object> commodityInShoppingCarCheckedMap = new HashMap();
		commodityInShoppingCarCheckedMap.put("commodityInShopCarCheckedList", commodityInShopCarList);
		commodityInShoppingCarCheckedMap.put("allCommodityTotalPriceInShoppingCar", allCommodityTotalPriceInShoppingCar);
	    JSONObject commodityInShoppingCarCheckedMapResult = JSONObject.fromObject(commodityInShoppingCarCheckedMap);
	    ModelAndView modelAndView  = new ModelAndView();
	    modelAndView.addObject("commodityInShoppingCarCheckedMapResult" , commodityInShoppingCarCheckedMapResult.toString());
	    modelAndView.setViewName("commodityOrder");
	    return modelAndView;
	}
}
