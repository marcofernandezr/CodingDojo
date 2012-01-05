package com.test.codingdojo.katas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class StringCalculatorKata {

	public int sum(String input) throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(input));
		String separator = ",";
		if (input.startsWith("\\")) {
			String firstLine = reader.readLine().trim();
			separator = firstLine.substring(1, firstLine.length());
		}
		return sum(reader, separator);
	}

	private int sum(BufferedReader reader, String separator) throws IOException {
		int result = 0;
		String line = null;
		while ((line = reader.readLine()) != null) {
			result += sumLine(line, separator);
		}
		return result;
	}

	private int sumLine(String line, String separator) {
		int sum = 0;
		for (String number : line.split(separator)) {
			if (!number.trim().isEmpty()) {
				sum += new Integer(number);
			}
		}
		return sum;
	}

}
