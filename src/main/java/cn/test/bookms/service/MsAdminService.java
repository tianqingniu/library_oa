package cn.test.bookms.service;

import java.util.Map;

import cn.test.bookms.entity.MsAdmin;
import cn.test.bookms.entity.PageBean;

public interface MsAdminService {

	/**
	 * 通过用户名密码登
	 * 登录(Map 放入登录号和密码)
	 * @param adminNumber
	 * @param adminPwd
	 * @return
	 */
	MsAdmin selectAdmin(Map<String,String> map);
	
	/**
	 * 查询通过主键
	 */
	MsAdmin selectByPrimaryKey(Integer id);
	
	// 修改密码
	int updatePwd(String newPwd, String adminNumber);
	
	// 显示读者，所有或查询
	PageBean<MsAdmin> selectReaderByPage(String adminNumber, String adminName, Integer identity, int currentPage);
	
	// 添加用户
	int insertAdmin(MsAdmin admin);
	// 删除
	int deleteAdmin(int adminId);
	
}
