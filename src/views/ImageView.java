package views;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.HunterPlayer;
import model.Map;
//import javafx.scene.input.MouseEvent;



/**
 * This class ImageView
 * Author: Suresh Krishna Devendran
 */

public class ImageView extends BorderPane implements Observer {

  private Map theGame;
  private static char[][] bor;
  private static boolean[][] vis;
  private static HunterPlayer hun;
  private Scene sc;
  private static Stage st;

  private static Canvas canvas;
  private static GraphicsContext gc;
  //private int scrx;
  //private int scry;

  private GridPane directions;
  private GridPane fields;
  private Button north;
  private Button south;
  private Button east;
  private Button west;

  //initializes the board and other parameters
  public ImageView(Map theGameMap, Scene scene, Stage stage) {

	 //copying the entire game, hunter, board and visibility board
	 theGame = theGameMap;
	 bor = theGame.getMapBoard();
	 hun = theGame.getHunterObject();
	 vis = theGame.getVisibleBoard();
	 sc = scene;
	 st = stage;

	 // Create the Canvas
	 canvas = new Canvas(480, 480);

	 //grid pane with the arrow direction button
	 directions = new GridPane();
	 fields = new GridPane();
	 fields.setStyle("-fx-background-color: black");


	 // Get the graphics context of the canvas
	 gc = canvas.getGraphicsContext2D();

	 north = new Button("N");
	 //north.setText("N");
	 south = new Button("S");
	 //south.setText("S");
	 east = new Button("E");
	 //east.setText("E");
	 west = new Button("W");
	 //west.setText("W");

	 initializePanel();  
  }
  
  public static Stage getStage() {
	 return st;
  }

  private void initializePanel() {

	 //drawing the empty canvas
	 gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

	 //drawing the initial board
	 drawBoard();

	 //adding the buttons to the grid pane
	 directions.add(north, 2, 2);
	 directions.add(west, 1, 3);
	 directions.add(east, 3, 3);
	 directions.add(south, 2, 4);

	 //adding the canvas and button grind pane to another grid pane
	 fields.add(canvas, 1, 1, 3, 2);
	 fields.add(directions, 1, 2);
	 BorderPane.setAlignment(fields, Pos.CENTER_LEFT);
	 BorderPane.setMargin(fields, new Insets(10, 40, 10, 40));
	 this.setCenter(fields);


	 BorderPane.setAlignment(directions, Pos.BOTTOM_LEFT);
	 BorderPane.setMargin(directions, new Insets(0, 10, 60, 230));
	 this.setBottom(directions);

	 //action event for all the buttons in the GUI
	 ButtonListener handler = new ButtonListener();
	 north.setOnAction(handler);
	 south.setOnAction(handler);
	 west.setOnAction(handler);
	 east.setOnAction(handler);

	 //arrow key event handler
	 sc.setOnKeyReleased(new KeyListener());

	 //DEBUG: Used to debug the drawing coordinates
	 //EventHandler<MouseEvent> hand = new mouseListener();
	 //canvas.setOnMouseReleased(hand);

	 //calls to draw the up to date game progress
	 updateButtons();
  }

  //arrow key event handler
  private class KeyListener implements EventHandler<KeyEvent>  {

	 @Override
	 public void handle(KeyEvent event) {
		//System.out.println(event.getCode());
		if(theGame.gameStillRunning()) {
		  if(KeyCode.UP == event.getCode()) {
			 theGame.hunterMove(1, 0);
			 // System.out.println("North");
		  }
		  else if(KeyCode.RIGHT == event.getCode()) {
			 theGame.hunterMove(4, 0);
			 //System.out.println("East");
		  }
		  else if(KeyCode.DOWN == event.getCode()) {
			 theGame.hunterMove(2, 0);
			 //System.out.println("South");
		  }
		  else if(KeyCode.LEFT== event.getCode()) {
			 theGame.hunterMove(3, 0);
			 //System.out.println("West");
		  }
		  updateButtons();
		}  
	 }
  }

  //DEBUG: Used to debug the drawing coordinates
  /*
  private class mouseListener implements EventHandler<MouseEvent> {

	 @Override
	 public void handle(MouseEvent event) {

		scrx = (int) event.getX();
		scry = (int) event.getY();

		System.out.println("X ="+scrx);
		System.out.println("Y ="+scry);
	 }
  }
   */

  //inital board with one location for the hunter
  private static void drawBoard() {
	 Image grnd = new Image("file:images/Ground.png", 40, 40, false, false);
	 for (int r = 0; r < 12; r++) {
		for (int c = 0; c < 12; c++) {
		  gc.drawImage(grnd, c*40, r*40);
		}
	 }
	 gc.clearRect((hun.getHunterCol())*40, (hun.getHunterRow())*40, 40, 40);
  }


