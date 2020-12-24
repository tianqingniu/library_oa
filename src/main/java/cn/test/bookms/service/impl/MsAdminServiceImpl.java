package cn.test.bookms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.test.bookms.entity.MsAdmin;
import cn.test.bookms.entity.PageBean;
import cn.test.bookms.mapper.MsAdminMapper;
import cn.test.bookms.service.MsAdminService;
import cn.test.bookms.util.Message;


@Service("msAdminService")
public class MsAdminServiceImpl implements MsAdminService{
	Logger logger = Logger.getLogger(MsAdminServiceImpl.class);

	@Autowired
	private MsAdminMapper msAdminMapper;
	

	public MsAdmin selectByPrimaryKey(Integer id) {
		return msAdminMapper.selectByPrimaryKey(id);
	}

	public MsAdmin selectAdmin(Map<String,String> map) {
		return msAdminMapper.selectAdmin(map);
	}

	public int updatePwd(String newPwd, String adminNumber) {
		logger.info("***MsAdminServiceImpl类的updatePwd方法***");
		return msAdminMapper.updatePwd(newPwd, adminNumber);
	}

	public PageBean<MsAdmin> selectReaderByPage(String adminNumber, String adminName, Integer identity, int currentPage) {
		HashMap<String,Object> map = new HashMap<String,Object>();
        PageBean<MsAdmin> pageBean = new PageBean<MsAdmin>();
		
        //设置当前页数
        pageBean.setCurrPage(currentPage);
        
        //设置每页显示的数据
        int pageSize = Message.PAGE_SIZE;
        pageBean.setPageSize(pageSize);
        
        //设置总页数
        int totalCount = msAdminMapper.selectCount();
        pageBean.setTotalCount(totalCount);
		
        //设置总页数
        double tc = totalCount;
        Double num = Math.ceil(tc/pageSize);
        pageBean.setTotalPage(num.intValue());
        
        map.put("start", (currentPage-1)*pageSize);
        map.put("size", pageBean.getPageSize());
        map.put("adminNumber", adminNumber);
        map.put("adminName", adminName);
        map.put("identity", identity);
        
        //封装每页显示的数据
        List<MsAdmin> adminList = msAdminMapper.selectByPage(map);
        pageBean.setLists(adminList);
        
		return pageBean;
	}

	public int insertAdmin(MsAdmin admin) {
		logger.info("***MsAdminServiceImpl类的insertAdmin方法***");
		return msAdminMapper.insert(admin);
	}

	@Override
	public int deleteAdmin(int adminId) {
		logger.info("***MsAdminServiceImpl类的deleteAdmin方法***");
		return msAdminMapper.deleteByPrimaryKey(adminId);
	}

}
