package Util;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
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

import Core.GameObject;
import SpaceWar.ResourceManager;
import SpaceWar.ResourceType;

/**
 * 블랙홀을 발생시키기 위한 클래스
 * @author 윤선태
 * @version 1.0
 */
public class BlackHoll extends GameObject{
	private long ownerId;
	private BufferedImage image;
	private int life;
	private Clip clip;

	/**
	 * 블랙홀 생성자
	 * @param ownerId 플레이어 id
	 * @param volunm 소리
	 */
	public BlackHoll(long ownerId, double volunm) {
		super(ResourceManager.getInstance().getImage(ResourceType.BLACKHOLL).getWidth(), ResourceManager.getInstance().getImage(ResourceType.BLACKHOLL).getHeight());
		image = ResourceManager.getInstance().getImage(ResourceType.BLACKHOLL);
		this.ownerId = ownerId;
		AudioInputStream ais;
		try {
			ais = AudioSystem.getAudioInputStream(ResourceManager.getInstance().getAudio(ResourceType.S_BLACKHOLE));
			clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));
			clip.open(ais);
			FloatControl gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);

			double gain = volunm; // number between 0 and 1 (loudest)
			float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
			gainControl.setValue(dB);

			BooleanControl muteControl = (BooleanControl) clip
					.getControl(BooleanControl.Type.MUTE);
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
		life = 10;
	}
	/**
	 * 블랙홀의 life를 얻는다.
	 * @return life
	 */
	public int getLife(){
		return life;
	}
	/**
	 * 블랙홀의 life를 정한다.
	 * @param l 블랙홀의 라이프
	 * @return 블랙홀의 라이프가 0이 �瑛뻑㎱� boolean값
	 */
	public boolean setLife(int l){
		life = l;
		if(life == 0)
		return true;
		else return false;
	}

	/**
	 * 다른 오브젝트와 충돌했는지를 판단하는 메소드
	 */
	public boolean check(GameObject other) {
		if (this == other)
			return false;
		if (ownerId == other.getId())	//소유자 아이디와 다른오브젝트의 아이디가 같다면 false
			return false;				//자기자신이 자기자신의 총알에 죽는것을 방지하기 위함
		return super.check(other);
	}
	
	/**
	 * 블랙홀의 위치를 업데이트하는 메소드
	 */
	public void update() {
		location.setLocation(325, 400);
	}

	@Override
	/**
	 * 블랙홀을 그리는 메소드
	 */
	public void paint(Graphics2D g2d) {
		int x = (int) Math.round(325 - width / 2);
		int y = (int) Math.round(400 - height / 2);
		g2d.drawImage(image, x, y, null);
	}
	@Override
	/**
	 * 총알이 스크린 바깥으로 나갔는지 판단하는 메소드
	 */
	public boolean isOutOfScreen(int screenWidth, int screenHeight) {
		return false;
	}
	/**
	 * 현재 총알 소유자의 id를 반환하는 메소드
	 * @return ownerId 값
	 */
	public long getOwnerId(){
		return ownerId;
	}

	protected Rectangle2D getBound() {    //충돌 영역을 반환하는 메소드
		return new Rectangle2D.Float((float) location.getX() - width/2, (float) location.getY() - width/2 , width, height);
	}
}
