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
 * ���� ������Ʈ�� ��ӹ��� Bullet Ŭ����
 * @author ������
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
	 * Bomb ������
	 * @param ownerId �Ѿ��� �����ϰ� �ִ� ��ü�� id
	 * @param direction �Ѿ��� ���ư� ����
	 */
	public Bomb(long ownerId, int direction) {
		super(ResourceManager.getInstance().getImage(ResourceType.BOMB).getWidth(), ResourceManager.getInstance().getImage(ResourceType.BOMB).getHeight());
		image = ResourceManager.getInstance().getImage(ResourceType.BOMB);
		this.ownerId = ownerId;
		this.direction = direction;
	}
	/**
	 * ��ź�� ������ ��Ÿ���� ȿ�� ������
	 * @param boom Bomb ������Ʈ
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
	 * �ٸ� ������Ʈ�� �浹�ߴ����� �Ǵ��ϴ� �޼ҵ�
	 */
	public boolean check(GameObject other) {
		if (this == other)
			return false;
		if (ownerId == other.getId())	//������ ���̵�� �ٸ�������Ʈ�� ���̵� ���ٸ� false
			return false;				//�ڱ��ڽ��� �ڱ��ڽ��� �Ѿ˿� �״°��� �����ϱ� ����
		return super.check(other);
	}
	@Override
	/**
	 * ��ź�� ��ġ�� ������Ʈ �ϴ� �޼ҵ� 
	 */
	public void update() {		//������ �ӵ��� ��ź�� �̵��� �� �ְ� �Ѵ�.
		if(num==1)
			direction=0;
		location.setLocation(location.getX(), location.getY() + direction * BOMB_SPEED);

	}

	@Override
	/**
	 * ��ź�� �׸��� �޼ҵ�
	 */
	public void paint(Graphics2D g2d) {	//���� ������� ��ġ�� �׸��� �߾����� �����Ͽ� �׸���.
		int x = (int) Math.round(location.getX() - width / 2);
		int y = (int) Math.round(location.getY() - height / 2);
		g2d.drawImage(image, x, y, null);		//�׷��׷�
	}
	@Override
	/**
	 * ��ź�� ��ũ�� �ٱ����� �������� �Ǵ��ϴ� �޼ҵ�
	 */
	public boolean isOutOfScreen(int screenWidth, int screenHeight) {	//�Ѿ��� �ٱ����� ������  ���ش�.
		float minY = (float) location.getY() - height;
		float maxY = (float) location.getY() + height;
		switch (direction) {
		case PLAYER:
			return maxY < 0;
		}
		return true;
	}
	/**
	 * ���� ��ź �������� id�� ��ȯ�ϴ� �޼ҵ�
	 * @return ownerId ��
	 */
	public long getOwnerId(){
		return ownerId;
	}
	/**
	 * ��ź�� �������� ��ȯ�ϴ� �޼ҵ�
	 * @return ��ź�� ������
	 */
	public static int getDamage(){
		return damage;
	}
	/**
	 * ��ź�� ��ȣ�� ��ȯ�ϴ� �޼ҵ�
	 * @return ��ź�� ��ȣ
	 */
	public int getNum(){
		return num;
	}
	/**
	 * ��ź�� ������ ��ȯ�ϴ� �޼ҵ�
	 * @return ��ź�� ����
	 */
	public int getDirection(){
		return direction;
	}
	/**
	 * ��ź�� ������ ���� �߻���Ű�� ���� �޼ҵ�
	 * @param volunm ��ź�� ����
	 * @return ��ź ��ü
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
	 * ȿ������ Clip�� ��� �޼ҵ�
	 * @return clip
	 */
	public Clip getClip(){
		return clip;
	}
	/**
	 * �浹������ ��ȯ�ϴ� �޼ҵ�
	 */
	protected Rectangle2D getBound() {    //�浹 ������ ��ȯ�ϴ� �޼ҵ�
		return new Rectangle2D.Float((float) location.getX() - width/2, (float) location.getY() - width/2 , width, height);
	}
}