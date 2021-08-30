package com.game.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.game.code.Position;

class PositionTest {
	private Position position;
	
	@BeforeEach
	void init()
	{
		position = new Position();
	}
	
	@Test
	@DisplayName("Testing board initialization")
	void testNewPosition() throws Exception {
		assertEquals('x', position.turn);// checking the turn of x
		assertEquals("         ", position.toString());// 9 blank positions
	}
	
	@Test
	@DisplayName("Testing move by x at index 1")
	void testMove() throws Exception {
		position = position.move(1);
		assertEquals('o', position.turn);
		assertEquals(" x       ", position.toString());
	}

	@Test
	@DisplayName("Testing unmove by x at index 1")
	public void testUnmove() throws Exception{
		position = position.move(1).unmove(1);
		assertEquals('x', position.turn);
		assertEquals("         ", position.toString());
	}
	
	@Test
	@DisplayName("Testing possible moves")
	public void testPossibleMoves() throws Exception {
		List<Integer> listOfInt = new ArrayList<Integer>();
		for( int i=0; i< Position.BOARD_SIZE;i++) {
			listOfInt.add(i);
		}
		listOfInt.remove(new Integer(1));
		listOfInt.remove(new Integer(2));
		assertEquals(listOfInt, position.move(1).move(2).possibleMoves());
	}	
}
