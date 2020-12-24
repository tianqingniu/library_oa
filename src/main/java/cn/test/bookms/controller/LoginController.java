package cn.test.bookms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.test.bookms.entity.MsAdmin;
import cn.test.bookms.service.MsAdminService;
import cn.test.bookms.util.Message;

@Controller
@Slf4j
public class LoginController {

	@Autowired
	private MsAdminService msAdminService;
	@Autowired
	private BorrowBookController borrowBookController;
	
	@RequestMapping(value = {"/login","/"})
	public String toLogin() {
		return "login";
	}
	/**
	 * 登录验证
	 */
	@RequestMapping(value = "/adminLogin")
	public String adminLogin(String adminNumber,String adminPwd,HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) {
		log.info("===LoginController类的adminLogin方法===");
		// 获取类型
//		String type = request.getParameter("type");
//		System.out.println(type);
		Map<String,String> map = new HashMap<String,String>();
		map.put("adminNumber", adminNumber);
		map.put("adminPwd", adminPwd);
		MsAdmin msAdmin = msAdminService.selectAdmin(map);
		// 判断类型
		if(msAdmin != null) {
			httpSession.setAttribute("msAdmin", msAdmin);
			//httpSession.setMaxInactiveInterval(1800);  //默认存在半个小时  设置回话存在时长 秒单位
			httpSession.setAttribute("imgPath", Message.IMG_PATH);
			// 超期罚款
			System.out.println(" ===超期处理===");
			MsAdmin user = (MsAdmin) httpSession.getAttribute("msAdmin");
			borrowBookController.checkIsPenalty(request, user.getId());	// 超期处理
			return "index";
		}else {
			request.setAttribute("Login_error", Message.LOGIN_FAILED_MSG);
//			httpSession.setMaxInactiveInterval(1);   //设置该消息存在一秒，显示后下次访问页面即消失
			return "redirect:/login";
		}
	}
	
	/**
	 * 安全退出
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/adminLogout")
	public String adminLogout(HttpSession httpSession) {
		MsAdmin msAdmin = (MsAdmin) httpSession.getAttribute("msAdmin");  //从sesion中获取MsAdmin对象
		if(msAdmin != null) {  
			httpSession.removeAttribute("msAdmin");  //移除
			return "redirect:/login";   //重定向到登登录界面
		}
		return "redirect:/login";
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping(value = "/showUserDetail")
	public String showUserDetail() {
		log.info("===LoginController类的showUserDetail方法===");
		return "userDetail";
	}

	/**
	 * 登录成功页面
	 */
	@RequestMapping(value = "/showLoginSuccess")
	public String showLoginSuccess() {
		log.info("===LoginController类的showLoginSuccess方法===");
		return "loginSuccess";
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping(value = "/toUpdatePwd")
	public String toUpdatePwd() {
		log.info("===LoginController类的toUpdatePwd方法===");
		return "updatePwd";
	}
	@RequestMapping(value = "/updatePwd")
	public String updatePwd(String oldPwd, String newPwd, String confirmPwd, HttpSession httpSession, HttpServletResponse response) throws IOException {
		log.info("===LoginController类的updatePwd方法===");
		MsAdmin user = (MsAdmin) httpSession.getAttribute("msAdmin");
		if (oldPwd.equals(user.getAdminPwd()) && newPwd.equals(confirmPwd)) {	// 不能用==，要用.equals()
			System.out.println("===原密码："+user.getAdminPwd());
			int count = msAdminService.updatePwd(newPwd, user.getAdminNumber());
			if (count == 1) {
				System.out.println("===修改密码成功！");
				httpSession.setMaxInactiveInterval(1);   //设置该消息存在一秒，显示后下次访问页面即消失
				httpSession.removeAttribute("msAdmin");  //移除session
				response.setContentType("text/html; charset=UTF-8"); //转码
				PrintWriter out = response.getWriter();
				out.flush();
				out.println("<script>");
				out.println("alert('修改密码成功！即将转到登录页面！');");
				out.println("top.location.href='/library_oa/login';");	// 转到登录页面
				out.println("</script>");
			}
		}
		System.out.println("===修改密码失败！");
		httpSession.setAttribute(Message.UPDATE_PWD_MSG, Message.UPDATE_PWD_FAILED_MSG);
		httpSession.setMaxInactiveInterval(1);   //设置该消息存在一秒，显示后下次访问页面即消失
		return "updatePwd";
	}
}
