import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;


@SuppressWarnings("serial")
public class SweeperSquares extends JButton{

	private boolean exposed=false;
	private boolean mine=false;
	private int nearbyMines=0;
	private boolean flagged=false;
	private int row;
	private int col;

	public SweeperSquares(int r,int c) {

		row = r;
		col = c;
		addMouseListener(new squareAction());
	}


	class squareAction implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			
			if (!Minesweeper.gameOver && !exposed) {
				if (e.getButton()==1 && !flagged) {
					if (mine) {
						setBackground(Color.red);
						setText("*");
						exposed=true;
						Minesweeper.gameOver=true;
					} else if(!flagged){
						setBackground(null);
						if(nearbyMines!=0) {
							setText(nearbyMines+"");
						}
						if (nearbyMines==0) {
							Minesweeper.clearArea(row,col);
							revealSquare();
						} else {
							revealSquare();
						}
					}
				}
				if(e.getButton()==3) {
					
					if(flagged) {
						setBackground(Color.gray);
						setText("");					
						Minesweeper.numMines++;
						flagged=false;
						Minesweeper.setMinesToWin();
						return;
					} else if(Minesweeper.numMines>0){
						setBackground(Color.BLUE);
						flagged=true;
						Minesweeper.numMines--;
						setText("|>");
						Minesweeper.setMinesToWin();
						return;
					}
				}
			}
			Minesweeper.checkWin();
		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}

	}

	public boolean getMine() {
		return mine;
	}
	
	public void setMine(boolean e) {
		mine=e;
	}
	
	public void setNearbyMines(int m) {
		nearbyMines=m;
	}
	
	public int getNearbyMines() {
		return nearbyMines;
	}


	public boolean getExposed() {
		return exposed;
	}
	
	public void setExposed(boolean b) {
		exposed=b;
	}
	public void revealSquare() {
		setBackground(null);
		if (nearbyMines!=0) {
			setText(nearbyMines+"");
		}
		exposed=true;
	}

	public boolean getFlagged() {
		return flagged;
	}
	public void setFlagged(boolean f) {
		flagged=f;
	}

}
