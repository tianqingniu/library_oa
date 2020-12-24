package test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.test.bookms.entity.MsBook;
import cn.test.bookms.entity.PageBean;
import cn.test.bookms.service.MsBookService;

public class TestBook {

	/**
	 * 测试书籍的分页显示
	 */
	@Test
	public void bookPage() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

		MsBookService msBookService = ac.getBean("msBookService", MsBookService.class);
		PageBean<MsBook> page = msBookService.selectByPage("","",1);
		List<MsBook>  bookList = page.getLists();
		for(MsBook list:bookList) {
			System.out.println(list);
		}
		ac.close();
		
	}
}
