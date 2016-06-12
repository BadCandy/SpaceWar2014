package SpaceWar;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

import Core.GameObject;

/**
 * ��Ȧ�� ������� �����带 �̿��Ͽ� �������� ������ Ŭ����
 * @author ����ö
 * @since 2014.12.05
 * @version 1.0
 * @see GameObject
 */
public class Bar extends JLabel{
	int barSize = 0;
	int maxBarSize;
	int width;
	/**
	 * Bar�� ������
	 * @param maxBarSize �ִ� Bar�� ����� �󸶳� �Ұ������� ���� �Ķ����
	 */
	Bar(int maxBarSize){
		this.maxBarSize = maxBarSize;
	}
	/**
	 * �ٸ� paint �ϴ� �޼ҵ�
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
	 * bar�� ����� ������� �޼ҵ�
	 * @return barSize
	 */
	public int getBarSize(){
		return barSize;
	}
	/**
	 * bar�� ����� �����ϴ� �޼ҵ�
	 * @param s bar�� size�� �󸶳� �Ұ������� ���� �Ķ����
	 */
	public void setBarSize(int s){
		barSize = s;
	}
	/**
	 * bar�� �������� ä��� �޼ҵ�
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
	 * bar�� �������� �Һ��ϴ� �޼ҵ�. �����忡 ���� �ֱ������� ����ȭ�Ǿ� �Һ�ȴ�.
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

