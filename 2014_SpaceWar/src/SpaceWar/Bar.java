package SpaceWar;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

import Core.GameObject;

/**
 * 블랙홀을 쏘기위해 쓰레드를 이용하여 게이지를 구현한 클래스
 * @author 정윤철
 * @since 2014.12.05
 * @version 1.0
 * @see GameObject
 */
public class Bar extends JLabel{
	int barSize = 0;
	int maxBarSize;
	int width;
	/**
	 * Bar의 생성자
	 * @param maxBarSize 최대 Bar의 사이즈를 얼마나 할것인지에 대한 파라미터
	 */
	Bar(int maxBarSize){
		this.maxBarSize = maxBarSize;
	}
	/**
	 * 바를 paint 하는 메소드
	 */
	public void paint(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.ORANGE);
		g.fillRect(30, 35, this.getWidth(), this.getHeight());
		
		width = (int)(((double)this.getWidth())/maxBarSize*barSize);
		if(barSize < 95){ //285
			g.setColor(Color.BLUE);
		}
		else{
			g.setColor(Color.MAGENTA);
		}
		if(width==0) 
		return;
		g.fillRect(30, 35, width, this.getHeight());

	}
	/**
	 * bar의 사이즈를 얻기위한 메소드
	 * @return barSize
	 */
	public int getBarSize(){
		return barSize;
	}
	/**
	 * bar의 사이즈를 세팅하는 메소드
	 * @param s bar의 size를 얼마나 할것인지에 대한 파라미터
	 */
	public void setBarSize(int s){
		barSize = s;
	}
	/**
	 * bar의 게이지를 채우는 메소드
	 */
	synchronized void fill(){
		if(barSize >= maxBarSize-1) {
		//	try{
				barSize = 99;
		//	}catch(InterruptedException e){
		//		return;
		//	}
		}
		barSize++;
		repaint();
		notify();
	}
	
	/**
	 * bar의 게이지를 소비하는 메소드. 쓰레드에 의해 주기적으로 동기화되어 소비된다.
	 */
	synchronized void consume(){
		if(barSize == 0){
			try{
				wait();
			} catch(InterruptedException e){
				return;
			}
		}
		barSize--;
		repaint();
		notify();
	}
}

class MinusThread extends Thread{
	Bar bar;
	
	MinusThread(Bar bar){
		this.bar = bar;
	}
	
	public void run(){
		while(true){
			try{
				sleep(100);
				bar.consume();
			}catch(InterruptedException e){
				return;
			}
		}
	}
}

