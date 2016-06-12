package Core;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Random;

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
import Util.Bullet;
/**
 * Ship�� ��ӹ��� ���⿡ ���� Ŭ����
 * @author ������
 * @since 2014.11.13
 * @version 1.0
 * @see GameObject
 * @see Ship
 * @see Bullet
 */
public class Enemy extends Ship {
	private static final int BULLET_Y_POS_MARGIN = 8;
	private static final int RANDOM_LIFETIME = 50;
	private static final int BASE_LIFETIME = 50;
	private static double spawnTick;
	private int bossNumber;
	private int b=1;
	private int life;
	private double x;
	private double y;
	private int itemEnemyNumber;
	private int enemyNumber;
	private Point2D moveDelta = new Point2D.Float();
	private Bullet bullet;
	private int booomNumber;
	private Clip clip;
	
	/**
	 * �Ϲ� ���⿡ ���� ������. �̹����� ������ ����Ŭ������ �־��ش�.
	 * @param location ������ ó�� ��ġ
	 * @param moveDelta �ð��� �󸶳� �̵������� ���� ��
	 */
	public Enemy(Point2D location, Point2D moveDelta) {
		super(ResourceManager.getInstance().getImage(ResourceType.ENEMY_SHIP));
		this.location.setLocation(location);
		this.moveDelta.setLocation(moveDelta);
		enemyNumber = 1;
		life = 0;
		setEnemyCode();
	}
	/**
	 * �������� ���� ���⿡ ���� ������. �̹����� ������ ����Ŭ������ �־��ش�.
	 * @param location ó����ġ
	 * @param moveDelta �ð��� �󸶳� �̵������� ���� ��
	 * @param itemNum �ٸ� ����� �����ϱ� ���Ͽ� �޴� ����
	 */
	public Enemy(Point2D location, Point2D moveDelta, double itemNum) {
		super(ResourceManager.getInstance().getImage(ResourceType.ITEM_ENEMY));
		this.location.setLocation(location);
		this.moveDelta.setLocation(moveDelta);
		life = 1;
		itemEnemyNumber = 1;
		setEnemyCode();
	}
	/**
	 * ���� ���⿡ ���� ������. �̹����� ������ ����Ŭ������ �־��ش�.
	 * @param location ������ ó�� ��ġ
	 * @param moveDelta �ð��� �󸶳� �̵������� ���� ��
	 * @param bossNum �ش��ϴ� ���������� �����ѹ�
	 */
	public Enemy(Point2D location, Point2D moveDelta, int bossNum){
		super(ResourceManager.getInstance().getImage(ResourceType.BOSS1));
		this.location.setLocation(location);
		this.moveDelta.setLocation(moveDelta);
		life = 50;
		setEnemyCode();
		bossNumber = bossNum;
	}
	/**
	 * ���Ⱑ ������ �߻��ϴ� ��� ������. �̹����� ������ ����Ŭ������ �־��ش�.
	 * @param enemy �ش��ϴ� ���������� ����
	 */
	public Enemy(Enemy enemy){
		super(ResourceManager.getInstance().getImage(ResourceType.E_BOOM));
		this.location.setLocation(enemy.location);
		life = 2;
		booomNumber=1;
		setEnemyCode();
	}
	/**
	 * ���Ⱑ ������ �߻��ϴ� ����� ��ȯ�ϴ� �޼ҵ�
	 * @param volunm ȿ������ �Ҹ�ũ��
	 * @return ���Ⱑ ������ �߻��ϴ� ���
	 */
	public Enemy generateBooom(double volunm){
		AudioInputStream ais;
		try {
			ais = AudioSystem.getAudioInputStream(ResourceManager.getInstance().getAudio(ResourceType.S_BOOOM));
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
		return new Enemy(this);
	}
	/**
	 * ȿ���� Clip���� �����ϴ� �޼ҵ�
	 * @return clip ����
	 */
	public Clip getClip(){
		return clip;
	}
	/**
	 * ������ ����� ���ҽ�Ű�� �޼ҵ�
	 * @param damage ������ ����� �󸶳� ���ҽ�ų�������� �Ķ����
	 */
	public void decreaseEnemyLife(int damage){
		life = life-damage;
	}
	/**
	 * ���Ⱑ �������� ��� �÷��׸� ��ȯ
	 * @return booomNumber ��ȯ
	 */
	public int getBooomNum(){
		return booomNumber;
	}
	/**
	 * ������ ����� �����ϴ� �޼ҵ�
	 * @param life ������ �������� ��ŭ �����Ұ��ΰ��� �Ķ����
	 */
	public void setEnemyLife(int life){
		this.life = life;
	}
	/**
	 * ������ ����� ��ȯ�ϴ� �޼ҵ�
	 * @return life ��ȯ
	 */
	public int getEnemyLife(){
		return life;
	}
	@Override
	/**
	 * ������ �̵���ġ�� ���� ������Ʈ �޼ҵ�
	 */
	public void update() {		//�̵���ġ�� ���� ������Ʈ
		x = location.getX() + b*moveDelta.getX();
		y = location.getY() + moveDelta.getY();
		if(x > 640 && bossNumber > 0 && b == 1){
			b = -1;
		}
		if(x < 0 && bossNumber > 0 && b == -1){
			b = 1;
		}

		if(y > 150 && bossNumber > 0){
			y=150; 
		}

		location.setLocation(x, y);
	}
	/**
	 * ���� ������ X��ǥ�� ��ȯ�ϴ� �޼ҵ�
	 * @return ���� ������ x��ǥ
	 */
	public double getX(){
		return x;
	}
	/**
	 * ���� ������ Y��ǥ�� ��ȯ�ϴ� �޼ҵ�
	 * @return ���� ������ Y��ǥ
	 */
	public double getY(){
		return y;
	}
	/**
	 * ������ ź �߻翡 �����̸� �ִ� �޼ҵ�
	 * @return ź�� �� �� ������ true, ź�� �� �� ������ false
	 */
	public boolean canShot() {	//���� ��� �ִ�?
		if(booomNumber == 1)
			return false;
		return (Math.random() < 0.05);
	}
	/**
	 * ���Ⱑ ź�� ����ϴ� �޼ҵ�
	 * @return Bullet�� ����
	 */
	public Bullet shot() {		//����� �Ѿ��� ����
		bullet = new Bullet(getId(), Bullet.ENEMY);
		bullet.setLocation((float) location.getX(), (float) location.getY() + height / 2 + BULLET_Y_POS_MARGIN);
		bullet.setEnemyCode();
		return bullet;
	}
	@Override

	/**
	 * ȭ������� ������ �� ���⸦ ó���ϴ� �޼ҵ�
	 */
	public boolean isOutOfScreen(int screenWidth, int screenHeight) {		//������ ��������
		float minX = (float) location.getX() - width / 2;
		float minY = (float) location.getY() - height / 2;
		return minX > screenWidth || minY > screenHeight;
	}
	/**
	 * ���� ��ݿ� �����̸� �ִ� �޼ҵ�
	 * @return ���Ⱑ ��ݰ����ϸ� true, �Ұ����ϸ� false
	 */
	public static boolean isSpawnable() {
		return spawnTick < Math.random();
	}
	/**
	 * ������ ��ȣ�� ��ȯ�ϴ� �޼ҵ�
	 * @return ���� ��ȣ
	 */
	public int getEnemyNumber(){
		return enemyNumber;
	}
	/**
	 * ���������� �ش��ϴ� �����ѹ��� ��ȯ�ϴ� �޼ҵ�
	 * @return bossNumber�� ��ȯ
	 */
	public int getBossNumber(){
		return bossNumber;
	}
	/**
	 * ������ �����۳ѹ��� ��ȯ�ϴ� �޼ҵ�
	 * @return itemEnemyNumber
	 */
	public int getItemEnemyNumber(){
		return itemEnemyNumber;
	}
	/**
	 * ������ �ѹ��� ���ϴ� �޼ҵ�
	 * @param n ������ �����ѹ��� �������� �Ұ������� ���� �Ķ����
	 */
	public void setBossNumber(int n){
		bossNumber = n;
	}
	/**
	 * ��Ȧ�� �߻������� ������ �������� ��� �ɰ����� ���ϴ� �޼ҵ�
	 */
	public void setBlackHollMoveDelta(){
		Random rand = new Random();
		int startX = (int)location.getX();
		int endX = 325;
		int startY = (int)location.getY();
		int endY = 400;
		float lifetime = 0.5f * RANDOM_LIFETIME + BASE_LIFETIME -30;
		moveDelta.setLocation((endX - startX)/lifetime, (endY - startY)/lifetime);
	}

	/**
	 * ���⸦ ��ݽ�Ű�� �޼ҵ�
	 * @param screenWidth ���� ��ũ���� ��
	 * @param screenHeight ���� ��ũ���� ����
	 * @return Enemy ��ü ��ȯ
	 */
	public static Enemy spawn(int screenWidth, int screenHeight) {
		spawnTick = 0.975;
		Random rand = new Random();
		int shipHeight = ResourceManager.getInstance().getImage(ResourceType.ENEMY_SHIP).getHeight();
		float lifetime = rand.nextFloat() * RANDOM_LIFETIME + BASE_LIFETIME + 150;	
		int startX = rand.nextInt(screenWidth);	
		int endX = rand.nextInt(screenWidth); 
		Point2D location = new Point2D.Float(startX, -1 * shipHeight);		
		Point2D moveDelta = new Point2D.Float((endX - startX) / lifetime, (screenHeight + shipHeight * 2) / lifetime);	//�̵��϶� (endX - startX) / lifetime  , (screenHeight + shipHeight * 2) / lifetime
		return new Enemy(location, moveDelta);	
	}
	/**
	 * �������� ���� ���⸦ ��ݽ�Ű�� �޼ҵ�
	 * @param screenWidth ���� ��ũ���� ��
	 * @param screenHeight ���� ��ũ���� ����
	 * @return �������� ���� ���� ��ü�� ��ȯ
	 */
	public static Enemy itemEnemySpawn(int screenWidth, int screenHeight) {
		Random rand = new Random();
		int shipHeight = ResourceManager.getInstance().getImage(ResourceType.ITEM_ENEMY).getHeight();
		float lifetime = rand.nextFloat() * RANDOM_LIFETIME + BASE_LIFETIME + 150;	
		int startX = rand.nextInt(screenWidth);	
		int endX = rand.nextInt(screenWidth);
		Point2D location = new Point2D.Float(startX, -1 * shipHeight);
		Point2D moveDelta = new Point2D.Float((endX - startX) / lifetime, (screenHeight + shipHeight * 2) / lifetime);	//�̵��϶�
		return new Enemy(location, moveDelta, Math.random());	//��ư� 7���� ���� �����ߵ��� ���� ����϶�!
	}
	/**
	 * ������ ��ݽ�Ű�� �޼ҵ�
	 * @param screenWidth ���� ��ũ���� ��
	 * @param screenHeight ���� ��ũ���� ����
	 * @return ������ ��ü�� ��ȯ
	 */
	public static Enemy bossSpawn(int screenWidth, int screenHeight) {
		Random rand = new Random();
		int shipHeight = ResourceManager.getInstance().getImage(ResourceType.BOSS1).getHeight();
		float lifetime = rand.nextFloat() * RANDOM_LIFETIME + BASE_LIFETIME + 150;
		int startX = rand.nextInt(screenWidth);	//�������� ��������
		Point2D location = new Point2D.Float(startX, -1 * shipHeight);
		Point2D moveDelta = new Point2D.Float(5 , (screenHeight + shipHeight * 2) /50);
		return new Enemy(location, moveDelta, 1);	//��ư� 7���� ���� �����ߵ��� ���� ����϶�!
	}
}

