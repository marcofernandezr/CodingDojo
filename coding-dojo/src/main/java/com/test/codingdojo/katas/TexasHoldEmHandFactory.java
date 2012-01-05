package com.test.codingdojo.katas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TexasHoldEmHandFactory {

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
		if (pokerCards.size() < 7) {
			return createFoldHand(pokerCards);
		}
		return findBestHand(findStraightFlushHand(pokerCards), findRankHand(pokerCards), findFlushHand(pokerCards), findStraightHand(pokerCards), findHighCardHand(pokerCards));
	}

	private TexasHoldEmHand findBestHand(TexasHoldEmHand... hands) {
		TexasHoldEmHand bestHand = null;
		for (TexasHoldEmHand hand : hands) {
			if (hand != null) {
				if (bestHand == null || hand.compareTo(bestHand) < 0) {
					bestHand = hand;
				}
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
		Set<List<PokerCard>> straightFlushHandOptions = new HashSet<List<PokerCard>>();
		for (PokerCard candidateCard : pokerCards) {
			List<PokerCard> straightFlushCards = findSortedStraightCards(candidateCard, pokerCards, flush);
			if (straightFlushCards != null) {
				straightFlushHandOptions.add(straightFlushCards);
			}
		}
		TexasHoldEmHand result = null;
		List<PokerCard> bestHand = findBestStraightHand(straightFlushHandOptions, flush);
		if (bestHand != null) {
			if (flush) {
				result = new TexasHoldEmHand(bestHand, getSortedUnusedCards(pokerCards, bestHand), bestHand.get(0), "T".equals(bestHand.get(0).getRank()) ? TexasHoldEmType.ROYAL_FLUSH : TexasHoldEmType.STRAIGHT_FLUSH);
			} else {
				result = new TexasHoldEmHand(bestHand, getSortedUnusedCards(pokerCards, bestHand), bestHand.get(0), TexasHoldEmType.STRAIGHT);
			}
		}
		return result;
	}

	private TexasHoldEmHand findFlushHand(List<PokerCard> pokerCards) {
		List<PokerCard> flushCards = findflushCards(pokerCards);
		TexasHoldEmHand flushHand = null;
		if (flushCards != null) {
			List<PokerCard> usedCards = new ArrayList<PokerCard>(flushCards.subList(0, 5));
			flushHand = new TexasHoldEmHand(usedCards, getSortedUnusedCards(pokerCards, usedCards), usedCards.get(0), TexasHoldEmType.FLUSH);
		}
		return flushHand;
	}

	private List<PokerCard> findBestStraightHand(Set<List<PokerCard>> straightFlushHandOptions, boolean flush) {
		List<PokerCard> bestHand = null;
		for (List<PokerCard> option : straightFlushHandOptions) {
			if (flush && "T".equals(option.get(0).getRank())) {
				return option;
			}
			if (bestHand == null) {
				bestHand = option;
			} else if ("A".equals(bestHand.get(0).getRank()) || "A".equals(option.get(0).getRank())) {
				continue;
			} else if (option.get(0).getRankValue() > bestHand.get(0).getRankValue()) {
				bestHand = option;
			}
		}
		return bestHand;
	}

	private List<PokerCard> findSortedStraightCards(PokerCard selectedCard, List<PokerCard> pokerCards, boolean flush) {
		Set<PokerCard> straightCards = new HashSet<PokerCard>();
		boolean lookForward = true;
		boolean lookBackward = true;
		int delta = 0;
		while (lookForward && straightCards.size() < 5) {
			PokerCard foundCard = null;
			for (PokerCard pokerCard : pokerCards) {
				if (isStraightCard(selectedCard, delta, pokerCard, flush)) {
					foundCard = pokerCard;
				}
			}
			if (foundCard != null) {
				delta++;
				straightCards.add(foundCard);
			} else {
				lookForward = false;
			}
		}
		delta = 0;
		while (lookBackward && straightCards.size() < 5) {
			PokerCard foundCard = null;
			for (PokerCard pokerCard : pokerCards) {
				if (isStraightCard(selectedCard, delta, pokerCard, flush)) {
					foundCard = pokerCard;
				}
			}
			if (foundCard != null) {
				delta--;
				straightCards.add(foundCard);
			} else {
				lookBackward = false;
			}
		}
		if (straightCards.size() == 5) {
			List<PokerCard> sortedStraightCards = new ArrayList<PokerCard>(straightCards);
			Collections.sort(sortedStraightCards);
			if ("A".equals(sortedStraightCards.get(0).getRank()) && "2".equals(sortedStraightCards.get(4).getRank())) {
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
		return null;
	}

	private boolean isStraightCard(PokerCard selectedCard, int delta, PokerCard pokerCard, boolean flush) {
		return pokerCard.getRankValue() == nextRankValue(selectedCard.getRankValue(), delta) && (!flush || selectedCard.getSuit().equals(pokerCard.getSuit()));
	}

	private TexasHoldEmHand createFoldHand(List<PokerCard> pokerCards) {
		return new TexasHoldEmHand(new ArrayList<PokerCard>(), pokerCards, null, null);
	}

	private TexasHoldEmHand findHighCardHand(List<PokerCard> pokerCards) {
		return new TexasHoldEmHand(pokerCards.subList(0, 5), pokerCards.subList(5, 7), pokerCards.get(0), TexasHoldEmType.HIGH_CARD);
	}

	private TexasHoldEmHand findRankHand(List<PokerCard> pokerCards) {
		Map<Integer, List<PokerCard>> rankMap = new HashMap<Integer, List<PokerCard>>();
		for (PokerCard pokerCard : pokerCards) {
			List<PokerCard> rankList = rankMap.get(pokerCard.getRankValue());
			if (rankList == null) {
				rankList = new ArrayList<PokerCard>();
				rankMap.put(pokerCard.getRankValue(), rankList);
			}
			rankList.add(pokerCard);
		}
		List<List<PokerCard>> threeCardSets = new ArrayList<List<PokerCard>>();
		List<List<PokerCard>> twoCardSets = new ArrayList<List<PokerCard>>();
		List<Integer> rankKeys = new ArrayList<Integer>(rankMap.keySet());
		Collections.sort(rankKeys);
		Collections.reverse(rankKeys);
		for (Integer rankKey : rankKeys) {
			List<PokerCard> rankSet = rankMap.get(rankKey);
			if (rankSet.size() == 4) {
				List<PokerCard> sortedUnusedCards = getSortedUnusedCards(pokerCards, rankSet);
				List<PokerCard> usedCards = new ArrayList<PokerCard>(rankSet);
				usedCards.add(sortedUnusedCards.remove(0));
				return new TexasHoldEmHand(usedCards, sortedUnusedCards, usedCards.get(0), TexasHoldEmType.QUAD);
			}
			if (rankSet.size() == 3) {
				threeCardSets.add(rankSet);
			}
			if (rankSet.size() == 2) {
				twoCardSets.add(rankSet);
			}
		}
		if (!threeCardSets.isEmpty()) {
			List<PokerCard> usedCards = new ArrayList<PokerCard>(threeCardSets.remove(0));
			if (!threeCardSets.isEmpty()) {
				usedCards.addAll(threeCardSets.get(0).subList(0, 2));
				return new TexasHoldEmHand(usedCards, getSortedUnusedCards(pokerCards, usedCards), usedCards.get(0), TexasHoldEmType.FULL_HOUSE);
			}
			if (!twoCardSets.isEmpty()) {
				usedCards.addAll(twoCardSets.get(0));
				return new TexasHoldEmHand(usedCards, getSortedUnusedCards(pokerCards, usedCards), usedCards.get(0), TexasHoldEmType.FULL_HOUSE);
			}
			List<PokerCard> sortedUnusedCards = getSortedUnusedCards(pokerCards, usedCards);
			usedCards.add(sortedUnusedCards.remove(0));
			usedCards.add(sortedUnusedCards.remove(0));
			return new TexasHoldEmHand(usedCards, sortedUnusedCards, usedCards.get(0), TexasHoldEmType.TRIP);
		}

		if (!twoCardSets.isEmpty()) {
			List<PokerCard> usedCards = new ArrayList<PokerCard>(twoCardSets.remove(0));
			if (!twoCardSets.isEmpty()) {
				usedCards.addAll(twoCardSets.get(0));
				List<PokerCard> sortedUnusedCards = getSortedUnusedCards(pokerCards, usedCards);
				usedCards.add(sortedUnusedCards.remove(0));
				return new TexasHoldEmHand(usedCards, getSortedUnusedCards(pokerCards, usedCards), usedCards.get(0), TexasHoldEmType.TWO_PAIRS);
			}
			List<PokerCard> sortedUnusedCards = getSortedUnusedCards(pokerCards, usedCards);
			usedCards.add(sortedUnusedCards.remove(0));
			usedCards.add(sortedUnusedCards.remove(0));
			usedCards.add(sortedUnusedCards.remove(0));
			return new TexasHoldEmHand(usedCards, getSortedUnusedCards(pokerCards, usedCards), usedCards.get(0), TexasHoldEmType.ONE_PAIR);
		}
		return null;
	}

	private List<PokerCard> getSortedUnusedCards(List<PokerCard> allCards, List<PokerCard> usedCards) {
		List<PokerCard> unusedCards = new ArrayList<PokerCard>(allCards);
		unusedCards.removeAll(usedCards);
		Collections.sort(unusedCards);
		return unusedCards;
	}

	private List<PokerCard> findflushCards(List<PokerCard> pokerCards) {
		Map<String, List<PokerCard>> suitMap = new HashMap<String, List<PokerCard>>();
		for (PokerCard pokerCard : pokerCards) {
			List<PokerCard> suitList = suitMap.get(pokerCard.getSuit());
			if (suitList == null) {
				suitList = new ArrayList<PokerCard>();
				suitMap.put(pokerCard.getSuit(), suitList);
			}
			suitList.add(pokerCard);
		}
		for (List<PokerCard> suitList : suitMap.values()) {
			if (suitList.size() >= 5) {
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

}
