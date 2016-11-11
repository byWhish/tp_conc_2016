package tests;

import edu.unq.pconc.gameoflife.solution.GameOfLifeGrid;
import junit.framework.TestCase;

public class GameOfLife extends TestCase{
	
	public void testThreads(){
		GameOfLifeGrid g = new GameOfLifeGrid();
		g.setThreads(1);
		assertEquals(1,1);
	}

}