  //action event for all the buttons in the GUI
  private class ButtonListener implements EventHandler<ActionEvent> {

	 @Override
	 public void handle(ActionEvent event) {
		String text = ((Button) event.getSource()).getText();
		char temp = 'C';
		if(theGame.gameStillRunning()) {
		  if(text.equals("N")) {
			 //System.out.println("Nor");
			 temp = theGame.hunterMove(5, 1);
		  }
		  else if (text.equals("S")) {
			 //System.out.println("Sou");
			 temp = theGame.hunterMove(5, 2);
		  }
		  else if (text.equals("W")) {
			 //System.out.println("Wes");
			 temp = theGame.hunterMove(5, 3);
		  }
		  else if (text.equals("E")) {
			 //System.out.println("Eas");
			 temp = theGame.hunterMove(5, 4);
		  }

		  if(temp == 'W') {
			 st.setTitle("Your arrow hit the wumpus. You win.");
			 updateLast();
			 theGame.gameEnded(0);
		  }
		  else {
			 st.setTitle("You just shot yourself. You lose.");
			 updateLast();
			 theGame.gameEnded(1);
		  }
		}
	 }

  }

  //Update the board for the LAST time showing all the hazards and warnings
  public static void updateLast() {

	 //clear the canvas
	 gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	 drawBoard();

	 //getting all the images of the objects
	 Image hunter = new Image("file:images/TheHunter.png", 40, 40, false, false);
	 Image grnd = new Image("file:images/Ground.png", 40, 40, false, false);
	 Image blood = new Image("file:images/Blood.png", 40, 40, false, false);
	 Image goop = new Image("file:images/Goop.png", 40, 40, false, false);
	 Image slime = new Image("file:images/Slime.png", 40, 40, false, false);
	 Image slPit = new Image("file:images/SlimePit.png", 40, 40, false, false);
	 Image wumpus = new Image("file:images/Wumpus.png", 40, 40, false, false);

	 //iterating over the entire game board and printing out all the correct objects
	 for (int r = 0; r < 12; r++) {
		for (int c = 0; c < 12; c++) {
		  //if its a hunter position
		  if(r == hun.getHunterRow() && c == hun.getHunterCol()) {
			 //check if he's standing on the goop
			 if(bor[r][c] == 'G') {
				gc.drawImage(goop, c*40, r*40);
			 }//check if he's standing on the blood
			 else if(bor[r][c] == 'B') {
				gc.drawImage(blood, c*40, r*40);
			 }//check if he's standing on the slime
			 else if(bor[r][c] == 'S') {
				gc.drawImage(slime, c*40, r*40);
			 }//check if he's standing on the pit
			 else if(bor[r][c] == 'P') {
				gc.drawImage(slPit, c*40, r*40);
			 }//check if he's standing on the wumpus
			 else if(bor[r][c] == 'W') {
				gc.drawImage(wumpus, c*40, r*40);
			 }
			 //draw the hunter on top of the image
			 gc.drawImage(hunter, hun.getHunterCol()*40, hun.getHunterRow()*40-10);
		  }
		  else if(bor[r][c] == 'B') {
			 //check if it's blood
			 if(vis[r][c]) {
				gc.clearRect(c*40, r*40, 40, 40);
			 }
			 gc.drawImage(blood, c*40, r*40);
		  }
		  else if(bor[r][c] == 'G') {
			 //check if it's goop
			 if(vis[r][c]) {
				gc.clearRect(c*40, r*40, 40, 40);
			 }
			 gc.drawImage(goop, c*40, r*40);
		  }
		  else if(bor[r][c] == 'S') {
			 //check if it's slime
			 if(vis[r][c]) {
				gc.clearRect(c*40, r*40, 40, 40);
			 }
			 gc.drawImage(slime, c*40, r*40);
		  }
		  else if(bor[r][c] == 'P') {
			 //check if it's pit
			 if(vis[r][c]) {
				gc.clearRect(c*40, r*40, 40, 40);
			 }
			 gc.drawImage(slPit, c*40, r*40);
		  }
		  else if(bor[r][c] == 'W') {
			 //check if it's wumpus
			 if(vis[r][c]) {
				gc.clearRect(c*40, r*40, 40, 40);
			 }
			 gc.drawImage(wumpus, c*40, r*40);
		  }
		  else if(bor[r][c] == 'X' && !(vis[r][c])) {
			 //check if it's a ground and has not been visited
			 gc.drawImage(grnd, c*40, r*40);
		  }
		  else {
			 //check if it's a ground and has been visited
			 gc.clearRect(c*40, r*40, 40, 40);
		  }
		}
	 }
  }


