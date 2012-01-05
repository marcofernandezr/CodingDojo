package com.test.codingdojo.katas;

import java.util.ArrayList;
import java.util.List;

public class PrimeFactorsKata {

	public List<Integer> generate(int num) {
		List<Integer> result = new ArrayList<Integer>();
		int prime = 2;
		while (num > 1) {
			if (num % prime == 0) {
				result.add(prime);
				num /= prime;
			} else {
				prime++;
			}
		}
		return result;
	}

}
