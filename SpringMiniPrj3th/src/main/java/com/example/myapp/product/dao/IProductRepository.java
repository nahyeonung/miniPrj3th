package com.example.myapp.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.myapp.product.model.Category;
import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.UploadProduct;

@Repository
@Mapper
public interface IProductRepository {
	List<Product> selectAllProduct(@Param("categoryId") int categoryId);
	
	int insertCategory(@Param("categoryName") String categoryName);
	
	List<Category> selectAllCategory();
	
	int deleteCategory(@Param("categoryId") int categoryId);
	
	int insertProduct(@Param("product") UploadProduct product);
	
	int insertProductImg(@Param("img") UploadProduct img, @Param("productId") int productId);
	
	int selectProductId();
}
