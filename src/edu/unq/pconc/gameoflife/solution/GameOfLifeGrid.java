package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import edu.unq.pconc.gameoflife.CellGrid;

public class GameOfLifeGrid implements CellGrid{
	//width = col
	//hegiht = row
	boolean[][] grilla;
	int generaciones;
	
	public Integer unaVariable = 2;
	
	
	public boolean esBorde(int col, int row)
	{
		return col < 0 || row < 0 || row > this.getDimension().height-1 || col > this.getDimension().width-1;  
	}
	public int getVecinosVivos( int col, int row )
	{
		int result = 0;
		
		for (int x = col-1; x < col+2; x++)
		{
			for (int y = row-1; y < row+2; y++)
			{
				if ( !esBorde(x, y) && !(col==x&&row==y) && grilla[x][y] )result++;
			}
		}
		
		return result;
	}
	
	public boolean realizarTarea(Integer vecinosVivos){
		if(vecinosVivos < 2){
			return false;
		}
		else {
			   return (vecinosVivos < 4) || false;
		     }
	}
	

	public boolean evalCell( int col, int row )
	{
		Integer vecinosVivos= this.getVecinosVivos(col, row);
		if (grilla[col][row]){
			return this.realizarTarea(vecinosVivos);
		}
		else {
			  return vecinosVivos == 3;
		     }
	}
	
	@Override
	public boolean getCell(int col, int row) {
		return grilla[col][row];
	}

	@Override
	public void setCell(int col, int row, boolean cell) {
		grilla[col][row] = cell;
	}

	@Override
	public Dimension getDimension() {
		//Dimension width height
		return new Dimension(grilla.length,grilla[0].length);
	}

	@Override
	public void resize(int col, int row) {
		grilla = new boolean [col][row];
		this.clear();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		for ( int x = 0; x < grilla.length-1 ; x++ )
		{
			for ( int y = 0; y < grilla[0].length-1 ; y++ )
			{
				this.setCell(x, y, false);
			}
		}
		
	}

	@Override
	public void setThreads(int threads) {
		// TODO Auto-generated method stub	
	}

	@Override
	public int getGenerations() {
		// TODO Auto-generated method stub
		return generaciones;
	}

	@Override
	public void next() {
		
		boolean[][] auxGrilla = new boolean[(int)this.getDimension().getWidth()][(int)this.getDimension().getHeight()];
		
		for ( int x = 0; x < (int)this.getDimension().getWidth()-1 ; x++ )
		{
			for ( int y = 0; y < (int)this.getDimension().getHeight()-1 ; y ++ )
			{
				auxGrilla[x][y] = this.evalCell(x, y);
			}
		}
		grilla = auxGrilla;
	}

}
