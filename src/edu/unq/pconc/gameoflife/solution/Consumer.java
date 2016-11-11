package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;

import edu.unq.pconc.gameoflife.CellGrid;

public class Consumer extends Thread implements CellGrid{

	private String test;
	
	public Consumer( String test ){
		this.test = test;
	}
	
	@Override
	public boolean getCell(int col, int row) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCell(int col, int row, boolean cell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Dimension getDimension() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resize(int col, int row) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setThreads(int threads) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getGenerations() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}
	
	public void run(){
		System.out.println(test);
	}
	

}
