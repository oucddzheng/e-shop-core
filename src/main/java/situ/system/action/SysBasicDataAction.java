package situ.system.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.json.JSONArray;
import situ.core.action.BaseAction;
import situ.system.mapper.SysBasicDataMapper;
import situ.system.model.SysBasicDataModel;
import situ.system.service.SysBasicDataService;
@Controller("sysBasicDataAction")
@RequestMapping("/sysBasicDataAction")
public class SysBasicDataAction extends BaseAction { 
	
	@Autowired
	private SysBasicDataService<SysBasicDataModel> sysBasicDataService;
	/**
	 * �������������ѯ��ӵ�ַʱ�ĵ�ַ����������
	 * @param sysBasicDataModelParameter
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectDistrict.do" , produces = "application/json;charset=utf-8")
	public void selectDistrict(SysBasicDataModel  sysBasicDataModelParameter , HttpServletRequest request , HttpServletResponse response) throws Exception {
		if(sysBasicDataModelParameter.getParentId()==null) {
			System.out.println("��ȡ�����ĸ���codeΪ�գ����ܻ�ȡ����");
			return;
		}		
		List<SysBasicDataModel> BasicDataModelList = sysBasicDataService.selectAll(sysBasicDataModelParameter);
		JSONArray result = JSONArray.fromObject(BasicDataModelList);
		response.getWriter().write(result.toString());		
		System.out.println(result.toString());		
	}

}
