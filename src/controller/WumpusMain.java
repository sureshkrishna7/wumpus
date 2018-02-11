package controller;

/**
 * This class . . .
 * Author: Suresh Krishna Devendran
 */

import java.util.Observer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Map;
import views.ImageView;
import views.TextView;

public class WumpusMain extends Application {

  public static void main(String[] args) {
	 launch(args);
  }

  private Map theGame;

  private MenuBar menuBar;
  private BorderPane pane;

  private Observer textView;
  private Observer imageView;
  private Observer currentView;
  private Scene scene;

  @Override
  public void start(Stage stage) throws Exception {
	 stage.setTitle("Hunt the Wumpus");
	 pane = new BorderPane();
	 scene = new Scene(pane, 550, 630);

	 setupMenus();
	 pane.setTop(menuBar);
	 initializeGameForTheFirstTime();

	 textView = new TextView(theGame, scene);
	 imageView = new ImageView(theGame);
	 theGame.addObserver(textView);
	 theGame.addObserver(imageView);

	 setViewTo(textView);

	 stage.setScene(scene);
	 stage.show();
  }


  public void initializeGameForTheFirstTime() {
	 theGame = new Map();
	 // This event driven program will always have
	 // a computer player who takes the second turn
  }

  private void setViewTo(Observer newView) {
	 pane.setCenter(null);
	 currentView = newView;
	 pane.setCenter((Node) currentView);
  }

  private void setupMenus() {


	 //added drawing menus
	 MenuItem text = new MenuItem("TextArea");
	 MenuItem draw = new MenuItem("ImageView");
	 Menu view = new Menu("Views");
	 view.getItems().addAll(text, draw);

	 MenuItem newGame = new MenuItem("New Game");
	 Menu options = new Menu("Options");
	 options.getItems().addAll(newGame, view);

	 menuBar = new MenuBar();
	 menuBar.getMenus().addAll(options);

	 // Add the same listener to all menu items requiring action
	 MenuItemListener menuListener = new MenuItemListener();
	 newGame.setOnAction(menuListener);
	 text.setOnAction(menuListener);
	 draw.setOnAction(menuListener); 
  }
  
  private class MenuItemListener implements EventHandler<ActionEvent> {

	 @Override
	 public void handle(ActionEvent e) {
		String text = ((MenuItem) e.getSource()).getText();
		if(text.equals("ImageView"))
		  setViewTo(imageView);
		else if (text.equals("TextArea"))
		  setViewTo(textView);
		else if (text.equals("New Game"))
		  theGame.startNewGame(); // The computer player has been set and should not change.
	 }
  }

}