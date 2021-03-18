package com.shopapplication.services.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.shopapplication.services.exceptions.ProductNotFoundException;
import com.shopapplication.services.model.ProductRequest;
import com.shopapplication.services.repository.ProductRepository;

@SpringBootTest
class ProductControllerTest {

	@InjectMocks
	ProductController controller;

	@Mock
	ProductRepository repository;

	@BeforeAll
	public static void init() {
	}

	@Test
	@DisplayName("Test-1: To test if exception is thrown when we try to fetch all records but no records present")
	void _01_getAllProductsTest() {
		Throwable exception = Assertions.assertThrows(ProductNotFoundException.class,
				() -> controller.getAllProducts());
		Assertions.assertEquals("No Products Found", exception.getMessage());
	}

	@Test
	@DisplayName("Test-2: To test if all available products in H2 are received")
	void _02_getAllProductsTest() throws ProductNotFoundException {
		List<ProductRequest> mockResponse = new ArrayList<>();
		mockResponse.add(getProductRequest());
		when(repository.findAll()).thenReturn(mockResponse);
		List<ProductRequest> response = controller.getAllProducts();
		Assertions.assertEquals(mockResponse, response);
	}

	@Test
	@DisplayName("Test-3: To test if exception is thrown when we try to fetch a record based on id but record is not present")
	void _03_getProductByIdTest() {
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		Throwable exception = Assertions.assertThrows(ProductNotFoundException.class,
				() -> controller.getProductById(1L));
		Assertions.assertEquals("Product not found for this id :: 1", exception.getMessage());
	}

	@Test
	@DisplayName("Test-4: To test if the call is suuccessful when we try to fetch a record based on id")
	void _04_getProductByIdTest() throws ProductNotFoundException {
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(getProductRequest()));

		ResponseEntity<ProductRequest> expectedResponse = controller.getProductById(1L);
		Assertions.assertEquals(HttpStatus.OK, expectedResponse.getStatusCode());
	}

	@Test
	@DisplayName("Test-5: To test if the call is successful when we try to create a new record")
	void _05_createProductTest() {
		when(repository.save(Mockito.any(ProductRequest.class))).thenReturn(getProductRequest());

		ResponseEntity<ProductRequest> response = controller.createProduct(getProductRequest());
		Assertions.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}

	@Test
	@DisplayName("Test-6: To test if the call is successful when we try to update a record")
	void _06_updateProductDetailsTest() throws ProductNotFoundException {
		when(repository.save(Mockito.any(ProductRequest.class))).thenReturn(getProductRequest());
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(getProductRequest()));

		ResponseEntity<ProductRequest> response = controller.updateProductDetails(1L, getProductRequest());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@DisplayName("Test-7: To test if exception is thrown when we try to update a record based on id but record is not present")
	void _07_updateProductDetailsTest() {
		when(repository.save(Mockito.any(ProductRequest.class))).thenReturn(getProductRequest());
		Throwable exception = Assertions.assertThrows(ProductNotFoundException.class,
				() -> controller.updateProductDetails(1L, getProductRequest()));
		Assertions.assertEquals("Product not found for this id :: 1", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test-8: To test if the call is successful when we try to delete a record")
	void _08_deleteProductTest() throws ProductNotFoundException {
		doNothing().when(repository).delete(Mockito.any());
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(getProductRequest()));
		Map<String, Boolean> response = controller.deleteProduct(1L);
	}

	@Test
	@DisplayName("Test-9: To test if exception is thrown when we try to delete a record based on id but record is not present")
	void _09_deleteProductTest() {
		when(repository.save(Mockito.any(ProductRequest.class))).thenReturn(getProductRequest());
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		Throwable exception = Assertions.assertThrows(ProductNotFoundException.class,
				() -> controller.deleteProduct(1L));
		Assertions.assertEquals("Product not found for this id :: 1", exception.getMessage());
	}

	/**
	 * @Def: Method to get the mocked productRequest
	 * @return mockedProductRequest
	 */
	private ProductRequest getProductRequest() {
		ProductRequest request = new ProductRequest();
		request.setProductCategory("FOOD GRAINS");
		request.setProductDesc("Figs are bell-shaped fruits with wrinkled and tough skin. "
				+ "They are one of the sweetest fruits and also have a very sugary scent. "
				+ "They are rich in antioxidants, phytonutrients and vitamins. "
				+ "Dried figs, indeed, are an extremely concentrated supply of minerals and vitamins. "
				+ "Dried figs are mainly consumed raw and are used as sweeteners in desserts and baked goods.");
		request.setProductName("Anjeer/Figs/Atti Hannu");
		request.setProductPrice(300);
		request.setProductQuantity(1);
		request.setProductPriceCurrency("INR");
		return request;
	}
}
