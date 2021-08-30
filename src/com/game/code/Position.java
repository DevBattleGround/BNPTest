package com.game.code;

import java.util.ArrayList;
import java.util.List;

public class Position {
	public static final int DIMENSION = 3;
	public static final int BOARD_SIZE = DIMENSION*DIMENSION;
	public char turn;
	public char[] board;

	public Position() {
		//Initialising board with blanks and the turn to player X
		this.turn = 'x';
		board = new char[BOARD_SIZE];
		for(short i=0 ; i< BOARD_SIZE; i++)
		{
			board[i]=' ';
		}
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
			
	@Override
	public String toString()
	{
		return new String(board);
	}
}
