package com.fetch.receipt.model;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Pattern;

@Component
public class ReceiptItem {
	@Pattern(regexp = "^[a-zA-Z0-9\\s\\-&]+$", message = "Short Description must contain only letters, numbers, spaces, hyphens, and ampersands.")
	private String shortDescription;
	@Pattern(regexp = "^(0|[1-9]\\d*)\\.\\d{2}$", message = "Price must be a valid decimal with exactly two decimal places.")
	private String price;
	
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	

	
	

	
}
