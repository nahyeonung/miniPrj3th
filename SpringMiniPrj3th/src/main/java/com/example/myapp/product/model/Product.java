package com.example.myapp.product.model;

import java.sql.Blob;
import java.sql.Date;

import lombok.Data;

@Data
public class Product {
	private String categoryName;
	private String productName;
	private int productPrice;
	private String productDescription;
	private int productStock;
	private Date productDate;
	private String imageName;
	private Blob imageData;
}
