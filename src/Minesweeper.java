import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


@SuppressWarnings("serial")
public class Minesweeper extends JFrame{

	private static int size = 20;
	private static SweeperSquares[][] board = new SweeperSquares[size][size];
	private JButton reset = new JButton("Reset");
	private JPanel centerPanel = new JPanel();
	static boolean gameOver=false;
	static int numMines = (int)((size*size) * 0.1);
	public static JTextField winText = new JTextField("Total Mines " + numMines);
	
	public Minesweeper() {
		setTitle("Minesweeper");
		setSize(1000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		for(int r=0;r<board.length;r++) {
			for(int c=0; c<board[r].length;c++) {
				board[r][c]=new SweeperSquares(r,c);
				board[r][c].setBackground(Color.gray);
				centerPanel.add(board[r][c]);

			}
		}
		for (int i = 0;i<numMines;i++) {
			int r=(int) (Math.random()*size);
			int c=(int) (Math.random()*size);
			if (!board[r][c].getMine()) {
				board[r][c].setMine(true);
				//System.out.println(r + " " + c);
			} else {
				i--;
			}
		}


		for(int r=0; r<board.length;r++) {
			for (int c=0; c<board[r].length;c++) {
				int mineCount=0;
				for(int i = -1; i<=+1; i++) {
					for(int j= -1; j<=+1; j++) {
						try {
							if (board[r+i][c+j].getMine()) mineCount++;
						} catch (ArrayIndexOutOfBoundsException f) {}

					}
				}
				board[r][c].setNearbyMines(mineCount);
			}
		}


		setLayout(new BorderLayout());
		centerPanel.setLayout(new GridLayout(size,size));
		add(centerPanel,BorderLayout.CENTER);
		add(reset,BorderLayout.SOUTH);
		reset.addActionListener(new ResetAction());
		add(BorderLayout.NORTH,winText);
		setVisible(true);
	}

	public static void findBombs() {
		for(int r=0;r<board.length;r++) {
			for(int c=0; c<board[r].length;c++) {
				if (board[r][c].getMine()) {

				}
			}
		}
	}

	public static void main(String[] args) {
		
		new Minesweeper();

	}

	public static void clearArea(int r,int c) {

		for(int i = -1; i<=+1; i++) {
			for(int j= -1; j<=+1; j++) {
				if(i+r>=0 && i+r<size && j+c>=0 && j+c<size && !board[r+i][c+j].getExposed()) {

					board[r+i][c+j].setExposed(true);
					if (board[r+i][c+j].getMine()) {
						board[r+i][c+j].setBackground(Color.black);
					} else if (board[r+i][c+j].getNearbyMines()==0) {
						board[r+i][c+j].revealSquare();
						clearArea(r+i,c+j);
					} else if (board[r+i][c+j].getNearbyMines()>0) {
						board[r+i][c+j].revealSquare();
					}
				}
			}

		}
	}


	public static void checkWin() {
		if (!gameOver) {
			for(int r=0;r<board.length;r++) {
				for(int c=0; c<board[r].length;c++) {
					if (!(board[r][c].getExposed()||board[r][c].getMine())) {
						return;
					}
				}
			}
			winText.setText("Winner!");
			for(int r=0;r<board.length;r++) {
				for(int c=0; c<board[r].length;c++) {
					if(!board[r][c].getFlagged()&&!board[r][c].getMine())
					board[r][c].setBackground(Color.green);
					if(board[r][c].getMine()) {
						board[r][c].setBackground(Color.orange);
						board[r][c].setText("#");
					}
				}
			}
			gameOver=true;
		} else if (!winText.getText().equals("Winner!")){
			winText.setText("You Lose Try Again by pressing the Reset Button");
			for(int r=0;r<board.length;r++) {
				for(int c=0; c<board[r].length;c++) {
					if (board[r][c].getMine()&& !board[r][c].getFlagged()) {
						board[r][c].setBackground(Color.red);
						board[r][c].setText("*");
					} else if(!board[r][c].getMine()&& board[r][c].getFlagged()) {
						board[r][c].setBackground(Color.BLACK);
					}
				}
			}
		}
	}
	
	class ResetAction implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			numMines = (int)((size*size) * 0.1);
			for(int r=0;r<board.length;r++) {
				for(int c=0; c<board[r].length;c++) {
					board[r][c].setExposed(false);
					board[r][c].setMine(false);
					board[r][c].setFlagged(false);
					board[r][c].setBackground(Color.gray);
					board[r][c].setText("");
				}
			}
			winText.setText("Total Mines " + numMines);
			gameOver=false;
			for (int i = 0;i<numMines;i++) {
				int r=(int) (Math.random()*size);
				int c=(int) (Math.random()*size);
				if (!board[r][c].getMine()) {
					board[r][c].setMine(true);
					//System.out.println(r + " " + c);
				} else {
					i--;
				}
			}
			for(int r=0; r<board.length;r++) {
				for (int c=0; c<board[r].length;c++) {
					int mineCount=0;
					for(int i = -1; i<=+1; i++) {
						for(int j= -1; j<=+1; j++) {
							try {
								if (board[r+i][c+j].getMine()) mineCount++;
							} catch (ArrayIndexOutOfBoundsException f) {}

						}
					}
					board[r][c].setNearbyMines(mineCount);
				}
			}
		}
		
	}

	public static void setMinesToWin() {
		winText.setText("Total Mines " + numMines);
		
	}
}
