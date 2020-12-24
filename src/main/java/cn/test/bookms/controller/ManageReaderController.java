package cn.test.bookms.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.test.bookms.entity.MsAdmin;
import cn.test.bookms.service.MsAdminService;
import cn.test.bookms.service.MsOverdueService;

@Controller
@Slf4j
public class ManageReaderController {
	
	@Autowired
	private BorrowBookController borrowBookController;
//	BorrowBookController borrowBookController = new BorrowBookController();	要用自动装配@Autowired才可，不然会报错
	@Autowired
	private MsAdminService msAdminService;
	@Autowired
	private MsOverdueService msOverdueService;
	
	/**
	 * 显示所有读者
	 */
	@RequestMapping(value = "/showReader")
	public String showReader(@RequestParam(value="currentPage",defaultValue="1",required=false)
			int currentPage, Model model) {
		log.info("===ManageReaderController类的showReader方法===");
		model.addAttribute("pageMsg",msAdminService.selectReaderByPage(null, null, null, currentPage));
		return "showAllReader";
	}
	
	/**
	 * 查询读者
	 */
	@RequestMapping(value = "/searchReader")
	public String searchReader() {
		log.info("===ManageReaderController类的searchReader方法===");
		return "searchReader";
	}
	@RequestMapping(value = "/searchAdminPage")
	public String searchAdmin(@RequestParam(value="currentPage",defaultValue="1",required=false)
			int currentPage,String adminNumber,String adminName,Integer identity, Model model, HttpServletRequest request) {
		log.info("===ManageReaderController类的searchAdmin方法===");
		request.setAttribute("placeholder1", adminNumber);	// 显示查询时输入内容
		request.setAttribute("placeholder2", adminName);
		request.setAttribute("placeholder3", identity);
		model.addAttribute("pageMsg",msAdminService.selectReaderByPage(adminNumber, adminName, identity, currentPage));
		return "searchReader";
	}
	
	/**
	 * 点击查看按钮：查看读者借阅信息
	 */
	@RequestMapping(value = "/showReaderBorrowDetail")
	public String showReaderBorrowDetail(int id, String adminName, Model model, HttpSession httpSession, HttpServletRequest request) {
		log.info("===ManageReaderController类的showReaderBorrowDetail方法===");
//		System.out.println(" ==test== adminId: "+id);
		request.setAttribute("readerName", adminName);
		request.setAttribute("readerId", id);	// 将读者id传到读者借阅信息界面，用于读者的借阅操作（谁：readerId；操作哪本书：bookId）
		return borrowBookController.showBorrowBook(1, model, httpSession, request, id, null);
	}
	
	/**
	 * 添加读者
	 */
	@RequestMapping(value = "/toAddUser")
	public String toAddUser() {
		return "addUser";
	}
	@RequestMapping(value = "/addUser")
	public String addUser(MsAdmin admin, HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("===ManageReaderController类的addUser方法===");
		admin.setAdminNumber(request.getParameter("adminNumber"));
		admin.setAdminName(request.getParameter("adminName"));
		admin.setAdminPwd(request.getParameter("adminPwd"));
		int identityId = Integer.parseInt(request.getParameter("identityId"));
		admin.setIdentityId(identityId);
		response.setContentType("text/html; charset=UTF-8"); //转码
		PrintWriter out = response.getWriter();
		try {
			msAdminService.insertAdmin(admin);
			out.flush();
			out.println("<script>");
			out.println("alert('添加成功！');");
			out.println("</script>");
		} catch (Exception e) {
			e.printStackTrace();
			out.flush();
			out.println("<script>");
			out.println("alert('添加失败：用户账号已存在！');");
			out.println("</script>");
		}
		return "addUser";
	}
	/**
	 * 删除读者
	 * @throws IOException 
	 */
	@RequestMapping(value = "/deleteReader")
	public String deleteReader(int id, @RequestParam(value="currentPage",defaultValue="1",required=false)
	int currentPage, Model model, HttpServletResponse response) throws IOException {
		try {
			msAdminService.deleteAdmin(id);
		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/html; charset=UTF-8"); //转码
			PrintWriter out = response.getWriter();
			out.flush();
			out.println("<script>");
			out.println("alert('删除失败：该用户还有借阅的图书没归还或欠费！');");
			out.println("</script>");
		}
		return showReader(currentPage, model);
	}
	// 全部逾期
	@RequestMapping(value = "/showOverdue")
	public String showOverdue(HttpServletRequest request,
			@RequestParam(value="currentPage",defaultValue="1",required=false)int currentPage) {
		borrowBookController.checkIsPenalty(request, 0);	// 逾期处理
//		request.setAttribute("overdue",msOverdueService.selectAllOverdue());
		request.setAttribute("pageMsg",msOverdueService.selectOverdueByPage(currentPage));
		return "showAllOverdue";
	}
	
}
