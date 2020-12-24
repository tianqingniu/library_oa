package cn.test.bookms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.test.bookms.entity.MsCategory;
import cn.test.bookms.mapper.MsCategoryMapper;
import cn.test.bookms.service.MsCategoryService;

@Service("msCategoryService")
public class MsCategoryServiceImpl implements MsCategoryService{

	@Autowired
	private MsCategoryMapper msCategoryMapper;
	
	
	public List<MsCategory> selectCategoruList() {
		return msCategoryMapper.selectCategoruList();
	}


	public MsCategory selectByPrimaryKey(Integer id) {
		return msCategoryMapper.selectByPrimaryKey(id);
	}

	
}
