package com.sellopy.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.OffsetMapping.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.sellopy.Repository.ProductRepository;
import com.sellopy.dto.ProductDto;
import com.sellopy.dto.ProductResponse;
import com.sellopy.model.Product;
import com.sellopy.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRespository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public Boolean saveProduct(ProductDto productDto) {
		
		//start model mapper use only one line of code for 6 line
		Product product = mapper.map(productDto , Product.class); 
		
		Product save = productRespository.save(product);
		if(ObjectUtils.isEmpty(save)) {
			return false;
		}
		
		return true;
	}
	

	
	@Override
	public List<ProductDto> getAllProduct() {
		List<Product> productList = productRespository.findAll();
		List<ProductDto>productDtoList = productList.stream()
				.map(product->mapper.map(product,ProductDto.class))
				.collect(Collectors.toList());
				return productDtoList;
	}

	@Override
	public ProductDto getProductById(Integer id) {
		Optional<Product> findByProduct = productRespository.findById(id);
		if(findByProduct.isPresent()) {
			Product product = findByProduct.get();
			ProductDto productDto = mapper.map(product, ProductDto.class);
			return productDto;
		}
		return null;
	}

	@Override
	public Boolean deleteProduct(Integer id) {
		Optional<Product> findById = productRespository.findById(id);
		if(findById.isPresent()) {
			Product product = findById.get();
			productRespository.delete(product);
			return true;
		}
		return false;
	}



	@Override
	public ProductResponse getProductsWithPagination(int pageNo, int pageSize, String sortBy, String sortDir) {

//		Sort sort = Sort.by(sortBy).ascending();
//		Sort sort2 = Sort.by(sortBy).descending();

		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Product> page = productRespository.findAll(pageable);

		List<Product> products = page.getContent();

		List<ProductDto> productsDtos = products.stream().map(prod -> mapper.map(prod, ProductDto.class)).toList();
		long totalElements = page.getTotalElements();
		int totalPages = page.getTotalPages();
		boolean first = page.isFirst();
		boolean last = page.isLast();

//		ProductResponse productResponse=new ProductResponse();
//		productResponse.setProducts(productsDtos);
//		productResponse.setTotalElements(totalElements);

		ProductResponse productResponse = ((Object) ProductResponse.builder()).products(productsDtos).totalElements(totalElements)
				.totalPages(totalPages).isFirst(first).isLast(last).pageNo(pageNo).pageSize(pageSize).build();

		return productResponse;

	}




}