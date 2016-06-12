package Util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Core.Player;
import SpaceWar.ResourceManager;
import SpaceWar.ResourceType;
import SpaceWar.SpaceWar2014;

/**
 * 순위를 나타내는 패널. 그 외에 부가기능 버튼이 있다.
 * @author 윤선태
 * @since 2014.11.13
 * @version 1.0
 * @see ResourceManager
 * @see ResourceType
 * @see SpaceWar2014
 */
public class Rank extends JPanel{
	private SpaceWar2014 game;
	private BufferedImage backGround;
	private JLabel rankLabel;
	private JButton exitBtn;
	private String rank = "";
	private Vector<String> v;
	private String playerName = "";
	private JLabel rankLabel2;
	private JLabel rankLabel3;
	private JLabel rankLabel4;


	/**
	 * Rank 패널의 생성자. Background의 이미지를 자신의 background 필드에 입력한다.
	 * @param f 게임프레임
	 */
	public Rank(SpaceWar2014 f){
		setSize(640, 700);
		setLayout(null);
		game = f;
		v = new Vector<String>();
		backGround = ResourceManager.getInstance().getImage(
				ResourceType.BACKGROUND2);

		ImageIcon Rank = new ImageIcon("Ranking2.jpg");
		rankLabel = new JLabel(Rank);
		rankLabel.setLocation(110, 30);
		rankLabel.setSize(400, 70);
		add(rankLabel);   

		ImageIcon Exit = new ImageIcon("Exit.jpg");
		exitBtn = new JButton(Exit);
		exitBtn.setLocation(190,530);
		exitBtn.setSize(250,100);
		//		exitBtn.setBounds(220,550,200,70); 
		exitBtn.setContentAreaFilled(false);
		exitBtn.setBorderPainted(false);

		add(exitBtn);

		makeTextField();
		exitBtn.addActionListener(new ActionListener() {
			/**
			 * 버튼을 눌렀을 때 메인메뉴 패널로 전환하게 하는 메소드
			 */
			public void actionPerformed(ActionEvent event) {
				game.change("MainMenu");
			}
		});
	}

	@Override
	/**
	 * Rank 패널을 그리는 메소드
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(backGround, 0, 0, this);
		if(v.size() >= 1){
			g.setColor(Color.PINK);
			g.setFont(new Font("Helvetica",Font.ITALIC,50));
			g.drawString(v.get(0),230,287);
		}
		if(v.size() >= 2){
			g.setColor(Color.PINK);
			g.setFont(new Font("Helvetica",Font.PLAIN,50));
			g.drawString(v.get(1),230,375);
		}		
		if(v.size() >= 3){
			g.setColor(Color.PINK);
			g.setFont(new Font("Helvetica",Font.PLAIN,50));
			g.drawString(v.get(2),230,470);
		}
		repaint();
	}
	/**
	 * 랭킹이 저장되어 있는 파일을 읽는 메소드
	 * @param pName 플레이어 이름
	 * @param score 플레이어 점수
	 * @throws Exception IOException
	 */
	public void writeRank(String pName,int score) throws Exception{
		String playerName = pName;
		FileWriter fw;
		fw = new FileWriter("rank.txt", true);
		String s = score + "    " + playerName + '\n';
		//		System.out.println(rank);
		//		rank += s;
		fw.write(s);
		fw.close();
	}
	/**
	 * 랭킹들을 sort하는 메소드
	 */
	public void sortRank(){
		if(v.size() >= 2){
			for (int i = 0; i < v.size()-1; i++) {
				String max = v.get(i);
				for (int j = i+1; j < v.size(); j++) {
					int del1Location = v.get(i).indexOf(" ");
					int del2Location = v.get(j).indexOf(" ");
					String sub1 = v.get(i).substring(0, del1Location);
					String sub2 = v.get(j).substring(0, del2Location);
					int score1 = Integer.parseInt(sub1);
					int score2 = Integer.parseInt(sub2);

					if(score1 < score2){
						String temp = v.get(i);
						v.set(i, v.get(j));
						v.set(j, temp);
					}
				}

			}
		}
	}

	/**
	 * 랭킹을 파일로부터 읽어오는 메소드
	 * @return String형 랭킹 반환
	 * @throws Exception IOException
	 */
	public String readRank() throws Exception{
		FileReader fr;
		fr = new FileReader("rank.txt");
		int input = fr.read();
		String line = "";
		while(input != -1) {
			line += (char)input;
			if((char)input == '\n'){
				v.add(line);
				rank += line;
				line = "";
			}
			input = fr.read();
		}
		fr.close();
		return rank;
	}
	/**
	 * 플레이어의 점수를 받아들이는 텍스트 필드를 만드는 메소드
	 */
	public void makeTextField(){
		try {
			readRank();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sortRank();
		final JTextField tf = new JTextField(3);
		if(Player.getScore() == 0){
			tf.setEditable(false);
			tf.setFocusable(false);
		}
		for (int i = 0; i < v.size(); i++) {

			System.out.println(v.get(i));	
		}
		JLabel label1 = new JLabel("Player Name : ");
		label1.setFont(new Font("Helvetica",Font.PLAIN,30));
		label1.setForeground(Color.ORANGE);
		label1.setLocation(100,30);
		label1.setSize(500,300);

		JLabel label2 = new JLabel("1st : ");
		label2.setFont(new Font("Helvetica",Font.PLAIN,50));
		label2.setForeground(Color.BLUE);
		label2.setLocation(100,120);
		label2.setSize(500,300);

		JLabel label3 = new JLabel("2nd : ");
		label3.setFont(new Font("Helvetica",Font.PLAIN,50));
		label3.setForeground(Color.BLUE);
		label3.setLocation(100,210);
		label3.setSize(500,300);

		JLabel label4 = new JLabel("3rd : ");
		label4.setFont(new Font("Helvetica",Font.PLAIN,50));
		label4.setForeground(Color.BLUE);
		label4.setLocation(100,300);
		label4.setSize(500,300);

		add(label1);
		add(label2);
		add(label3);
		add(label4);
		add(tf);
		tf.setSize(150,30);
		tf.setLocation(300,165);
		tf.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				playerName = tf.getText();
				
				tf.setEditable(false);
				tf.setFocusable(false);
				try {
					writeRank(playerName, Player.getScore());
					v.clear();
					readRank();
					sortRank();
					repaint();
				} catch (Exception a) {
					// TODO Auto-generated catch block
					a.printStackTrace();
				}
			}
		});
	}
}
