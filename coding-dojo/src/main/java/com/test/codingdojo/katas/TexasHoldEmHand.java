package com.test.codingdojo.katas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TexasHoldEmHand implements Comparable<TexasHoldEmHand> {

	private final List<PokerCard> usedCards;
	private final List<PokerCard> unusedCards;
	private final List<String> cards;
	private final TexasHoldEmType type;
	private PokerCard kicker;

	@SuppressWarnings("unchecked")
	public TexasHoldEmHand(List<PokerCard> usedCards, List<PokerCard> unusedCards, PokerCard kicker, TexasHoldEmType type) {
		this.usedCards = Collections.unmodifiableList(new ArrayList<PokerCard>(usedCards));
		this.unusedCards = Collections.unmodifiableList(new ArrayList<PokerCard>(unusedCards));
		this.kicker = kicker;
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

	public PokerCard getKicker() {
		return kicker;
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
				if ("A".equals(kicker.getRank())) {
					return 1;
				} else if ("A".equals(o.kicker.getRank())) {
					return -1;
				}
			}
			if (kicker.compareTo(o.kicker) == 0) {
				for (Integer nextKickerIndex : type.alternativeKickers()) {
					int comparison = usedCards.get(nextKickerIndex).compareTo(o.usedCards.get(nextKickerIndex));
					if (comparison != 0) {
						return comparison;
					}
				}
			}

			return kicker.compareTo(o.kicker);
		}
		return type.compareTo(o.type);
	}

	@Override
	public String toString() {
		return cards + " > " + type;
	}
}
