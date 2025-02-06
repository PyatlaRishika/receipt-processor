package com.fetch.receipt.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fetch.receipt.model.ReceiptModel;
import com.fetch.receipt.service.ReceiptService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
	
	@Autowired
	public ReceiptService receiptService;
	
	@GetMapping("{id}/points")
	public ResponseEntity<Map<String,Integer>> getPoints(@PathVariable String id) {
		Integer points = receiptService.getPoints(id);
		
		if(points==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Map<String,Integer> getResponse = new HashMap<>();
		getResponse.put("points", points);
		return new ResponseEntity<>(getResponse,HttpStatus.OK);
	}
	
	@PostMapping("/process")
	public ResponseEntity<Map<String,String>> processReceipts(@RequestBody @Valid ReceiptModel receiptModel){
		 if (receiptModel == null || receiptModel.getRetailer() == null || receiptModel.getTotal() == null || receiptModel.getItems() == null || receiptModel.getItems().isEmpty()) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        }
		String id = receiptService.processReceipt(receiptModel);
		
		Map<String,String> postResponse = new HashMap<>();
		postResponse.put("id", id);
		return new ResponseEntity<>(postResponse,HttpStatus.OK);
		
	}

}
