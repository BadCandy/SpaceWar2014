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
 * 스테이지 클래스
 * @author 윤선태
 * @since 2014.11.13
 * @version 1.0
 */
public class Stage{
	private int level;
	private BufferedImage image;
	static private Clip clip;
	private static double volunm = 0.5;
	/**
	 * Stage 생성자
	 */
	public Stage(){

	}
	/**
	 * 메인메뉴를 set하는 메소드
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
	 * 스테이지1을 set하는 메소드
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
	 * 스테이지2를 set하는 메소드
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
	 * 스테이지3을 set하는 메소드
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
	 * 보스 스테이지를 set하는 메소드
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
	 * 스테이지의 배경음악음 끈다.
	 */
	public static void offStageSound(){
		clip.stop();
		clip.close();
	}

	/**
	 * 스테이지의 레벨을 반환하는 메소드
	 * @return 스테이지의 level
	 */
	public int getStageLevel(){
		return level;
	}

	/**
	 * 스테이지의 이미지를 반환하는 메소드
	 * @return image
	 */
	public BufferedImage getImage(){
		return image;
	}
	/**
	 * 스테이지의 배경음악을 Clip타입으로 반환한다.
	 * @return clip
	 */
	static public Clip isExeClip(){
		return clip;
	}
	/**
	 * 배경음악의 소리크기를 얻는다.
	 * @return 소리크기
	 */
	public static double getVolunm(){
		return volunm;
	}
	/**
	 * 배경음악의 소리크기를 정한다.
	 * @param v 소리크기
	 */
	public static void setVolunm(double v){
		volunm = v;
	}
}
