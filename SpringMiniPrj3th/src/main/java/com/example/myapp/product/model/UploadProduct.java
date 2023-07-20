package com.example.myapp.product.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UploadProduct {
	private int productId;
	private int categoryId;
	private String productName;
	private int productPrice;
	private int productStock;
	private String productDescription;
	private String imgName;
	private long imgSize;
	private String imgType;
	private byte[] imgData;
	private MultipartFile file;
}
