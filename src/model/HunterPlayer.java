package model;

import java.util.Random;

public class HunterPlayer {

  private Map theGame;
  private Random rand;
  //final private char hun = 'O';
  private int[] position;

  public HunterPlayer(Map map){
	 theGame = map;
	 rand = new Random();
	 position = new int[2];
  }


  public void setPosition() {
	 // TODO Auto-generated method stub
	 int r = rand.nextInt(12);
	 int c = rand.nextInt(12);

	 //keep on looking for a random available spot
	 while(!(theGame.initialHunterPos(r, c))) {
		r = rand.nextInt(12);
		c = rand.nextInt(12);
	 }

		setHunterPos(r, c);
		setHunterVisibility(r, c);
  }
  private void setHunterVisibility(int r, int c) {
	 theGame.setVisibility(r, c);
  }

  private void setHunterPos(int r, int c) {
	 this.position[0] = r;
	 this.position[1] = c;

  }
  
  public int getHunterRow() {
	 return position[0];
  }

  public int getHunterCol() {
	 return position[1];
  }

  public int[] getHunterPosition() {
	 return position;
  }


  public void move(int newR, int newC) {
	 // TODO Auto-generated method stub
	 setHunterPos(newR, newC);
  }

}
