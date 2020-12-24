package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.test.bookms.entity.MsBook;
import cn.test.bookms.service.MsBookService;
import cn.test.bookms.util.Message;

/**
 * 批量添加书籍
 * 
 * @author wk
 *
 */
public class TestAddBooks {

	/**
	 * 增加数据库书籍的测试数据   
	 * 
	 * 执行一次增加一百条随机数据
	 */
	@Test
	public void AddBooks() {
		AbstractApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

		MsBookService msBookService = ac.getBean("msBookService", MsBookService.class);

		// 随机添加一百本书籍
		for (int i = 0; i <= 10; i++) {
			for (int j = 1; j <= 9; j++) {
				MsBook book = new MsBook();
				book.setTitle(bookName());
				book.setIsbn(getISBN());
				book.setAuthor(getName());
				book.setIntroduction(bookIntroduction());
				book.setPrice(price());
				book.setPublishTime(publishTime());
				book.setCreateTime(createTime());
				book.setCategoryId(j);
				book.setCreateAdmin((int)(Math.random()*9+1)%2==0?"Bob":"大天狗");
				//book.setImage("book.jpg");
				book.setDelFlg(1);
				book.setUpdatePreAdmin((int)(Math.random()*9+1)%2==0?"酒吞童子":"大天狗");
				msBookService.insertBook(book);
			}
		}
		ac.close();

	}
	/**
	 * 产生随机出版时间(1880.1-2010.12)
	 */
	public String publishTime() {
		int year = (int)(Math.random()*(2010-1880)+1880);
		int month = (int)(Math.random()*12+1);
		return year+"年"+month+"月";
	}
	
	/**
	 * 产生随机上架时间(2010.1.1-2018.12.30)
	 * 这里不判断闰年2月天数
	 */
	public Date createTime() {
		int year = (int)(Math.random()*10+2010);
		int month = (int)(Math.random()*12+1);
		int day = month%2==0?30:31;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date time = new Date();
		try {
			time = sdf.parse(year+"-"+month+"-"+day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	
	/**
	 * 随机价格
	 * @return
	 */
	public String price() {
		String price = (int)(Math.random()*9+1)+""+(int)(Math.random()*10);
		return  price;
	}
	
	/**
	 * 产生随机书简介
	 */
	public String bookIntroduction() {
		String introduction = commStr();
		for(int i=0;i<20;i++) {
			introduction+=commStr();
		}
		for(int i=0;i<100;i++) {
			if((int)(Math.random()*9+1)%2 ==0) {
				introduction+=commStr();   //创建了大量的字符串，消耗内存，实际生产中可使用StringBuilder代替
			}
		}
		return introduction;
	}
	
	/**
	 * 产生随机书名1-5位的书名
	 */
	public String bookName() {
		String bookName = commStr();
		for(int i=0;i<6;i++) {
			if((int)(Math.random()*9+1)%2 ==0) {
				bookName+=commStr();   //创建了大量的字符串，消耗内存，实际生产中可使用StringBuilder代替
			}
		}
		return bookName;
	}

	/**
	 * 产生随机ISBN
	 * @return
	 */
	public static String getISBN() {
		int str = (int) (Math.random() * 9) + 1;
		String pre = ((int) (Math.random() * 10)) % 2 == 0 ? "997" : "998";
		String num = System.currentTimeMillis() * str + "";
		return pre + num.substring(0, 9);
	}
	
	/**
	 * 产生随机姓名
	 * @return
	 */
	public static String getName() {
		String firstName = String.valueOf(Message.FIRST_NAME.charAt((int)(Math.random()*(Message.FIRST_NAME.length()))));  //获取一个字
		String lastName = commStr();
		if((int)(Math.random()*9+1)%2 ==0) {
			lastName+=commStr();
		}
		return firstName+lastName;
	}
	
	/**
	 * 产生一个随机字
	 */
	public static String commStr() {
		return String.valueOf(Message.COMMON_CHARACTERS.charAt((int)(Math.random()*(Message.COMMON_CHARACTERS.length()))));
	}
	
	@Test
	public void Test1() {
		for (int i = 1; i <= 50; i++) {
			System.out.println(createTime());
			if(i%10==0) {
				System.out.println();
			}
		}
	}

}
