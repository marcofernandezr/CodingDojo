package com.test.codingdojo.katas;

import java.util.ArrayList;
import java.util.List;

public class TexasHoldEmKata {

	private final TexasHoldEmHandFactory handFactory = new TexasHoldEmHandFactory();

	public TexasHoldEmMatchResult evaluateHands(List<String>... handsCards) {
		List<TexasHoldEmHand> hands = new ArrayList<TexasHoldEmHand>();
		for (List<String> handCards : handsCards) {
			hands.add(createHand(handCards));
		}
		return new TexasHoldEmMatchResult(hands);
	}

	public TexasHoldEmHand createHand(List<String> cards) {
		return handFactory.create(cards);
	}

}
