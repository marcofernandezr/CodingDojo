package com.test.codingdojo.katas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TexasHoldEmHandFactory {

	private static final int PAIR_LENGTH = 2;
	private static final int TRIP_LENGTH = 3;
	private static final int QUAD_LENGTH = 4;
	private static final String TWO = "2";
	private static final int TEXAS_HOLDEM_TOTAL_SIZE = 7;
	private static final int TEXAS_HOLDEM_HAND_SIZE = 5;
	private static final String ACE = "A";
	private static final String KING = "T";

	public TexasHoldEmHand create(List<String> cards) {
		return createHand(createSortedPokerCards(cards));
	}

	private List<PokerCard> createSortedPokerCards(List<String> cards) {
		List<PokerCard> pokerCards = new ArrayList<PokerCard>();
		for (String card : cards) {
			pokerCards.add(new PokerCard(card));
		}
		Collections.sort(pokerCards);
		return pokerCards;
	}

	private TexasHoldEmHand createHand(List<PokerCard> pokerCards) {
		if (pokerCards.size() < TEXAS_HOLDEM_TOTAL_SIZE) {
			return createFoldHand(pokerCards);
		}
		return chooseBestHand(findStraightFlushHand(pokerCards), findRankHand(pokerCards), findFlushHand(pokerCards),
				findStraightHand(pokerCards), findHighCardHand(pokerCards));
	}

	private TexasHoldEmHand chooseBestHand(TexasHoldEmHand... hands) {
		TexasHoldEmHand bestHand = null;
		for (TexasHoldEmHand hand : hands) {
			if (bestHand == null || (hand != null && hand.compareTo(bestHand) < 0)) {
				bestHand = hand;
			}
		}
		return bestHand;
	}

	private TexasHoldEmHand findStraightFlushHand(List<PokerCard> pokerCards) {
		return findStraightHand(pokerCards, true);
	}

	private TexasHoldEmHand findStraightHand(List<PokerCard> pokerCards) {
		return findStraightHand(pokerCards, false);
	}

	private TexasHoldEmHand findStraightHand(List<PokerCard> pokerCards, boolean flush) {
		Set<List<PokerCard>> straightHandOptions = new HashSet<List<PokerCard>>();
		for (PokerCard candidateCard : pokerCards) {
			List<PokerCard> straightFlushCards = findSortedStraightCards(candidateCard, pokerCards, flush);
			if (straightFlushCards != null) {
				straightHandOptions.add(straightFlushCards);
			}
		}
		return createStraightHand(pokerCards, chooseBestStraightCards(straightHandOptions, flush), flush);
	}

	private TexasHoldEmHand createStraightHand(List<PokerCard> pokerCards, List<PokerCard> bestHand, boolean flush) {
		if (bestHand == null) {
			return null;
		}
		PokerCard kicker = bestHand.get(0);
		if (isRoyalFlush(flush, kicker)) {
			return new TexasHoldEmHand(bestHand, getSortedUnusedCards(pokerCards, bestHand), TexasHoldEmType.ROYAL_FLUSH);
		}
		if (flush) {
			return new TexasHoldEmHand(bestHand, getSortedUnusedCards(pokerCards, bestHand), TexasHoldEmType.STRAIGHT_FLUSH,
					kicker);
		}
		return new TexasHoldEmHand(bestHand, getSortedUnusedCards(pokerCards, bestHand), TexasHoldEmType.STRAIGHT, kicker);
	}

	private TexasHoldEmHand findFlushHand(List<PokerCard> pokerCards) {
		List<PokerCard> flushCards = findflushCards(pokerCards);
		if (flushCards == null) {
			return null;
		}
		List<PokerCard> usedCards = new ArrayList<PokerCard>(flushCards.subList(0, TEXAS_HOLDEM_HAND_SIZE));
		return new TexasHoldEmHand(usedCards, getSortedUnusedCards(pokerCards, usedCards), TexasHoldEmType.FLUSH,
				usedCards.get(0));
	}

	private List<PokerCard> chooseBestStraightCards(Set<List<PokerCard>> straightHandOptions, boolean isFlushHand) {
		List<PokerCard> bestHand = null;
		PokerCard bestKicker = null;
		for (List<PokerCard> option : straightHandOptions) {
			PokerCard optionKicker = option.get(0);
			if (isRoyalFlush(isFlushHand, optionKicker)) {
				return option;
			}
			if (bestHand == null || isOptionKickerBetter(bestKicker, optionKicker)) {
				bestHand = option;
				bestKicker = option.get(0);
			}
		}
		return bestHand;
	}

	private boolean isOptionKickerBetter(PokerCard bestKicker, PokerCard optionKicker) {
		if (isLowestStraightKicker(bestKicker) || isLowestStraightKicker(optionKicker)) {
			return false;
		}
		return optionKicker.getRankValue() > bestKicker.getRankValue();
	}

	private boolean isLowestStraightKicker(PokerCard kicker) {
		return ACE.equals(kicker.getRank());
	}

	private boolean isRoyalFlush(boolean isFlush, PokerCard optionTopCard) {
		return isFlush && KING.equals(optionTopCard.getRank());
	}

	private boolean isStraightCard(PokerCard selectedCard, int delta, PokerCard pokerCard, boolean flush) {
		return pokerCard.getRankValue() == nextRankValue(selectedCard.getRankValue(), delta)
				&& (!flush || selectedCard.getSuit().equals(pokerCard.getSuit()));
	}

	private TexasHoldEmHand createFoldHand(List<PokerCard> pokerCards) {
		return new TexasHoldEmHand(new ArrayList<PokerCard>(), pokerCards, null);
	}

	private TexasHoldEmHand findHighCardHand(List<PokerCard> pokerCards) {
		List<PokerCard> usedCards = pokerCards.subList(0, TEXAS_HOLDEM_HAND_SIZE);
		return new TexasHoldEmHand(usedCards, pokerCards.subList(TEXAS_HOLDEM_HAND_SIZE, TEXAS_HOLDEM_TOTAL_SIZE),
				TexasHoldEmType.HIGH_CARD, usedCards.toArray(new PokerCard[TEXAS_HOLDEM_HAND_SIZE]));
	}

	private List<PokerCard> getSortedUnusedCards(List<PokerCard> allCards, List<PokerCard> usedCards) {
		List<PokerCard> unusedCards = new ArrayList<PokerCard>(allCards);
		unusedCards.removeAll(usedCards);
		Collections.sort(unusedCards);
		return unusedCards;
	}

	private List<PokerCard> findflushCards(List<PokerCard> pokerCards) {
		return findFlushCards(mapPokerCardsBySuit(pokerCards));
	}

	private Map<String, List<PokerCard>> mapPokerCardsBySuit(List<PokerCard> pokerCards) {
		Map<String, List<PokerCard>> suitMap = new HashMap<String, List<PokerCard>>();
		for (PokerCard pokerCard : pokerCards) {
			List<PokerCard> suitList = suitMap.get(pokerCard.getSuit());
			if (suitList == null) {
				suitList = new ArrayList<PokerCard>();
				suitMap.put(pokerCard.getSuit(), suitList);
			}
			suitList.add(pokerCard);
		}
		return suitMap;
	}

	private List<PokerCard> findFlushCards(Map<String, List<PokerCard>> suitMap) {
		for (List<PokerCard> suitList : suitMap.values()) {
			if (suitList.size() >= TEXAS_HOLDEM_HAND_SIZE) {
				return suitList;
			}
		}
		return null;
	}

	private int nextRankValue(int rankValue, int delta) {
		int nextRankValue = rankValue + delta;
		if (nextRankValue > 14) {
			nextRankValue -= 13;
		}
		if (nextRankValue < 2) {
			nextRankValue += 13;
		}
		return nextRankValue;
	}

	private List<PokerCard> findSortedStraightCards(PokerCard selectedCard, List<PokerCard> pokerCards, boolean flush) {
		Set<PokerCard> straightCards = new HashSet<PokerCard>();
		straightCards.addAll(selectStraightCards(selectedCard, pokerCards, flush, true));
		straightCards.addAll(selectStraightCards(selectedCard, pokerCards, flush, false));
		if (straightCards.size() == TEXAS_HOLDEM_HAND_SIZE) {
			return sortStraightCards(straightCards);
		}
		return null;
	}

	private List<PokerCard> sortStraightCards(Set<PokerCard> straightCards) {
		List<PokerCard> sortedStraightCards = new ArrayList<PokerCard>(straightCards);
		Collections.sort(sortedStraightCards);
		if (isNonLinearStraightHand(sortedStraightCards.get(0), sortedStraightCards.get(TEXAS_HOLDEM_HAND_SIZE - 1))) {
			List<PokerCard> temp = new ArrayList<PokerCard>(sortedStraightCards);
			sortedStraightCards.clear();
			int index = 1;
			while ((temp.get(index - 1).getRankValue() - 1) == (temp.get(index).getRankValue())) {
				index++;
			}
			List<PokerCard> firstBlock = temp.subList(0, index);
			Collections.reverse(firstBlock);
			sortedStraightCards.addAll(firstBlock);
			List<PokerCard> secondBlock = temp.subList(index, temp.size());
			Collections.reverse(secondBlock);
			sortedStraightCards.addAll(secondBlock);
		} else {
			Collections.reverse(sortedStraightCards);
		}
		return sortedStraightCards;
	}

	private boolean isNonLinearStraightHand(PokerCard firstCard, PokerCard lastCard) {
		return isLowestStraightKicker(firstCard) && isRankedTwo(lastCard);
	}

	private boolean isRankedTwo(PokerCard pokerCard) {
		return TWO.equals(pokerCard.getRank());
	}

	private Set<PokerCard> selectStraightCards(PokerCard referenceCard, List<PokerCard> cards, boolean flush, boolean asc) {
		Set<PokerCard> straightCards = new HashSet<PokerCard>();
		boolean keepLooking = true;
		int delta = 0;
		while (keepLooking && straightCards.size() < TEXAS_HOLDEM_HAND_SIZE) {
			PokerCard foundCard = null;
			for (PokerCard pokerCard : cards) {
				if (isStraightCard(referenceCard, delta, pokerCard, flush)) {
					foundCard = pokerCard;
				}
			}
			if (foundCard != null) {
				delta = asc ? delta + 1 : delta - 1;
				straightCards.add(foundCard);
			} else {
				keepLooking = false;
			}
		}
		return straightCards;
	}

	private TexasHoldEmHand findRankHand(List<PokerCard> pokerCards) {
		return chooseBestRankHand(pokerCards, mapPokerCardsByRankCount(mapPokerCardsByRank(pokerCards)));
	}

	private TexasHoldEmHand chooseBestRankHand(List<PokerCard> pokerCards,
			Map<Integer, List<List<PokerCard>>> rankCountMap) {
		List<Integer> sortedRankCount = new ArrayList<Integer>(rankCountMap.keySet());
		Collections.sort(sortedRankCount);
		Collections.reverse(sortedRankCount);
		Integer maxRankCount = sortedRankCount.get(0);
		switch (maxRankCount) {
			case QUAD_LENGTH:
				return createQuadHand(pokerCards, rankCountMap);
			case TRIP_LENGTH:
				return createFullHouseOrTripHand(pokerCards, rankCountMap);
			case PAIR_LENGTH:
				return createOneOrTwoPairHand(pokerCards, rankCountMap);
			default:
				return null;
		}
	}

	private TexasHoldEmHand createOneOrTwoPairHand(List<PokerCard> pokerCards,
			Map<Integer, List<List<PokerCard>>> rankCountMap) {
		List<List<PokerCard>> pairs = rankCountMap.get(PAIR_LENGTH);
		List<PokerCard> usedCards = new ArrayList<PokerCard>(pairs.remove(0));
		if (!pairs.isEmpty()) {
			usedCards.addAll(pairs.get(0));
			List<PokerCard> sortedUnusedCards = getSortedUnusedCards(pokerCards, usedCards);
			usedCards.add(sortedUnusedCards.remove(0));
			return new TexasHoldEmHand(usedCards, getSortedUnusedCards(pokerCards, usedCards), TexasHoldEmType.TWO_PAIRS,
					usedCards.get(0), usedCards.get(PAIR_LENGTH), usedCards.get(TEXAS_HOLDEM_HAND_SIZE - 1));
		}
		List<PokerCard> sortedUnusedCards = getSortedUnusedCards(pokerCards, usedCards);
		for (int i = 0; i < TEXAS_HOLDEM_HAND_SIZE - PAIR_LENGTH; i++) {
			usedCards.add(sortedUnusedCards.remove(0));
		}
		return new TexasHoldEmHand(usedCards, getSortedUnusedCards(pokerCards, usedCards), TexasHoldEmType.ONE_PAIR,
				usedCards.get(0), usedCards.get(PAIR_LENGTH), usedCards.get(PAIR_LENGTH + 1),
				usedCards.get(TEXAS_HOLDEM_HAND_SIZE - 1));
	}

	private TexasHoldEmHand createFullHouseOrTripHand(List<PokerCard> pokerCards,
			Map<Integer, List<List<PokerCard>>> rankCountMap) {
		List<List<PokerCard>> trips = rankCountMap.get(TRIP_LENGTH);
		List<PokerCard> usedCards = new ArrayList<PokerCard>(trips.remove(0));
		if (!trips.isEmpty()) {
			usedCards.addAll(trips.get(0).subList(0, PAIR_LENGTH));
			return new TexasHoldEmHand(usedCards, getSortedUnusedCards(pokerCards, usedCards), TexasHoldEmType.FULL_HOUSE,
					usedCards.get(0), usedCards.get(TRIP_LENGTH));
		}
		List<List<PokerCard>> pairList = rankCountMap.get(PAIR_LENGTH);
		if (pairList != null && !pairList.isEmpty()) {
			usedCards.addAll(pairList.get(0));
			return new TexasHoldEmHand(usedCards, getSortedUnusedCards(pokerCards, usedCards), TexasHoldEmType.FULL_HOUSE,
					usedCards.get(0), usedCards.get(TRIP_LENGTH));
		}
		List<PokerCard> sortedUnusedCards = getSortedUnusedCards(pokerCards, usedCards);
		usedCards.add(sortedUnusedCards.remove(0));
		usedCards.add(sortedUnusedCards.remove(0));
		return new TexasHoldEmHand(usedCards, sortedUnusedCards, TexasHoldEmType.TRIP, usedCards.get(0),
				usedCards.get(TRIP_LENGTH), usedCards.get(TEXAS_HOLDEM_HAND_SIZE - 1));
	}

	private TexasHoldEmHand createQuadHand(List<PokerCard> pokerCards, Map<Integer, List<List<PokerCard>>> rankCountMap) {
		List<PokerCard> usefulCards = rankCountMap.get(QUAD_LENGTH).get(0);
		List<PokerCard> sortedUnusedCards = getSortedUnusedCards(pokerCards, usefulCards);
		usefulCards.add(sortedUnusedCards.remove(0));
		return new TexasHoldEmHand(usefulCards, sortedUnusedCards, TexasHoldEmType.QUAD, usefulCards.get(0),
				usefulCards.get(QUAD_LENGTH));
	}

	private Map<Integer, List<List<PokerCard>>> mapPokerCardsByRankCount(Map<Integer, List<PokerCard>> rankMap) {
		Map<Integer, List<List<PokerCard>>> result = new HashMap<Integer, List<List<PokerCard>>>();
		List<Integer> rankKeys = new ArrayList<Integer>(rankMap.keySet());
		Collections.sort(rankKeys);
		Collections.reverse(rankKeys);
		for (Integer rankKey : rankKeys) {
			List<PokerCard> rankSet = rankMap.get(rankKey);
			List<List<PokerCard>> rankCountList = result.get(rankSet.size());
			if (rankCountList == null) {
				rankCountList = new ArrayList<List<PokerCard>>();
				result.put(rankSet.size(), rankCountList);
			}
			rankCountList.add(rankSet);
		}
		return result;
	}

	private Map<Integer, List<PokerCard>> mapPokerCardsByRank(List<PokerCard> pokerCards) {
		Map<Integer, List<PokerCard>> rankMap = new HashMap<Integer, List<PokerCard>>();
		for (PokerCard pokerCard : pokerCards) {
			List<PokerCard> rankList = rankMap.get(pokerCard.getRankValue());
			if (rankList == null) {
				rankList = new ArrayList<PokerCard>();
				rankMap.put(pokerCard.getRankValue(), rankList);
			}
			rankList.add(pokerCard);
		}
		return rankMap;
	}

}
