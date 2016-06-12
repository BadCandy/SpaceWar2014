package Util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import SpaceWar.ResourceManager;
import SpaceWar.ResourceType;

/**
 * �������� Ŭ����
 * @author ������
 * @since 2014.11.13
 * @version 1.0
 */
public class Stage{
	private int level;
	private BufferedImage image;
	static private Clip clip;
	private static double volunm = 0.5;
	/**
	 * Stage ������
	 */
	public Stage(){

	}
	/**
	 * ���θ޴��� set�ϴ� �޼ҵ�
	 */
	public void setMainMenu(){
		AudioInputStream ais;
		try {
			ais = AudioSystem.getAudioInputStream(ResourceManager.getInstance().getAudio(ResourceType.S_MAINMENU));
			clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
			clip.open(ais);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (Math.log(volunm) / Math.log(10.0) * 20.0);
			gainControl.setValue(dB);	
			BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
			muteControl.setValue(false);
			clip.start();
		}catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ��������1�� set�ϴ� �޼ҵ�
	 */
	public void setStage1(){
		AudioInputStream ais;
		try {
			ais = AudioSystem.getAudioInputStream(ResourceManager.getInstance().getAudio(ResourceType.S_STAGE1));
			clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
			clip.open(ais);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (Math.log(volunm) / Math.log(10.0) * 20.0);
			gainControl.setValue(dB);	
			BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
			muteControl.setValue(false);
			clip.start();
		}catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		image = ResourceManager.getInstance().getImage(ResourceType.STAGE1);
		level=1;
	}
	/**
	 * ��������2�� set�ϴ� �޼ҵ�
	 */
	public void setStage2(){
		AudioInputStream ais;
		try {
			ais = AudioSystem.getAudioInputStream(ResourceManager.getInstance().getAudio(ResourceType.S_STAGE2));
			clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
			clip.open(ais);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (Math.log(volunm) / Math.log(10.0) * 20.0);
			gainControl.setValue(dB);	
			BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
			muteControl.setValue(false);
			clip.start();
		}catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		image = ResourceManager.getInstance().getImage(ResourceType.STAGE2);
		level=2;
	}
	/**
	 * ��������3�� set�ϴ� �޼ҵ�
	 */
	public void setStage3(){
		AudioInputStream ais;
		try {
			ais = AudioSystem.getAudioInputStream(ResourceManager.getInstance().getAudio(ResourceType.S_STAGE3));
			clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
			clip.open(ais);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (Math.log(volunm) / Math.log(10.0) * 20.0);
			gainControl.setValue(dB);	
			BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
			muteControl.setValue(false);
			clip.start();
		}catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		image = ResourceManager.getInstance().getImage(ResourceType.STAGE3);
		level=3;
	}
	/**
	 * ���� ���������� set�ϴ� �޼ҵ�
	 */
	public void setBossStage(){
		AudioInputStream ais;
		try {
			ais = AudioSystem.getAudioInputStream(ResourceManager.getInstance().getAudio(ResourceType.S_BOSS));
			clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
			clip.open(ais);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (Math.log(volunm) / Math.log(10.0) * 20.0);
			gainControl.setValue(dB);	
			BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
			muteControl.setValue(false);
			clip.start();
		}catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ���������� ��������� ����.
	 */
	public static void offStageSound(){
		clip.stop();
		clip.close();
	}

	/**
	 * ���������� ������ ��ȯ�ϴ� �޼ҵ�
	 * @return ���������� level
	 */
	public int getStageLevel(){
		return level;
	}

	/**
	 * ���������� �̹����� ��ȯ�ϴ� �޼ҵ�
	 * @return image
	 */
	public BufferedImage getImage(){
		return image;
	}
	/**
	 * ���������� ��������� ClipŸ������ ��ȯ�Ѵ�.
	 * @return clip
	 */
	static public Clip isExeClip(){
		return clip;
	}
	/**
	 * ��������� �Ҹ�ũ�⸦ ��´�.
	 * @return �Ҹ�ũ��
	 */
	public static double getVolunm(){
		return volunm;
	}
	/**
	 * ��������� �Ҹ�ũ�⸦ ���Ѵ�.
	 * @param v �Ҹ�ũ��
	 */
	public static void setVolunm(double v){
		volunm = v;
	}
}
