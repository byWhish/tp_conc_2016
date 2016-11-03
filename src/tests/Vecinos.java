package tests;

import static org.junit.Assert.assertEquals;

import java.awt.Dimension;

import org.junit.Before;
import org.junit.Test;

import edu.unq.pconc.gameoflife.solution.GameOfLifeGrid;

public class Vecinos {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGrid() {
		GameOfLifeGrid unGame = new GameOfLifeGrid();
		unGame.resize(10,10);
		unGame.setCell(1, 1, true);
		unGame.setCell(0, 0, true);
		assertEquals( unGame.getDimension() , new Dimension(10,10));
		assertEquals( unGame.esBorde(1, 10),true);
		assertEquals( unGame.getVecinosVivos(0, 1), 2 ); 
	}
	
	public void testFilasColumnas(){
		GameOfLifeGrid unGame = new GameOfLifeGrid();
		unGame.resize(10,5);
		assertEquals((int)unGame.getDimension().getWidth(),10);
		assertEquals((int)unGame.getDimension().getHeight(),5);
	}
	
	

}
