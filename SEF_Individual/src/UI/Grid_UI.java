package UI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Grid_Database.Cell;
import Grid_Database.Food;
import Grid_Database.Grid;
import Grid_Database.Monster;
import Grid_Database.Player;

public class Grid_UI extends JFrame implements KeyListener {
	Grid _game;
	gridPanel mp = null;
	int rnd1;
	int rnd2;
	Player player;
	Food food;
	Monster[] monster = new Monster[2];
	JFrame frame = new JFrame("UI");

	public Grid_UI() {
		Grid game = new Grid(11);
		_game = game;
		rndPos();
		player = new Player(rnd1, rnd2);
		for (int x = 0; x < 2; x++) {
			rndPos();
			monster[x] = new Monster(rnd1, rnd2);
		}
		
		rndPos();
		food = new Food(rnd1,rnd2);
		
		mp = new gridPanel();
		frame.add(mp);
		frame.addKeyListener(this);
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
	}

	class gridPanel extends JPanel {
		int n = 11;
		int x = n ^ 2 - ((n - 3) / 2) ^ 2 * 4;//number of cell
		Image image = null;

		public void paintComponent(Graphics g) {
			ArrayList<Cell> cellLsit = _game.cellList;
			this.setLayout(null);
			
			//draw the cell
			for (int i = 0; i < cellLsit.size(); i++) {
				// System.out.println("X:" + cellLsit.get(i).getX() + ",Y:" +
				// cellLsit.get(i).getY());
				try {
					image = ImageIO.read(new File("res//square.png"));
					g.drawImage(image, cellLsit.get(i).getX() * 40, cellLsit.get(i).getY() * 40, 50, 50, null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//draw the player
			try {
				image = ImageIO.read(new File("res//body.png"));
				g.drawImage(image, player.getX() * 40, player.getY() * 40, 50, 50, null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//draw the monster
			for (int i = 0; i < 2; i++) {
				try {
					image = ImageIO.read(new File("res//body4.png"));
					g.drawImage(image, monster[i].getX() * 40, monster[i].getY() * 40, 50, 50, null);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			try {
				image = ImageIO.read(new File("res//posion.png"));
				g.drawImage(image, food.getX() * 40, food.getY() * 40, 50, 50, null);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public void rndPos() {
		int x;
		Random random = new Random();
		rnd1 = random.nextInt(10);
		int[] numbers = { 0, (_game.getSize() - 1) / 2, (_game.getSize() - 1) };
		if (rnd1 == 0 || rnd1 == (_game.getSize() - 1) / 2 || rnd1 == (_game.getSize() - 1)) {
			rnd2 = (int) (Math.random() * 10);
		} else {
			x = random.nextInt(numbers.length);
			rnd2 = numbers[x];
		}
	}

// Control monitor
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int x = 0;
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			x = 1;

		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			x = 2;

		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			x = 3;

		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			x = 4;

		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			x = 5;

		}else
			;
		int xpos;
		int ypos;

		switch (x) {
		case 1:
			xpos = player.getX();
			if (xpos == 0 || xpos == (_game.getSize() - 1) / 2 || xpos == (_game.getSize() - 1)) {
				if (player.getY() < _game.getSize() - 1) {
					player.setY(player.getY() + 1);
					System.out.println("down");break;
				}

			} else
				System.out.println("out of border");return;
			
		case 2:
			xpos = player.getX();
			if (xpos == 0 || xpos == (_game.getSize() - 1) / 2 || xpos == (_game.getSize() - 1)) {
				if (player.getY() > 0) {
					player.setY(player.getY() - 1);
					System.out.println("up");break;
				}
			} else
				System.out.println("out of border");return;
		case 3:
			ypos = player.getY();
			if (ypos == 0 || ypos == (_game.getSize() - 1) / 2 || ypos == (_game.getSize() - 1)) {
				if (player.getX() < _game.getSize() - 1) {
					player.setX(player.getX() + 1);
					System.out.println("to right");break;
				}
			} else
				System.out.println("out of border");return;
		case 4:
			ypos = player.getY();
			if (ypos == 0 || ypos == (_game.getSize() - 1) / 2 || ypos == (_game.getSize() - 1)) {
				if (player.getX() > 0) {
					player.setX(player.getX() - 1);
					System.out.println("to left");break;
				}
			} else
				System.out.println("out of border");return;
		case 5:
			JOptionPane.showMessageDialog(mp, "Game pause", "Game", JOptionPane.WARNING_MESSAGE);
			return;
		default:
			return;
		}
		//chase();
		frame.add(mp);
		frame.repaint();

		if ((player.getX() == monster[0].getX()
				&& player.getY() == monster[0].getY())
				|| (player.getX() == monster[1].getX()
						&& player.getY() == monster[1].getY())) {
			JOptionPane.showMessageDialog(mp, "You Lose", "Game", JOptionPane.WARNING_MESSAGE);
			frame.dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

//	public void chase() {
//		int distance = Math.abs(monster[0].getX() - monster[1].getX())
//				+ Math.abs(monster[0].getY() - monster[1].getY());
//		System.out.println(distance);
//		if (distance < 5 || monster[0].getMark()) {
//			monster[0].chase(player.getX(), player.getY(), _game.getSize());
//		} else
//			monster[0].logic0(player.getX(), player.getY(), _game.getSize());
//		monster[1].logic0(player.getX(), player.getY(), _game.getSize());
//
//	}
}
