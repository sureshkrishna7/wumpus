package controller;

/**
 * This class WumpusMain
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

  //the model object
  private Map theGame;

  //the menubar
  private MenuBar menuBar;
  //the entire pane
  private BorderPane pane;

  //textView of the model
  private Observer textView;
  
  //imageView of the model
  private Observer imageView;
  
  //currentView will be textView
  private Observer currentView;
  private Scene scene;

  @Override
  public void start(Stage stage) throws Exception {
	 //set the title of the stage
	 stage.setTitle("Hunt the Wumpus");
	 pane = new BorderPane();
	 
	 //set scene width
	 scene = new Scene(pane, 560, 640);

	 //set up the menus
	 setupMenus();
	 pane.setTop(menuBar);
	 
	 //initialize everything for the first time
	 initializeGameForTheFirstTime();

	 //textView for the model
	 textView = new TextView(theGame, scene);
	 
	//imageView for the model
	 imageView = new ImageView(theGame, scene, stage);
	 
	 //add observers for both the views
	 theGame.addObserver(textView);
	 theGame.addObserver(imageView);

	 //set the initial view to textView
	 setViewTo(textView);

	 stage.setScene(scene);
	 stage.show();
  }

  //create new objects for everything
  public void initializeGameForTheFirstTime() {
	 theGame = new Map();
	 // This event driven program will always have
	 // a player who takes theturn
  }

  //set the initial view and also the subsequent views
  private void setViewTo(Observer newView) {
	 //set the center to null
	 pane.setCenter(null);
	 currentView = newView;
	 //set the view to the preferred view
	 pane.setCenter((Node) currentView);
  }

  //set up the options menu
  private void setupMenus() {

	 //added the view menus
	 MenuItem text = new MenuItem("TextArea");
	 MenuItem draw = new MenuItem("ImageView");
	 Menu view = new Menu("Views");
	 view.getItems().addAll(text, draw);

	 //added the new game option
	 MenuItem newGame = new MenuItem("New Game");
	 Menu options = new Menu("Options");
	 options.getItems().addAll(newGame, view);

	 //add everything in the menubar
	 menuBar = new MenuBar();
	 menuBar.getMenus().addAll(options);

	 //Add the same listener to all menu items requiring action
	 MenuItemListener menuListener = new MenuItemListener();
	 newGame.setOnAction(menuListener);
	 text.setOnAction(menuListener);
	 draw.setOnAction(menuListener); 
  }

  //event hander for all the menubar options
  private class MenuItemListener implements EventHandler<ActionEvent> {

	 //checks if you called newgame or selected either one of both the views
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