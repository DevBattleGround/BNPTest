package com.game.code;

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

	@Override
	public String toString()
	{
		return new String(board);
	}
}
