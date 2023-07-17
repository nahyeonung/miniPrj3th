package com.example.myapp.product.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface IProductRepository {
	int selectCnt();
}
