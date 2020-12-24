package cn.test.bookms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

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
import cn.test.bookms.entity.MsBorrow;
import cn.test.bookms.service.MsBookService;
import cn.test.bookms.service.MsBorrowService;
import cn.test.bookms.util.DateUtil;
// 借书界面
@Controller
@Slf4j
public class BorrowBookController {


	@Autowired
	private MsBookService msBookService;
	@Autowired
	private MsBorrowService msBorrowService;	
	@Autowired
	private ManageReaderController manageReaderController;
	
	/**
	 * 显示借阅图书信息
	 */
	@RequestMapping(value = "/showBorrowBook")
	public String showBorrowBook(@RequestParam(value="currentPage",defaultValue="1",required=false)
			int currentPage,Model model, HttpSession httpSession, HttpServletRequest request, Integer adminId, Integer identity) {
		log.info("===BookController类的,showBorrowBook方法===");
		MsAdmin user = (MsAdmin) httpSession.getAttribute("msAdmin");
		adminId = user.getId();
		checkIsPenalty(request, adminId);	// 超期处理
		Float sumPenalty = msBorrowService.selectSumPenalty(adminId);	// 计算罚款总额；	不能用float基本类型；要有Float，返回null时不会报错
		request.setAttribute("sumPenalty", sumPenalty);
		model.addAttribute("pageMsg",msBookService.selectByAdminId(adminId, currentPage));	// 获得个人借阅的图书信息
		return "borrowBook";
	}
	
	/**
	 * 借阅图书操作
	 * @throws IOException 
	 */
	@RequestMapping(value = "/toBorrowBook")
	public String toBorrowBook(HttpServletResponse response, HttpSession httpSession, MsBorrow msBorrow, int id, HttpServletRequest request, @RequestParam(value="currentPage",defaultValue="1",required=false)
			int currentPage,String title,String author,Model model) throws IOException {
		log.info("===BookController类的,toBorrowBook方法===");
		MsAdmin user = (MsAdmin) httpSession.getAttribute("msAdmin");
		response.setContentType("text/html; charset=UTF-8"); //转码
		PrintWriter out = response.getWriter();
		int countBook = msBorrowService.countBorrowBook(user.getId());	// 已借阅图书数量
		if (null == msBorrowService.selectByRIdAndBId(user.getId(), id) && 5 > countBook) {	// 检查读者是否已借阅过这本书, 同一本书只可借一册，并且借阅数量最多5本
			msBorrow.setBorrowReaderId(user.getId());	// 谁借书
			msBorrow.setBorrowBookId(id);	// 借哪一本书
			msBorrowService.insertBorrowDetail(msBorrow, "toBorrow");	// 插入借阅信息
			msBookService.updateBookRemainder(id, "borrow");	// 数量-1
			out.flush();
			out.println("<script>");
			out.println("alert('借阅成功，记得在一个月内归还图书哦！');");
			out.println("</script>");
		}else {
			out.flush();
			out.println("<script>");
			out.println("alert('您已借过这本书或已借阅5本书，同一本书只可借一册哦，并且每人最多可借5本书！');");
			out.println("</script>");
		}
		model.addAttribute("pageMsg",msBookService.selectByPage(title, author, currentPage));	// 获得图书数据
		// 来自哪个页面就返回哪个页面
		String to = request.getParameter("from");
		return to;
	}
	
	/**
	 * 归还图书操作
	 * @throws IOException 
	 */
	@RequestMapping(value = "/toReturnBook")
	public String toReturnBook(HttpSession httpSession, HttpServletResponse response, MsBorrow msBorrow, int id, Integer readerId, 
			HttpServletRequest request, @RequestParam(value="currentPage",defaultValue="1",required=false)
			int currentPage,String title,String author,Model model) throws IOException {
		log.info("===BookController类的,toBorrowBook方法===");
		// 若是管理员操作读者，会传过来readerId，即对哪个读者进行操作
		if (readerId != null) {	// 管理员管理读者归还
			msBorrow.setBorrowReaderId(readerId);
		}else {	// 读者自己归还,管理员也算读者
			MsAdmin user = (MsAdmin) httpSession.getAttribute("msAdmin");
			msBorrow.setBorrowReaderId(user.getId());
		}
		msBorrow.setBorrowBookId(id);	// 归还哪一本书
		msBorrowService.deleteBorrowDetail(msBorrow);	// 删除借阅信息
		msBookService.updateBookRemainder(id, "return");	// 剩余数量+1
		response.setContentType("text/html; charset=UTF-8"); //转码
		PrintWriter out = response.getWriter();
		out.flush();
		out.println("<script>");
		out.println("alert('归还成功！图书剩余量+1。');");
		out.println("</script>");
		return showBorrowBook(currentPage,model, httpSession, request, readerId, null);	// 获得图书数据
	}

