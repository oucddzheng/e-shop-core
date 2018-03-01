package situ.system.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import situ.FormatCalendar;
import situ.FormatEmpty;
import situ.UniqueCode;
import situ.core.action.BaseAction;
import situ.system.model.SysCommodityPictureModel;
import situ.system.service.SysCommodityPictureService;
@Controller("sysCommodityPictureAction")
@RequestMapping("/sysCommodityPictureAction")
public class SysCommodityPictureAction extends BaseAction {
	@Autowired
	private SysCommodityPictureService  sysCommodityPictureService;
	public void testAction() {
		System.out.println("Controller层没有问题");
	}
	@RequestMapping(value = "/addCommodityPicture.do" , method = RequestMethod.POST , produces = "application/text;charset=UTF-8")
	public void addCommodityPicture(HttpServletRequest request, String commodityId, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		if(FormatEmpty.isEmpty(commodityId)) {
			System.out.println("您没有选择商品");
			return;
		}
		
		String fileName = UniqueCode.getUniqueCodeByDate()+".jpg";
		SysCommodityPictureModel scpm = new SysCommodityPictureModel();
		scpm.setCommodityInformationId(Integer.valueOf(commodityId));
		scpm.setUrl("/situ-shopping-web/upload/" + fileName );
		scpm.setCreateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		scpm.setUpdateTime(FormatCalendar.getCurrentTime(FormatCalendar.F_YMDHMS));
		scpm.setCreator("admin@qq.com");
		scpm.setUpdater("admin@qq.com");
		scpm.setIsDelete(1);
		scpm.setIsEffect(0);	
		
		String path = request.getSession().getServletContext().getRealPath("upload");
		
		System.out.println(path);
		System.out.println(commodityId);
		
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sysCommodityPictureService.insert(scpm);
		System.out.println("图片添加成功");
	}	
}
