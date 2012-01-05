package com.test.codingdojo.katas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RomanNumeralsKata {

	private static Map<Integer, String> DECIMAL_ROMAN_MAP = new HashMap<Integer, String>();
	static {
		DECIMAL_ROMAN_MAP.put(1, "I");
		DECIMAL_ROMAN_MAP.put(4, "IV");
		DECIMAL_ROMAN_MAP.put(5, "V");
		DECIMAL_ROMAN_MAP.put(9, "IX");
		DECIMAL_ROMAN_MAP.put(10, "X");
		DECIMAL_ROMAN_MAP.put(40, "XL");
		DECIMAL_ROMAN_MAP.put(50, "L");
		DECIMAL_ROMAN_MAP.put(90, "XC");
		DECIMAL_ROMAN_MAP.put(100, "C");
		DECIMAL_ROMAN_MAP.put(400, "CD");
		DECIMAL_ROMAN_MAP.put(500, "D");
		DECIMAL_ROMAN_MAP.put(900, "CM");
		DECIMAL_ROMAN_MAP.put(1000, "M");
	}

	private static Map<String, Integer> ROMAN_DECIMAL_MAP = new HashMap<String, Integer>();
	static {
		ROMAN_DECIMAL_MAP.put("I", 1);
		ROMAN_DECIMAL_MAP.put("IV", 4);
		ROMAN_DECIMAL_MAP.put("V", 5);
		ROMAN_DECIMAL_MAP.put("IX", 9);
		ROMAN_DECIMAL_MAP.put("X", 10);
		ROMAN_DECIMAL_MAP.put("XL", 40);
		ROMAN_DECIMAL_MAP.put("L", 50);
		ROMAN_DECIMAL_MAP.put("XC", 90);
		ROMAN_DECIMAL_MAP.put("C", 100);
		ROMAN_DECIMAL_MAP.put("CD", 400);
		ROMAN_DECIMAL_MAP.put("D", 500);
		ROMAN_DECIMAL_MAP.put("CM", 900);
		ROMAN_DECIMAL_MAP.put("M", 1000);
	}

	private static List<Integer> SORTED_ROMAN_DIVISORS = Arrays.asList(1, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000);

	public String toRoman(Integer num) {
		return createRomanNumberString(findRomanDivisorsCountMap(num, findMaxRomanDivisorIndex(num)));
	}

	public int toDecimal(String romanNumber) {
		int result = 0;
		int currentIndex = 0;
		while (currentIndex < romanNumber.length()) {
			int numberLength = 1;
			int offset = currentIndex;
			if (currentIndex <= (romanNumber.length() - 2) && ROMAN_DECIMAL_MAP.containsKey(romanNumber.substring(currentIndex, currentIndex + 2))) {
				numberLength++;
				currentIndex++;
			}
			result += ROMAN_DECIMAL_MAP.get(romanNumber.substring(offset, offset + numberLength));
			currentIndex++;
		}
		return result;
	}

	private String createRomanNumberString(Map<Integer, Integer> romanDivisorsCountMap) {
		List<Integer> romanDivisors = new ArrayList<Integer>(romanDivisorsCountMap.keySet());
		Collections.sort(romanDivisors);
		Collections.reverse(romanDivisors);
		StringBuilder result = new StringBuilder();
		for (Integer romanDivisor : romanDivisors) {
			result.append(buildRomanNumber(romanDivisor, romanDivisorsCountMap.get(romanDivisor)));
		}
		return result.toString();
	}

	private String buildRomanNumber(Integer romanNumber, Integer counter) {
		if (counter.equals(4)) {
			Integer nextRomanNumber = SORTED_ROMAN_DIVISORS.get(SORTED_ROMAN_DIVISORS.indexOf(romanNumber) + 1);
			return DECIMAL_ROMAN_MAP.get(romanNumber) + DECIMAL_ROMAN_MAP.get(nextRomanNumber);
		}
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < counter; j++) {
			sb.append(DECIMAL_ROMAN_MAP.get(romanNumber));
		}
		return sb.toString();
	}

	private Map<Integer, Integer> findRomanDivisorsCountMap(Integer num, int beginIndex) {
		int sum = 0;
		int module = num;
		Map<Integer, Integer> resultMap = new HashMap<Integer, Integer>();
		while (!num.equals(sum)) {
			int romanNumber = SORTED_ROMAN_DIVISORS.get(beginIndex);
			int counter = module / romanNumber;
			module = module % romanNumber;
			sum += (counter * romanNumber);
			resultMap.put(romanNumber, counter);
			beginIndex--;
		}
		return resultMap;
	}

	private int findMaxRomanDivisorIndex(Integer num) {
		for (int i = 0; i < SORTED_ROMAN_DIVISORS.size(); i++) {
			if (SORTED_ROMAN_DIVISORS.get(i) > num) {
				return i - 1;
			}
		}
		return SORTED_ROMAN_DIVISORS.size() - 1;
	}

}