  //the method is called upin when the game is STILL RUNNING
  private void updateButtons() {

	 //while the game is still running
	 if(theGame.gameStillRunning()) {

		//clear teh canvas
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		//draw the inital board
		drawBoard();

		//get all the image objects
		Image hunter = new Image("file:images/TheHunter.png", 40, 40, false, false);
		Image grnd = new Image("file:images/Ground.png", 40, 40, false, false);
		Image blood = new Image("file:images/Blood.png", 40, 40, false, false);
		Image goop = new Image("file:images/Goop.png", 40, 40, false, false);
		Image slime = new Image("file:images/Slime.png", 40, 40, false, false);
		Image slPit = new Image("file:images/SlimePit.png", 40, 40, false, false);
		Image wumpus = new Image("file:images/Wumpus.png", 40, 40, false, false);

		//iterates over the entire game board and draws the correct objects
		for (int r = 0; r < 12; r++) {
		  for (int c = 0; c < 12; c++) {
			 //if the square is visible or already has been visited
			 if(vis[r][c]) {

				//check if it's a hunter spot
				if(r == hun.getHunterRow() && c == hun.getHunterCol()) {

				  //check if he's standing on goop
				  if(bor[r][c] == 'G') {
					 gc.drawImage(goop, c*40, r*40);
				  }//check if he's standing on blood
				  else if(bor[r][c] == 'B') {
					 gc.drawImage(blood, c*40, r*40);
				  }//check if he's standing on slime
				  else if(bor[r][c] == 'S') {
					 gc.drawImage(slime, c*40, r*40);
				  }//check if he's standing on pit
				  else if(bor[r][c] == 'P') {
					 gc.drawImage(slPit, c*40, r*40);
				  }//check if he's standing on wumpus
				  else if(bor[r][c] == 'W') {
					 gc.drawImage(wumpus, c*40, r*40);
				  }

				  //draw the hunter on top the object present there
				  gc.drawImage(hunter, hun.getHunterCol()*40, hun.getHunterRow()*40-10);
				}
				else if(bor[r][c] == 'B') {
				  //check if its blood
				  gc.clearRect(c*40, r*40, 40, 40);
				  gc.drawImage(blood, c*40, r*40);
				}
				else if(bor[r][c] == 'G') {
				  //check if its goop
				  gc.clearRect(c*40, r*40, 40, 40);
				  gc.drawImage(goop, c*40, r*40);
				}
				else if(bor[r][c] == 'S') {
				  //check if its slime
				  gc.clearRect(c*40, r*40, 40, 40);
				  gc.drawImage(slime, c*40, r*40);
				}
				else if(bor[r][c] == 'P') {
				  //check if its pit
				  gc.clearRect(c*40, r*40, 40, 40);
				  gc.drawImage(slPit, c*40, r*40);
				}
				else if(bor[r][c] == 'W') {
				  //check if its wumpus
				  gc.clearRect(c*40, r*40, 40, 40);
				  gc.drawImage(wumpus, c*40, r*40);
				}
				else {
				  //check if its an already visited empty spot
				  gc.clearRect(c*40, r*40, 40, 40);
				}
			 }
			 else {
				//if it's not visited then draw the ground image
				gc.drawImage(grnd, c*40, r*40);
			 }
		  }
		}
	 }
  }


  @Override
  public void update(Observable observable, Object arg) {
	 // TODO Auto-generated method stub
	 theGame = (Map) observable;

	 //while the game is still running update the board
	 if(theGame.gameStillRunning()) {
		updateButtons();
	 }
	 else {
		//once the game is done, run updateLast to print all the hazards and warning
		updateLast();
	 }

	 //change the title of the stage appropriately
	 if (bor[hun.getHunterRow()][hun.getHunterCol()] == 'B')
		st.setTitle("I smell something foul");
	 else if (bor[hun.getHunterRow()][hun.getHunterCol()] == 'S')
		st.setTitle("I can hear the wind");
	 else if (bor[hun.getHunterRow()][hun.getHunterCol()] == 'G')
		st.setTitle("I smell something foul, I can hear the wind");
	 else if (bor[hun.getHunterRow()][hun.getHunterCol()] == 'P') {
		//if you lost, report that the game is ended and print the last board
		st.setTitle("You fell down a bottomless pit. You lose.");
		updateLast();
		theGame.gameEnded(2);
	 }
	 else if (bor[hun.getHunterRow()][hun.getHunterCol()] == 'W') {
		//if you lost, report that the game is ended and print the last board
		st.setTitle("You walked into the Wumpus. You lose");
		updateLast();
		theGame.gameEnded(3);
	 }
	 else {
		//otherwise set the title to hunt the wumpus
		st.setTitle("Hunt the Wumpus");
	 }
  }

}