	/**
	 * 续借
	 * @param currentPage 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/toRenewBook")
	public String toRenewBook(@RequestParam(value="currentPage",defaultValue="1",required=false)
			int currentPage,String title,String author,Model model, HttpSession httpSession, int id, Integer readerId, HttpServletResponse response, HttpServletRequest request) throws IOException {
		log.info("===BookController类的,toRenewBook方法===");
		// 若是管理员操作读者，会传过来readerId，即对哪个读者进行操作
//		String readerIdStr = request.getParameter("readerId");
//		Integer readerIdInt = null;
		int count = 0;
		if (readerId != null) {	// 管理员管理读者操作
			count = msBorrowService.updateBorrowReturnTime(readerId, id);
		}else {	// 读者自己操作
			MsAdmin user = (MsAdmin) httpSession.getAttribute("msAdmin");
			count = msBorrowService.updateBorrowReturnTime(user.getId(), id);
		}
		response.setContentType("text/html; charset=UTF-8"); //转码
		PrintWriter out = response.getWriter();
		if (count == 1) {	// 未续借
			out.flush();
			out.println("<script>");
			out.println("alert('续借成功，归还时间推迟15天。');");
			out.println("</script>");
		}else if (count == 0) {	// 已续借
			out.flush();
			out.println("<script>");
			out.println("alert('您已续借过这本书，同一本书只可续借一次哦！');");
			out.println("</script>");
		}
		return showBorrowBook(currentPage, model, httpSession, request, readerId, null);	// 获得图书数据
	}
	
	/**
	 * 预约
	 */
	@RequestMapping(value = "/toBook")
	public String toBook(@RequestParam(value="currentPage",defaultValue="1",required=false)
			int currentPage,String title,String author,Model model, HttpSession httpSession, HttpServletResponse response, HttpServletRequest request, MsBorrow msBorrow, int id) throws IOException {
		log.info("===BookController类的,toBook方法===");
		MsAdmin user = (MsAdmin) httpSession.getAttribute("msAdmin");
		response.setContentType("text/html; charset=UTF-8"); //转码
		PrintWriter out = response.getWriter();
		if (msBorrowService.selectByRIdAndBId(user.getId(), id) == null) {	// 检查读者是否已借阅过这本书, 同一本书只可借一册
			msBorrow.setBorrowReaderId(user.getId());	// 谁借书
			msBorrow.setBorrowBookId(id);	// 借哪一本书
			msBorrowService.insertBorrowDetail(msBorrow, "toBook");	// 插入借阅信息
//			msBookService.updateBookRemainder(id, "borrow");	// 剩余数量-1
			out.flush();
			out.println("<script>");
			out.println("alert('预约成功，请注意领取消息！');");
			out.println("</script>");
		}else {
			out.flush();
			out.println("<script>");
			out.println("alert('您已预约过(或借过)这本书，同一本书只可预约(或借)一次哦！');");
			out.println("</script>");
		}
		model.addAttribute("pageMsg",msBookService.selectByPage(title, author, currentPage));	// 获得图书数据
		// 来自哪个页面就返回哪个页面
		String to = request.getParameter("from");
		return to;
	}
	
	/**
	 * 领取预约的书
	 */
	@RequestMapping(value = "/toTakeBook")
	public String toTakeBook(HttpServletResponse response, HttpSession httpSession, MsBorrow msBorrow, int id, Integer readerId, HttpServletRequest request, @RequestParam(value="currentPage",defaultValue="1",required=false)
			int currentPage,Model model) throws IOException {
		log.info("===BookController类的,toTakeBook方法===");
		response.setContentType("text/html; charset=UTF-8"); //转码
		PrintWriter out = response.getWriter();
		// 若是管理员操作读者，会传过来一个readerId的值，即对哪个读者进行操作
		if (readerId != null) {	// 管理员管理读者操作
			msBorrow.setBorrowReaderId(readerId);	// 管理员帮读者操作
		}else {	// 读者自己操作
			MsAdmin user = (MsAdmin) httpSession.getAttribute("msAdmin");
			msBorrow.setBorrowReaderId(user.getId());	// 读者自己操作
		}
		msBorrow.setBorrowBookId(id);	// 借哪一本书
		msBorrowService.deleteBorrowDetail(msBorrow);	// 删除预约信息
		msBorrowService.insertBorrowDetail(msBorrow, "toBorrow");	// 插入借阅信息
		msBookService.updateBookRemainder(id, "borrow");	// 数量-1
		out.flush();
		out.println("<script>");
		out.println("alert('借阅成功，记得在一个月内归还图书哦！');");
		out.println("</script>");
		return showBorrowBook(currentPage, model, httpSession, request, readerId, null);	
	}
	
