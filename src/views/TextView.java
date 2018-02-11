package views;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.HunterPlayer;
import model.Map;


public class TextView  extends BorderPane implements Observer {

  private Map theGame;
  private HunterPlayer hun;
  private char[][] bor;

  private TextArea textBox;
  private String textString;
  private Scene sc;
  private GridPane fields;
  private GridPane directions;
  private Label textMessage;
  private Button north;
  private Button south;
  private Button east;
  private Button west;

  public TextView(Map theGameMap, Scene scene) {
	 theGame = theGameMap;
	 hun = theGameMap.getHunterObject();
	 bor = theGame.getMapBoard();
	 sc = scene;

	 directions = new GridPane();
	 fields = new GridPane();

	 textMessage = new Label("Safe for now");
	 GridPane.setMargin(textMessage, new Insets(0, 10, 10, 25));
	 textMessage.setFont(new Font("Arial", 17));
	 textMessage.setTextFill(Color.web("#008000"));

	 textBox = new TextArea();
	 textBox.setEditable(false);

	 north = new Button("N");
	 //north.setText("N");
	 south = new Button("S");
	 //south.setText("S");
	 east = new Button("E");
	 //east.setText("E");
	 west = new Button("W");
	 //west.setText("W");

	 textBox.setFont(new Font("Courier", 22));
	 textBox.setStyle( "-fx-text-fill: #b38600" );
	 textBox.setMaxWidth(510);
	 textBox.setMaxHeight(340);
	 GridPane.setMargin(textBox, new Insets(15, 20, 15, 25));
	 textBox.setPadding(new Insets(0, 0, 0, 0));
	 //this.setTop(textBox);

	 initializePanel();
  }

  private void initializePanel() {
	 // TODO Auto-generated method stub
	 /*Label label = new Label("TextAreaView");
	 label.setFont(new Font("serif", 24));
	 setCenter(label);
	  */
	 fields.add(textBox, 0, 0);
	 fields.add(textMessage, 0, 1);
	 this.setCenter(fields);

	 ColumnConstraints cc = new ColumnConstraints();
	 cc.setHgrow(Priority.ALWAYS);
	 fields.getColumnConstraints().add(cc);
	 RowConstraints rc = new RowConstraints();
	 rc.setVgrow(Priority.ALWAYS);
	 fields.getRowConstraints().add(rc);	 

	 directions.add(north, 2, 2);
	 directions.add(west, 1, 3);
	 directions.add(east, 3, 3);
	 directions.add(south, 2, 4);


	 BorderPane.setAlignment(directions, Pos.BOTTOM_LEFT);
	 BorderPane.setMargin(directions, new Insets(10, 10, 30, 220));
	 this.setBottom(directions);

	 ButtonListener handler = new ButtonListener();
	 north.setOnAction(handler);
	 south.setOnAction(handler);
	 west.setOnAction(handler);
	 east.setOnAction(handler);

	 sc.setOnKeyReleased(new KeyListener());

	 updateButtons();
  }

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
		}  
	 }
  }



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
			 textMessage.setText("Your arrow hit the wumpus. You win.");
			 textMessage.setTextFill(Color.web("#ffcc00"));
			 updateLastResult();
			 theGame.gameEnded();
		  }
		  else {
			 textMessage.setText("You just shot yourself. You lose.");
			 textMessage.setTextFill(Color.web("#e60000"));
			 updateLastResult();
			 theGame.gameEnded();
		  }
		}
	 }

  }

  @Override
  public void update(Observable observable, Object arg) {
	 // TODO Auto-generated method stub
	 theGame = (Map) observable;
	 updateButtons();
	 if (bor[hun.getHunterRow()][hun.getHunterCol()] == 'B')
		textMessage.setText("I smell something foul");
	 else if (bor[hun.getHunterRow()][hun.getHunterCol()] == 'S')
		textMessage.setText("I can hear the wind");
	 else if (bor[hun.getHunterRow()][hun.getHunterCol()] == 'G')
		textMessage.setText("I smell something foul, I can hear the wind");
	 else if (bor[hun.getHunterRow()][hun.getHunterCol()] == 'P') {
		textMessage.setText("You fell down a bottomless pit. You lose.");
		textMessage.setTextFill(Color.web("#e60000"));
		updateLastResult();
		theGame.gameEnded();
	 }
	 else if (bor[hun.getHunterRow()][hun.getHunterCol()] == 'W') {
		textMessage.setText("You walked into the Wumpus. You lose");
		textMessage.setTextFill(Color.web("#e60000"));
		updateLastResult();
		theGame.gameEnded();
	 }
	 else {
		textMessage.setTextFill(Color.web("#008000"));
		textMessage.setText("Safe for now");
	 }

  }

  private void updateButtons() {
	 // TODO Auto-generated method stub
	 textString = new String();
	 textString = theGame.toString();
	 textBox.setText(textString);
  }

  private void updateLastResult() {
	 textString = new String();
	 textString = theGame.toEndGameString();
	 textBox.setText(textString);
  }
  
}
