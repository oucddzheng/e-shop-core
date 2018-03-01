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
			System.out.println("用户的id不能为空");
			return;
		}
		if(sscModel.getCommodityId()==null) {
			System.out.println("商品的id不能为空");
			return;
		}
		if(sscModel.getCommodityNumber()==null) {
			System.out.println("商品的数量不能呢为空");
			return;
		}
		if(FormatEmpty.isEmpty(sscModel.getCreator())) {
			System.out.println("creator不能为空");
			return;
		}
		if(FormatEmpty.isEmpty(sscModel.getUpdater())) {
			System.out.println("updater不能为空");
			return;
		}
		//在往购物车中添加商品的时候，同一个用户，添加相同商品时，只是改变商品的数量，不增加记录
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
			System.out.println("用户的id信息为空，出现错误，无法进行查询");
			return "" ;
		}
		//这里既需要购物车表的信息，又需要商品表中的信息 获取用户购物车中的信息，并获得购物车中商品的信息
		ArrayList<SysShoppingCarModel> commodityInShopCarList = null;
		commodityInShopCarList = (ArrayList<SysShoppingCarModel>) sysShoppingCarService.selectAll(shoppingCarModel);
		//定义存储详细商品信息的集合
		ArrayList<CommodityInformationModel> commodityInformationModelList  = new ArrayList();
		//定义商品信息的Model做返回值用
		CommodityInformationModel commodityInformationModel = null;
		//定义商品信息的Model做参数用
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
		System.out.println("在购物车中商品的长度是"+commodityInShopCarList.size());
		System.out.println("根据购物车中的商品在在商品信息表中查出的商品的长度是"+commodityInformationModelList.size());
		return result.toString();
	}
	//这方法，用来计算商品数量的变化
	@RequestMapping("/commodityNumberChange.do")
	public void commodityNumberChange(SysShoppingCarModel shoppingCarModel ,HttpServletRequest request , HttpServletResponse response) throws Exception {
		
		if(shoppingCarModel.getId()==null) {
			System.out.println("id为空，无法进行更改");
			return;
		}
		if(shoppingCarModel.getCommodityNumber()==null) {
			System.out.println("商品数量不能为空");
			return;
		}
		sysShoppingCarService.updateActive(shoppingCarModel);
		//计算出商品的总的价格
		SysShoppingCarModel shoppingCarModelForNumberChange = (SysShoppingCarModel) sysShoppingCarService.selectId(shoppingCarModel);
		CommodityInformationModel commodityInformationModelParameter = new CommodityInformationModel();
		commodityInformationModelParameter.setId(shoppingCarModelForNumberChange.getCommodityId());
		CommodityInformationModel commodityInformationModel = sysCommodityInformationService.selectId(commodityInformationModelParameter);
		Double singleCommodityTotalPrice = commodityInformationModel.getCommodityPrice()*shoppingCarModelForNumberChange.getCommodityNumber();		
		response.getWriter().write(singleCommodityTotalPrice.toString());
	}
	@RequestMapping("/allCommodityTotalPrice.do")  
	public void allCommodityTotalPrice(String allCommodityIdchecked , HttpServletRequest request , HttpServletResponse response) throws Exception {
        //		allCommodityId 传过来的是购物车中每个商品的id  即购物车中每条记录的id
		Double allCommodityTotalPrice = 0.00;
		if(FormatEmpty.isEmpty(allCommodityIdchecked)) {
			response.getWriter().write(allCommodityTotalPrice.toString());
			return;
		}			
		//传过来的是一个字符串，要对字符串进行处理
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
		/*System.out.println("所有选中的商品的总价格是"+allCommodityTotalPrice);*/
		response.getWriter().write(allCommodityTotalPrice.toString());
		return;
	}
	@RequestMapping("/deletCommodityFromShoppingCar.do")
	public void deletCommodityFromShoppingCar(String deletedCommodityId , HttpServletRequest request , HttpServletResponse response) throws NumberFormatException, Exception {		
		sysShoppingCarService.delete(Integer.valueOf(deletedCommodityId));
		System.out.println("删除的商品的id是  "+deletedCommodityId);
		response.getWriter().write("删除成功");
	}
	//该函数用来从数据库中获得已经选中的准备结账的购物车商品
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
		
		//将已经选中的在购物车中的商品的列表，放到session域中，在提交订单的时候取出。
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
