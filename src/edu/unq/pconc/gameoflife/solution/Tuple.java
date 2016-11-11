package edu.unq.pconc.gameoflife.solution;

public class Tuple{
		
		public int left;
		public int right;
		
		public Tuple( int left, int right ){
			this.left = left;
			this.right = right;
		}
		
		public int left(){
			return this.left;
		}
		
		public int right(){
			return this.right;
		}
	}