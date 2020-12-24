package cn.test.bookms.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.test.bookms.entity.MsBorrow;
import cn.test.bookms.mapper.MsBorrowMapper;
import cn.test.bookms.service.MsBorrowService;

@Service("MsBorrowService")
public class MsBorrowServiceImpl implements MsBorrowService {
	Logger logger = Logger.getLogger(MsBorrowServiceImpl.class);
	
	@Autowired
	private MsBorrowMapper msBorrowMapper;
	/**
	 * 插入借阅图书信息
	 */
	public int insertBorrowDetail(MsBorrow msBorrow, String borrowOrBook) {
		logger.info("***MsBorrowServiceImpl类的,insertBorrowDetail方法***");
		if (borrowOrBook == "toBorrow") {
			return msBorrowMapper.insertBorrowDetail_borrow(msBorrow);
		}else if (borrowOrBook == "toBook") {
			return msBorrowMapper.insertBorrowDetail_book(msBorrow);
		}
		return 0;
	}
	/**
	 * 删除借阅图书信息
	 */
	public int deleteBorrowDetail(MsBorrow msBorrow) {
		logger.info("***MsBorrowServiceImpl类的,deleteBorrowDetail方法***");
		return msBorrowMapper.deleteBorrowDetail(msBorrow);
	}
	
	public List<MsBorrow> selectBorrowDetail(Integer adminId) {
		logger.info("***MsBorrowServiceImpl类的,selectBorrowDetail方法***");
		if (adminId == 0) { // 传入的值为0 则查询全部逾期
			return msBorrowMapper.selectOverdueDetail();
		}
		return msBorrowMapper.selectBorrowDetail_1(adminId);
	}
	/**
	 * 检查借阅图书是否已借阅
	 */
	public MsBorrow selectByRIdAndBId(int adminId, int borrowId) {
		logger.info("***MsBorrowServiceImpl类的,selectByRIdAndBId方法***");
		return msBorrowMapper.selectByRIdAndBId(adminId, borrowId);
	}
	/**
	 * 续借
	 */
	public int updateBorrowReturnTime(int adminId, int borrowId) {
		logger.info("***MsBorrowServiceImpl类的,updateBorrowReturnTime方法***");
		return msBorrowMapper.updateBorrowReturnTime(adminId, borrowId);
	}
	/**
	 * 罚款
	 */
	public int updateBorrowPenalty(int borrowId, float penalty) {
		logger.info("***MsBorrowServiceImpl类的,updateBorrowPenalty方法***");
		return msBorrowMapper.updateBorrowPenalty(borrowId, penalty);
	}
	// 计算罚款总额
	public Float selectSumPenalty(Integer adminId) {
		logger.info("***MsBorrowServiceImpl类的,selectSumPenalty方法***");
		return msBorrowMapper.selectSumPenalty(adminId);
	}
	
	public Integer selectPenalty(int adminId) {
		logger.info("***MsBorrowServiceImpl类的,selectPenalty方法***");
		return msBorrowMapper.selectPenalty(adminId);
	}
	
	public int countBorrowBook(int adminId) {
		logger.info("***MsBorrowServiceImpl类的,countBorrowBook方法***");
		return msBorrowMapper.countBorrowBook(adminId);
	}

//	public List<MsAdmin> selectAllOverdue() {
//		logger.info("***MsBorrowServiceImpl类的,selectAllOverdue方法***");
//		return msBorrowMapper.selectAllOverdue();
//	}

}
