package test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.test.bookms.entity.MsBorrow;
import cn.test.bookms.service.MsBorrowService;

public class TestBorrowBook {
	@Test
	public void borrowbook() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

		MsBorrowService msBorrowService = ac.getBean("msBorrowService", MsBorrowService.class);
		List<MsBorrow> list = msBorrowService.selectBorrowDetail(4);	// 谁
		for (MsBorrow msBorrow : list) {
			System.out.println("===borrowDetailMsg: "+msBorrow);
		}
		ac.close();
		
	}
}
