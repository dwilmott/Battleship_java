
public class Battleship {

public static void main(String[] args){
		char[][] p1BoardA = new char[10][10];
		char[][] p2BoardA = new char[10][10];
		char[][] p1BoardB = new char[10][10];
		char[][] p2BoardB = new char [10][10];
		String[] shipType = {"Attack Cruiser","Battleship","Submarine","Destroyer","Patrol Boat"};
		int[] shipLength = {5,4,3,3,2};
		
		
		initBoard(p1BoardA);
		initBoard(p2BoardA);
		initBoard(p1BoardB);
		initBoard(p2BoardB);
		
		int humanOrComp; // play against a human?
		do{
		System.out.println("Would you like to play against a Human player? Select 0 for Yes, 1 for No");
		humanOrComp = IO.readInt();
		} while(humanOrComp!=1 & humanOrComp !=0);
		
		
		if(humanOrComp==0){

			
			Player P1 = new Player_djw166(p1BoardA, p1BoardB, p2BoardA, p2BoardB, shipType, shipLength);
			Player P2 = new Player_djw166(p2BoardA, p2BoardB, p1BoardA, p1BoardB, shipType, shipLength);
			
			for(int i=1;i<3;i++){
				if(i==1)printBoard(p1BoardA);
				else printBoard(p2BoardA);
				
				System.out.println("Player "+i+", place your ships");
				
				if(i==1)P1.placeShips();
				else P2.placeShips();
			}
			
			

			
			
				for(int yxz = 0;;yxz++){
					
					if(yxz%2==0){ // if yxz is even, and divided by 2, remainder is 0; player 1's turn
					P1.printBoard();
					System.out.println("Player 1, your turn!");
					
					Coordinate guess1 =  P1.fire();
					char result1 = P1.fireUpon(guess1);
					char[][] temp1 = p1BoardB;
					updateBoard(guess1,temp1,result1);
					P1.fireResult(result1);
					
					if(result1=='M'); // what happens after result is determined? if a miss, loop ends
					else{ boolean P1win;
					P1win = P2.lost();
						if(P1win==true){
						System.out.println("Player 1 wins! Game is over");
						break;}
						}
					
					
					}
					else{ // else, player 2's turn 
					P2.printBoard();
					System.out.println("Player 2, your turn!");
					
					Coordinate guess2 = P2.fire();
					char result = P2.fireUpon(guess2);
					char[][] temp = p2BoardB;
					updateBoard(guess2,temp,result);
					P2.fireResult(result);
					
					if(result=='M'); // what happens after result is determined? if a miss, loop ends
					else{ boolean P2win;
					P2win = P1.lost();
						if(P2win==true){
						System.out.println("Player 2 wins! Game is over");
						break;}
						}
					
					
					}
				}
			
		} // end of human vs. human loop
		else{
			
			int easyOrHardAI; // play against a hard or easy computer??
			do{
			System.out.println("Would you like to play against an easy or hard computer player? press 0 for easy, 1 for hard");
			easyOrHardAI = IO.readInt();
			}while(easyOrHardAI!=0 & easyOrHardAI!=1);
			
			if(easyOrHardAI==0){ // we have chosen the easy AI

				
				Player P1 = new Player_djw166(p1BoardA, p1BoardB, p2BoardA, p2BoardB, shipType, shipLength);
				Player P2 = new EasyAIPlayer_djw166(p2BoardA, p2BoardB, p1BoardA, p1BoardB, shipType, shipLength);
				
				for(int i=1;i<3;i++){
					if(i==1)printBoard(p1BoardA);
					
					System.out.println("Player "+i+", place your ships");
					
					if(i==1)P1.placeShips();
					else P2.placeShips();
					if(i==2){
						System.out.println("The ships for the computer have all been placed");
					}
				}
				
				

				
				
					for(int yxz = 0;;yxz++){
						
						if(yxz%2==0){ // if yxz is even, and divided by 2, remainder is 0; player 1's turn
						P1.printBoard();
						System.out.println("Player 1, your turn!");
						
						Coordinate guess1 =  P1.fire();
						char result1 = P1.fireUpon(guess1);
						char[][] temp1 = p1BoardB;
						updateBoard(guess1,temp1,result1);
						P1.fireResult(result1);
						
						if(result1=='M'); // what happens after result is determined? if a miss, loop ends
						else{ boolean P1win;
						P1win = P2.lost();
							if(P1win==true){
							System.out.println("Player 1 wins! Game is over");
							break;}
							}
						
						
						}
						else{ // else, player 2's turn 
						System.out.println("Player 2, your turn!");
						
						Coordinate guess2 = P2.fire();
						char result = P2.fireUpon(guess2);
						char[][] temp = p2BoardB;
						updateBoard(guess2,temp,result);
						P2.fireResult(result);
						
						if(result=='M')continue; // what happens after result is determined? if a miss, loop ends
						else{ boolean P2win;
						P2win = P1.lost();
							if(P2win==true){
							System.out.println("Player 2 wins! Game is over");
							break;}
							}
						
						
						}
					} // end of game loop
			}// end of Battleship version against an Easy AI Player
			else{

				
				Player P1 = new Player_djw166(p1BoardA, p1BoardB, p2BoardA, p2BoardB, shipType, shipLength);
				Player P2 = new HardAIPlayer_djw166(p2BoardA, p2BoardB, p1BoardA, p1BoardB, shipType, shipLength);
				
				for(int i=1;i<3;i++){
					if(i==1)printBoard(p1BoardA);
					
					System.out.println("Player "+i+", place your ships");
					
					if(i==1)P1.placeShips();
					else P2.placeShips();
				}
				
				

				
				
					for(int yxz = 0;;yxz++){
						
						if(yxz%2==0){ // if yxz is even, and divided by 2, remainder is 0; player 1's turn
						P1.printBoard();
						System.out.println("Player 1, your turn!");
						
						Coordinate guess1 =  P1.fire();
						char result1 = P1.fireUpon(guess1);
						char[][] temp1 = p1BoardB;
						updateBoard(guess1,temp1,result1);
						P1.fireResult(result1);
						
						if(result1=='M'); // what happens after result is determined? if a miss, loop ends
						else{ boolean P1win;
						P1win = P2.lost();
							if(P1win==true){
							System.out.println("Player 1 wins! Game is over");
							break;}
							}
						
						
						}
						else{ // else, player 2's turn 
						System.out.println("Player 2's turn!");
						boolean Necessary;
						Coordinate actualGuess = new Coordinate();
						
						// we enter MaimKill mode if we already hit a target last round...
						if(((HardAIPlayer_djw166) P2).numPrevHits>0){ // if we've previously hit a target on the round before
							actualGuess = ((HardAIPlayer_djw166) P2).MaimKill(((HardAIPlayer_djw166) P2).hitMarker);
						}
						else{ // if we haven't hit a target previously, then we enter STALK mode
						do{  // randomly finds a spot on the board, checks if it needs to look here b/c we've searched the area within two space of this square
						Coordinate guess2 = P2.fire();
						actualGuess.x = guess2.x;
						actualGuess.y = guess2.y;
						Necessary = ((HardAIPlayer_djw166) P2).needToSearchHere(guess2);
						}while(Necessary==false);
						}
						
						char result = P2.fireUpon(actualGuess);
						char[][] temp = p2BoardB;
						updateBoard(actualGuess,temp,result);
						P2.fireResult(result);
						
						if(result=='M'); // what happens after result is determined? if a miss, loop ends
						else{ boolean P2win;
						P2win = P1.lost();
							if(P2win==true){
							System.out.println("Player 2 wins! Game is over");
							break;}
							}
						
						
						}
					}
			}
			
			
		}// end of human vs. computer loop
			
		
		
}// end of main method





public static void initBoard(char[][] board){
		for(int i=0;i<board.length;i++){
			for(int j=0;j<board[i].length;j++){
			board[i][j] = '~';}
		  }
		 }

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


public static void updateBoard(Coordinate x,char [][] board,char r){
	int e = x.x;
	int d = x.y;
	board[e][d]= r;
	}


}
