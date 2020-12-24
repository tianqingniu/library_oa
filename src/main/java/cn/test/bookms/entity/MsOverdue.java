package cn.test.bookms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsOverdue {
	private int overId;
	private String adminNumber;
	private String adminName;
	private String ISBN;
	private String title;
	private float borrowPenalty;
	private int bookId;
	private int readerId;
	
}
