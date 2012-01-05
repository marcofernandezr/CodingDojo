package com.test.codingdojo.katas;

import static org.junit.Assert.*;

import org.junit.Test;

public class PotterKataTest {

	@Test
	public void shouldSolvePotterKata() throws Exception {
		PotterKata kataPotter = new PotterKata();
		assertEquals(new Double(0), kataPotter.sum());
		assertEquals(new Double(8), kataPotter.sum(0));
		assertEquals(new Double(8), kataPotter.sum(1));
		assertEquals(new Double(8), kataPotter.sum(2));
		assertEquals(new Double(8), kataPotter.sum(3));
		assertEquals(new Double(8), kataPotter.sum(4));
		assertEquals(new Double(8 * 2), kataPotter.sum(0, 0));
		assertEquals(new Double(8 * 3), kataPotter.sum(1, 1, 1));
		assertEquals(new Double(8 * 2 * 0.95), kataPotter.sum(0, 1));
		assertEquals(new Double(8 * 3 * 0.9), kataPotter.sum(0, 2, 4));
		assertEquals(new Double(8 * 4 * 0.8), kataPotter.sum(0, 1, 2, 4));
		assertEquals(new Double(8 * 5 * 0.75), kataPotter.sum(0, 1, 2, 3, 4));
		assertEquals(new Double(8 + (8 * 2 * 0.95)), kataPotter.sum(0, 0, 1));
		assertEquals(new Double(2 * (8 * 2 * 0.95)), kataPotter.sum(0, 0, 1, 1));
		assertEquals(new Double((8 * 4 * 0.8) + (8 * 2 * 0.95)), kataPotter.sum(0, 0, 1, 2, 2, 3));
		assertEquals(new Double(8 + (8 * 5 * 0.75)), kataPotter.sum(0, 1, 1, 2, 3, 4));
		assertEquals(new Double(2 * (8 * 4 * 0.8)), kataPotter.sum(0, 0, 1, 1, 2, 2, 3, 4));
		assertEquals(new Double(3 * (8 * 5 * 0.75) + 2 * (8 * 4 * 0.8)), kataPotter.sum(0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4));
	}

}
