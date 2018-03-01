package situ.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;
import situ.FormatCalendar;
import situ.FormatEmpty;
import situ.core.action.BaseAction;
import situ.core.session.HtmlUtil;
import situ.core.session.JSONUtil;
import situ.system.model.SysCommodityClassificationModel;
import situ.system.service.SysCommodityClassificationService;
@Controller("sysCommodityClassificationAction")
@RequestMapping("/syscommodityclassification")
public class SysCommodityClassificationAction extends BaseAction {
	@Autowired
	private SysCommodityClassificationService<SysCommodityClassificationModel>  sysCommodityClassificationService;
	@RequestMapping("/save.do")
	public void save(SysCommodityClassificationModel model , HttpServletRequest request , HttpServletResponse response) throws Exception {
	 int commodityClassificationIsLegalFlag = commodityClassificationIsLegal(model) ;
	 if(commodityClassificationIsLegalFlag ==-1) {
		 System.out.println("商品分类不能为空");
		 return;
	 }
	 if(commodityClassificationIsLegalFlag ==-2) {
		 System.out.println("商品描述不能为空");
		 return;
	 }
	 if(commodityClassificationIsLegalFlag ==-3) {
		 System.out.println("该分类已经存在");
		 return;
	 }	 
	 if(commodityClassificationIsLegalFlag == 1) {
		 System.out.println("输入的商品分类合法，可以进行插入");
		 commodityClassificationInsert(model);
		 return;
	 }
	}
	public int commodityClassificationIsLegal(SysCommodityClassificationModel model) throws Exception {
		String name = model.getClassificationName();
		String descr = model.getDescr();
		if(FormatEmpty.isEmpty(name)) {
			return -1;
		}
		if(FormatEmpty.isEmpty(descr)) {
			return -2;
		}
		SysCommodityClassificationModel isExistModel =  new SysCommodityClassificationModel();
		isExistModel.setClassificationName(model.getClassificationName());
	    int isExist = sysCommodityClassificationService.selectCount(isExistModel);
		if(isExist != 0) {
			return -3;
		}else {
			return 1;
		}
	 }
	public void commodityClassificationInsert(SysCommodityClassificationModel model) throws Exception {
		String classificationCode = UUID.randomUUID().toString().replaceAll("-", "");
		String currentTime = FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS);
		model.setClassificationCode(classificationCode);
		model.setParentCode("000000");
		model.setCreateTime(currentTime);
		model.setUpdateTime(currentTime);
		model.setCreator("admin@qq.com");
		model.setUpdater("admin@qq.com");
		model.setIsDelete(1);
		model.setIsEffect(0);
		sysCommodityClassificationService.insert(model);		
	}
	@RequestMapping("/firstCommodityClassificationSelect.do")
	public void firstCommodityClassificationSelect(HttpServletRequest request , HttpServletResponse response) throws Exception {				    
		CommodityClassificationSelec(request ,response , "000000" , "1");
	}
	//查询不同等级分类
	public void CommodityClassificationSelec( HttpServletRequest request , HttpServletResponse response , String parentCode , String grade) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		SysCommodityClassificationModel sccm = new SysCommodityClassificationModel();
		    sccm.setParentCode(parentCode);
		    List<SysCommodityClassificationModel> list =  sysCommodityClassificationService.selectAll(sccm);	    
		    System.out.println(grade+"级商品的分类个数"+list.size());
		    //将查出来的对象集合放到session域中
		    HttpSession session = request.getSession();
		    session.setAttribute("commodityClassificationlist"+grade, list);
		    //建一个新的集合， 从查询出来的list集合中，找出我们需要的信息放在这个新的集合中，最后将这个新的集合返回给前台，
		    List<SysCommodityClassificationModel> listSelect = new ArrayList();		    
		    for(SysCommodityClassificationModel sccmforeach : list){
		       SysCommodityClassificationModel sccmSelect = new SysCommodityClassificationModel();//定义一个可以向页面发送的对象
	    	   sccmSelect.setId(sccmforeach.getId());
		       sccmSelect.setClassificationCode(sccmforeach.getClassificationCode());
	    	   sccmSelect.setClassificationName(sccmforeach.getClassificationName());
	    	   listSelect.add(sccmSelect);	    	   
		    }
		    JSONArray result = JSONArray.fromObject(listSelect);
		    response.getWriter().write(result.toString());
	}	
	@RequestMapping("/secondCommodityClassificationSave.do")
	public void secondCommodityClassificationSave(SysCommodityClassificationModel model , HttpServletRequest request , HttpServletResponse response) throws Exception {		
		String parentCode = model.getParentCode();
		if(FormatEmpty.isEmpty(parentCode)) {
			System.out.println("上级code不能为空");
			return;
		}
		 int commodityClassificationIsLegalFlag = commodityClassificationIsLegal(model) ;
		 if(commodityClassificationIsLegalFlag ==-1) {
			 System.out.println("商品分类不能为空");
			 return;
		 }
		 if(commodityClassificationIsLegalFlag ==-2) {
			 System.out.println("商品描述不能为空");
			 return;
		 }
		 if(commodityClassificationIsLegalFlag ==-3) {
			 System.out.println("该分类已经存在");
			 return;
		 }	 
		 if(commodityClassificationIsLegalFlag == 1) {
			 System.out.println("输入的商品分类合法，可以进行插入");
			 secondCommodityClassificationInsert(model);
		 }				
	}
	public void secondCommodityClassificationInsert(SysCommodityClassificationModel model) throws Exception {
		String classificationCode = UUID.randomUUID().toString().replaceAll("-", "");
		String currentTime = FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS);
		model.setClassificationCode(classificationCode);
		model.setCreateTime(currentTime);
		model.setUpdateTime(currentTime);
		model.setCreator("admin@qq.com");
		model.setUpdater("admin@qq.com");
		model.setIsDelete(1);
		model.setIsEffect(0);
		sysCommodityClassificationService.insert(model);		
	}
	@RequestMapping("/queryProductTypeAll.do") 
	public void queryProductTypeAll(HttpServletResponse response) throws Exception {
		SysCommodityClassificationModel  sysCommodityClassificationModel = new SysCommodityClassificationModel ();
		sysCommodityClassificationModel.setSort("parent_code"); //sort这个属性是设置排序的字段    
		sysCommodityClassificationModel .setOrder("ASC");  //设置排序的方式 ASC是升序，DESC是降序 的意思
		
	        List<SysCommodityClassificationModel> list = sysCommodityClassificationService.selectAll(sysCommodityClassificationModel);
	        List<SysCommodityClassificationModel> result = new ArrayList<>();
	        for (SysCommodityClassificationModel sccm : list) {
	            String parentCode = sccm.getParentCode();
	            if (FormatEmpty.isEmpty(parentCode) || "000000".equals(parentCode)) {
	                result.add(sccm);
	                continue;     
	            }
	            for (SysCommodityClassificationModel sccm2 : result) {
	                if (parentCode.equals(sccm2.getClassificationCode())) {
	                    sccm2.getList().add(sccm);
	                    break;
	                }
	            }
	        }
	        Map<String, Object> jsonMap = new HashMap<>();
	        jsonMap.put("list", result);
	        System.out.println(JSONUtil.toJSONString(jsonMap));
	        HtmlUtil.writerJson(response, jsonMap);		
	}
	@RequestMapping("secondCommodityClassificationSelect.do")
	public void secondCommodityClassificationSelect(SysCommodityClassificationModel model , HttpServletRequest request , HttpServletResponse response) throws Exception {
		String parentCode = model.getParentCode();
		if(FormatEmpty.isEmpty(parentCode)) {
			System.out.println("没有获得一级分类的code");
			return;
		}
		CommodityClassificationSelec(request ,response , parentCode , "2");		
	}
	
}
