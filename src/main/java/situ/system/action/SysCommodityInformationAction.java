package situ.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import situ.UniqueCode;
import situ.core.action.BaseAction;
import situ.system.model.CommodityInformationModel;
import situ.system.model.SysCommodityClassificationModel;
import situ.system.model.SysCommodityPictureModel;
import situ.system.service.SysCommodityInformationService;
import situ.system.service.SysCommodityPictureService;
@Controller("sysCommodityInformationAction")
@RequestMapping("/sysCommodityInformationAction")
public class SysCommodityInformationAction extends BaseAction {

	@Autowired
	private SysCommodityInformationService<CommodityInformationModel> sysCommodityInformationService;
	@Autowired
	private SysCommodityPictureService<SysCommodityPictureModel> sysCommodityPictureService;
	@RequestMapping("/addCommodityInformation.do")
	public void addCommodityInformation(CommodityInformationModel model , HttpServletRequest request , HttpServletResponse response) throws Exception {		
	String firstCommodityClassificationCode =  model.getFirstCommodityClassificationCode();
	String seconCommodityClassificationCode =  model.getSeconCommodityClassificationCode()	;
	String commodityName = model.getCommodityName();
	String commodityDescr = model.getCommodityDescr();
	Double commodityPrice = model.getCommodityPrice();
	Integer commodityNumber = model.getCommodityStock();
	String commodityState = model.getCommodityState();
	if(FormatEmpty.isEmpty(firstCommodityClassificationCode)) {
		System.out.println("一级分类没有填写");
		return;
	}
	if(FormatEmpty.isEmpty(seconCommodityClassificationCode)) {
		System.out.println("二级分类没有填写");
		return;
	}
	if(FormatEmpty.isEmpty(commodityName)) {
		System.out.println("商品的名字不能为空");
		return;
	}
	if(FormatEmpty.isEmpty(commodityDescr)) {
		System.out.println("商品描述不能为空");
		return;
	}
	if(commodityPrice==null) {
		System.out.println("商品的价格不能为空");
		return;
	}
	if(commodityNumber==null) {
		System.out.println("库存量不能为空");
		return;
		
	}
	if(FormatEmpty.isEmpty(commodityState)) {
		System.out.println("商品的状态不能为空");
		return;
	}	
	HttpSession session = request.getSession();
	List<SysCommodityClassificationModel> commodityClassFirstList = (List<SysCommodityClassificationModel>) session.getAttribute("commodityClassificationlist1");
	List<SysCommodityClassificationModel> commodityClassSecondList = (List<SysCommodityClassificationModel>) session.getAttribute("commodityClassificationlist2");
	Integer CommodityFirstClassId = null;
	Integer CommoditySecondClassId = null;
	for (SysCommodityClassificationModel modelFirstClass :commodityClassFirstList) {		
		 if(modelFirstClass.getClassificationCode().equals(firstCommodityClassificationCode)) {
			 CommodityFirstClassId = modelFirstClass.getId();
		 }
	}
	for (SysCommodityClassificationModel modelSecondClass :commodityClassSecondList) {
		 if(modelSecondClass.getClassificationCode().equals(seconCommodityClassificationCode)) {
			 CommoditySecondClassId  = modelSecondClass.getId();
		 }
	}
	CommodityInformationModel ciModel = new CommodityInformationModel();
	ciModel.setCommodityName(commodityName);
	ciModel.setCommodityCode(UniqueCode.getUUID());
	ciModel.setCommodityPrice(commodityPrice);
	ciModel.setCommodityDescr(commodityDescr);
	ciModel.setCommodityStock(commodityNumber);
	ciModel.setSellerId(1);
	ciModel.setCommodityState(commodityState);
	ciModel.setFirstCommodityClassificationId(CommodityFirstClassId);
	ciModel.setSecondCommodityClassificationId(CommoditySecondClassId);
	ciModel.setFirstCommodityClassificationCode(firstCommodityClassificationCode);
	ciModel.setSeconCommodityClassificationCode(seconCommodityClassificationCode);
	ciModel.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
	ciModel.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
	ciModel.setCreator("admin@qq.com");
	ciModel.setUpdater("admin@qq.com");
	ciModel.setIsDelete(1);
	ciModel.setIsEffect(0);
	sysCommodityInformationService.insert(ciModel);		
	}
	@RequestMapping(value="/selectCommodityInformation.do",produces = "application/json;charset=utf-8")//produces 这个属性时用来解决@ResponseBody中文乱码问题的
	@ResponseBody
	public String selectCommodityInformation(CommodityInformationModel model , HttpServletRequest request , HttpServletResponse response) throws Exception {
	ArrayList<CommodityInformationModel> cimList =	(ArrayList<CommodityInformationModel>) sysCommodityInformationService.selectAll(model);	
	 JSONArray result = JSONArray.fromObject(cimList);
	 //response.setContentType("application/json;charset=utf-8");//解决response乱码问题
	 //response.setContentType("text/html;charset=utf-8");
	/* response.getWriter().write(result.toString());
	 System.out.println(result.toString());*/
	 return result.toString();		
	}
	@RequestMapping("/selectCommodityInformationDispach.do")
	public ModelAndView selectCommodityInformationDispach(CommodityInformationModel ciModel , HttpServletRequest request , HttpServletResponse response) {
		Integer secondCommodityClassificationId = ciModel.getSecondCommodityClassificationId();
		if(secondCommodityClassificationId == null) {
			System.out.println("商品二级分类的id不能为空");
			return null;
		}
		/*ciModel.setPage(1);
		ciModel.setRows(10);*/
		JSONArray result = JSONArray.fromObject(ciModel);		
		/*System.out.println(result.toString());*/
		ModelAndView commodityInformationDispachModelAndView = new ModelAndView();
		commodityInformationDispachModelAndView.addObject("commodityInformationModel",result.toString());
		commodityInformationDispachModelAndView.setViewName("commodityShow"); 
		return commodityInformationDispachModelAndView;
	}
	
