
public class Player_djw166 implements Player {

	// fields 

		// the player's board A and board B
	private char[][] shipPlace;
	private char [][] HitMiss;

		// the player's opponent's board A and Board B
	private char[][] oppShipPlace;
	private char[][] oppHitMiss;

		// arrays for use in manipulating boards: ship type and length
	private String [] ShipType;
	private int[] ShipLength;



	// constructor that takes in Boards, type of ship, ship length for use in class 
	public Player_djw166(char[][] pBoardA,char[][] pBoardB, char [][] oBoardA, char[][] oBoardB, String[] TypeShip, int[] LengthShip){
	shipPlace = pBoardA;
	HitMiss = pBoardB;
	oppShipPlace = oBoardA;
	oppHitMiss = oBoardB;
	ShipType = TypeShip;
	ShipLength = LengthShip;
	}



	// implementations of Player.java for Player_djw166

	public char fireUpon(Coordinate x){
		int a = x.x;
		int b = x.y;
		char W;
		if(oppShipPlace[a][b]!='~'){
			W = oppShipPlace[a][b];
			return W;
		}else{W = 'M';
			return W;}
	}

	// takes in a guess from a player for firing upon
	public Coordinate fire(){
		int tx;
		do{System.out.println("Enter a valid row # ");
		 tx = IO.readInt();}
		while(tx <1||tx >10);
		tx= tx-1;
		int y;
		do{System.out.println("Enter a valid column #");
		 y = IO.readInt();}
		while(y<1||y>10);
		y = y-1;
		Coordinate guess = new Coordinate(tx,y);	
		
		return guess;
		
	}


		public void fireResult(char result){
			if(result=='M'){
				printBoard();
				System.out.println("You missed");
			}else{
				printBoard();
				System.out.println("You've gotten a hit!");
			}
		}
		
		
		/**
		* Places all the ships for this player
		*/
		public void placeShips(){
			for(int i=0;i<5;i++){ // runs five times, one for each ship
				printBoard(shipPlace); // shows player the board 
				
				System.out.println("Begin placing "+ShipType[i]);
				System.out.println("This ship consists of "+ShipLength[i]+" spaces");
				System.out.println(" Enter a row # then column # where you'd like one end of the "+ShipType[i]+" to go.");
				int firstRow = IO.readInt();
				firstRow = firstRow-1;
				int firstCol = IO.readInt();
				firstCol = firstCol-1;
				boolean firstEnd = XYinBound(firstRow,firstCol);
				while(firstEnd==false){ 
					System.out.println("You've entered coordinates that do not exist");
					firstRow = IO.readInt();
					firstRow = firstRow-1;
					firstCol = IO.readInt();
					firstCol = firstCol-1;} // checks for valid entry
				
				System.out.println("Enter the row #, then column # of the other end of the ship");
				int lastRow = IO.readInt();
				lastRow = lastRow-1;
				int lastCol = IO.readInt();
				lastCol = lastCol-1;
				boolean lastEnd = XYinBound(lastRow, lastCol);
				while(lastEnd==false){
					System.out.println("You've entered coordinates that don't exist");
					lastRow = IO.readInt();
					lastRow = lastRow-1;
					lastCol = IO.readInt();
					lastCol = lastCol-1;}
					
				boolean validPlace = shipPlacementValid(shipPlace,firstRow,firstCol,lastRow,lastCol,ShipType,ShipLength,i);
				
				while(validPlace==false){
					System.out.println("The placement of your ship is invalid. Please start over for "+ShipType[i]);
					// repeats the whole process for this ship until it fits
					System.out.println("Begin placing "+ShipType[i]);
				System.out.println("This ship consists of "+ShipLength[i]+" spaces");
				System.out.println(" Enter a row # then column # where you'd like one end of the "+ShipType[i]+" to go.");
				firstRow = IO.readInt();
				firstCol = IO.readInt();
				firstRow = firstRow-1;
				firstCol = firstCol-1;
						firstEnd = XYinBound(firstRow,firstCol);
				while(firstEnd==false){ 
					System.out.println("You've entered coordinates that do not exist");
					firstRow = IO.readInt();
					firstRow = firstRow-1;
					firstCol = IO.readInt();} // checks for valid entry
					firstCol = firstCol-1;
				
				System.out.println("Enter the row #, then column # of the other end of the ship");
				 lastRow = IO.readInt();
				 lastRow = lastRow-1;
				 lastCol = IO.readInt();
				 lastCol = lastCol-1;
				 lastEnd = XYinBound(lastRow,lastCol);
				while(lastEnd==false){
					System.out.println("You've entered coordinates that don't exist");
					lastRow = IO.readInt();
					lastRow = lastRow-1;
					lastCol = IO.readInt();
					lastCol = lastCol-1; }
					
				 validPlace = shipPlacementValid(shipPlace,firstRow,firstCol,lastRow,lastCol,ShipType,ShipLength, i);
				} // at this point, we have a valid location for the ship!!! (Whew)
				
				char qwerty = ShipType[i].charAt(0);
			
				 
				 if(firstRow==lastRow){
					placeShipCharC(shipPlace,firstRow,firstCol,lastCol,qwerty);}
				 else if(firstCol==lastCol){
					 placeShipCharR(shipPlace,firstCol,firstRow,lastRow,qwerty);}
				 }
		}
		
