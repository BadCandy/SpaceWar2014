package Util;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

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
public class Bullet extends GameObject {
	public static final int PLAYER = -1;	//플레이어의 총알은 -y방향으로 진행
	public static final int ENEMY = 1;		//적기의 총알은 +y방향으로 진행
	private static final int WIDTH = 2;
	private static final int HEIGHT = 5;
	private static final int RANDOM_LIFETIME = 50;
	private static final int BASE_LIFETIME = 50;
	private static final int BULLET_SPEED = 10;	//총알의 속도 일정하게
	private int direction;
	private long ownerId;		//소유자 아이디
	private boolean enemyCode = false;
	private static int damage = 1;
	private BufferedImage image;
	/**
	 * 적기의 Bullet 생성자
	 * @param ownerId 총알을 소유하고 있는 객체의 id
	 * @param direction 총알이 날아갈 방향
	 */
	public Bullet(long ownerId, int direction) {
		super(ResourceManager.getInstance().getImage(ResourceType.ENEMY_BULLET).getWidth(), ResourceManager.getInstance().getImage(ResourceType.ENEMY_BULLET).getHeight());
		//ResourceManager.getInstance().getImage(ResourceType.PLAYER_SHIP)
		image = ResourceManager.getInstance().getImage(ResourceType.ENEMY_BULLET);
		this.ownerId = ownerId;
		this.direction = direction;
	}
	/**
	 * 플레이어의 Bullet 생성자
	 * @param ownerId 총알을 소유하고 있는 객체의 ID
	 * @param direction 총알이 날아갈 방향
	 * @param player 플레이어의 넘버
	 */
	public Bullet(long ownerId, int direction, int player) {
		
		super(ResourceManager.getInstance().getImage(ResourceType.PLAYER_BULLET).getWidth(), ResourceManager.getInstance().getImage(ResourceType.PLAYER_BULLET).getHeight());
		image = ResourceManager.getInstance().getImage(ResourceType.PLAYER_BULLET);
		this.ownerId = ownerId;
		this.direction = direction;
		
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
	@Override
	/**
	 * 총알의 위치를 업데이트 하는 메소드 
	 */
	public void update() {		//지정된 속도로 총알이 이동할 수 있게 한다.
			location.setLocation(location.getX(), location.getY() + direction * BULLET_SPEED);
	}

	@Override
	/**
	 * 총알을 그리는 메소드
	 */
	public void paint(Graphics2D g2d) {	//현재 비행기의 위치를 그림의 중앙으로 보정하여 그린다.
		int x = (int) Math.round(location.getX() - width / 2);
		int y = (int) Math.round(location.getY() - height / 2);
		g2d.drawImage(image, x, y, null);		//그려그려
	}
	@Override
	/**
	 * 총알이 스크린 바깥으로 나갔는지 판단하는 메소드
	 */
	public boolean isOutOfScreen(int screenWidth, int screenHeight) {	//총알이 바깥으로 나가면  없앤다.
		float minY = (float) location.getY() - height;
		float maxY = (float) location.getY() + height;
		switch (direction) {
		case PLAYER:
			return maxY < 0;
		case ENEMY:
			return minY > screenHeight;
		}
		return true;
	}
	/**
	 * 현재 총알 소유자의 id를 반환하는 메소드
	 * @return ownerId 값
	 */
	public long getOwnerId(){
		return ownerId;
	}
	/**
	 * 총알의 데미지를 반환하는 메소드
	 * @return 총알의 데미지
	 */
	public static int getDamage(){
		return damage;
	}
	/**
	 * 방향을 반환하는 메소드
	 * @return direction
	 */
	public int getDirection(){
		return direction;
	}
	/**
	 * 충돌영역을 반환하는 메소드
	 */
	protected Rectangle2D getBound() {    //충돌 영역을 반환하는 메소드
		return new Rectangle2D.Float((float) location.getX() - width/2, (float) location.getY() - width/2 , width, height);
	}
}