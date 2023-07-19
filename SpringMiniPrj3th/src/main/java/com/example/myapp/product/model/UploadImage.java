package com.example.myapp.product.model;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UploadImage {
	private int imageId;
	private String imageName;
	private long imageSize;
	private String imageType;
	private byte[] imageData;
	private int productId;
}
