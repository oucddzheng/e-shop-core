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
		System.out.println("һ������û����д");
		return;
	}
	if(FormatEmpty.isEmpty(seconCommodityClassificationCode)) {
		System.out.println("��������û����д");
		return;
	}
	if(FormatEmpty.isEmpty(commodityName)) {
		System.out.println("��Ʒ�����ֲ���Ϊ��");
		return;
	}
	if(FormatEmpty.isEmpty(commodityDescr)) {
		System.out.println("��Ʒ��������Ϊ��");
		return;
	}
	if(commodityPrice==null) {
		System.out.println("��Ʒ�ļ۸���Ϊ��");
		return;
	}
	if(commodityNumber==null) {
		System.out.println("���������Ϊ��");
		return;
		
	}
	if(FormatEmpty.isEmpty(commodityState)) {
		System.out.println("��Ʒ��״̬����Ϊ��");
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
	@RequestMapping(value="/selectCommodityInformation.do",produces = "application/json;charset=utf-8")//produces �������ʱ�������@ResponseBody�������������
	@ResponseBody
	public String selectCommodityInformation(CommodityInformationModel model , HttpServletRequest request , HttpServletResponse response) throws Exception {
	ArrayList<CommodityInformationModel> cimList =	(ArrayList<CommodityInformationModel>) sysCommodityInformationService.selectAll(model);	
	 JSONArray result = JSONArray.fromObject(cimList);
	 //response.setContentType("application/json;charset=utf-8");//���response��������
	 //response.setContentType("text/html;charset=utf-8");
	/* response.getWriter().write(result.toString());
	 System.out.println(result.toString());*/
	 return result.toString();		
	}
	@RequestMapping("/selectCommodityInformationDispach.do")
	public ModelAndView selectCommodityInformationDispach(CommodityInformationModel ciModel , HttpServletRequest request , HttpServletResponse response) {
		Integer secondCommodityClassificationId = ciModel.getSecondCommodityClassificationId();
		if(secondCommodityClassificationId == null) {
			System.out.println("��Ʒ���������id����Ϊ��");
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
		/*System.out.println("��չʾ��Ʒ�ķ�����selectCommodityInformationAndPicture��ѯ�ļ�¼������" + cimList.size());*/
		
		SysCommodityPictureModel  sysCommodityPictureModel = new SysCommodityPictureModel();
		//��ͼƬ������Ʒ��Ϣ��list������
		for (CommodityInformationModel commodityInformationModel : cimList) {
			sysCommodityPictureModel.setCommodityInformationId(commodityInformationModel.getId());
			//��������һ����ÿ�κ����ķ���ֵ���ǲ�ͬ��ָ�룬ÿ������������ӵ�Ҳ�ǲ�ͬ��ָ��ֵ���������Ϊʲô����sysCommodityPictureService.selectModel�Ļ���ѯ�Ľ�������10������Ϊ�÷�����������ҳ�ġ�Ӧ�õ���selectAll()����
			ArrayList<SysCommodityPictureModel> sysCommodityPictureList  =	(ArrayList<SysCommodityPictureModel>) sysCommodityPictureService.selectAll(sysCommodityPictureModel);
			/*System.out.println("�������ͼƬ������"+ sysCommodityPictureList.size());*/
			commodityInformationModel.setCommodityPictureList(sysCommodityPictureList);
		}
		/*
		 * pageNumber �� pageSize ��ҳ���ϵ��������� ����model�е�page ��rows���Ӧ ��������������ǰ��ҳ��ȷ���ġ�
		 * Integer pageSize = 9;*/
		
		//����ǰҳ�е���Ʒ��Ϣ�洢��session����
		HttpSession session = request.getSession();
		session.setAttribute("commodityInformationList", cimList);
		Integer pageNumber = ciModel.getPage(); 
		 //������Ĵ����õ������ô�ֵ  ��ȡ�������������ܵļ�¼����
	    Integer commodityTotal = ciModel.getPager().getRowCount();
	    Integer lastpage = ciModel.getPager().getPageCount();
		HashMap<String , Object> commodityInformationMap = new HashMap();
		commodityInformationMap.put("commodityInformationList",cimList );
		commodityInformationMap.put("pageNumber", pageNumber);
		/*commodityInformationMap.put("pageSize", pageSize); �������д�����ﲻ��ȷpageSizeӦ����ǰ�����û�ָ��*/
		commodityInformationMap.put("lastPage", lastpage );
		JSONObject result = JSONObject.fromObject(commodityInformationMap);
		return result.toString();
	}
	
	@RequestMapping("/commodityIntroductionShowDispach.do")
    public ModelAndView commodityIntroductionShowDispach(CommodityInformationModel ciModel , HttpServletRequest request , HttpServletResponse response){		
		System.out.println("��Ʒ����ҳ����Ʒ��id��"+ciModel.getId());
		HttpSession session = request.getSession();
		ArrayList<CommodityInformationModel> cimList = (ArrayList<CommodityInformationModel>) session.getAttribute("commodityInformationList");
		CommodityInformationModel commodityIntroductionModel = null;
		ModelAndView commodityIntroductionDispachModelAndView = new ModelAndView();
		//����id���������������Ʒ
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









