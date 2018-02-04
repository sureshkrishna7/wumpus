package model;

import java.util.Random;

public class Wumpus {

  private Map theGame;
  private Random rand;
  final private char wum = 'W';
  final private char blood = 'B';
  private int[] position;

  public Wumpus(Map map){
	 theGame = map;
	 rand = new Random();
	 position = new int[2];
  }

  public void setPosition() {
	 // TODO Auto-generated method stub
	 int r = rand.nextInt(12);
	 int c = rand.nextInt(12);

	 //keep on looking for a random available spot
	 while(!(theGame.available(r, c))) {
		r = rand.nextInt(12);
		c = rand.nextInt(12);
	 }

	 if(theGame.setSpot(r , c, wum)) {
		setWumpusPos(r, c);
		setBlood(r,c);
	 }

  }

  private void setBlood(int r, int c) {
	 // sets the blood around wumpus
	 int[] fir = new int[2];
	 int[] sec = new int[2];

	 //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 //  B
	 //B B B
	 //  W
	 //up one step above the wumpus
	 fir = theGame.up(r, c);
	 theGame.setSpot(fir[0], fir[1], blood);

	 //up two steps above the wumpus
	 sec = theGame.up(fir[0], fir[1]);
	 theGame.setSpot(sec[0], sec[1], blood);

	 //up left of the wumpus
	 sec = theGame.left(fir[0], fir[1]);
	 theGame.setSpot(sec[0], sec[1], blood);

	 //up right of the wumpus
	 sec = theGame.right(fir[0], fir[1]);
	 theGame.setSpot(sec[0], sec[1], blood);
	 //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 //  W 
	 //B B B
	 //  B
	 //down one step below the wumpus
	 fir = theGame.down(r, c);
	 theGame.setSpot(fir[0], fir[1], blood);

	 //down two steps below the wumpus
	 sec = theGame.down(fir[0], fir[1]);
	 theGame.setSpot(sec[0], sec[1], blood);

	 //down left of the wumpus
	 sec = theGame.left(fir[0], fir[1]);
	 theGame.setSpot(sec[0], sec[1], blood);

	 //down right of the wumpus
	 sec = theGame.right(fir[0], fir[1]);
	 theGame.setSpot(sec[0], sec[1], blood);
	 //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 //B B W
	 //left of the wumpus
	 fir = theGame.left(r, c);
	 theGame.setSpot(fir[0], fir[1], blood);

	 //left left of the wumpus
	 sec = theGame.left(fir[0], fir[1]);
	 theGame.setSpot(sec[0], sec[1], blood);
	 //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 //W B B
	 //right of the wumpus
	 fir = theGame.right(r, c);
	 theGame.setSpot(fir[0], fir[1], blood);

	 //right right of the wumpus
	 sec = theGame.right(fir[0], fir[1]);
	 theGame.setSpot(sec[0], sec[1], blood);
	 //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  }

  private void setWumpusPos(int r, int c) {
	 this.position[0] = r;
	 this.position[1] = c;
  }

  public int getWumpusRow() {
	 return position[0];
  }

  public int getWumpusCol() {
	 return position[1];
  }

  public int[] getWumpusPosition() {
	 return position;
  }


}
