package com.example.myapp.product.model;

import lombok.Data;

@Data
public class UploadImage {
	private int fileId;
	private int boardId;
	private String fileName;
	private long fileSize;
	private String fileContentType;
	private byte[] fileData;
}
