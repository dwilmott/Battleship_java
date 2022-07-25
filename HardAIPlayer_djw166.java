
import java.util.Random;

public class HardAIPlayer_djw166 implements Player {
	/*
	This AI player differs from the Easy AI player in several ways:
	1) during Placement of Ships, Hard AI player will NOT place ships next to each other
			Easy AI DOESN'T CHECK FOR THIS	
	2) DURING FIRE MODE...	
		we have two versions of fire mode, Stalk mode and MaimKill mode(after a hit has been declared)
		*Easy AI player searches randomly throughout the board during Stalk mode
			**it won't take into account whether it has hit a ship last turn or not
		*Hard AI Player searches randomly around board BUT only searches spots two spaces away from a miss
			** takes into account that you don't need to search every single square to locate every ship
			** it HAS a MaimKill mode, where it remembers if it got a hit on the previous turn or not
				***it will fire around the hit square randomly 
			NOTE: this MaimKill mode isn't perfect; for instance, if there are 2 consecutive hits we can
			narrow our targets even more because we know if the ship is placed horizontally or vertically.
			This sentiment isn't lost on me but I was unable to come up with a reliable way to do this as of the submission of this assignment.
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

// used for MaimKIll mode...
int numPrevHits;
Coordinate hitMarker = new Coordinate();


// constructor that takes in Boards, type of ship, ship length for use in class 
public HardAIPlayer_djw166(char[][] pBoardA,char[][] pBoardB, char [][] oBoardA, char[][] oBoardB, String[] TypeShip, int[] LengthShip){

shipPlace = pBoardA;
HitMiss = pBoardB;
oppShipPlace = oBoardA;
oppHitMiss = oBoardB;
ShipType = TypeShip;
ShipLength = LengthShip;


}


/*
 * 
 * THese methods below are used
 * for firing upon a ship
 */


	@Override
	public char fireUpon(Coordinate x) {
		int a = x.x;
		int b = x.y;
		char W;
		if(oppShipPlace[a][b]!='~'){
			W = oppShipPlace[a][b];
			hitMarker.x = a;
			hitMarker.y = b;
			return W;
			
		}
		else{W = 'M';
			return W;
		}
	}

	@Override //******* different from human player
	public Coordinate fire() {
		Coordinate fire = new Coordinate();
		int fireRow;
		int fireCol;	
		if(numPrevHits>0){
			
		}
		else{
		do{
		 fireRow = randomGenerator1.nextInt(10);
		 fireCol = randomGenerator1.nextInt(10);
		fire.x = fireRow;
		fire.y = fireCol;
		} while(HitMiss[fireRow][fireCol]!='~');
		}
		return fire;
	}

	public boolean needToSearchHere(Coordinate search){
	
	if(search.x-2<0){
		if(HitMiss[search.x+2][search.y]=='H')return false;
		else if(HitMiss[search.x][search.y-2]=='H') return false;
		else if(HitMiss[search.x][search.y+2]=='H') return false;
		else return true; }
	else if(search.x+2>9){
		if(HitMiss[search.x-2][search.y]=='H') return false;
		else if(HitMiss[search.x][search.y-2]=='H') return false;
		else if(HitMiss[search.x][search.y+2]=='H') return false;
		else return true;
	}
	else if(search.y-2<0){
		if(HitMiss[search.x-2][search.y]=='H') return false;
		else if(HitMiss[search.x+2][search.y]=='H')return false;
		else if(HitMiss[search.x][search.y+2]=='H') return false;
		else return true;
	}
	else if(search.y+2>9){
		if(HitMiss[search.x-2][search.y]=='H') return false;
		else if(HitMiss[search.x+2][search.y]=='H')return false;
		else if(HitMiss[search.x][search.y-2]=='H') return false;
		else return true;
	}
	else{	
		if(HitMiss[search.x-2][search.y]=='H') return false;
		else if(HitMiss[search.x+2][search.y]=='H')return false;
		else if(HitMiss[search.x][search.y-2]=='H') return false;
		else if(HitMiss[search.x][search.y+2]=='H') return false;
		else return true;
	}
		
		
	}
	
	
	
