package com.test.codingdojo.katas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RomanNumeralsKataTest {

	private RomanNumeralsKata kata = new RomanNumeralsKata();

	@Test
	public void shouldSolveKataRomanNumerals() throws Exception {
		assertEquals("I", kata.toRoman(1));
		assertEquals("II", kata.toRoman(2));
		assertEquals("III", kata.toRoman(3));
		assertEquals("IV", kata.toRoman(4));
		assertEquals("V", kata.toRoman(5));
		assertEquals("VI", kata.toRoman(6));
		assertEquals("VII", kata.toRoman(7));
		assertEquals("VIII", kata.toRoman(8));
		assertEquals("IX", kata.toRoman(9));
		assertEquals("X", kata.toRoman(10));
		assertEquals("XXIV", kata.toRoman(24));
		assertEquals("XXXI", kata.toRoman(31));
		assertEquals("L", kata.toRoman(50));
		assertEquals("XL", kata.toRoman(40));
		assertEquals("CCCLXIX", kata.toRoman(369));
		assertEquals("CDXLVIII", kata.toRoman(448));
		assertEquals("MCMXCVIII", kata.toRoman(1998));
		assertEquals("MCMXCIX", kata.toRoman(1999));
		assertEquals("MMDCCLI", kata.toRoman(2751));
		assertEquals("MMMCMXCIX", kata.toRoman(3999));
	}

	@Test
	public void shouldSolveKataRomanNumeralsBSide() throws Exception {
		assertEquals(1, kata.toDecimal("I"));
		assertEquals(2, kata.toDecimal("II"));
		assertEquals(3, kata.toDecimal("III"));
		assertEquals(4, kata.toDecimal("IV"));
		assertEquals(5, kata.toDecimal("V"));
		assertEquals(6, kata.toDecimal("VI"));
		assertEquals(7, kata.toDecimal("VII"));
		assertEquals(8, kata.toDecimal("VIII"));
		assertEquals(9, kata.toDecimal("IX"));
		assertEquals(10, kata.toDecimal("X"));
		assertEquals(24, kata.toDecimal("XXIV"));
		assertEquals(31, kata.toDecimal("XXXI"));
		assertEquals(50, kata.toDecimal("L"));
		assertEquals(40, kata.toDecimal("XL"));
		assertEquals(369, kata.toDecimal("CCCLXIX"));
		assertEquals(448, kata.toDecimal("CDXLVIII"));
		assertEquals(1998, kata.toDecimal("MCMXCVIII"));
		assertEquals(1999, kata.toDecimal("MCMXCIX"));
		assertEquals(2751, kata.toDecimal("MMDCCLI"));
		assertEquals(3999, kata.toDecimal("MMMCMXCIX"));
	}

}
