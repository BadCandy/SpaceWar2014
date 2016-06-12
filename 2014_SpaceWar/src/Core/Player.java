package Core;

import SpaceWar.ResourceManager;
import SpaceWar.ResourceType;
import Util.Bomb;
import Util.Bullet;

/**
 * Ship을 상속받은 player에 관한 클래스
 * @author 김현준
 * @since 2014.11.13
 * @version 1.0
 * @see Ship
 * @see GameObject
 * @see Bullet
 */
public class Player extends Ship {
	private static final int BULLET_Y_POS_MARGIN = 8;
	private static final int SHOT_DELAY = 200;
	private static final int SPEED = 5;
	private long lastShotTick = 0;
	private long bombLastShotTick = 0;
	static private int score;
	private Bullet bullet;
	private Bomb bomb;
	private PlayerInfo playerInfo;
	/**
	 * Player의 생성자. 이미지를 가져와 상위클래스 생성자에 넣어준다.
	 * @param p 플레이어정보
	 */
	public Player(PlayerInfo p) {
		super(ResourceManager.getInstance().getImage(ResourceType.PLAYER_SHIP)); //이미지를 가져와 상위클래스 생성자에 넣어준다.
		score = 0;
		playerInfo = p;
	}
	@Override
	/**
	 * 플레이어의 위치를 업데이트 하는 메소드
	 */
	public void update() {
	}
	/**
	 * 플레이어를 움직이게 하는 메소드
	 * @param dpressed 두개의 키가 눌러졌는지에 대한 boolean값
	 * @param up 위로 이동하는 키가 눌러졌는지에 대한 boolean값
	 * @param down 아래로 이동하는 키가 눌러졌는지에 대한 boolean값
	 * @param left 왼쪽으로 이동하는 키가 눌러졌는지에 대한 boolean값
	 * @param right 오른쪽으로 이동하는 키가 눌러졌는지에 대한 boolean값
	 */
	public void move(boolean dpressed, boolean up, boolean down, boolean left, boolean right) {
		if(dpressed == true){
			if(up&&left==true){
				location.setLocation(location.getX() - SPEED, location.getY());
				location.setLocation(location.getX(), location.getY() - SPEED);
			}
			if(up&&right==true){
				location.setLocation(location.getX(), location.getY() - SPEED);
				location.setLocation(location.getX() + SPEED, location.getY());
			}
			if(down&&left==true){
				location.setLocation(location.getX(), location.getY() + SPEED);
				location.setLocation(location.getX() - SPEED, location.getY());
			}
			if(down&&right==true){
				location.setLocation(location.getX(), location.getY() + SPEED);
				location.setLocation(location.getX() + SPEED, location.getY());
			}
		}
		else{
			if(up==true){
				location.setLocation(location.getX(), location.getY() - SPEED); 
			}
			if(down==true){
				location.setLocation(location.getX(), location.getY() + SPEED); 
			}
			if(left==true){
				location.setLocation(location.getX() - SPEED, location.getY()); 
			}
			if(right==true){
				location.setLocation(location.getX() + SPEED, location.getY()); 
			}
		}
	}
	/**
	 * 플레이어의 탄 발사에 딜레이를 주는 메소드
	 * @return boolean 값
	 */
	public boolean canShot() {		//내가 총알을 지금 쏠수 있니?(딜레이가 없으면 총알이 자기끼리 충돌판정받아 없어진다. -_-
		return (lastShotTick + SHOT_DELAY < System.currentTimeMillis());
	}
	/**
	 * 폭탄을 쏠수 있는가에 대한 메소드
	 * @return 폭탄을 쏠수 있으면 true, 폭탄을 쏠수 없으면 false 반환
	 */
	public boolean canBombShot(){
		return playerInfo.getBomb() > 0 && (bombLastShotTick + SHOT_DELAY < System.currentTimeMillis());
	}
	
	/**
	 * 총알을 만들어 발사하는 메소드
	 * @param x 현재 x좌표에 더하는 값
	 * @return Bullet을 리턴
	 */
	public Bullet shot(int x){		//총알을 만들어 반환한다.
		lastShotTick = System.currentTimeMillis();
		bullet = new Bullet(getId(), Bullet.PLAYER, 1);	//아이디와 방향을 가진 총알 객체를 만든다.
		bullet.setLocation((float) location.getX() + x, (float) location.getY() - height / 2 - BULLET_Y_POS_MARGIN);
		return bullet;
	}
	/**
	 * 폭탄을 쏘게하는 메소드
	 * @return bomb 반환
	 */
	public Bomb bombShot(){
		playerInfo.setBomb(playerInfo.getBomb()-1);
		bombLastShotTick = System.currentTimeMillis();
		bomb = new Bomb(getId(), Bomb.PLAYER);
		bomb.setLocation((float) location.getX(), (float) location.getY() - height / 2 - BULLET_Y_POS_MARGIN);
		return bomb;
	}
	/**
	 * 스크린 바깥으로 나갔는지 판단하는 메소드
	 */
	public boolean isOutOfScreen(int screenWidth, int screenHeight) {
		if(location.getX()>600)
			location.setLocation(600, location.getY());
		if(location.getX()<25)
			location.setLocation(25, location.getY());
		if(location.getY()>600)
			location.setLocation(location.getX(), 600);
		if(location.getY()<30)
			location.setLocation(location.getX(), 30);
		return false;
	}
	/**
	 * 플레이어를 만드는 메소드
	 * @param screenWidth 현재 스크린의 폭
	 * @param screenHeight 현재 스크린의 높이
	 * @param p 플레이어정보
	 * @return Player 객체 반환
	 */
	public static Player create(int screenWidth, int screenHeight, PlayerInfo p) {
		Player player = new Player(p);
		player.setLocation(screenWidth / 2, screenHeight * 2 / 3);
		return player;
	}
	/**
	 * 플레이어의 점수를 설정하는 메소드
	 */
	public void setScore(){
		score++;
	}
	/**
	 * 플레이어의 점수를 반환하는 메소드
	 * @return score 값
	 */
	static public int getScore(){
		return score;
	}
	/**
	 * 플레이어의 스코어를 리셋하는 메소드
	 */
	static public void resetScore(){
		score = 0;
	}
}
