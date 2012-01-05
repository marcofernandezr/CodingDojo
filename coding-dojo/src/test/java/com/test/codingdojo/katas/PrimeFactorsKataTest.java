package com.test.codingdojo.katas;

import static org.junit.Assert.*;
import static java.util.Arrays.*;

import org.junit.Test;

public class PrimeFactorsKataTest {

	@Test
	public void shouldSolvePrimeFactorsKata() throws Exception {
		PrimeFactorsKata kata = new PrimeFactorsKata();
		assertEquals(asList(2), kata.generate(2));
		assertEquals(asList(3), kata.generate(3));
		assertEquals(asList(2, 2), kata.generate(4));
		assertEquals(asList(5), kata.generate(5));
		assertEquals(asList(2, 3), kata.generate(6));
		assertEquals(asList(7), kata.generate(7));
		assertEquals(asList(2, 2, 2), kata.generate(8));
		assertEquals(asList(3, 3), kata.generate(9));
		assertEquals(asList(2, 5), kata.generate(10));
		assertEquals(asList(37799), kata.generate(37799));
		assertEquals(asList(3, 37799), kata.generate(113397));
	}
}