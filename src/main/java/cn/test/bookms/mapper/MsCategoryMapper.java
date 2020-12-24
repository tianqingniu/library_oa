package cn.test.bookms.mapper;

import java.util.List;

import cn.test.bookms.entity.MsCategory;

public interface MsCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MsCategory record);

    int insertSelective(MsCategory record);

    MsCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MsCategory record);

    int updateByPrimaryKey(MsCategory record);
    
    
    List<MsCategory> selectCategoruList();
    
}