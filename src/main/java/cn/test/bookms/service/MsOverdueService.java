package cn.test.bookms.service;

import cn.test.bookms.entity.MsOverdue;
import cn.test.bookms.entity.PageBean;

public interface MsOverdueService {
	PageBean<MsOverdue> selectOverdueByPage(int currentPage);
	
}
