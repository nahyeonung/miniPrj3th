package com.example.myapp.community.model;



import java.sql.Date;

import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;

//@Getter @Setter
@Data
public class Community {
	private String userId;
	private int writeId;
	private Date writeDate;
	private String writeTitle;
	private String writeContent;
//	private int readCount;
//	private int replyNumber;
//	private int replyStep;
//	private int page;
//	@Override
//	public String toString() {
//		return "Board [userId=" + userId + ", writeId=" + writeId + ", writeDate=" + writeDate + ", masterId=" + masterId + ", writeTitle=" + writeTitle + ", writeDate=" + writeDate
//			+ ", writeContent=" + writeContent + ", readCount=" + readCount 
//			+ ", replyNumber=" + replyNumber + ", replyStep=" 
//			+ replyStep +  "]";
//	}
}
