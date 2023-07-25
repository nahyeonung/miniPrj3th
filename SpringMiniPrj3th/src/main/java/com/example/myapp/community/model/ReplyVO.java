package com.example.myapp.community.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class ReplyVO {
	 private int rno;
	 private String replyContent;
	 private Timestamp regDate;
		private String userId;
		private int writeId;
}
