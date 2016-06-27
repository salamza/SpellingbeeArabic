package framework;
/*
author:Sameh Salama.
Date: 7/1/2013
Email:eng.sameh11@gmail.com

This file is part a of SpellingBee Application
SpellingBee Application is a free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

SpellingBee Application is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with SpellingBee Application. If not, see <http://www.gnu.org/licenses/>.
*/
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SpellingBoard extends JPanel implements Runnable {

	int blockNum = 0;
	int starCounter = 3;
	int score=0;
	private int[] grid;
	private int[] gridAnswer;
	Image Background;
	Image Block;
	Image Star;
	Image goodAnswer;
	Image badAnswer;
	BufferedImage img = null;
	Timer timer;
	int posY,posX1 = 370 ,posY1 = 120,posX2 = 430,posY2=250;
	Clip clip;
	AudioInputStream audioIn;
	private LettersStructure letters;
	private QImagesStructure quizs;
	private Thread animator;
	private final int DELAY = 500;
	int quizimgIndex = 0;
	int cycleCounter = 0;
	int inputBlock = -1;
	boolean[] gridAbility;
	boolean solving;
	boolean endgameFlag = false;
	boolean successFlag;
	boolean answerCompleted;
	boolean RightAnswer;
	boolean WrongAnswer;
	Image thinking;
	
	public SpellingBoard() throws UnsupportedAudioFileException, IOException {
		setDoubleBuffered(true);
		this.setFocusable(true);
		letters = new LettersStructure();
		quizs = new QImagesStructure();
		addKeyListener(new TAdapter());
		try{
		audioIn= AudioSystem.getAudioInputStream(new File("C_Melodi-Jay_Sard-10152_hifi.mp3"));
		clip = AudioSystem.getClip();
		clip.open(audioIn);
		}
		catch(IOException | LineUnavailableException e){
			
		}
		try {
			img = ImageIO.read(new File("Background.jpg"));
		} catch (IOException e) {
			System.out.println("Couldn't read background");
		}
		Background = img;
		try {
			img = ImageIO.read(new File("Star.png"));
		} catch (IOException e) {
			System.out.println("Couldn't read Star");
		}
		Star = img;
		try {
			img = ImageIO.read(new File("Block.png"));
		} catch (IOException e) {
			System.out.println("Couldn't read block");
		}
		Block = img;
		try {
			img = ImageIO.read(new File("Rightanswer.gif"));
		} catch (IOException e) {
			System.out.println("Couldn't read Rightanswer");
		}
		goodAnswer=img;
		try {
			img = ImageIO.read(new File("SadFace.png"));
		} catch (IOException e) {
			System.out.println("Couldn't SadFace");
		}
		badAnswer=img;
		try {
			img = ImageIO.read(new File("thinking.png"));
		} catch (IOException e) {
			System.out.println("Couldn't SadFace");
		}
		thinking=img;
		clip.start();
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	public void addNotify() {
		super.addNotify();
		animator = new Thread(this);
		animator.start();
		successFlag = false;
		answerCompleted = false;
		solving = false;
		RightAnswer = false;
		WrongAnswer = false;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(Background, 0, 0, null);
		int x1 = 160 + 800, y1 = 10, x2 = 230 + 800, y2 = 80;
		// stars number must change.
		for (int i = 0; i < starCounter; i++) {
			g2d.drawImage(Star, x1, y1, x2, y2, 0, 0, 577, 553, null);
			x1 -= 80;
			x2 -= 80;
		}
		// block number must change.
		int bx1 = 994 - 76, by1 = 500, bx2 = 994, by2 = 580;
		for (int j = 0; j < blockNum; j++) {
			g2d.drawImage(Block, bx1, by1, bx2, by2, 0, 0, 74, 76, null);
			bx1 -= 100;
			bx2 -= 100;
		}
		g2d.drawImage(
				quizs.Quizimages.get(quizimgIndex).getQuizImage(),
				92,
				109,
				343,
				427,
				0,
				0,
				quizs.Quizimages.get(quizimgIndex).getQuizImage()
						.getWidth(null),
				quizs.Quizimages.get(quizimgIndex).getQuizImage()
						.getHeight(null), null);
		int dx1, dy1, dx2, dy2;
		dx1 = 851;
		dy1 = 126;
		dy2 = dy1 + 60;
		for (int i = 0; i < 3; i++) {
			dx2 = dx1 + 65;
			if(!gridAbility[i])
			{g2d.drawImage(letters.arabicLetters.get(grid[i] - 1)
					.getLetterImage(), dx1, dy1, dx2, dy2, 0, 0, 60, 65, null);}
			dx1 = dx2 + 70;
		}
		dx1 = 851;
		dy1 = 223;
		dy2 = dy1 + 68;
		for (int i = 3; i < 6; i++) {
			dx2 = dx1 + 65;
			if(!gridAbility[i]){
			g2d.drawImage(letters.arabicLetters.get(grid[i] - 1)
					.getLetterImage(), dx1, dy1, dx2, dy2, 0, 0, 60, 65, null);}
			dx1 = dx2 + 60;
		}
		dx1 = 851;
		dy1 = 350;
		dy2 = dy1 + 65;
		for (int i = 6; i < 9; i++) {
			dx2 = dx1 + 65;
			if(!gridAbility[i]){
			g2d.drawImage(letters.arabicLetters.get(grid[i] - 1)
					.getLetterImage(), dx1, dy1, dx2, dy2, 0, 0, 60, 65, null);}
			dx1 = dx2 + 60;
		}
		dx1=910;
		dy1=500;
		dy2=580;
		dx2=994;
		for (int i=0;i<gridAnswer.length;i++){
			if(gridAnswer[i]!=0){
				g2d.drawImage(letters.arabicLetters.get(gridAnswer[i]-1).getLetterImage(), dx1, dy1, dx2, dy2, 0,0, 60, 65, null);
			}
			dx1 = dx1-100;
			dx2 = dx1+90;
		}
		if(!WrongAnswer&&!RightAnswer){
			g2d.drawImage(thinking,posX1+30, posY1+20,posX2-40,posY2-20, null);
		}
	if(WrongAnswer){
		g2d.drawImage(badAnswer,posX1+100,posY1,posX2-160,posY2-50, null);
	}
	if(RightAnswer){
		g2d.drawImage(goodAnswer,posX1, posY1,posX2,posY2, null);
	}
	g2d.setColor(Color.BLUE);
	g2d.setFont(new Font(TOOL_TIP_TEXT_KEY, WIDTH, 50));
	g2d.drawString(new String("Score: "), 340, 40);
	g2d.drawString(Integer.toString(score),490 , 40);
		// for (int i =0 ;i<quizs.Quizimages.size();i++){
		//
		// g2d.drawImage(quizs.Quizimages.get(i).getQuizImage(), 92, 109, 343,
		// 427, 0, 0, quizs.Quizimages.get(i).getQuizImage().getWidth(null),
		// quizs.Quizimages.get(i).getQuizImage().getHeight(null), null);
		//
		// }
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void cycle() {
		int tmp;
		Random rand = new Random();
		String[] letterNumbers = null;

		if (solving == false) {
			grid = new int[9];
			gridAbility= new boolean[9];
			for (int i = 0; i < grid.length; i++) {
				grid[i] = 0;
				gridAbility[i]=false;
			}
			quizimgIndex = rand.nextInt(quizs.Quizimages.size());
			letterNumbers = quizs.Quizimages.get(quizimgIndex).getImageName()
					.split(" ");
			blockNum = letterNumbers.length;
			gridAnswer = new int[blockNum];
			for (int i = 0; i < gridAnswer.length; i++) {
				gridAnswer[i] = 0;
			}
			for (int i = 0; i < letterNumbers.length; i++) {
				tmp = rand.nextInt(9);
				if (grid[tmp] == 0) {
					grid[tmp] = Integer.parseInt(letterNumbers[i]);
				} else {
					while (grid[tmp] != 0) {
						tmp = rand.nextInt(9);
					}
					grid[tmp] = Integer.parseInt(letterNumbers[i]);
				}
				System.out.print("tmp..");
				System.out.print(tmp);
			}
			System.out.println();
			//int intTemp = 9 - letterNumbers.length;
			// fill the empty spaces in the grid with random letters. needs
			// optimization.
			for (int i = 0; i < grid.length; i++) {
				if (grid[i] == 0) {
					grid[i] = 1 + rand.nextInt(29);
				}
			}
			solving = true;
		}
		// check if he completed the answer
		if (answerCompleted == false) {
			int f = 1;
			for (int i = 0; i < gridAnswer.length; i++) {
				if (gridAnswer[i] == 0) {
					f = 0;
				}
			}
			if (f == 1) {
				answerCompleted = true;
			} else {
				if (inputBlock > 0) {
					//add the exclusion for repeated positions.
					for(int i=0 ;i<gridAnswer.length;i++){
						if(gridAnswer[i]==0  && !gridAbility[inputBlock-1]){
							gridAnswer[i] = grid[inputBlock-1];
							gridAbility[inputBlock-1]=true;
							break;
						}
					}
					inputBlock=0;
				}
			}
		}
		// check the gridAnswer...Evaluate the answer..
		if (!successFlag&& answerCompleted&&solving) {
			successFlag = true;
			for (int i = 0; i < gridAnswer.length; i++) {
				letterNumbers = quizs.Quizimages.get(quizimgIndex)
						.getImageName().split(" ");
				if (gridAnswer[i] != Integer.parseInt(letterNumbers[i])) {
					successFlag = false;
					gridAnswer = new int[blockNum];
					for (int j = 0; j < gridAnswer.length; j++) {
						gridAnswer[j] = 0;
					}					
					for(int j=0;j<gridAbility.length;j++){
						gridAbility[j]=false;
					}
					answerCompleted=false;
					WrongAnswer=true;
					if(starCounter==1){
						solving=false;
						starCounter=3;
					}
					else{
					starCounter--;}
					break;
				}
			}
			if (successFlag == true) {
				// animate Right answer.
				score+=5;
				RightAnswer = true;
				solving = false;
				successFlag=false;
				answerCompleted=false;
			}
		}
		if (solving == true && answerCompleted == false && successFlag == false) {	
		}
		if(posY<720&& (RightAnswer || WrongAnswer)){
			posY+=300;
			
		}
		if(posY>=720){
			posY =0;
			WrongAnswer=false;
			RightAnswer=false;
		}
		// evaluate the current solution.
		// solving=false;
		System.out.println();
		System.out.println("cycle no :" + cycleCounter);
		System.out.println("the grid : ");
		for (int i = 0; i < grid.length; i++) {
			System.out.print(grid[i] + " ");
		}
		System.out.println();
		System.out.print("ability grid : ");
		for(int i =0 ; i<gridAbility.length;i++){
			System.out.print(" " + gridAbility[i]);
		}
		System.out.println();
		System.out.print("Answer grid : ");
		for (int i=0 ;i<gridAnswer.length;i++){
			System.out.print(" "+gridAnswer[i]);
		}
		System.out.println();
		System.out.println("solving Flag:"+solving);
		System.out.println("answerCompleted Flag:" + answerCompleted);
		System.out.println("success flag: "+ successFlag);
		System.out.println("Right Answer "+RightAnswer);
		System.out.println("wrong Answer: "+ WrongAnswer);
		System.out.println("PosY :" + posY);
		cycleCounter++;
	}

	class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();
			if (keycode == '1' | keycode=='q'  | keycode=='Q' | keycode==KeyEvent.VK_NUMPAD1) {
				inputBlock=1;
				return;
			} else if (keycode == '2' | keycode=='w'  | keycode=='W' | keycode==KeyEvent.VK_NUMPAD2) {
				inputBlock=2;
				return;
			} else if (keycode == '3'| keycode=='e'  | keycode=='E' | keycode==KeyEvent.VK_NUMPAD3) {
				inputBlock=3;
				return;
			} else if (keycode == '4'| keycode=='a'  | keycode=='A' | keycode==KeyEvent.VK_NUMPAD4) {
				inputBlock=4;
				return;
			} else if (keycode == '5'| keycode=='s'  | keycode=='S' | keycode==KeyEvent.VK_NUMPAD5) {
				inputBlock=5;
				return;
			} else if (keycode == '6'| keycode=='d'  | keycode=='D' | keycode==KeyEvent.VK_NUMPAD6) {
				inputBlock=6;
				return;
			} else if (keycode == '7'| keycode=='z'  | keycode=='Z' | keycode==KeyEvent.VK_NUMPAD7) {
				inputBlock=7;
				return;
			} else if (keycode == '8' | keycode=='x'  | keycode=='X' | keycode==KeyEvent.VK_NUMPAD8) {
				inputBlock=8;
				return;
			} else if (keycode == '9'| keycode=='c'  | keycode=='C' | keycode==KeyEvent.VK_NUMPAD9) {
				inputBlock=9;
				return;
			} else if (keycode == '0') {
				endgameFlag = true;
				return;
			}
		}
	}

	public void run() {
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();
		while (true) {
			cycle();
			repaint();
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep < 0)
				sleep = 2;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
			beforeTime = System.currentTimeMillis();
		}
	}
}