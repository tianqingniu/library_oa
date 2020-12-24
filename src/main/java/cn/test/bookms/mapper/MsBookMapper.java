package cn.test.bookms.mapper;

import java.util.List;
import java.util.Map;

import cn.test.bookms.entity.MsBook;

public interface MsBookMapper {
	
	/**
	 * 借还图书，通过图书id加或减图书数量
	 */
	public int updateBookRemainderSub(Integer id);
	public int updateBookRemainderAdd(Integer id);
	
	/**
	 *  缴费2，但未还书 书总量-1，余量不变
	 */
	public int updateBookSum(Integer id);
	
	/**
	 * 通过主键删除(逻辑删除)
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入书籍
     * @param record
     * @return
     */
    int insert(MsBook record);
    /**
     * 插入书籍    
     * @param record
     * @return
     */
    int insertSelective(MsBook record);

    MsBook selectByPrimaryKey(Integer id);

    /**
     * 通过主键更新书籍
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(MsBook record);

    
    int updateByPrimaryKey(MsBook record);
    
    /**
     * 查询借阅信息
     */
    List<MsBook> selectByAdminId(int adminId);
    
    /**
     * 分页查询
     * @param map
     * @return
     */
    List<MsBook> selectByPage(Map<String,Object> map);
    
    /**
     * 查询总数
     * @return
     */
    int selectCount();
    
    
    /**
     * 最近上架的图书(最近2个月)
     */
    List<MsBook>  selectNewBook();
    
    
    /**
     * 所有下架的图书
     */
    List<MsBook>  selectBookDel();
    
    
    /**
     * 重新上架图书
     */
    int updateBackBook(int id);
    
    
    /**
     * 彻底删除图书
     */
    int deleteBookReal(int id);
    
    
    
    
}