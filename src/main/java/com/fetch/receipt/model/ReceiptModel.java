package com.fetch.receipt.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Component
public class ReceiptModel {
	
	@NotNull
    @Pattern(regexp = "^[\\w\\s\\-&]+$", message = "Retailer name must contain only allowed characters")
    private String retailer;
	
	 @NotNull
	    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	    private LocalDate purchaseDate;
	    
	    @NotNull
	    @DateTimeFormat(pattern = "HH:mm")
	    private LocalTime purchaseTime;
    
    @NotNull
    @Valid
    @Size(min = 1, message = "There must be at least one item in the receipt")
    private List<ReceiptItem> items;
    
    @Pattern(regexp = "^(0|[1-9]\\d*)\\.\\d{2}$", message = "Total must be a valid decimal with exactly two decimal places (e.g., 6.49)")
    private String total;

   // private String total;
    
    
	public String getRetailer() {
		return retailer;
	}

	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public LocalTime getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(LocalTime purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public List<ReceiptItem> getItems() {
		return items;
	}

	public void setItems(List<ReceiptItem> items) {
		this.items = items;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	



}
