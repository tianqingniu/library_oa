package cn.test.bookms.mapper;

import java.util.List;
import java.util.Map;

import cn.test.bookms.entity.MsOverdue;

public interface MsOverdueMapper {
	 int selectCount();
	 List<MsOverdue> selectOverdueByPage(Map<String,Object> map);
}
