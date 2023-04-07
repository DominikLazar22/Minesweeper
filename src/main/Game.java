package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game implements MouseListener, KeyListener{
	
	private final int WIDTH = 800;
	private final int HEIGTH = 600;
	
	private final int boxSize = 40;
	private final int boardWidth = WIDTH/boxSize;
	private final int boardHeigth = HEIGTH/boxSize;
	
	private MyCanvas canvas = new MyCanvas();
	
	private Tile[][] board; 
	
	private boolean alive;
	
	private void initialize() {
		alive = true;
		board = new Tile[boardWidth][boardHeigth];
		Random rd = new Random();
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j ++) {
				if(rd.nextInt(20) < 3) {
					board[i][j] = new Tile(true);
				}else {
					board[i][j] = new Tile(false);
				}
			}
		}
		
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				calcNeighbours(i, j);
			}
		}
	}
	
	public void calcNeighbours(int x, int y){
		int sum = 0;
		for(int i = -1; i <=1 ; i++) {
			for(int j = -1; j<= 1; j++) {
				if(i+x < 0 || y+j < 0 || i+x >= boardWidth || j+y >= boardHeigth) {
					continue;
				}
				if(board[i+x][j+y].isMine()) {
					sum += 1;
				}
			}
		}
		board[x][y].setNeighbourCount(sum);
	}

	public Game() {
		initialize();
	
		JFrame frame = new JFrame("Minesweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = frame.getContentPane();
		c.setPreferredSize(new Dimension(WIDTH,HEIGTH));
		canvas.setFocusable(true);
		canvas.addMouseListener(this);
		canvas.addKeyListener(this);
		c.add(canvas);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}

	
	@SuppressWarnings("serial")
	class MyCanvas extends JPanel{
	
		public void update() {
			repaint();
		}
	
		public MyCanvas() {
			//setBackground(Color.GRAY);
		}
	
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board[0].length; j++) {
					if(board[i][j].isHidden()) {
						g.setColor(Color.BLUE);
						g.fillRect(i*boxSize, j*boxSize, boxSize, boxSize);
						if(board[i][j].isMarked()) {
							int xDraw = (int) (i*boxSize+ 0.5 * boxSize);
							int yDraw = (int) (j*boxSize+ 0.5 * boxSize);
							g.setColor(Color.BLACK);
							g.drawString("M", xDraw, yDraw);
						}
					}else {
						if(board[i][j].isMine()) {
							g.setColor(Color.DARK_GRAY);
							g.fillRect(i*boxSize, j*boxSize, boxSize, boxSize);
						}else {
							int xDraw = (int) (i*boxSize+ 0.5 * boxSize);
							int yDraw = (int) (j*boxSize+ 0.5 * boxSize);
							g.setColor(Color.WHITE);
							g.fillRect(i*boxSize, j*boxSize, boxSize, boxSize);
							g.setColor(Color.BLACK);
							g.drawString(""+board[i][j].getNeighbours(), xDraw, yDraw);
						}
					}
				}
			}
			
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board[0].length; j++) {
					g.setColor(Color.BLACK);
					g.drawRect(i*boxSize, j*boxSize,boxSize, boxSize);
				}
			}
		}
	}

	public void triggerZeros(int x , int y) {
		for(int i = -1; i <=1 ; i++) {
			for(int j = -1; j<= 1; j++) {
				if(i+x < 0 || y+j < 0 || i+x >= boardWidth || j+y >= boardHeigth) {
					continue;
				}
				if(!board[i+x][j+y].isMine() && board[i+x][j+y].isHidden()) {
					board[i+x][j+y].clicked();
					if(board[i+x][j+y].getNeighbours() == 0) {
						triggerZeros(i+x, y+j);
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {	
		if(alive) {
			int tileX = e.getX() / boxSize;
			int tileY = e.getY() / boxSize;
			if(e.getButton() == 3) {
				board[tileX][tileY].marked();
			}else {			
				alive = board[tileX][tileY].clicked();
				if(board[tileX][tileY].getNeighbours() == 0) {			
					triggerZeros(tileX, tileY);
				}
			}
			canvas.update();
		}
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER && !alive) {
			initialize();
			canvas.update();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}