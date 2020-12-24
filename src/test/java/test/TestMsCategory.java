package test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.test.bookms.entity.MsCategory;
import cn.test.bookms.service.MsCategoryService;

public class TestMsCategory {

	@Test
	public void shwoAllCategory() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		MsCategoryService ms = ac.getBean("msCategoryService",MsCategoryService.class);
		List<MsCategory>  list =ms.selectCategoruList();
		for(MsCategory cate:list) {
			System.out.println(cate);
		}
		ac.close();
	}
}
