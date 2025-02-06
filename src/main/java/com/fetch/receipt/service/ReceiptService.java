package com.fetch.receipt.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fetch.receipt.model.ReceiptItem;
import com.fetch.receipt.model.ReceiptModel;

@Service
public class ReceiptService {

	private Map<String, Integer> receiptPoints = new HashMap<>();

	public int getPoints(String id) {

		if (!receiptPoints.containsKey(id)) {
			throw new IllegalArgumentException("Invalid receipt Id: " + id);
		}

		return receiptPoints.get(id);
	}

	public String processReceipt(ReceiptModel receiptModel) {
		if (receiptModel == null) {
			throw new IllegalArgumentException("Receipt cannot be null");
		}
		try {
			int calculatedPoints = calculateReceiptPoints(receiptModel);
			String receiptId = UUID.randomUUID().toString();
			receiptPoints.put(receiptId, calculatedPoints);

			return receiptId;
		} catch (Exception e) {
			throw new RuntimeException("Error processing receipt: " + e.getMessage(), e);
		}
	}

	private int calculateReceiptPoints(ReceiptModel receipt) {
		try {
			int points = 0;

			// rule1

			points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();
			
			String totalinStr = receipt.getTotal();
			
			try {
			    BigDecimal total = new BigDecimal(totalinStr);

			    // Rule 1: 50 points if the total is a round dollar amount (no cents)
			    if (total.scale() == 0 || total.stripTrailingZeros().scale() == 0) {
			        points += 50;
			    }

			    // Rule 2: 25 points if the total is a multiple of 0.25
			    if (total.remainder(new BigDecimal("0.25")).compareTo(BigDecimal.ZERO) == 0) {
			        points += 25;
			    }

			} catch (NumberFormatException e) {
			    System.out.println("Invalid total value: " + totalinStr);
			}
			// rule2
			

			// rule4
			int listCount = receipt.getItems().size() / 2;
			points += listCount * 5;

			// rule5
			points += calculateTrim(receipt.getItems());

			// rule6
			if (receipt.getPurchaseDate().getDayOfMonth() % 2 == 1) {
				points += 6;
			}

			// rule7
			int purchasehour = receipt.getPurchaseTime().getHour();
			if (purchasehour >= 14 && purchasehour < 16) {
				points += 10;
			}

			return points;
		} catch (Exception e) {
			throw new RuntimeException("Error calculating receipt: " + e.getMessage(), e);
		}
	}

	private int calculateTrim(List<ReceiptItem> itemList) {
		int totalItemPrice = 0;
		for (var item : itemList) {
			String description = item.getShortDescription().trim();

			if (description.length() % 3 == 0) {
				BigDecimal price = new BigDecimal(item.getPrice());

				BigDecimal multiplyPrice = price.multiply(new BigDecimal("0.2"));

				double roundedValue = Math.ceil(multiplyPrice.doubleValue());

				BigDecimal roundedPrice = new BigDecimal(roundedValue);

				int pointsForEachItem = roundedPrice.intValue();

				totalItemPrice += pointsForEachItem;

			}

		}
		return totalItemPrice;

	}

}
