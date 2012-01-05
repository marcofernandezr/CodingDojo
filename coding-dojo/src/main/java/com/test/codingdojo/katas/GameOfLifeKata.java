package com.test.codingdojo.katas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class GameOfLifeKata {

	private static final String DEAD_CELL = " ";
	private static final String LIVE_CELL = "*";

	public String tic(String input) throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(input));
		String line = null;
		List<String[]> before = new ArrayList<String[]>();
		while ((line = reader.readLine()) != null) {
			before.add(line.split("(?<=\\G.{1})"));
		}
		List<String[]> after = new ArrayList<String[]>();
		for (int i = 0; i < before.size(); i++) {
			String[] row = before.get(i);
			String[] nextRow = new String[row.length];
			after.add(nextRow);
			for (int j = 0; j < row.length; j++) {
				nextRow[j] = getNextStatus(row[j], calculateNeighboursAlive(before, i, j));
			}
		}
		StringBuilder result = new StringBuilder();
		for (String[] row : after) {
			for (String value : row) {
				result.append(value);
			}
			result.append("\n");
		}
		return result.substring(0, result.length() - 1);
	}

	private int calculateNeighboursAlive(List<String[]> before, int row, int column) {
		List<String> neighbours = new ArrayList<String>();
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				if (i == row && j == column) {
					continue;
				}
				try {
					neighbours.add(before.get(i)[j]);
				} catch (IndexOutOfBoundsException e) {
				}
			}
		}
		int result = 0;
		for (String neighbour : neighbours) {
			if (LIVE_CELL.equals(neighbour)) {
				result++;
			}
		}
		return result;
	}

	private String getNextStatus(String currentStatus, int aliveNeighbours) {
		if (aliveNeighbours < 2 || aliveNeighbours > 3) {
			return DEAD_CELL;
		}
		if (DEAD_CELL.equals(currentStatus) && aliveNeighbours == 3) {
			return LIVE_CELL;
		}
		return currentStatus;
	}
}
