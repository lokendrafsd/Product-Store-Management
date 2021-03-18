package com.shopapplication.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@NoArgsConstructor
public class ProductRequest {
	private long id;
	private String productName;
	private String productDesc;
	private String productCategory;
	private double productPrice;
	private int productQuantity;
	private String productPriceCurrency;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "product_name", nullable = false)
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "product_discription", columnDefinition = "LONGTEXT")
	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	@Column(name = "product_category")
	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	@Column(name = "product_quantity", nullable = false)
	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	@Column(name = "product_price", nullable = false)
	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	@Column(name = "product_price_currency", nullable = false)
	public String getProductPriceCurrency() {
		return productPriceCurrency;
	}

	public void setProductPriceCurrency(String productPriceCurrency) {
		this.productPriceCurrency = productPriceCurrency;
	}

	@Override
	public String toString() {
		return "ProductRequest [id=" + id + ", productName=" + productName + ", productDesc=" + productDesc
				+ ", productCategory=" + productCategory + ", productPrice=" + productPrice + ", productQuantity="
				+ productQuantity + ", productPriceCurrency=" + productPriceCurrency + "]";
	}

}
