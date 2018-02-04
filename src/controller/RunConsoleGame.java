package controller;

import java.util.Scanner;

import model.Map;

public class RunConsoleGame {

  private static Scanner sc;
  private static Map theGame;

  public static void main(String[] args) {
	 theGame = new Map();

	 //directions the player is gonna move
	 String north = "n";
	 String south = "s";
	 String west = "w";
	 String east = "e";
	 String arrow = "arrow";

	 //objects in the game
	 String wumpus = "W";
	 String blood = "B";
	 String slime = "S";
	 String pit = "P";
	 String goop = "G";

	 String direction;
	 String gameLogic = "";
	 String message = "";
	 
	 //scanner the next direction and act accordingly
	 while (true) {

		if(!(message.equals(""))) {
		System.out.print("\n"+theGame.toString());
		  System.out.print(message);
		}
		else {
		  System.out.print(theGame.toString());
		  System.out.print(message);
		}
		
		System.out.print("Move (n, e, s, w, arrow)?");

		sc = new Scanner(System.in);
		direction=sc.next();

		//hunter moved north
		if(direction.equals(north)) {
		  gameLogic = Character.toString(theGame.hunterMove(1, 0));
		}//hunter moved south
		else if(direction.equals(south)) {
		  gameLogic = Character.toString(theGame.hunterMove(2, 0));
		}//hunter moved west
		else if(direction.equals(west)) {
		  gameLogic = Character.toString(theGame.hunterMove(3, 0));
		}//hunter moved east
		else if(direction.equals(east)) {
		  gameLogic = Character.toString(theGame.hunterMove(4, 0));
		}//hunter shot an arrow
		else if(direction.equals(arrow)) {
		  System.out.print("Shoot (n, e, s, w)?");
		  sc = new Scanner(System.in);
		  direction = sc.next();
		  //hunter shot an arrow north
		  if(direction.equals(north)) {
			 gameLogic = Character.toString(theGame.hunterMove(5, 1));
		  }//hunter shot an arrow south
		  else if(direction.equals(south)) {
			 gameLogic = Character.toString(theGame.hunterMove(5, 2));
		  }//hunter shot an arrow west
		  else if(direction.equals(west)) {
			 gameLogic = Character.toString(theGame.hunterMove(5, 3)); 
		  }//hunter shot an arrow east
		  else if(direction.equals(east)) {
			 gameLogic = Character.toString(theGame.hunterMove(5, 4));
		  }
		  else {
			 System.out.print("\n\n Try shooting n, e, s or w");
			 continue;
		  }

		  System.out.print("\n\n");
		  //he shot an arrow, is he dead or victorious?
		  if(gameLogic.equals(wumpus)) {
			 System.out.print("Your arrow hit the wumpus. You win.\n\n");
		  }
		  else {
			 System.out.print("You just shot yourself. You lose.\n\n");
		  }
		  break;
		}
		else {
		  System.out.print("\n Try entering n, e, s, w or arrow\n\n");
		  continue;
		}

		if(gameLogic.equals(wumpus)) {
		  System.out.print(theGame.toString());
		  System.out.print("\nYou walked into the Wumpus. You lose\n\n");
		  break;
		}
		else if(gameLogic.equals(pit)) {
		  System.out.print(theGame.toString());
		  System.out.print("\nYou fell down a bottomless pit. You lose.\n\n");
		  break;
		}
		else if(gameLogic.equals(blood)) {
		  message = "I smell something foul\n\n";
		}
		else if(gameLogic.equals(slime)) {
		  message = "I can hear the wind\n\n"; 
		}
		else if(gameLogic.equals(goop)) {
		  message = "I can hear the wind\n\n" + "I smell something foul\n\n";
		}
		else {
		  System.out.print("\n\n");
		  message = "";
		  continue;
		}
	 }
	 sc.close();

  }
}
