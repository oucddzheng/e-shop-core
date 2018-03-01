package situ.system.action;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import situ.AutoVerificationCode;
import situ.FormatEmpty;

@Controller("sysToolAction")
@RequestMapping("/sysToolAction")
public class SysToolAction {
	
	@RequestMapping("/generatingVerificationCode.do")
	public void generatingVerificationCode(HttpServletRequest request , HttpServletResponse response) throws IOException {
		AutoVerificationCode autoVerificationCode = new AutoVerificationCode();
		request.getSession().setAttribute("autoCode", autoVerificationCode.getAutoCode()); 
	    ImageIO.write(autoVerificationCode.getImage(), "jpg", response.getOutputStream()); 	
	}
	@RequestMapping("/confirmVerificationCode.do")
	public void confirmVerificationCode(String verificationCodeValue , HttpServletRequest request , HttpServletResponse response ) throws IOException {
		HttpSession session = request.getSession();
		String verificationCodeValueInsession = (String) session.getAttribute("autoCode");
		if(FormatEmpty.isEmpty(verificationCodeValueInsession)) {
			System.out.println("验证码没有生成");
			return;
		}
		if(verificationCodeValueInsession.equals(verificationCodeValue)) {
			response.getWriter().write("yes");
		}else {
			response.getWriter().write("no");
		}		
	}

}