		// used in placeShips method
		 public static boolean XYinBound(int a, int b){
				if(a<0||a>9)return false;
				if(b<0||b>9)return false;
				
				return true;}
				
		// used in placeShips method	
		 public static boolean shipPlacementValid(char[][] boardA,int a, int b, int x, int y, String[] ship, int[] shipLength, int z){
			 if(a==x){ // if row # of 1 and row # of 2's diff=0, then on same row
					if(Math.abs(b-y)==shipLength[z]-1){// if the diff between column # of 1 and column # of 2 =shiplength, then placement is ok
						for(int r = Math.min(b,y); r<=Math.max(b,y);r++) { // go from largest column # to smallest
								if(boardA[a][r]=='~') continue;
								else return false;// return false if there is already another ship occupying one or more of the squares 
								} return true; // if we get to this point, ship can finally be placed
							} else return false;
						}
				else if(b==y){ // if column # of 1 and column # of 2's diff= 0, then in same column
					if(Math.abs(a-x)==shipLength[z]-1){ // if diff between row #s equals shipLength, we can move forward
							for(int q = Math.min(a, x);q<=Math.max(a, x);q++){ // goes from smallest to largest
								if(boardA[q][b]=='~') continue;
								else return false;// returns false if there is another ship already occupying part of space						
								} return true; // if we get here, all's good!
							} else return true;
						}
						
				else return false; // if you attempt to place ship in anything other than a straight line
			}
		 
		// used in placeShips Method
		
		 public static void placeShipCharR(char[][] board, int col, int a, int b, char c){
			for(int rho=Math.min(a,b);rho<=Math.max(a,b);rho++)
			{ board [rho][col] = c;}   } // used to place ship's car on board if ship in 1 column
		 
		 public static void placeShipCharC(char[][] board, int row, int a, int b, char c){
			for(int kol = Math.min(a,b);kol<=Math.max(a,b);kol++)
			{ board[row][kol]= c; }   } // used to place ship's char on board if ship in 1 row
		/**
		* Returns if this player has lost 
		*
		* @return true if this player has lost, false otherwise
		*/
		public boolean lost(){
				boolean verify = true;
				for(int i=0;i<oppHitMiss.length;i++){
					for(int k =0;k<oppHitMiss.length;k++){
						if(shipPlace[i][k]!='~'){
							if(oppHitMiss[i][k]==shipPlace[i][k]) continue;
							else {verify = false;}
						}else continue;
					}	
				}
				return verify;
		}
		/**
		* Prints this player's Board B. 
		*/
		public void printBoard(){ // can be called outside of this class
		 System.out.print("  ");
			 for(int z=1;z<=HitMiss.length;z++){
			  System.out.print(" "+z);}
			  System.out.println(" "); // column # row
			 
			 for(int a=1;a<=HitMiss.length;a++){// for each row...
				 if(a>=10){System.out.print(a);}
				 else{System.out.print(a+" "); }// print out the row number..
				 
				for(int b=0;b<HitMiss.length;b++){ // then for each column...
				 System.out.print(" "+HitMiss[a-1][b]); // print the contents of array
				 System.out.println(" "); 
				}
			 }
		} 
		
		// used solely for human placement of ships; used to remind player where they place ships
		// different signatures! overloading
		public static void printBoard(char[][] board){
			 System.out.print("  ");
			 for(int z=1;z<=board.length;z++){
			  System.out.print(" "+z);}
			  System.out.println(" "); // column # row
			 
			 for(int a=1;a<=board.length;a++){// for each row...
				 if(a>=10){System.out.print(a);}
				 else{System.out.print(a+" "); }// print out the row number..
				 
				for(int b=0;b<board.length;b++){ // then for each column...
				 System.out.print(" "+board[a-1][b]); // print the contents of array
				}
				System.out.println(" ");// move to next line 
			 }
			 }

		
		

}
