package edu.unq.pconc.gameoflife.solution;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import edu.unq.pconc.gameoflife.CellGrid;


public class GameOfLifeGrid implements CellGrid{
	//width = col
	//hegiht = row
	//Dimension width height
	//Dimension(grilla.length,grilla[0].length);
	
	private static boolean[][] grilla;
	private static boolean[][] tmpGrilla;
	private int generaciones = 0;
	private int threads;
	private static int nextCol;
	
	public class Monitor{		
		
		private boolean hayDato;
		private int tareando;
		private boolean puedeUpdate;
		private boolean finGrilla;
		
		public Monitor(){
			tareando = 0;
			hayDato = false;
			puedeUpdate = false;
			finGrilla = false;
		}
		
		public boolean getFinGrilla(){
			return finGrilla;
		}
		
		public synchronized void finGrilla(){
			finGrilla = true;
			this.notifyAll();
		}
		
		private void iniciarGeneracion(){
			puedeUpdate = false;
		}
		
		private synchronized void datoGenerado(){
			hayDato = true;
			this.notifyAll();
		}
		
		public synchronized void consumioDato(){
			hayDato = false;
			this.notifyAll();
		}
		
		public synchronized void esperandoDato(){
			while ( ! hayDato && ! finGrilla ){	
			try{
				this.wait();
				} 
				catch( InterruptedException ex ){
				return;
				}
			}
		}
		
		public synchronized void esperandoProducir()
		{   
			while ( hayDato ){
				try {
					this.wait();
					}
					catch(InterruptedException ex){
					return;
					}	
			}
		}
		
		public synchronized void inicioTarea(){
			tareando++;
		}
		
		public synchronized void finTarea(){
			tareando--;
			
			if ( tareando == 0 ){
				puedeUpdate = true;
				this.notifyAll();
			}
		}

		public synchronized void updateGeneracion() {
			// 
			while ( ! puedeUpdate ){
				try{
					this.wait();
					} 
					catch( InterruptedException ex ){
					return;
					}
			}
			this.notifyAll();
		}
		
	}
	
	public class Consumer extends Thread{
	
		private Monitor monitor;
		
		public Consumer( Monitor monitor ){
			this.monitor = monitor;
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
		
		
		public boolean evalAliveCell(Integer vecinosVivos){
			
			if ( vecinosVivos < 4 )
			{
				return (vecinosVivos > 1);
			}
		else
			{
				return false;
			}
			
		}
		
		public boolean evalCell( int col, int row )
		{
			int vecinosVivos = getVecinosVivos(col, row);
			if (grilla[col][row])
			{
				return this.evalAliveCell(vecinosVivos);
			}
			else 
			{
				return vecinosVivos == 3;
			}
		}
		
		public void run(){
			
			monitor.inicioTarea();
			
			int tareas = 0;
			//Tuple auxCell = new Tuple( 0, 0 );
			int auxCol = 0;
			
			do{ 
					
				tareas++;
				
				monitor.esperandoDato();
			
				//if ( ! finGrilla ){
					//auxCell = nextCell;
					auxCol = nextCol;
				//}else
				//{
					//tareas--;
				//}
				
				monitor.consumioDato();
				
				//tmpGrilla[auxCell.left][auxCell.right] = evalCell(auxCell.left,auxCell.right);
				
				if ( ! monitor.getFinGrilla() ){
				
				for ( int y = 0 ; y < tmpGrilla[auxCol].length ; y++ ){
					tmpGrilla[auxCol][y] = evalCell(auxCol,y);
				}
				}else
				{
					tareas--;
				}
		
			} while ( ! monitor.getFinGrilla() );
			
			monitor.finTarea();		
			
		}

		
	}
	
	public class Producer extends Thread{
		
		Monitor monitor;
		
		private int maxX;
		private int maxY;
		
		public Producer( Monitor monitor, Dimension d ){
			this.monitor = monitor;
			this.maxX = (int)d.getWidth();
			this.maxY = (int)d.getHeight();
		}
		
		public void run(){
			
				
				for  ( int x = 0 ; x <  maxX ; x++ ){
					
					//for ( int y = 0 ; y < maxY ; y++ ){
						
							monitor.esperandoProducir();
		
							//nextCell = new Tuple(x,y);
							nextCol = x;
							
							monitor.datoGenerado();
						
					//}
				}
				
				monitor.finGrilla();
				
			}
		}

	
	
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
	
	
	public boolean evalAliveCell(Integer vecinosVivos){
		
		if ( vecinosVivos < 4 )
			{
				return (vecinosVivos > 1);
			}
		else
			{
				return false;
			}
		
	}
	

	public boolean evalCell( int col, int row )
	{
		int vecinosVivos = getVecinosVivos(col, row);
		if (grilla[col][row])
		{
			return this.evalAliveCell(vecinosVivos);
		}
		else 
		{
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
		generaciones = 0;
		
		for ( int x = 0; x < grilla.length ; x++ )
		{
			for ( int y = 0; y < grilla[0].length ; y++ )
			{
				this.setCell(x, y, false);
			}
		}
		
	}

	
	@Override
	public void setThreads(int threads) {
		this.threads = threads;
	}

	@Override
	public int getGenerations() {
		// TODO Auto-generated method stub
		return generaciones;
	}
	
		

	@Override
	public void next() {
		
		tmpGrilla = new boolean[(int)this.getDimension().getWidth()][(int)this.getDimension().getHeight()];
		
		
		Monitor m = new Monitor();
		
		m.iniciarGeneracion();
		
		Producer p = new Producer( m , this.getDimension() );
		
		p.start();
		
		for ( int idx = 0; idx < this.threads; idx++)
		{
			Consumer c = new Consumer( m );
			c.start();
		}		
		
		m.updateGeneracion();
		grilla = tmpGrilla;	
		generaciones++;
		
	}

}




