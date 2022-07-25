
import java.util.Random;

public class EasyAIPlayer_djw166 implements Player {
	
	/*
	 * This computer player is an easy version of an AI player. this class differs from that of a human player
	 * because the inputs for placeships() and fire() are not asked for; we must generate them.
	 * This version uses randomness to place ships; however, it will not check to see if other placed ships are close, like difficult AI does
	 * This version uses randomness to search for ships; it is so dumb it doesn't think to check around previous hits
	 * 
	 */

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
	private Random randomGenerator1 = new Random();



	// constructor that takes in Boards, type of ship, ship length for use in class 
	public EasyAIPlayer_djw166(char[][] pBoardA,char[][] pBoardB, char [][] oBoardA, char[][] oBoardB, String[] TypeShip, int[] LengthShip){

	shipPlace = pBoardA;
	HitMiss = pBoardB;
	oppShipPlace = oBoardA;
	oppHitMiss = oBoardB;
	ShipType = TypeShip;
	ShipLength = LengthShip;


	}
	
	
	
	
	/*
	 **************************** 
	 * For placement of ships 
	 * *************************
	 */

public void placeShips(){
	

	
for(int a = 0;a<5;a++){

int lengthofShip = ShipLength[a];

	
char PlaceMarker = ShipType[a].charAt(0);

boolean isThereAShip; // local variable to declare method for placing ships
boolean oneRow;

/*
this first do...while loop helps to determine whether a ship, after creating a 'hypothetical coordinate',
and also after determining the placement of the ship won't occupy another ship's space, is at least one square away from any other ships on the grid. IF it is not, then it chooses a new random 'anchor coordinate' to begin the process until the algorithm is met.
*/


int initialRow = randomGenerator1.nextInt(10); // randomly generates a row # to choose for 'anchor' square
int initialCol = randomGenerator1.nextInt(10); // randomly generates a column # to choose for 'anchor' square


Coordinate anchorSquare = new Coordinate(initialRow, initialCol);
Coordinate chosenCoordinate = new Coordinate();

	
/*
 * this first do...while loop is for the difficult setting...
 * detects if any ships have been placed around the proposed new ship placement
 * 
 */
	do{
		int GridNum = EasyAIPlayer_djw166.WhichGridisIt(anchorSquare);
	Coordinate hypothCoordinate = EasyAIPlayer_djw166.hypotheticalPosition(anchorSquare, lengthofShip, GridNum);
	
	anchorSquare.x = Math.min(anchorSquare.x,hypothCoordinate.x); // these commands guarantee the anchor square always contains the smallest row
	anchorSquare.y = Math.min(anchorSquare.y, hypothCoordinate.y); // or column we need for placing ships. it just helps with the design of the loops.
	hypothCoordinate.x = Math.max(anchorSquare.x, hypothCoordinate.x); 
	hypothCoordinate.y = Math.max(anchorSquare.y,hypothCoordinate.y);
			
		if(anchorSquare.x-hypothCoordinate.x==0){
				oneRow = true;
			}else oneRow = false;
			
	isThereAShip = EasyAIPlayer_djw166.isThereAlreadyAShip(shipPlace,anchorSquare,hypothCoordinate);
	
	chosenCoordinate.x = hypothCoordinate.x;
	chosenCoordinate.y = hypothCoordinate.y;
	} while(isThereAShip==true); 
	/*
 // this second do..while.. guarantees that the placement of the ship is correct; there are only two possible choices for it to choose, thanks to the grid algorithm employed by hypotheticalCoordinate() (details below)
 It may run several times until the random effect generates a binary signal and creates another hypothetical situation; but it will eventually choose the second position 
 */


	

	if(oneRow==true){ // then the ship is being placed on 1 row
		placeShipCharR(shipPlace,anchorSquare,chosenCoordinate,PlaceMarker);
	}
	else{ ///... the ship is being placed in 1 column 
		placeShipCharC(shipPlace,anchorSquare,chosenCoordinate,PlaceMarker);
	}

	
} // end of 1 iteration of for loop; runs five times, 1 for each ship


} // end of method placeShips()

	
	
	
	
	
	
	
	//**** this method determines if there is already a ship placed in the hypothetical situation proposed by the computer, used before the placement of ships on Board A

		public static boolean isThereAlreadyAShip(char[][] shipPlacement, Coordinate front, Coordinate back){
		int frontRow = front.x;
		int frontCol = front.y;
		int backRow = back.x;
		int backCol = back.y;
		
		if(frontRow-backRow==0){ // then the ship will be placed in 1 row
			for(int z = frontCol;z<=backCol;z++){
				if(shipPlacement[frontRow][z]!='~') return false;
			}
			return true;
		}
		else{ // the ship will be placed in 1 column
			for(int r = frontRow;r<= backRow;r++){
				if(shipPlacement[r][frontCol]!='~') return false;
			}
			return true;
		}
		}
		
		
		// placing a ship that's horizontal
		 public static void placeShipCharR(char[][] board, Coordinate a, Coordinate b, char c){
				int first = a.y;
				int last = b.y;
				int row = a.x;
			 	for(int p = first;p<=last; p++)
				{ board [row][p] = c;}   } // used to place ship's car on board if ship in 1 column

