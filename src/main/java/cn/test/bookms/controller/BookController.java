package cn.test.bookms.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.test.bookms.entity.MsAdmin;
import cn.test.bookms.entity.MsBook;
import cn.test.bookms.entity.MsCategory;
import cn.test.bookms.service.MsBookService;
import cn.test.bookms.service.MsCategoryService;
import cn.test.bookms.util.Message;

@Controller
@Slf4j
public class BookController {

	@Autowired
	private MsBookService msBookService;
	@Autowired
	private MsCategoryService msCategoryService;
	@Autowired
	private BorrowBookController borrowBookController;


	/**
	 * 显示所有图书
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/showBook")
	public String showAllBookByPage(@RequestParam(value="currentPage",defaultValue="1",required=false)
			int currentPage,String title,String author,Model model, HttpSession httpSession, HttpServletRequest request) {
		log.info("===BookController类的showAllBookByPage方法===");
		model.addAttribute("pageMsg",msBookService.selectByPage(title, author, currentPage));
		MsAdmin user = (MsAdmin) httpSession.getAttribute("msAdmin");
		borrowBookController.checkIsPenalty(request, user.getId());	// 超期处理
		return "showAllBook";
	}

	/******************查询图书***********************
	 * 
	 */
	//跳转页面
	@RequestMapping(value = "/searchBook")
	public String toSearchBook() {
		return "searchBook";
	}
	@RequestMapping(value = "/searchBookPage")
	public String searchBook(@RequestParam(value="currentPage",defaultValue="1",required=false)
			int currentPage,String title,String author,Model model, HttpServletRequest request) {
		request.setAttribute("placeholder1", title);	// 显示查询时输入内容
		request.setAttribute("placeholder2", author);
		model.addAttribute("pageMsg",msBookService.selectByPage(title, author, currentPage));
		return "searchBook";
	}
	/**
	 * ****************查询图书end*********************
	 */
	
	
	/**
	 * 查看图书详细信息
	 */
	@RequestMapping(value="/bookDetail")
	public String showBookDetail(int id,Model model) {
		MsBook book = msBookService.selectByID(id);
		MsCategory cate = msCategoryService.selectByPrimaryKey(book.getCategoryId());
		model.addAttribute("book",book);
		model.addAttribute("cate", cate);
		return "bookDetail";
	}
	
	
	/**
	 * 跳转到添加图书页面
	 * @return
	 */
	@RequestMapping(value = "/toAddNewBook")
	public String toAddNewBook() {
		return "addNewBook";
	}
	/**
	 * 添加图书
	 * @param book
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addNewBook")
	public String addNewBook(MsBook book,MultipartFile file,HttpServletRequest request,HttpSession httpSession) {
		MsAdmin admin = (MsAdmin)httpSession.getAttribute("msAdmin");
		System.out.println("页面提交过来的表单："+book);
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		book.setCreateTime(new Date());
		book.setCreateAdmin(admin.getAdminName());
		book.setUpdatePreAdmin(admin.getAdminName());
		book.setDelFlg(1);
		
		String filePath = request.getSession().getServletContext().getRealPath("/static/images");; //定义图片上传后的路径
		System.out.println("文件上传路径:"+filePath);
		// 返回 图片名字
		String imgName = fileOperate(file,filePath);
		book.setImage(Message.IMG_LOCAL_PATH + imgName);
		System.out.println("添加数据后的book:"+book);
		msBookService.insertBook(book);
		return "redirect:newBookList";
	}
	
	
	/**
	 * 查询最近上架的图书
	 */
	@RequestMapping(value="/newBookList")
	public String newBookList(Model model) {
		model.addAttribute("newBookList", msBookService.selectNewBook());
		return "newBookList";
	}
	
