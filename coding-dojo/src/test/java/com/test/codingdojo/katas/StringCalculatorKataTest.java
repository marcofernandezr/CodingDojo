package com.test.codingdojo.katas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringCalculatorKataTest {

	private StringCalculatorKata calculator = new StringCalculatorKata();

	@Test
	public void shouldSumNumbersInString() throws Exception {
		assertEquals(6, calculator.sum("1,2,3"));
	}

	@Test
	public void shouldSumNumbersInStringWithMultipleLines() throws Exception {
		assertEquals(21, calculator.sum("1,2,3\n4,5\n6"));
	}

	@Test
	public void shouldSumNumbersInStringWithMultipleLinesAndSpecificSeparator() throws Exception {
		assertEquals(21, calculator.sum("\\;\n1;2;3\n4;5\n6"));
	}

}
