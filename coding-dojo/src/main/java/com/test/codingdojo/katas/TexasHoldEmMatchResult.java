package com.test.codingdojo.katas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TexasHoldEmMatchResult {

	private final List<TexasHoldEmHand> hands;

	public TexasHoldEmMatchResult(List<TexasHoldEmHand> hands) {
		this.hands = Collections.unmodifiableList(hands);
	}

	public List<TexasHoldEmHand> getWinnerHands() {
		List<TexasHoldEmHand> sortedHands = getSortedHands();
		Iterator<TexasHoldEmHand> iterator = sortedHands.iterator();
		TexasHoldEmHand previousWinner = null;
		while (iterator.hasNext()) {
			TexasHoldEmHand nextWinner = iterator.next();
			if (previousWinner == null) {
				previousWinner = nextWinner;
			} else if (nextWinner.compareTo(previousWinner) != 0) {
				iterator.remove();
			}
		}
		return sortedHands;
	}

	public List<TexasHoldEmHand> getSortedHands() {
		List<TexasHoldEmHand> list = new ArrayList<TexasHoldEmHand>(hands);
		Collections.sort(list);
		return list;
	}

}
