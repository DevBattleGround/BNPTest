package com.game.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Position {
	public static final int DIMENSION = 3;
	public static final int BOARD_SIZE = DIMENSION*DIMENSION;
	public char turn;
	public char[] board;
	private Map<Integer, Integer> cache = new HashMap<Integer, Integer>();
	
	public Position() {
		//Initialising board with blanks and the turn to player X
		this.turn = 'x';
		board = new char[BOARD_SIZE];
		for(short i=0 ; i< BOARD_SIZE; i++)
		{
			board[i]=' ';
		}
	}
	
	public Position(String board, char turn) {
		this.board = board.toCharArray();
		this.turn = turn;
	}

	public Position move(int index)
	{
		board[index] = turn;
		turn = turn == 'x' ? 'o' : 'x';
		return this;
	}

	public Position unmove(int index) {
		board[index] = ' ';
		turn = turn == 'x' ? 'o' : 'x';
		return this;
	}
	
	public List<Integer> possibleMoves() {
		List<Integer> listOfInt = new ArrayList<Integer>();
		for (int i = 0; i < board.length; i++)
		{
			if (board[i] == ' ')
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
				return a-b;
			}
		};
		List<Integer> list = possibleMoves();
		return turn == 'x' ? Collections.max(list, cmp) : Collections.min(list, cmp);

	}
	
	public int minmax() {
	
				Integer key = generateKey();
				Integer value = cache.get(key);
				if(value != null) return value;
		
		if(isGameWonBy('x')) 
			return blanks();
		if(isGameWonBy('o')) 
			return -blanks();
		if(blanks() == 0) 
			return 0;
		// recursive cases
		List<Integer> list = new ArrayList<Integer>();
		for(Integer idx : possibleMoves()) {
			list.add(move(idx).minmax());
			unmove(idx);
		}
		value = turn == 'x' ? Collections.max(list) : Collections.min(list);
		cache.put(key, value);
		return value;
	}

	public boolean isGameWonBy(char turn) {
		boolean isWin = false;
		// test win for xxx in row
		for(int i=0; i< BOARD_SIZE ; i+= DIMENSION) {
			isWin = isWin || MatchALine(turn, i, i+DIMENSION , 1);
		}
		// test win for xxx in column
		for(int i=0; i< DIMENSION ; i++) {
			isWin = isWin || MatchALine(turn, i, BOARD_SIZE , DIMENSION);
		}
		// test for diagonal
		isWin = isWin || MatchALine(turn, 0, BOARD_SIZE, DIMENSION+1);
		// test for diagonal
		isWin = isWin || MatchALine(turn, DIMENSION-1, BOARD_SIZE-1, DIMENSION-1);
		return isWin;
	}


	private boolean MatchALine(char turn, int startIndex, int endIndex, int step) {
		for(int i= startIndex; i < endIndex; i+= step)
		{
			if(board[i] != turn)
				return false;
		}
		return true;
	}
		
	public int blanks() {
		int total = 0;
		for(int i=0; i< BOARD_SIZE; i++)
		{
			if(board[i] == ' ')
				total++;
		}
	return total;
	}
	
	public boolean isGameOver() {
		return isGameWonBy('x') || isGameWonBy('o') || blanks() == 0;
	}
	
	public int generateKey()
	{
		int value = 0;
		for(int i = 0; i< BOARD_SIZE; i++)
		{
			value = value*3; 
			if(board[i]=='x')
				value +=1;
			else if(board[i] == 'o')
				value+=2;
		}
		return value;
	}
	
	@Override
	public String toString()
	{
		return new String(board);
	}
}
