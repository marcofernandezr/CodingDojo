package com.test.codingdojo.katas;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class TexasHoldEmKataTest {

	private TexasHoldEmKata kata = new TexasHoldEmKata();

	@Test
	public void shouldRankAndCompareTexasHoldEmHands() {

		List<String> foldedHandA = asList("9h", "5s");
		List<String> foldedHandB = asList("Ac", "Qc", "Ks", "Kd", "9d");
		List<String> foldedHandC = asList("7s", "Ts", "Ks", "Kd", "9d");
		List<String> fullHouseHand = asList("Kc", "9s", "Ks", "Kd", "9d", "3c", "6d");
		List<String> flushHand = asList("4d", "2d", "Ks", "Kd", "9d", "3c", "6d");
		List<String> twoPairHand = asList("9c", "Ah", "Ks", "Kd", "9d", "3c", "6d");

		@SuppressWarnings("unchecked")
		TexasHoldEmMatchResult result = kata.evaluateHands(foldedHandA, fullHouseHand, flushHand, foldedHandB, foldedHandC, twoPairHand);

		List<TexasHoldEmHand> winnerHands = result.getWinnerHands();
		assertEquals(1, winnerHands.size());
		TexasHoldEmHand winnerHand = winnerHands.get(0);
		assertEquals(TexasHoldEmType.FULL_HOUSE, winnerHand.getType());
		List<TexasHoldEmHand> sortedHands = result.getSortedHands();
		assertEquals(6, sortedHands.size());
		assertEquals(winnerHand, sortedHands.get(0));
		TexasHoldEmHand actualFlushHand = sortedHands.get(1);
		assertEquals(TexasHoldEmType.FLUSH, actualFlushHand.getType());
		TexasHoldEmHand actualTwoPairHand = sortedHands.get(2);
		assertEquals(TexasHoldEmType.TWO_PAIRS, actualTwoPairHand.getType());
		assertTrue(sortedHands.get(3).isFolded());
		assertTrue(sortedHands.get(4).isFolded());
		assertTrue(sortedHands.get(5).isFolded());
	}

	@Test
	public void shouldCreateRoyalFlushHand() throws Exception {
		TexasHoldEmHand hand = kata.createHand(asList("2h", "3h", "Th", "Qh", "Ah", "Jh", "Kh"));
		assertNotNull(hand);
		assertEquals(TexasHoldEmType.ROYAL_FLUSH, hand.getType());
		assertTrue(hand.getKickers().isEmpty());

		hand = kata.createHand(asList("Ah", "Qh", "Th", "Qd", "Jh", "Kh", "3s"));
		assertEquals(TexasHoldEmType.ROYAL_FLUSH, hand.getType());
		assertTrue(hand.getKickers().isEmpty());
	}

	@Test
	public void shouldCreateStraightFlushHand() throws Exception {
		TexasHoldEmHand hand = kata.createHand(asList("9c", "8c", "7c", "6c", "5c", "4h", "3s"));
		assertNotNull(hand);
		assertEquals(TexasHoldEmType.STRAIGHT_FLUSH, hand.getType());
		assertEquals(asList(new PokerCard("5c")), hand.getKickers());

		hand = kata.createHand(asList("9c", "8c", "7c", "6c", "5c", "Tc", "Jc"));
		assertNotNull(hand);
		assertEquals(TexasHoldEmType.STRAIGHT_FLUSH, hand.getType());
		assertEquals(asList(new PokerCard("7c")), hand.getKickers());

	}

	@Test
	public void shouldCreateAFourOfAKindHand() throws Exception {
		TexasHoldEmHand hand = kata.createHand(asList("Kh", "Kc", "8h", "Kd", "Ks", "2h", "As"));
		assertNotNull(hand);
		assertEquals(TexasHoldEmType.QUAD, hand.getType());
		assertEquals(asList(new PokerCard("Kh"), new PokerCard("As")), hand.getKickers());
	}

	@Test
	public void shouldCreateFullHouseHand() throws Exception {
		TexasHoldEmHand hand = kata.createHand(asList("Kh", "Kc", "Ah", "Kd", "Ts", "Th", "As"));
		assertNotNull(hand);
		assertEquals(TexasHoldEmType.FULL_HOUSE, hand.getType());
		assertEquals(asList(new PokerCard("Kh"), new PokerCard("Ah")), hand.getKickers());
	}

	@Test
	public void shouldCreateFlushHand() throws Exception {
		TexasHoldEmHand hand = kata.createHand(asList("Ah", "2h", "5h", "Kh", "Jh", "Th", "As"));
		assertNotNull(hand);
		assertEquals(TexasHoldEmType.FLUSH, hand.getType());
		assertEquals(asList(new PokerCard("Ah")), hand.getKickers());
	}

	@Test
	public void shouldCreateStraightHand() throws Exception {
		TexasHoldEmHand hand = kata.createHand(asList("Ah", "2h", "3s", "Kc", "Jh", "Qc", "Ts"));
		assertNotNull(hand);
		assertEquals(TexasHoldEmType.STRAIGHT, hand.getType());
		assertEquals(asList(new PokerCard("Qc")), hand.getKickers());
	}

	@Test
	public void shouldCreateAThreeOfAKindHand() throws Exception {
		TexasHoldEmHand hand = kata.createHand(asList("Th", "2h", "Ts", "Kc", "Tc", "Qc", "3s"));
		assertNotNull(hand);
		assertEquals(TexasHoldEmType.TRIP, hand.getType());
		assertEquals(asList(new PokerCard("Th"), new PokerCard("Kc"), new PokerCard("Qc")), hand.getKickers());
	}

	@Test
	public void shouldCreateATwoPairHand() throws Exception {
		TexasHoldEmHand hand = kata.createHand(asList("Th", "2h", "Ts", "Qd", "Jc", "Qc", "3s"));
		assertNotNull(hand);
		assertEquals(TexasHoldEmType.TWO_PAIRS, hand.getType());
		assertEquals(asList(new PokerCard("Qd"), new PokerCard("Th"), new PokerCard("Jc")), hand.getKickers());
	}

	@Test
	public void shouldCreateAOnePairHand() throws Exception {
		TexasHoldEmHand hand = kata.createHand(asList("Th", "2h", "As", "Qd", "Jc", "Qc", "3s"));
		assertNotNull(hand);
		assertEquals(TexasHoldEmType.ONE_PAIR, hand.getType());
		assertEquals(asList(new PokerCard("Qd"), new PokerCard("As"), new PokerCard("Jc"), new PokerCard("Th")), hand.getKickers());
	}

	@Test
	public void shouldCreateAHighCardHand() throws Exception {
		TexasHoldEmHand hand = kata.createHand(asList("Th", "2h", "As", "Qd", "Jc", "7c", "3s"));
		assertNotNull(hand);
		assertEquals(TexasHoldEmType.HIGH_CARD, hand.getType());
		assertEquals(hand.getUsedCards(), hand.getKickers());
	}

	@Test
	public void shouldCompareTexasHoldEmHands() throws Exception {
		TexasHoldEmHand royalFlushA = kata.createHand(asList("Ah", "Qh", "Th", "Qd", "Jh", "Kh", "3s"));
		TexasHoldEmHand royalFlushB = kata.createHand(asList("Ac", "Qc", "Tc", "Qd", "Jc", "Kc", "3s"));
		TexasHoldEmHand maxStraightFlushHand = kata.createHand(asList("Kc", "Ac", "2c", "3c", "4c", "4h", "3s"));
		TexasHoldEmHand someStraightFlushHand = kata.createHand(asList("9c", "8c", "7c", "6c", "5c", "4h", "3s"));
		TexasHoldEmHand sameStraightFlushHand = kata.createHand(asList("9d", "8d", "7d", "6d", "5d", "4h", "3s"));
		TexasHoldEmHand minStraightFlushHand = kata.createHand(asList("2c", "3c", "4c", "Ac", "5c", "4h", "3s"));
		TexasHoldEmHand maxFourOfAKindHand = kata.createHand(asList("Ah", "Ac", "8h", "Ad", "As", "2h", "Ks"));
		TexasHoldEmHand someFourOfAKindHand = kata.createHand(asList("3h", "8c", "8h", "8d", "8s", "2h", "5s"));
		TexasHoldEmHand sameFourOfAKindHand = kata.createHand(asList("3h", "8c", "8h", "8d", "8s", "2h", "5c"));
		TexasHoldEmHand lessFourOfAKindHand = kata.createHand(asList("3h", "8c", "8h", "8d", "8s", "2h", "4c"));
		TexasHoldEmHand minFourOfAKindHand = kata.createHand(asList("3h", "2c", "2h", "2d", "2s", "3c", "3s"));
		TexasHoldEmHand maxFullHouseHand = kata.createHand(asList("Ah", "Ac", "Kh", "Ad", "8s", "2h", "Ks"));
		TexasHoldEmHand someFullHouseHand = kata.createHand(asList("8h", "8c", "Kh", "8d", "2s", "2h", "Ks"));
		TexasHoldEmHand sameFullHouseHand = kata.createHand(asList("8h", "8s", "Kc", "8d", "2s", "2h", "Kd"));
		TexasHoldEmHand lessFullHouseHand = kata.createHand(asList("8h", "8c", "7h", "2d", "8s", "2h", "7s"));
		TexasHoldEmHand minFullHouseHand = kata.createHand(asList("2h", "2c", "Kh", "2d", "8s", "3h", "3s"));
		TexasHoldEmHand maxFlushHand = kata.createHand(asList("2h", "2c", "Kh", "Ah", "8h", "3h", "3s"));
		TexasHoldEmHand someFlushHand = kata.createHand(asList("2h", "2c", "Kc", "7c", "8c", "3c", "3s"));
		TexasHoldEmHand sameFlushHand = kata.createHand(asList("2h", "2c", "Kh", "7h", "8s", "3h", "4h"));
		TexasHoldEmHand minFlushHand = kata.createHand(asList("2h", "3h", "4h", "7h", "8s", "6h", "3s"));
		TexasHoldEmHand maxStraightHand = kata.createHand(asList("Ac", "Qh", "Kd", "5h", "Ts", "6h", "Js"));
		TexasHoldEmHand someStraightHand = kata.createHand(asList("2c", "3h", "4d", "5h", "8s", "6h", "3s"));
		TexasHoldEmHand sameStraightHand = kata.createHand(asList("2s", "3d", "4c", "5h", "8s", "6h", "3s"));
		TexasHoldEmHand minStraightHand = kata.createHand(asList("2h", "3s", "4h", "Tc", "5s", "8h", "As"));
		TexasHoldEmHand maxTripHand = kata.createHand(asList("Ah", "Ks", "Ah", "Qc", "5s", "8h", "As"));
		TexasHoldEmHand someTripHand = kata.createHand(asList("7h", "Ts", "7h", "Qc", "5s", "8h", "7s"));
		TexasHoldEmHand sameTripHand = kata.createHand(asList("7d", "Td", "7h", "Qc", "5s", "8h", "7s"));
		TexasHoldEmHand lessTripHand = kata.createHand(asList("7d", "Td", "7h", "2c", "5s", "8h", "7s"));
		TexasHoldEmHand lesserTripHand = kata.createHand(asList("7d", "Td", "7h", "2c", "5s", "4h", "7s"));
		TexasHoldEmHand minTripHand = kata.createHand(asList("2h", "2s", "2h", "3c", "7s", "5h", "6s"));
		TexasHoldEmHand maxTwoPairHand = kata.createHand(asList("Ah", "As", "Kh", "Kc", "Qs", "5h", "6s"));
		TexasHoldEmHand someTwoPairHand = kata.createHand(asList("7h", "7s", "5h", "5c", "2s", "3h", "6s"));
		TexasHoldEmHand sameTwoPairHand = kata.createHand(asList("7d", "7c", "5s", "3c", "2s", "5h", "6s"));
		TexasHoldEmHand lessTwoPairHand = kata.createHand(asList("7h", "7s", "5h", "5c", "2s", "3h", "4s"));
		TexasHoldEmHand minTwoPairHand = kata.createHand(asList("2h", "2s", "3h", "3c", "7s", "5h", "6s"));
		TexasHoldEmHand maxOnePairHand = kata.createHand(asList("Ah", "As", "Kh", "Qc", "Js", "5h", "6s"));
		TexasHoldEmHand someOnePairHand = kata.createHand(asList("Th", "Ts", "Kh", "8c", "7s", "5h", "2s"));
		TexasHoldEmHand sameOnePairHand = kata.createHand(asList("Td", "Tc", "Kh", "8s", "7c", "5h", "3s"));
		TexasHoldEmHand lessOnePairHand = kata.createHand(asList("Td", "Tc", "Kh", "6s", "7c", "5h", "3s"));
		TexasHoldEmHand lesserOnePairHand = kata.createHand(asList("Td", "Tc", "Kh", "2s", "7c", "5h", "3s"));
		TexasHoldEmHand minOnePairHand = kata.createHand(asList("2h", "2s", "4h", "3c", "7s", "8h", "6s"));
		TexasHoldEmHand maxHighCardHand = kata.createHand(asList("Ah", "Ks", "Qh", "Jc", "9s", "8h", "6s"));
		TexasHoldEmHand someHighCardHand = kata.createHand(asList("Ks", "Qh", "5c", "3s", "8h", "6s", "4s"));
		TexasHoldEmHand sameHighCardHand = kata.createHand(asList("Kd", "Qc", "5c", "2s", "8h", "6s", "4s"));
		TexasHoldEmHand lessHighCardHand = kata.createHand(asList("Kd", "Qc", "4c", "2s", "8h", "6s", "3s"));
		TexasHoldEmHand minHighCardHand = kata.createHand(asList("2h", "3s", "4h", "9c", "7s", "8h", "6s"));

		assertTrue(royalFlushA.compareTo(royalFlushB) == 0);
		assertTrue(royalFlushB.compareTo(maxStraightFlushHand) < 0);
		assertTrue(maxStraightFlushHand.compareTo(someStraightFlushHand) < 0);
		assertTrue(someStraightFlushHand.compareTo(sameStraightFlushHand) == 0);
		assertTrue(sameStraightFlushHand.compareTo(minStraightFlushHand) < 0);
		assertTrue(minStraightFlushHand.compareTo(maxFourOfAKindHand) < 0);
		assertTrue(maxFourOfAKindHand.compareTo(someFourOfAKindHand) < 0);
		assertTrue(someFourOfAKindHand.compareTo(sameFourOfAKindHand) == 0);
		assertTrue(someFourOfAKindHand.compareTo(lessFourOfAKindHand) < 0);
		assertTrue(lessFourOfAKindHand.compareTo(minFourOfAKindHand) < 0);
		assertTrue(minFourOfAKindHand.compareTo(maxFullHouseHand) < 0);
		assertTrue(maxFullHouseHand.compareTo(someFullHouseHand) < 0);
		assertTrue(someFullHouseHand.compareTo(sameFullHouseHand) == 0);
		assertTrue(someFullHouseHand.compareTo(lessFullHouseHand) < 0);
		assertTrue(lessFullHouseHand.compareTo(minFullHouseHand) < 0);
		assertTrue(minFullHouseHand.compareTo(maxFlushHand) < 0);
		assertTrue(maxFlushHand.compareTo(someFlushHand) < 0);
		assertTrue(someFlushHand.compareTo(sameFlushHand) == 0);
		assertTrue(someFlushHand.compareTo(minFlushHand) < 0);
		assertTrue(minFlushHand.compareTo(maxStraightHand) < 0);
		assertTrue(maxStraightHand.compareTo(someStraightHand) < 0);
		assertTrue(someStraightHand.compareTo(sameStraightHand) == 0);
		assertTrue(someStraightHand.compareTo(minStraightHand) < 0);
		assertTrue(minStraightHand.compareTo(maxTripHand) < 0);
		assertTrue(maxTripHand.compareTo(someTripHand) < 0);
		assertTrue(someTripHand.compareTo(sameTripHand) == 0);
		assertTrue(someTripHand.compareTo(lessTripHand) < 0);
		assertTrue(lessTripHand.compareTo(lesserTripHand) < 0);
		assertTrue(lesserTripHand.compareTo(minTripHand) < 0);
		assertTrue(minTripHand.compareTo(maxTwoPairHand) < 0);
		assertTrue(maxTwoPairHand.compareTo(someTwoPairHand) < 0);
		assertTrue(someTwoPairHand.compareTo(sameTwoPairHand) == 0);
		assertTrue(someTwoPairHand.compareTo(lessTwoPairHand) < 0);
		assertTrue(lessTwoPairHand.compareTo(minTwoPairHand) < 0);
		assertTrue(minTwoPairHand.compareTo(maxOnePairHand) < 0);
		assertTrue(maxOnePairHand.compareTo(someOnePairHand) < 0);
		assertTrue(someOnePairHand.compareTo(sameOnePairHand) == 0);
		assertTrue(someOnePairHand.compareTo(lessOnePairHand) < 0);
		assertTrue(lessOnePairHand.compareTo(lesserOnePairHand) < 0);
		assertTrue(lesserOnePairHand.compareTo(minOnePairHand) < 0);
		assertTrue(minOnePairHand.compareTo(maxHighCardHand) < 0);
		assertTrue(maxHighCardHand.compareTo(someHighCardHand) < 0);
		assertTrue(someHighCardHand.compareTo(sameHighCardHand) == 0);
		assertTrue(someHighCardHand.compareTo(lessHighCardHand) < 0);
		assertTrue(lessHighCardHand.compareTo(minHighCardHand) < 0);
	}

}
