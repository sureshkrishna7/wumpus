package model;

import java.util.Observable;

/**
 * This class Map
 * Author: Suresh Krishna Devendran
 */
public class Map extends Observable {
  private char[][] board;
  private boolean[][] visible;
  private int size;
  private Wumpus prey;
  private HunterPlayer hunter;
  private Traps pit;
  private boolean didGameEnd;

  //the map of the game
  public Map() {
	 didGameEnd = false;
	 size = 12;
	 board = new char[size][size];
	 visible = new boolean[size][size];
	 initializeBoard();
	 prey = new Wumpus(this);
	 prey.setPosition();
	 pit = new Traps(this);
	 pit.setPosition();
	 hunter = new HunterPlayer(this);
	 hunter.setPosition();
	 setChanged();
	 notifyObservers();
  }
  
  /**
   * Start a new game and tell all observers to draw an new game
   * with the string message startNewGame()
   */
  public void startNewGame() {
	 didGameEnd = false;
	 size = 12;
	 //board = new char[size][size];
	 //visible = new boolean[size][size];
	 initializeBoard();
	 //prey = new Wumpus(this);
	 prey.setPosition();
	 //pit = new Traps(this);
	 pit.setPosition();
	 //hunter = new HunterPlayer(this);
	 hunter.setPosition();
	 // The state of this model just changed so tell any observer to update themselves
	 setChanged();
	 notifyObservers("startNewGame()");
  }


  //initialize the board with X's
  private void initializeBoard() {
	 for (int r = 0; r < size; r++) {
		for (int c = 0; c < size; c++) {
		  board[r][c] = 'X';
		  visible[r][c] = false;
		}
	 }
  }

  //returns the hunter object
  public HunterPlayer getHunterObject() {
	 return this.hunter;
  }
  
  //returns the entire game board
  public char[][] getMapBoard(){
	 return this.board;
  }
  
  //returns the entire visibility board
  public boolean[][] getVisibleBoard() {
	 return this.visible;
  }

  //return an array of two elements/ above the current provided position
  public int[] up(int row, int col) {
	 int[] move = new int[2];

	 //wrap around on row 0
	 if(row == 0) {
		move[0] = 11;
		move[1] = col;
	 }
	 else {
		move[0] = row - 1;
		move[1] = col;
	 }
	 return move;
  }

  //return an array of two elements/ below the current provided position
  public int[] down(int row, int col) {
	 int[] move = new int[2];
	 //wrap around on row 11
	 if(row == 11) {
		move[0] = 0;
		move[1] = col;
	 }
	 else {
		move[0] = row + 1;
		move[1] = col;
	 }
	 return move;
  }

  //return an array of two elements/ left of the current provided position
  public int[] left(int row, int col) {
	 int[] move = new int[2];

	 if(col == 0) {
		move[0] = row;
		move[1] = 11;
	 }
	 else {
		move[0] = row;
		move[1] = col - 1;
	 }
	 return move;
  }

  //return an array of two elements/ right of the current provided position
  public int[] right(int row, int col) {
	 int[] move = new int[2];

	 if(col == 11) {
		move[0] = row;
		move[1] = 0;
	 }
	 else {
		move[0] = row;
		move[1] = col + 1;
	 }
	 return move;
  }

  //set that the game ended
  public void gameEnded() {
	 didGameEnd = true;
  }

  //check if the game is still running, true if its running and false if it ended
  public boolean gameStillRunning() {
	 return !(didGameEnd);
  }

  //method called whenever the hunter moves
  public char changeHunterPos(int newR, int newC) {
	 setVisibility(newR, newC);
	 return board[newR][newC];

  }

  //sets the spot of the character in the board
  public boolean setSpot(int row, int col, char fer) {

	 //if an empty spot set as it is
	 if(board[row][col] == 'X') {
		this.board[row][col] = fer;
	 }
	 else if (board[row][col] == 'B') {
		//if there is both blood and slime, set the spot to goop
		this.board[row][col] = (fer == 'S') ? 'G' : fer;
	 }
	 else if(board[row][col] == 'S') {
		//if there is both blood and slime, set the spot to goop
		this.board[row][col] = (fer == 'B') ? 'G' : fer;
	 }
	 else if(board[row][col] == 'G') {
		this.board[row][col] = fer;
	 }//then its either 'W' or 'P', can't set in those places
	 else {
		return false;
	 }
	 return true;
  }

  //checks for an empty spot
  public boolean available(int r, int c) {
	 // checks if the availabel 
	 if(board[r][c] == 'X' || board[r][c] == 'B' || board[r][c] == 'S' || board[r][c] == 'G') {
		return true;
	 }
	 return false;
  }

