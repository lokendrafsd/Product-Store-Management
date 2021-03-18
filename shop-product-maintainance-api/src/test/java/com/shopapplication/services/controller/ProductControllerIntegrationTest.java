package com.shopapplication.services.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.shopapplication.services.Application;
import com.shopapplication.services.model.ProductRequest;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	@DisplayName("Test-1: To test if all available products in H2 are received")
	void testGetAllProducts() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/product", HttpMethod.GET, entity,
				String.class);
		assertNotNull(response.getBody());
	}

	@Test
	@DisplayName("Test-2: To test if the call is suuccessful when we try to fetch a record based on id")
	void testGetProductById() {
		ProductRequest productRequest = restTemplate.getForObject(getRootUrl() + "/product/1", ProductRequest.class);
		assertNotNull(productRequest);
	}

	@Test
	@DisplayName("Test-3: To test if the call is successful when we try to create a new record")
	void testCreateProduct() {
		ProductRequest productRequest = new ProductRequest();

		ResponseEntity<ProductRequest> postResponse = restTemplate.postForEntity(getRootUrl() + "/product",
				productRequest, ProductRequest.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	@DisplayName("Test-4: To test if the call is successful when we try to update a record")
	void testUpdateProduct() {
		int id = 1;
		ProductRequest productRequest = restTemplate.getForObject(getRootUrl() + "/product/" + id,
				ProductRequest.class);

		restTemplate.put(getRootUrl() + "/product/" + id, productRequest);

		ProductRequest updatedEmployee = restTemplate.getForObject(getRootUrl() + "/product/" + id,
				ProductRequest.class);
		assertNotNull(updatedEmployee);
	}

	@Test
	@DisplayName("Test-5: To test if the call is successful when we try to delete a record")
	void testDeleteProduct() {
		int id = 2;
		ProductRequest productRequest = restTemplate.getForObject(getRootUrl() + "/product/" + id,
				ProductRequest.class);
		assertNotNull(productRequest);

		restTemplate.delete(getRootUrl() + "/product/" + id);

		try {
			productRequest = restTemplate.getForObject(getRootUrl() + "/product/" + id, ProductRequest.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}
}
