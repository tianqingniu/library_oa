package cn.test.bookms.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.test.bookms.entity.MsOverdue;
import cn.test.bookms.entity.PageBean;
import cn.test.bookms.mapper.MsOverdueMapper;
import cn.test.bookms.service.MsOverdueService;
import cn.test.bookms.util.Message;

@Service("msOverdueService")
public class MsOverdueServiceImpl implements MsOverdueService {
	Logger logger = Logger.getLogger(MsOverdueServiceImpl.class);
	
	@Autowired
	private MsOverdueMapper msOverdueMapper;

	public PageBean<MsOverdue> selectOverdueByPage(int currentPage) {
		HashMap<String,Object> map = new HashMap<String,Object>();
        PageBean<MsOverdue> pageBean = new PageBean<MsOverdue>();
		
        //设置当前页数
        pageBean.setCurrPage(currentPage);
        
        //设置每页显示的数据
        int pageSize = Message.PAGE_SIZE;
        pageBean.setPageSize(pageSize);
        
        //设置总页数
        int totalCount = msOverdueMapper.selectCount();
        pageBean.setTotalCount(totalCount);
		
        //设置总页数
        double tc = totalCount;
        Double num = Math.ceil(tc/pageSize);
        pageBean.setTotalPage(num.intValue());
        
        map.put("start", (currentPage-1)*pageSize);
        map.put("size", pageBean.getPageSize());
        
        //封装每页显示的数据
        List<MsOverdue> list = msOverdueMapper.selectOverdueByPage(map);
        pageBean.setLists(list);
        
		return pageBean;
	}

}
