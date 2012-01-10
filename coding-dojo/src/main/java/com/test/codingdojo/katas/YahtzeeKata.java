package com.test.codingdojo.katas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class YahtzeeKata {

	public static enum Category {
		ONES, TWOS, THREES, FOURS, FIVES, SIXS, TRIP, QUAD, SMALL_STRAIGHT, LARGE_STRAIGHT, FULL_HOUSE, YAHTZEE, CHANCE;
	}

	private static final int TOTAL_DICES = 5;
	private static final int PAIR_SIZE = 2;
	private static final int QUAD_SIZE = 4;
	private static final int TRIP_SIZE = 3;
	private static final int FULL_HOUSE_SCORE = 25;
	private static final int SMALL_STRAIGHT_SCORE = 30;
	private static final int LARGE_STRAIGHT_SCORE = 40;
	private static final int YAHTZEE_SCORE = 50;
	private static final List<Integer> SMALL_STRAIGHT = Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4, 5));
	private static final List<Integer> LARGE_STRAIGHT = Collections.unmodifiableList(Arrays.asList(2, 3, 4, 5, 6));

	public int evaluate(List<Integer> dices, Category selectedCategory) {
		if (dices == null || dices.size() != TOTAL_DICES || selectedCategory == null) {
			throw new IllegalArgumentException("Invalid dices or category.");
		}
		switch (selectedCategory) {
			case TRIP:
				return evaluateTrip(dices);
			case QUAD:
				return evaluateQuad(dices);
			case SMALL_STRAIGHT:
				return evaluateSmallStraight(dices);
			case LARGE_STRAIGHT:
				return evaluateLargeStraight(dices);
			case FULL_HOUSE:
				return evaluateFullHouse(dices);
			case YAHTZEE:
				return evaluateYahtzee(dices);
			case CHANCE:
				return evaluateChance(dices);
			default:
				return evaluateUpper(dices, selectedCategory.ordinal() + 1);
		}
	}

	private int evaluateYahtzee(List<Integer> dices) {
		Integer reference = dices.get(0);
		for (Integer dice : dices) {
			if (!reference.equals(dice)) {
				return 0;
			}
		}
		return YAHTZEE_SCORE;
	}

	private int evaluateChance(List<Integer> dices) {
		int score = 0;
		for (Integer dice : dices) {
			score += dice;
		}
		return score;
	}

	private int evaluateLargeStraight(List<Integer> dices) {
		if (dices.containsAll(LARGE_STRAIGHT)) {
			return LARGE_STRAIGHT_SCORE;
		}
		return 0;
	}

	private int evaluateSmallStraight(List<Integer> dices) {
		if (dices.containsAll(SMALL_STRAIGHT)) {
			return SMALL_STRAIGHT_SCORE;
		}
		return 0;
	}

	private int evaluateFullHouse(List<Integer> dices) {
		return (evaluateCount(dices, TRIP_SIZE) > 0 && evaluateCount(dices, PAIR_SIZE) > 0) ? FULL_HOUSE_SCORE : 0;
	}

	private int evaluateTrip(List<Integer> dices) {
		return evaluateCount(dices, TRIP_SIZE);
	}

	private int evaluateQuad(List<Integer> dices) {
		return evaluateCount(dices, QUAD_SIZE);
	}

	private int evaluateUpper(List<Integer> dices, int category) {
		int score = 0;
		for (Integer dice : dices) {
			if (category == dice) {
				score += category;
			}
		}
		return score;
	}

	private int evaluateCount(List<Integer> dices, int expectedCount) {
		Map<Integer, AtomicInteger> countMap = mapDicesByCount(dices);
		boolean hasExpectedCount = false;
		int score = 0;
		for (Integer dice : getSortedDices(countMap)) {
			AtomicInteger counter = countMap.get(dice);
			if (!hasExpectedCount && counter.get() == expectedCount) {
				hasExpectedCount = true;
			}
			score += dice * countMap.get(dice).get();
		}
		return hasExpectedCount ? score : 0;
	}

	private Map<Integer, AtomicInteger> mapDicesByCount(List<Integer> dices) {
		Map<Integer, AtomicInteger> countMap = new HashMap<Integer, AtomicInteger>();
		for (Integer dice : dices) {
			AtomicInteger counter = countMap.get(dice);
			if (counter == null) {
				counter = new AtomicInteger(0);
				countMap.put(dice, counter);
			}
			counter.incrementAndGet();
		}
		return countMap;
	}

	private List<Integer> getSortedDices(Map<Integer, AtomicInteger> countMap) {
		List<Integer> sortedDices = new ArrayList<Integer>(countMap.keySet());
		Collections.sort(sortedDices);
		Collections.reverse(sortedDices);
		return sortedDices;
	}

}
