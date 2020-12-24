package cn.test.bookms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsBorrow {
	private int borrowId;
	private int borrowReaderId;
	private int borrowBookId;
	private String borrowBooking;	// enum('未预约', '已预约') default'未预约',
	private String borrowRenew;		// enum('不续借', '续借') default'不续借',
	private Date borrowTime;
	private Date borrowReturnTime;
	private float borrowPenalty;	// default 0
	
}