	@RequestMapping(value ="/selectCommodityInformationAndPicture.do" , produces = "application/json;charset=utf-8")
	@ResponseBody
	public String selectCommodityInformationAndPicture(CommodityInformationModel ciModel , HttpServletRequest request , HttpServletResponse response) throws Exception {
		
	
		/*System.out.println(ciModel.getPage());
		System.out.println(ciModel.getRows());
		System.out.println(ciModel.getSecondCommodityClassificationId());*/
		
		ArrayList<CommodityInformationModel> cimList = (ArrayList<CommodityInformationModel>) sysCommodityInformationService.selectModel(ciModel);
		/*System.out.println("在展示商品的方法中selectCommodityInformationAndPicture查询的记录条数是" + cimList.size());*/
		
		SysCommodityPictureModel  sysCommodityPictureModel = new SysCommodityPictureModel();
		//将图片放在商品信息的list集合中
		for (CommodityInformationModel commodityInformationModel : cimList) {
			sysCommodityPictureModel.setCommodityInformationId(commodityInformationModel.getId());
			//在下面这一行中每次函数的返回值都是不同的指针，每次往集合中添加的也是不同的指针值。这条语句为什么调用sysCommodityPictureService.selectModel的话查询的结果是最多10条，因为该方法是用来分页的。应该调用selectAll()方法
			ArrayList<SysCommodityPictureModel> sysCommodityPictureList  =	(ArrayList<SysCommodityPictureModel>) sysCommodityPictureService.selectAll(sysCommodityPictureModel);
			/*System.out.println("查出来的图片个数是"+ sysCommodityPictureList.size());*/
			commodityInformationModel.setCommodityPictureList(sysCommodityPictureList);
		}
		/*
		 * pageNumber 和 pageSize 是页面上的两个参数 ，与model中的page 和rows相对应 ，这两个参数是前端页面确定的。
		 * Integer pageSize = 9;*/
		
		//将当前页中的商品信息存储到session域中
		HttpSession session = request.getSession();
		session.setAttribute("commodityInformationList", cimList);
		Integer pageNumber = ciModel.getPage(); 
		 //在下面的代码用的是引用传值  ，取出符合条件的总的记录条数
	    Integer commodityTotal = ciModel.getPager().getRowCount();
	    Integer lastpage = ciModel.getPager().getPageCount();
		HashMap<String , Object> commodityInformationMap = new HashMap();
		commodityInformationMap.put("commodityInformationList",cimList );
		commodityInformationMap.put("pageNumber", pageNumber);
		/*commodityInformationMap.put("pageSize", pageSize); 这个变量写在这里不正确pageSize应该在前端让用户指定*/
		commodityInformationMap.put("lastPage", lastpage );
		JSONObject result = JSONObject.fromObject(commodityInformationMap);
		return result.toString();
	}
	
	@RequestMapping("/commodityIntroductionShowDispach.do")
    public ModelAndView commodityIntroductionShowDispach(CommodityInformationModel ciModel , HttpServletRequest request , HttpServletResponse response){		
		System.out.println("商品详情页中商品的id是"+ciModel.getId());
		HttpSession session = request.getSession();
		ArrayList<CommodityInformationModel> cimList = (ArrayList<CommodityInformationModel>) session.getAttribute("commodityInformationList");
		CommodityInformationModel commodityIntroductionModel = null;
		ModelAndView commodityIntroductionDispachModelAndView = new ModelAndView();
		//根据id遍历出所点击的商品
		for(CommodityInformationModel commodityModel : cimList) {
			if(commodityModel.getId()==ciModel.getId()) {
				commodityIntroductionModel = commodityModel;
			}			
		}
		/*JSONObject commodityIntroductionModelResult = JSONObject.fromObject(commodityIntroductionModel);*/
    	commodityIntroductionDispachModelAndView.addObject("commodityIntroductionModel", commodityIntroductionModel );
    	commodityIntroductionDispachModelAndView.setViewName("commodityIntroduction");
    	return commodityIntroductionDispachModelAndView;    	
    }	
	@RequestMapping("/selectCommodityInformationDispachForSesrch.do")
	public ModelAndView selectCommodityInformationDispachForSesrch(CommodityInformationModel ciModel , HttpServletRequest request , HttpServletResponse response) {		
		/*ciModel.setPage(1);
		ciModel.setRows(10);*/
		JSONObject result = JSONObject.fromObject(ciModel);	
		ModelAndView commodityInformationDispachModelAndView = new ModelAndView();
		commodityInformationDispachModelAndView.addObject("commodityInformationModel",result.toString());
		commodityInformationDispachModelAndView.setViewName("commodityShowForSearch"); 
		return commodityInformationDispachModelAndView;
	}
}









