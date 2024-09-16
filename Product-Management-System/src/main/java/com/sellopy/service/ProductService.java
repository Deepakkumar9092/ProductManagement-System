package com.sellopy.service;

import java.util.List;

import com.sellopy.dto.ProductDto;
import com.sellopy.dto.ProductResponse;


public interface ProductService {
	
	public Boolean saveProduct(ProductDto productDto);
	
	public List<ProductDto>getAllProduct();
	
	public ProductDto getProductById(Integer id);
	
	public Boolean deleteProduct(Integer id);
	
	public ProductResponse getProductsWithPagination(int pageNo, int pageSize, String sortBy, String sortDir);

}
