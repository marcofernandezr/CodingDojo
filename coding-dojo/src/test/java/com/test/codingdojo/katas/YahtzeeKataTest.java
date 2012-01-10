package com.test.codingdojo.katas;

import static org.junit.Assert.*;
import static java.util.Arrays.*;
import org.junit.Test;

import com.test.codingdojo.katas.YahtzeeKata.Category;

public class YahtzeeKataTest {

	@Test
	public void shouldSolveYahtzeeKata() throws Exception {
		YahtzeeKata kata = new YahtzeeKata();
		assertEquals(8, kata.evaluate(asList(1, 1, 2, 4, 4), Category.FOURS));
		assertEquals(18, kata.evaluate(asList(5, 4, 3, 3, 3), Category.TRIP));
		assertEquals(13, kata.evaluate(asList(2, 2, 2, 2, 5), Category.QUAD));
		assertEquals(30, kata.evaluate(asList(1, 2, 3, 4, 5), Category.SMALL_STRAIGHT));
		assertEquals(40, kata.evaluate(asList(6, 2, 3, 4, 5), Category.LARGE_STRAIGHT));
		assertEquals(25, kata.evaluate(asList(1, 1, 2, 2, 2), Category.FULL_HOUSE));
		assertEquals(50, kata.evaluate(asList(2, 2, 2, 2, 2), Category.YAHTZEE));
		assertEquals(17, kata.evaluate(asList(1, 2, 3, 5, 6), Category.CHANCE));
	}
}
