package com.example.myapp.product.model;

import java.sql.Blob;
import java.sql.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Product {
	private int productId;
	private int categoryId;
	private String categoryName;
	private String productName;
	private int productPrice;
	private String productDescription;
	private int productState;
	private int productStock;
	private Date productDate;
	private int imageId;
	private String imageName;
	private long imageSize;
	private String imageType;
	private byte[] imageData;
}