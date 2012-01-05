package com.test.codingdojo.katas;

import java.util.Collections;
import java.util.List;
import static java.util.Arrays.*;

public enum TexasHoldEmType {
	ROYAL_FLUSH, STRAIGHT_FLUSH, QUAD, FULL_HOUSE, FLUSH, STRAIGHT, TRIP, TWO_PAIRS, ONE_PAIR, HIGH_CARD;
	
	public List<Integer> alternativeKickers(){
		switch (this) {
		case QUAD:
			return asList(4);
		case FULL_HOUSE:
			return asList(4);
		case TRIP:
			return asList(4);
		case TWO_PAIRS:
			return asList(3,4);
		case ONE_PAIR:
			return asList(2, 3,4);
		case HIGH_CARD:
			return asList(1, 2, 3,4);
		default:
			return Collections.emptyList();
		}
	}
}
