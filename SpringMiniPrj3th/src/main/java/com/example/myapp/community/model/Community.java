package com.example.myapp.community.model;



import java.sql.Date;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter

public class Community {
	private String userId;
	private int writeId;
	private Date writeDate;
	private String writeTitle;
	private String writeContent;
	 private String formattedWriteDate;
	 private int page;
	 private int bbsCount;

//	private int readCount;
//	private int replyNumber;
//	private int replyStep;
//	private int page;
	@Override
	public String toString() {
		return "Community [userId=" + userId + ", writeId=" + writeId + ", writeDate=" + writeDate + ", writeTitle=" + writeTitle + ", writeDate=" + writeDate
			+ ", writeContent=" + writeContent  +  "]";
	}
}
