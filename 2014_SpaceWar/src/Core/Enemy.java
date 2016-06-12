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
 * Ship을 상속받은 적기에 관한 클래스
 * @author 김현준
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
	 * 일반 적기에 대한 생성자. 이미지를 가져와 상위클래스에 넣어준다.
	 * @param location 적기의 처음 위치
	 * @param moveDelta 시간당 얼마나 이동할지에 관한 값
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
	 * 아이템을 가진 적기에 대한 생성자. 이미지를 가져와 상위클래스에 넣어준다.
	 * @param location 처음위치
	 * @param moveDelta 시간당 얼마나 이동할지에 관한 값
	 * @param itemNum 다른 적기와 구별하기 위하여 받는 인자
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
	 * 보스 적기에 대한 생성자. 이미지를 가져와 상위클래스에 넣어준다.
	 * @param location 보스의 처음 위치
	 * @param moveDelta 시간당 얼마나 이동할지에 관한 값
	 * @param bossNum 해당하는 스테이지의 보스넘버
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
	 * 적기가 터질때 발생하는 모션 생성자. 이미지를 가져와 상위클래스에 넣어준다.
	 * @param enemy 해당하는 스테이지의 적기
	 */
	public Enemy(Enemy enemy){
		super(ResourceManager.getInstance().getImage(ResourceType.E_BOOM));
		this.location.setLocation(enemy.location);
		life = 2;
		booomNumber=1;
		setEnemyCode();
	}
	/**
	 * 적기가 터질때 발생하는 모션을 반환하는 메소드
	 * @param volunm 효과음의 소리크기
	 * @return 적기가 터질때 발생하는 모션
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
	 * 효과음 Clip파일 리턴하는 메소드
	 * @return clip 리턴
	 */
	public Clip getClip(){
		return clip;
	}
	/**
	 * 적기의 목숨을 감소시키는 메소드
	 * @param damage 적기의 목숨을 얼마나 감소시킬것인지의 파라미터
	 */
	public void decreaseEnemyLife(int damage){
		life = life-damage;
	}
	/**
	 * 적기가 터질때의 모션 플래그를 반환
	 * @return booomNumber 반환
	 */
	public int getBooomNum(){
		return booomNumber;
	}
	/**
	 * 적기의 목숨을 세팅하는 메소드
	 * @param life 적기의 라이프를 얼만큼 세팅할것인가의 파라미터
	 */
	public void setEnemyLife(int life){
		this.life = life;
	}
	/**
	 * 적기의 목숨을 반환하는 메소드
	 * @return life 반환
	 */
	public int getEnemyLife(){
		return life;
	}
	@Override
	/**
	 * 적기의 이동위치에 대한 업데이트 메소드
	 */
	public void update() {		//이동위치에 대한 업데이트
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
	 * 현재 적기의 X좌표를 반환하는 메소드
	 * @return 현재 적기의 x좌표
	 */
	public double getX(){
		return x;
	}
	/**
	 * 현재 적기의 Y좌표를 반환하는 메소드
	 * @return 현재 적기의 Y좌표
	 */
	public double getY(){
		return y;
	}
	/**
	 * 적기의 탄 발사에 딜레이를 주는 메소드
	 * @return 탄을 쏠 수 있으면 true, 탄을 쏠 수 없으면 false
	 */
	public boolean canShot() {	//내가 쏠수 있니?
		if(booomNumber == 1)
			return false;
		return (Math.random() < 0.05);
	}
	/**
	 * 적기가 탄을 쏘게하는 메소드
	 * @return Bullet을 리턴
	 */
	public Bullet shot() {		//적기야 총알을 쏘자
		bullet = new Bullet(getId(), Bullet.ENEMY);
		bullet.setLocation((float) location.getX(), (float) location.getY() + height / 2 + BULLET_Y_POS_MARGIN);
		bullet.setEnemyCode();
		return bullet;
	}
	@Override

	/**
	 * 화면밖으로 나갔을 때 적기를 처리하는 메소드
	 */
	public boolean isOutOfScreen(int screenWidth, int screenHeight) {		//밖으로 나갔는지
		float minX = (float) location.getX() - width / 2;
		float minY = (float) location.getY() - height / 2;
		return minX > screenWidth || minY > screenHeight;
	}
	/**
	 * 적기 출격에 딜레이를 주는 메소드
	 * @return 적기가 출격가능하면 true, 불가능하면 false
	 */
	public static boolean isSpawnable() {
		return spawnTick < Math.random();
	}
	/**
	 * 적기의 번호를 반환하는 메소드
	 * @return 적기 번호
	 */
	public int getEnemyNumber(){
		return enemyNumber;
	}
	/**
	 * 스테이지에 해당하는 보스넘버를 반환하는 메소드
	 * @return bossNumber를 반환
	 */
	public int getBossNumber(){
		return bossNumber;
	}
	/**
	 * 적기의 아이템넘버를 반환하는 메소드
	 * @return itemEnemyNumber
	 */
	public int getItemEnemyNumber(){
		return itemEnemyNumber;
	}
	/**
	 * 보스의 넘버를 정하는 메소드
	 * @param n 적기의 보스넘버를 무엇으로 할것인지에 대한 파라미터
	 */
	public void setBossNumber(int n){
		bossNumber = n;
	}
	/**
	 * 블랙홀이 발생했을때 적기의 움직임이 어떻게 될것인지 정하는 메소드
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
	 * 적기를 출격시키는 메소드
	 * @param screenWidth 현재 스크린의 폭
	 * @param screenHeight 현재 스크린의 높이
	 * @return Enemy 객체 반환
	 */
	public static Enemy spawn(int screenWidth, int screenHeight) {
		spawnTick = 0.975;
		Random rand = new Random();
		int shipHeight = ResourceManager.getInstance().getImage(ResourceType.ENEMY_SHIP).getHeight();
		float lifetime = rand.nextFloat() * RANDOM_LIFETIME + BASE_LIFETIME + 150;	
		int startX = rand.nextInt(screenWidth);	
		int endX = rand.nextInt(screenWidth); 
		Point2D location = new Point2D.Float(startX, -1 * shipHeight);		
		Point2D moveDelta = new Point2D.Float((endX - startX) / lifetime, (screenHeight + shipHeight * 2) / lifetime);	//이동하라 (endX - startX) / lifetime  , (screenHeight + shipHeight * 2) / lifetime
		return new Enemy(location, moveDelta);	
	}
	/**
	 * 아이템을 가진 적기를 출격시키는 메소드
	 * @param screenWidth 현재 스크린의 폭
	 * @param screenHeight 현재 스크린의 높이
	 * @return 아이템을 가진 적기 객체를 반환
	 */
	public static Enemy itemEnemySpawn(int screenWidth, int screenHeight) {
		Random rand = new Random();
		int shipHeight = ResourceManager.getInstance().getImage(ResourceType.ITEM_ENEMY).getHeight();
		float lifetime = rand.nextFloat() * RANDOM_LIFETIME + BASE_LIFETIME + 150;	
		int startX = rand.nextInt(screenWidth);	
		int endX = rand.nextInt(screenWidth);
		Point2D location = new Point2D.Float(startX, -1 * shipHeight);
		Point2D moveDelta = new Point2D.Float((endX - startX) / lifetime, (screenHeight + shipHeight * 2) / lifetime);	//이동하라
		return new Enemy(location, moveDelta, Math.random());	//어렵게 7조가 너희를 생산했도다 이제 출격하라!
	}
	/**
	 * 보스를 출격시키는 메소드
	 * @param screenWidth 현재 스크린의 폭
	 * @param screenHeight 현재 스크린의 높이
	 * @return 보스의 객체를 반환
	 */
	public static Enemy bossSpawn(int screenWidth, int screenHeight) {
		Random rand = new Random();
		int shipHeight = ResourceManager.getInstance().getImage(ResourceType.BOSS1).getHeight();
		float lifetime = rand.nextFloat() * RANDOM_LIFETIME + BASE_LIFETIME + 150;
		int startX = rand.nextInt(screenWidth);	//시작지점 랜덤지정
		Point2D location = new Point2D.Float(startX, -1 * shipHeight);
		Point2D moveDelta = new Point2D.Float(5 , (screenHeight + shipHeight * 2) /50);
		return new Enemy(location, moveDelta, 1);	//어렵게 7조가 너희를 생산했도다 이제 출격하라!
	}
}

