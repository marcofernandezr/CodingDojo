package com.test.codingdojo.katas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PotterKata {

	private static final int BOOK_PRICE = 8;
	private static List<Double> DISCOUNTS = Arrays.asList(1.0, 0.95, .9, .8, .75);

	public Double sum(Integer... books) {
		List<List<Integer>> bookPackages = createPackages(books);
		Double price = calculatePrice(bookPackages);
		Double alternativePrice = calculateAlternativePrice(new ArrayList<List<Integer>>(bookPackages));
		return price <= alternativePrice ? price : alternativePrice;
	}

	private List<List<Integer>> createPackages(Integer... books) {
		List<List<Integer>> bookPackages = new ArrayList<List<Integer>>();
		boolean newPackage = true;
		for (Integer book : books) {
			for (List<Integer> bookPackage : bookPackages) {
				if (bookPackage.contains(book)) {
					newPackage = true;
					continue;
				} else {
					bookPackage.add(book);
					newPackage = false;
					break;
				}
			}
			if (newPackage) {
				ArrayList<Integer> bookPackage = new ArrayList<Integer>();
				bookPackage.add(book);
				bookPackages.add(bookPackage);
			}
		}
		return bookPackages;
	}

	private Double calculateAlternativePrice(List<List<Integer>> bookPackages) {
		int currentIndex = 0;
		while (currentIndex < bookPackages.size() - 1) {
			List<Integer> currentPackage = bookPackages.get(currentIndex);
			List<Integer> nextPackage = bookPackages.get(currentIndex + 1);
			if (currentPackage.size() > nextPackage.size()) {
				Iterator<Integer> iterator = currentPackage.iterator();
				while (iterator.hasNext()) {
					Integer book = iterator.next();
					if (!nextPackage.contains(book)) {
						iterator.remove();
						nextPackage.add(book);
						if (currentPackage.size() <= nextPackage.size()) {
							break;
						}
					}
				}
			}
			currentIndex++;
		}
		return calculatePrice(bookPackages);

	}

	private Double calculatePrice(List<List<Integer>> bookPackages) {
		Double price = 0.0;
		for (List<Integer> bookPackage : bookPackages) {
			price += BOOK_PRICE * bookPackage.size() * DISCOUNTS.get(bookPackage.size() - 1);
		}
		return price;
	}

}