	public Coordinate MaimKill(Coordinate prevHit){
		Coordinate next = new Coordinate();
		boolean checkAgain=false;
		
		
		do{
		
		int whichDirection = randomGenerator1.nextInt(4);
		
		
		if(whichDirection==0 ){
			if(prevHit.x==0||HitMiss[prevHit.x-1][prevHit.y]!='~'){
				checkAgain=true;
			}
			else{next.x = prevHit.x-1;
			next.y = prevHit.y;
			return next;}
		}
		else if(whichDirection==1){
			if(prevHit.x==9||HitMiss[prevHit.x+1][prevHit.y]!='~'){
				checkAgain=true;
			}
			else{next.x = prevHit.x+1;
			next.y = prevHit.y;
			return next;}
		}
		else if(whichDirection==2){
			if(prevHit.y==0||HitMiss[prevHit.x][prevHit.y-1]!='~'){
				checkAgain= true;
			}
			else{next.x = prevHit.x;
			next.y = prevHit.y-1;
			return next;}
		}
		else{
			if(prevHit.y==9||HitMiss[prevHit.x][prevHit.y+1]!='~'){
				checkAgain=true;
			}
			else{next.x = prevHit.x;
			next.y = prevHit.y+1;
			return next;}	
		}
		} while(checkAgain==true);
		
		return next;
		
	}
	
	
	
	
	public void fireResult(char result){
		if(result=='M'){
			System.out.println("The Computer missed");
			numPrevHits =0;
		}else{
			System.out.println("The Computer has gotten a hit!");
			numPrevHits++;
		}
		
	}
	
	
	
	

	@Override
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
	 * Methods below are 
	 * Used for placement of ship
	 */
	

public void placeShips(){
	

	
for(int a = 0;a<5;a++){

int counter = ShipLength[a];	
	
char PlaceMarker = ShipType[a].charAt(0);

boolean isThereAShip; // local variable to declare method for placing ships
boolean isShipClose; // local variable to declare method for Difficult AI player 



/*
this first do...while loop helps to determine whether a ship, after creating a 'hypothetical coordinate',
and also after determining the placement of the ship won't occupy another ship's space, is at least one square away from any other ships on the grid. IF it is not, then it chooses a new random 'anchor coordinate' to begin the process until the algorithm is met.
*/
Coordinate realAnchorSquare = new Coordinate();
Coordinate chosenCoordinate = new Coordinate();

do{
int initialRow = randomGenerator1.nextInt(10); // randomly generates a row # to choose for 'anchor' square
int initialCol = randomGenerator1.nextInt(10); // randomly generates a column # to choose for 'anchor' square


Coordinate anchorSquare = new Coordinate(initialRow, initialCol);
	
/*
 * this first do...while loop is for the difficult setting...
 * detects if any ships have been placed around the proposed new ship placement
 * 
 */
	do{
		int GridNum = WhichGridisIt(anchorSquare);
	Coordinate hypothCoordinate = hypotheticalPosition(anchorSquare, counter, GridNum);
	
	anchorSquare.x = Math.min(anchorSquare.x,hypothCoordinate.x); // these commands guarantee the anchor square always contains the smallest row
	anchorSquare.y = Math.min(anchorSquare.y, hypothCoordinate.y); // or column we need for placing ships. it just helps with the design of the loops.
	hypothCoordinate.x = Math.max(anchorSquare.x, hypothCoordinate.x); 
	hypothCoordinate.y = Math.max(anchorSquare.y,hypothCoordinate.y);
			
			
	isThereAShip = HardAIPlayer_djw166.isThereAlreadyAShip(shipPlace,anchorSquare,hypothCoordinate);
	isShipClose = HardAIPlayer_djw166.isAShipClose(shipPlace,anchorSquare, hypothCoordinate, counter);
	
	
	realAnchorSquare.x = anchorSquare.x;
	realAnchorSquare.y = anchorSquare.y;
	chosenCoordinate.x = hypothCoordinate.x;
	chosenCoordinate.y = hypothCoordinate.y; 
	} while(isThereAShip==true); 
	/*
 // this second do..while.. guarantees that the placement of the ship is correct; there are only two possible choices for it to choose, thanks to the grid algorithm employed by hypotheticalCoordinate() (details below)
 It may run several times until the random effect generates a binary signal and creates another hypothetical situation; but it will eventually choose the second position 
 */
}
while(isShipClose==false);

	int rowDiff = realAnchorSquare.x-chosenCoordinate.x;

	if(rowDiff==0){ // then the ship is being placed on 1 row
		placeShipCharR(shipPlace,realAnchorSquare,chosenCoordinate,PlaceMarker);
	}
	else{ ///... the ship is being placed in 1 column 
		placeShipCharC(shipPlace,realAnchorSquare,chosenCoordinate,PlaceMarker);
	}

	
} // end of 1 iteration of for loop; runs five times, 1 for each ship


} // end of method placeShips()

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
	
