package com.test.codingdojo.katas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BankOCRKata {

	private static final int DIGIT_LENGTH = 3;

	public List<String> read(String input) throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(input));
		String line = null;
		List<List<String>> numbers = new ArrayList<List<String>>();
		List<String> results = new ArrayList<String>();
		int row = 0;
		while ((line = reader.readLine()) != null) {
			if (row % DIGIT_LENGTH == 0) {
				if (row > 0) {
					StringBuilder result = new StringBuilder();
					for (List<String> digit : numbers) {
						result.append(readDigit(digit));
					}
					results.add(result.toString());
					numbers.clear();
				}
				for (int i = 0; i < (line.length() / DIGIT_LENGTH); i++) {
					numbers.add(new ArrayList<String>());
				}
			}
			String[] split = line.split("(?<=\\G.{3})");
			for (int i = 0; i < (line.length() / DIGIT_LENGTH); i++) {
				numbers.get(i).add(split[i]);
			}
			row++;
		}
		StringBuilder result = new StringBuilder();
		for (List<String> digit : numbers) {
			result.append(readDigit(digit));
		}
		results.add(result.toString());

		return results;
	}

	private String readDigit(List<String> digit) {
		for (Digit validDigit : Digit.values()) {
			if (validDigit.getLines().equals(digit)) {
				return "" + validDigit.ordinal();
			}
		}
		return "?";
	}

	public static enum Digit {

		ZERO(" _ ", "| |", "|_|"), ONE("   ", "  |", "  |"), TWO(" _ ", " _|", "|_ "), THREE(" _ ", " _|", " _|"), FOUR(
				"   ", "|_|", "  |"), FIVE(" _ ", "|_ ", " _|"), SIX(" _ ", "|_ ", "|_|"), SEVEN(" _ ", "  |", "  |"), EIGHT(
				" _ ", "|_|", "|_|"), NINE(" _ ", "|_|", " _|");

		private final List<String> lines;

		Digit(String... lines) {
			this.lines = Collections.unmodifiableList(Arrays.asList(lines));
		}

		public List<String> getLines() {
			return lines;
		}
	}

}