  //set the visibility of that cave
  public void setVisibility(int r, int c) {
	 this.visible[r][c] = true;
  }

  //prints the board as a string and checks for visibility of the caves when doing so
  @Override
  public String toString(){
	 String result = "";
	 for (int r = 0; r < size; r++) {
		for (int c = 0; c < size; c++) {
		  if(hunter.getHunterRow() == r && hunter.getHunterCol() == c) {
			 result += " "+"O"+" ";
			 continue;
		  }
		  if(visible[r][c]) {
			 result += " ";
			 result += (board[r][c] == 'X') ? ' ' : board[r][c];
			 result += " ";
		  }
		  else {
			 //To DEBUG 
			 //result += " "+board[r][c]+" ";
			 result += " " +'X'+" ";
		  }
		}
		result += "\n";
	 }
	 return result;
  }

  public String toEndGameString(){
	 String result = "";
	 for (int r = 0; r < size; r++) {
		for (int c = 0; c < size; c++) {
		  if(hunter.getHunterRow() == r && hunter.getHunterCol() == c) {
			 result += " "+"O"+" ";
			 continue;
		  }
		  if(visible[r][c]) {
			 result += " ";
			 result += (board[r][c] == 'X') ? ' ' : board[r][c];
			 result += " ";
		  }
		  else {
			 result += " ";
			 result += (board[r][c] == 'X') ? '_' : board[r][c];
			 result += " ";
		  }

		}
		result += "\n";
	 }
	 return result;
  }



  //whenever the hunter makes a move, either he walks in a direction or he shoots an arrow
  //returns the character found in the area where he moves to
  //also checks if the arrow shot, hit the wumpus or not
  public char hunterMove(int mo, int dir) {
	 int[] jfk = new int[2];
	 jfk = hunter.getHunterPosition();
	 int[] sec = new int[2];
	 //move hunter north

	 if(mo == 1) {
		sec = up(jfk[0], jfk[1]);
		hunter.move(sec[0], sec[1]);
		setChanged();
		notifyObservers();
		return changeHunterPos(sec[0], sec[1]);
	 }
	 //move hunter south
	 else if(mo == 2) {
		sec = down(jfk[0], jfk[1]);
		hunter.move(sec[0], sec[1]);
		setChanged();
		notifyObservers();
		return changeHunterPos(sec[0], sec[1]);
	 }
	 //move hunter west
	 else if(mo == 3) {
		sec = left(jfk[0], jfk[1]);
		hunter.move(sec[0], sec[1]);
		setChanged();
		notifyObservers();
		return changeHunterPos(sec[0], sec[1]);
	 }
	 //move hunter east
	 else if(mo == 4) {
		sec = right(jfk[0], jfk[1]);
		hunter.move(sec[0], sec[1]);
		setChanged();
		notifyObservers();
		return changeHunterPos(sec[0], sec[1]);
	 }
	 //hunter just shot an arrow
	 else {
		if(dir == 1) {
		  sec = arrowFired(jfk[0], jfk[1], dir);
		}
		else if(dir == 2) {
		  sec = arrowFired(jfk[0], jfk[1], dir);
		}
		else if(dir == 3) {
		  sec = arrowFired(jfk[0], jfk[1], dir);
		}
		else {
		  sec = arrowFired(jfk[0], jfk[1], dir);
		}
		setChanged();
		notifyObservers();
		return changeHunterPos(sec[0], sec[1]);
	 }
  }

  //wraparound arrow travelling in full speed
  private int[] arrowFired(int row, int col, int dir) {
	 int[] move = new int[2];

	 //if the arrow was shot north or south
	 if(dir == 1 || dir == 2) {
		//col is fixed, just check if there is a wumpus in that col
		if(col == prey.getWumpusCol()) {
		  move[0] = prey.getWumpusRow();
		  move[1] = prey.getWumpusCol();
		}
		else {
		  move[0] = hunter.getHunterRow();
		  move[1] = hunter.getHunterCol();
		}
	 }//if arrow shot in west or east
	 else if(dir == 3 || dir == 4) {
		//row is fixed. just check if there is a wumpus in that row
		if(row == prey.getWumpusRow()) {
		  move[0] = prey.getWumpusRow();
		  move[1] = prey.getWumpusCol();
		}
		else {
		  move[0] = hunter.getHunterRow();
		  move[1] = hunter.getHunterCol();
		}
	 }

	 return move;
  }

  //when the game begins, set the hunter position at the correct non dangerous spot
  public boolean initialHunterPos(int r, int c) {
	 //checks if its an empty spot
	 if(board[r][c] == 'X') {
		return true;
	 }
	 return false;
  }
}