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
 * 게임 오브젝트를 상속받은 Bullet 클래스
 * @author 윤선태
 * @since 2014.11.13
 * @version 1.0
 * @see GameObject
 */
public class Bomb extends GameObject {
	public static final int PLAYER = -1;
	private static final int BOMB_SPEED = 15;
	private int direction;
	private long ownerId;
	private BufferedImage image;
	private static int damage = 10;
	private int num;
	private Clip clip;
	/**
	 * Bomb 생성자
	 * @param ownerId 총알을 소유하고 있는 객체의 id
	 * @param direction 총알이 날아갈 방향
	 */
	public Bomb(long ownerId, int direction) {
		super(ResourceManager.getInstance().getImage(ResourceType.BOMB).getWidth(), ResourceManager.getInstance().getImage(ResourceType.BOMB).getHeight());
		image = ResourceManager.getInstance().getImage(ResourceType.BOMB);
		this.ownerId = ownerId;
		this.direction = direction;
	}
	/**
	 * 폭탄이 터질때 나타나는 효과 생성자
	 * @param boom Bomb 오브젝트
	 */
	public Bomb(Bomb boom) {
		super(ResourceManager.getInstance().getImage(ResourceType.BOOM).getWidth(), ResourceManager.getInstance().getImage(ResourceType.BOOM).getHeight());
		image = ResourceManager.getInstance().getImage(ResourceType.BOOM);
		ownerId = boom.getOwnerId();
		direction = boom.getDirection();
		this.num = 1;
		location.setLocation(boom.location.getX(), boom.location.getY());
	}
	@Override


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
	@Override
	/**
	 * 폭탄의 위치를 업데이트 하는 메소드 
	 */
	public void update() {		//지정된 속도로 폭탄이 이동할 수 있게 한다.
		if(num==1)
			direction=0;
		location.setLocation(location.getX(), location.getY() + direction * BOMB_SPEED);

	}

	@Override
	/**
	 * 폭탄을 그리는 메소드
	 */
	public void paint(Graphics2D g2d) {	//현재 비행기의 위치를 그림의 중앙으로 보정하여 그린다.
		int x = (int) Math.round(location.getX() - width / 2);
		int y = (int) Math.round(location.getY() - height / 2);
		g2d.drawImage(image, x, y, null);		//그려그려
	}
	@Override
	/**
	 * 폭탄이 스크린 바깥으로 나갔는지 판단하는 메소드
	 */
	public boolean isOutOfScreen(int screenWidth, int screenHeight) {	//총알이 바깥으로 나가면  없앤다.
		float minY = (float) location.getY() - height;
		float maxY = (float) location.getY() + height;
		switch (direction) {
		case PLAYER:
			return maxY < 0;
		}
		return true;
	}
	/**
	 * 현재 폭탄 소유자의 id를 반환하는 메소드
	 * @return ownerId 값
	 */
	public long getOwnerId(){
		return ownerId;
	}
	/**
	 * 폭탄의 데미지를 반환하는 메소드
	 * @return 폭탄의 데미지
	 */
	public static int getDamage(){
		return damage;
	}
	/**
	 * 폭탄의 번호를 반환하는 메소드
	 * @return 폭탄의 번호
	 */
	public int getNum(){
		return num;
	}
	/**
	 * 폭탄의 방향을 반환하는 메소드
	 * @return 폭탄의 방향
	 */
	public int getDirection(){
		return direction;
	}
	/**
	 * 폭탄이 터지는 것을 발생시키기 위한 메소드
	 * @param volunm 폭탄의 볼륨
	 * @return 폭탄 객체
	 */
	public Bomb generateBoom(double volunm){
		AudioInputStream ais;
		try {
			ais = AudioSystem.getAudioInputStream(ResourceManager.getInstance().getAudio(ResourceType.S_BOOM));
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
		return new Bomb(this);
	}
	/**
	 * 효과음의 Clip을 얻는 메소드
	 * @return clip
	 */
	public Clip getClip(){
		return clip;
	}
	/**
	 * 충돌영역을 반환하는 메소드
	 */
	protected Rectangle2D getBound() {    //충돌 영역을 반환하는 메소드
		return new Rectangle2D.Float((float) location.getX() - width/2, (float) location.getY() - width/2 , width, height);
	}
}