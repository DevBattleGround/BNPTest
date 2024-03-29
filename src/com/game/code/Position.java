package com.game.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Position {
	public static final int DIMENSION = 3;
	public static final int BOARD_SIZE = DIMENSION * DIMENSION;
	public char playerTurn;
	public char[] ticTacToeBoard;
	private Map<Integer, Integer> cache = new HashMap<Integer, Integer>();

	public Position() {
		// Initialising board with blanks and the turn to player X
		this.playerTurn = 'x';
		ticTacToeBoard = new char[BOARD_SIZE];
		for (short i = 0; i < BOARD_SIZE; i++) {
			ticTacToeBoard[i] = ' ';
		}
	}

	public Position(String board, char turn) {
		this.ticTacToeBoard = board.toCharArray();
		this.playerTurn = turn;
	}

	public Position move(int index) {
		ticTacToeBoard[index] = playerTurn;
		playerTurn = playerTurn == 'x' ? 'o' : 'x';
		return this;
	}

	public Position unmove(int index) {
		ticTacToeBoard[index] = ' ';
		playerTurn = playerTurn == 'x' ? 'o' : 'x';
		return this;
	}

	public List<Integer> possibleMoves() {
		List<Integer> listOfInt = new ArrayList<Integer>();
		for (int i = 0; i < ticTacToeBoard.length; i++) {
			if (ticTacToeBoard[i] == ' ')
				listOfInt.add(i);
		}
		return listOfInt;
	}

	public int bestMove() {
		Comparator<Integer> cmp = new Comparator<Integer>() {
			public int compare(Integer firstElementInList, Integer secondElementInList) {
				int a = move(firstElementInList).minmax();
				unmove(firstElementInList);
				int b = move(secondElementInList).minmax();
				unmove(secondElementInList);
				return a - b;
			}
		};
		List<Integer> list = possibleMoves();
		return playerTurn == 'x' ? Collections.max(list, cmp) : Collections.min(list, cmp);

	}

	public int minmax() {

		Integer key = generateKey();
		Integer value = cache.get(key);
		if (value != null)
			return value;

		if (isGameWonBy('x'))
			return blanksOnBoard();
		if (isGameWonBy('o'))
			return -blanksOnBoard();
		if (blanksOnBoard() == 0)
			return 0;
		// recursive cases
		List<Integer> list = new ArrayList<Integer>();
		for (Integer idx : possibleMoves()) {
			list.add(move(idx).minmax());
			unmove(idx);
		}
		value = playerTurn == 'x' ? Collections.max(list) : Collections.min(list);
		cache.put(key, value);
		return value;
	}

	public boolean isGameWonBy(char turn) {

		boolean isWin = false;
		return matchesFoundInRow(turn, isWin) ||	matchesFoundInColumn(turn, isWin) || matchesFoundInLeftDiagonal(turn, isWin) || matchesFoundInRightDiagonal(turn, isWin);
	}

	private boolean matchesFoundInLeftDiagonal(char turn, boolean isWin) {
		isWin = isWin || MatchALine(turn, 0, BOARD_SIZE, DIMENSION + 1);
		return isWin;
	}

	private boolean matchesFoundInRightDiagonal(char turn, boolean isWin) {
		isWin = isWin || MatchALine(turn, DIMENSION - 1, BOARD_SIZE - 1, DIMENSION - 1);
		return isWin;
	}

	private boolean matchesFoundInColumn(char turn, boolean isWin) {
		for (int i = 0; i < DIMENSION; i++) {
			isWin = isWin || MatchALine(turn, i, BOARD_SIZE, DIMENSION);
		}
		return isWin;
	}

	private boolean matchesFoundInRow(char turn, boolean isWin) {
		for (int i = 0; i < BOARD_SIZE; i += DIMENSION) {
			isWin = isWin || MatchALine(turn, i, i + DIMENSION, 1);
		}
		return isWin;
	}

	private boolean MatchALine(char turn, int startIndex, int endIndex, int step) {
		for (int i = startIndex; i < endIndex; i += step) {
			if (ticTacToeBoard[i] != turn)
				return false;
		}
		return true;
	}

	public int blanksOnBoard() {
		int totalBlanksOnBoard = 0;
		for (int i = 0; i < BOARD_SIZE; i++) {
			if (ticTacToeBoard[i] == ' ')
				totalBlanksOnBoard++;
		}
		return totalBlanksOnBoard;
	}

	public boolean isGameOver() {
		return isGameWonBy('x') || isGameWonBy('o') || blanksOnBoard() == 0;
	}

	public int generateKey() {
		int value = 0;
		for (int i = 0; i < BOARD_SIZE; i++) {
			value = value * 3;
			if (ticTacToeBoard[i] == 'x')
				value += 1;
			else if (ticTacToeBoard[i] == 'o')
				value += 2;
		}
		return value;
	}

	@Override
	public String toString() {
		return new String(ticTacToeBoard);
	}
}
