package com.example.myapp.community.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter @ToString
public class ReplyVO {
	 private int rno;
	 private String replyContent;
	 private Timestamp regDate;
		private String userId;
		private int writeId;
}
