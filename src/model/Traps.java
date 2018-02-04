package model;

import java.util.Random;

public class Traps {

  private Map theGame;
  private Random rand;
  private int numberOfTraps;
  final private char pit = 'P';
  final private char slime = 'S';
  private int[][] position;

  public Traps(Map map){
	 theGame = map;
	 rand = new Random();
	 numberOfTraps = rand.nextInt(3) + 3;
	 position = new int[numberOfTraps][2];
  }

  public void setPosition() {

	 int r = rand.nextInt(12);
	 int c = rand.nextInt(12);
	 int i = 0;

	 while(i < numberOfTraps) {
		//keep on looking for a random available spot
		while(!(theGame.available(r, c))) {
		  r = rand.nextInt(12);
		  c = rand.nextInt(12);
		}

		if(theGame.setSpot(r, c, pit)) {
		  setTrapPos(i, r, c);
		  setSlime(r, c);
		}
		i++;
	 }

  }

  private void setSlime(int r, int c) {
	 
	 // sets the slime around bottomlessPit
	 int[] fir = new int[2];
	 //  S
	 //S P S
	 //  S
	 //up above the bottomlessPit
	 fir = theGame.up(r, c);
	 theGame.setSpot(fir[0], fir[1], slime);

	 //down below the bottomlessPit
	 fir = theGame.down(r, c);
	 theGame.setSpot(fir[0], fir[1], slime);
	 
	 //left of the bottomlessPit
	 fir = theGame.left(r, c);
	 theGame.setSpot(fir[0], fir[1], slime);
	 
	 //right of the bottomlessPit
	 fir = theGame.right(r, c);
	 theGame.setSpot(fir[0], fir[1], slime);
	 
  }

  private void setTrapPos(int i, int r, int c) {
	 this.position[i][0] = r;
	 this.position[i][1] = c;
  }
  
  public int[][] getTrapsPos() {
	 return position;
  }

}
