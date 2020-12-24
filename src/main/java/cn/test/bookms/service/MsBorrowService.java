package cn.test.bookms.service;

import java.util.List;

import cn.test.bookms.entity.MsBorrow;

public interface MsBorrowService {
	int insertBorrowDetail(MsBorrow msBorrow, String borrowOrBook);
	
	int deleteBorrowDetail(MsBorrow msBorrow);
	
	List<MsBorrow> selectBorrowDetail(Integer adminId);
	
	MsBorrow selectByRIdAndBId(int adminId, int borrowId);
	
	int updateBorrowReturnTime(int adminId, int borrowId);
	
	int updateBorrowPenalty(int borrowId, float penalty);
	
	Float selectSumPenalty(Integer adminId);
	
	Integer selectPenalty(int adminId);
	
	int countBorrowBook(int adminId);
	
}