		 // placing a ship that's vertical
			 public static void placeShipCharC(char[][] board, Coordinate a, Coordinate b, char c){
				int first = a.x;
				int last = b.x;
				int col = a.y;
				 for(int k = first; k<= last ;k++)
				{ board[k][col]= c; }   } // used to place ship's char on board if ship in 1 row
		
		
			 /*
			 ****************************
			 Determines which quarter of the board our coordinate is in. knowing which quarter of the board we are can 
			 be useful for placing ships, in terms of deciding where to place them, as well as with guiding the firing of missiles. the return value is an integer from 1 to 4 corresponding with the numbers below
			 -----------------------------------
			 |                |                |
			 |                |                |
			 |        1       |        2       |
			 |                |                |
			 |                |                |
			 -----------------------------------                              
			 |                |                |
			 |        3       |        4       |
			 |                |                |
			 |                |                |
			 -----------------------------------

			 each quarter consists of five rows and five columns, EX: 0<= Grid1 Row# <5 , and  0<= Grid1 Col # <5

			 */
			 public static int WhichGridisIt(Coordinate x){
			 	int anchorRow = x.x;
			 	int anchorCol = x.y;
			 	int gridNum;
			 	
			 	if(anchorRow<5){
			 		if(anchorCol<5){gridNum = 1;
			 		return gridNum; }
			 		else{ gridNum = 2;
			 		return gridNum; }
			 	}
			 	else{
			 		if(anchorCol<5){gridNum = 3;
			 		return gridNum; }
			 		else{ gridNum = 4;
			 		return gridNum; }
			 	}
			 }
			 	
			 
			 
			 	// Determines a hypothetical position for the ship being placed	
			 public static Coordinate hypotheticalPosition(Coordinate anchor,  int shipLength, int quarterNum){
			 	
			 	
			 	Coordinate hypothEnd = new Coordinate();
			 	int initRow = anchor.x;
			 	int  initCol = anchor.y;

			 	Random randomGenerator2 = new Random();
			 	
			 	if(quarterNum==1){
			 		boolean placeShipDown = randomGenerator2.nextBoolean();
			 			if(placeShipDown == true){
			 				hypothEnd.x = initRow + (shipLength-1);
			 				hypothEnd.y = initCol;
			 			return hypothEnd;}
			 			else{
			 				hypothEnd.x = initRow;
			 				hypothEnd.y = initCol + (shipLength-1);
			 				return hypothEnd;
			 			}
			 	}
			 	else if(quarterNum==2){
			 		boolean placeShipDown = randomGenerator2.nextBoolean();
			 		if(placeShipDown==true){
			 		hypothEnd.x = initRow +(shipLength-1);
			 		hypothEnd.y = initCol;
			 		return hypothEnd;
			 		}
			 		else{
			 		hypothEnd.x = initRow;
			 		hypothEnd.y = initCol - (shipLength-1);
			 		return hypothEnd;
			 		}
			 	}
			 	else if(quarterNum == 3){
			 		boolean placeShipUp = randomGenerator2.nextBoolean();
			 		if(placeShipUp == true){
			 		hypothEnd.x = initRow -(shipLength-1);
			 		hypothEnd.y = initCol;
			 		return hypothEnd;
			 		}
			 	else{
			 		hypothEnd.x = initRow;
			 		hypothEnd.y = initCol + (shipLength-1);
			 		return hypothEnd;
			 		}
			 	}
			 	else{
			 		boolean placeShipUp = randomGenerator2.nextBoolean();
			 		if(placeShipUp == true){
			 		hypothEnd.x = initRow - (shipLength-1);
			 		hypothEnd.y = initCol;
			 		return hypothEnd;
			 		}
			 		else{
			 		hypothEnd.x = initRow;
			 		hypothEnd.y = initCol - (shipLength-1);
			 		return hypothEnd;
			 		}
			 		
			 		
			 	}

			 }

			 public void printBoard() {
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
	
	/*
	 * ****************
	 *  Firing methods
	 * ****************
	 */
			 
			 
		// randomly looks for a coordinate; this is what I call 'Stalk' mode	 
	public Coordinate fire(){
		Coordinate fire = new Coordinate();
		int fireRow;
		int fireCol;	
		do{
		 fireRow = randomGenerator1.nextInt(10);
		 fireCol = randomGenerator1.nextInt(10);
		fire.x = fireRow;
		fire.y = fireCol;
		} while(HitMiss[fireRow][fireCol]!='~');
		
		return fire;
	}		 
		
	
	
	
	public char fireUpon(Coordinate x) {
		int a = x.x;
		int b = x.y;
		char W;
		if(oppShipPlace[a][b]!='~'){
			W = oppShipPlace[a][b];
			return W;
		}else{W = 'M';
			return W;}
	}
	
	
	public void fireResult(char result){
		if(result=='M'){
			printBoard();
			System.out.println("The computer missed");
		}else{
			printBoard();
			System.out.println("The Computer has gotten a hit!");
		}
		
		
		
	}
	
	
	public boolean lost() {
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

	

} // end of class 
