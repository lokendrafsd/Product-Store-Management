package com.shopapplication.services.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopapplication.services.exceptions.ProductNotFoundException;
import com.shopapplication.services.model.ProductRequest;
import com.shopapplication.services.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "${app.context.path}")
@Slf4j
public class ProductController {

	@Autowired
	private ProductRepository repository;

	/**
	 * @throws ProductNotFoundException
	 * @Def: Method to Get All Products
	 * @Response: Success = HttpStatus(200), ProductRequest
	 */
	@GetMapping("/product")
	public List<ProductRequest> getAllProducts() throws ProductNotFoundException {
		log.info("STARTED: Shop Product Maintainance API: getAllProducts ");
		List<ProductRequest> response = repository.findAll();
		if (CollectionUtils.isEmpty(response)) {
			log.info("ERROR: Shop Product Maintainance API: getAllProducts, failed as there were no records");
			throw new ProductNotFoundException("No Products Found");
		}
		log.info("COMPLETED: Shop Product Maintainance API: getAllProducts, Response --> {} ", response);
		return response;
	}

	/**
	 * @throws ProductNotFoundException
	 * @Def: Method to Get Products based on id
	 * 
	 * @Request_Param = id
	 * @Response: Success = HttpStatus(200), ProductRequest
	 * @Response: Failure = HttpStatus(404), ErrorDetails
	 */
	@GetMapping("/product/{id}")
	public ResponseEntity<ProductRequest> getProductById(@PathVariable(value = "id") Long employeeId)
			throws ProductNotFoundException {
		log.info("STARTED: Shop Product Maintainance API: getProductById ");
		ProductRequest productRequest = repository.findById(employeeId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found for this id :: " + employeeId));
		log.info("COMPLETED: Shop Product Maintainance API: getProductById, Response --> {} ", productRequest);
		return ResponseEntity.ok().body(productRequest);
	}

	/**
	 * @Def: Method to create/add Product
	 * 
	 * @Request pitstopRequest
	 * @Response: Success = HttpStatus(200), ProductRequest
	 */
	@PostMapping("/product")
	public ResponseEntity<ProductRequest> createProduct(@RequestBody ProductRequest productRequest) {
		log.info("STARTED: Shop Product Maintainance API: createProduct ");
		ProductRequest request = repository.save(productRequest);
		log.info("COMPLETED: Shop Product Maintainance API: createProduct, Response --> {} ", request);
		return ResponseEntity.accepted().body(productRequest);
	}

	/**
	 * @throws ProductNotFoundException
	 * @Def: Method to Update Product Details
	 * 
	 * @Request: ProductRequest
	 * @Request_Param = id
	 * @Response: Success = HttpStatus(200), ProductRequest
	 * @Response: Failure = HttpStatus(404), ErrorDetails
	 */
	@PutMapping("/product/{id}")
	public ResponseEntity<ProductRequest> updateProductDetails(@PathVariable(value = "id") Long employeeId,
			@RequestBody ProductRequest productRequest) throws ProductNotFoundException {
		log.info("STARTED: Shop Product Maintainance API: updateProductDetails ");
		ProductRequest existingProduct = repository.findById(employeeId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found for this id :: " + employeeId));

		existingProduct.setProductName(productRequest.getProductName());
		existingProduct.setProductDesc(productRequest.getProductDesc());
		existingProduct.setProductCategory(productRequest.getProductCategory());
		existingProduct.setProductPrice(productRequest.getProductPrice());
		existingProduct.setProductPriceCurrency(productRequest.getProductPriceCurrency());

		final ProductRequest updatedProduct = repository.save(existingProduct);
		log.info("COMPLETED: Shop Product Maintainance API: updateProductDetails, Response --> {} ", updatedProduct);
		return ResponseEntity.ok(updatedProduct);
	}

	/**
	 * @throws ProductNotFoundException
	 * @Def: Method to delete product
	 * 
	 * @Request_Param = id
	 * @Response: Success = HttpStatus(200), ProductRequest
	 * @Response: Failure = HttpStatus(404), ErrorDetails
	 */
	@DeleteMapping("/product/{id}")
	public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Long employeeId)
			throws ProductNotFoundException {
		log.info("STARTED: Shop Product Maintainance API: deleteProduct ");
		ProductRequest employee = repository.findById(employeeId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found for this id :: " + employeeId));
		repository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		log.info("COMPLETED: Shop Product Maintainance API: deleteProduct, Response --> {} ", response);
		return response;
	}
}
