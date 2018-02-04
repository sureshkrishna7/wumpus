package controller;

/**
 * This class . . .
 * Author: Suresh Krishna Devendran
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WumpusMain extends Application {

  public static void main(String[] args) {
	 launch(args);
  }

  
  @Override
  public void start(Stage stage) throws Exception {
	 BorderPane pane = new BorderPane();

	 Scene scene = new Scene(pane, 690, 630);
	 stage.setScene(scene);
	 stage.show();
  }
}