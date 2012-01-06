package com.test.codingdojo.katas;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class BankOCRKataTest {

	@Test
	public void shouldSolveKataBankOCR() throws Exception {
		String input = "    _  _     _  _  _  _  _ " + "\n" + "  | _| _||_||_ |_   ||_||_|" + "\n"
				+ "  ||_  _|  | _||_|  ||_| _|" + "\n" + "    _  _  _  _  _  _     _ " + "\n" + "|_||_|| || ||_   |  |  | _ "
				+ "\n" + "  | _||_||_||_|  |  |  | _|";
		BankOCRKata kata = new BankOCRKata();
		assertEquals(Arrays.asList("123456789", "49006771?"), kata.read(input));
	}

}
