package views;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import model.Map;


/**
 * This class . . . 
 * Author: Suresh Krishna Devendran
 */

public class ImageView extends BorderPane implements Observer {

  
  public ImageView(Map theGame) {
	 
	 initializePane();
  }
  
  private void initializePane() {
	 // TODO Auto-generated method stub
	 Label label = new Label("ImageView");
	 label.setFont(new Font("serif", 24));
	 setCenter(label);

  }
  
  @Override
  public void update(Observable o, Object arg) {
	 // TODO Auto-generated method stub
	 
  }

}
