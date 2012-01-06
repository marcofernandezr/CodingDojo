package com.test.codingdojo.katas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TexasHoldEmHand implements Comparable<TexasHoldEmHand> {

	private final List<PokerCard> usedCards;
	private final List<PokerCard> unusedCards;
	private final List<String> cards;
	private final TexasHoldEmType type;
	private final List<PokerCard> kickers;

	@SuppressWarnings("unchecked")
	public TexasHoldEmHand(List<PokerCard> usedCards, List<PokerCard> unusedCards, TexasHoldEmType type,
			PokerCard... kickers) {
		this.usedCards = Collections.unmodifiableList(new ArrayList<PokerCard>(usedCards));
		this.unusedCards = Collections.unmodifiableList(new ArrayList<PokerCard>(unusedCards));
		this.kickers = Collections.unmodifiableList(Arrays.asList(kickers));
		this.type = type;
		this.cards = Collections.unmodifiableList(createCardList(usedCards, unusedCards));
	}

	private List<String> createCardList(List<PokerCard>... allCards) {
		List<String> result = new ArrayList<String>();
		for (List<PokerCard> pokerCards : allCards) {
			for (PokerCard pokerCard : pokerCards) {
				result.add(pokerCard.getName());
			}
		}
		return result;
	}

	public List<String> getCards() {
		return cards;
	}

	public List<PokerCard> getUnusedCards() {
		return unusedCards;
	}

	public List<PokerCard> getUsedCards() {
		return usedCards;
	}

	public TexasHoldEmType getType() {
		return type;
	}

	public boolean isFolded() {
		return cards.size() < 7;
	}

	public List<PokerCard> getKickers() {
		return kickers;
	}

	@Override
	public int compareTo(TexasHoldEmHand o) {
		if (o.type == null) {
			return -1;
		}
		if (type == null) {
			return +1;
		}
		if (type == o.type) {
			if (type == TexasHoldEmType.STRAIGHT_FLUSH || type == TexasHoldEmType.STRAIGHT) {
				if ("A".equals(kickers.get(0).getRank())) {
					return 1;
				} else if ("A".equals(o.kickers.get(0).getRank())) {
					return -1;
				}
			}
			for (int i = 0; i < kickers.size(); i++) {
				int comparison = kickers.get(i).compareTo(o.kickers.get(i));
				if (comparison != 0) {
					return comparison;
				}
			}
		}
		return type.compareTo(o.type);
	}

	@Override
	public String toString() {
		return cards + " > " + type;
	}
}
