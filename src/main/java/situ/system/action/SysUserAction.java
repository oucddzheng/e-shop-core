package situ.system.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import situ.FormatEmpty;
import situ.FormatMD5;
import situ.core.action.BaseAction;
import situ.system.model.SysUserModel;
import situ.system.service.SysUserService;
@Controller("sysUserAction")
@RequestMapping("/sysUser")
public class SysUserAction extends BaseAction {
		
	@Autowired
	private SysUserService<SysUserModel>  sysUserService;
	@RequestMapping("/save.do")
	public void save (SysUserModel model, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		int userAccountIsLegalFlag = userAccountIsLegal(model);
		if(userAccountIsLegalFlag == -1) {
			System.out.println("���벻��Ϊ�գ�����������");
		}
		if(userAccountIsLegalFlag == -2) {
			System.out.println("��������Ϊ�գ�����������");
		}
		if(userAccountIsLegalFlag == -3) {
			System.out.println("�˺Ų���Ϊ�գ��������˺�");
		}
		if(userAccountIsLegalFlag == -4) {
			System.out.println("��������û����Ѵ��ڣ�����������");
		}
		if(userAccountIsLegalFlag == 0) {
		  Integer insertState =	registeAccount( model);
		  response.getWriter().write(insertState.toString());		  
		}
	}
	
	public int userAccountIsLegal(SysUserModel model) throws Exception {
		 String password = model.getUserPassword();
		 if(FormatEmpty.isEmpty(password)) {
			 return -1;
		 }
		 String name = model.getUserName();
		 if(FormatEmpty.isEmpty(name)) {
			 return -2;
		 }
		 String userAccountIsExist = model.getUserAccount();
		 if(!FormatEmpty.isEmpty(userAccountIsExist)) {
			SysUserModel userIsExist = new SysUserModel();
			userIsExist.setUserAccount(userAccountIsExist);
			int count = sysUserService.selectCount(userIsExist);
			if(count == 0) {
				return 0;
			}else {
				return -4;
			}
		 }else {
			    return -3;
		 }
	}	
	public int registeAccount(SysUserModel model) throws Exception {		
		SysUserModel userRegister = new SysUserModel();
		//��ȡ��ǰʱ��
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		String currentTime = sdf.format(date);		
		//��ȡע�����
		userRegister.setUserAccount(model.getUserAccount());		
		userRegister.setUserPassword( FormatMD5.MD5Encode(model.getUserPassword(), "utf-8"));		
		userRegister.setUserName(model.getUserName());
		userRegister.setBuyerOrSaller(1);		
		userRegister.setCreateTime(currentTime);
		userRegister.setUpdateTime(currentTime);
		userRegister.setCreator(model.getUserAccount());
		userRegister.setUpdater(model.getUserAccount());
		userRegister.setIsDelete(1);
		userRegister.setIsEffect(0);
		userRegister.setLoginCount(0);
		userRegister.setLoginTime("0");
		userRegister.setDescr("");	   
		sysUserService.insert(userRegister);		
		return 1;		
	}
	@RequestMapping("/login.do")
	public void login(SysUserModel model, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		String userAccount = model.getUserAccount();
		String userPassword = model.getUserPassword();
		if(FormatEmpty.isEmpty(userAccount)) {
			System.out.println("�û�������Ϊ��");
			return;
		}
		if(FormatEmpty.isEmpty(userPassword)) {
			System.out.println("���벻��Ϊ��");
			return;
		}		
		SysUserModel loginUser = new SysUserModel();
		loginUser.setUserAccount(userAccount);
		loginUser.setUserPassword(FormatMD5.MD5Encode(userPassword, "utf-8"));
		List<SysUserModel> sysUserModelList = sysUserService.selectModel(loginUser);		
		if(FormatEmpty.isEmpty(sysUserModelList)) {
			System.out.println("�û�������");
			return;
		}
		SysUserModel getUser = sysUserModelList.get(0);		
		if(getUser!=null) {
			response.getWriter().write("1");
			System.out.println("���Բ鵽���û�");
			HttpSession session = request.getSession();
			session.setAttribute("user", getUser);//����û����ڣ����û����浽session����
			System.out.println(session.getAttribute("user"));
			System.out.println("�Ѿ����û���Ϣ���뵽session����");			
		}else {
			
		}		
	}
	@RequestMapping("removeUser.do")
	public void removeUser(SysUserModel model, HttpServletRequest request, HttpServletResponse response) throws IOException {		
		request.getSession().removeAttribute("user");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write("session�е��û������Ѿ����");
	}
	
}
