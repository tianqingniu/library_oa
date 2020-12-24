package cn.test.bookms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.test.bookms.entity.MsAdmin;

public interface MsAdminMapper {
	
	/**
	 * 自动创建start
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);

    int insert(MsAdmin record);

    int insertSelective(MsAdmin record);

    MsAdmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MsAdmin record);

    int updateByPrimaryKey(MsAdmin record);
    
    /**
     * 自动创建end
     */
    
    /**
     * 以下新增
     */
    
    /**
	 * 登录(Map 放入登录号和密码)
	 */
	MsAdmin selectAdmin(Map<String,String> map);
    
	// 修改密码
    int updatePwd(@Param("newPwd")String newPwd, @Param("adminNumber")String adminNumber);
    
    /**
     * 查询总数
     * @return
     */
    int selectCount();
    
    /**
     * 分页查询
     * @param map
     * @return
     */
    List<MsAdmin> selectByPage(Map<String,Object> map);
    
}