	/**
	 * 下架图书
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteBook")
	public String deleteBook(int id) {
		msBookService.deleteByPrimaryKey(id);
		return "redirect:showBook";
	}
	
	/**
	 * 下架新上架的图书
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteBookNewList")
	public String deleteBookNewList(int id) {
		msBookService.deleteByPrimaryKey(id);
		return "redirect:newBookList";
	}
	
	/**
	 * 下架查询到的图书
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteSearchBook")
	public String deleteSearchBook(int id) {
		msBookService.deleteByPrimaryKey(id);
		return "redirect:searchBook";
	}
	
	
	/**
	 * 已下架图书列表
	 */
	@RequestMapping(value = "/deleteBookList")
	public String deleteBookList(Model model) {
		model.addAttribute("bookList", msBookService.selectBookDel());
		return "delBookList";
	}
	
	
	/**
	 * 跳转到修改书籍信息
	 */
	@RequestMapping(value = "/toUpdateBook")
	public String updateBookPage(int id,Model model) {
		MsBook book = msBookService.selectByID(id);
		MsCategory cate = msCategoryService.selectByPrimaryKey(book.getCategoryId());
		model.addAttribute("book", book);
		model.addAttribute("cate", cate);
		return "editBook";
	}
	
	/**
	 * 修改图书信息
	 * @param book
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/updateBook")
	public String updateBook(MsBook book, MultipartFile file,HttpServletRequest request) {
		System.out.println("上传过来的图书信息:"+book);		
		MsBook oldBook = msBookService.selectByID(book.getId());
		book.setPublishTime(oldBook.getPublishTime());
		MsAdmin admin = (MsAdmin)request.getSession().getAttribute("msAdmin");
		book.setUpdatePreAdmin(admin.getAdminName());
		// 保证图片唯一性判断
		if(StringUtils.isEmpty(oldBook.getImage())){
			// 新图片上传
			String filePath = request.getSession().getServletContext().getRealPath("/static/images");
			String newImgName = fileOperate(file,filePath);
			book.setImage(Message.IMG_LOCAL_PATH + newImgName);
		}else{
			// 旧图片取缔  仅进行图片覆盖
			String filePath = request.getSession().getServletContext().getRealPath("/static/images") + "/" + oldBook.getImage();
			fileUpdateOperate(file,filePath);
		}
		System.out.println("添加完成的图书信息:"+book);
		msBookService.updateByPrimaryKeySelective(book);
		return "redirect:showBook";
		
	}



	/**
	 * 重新上架图书
	 */
	@RequestMapping("/updateBackBook")
	public String updateBackBook(int id) {
		msBookService.updateBackBook(id);
		return "redirect:deleteBookList";
	}
	
	/**
	 * 彻底删除图书
	 */
	@RequestMapping("/deleteBookReal")
	public String deleteBookReal(int id) {
		msBookService.deleteBookReal(id);
		return "redirect:deleteBookList";
	}
	
	
	/**
	 * 上传新图片文件预处理方法
	 * @param file
	 * @param filePath
	 * @return
	 */
	private String fileOperate(MultipartFile file,String filePath) {
//		System.out.println("进入文件操作方法");
//		String originalFileName = file.getOriginalFilename();//获取原始图片的扩展名
//		System.out.println("图片原始名称："+originalFileName);
		String newFileName = UUID.randomUUID() +".jpg";  //新的文件名称
		System.out.println("新的文件名称："+newFileName);

		File targetFile = new File(filePath,newFileName); //创建新文件
		// 真实上传方法
		fileUpload(file,targetFile);

		return newFileName;
	}

	/**
	 * 旧图片覆盖预处理方法
	 * @param file
	 * @param filePath
	 */
	private void fileUpdateOperate(MultipartFile file, String filePath) {
		File targetFile = new File(filePath);
		fileUpload(file,targetFile);
	}

	/**
	 * 上传文件方法
	 * @param file
	 * @param targetFile
	 */
	private void fileUpload(MultipartFile file,File targetFile) {
		try {
			file.transferTo(targetFile); //把本地文件上传到文件位置 , transferTo()是springmvc封装的方法，用于图片上传时，把内存中图片写入磁盘
			System.out.println("文件上传成功！");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
