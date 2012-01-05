package com.test.codingdojo.katas;

public class PokerCard implements Comparable<PokerCard> {

	private final String rank;
	private final Integer rankValue;
	private final String suit;
	private final String name;

	public PokerCard(String card) {
		this.name = card;
		this.rank = card.substring(0, 1);
		this.suit = card.substring(1);
		this.rankValue = parseRankValue(this.rank);
	}

	private int parseRankValue(String rankStr) {
		switch (rankStr.charAt(0)) {
		case 'T':
			return 10;
		case 'J':
			return 11;
		case 'Q':
			return 12;
		case 'K':
			return 13;
		case 'A':
			return 14;
		default:
			return new Integer(rankStr);
		}
	}

	@Override
	public int compareTo(PokerCard c2) {
		return c2.rankValue.compareTo(rankValue);
	}

	public String getSuit() {
		return suit;
	}

	public String getRank() {
		return rank;
	}

	public String getName() {
		return name;
	}

	public Integer getRankValue() {
		return rankValue;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PokerCard other = (PokerCard) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
