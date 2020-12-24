package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.test.bookms.entity.MsAdmin;
import cn.test.bookms.service.MsAdminService;

public class TestLoginService {
	@Test
	public void testLogin() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

		MsAdminService msAdminService = ac.getBean("msAdminService", MsAdminService.class);
		Map<String, String> map = new HashMap<String, String>();
		map.put("adminNumber", "1102");
		map.put("adminPwd", "123456");
		MsAdmin result = msAdminService.selectAdmin(map);
		System.out.println("result=" + result);
		ac.close();
	}

	@Test
	public void testFind() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

		MsAdminService msAdminService = ac.getBean("msAdminService", MsAdminService.class);
		MsAdmin result = msAdminService.selectByPrimaryKey(1);
		System.out.println("result=" + result);
		ac.close();
	}

}