	/**
	 * 缴费1，并且还书：书总量不变，余量+1
	 */
	@RequestMapping(value = "/toPayAndReturn")
	public String toPayAndReturn(HttpSession httpSession, HttpServletResponse response, MsBorrow msBorrow, int id, Integer readerId, 
			HttpServletRequest request, @RequestParam(value="currentPage",defaultValue="1",required=false)
			int currentPage,String title,String author,Model model, String from) throws IOException {
		log.info("===BookController类的,toPayAndReturn方法===");
		System.out.println("bookId: "+id+"  readerId: "+readerId);
		if (from.equals("showAllOverdue")) {	// 先还书，再跳转到showAllOverdue页面
			toReturnBook(httpSession, response, msBorrow, id, readerId, request, currentPage, title, author, model);
			return manageReaderController.showOverdue(request, currentPage);
		}else if (from.equals("borrowBook")) {	// 个人借阅页面
			return toReturnBook(httpSession, response, msBorrow, id, readerId, request, currentPage, title, author, model);
		}
		return null;
	}
	
	/**
	 * 缴费2，但未还书 书总量-1，余量不变
	 */
	@RequestMapping(value = "/toPayNoReturn")
	public String toPayNoReturn(HttpSession httpSession, HttpServletResponse response, MsBorrow msBorrow, int id, Integer readerId, 
			HttpServletRequest request, @RequestParam(value="currentPage",defaultValue="1",required=false)
			int currentPage,String title,String author,Model model, String from) throws IOException {
		log.info("===BookController类的,toPayNoReturn方法===");
		System.out.println("bookId: "+id+"  readerId: "+readerId);
		response.setContentType("text/html; charset=UTF-8"); //转码
		PrintWriter out = response.getWriter();
		out.flush();
		try {
			msBorrow.setBorrowReaderId(readerId);	// 哪一位读者
			msBorrow.setBorrowBookId(id);	// 归还哪一本书
			msBorrowService.deleteBorrowDetail(msBorrow);	// 删除借阅信息
			msBookService.updateBookSum(id);	// 书总数量-1
			out.println("<script>");
			out.println("alert('操作成功！记录已清除！该书总量-1。');");
			out.println("</script>");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<script>");
			out.println("alert('操作失败！请在管理读者界面进行缴费清除！');");
			out.println("</script>");
		}
		if (from.equals("showAllOverdue")) {	// 跳转到showAllOverdue页面
			return manageReaderController.showOverdue(request, currentPage);
		}else if (from.equals("borrowBook")) {	// 跳转到个人借阅页面
			return showBorrowBook(currentPage, model, httpSession, request, readerId, null);	
		}
		return null;
	}
	
	/**
	 * 逾期处理
	 */
	public Integer checkIsPenalty(HttpServletRequest request, int adminId) {
		Integer isPenalty = null;
		Date dateNow = new Date();
		List<MsBorrow> list = msBorrowService.selectBorrowDetail(adminId);
		for (MsBorrow msBorrow : list) {	// 循环判断是否超期
			if (msBorrow.getBorrowReturnTime() != null) {	// getBorrowReturnTime()为空时不能直接get，会报空指针异常，要先判断是否为空
				if (dateNow.getTime() > msBorrow.getBorrowReturnTime().getTime()) {	// 循环判断图书是否超期
					int days = DateUtil.getDay(msBorrow.getBorrowReturnTime(), dateNow);
					float penalty = (float) (days * 0.1);	// 每超一天罚款0.1元
					msBorrowService.updateBorrowPenalty(msBorrow.getBorrowId(), penalty);	// 更新罚款金额
					isPenalty = 1;
				}
			}
		}
		request.setAttribute("borrowList", msBorrowService.selectBorrowDetail(adminId));
		request.setAttribute("date", new Date());	// 获得当前时间与图书归还时间进行对比，用于超期罚款
		request.setAttribute("isPenalty", isPenalty);	// 是否逾期罚款
		return isPenalty;
	}
	
}
