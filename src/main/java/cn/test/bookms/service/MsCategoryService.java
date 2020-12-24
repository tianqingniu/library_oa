package cn.test.bookms.service;

import java.util.List;

import cn.test.bookms.entity.MsCategory;

public interface MsCategoryService {

	/**
	 * 查询所有的目录
	 * @return
	 */
	List<MsCategory> selectCategoruList();
	
	/**
	 * 通过主键查询目录
	 * @param id
	 * @return
	 */
	MsCategory selectByPrimaryKey(Integer id);
}
