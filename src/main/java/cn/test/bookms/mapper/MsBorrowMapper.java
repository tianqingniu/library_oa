package cn.test.bookms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.test.bookms.entity.MsBorrow;

public interface MsBorrowMapper {
	int insertBorrowDetail_borrow(MsBorrow msBorrow);
	int insertBorrowDetail_book(MsBorrow msBorrow);
	
	int deleteBorrowDetail(MsBorrow msBorrow);
	
	List<MsBorrow> selectOverdueDetail();
	List<MsBorrow> selectBorrowDetail_1(Integer adminId);
	
	MsBorrow selectByRIdAndBId(@Param("adminId")int adminId, @Param("borrowId")int borrowId);
	
	int updateBorrowReturnTime(@Param("adminId")int adminId, @Param("borrowId")int borrowId);
	
	int updateBorrowPenalty(@Param("borrowId")int borrowId, @Param("borrowPenalty")float borrowPenalty);
	
	Float selectSumPenalty(Integer adminId);
	
	Integer selectPenalty(@Param("adminId")int adminId);
	
	int countBorrowBook(@Param("adminId")int userId);
	
}
