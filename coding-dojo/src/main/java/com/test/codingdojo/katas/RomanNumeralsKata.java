package com.test.codingdojo.katas;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RomanNumeralsKata {

	private static List<String> ONE_DIGIT_NUMBERS = asList("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX");
	private static List<String> TWO_DIGIT_NUMBERS = asList("X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC");
	private static List<String> THREE_DIGIT_NUMBERS = asList("C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM");
	private static List<String> FOUR_DIGIT_NUMBERS = asList("M", "MM", "MMM");
	@SuppressWarnings("unchecked")
	private static List<List<String>> ROMAN_NUMERS = asList(ONE_DIGIT_NUMBERS, TWO_DIGIT_NUMBERS, THREE_DIGIT_NUMBERS,
			FOUR_DIGIT_NUMBERS);
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

	public String toRoman(Integer number) {
		String strNum = number.toString();
		StringBuilder result = new StringBuilder();
		for (int index = 0; index < strNum.length(); index++) {
			int num = new Integer("" + strNum.charAt(index)) - 1;
			if (num >= 0) {
				result.append(ROMAN_NUMERS.get(strNum.length() - (index + 1)).get(num));
			}
		}
		return result.toString();
	}

	public int toDecimal(String romanNumber) {
		int result = 0;
		int currentIndex = 0;
		while (currentIndex < romanNumber.length()) {
			int numberLength = 1;
			int offset = currentIndex;
			if (currentIndex <= (romanNumber.length() - 2)
					&& ROMAN_DECIMAL_MAP.containsKey(romanNumber.substring(currentIndex, currentIndex + 2))) {
				numberLength++;
				currentIndex++;
			}
			result += ROMAN_DECIMAL_MAP.get(romanNumber.substring(offset, offset + numberLength));
			currentIndex++;
		}
		return result;
	}
}