	// used to determine if a ship is within one square of hypothetical placement. This method is ***exclusive*** to the difficult AI player.
	
	
	// ******* adjust for whether the search algorithm tries to search off map (when ship is placed on the edges of the board)
	public static boolean isAShipClose(char[][] shipPlace, Coordinate front, Coordinate back, int shipLength){
	int frontRow = front.x;
	int frontCol = front.y;
	int backRow = back.x;
	int backCol = back.y;
	
	if(frontRow-backRow==0){ // then the ship is going on 1 row
	
		if(frontRow==0){ // if the ship is placed horizontally on row 0
			if(shipPlace[frontRow][frontCol-1]!='~') return false;
			if(shipPlace[backRow][backCol+1]!='~') return false;
			for(int e=frontCol;e<=backCol ;e++){
				if(shipPlace[frontRow+1][e]!='~') return false;
			}
		}
		else if(frontRow==9){ // if the ship is placed horizontally on row 9
		if(shipPlace[frontRow][frontCol-1]!='~') return false;
			if(shipPlace[backRow][backCol+1]!='~') return false;
			for(int e=frontCol;e<=backCol ;e++){
				if(shipPlace[frontRow-1][e]!='~')return false;
			}
		}
		else if(frontCol==0){ // if the ship is placed with it's front coordinate at column 0
			if(shipPlace[backRow][backCol+1]!='~') return false;
			for(int e=frontCol;e<=backCol ;e++){
				if(shipPlace[frontRow-1][e]!='~')return false;
				if(shipPlace[frontRow+1][e]!='~') return false;
			}
		}
		else if(backCol==9){ // if the ship is placed with it's back coordinate at column 9
		if(shipPlace[frontRow][frontCol-1]!='~') return false;
			for(int e=frontCol;e<=backCol ;e++){
				if(shipPlace[frontRow-1][e]!='~')return false;
				if(shipPlace[frontRow+1][e]!='~') return false;
			}
		}
		else{
			if(shipPlace[frontRow][frontCol-1]!='~') return false;
			if(shipPlace[backRow][backCol+1]!='~') return false;
			for(int e=frontCol;e<=backCol ;e++){
				if(shipPlace[frontRow-1][e]!='~')return false;
				if(shipPlace[frontRow+1][e]!='~') return false;
			}
		}
			return true;
	}
	
	else{ // the ship is going on 1 column
		
		if(frontCol==0){ //if the ship is placed vertically in column 0
			if(shipPlace[frontRow-1][frontCol]!='~') return false;
			if(shipPlace[backRow+1][backCol]!='~') return false;
			for(int d =frontRow;d<=backRow;d++){
			if(shipPlace[d][frontCol+1]!='~') return false;
			} //....we don't search for an empty space to the left of it
		}
		else if(frontCol==9){ // if the ship is placed vertically in column 9
			if(shipPlace[frontRow-1][frontCol]!='~') return false;
			if(shipPlace[backRow+1][backCol]!='~') return false;
			for(int d =frontRow;d<=backRow;d++){
			if(shipPlace[d][frontCol-1]!='~') return false;
			} //... we don't search for an empty space to the right of it
		}
		else if(frontRow==0){ // if the ship is placed so the first row # is 0
			if(shipPlace[frontRow+1][backCol]!='~') return false;
			for(int d =frontRow;d<=backRow;d++){
			if(shipPlace[d][frontCol-1]!='~') return false;
			if(shipPlace[d][frontCol+1]!='~') return false;
			} // .... we don't search for an empty space above it
		}
		else if(backRow==9){ // if the ship is placed so the last row # is 9
			if(shipPlace[frontRow-1][frontCol]!='~') return false;
			if(shipPlace[backRow+1][backCol]!='~') return false;
			for(int d = frontRow;d<=backRow;d++){
			if(shipPlace[d][frontCol-1]!='~') return false;
			if(shipPlace[d][frontCol+1]!='~') return false;
			}
		} // .... we don't search for an empty space below it 
		
		else{ // else, there are open spaces around the whole hypothetical area
			if(shipPlace[frontRow-1][frontCol]!='~') return false;
			if(shipPlace[backRow+1][backCol]!='~') return false;
			for(int d =frontRow;d<=backRow;d++){
			if(shipPlace[d][frontCol-1]!='~') return false;
			if(shipPlace[d][frontCol+1]!='~') return false;
			}
		}
	return true;
	}
	
	}
	
	
	
	
	
	// ***** Used for placement of ships in placeShips() 
	
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
	
}
