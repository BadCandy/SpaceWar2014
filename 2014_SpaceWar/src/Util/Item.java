package Util;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import SpaceWar.ResourceManager;
import SpaceWar.ResourceType;
import Core.Enemy;
import Core.GameObject;
import Core.Player;


/**
 * GameObject 클래스를 상속받은 Item 클래스
 * @author 윤선태
 * @since 2014.11.13
 * @version 1.0
 */
public class Item extends GameObject{
	private int itemNum;
	private BufferedImage image;
	private Point2D moveDelta = new Point2D.Float(); //매 tick마다 update에서  더해줄 이동 값
	private Enemy enemy;
	private static final int RANDOM_LIFETIME = 50;
	private static final int BASE_LIFETIME = 50;
	/**
	 * Item의 생성자
	 * @param location Item의 초기 위치
	 * @param moveDelta Item이 시간당 얼마나 이동할 것인지
	 * @param e 아이템을 가질 적기
	 */
	public Item(Point2D location, Point2D moveDelta, Enemy e){
		super(ResourceManager.getInstance().getImage(ResourceType.ITEM).getWidth(), ResourceManager.getInstance().getImage(ResourceType.ITEM).getHeight());
		image = ResourceManager.getInstance().getImage(ResourceType.ITEM);
		enemy = e;
		double a = Math.random();
		if(a < 0.35)
			itemNum = 1;
		if(a >= 0.35 && a< 0.7)
			itemNum = 2;
		if(a >= 0.7 && a < 1)
			itemNum = 3;
		this.location.setLocation(location);
		this.moveDelta.setLocation(moveDelta);
	}

	/**
	 * 아이템을 그리는 메소드
	 */
    public void paint(Graphics2D g2d) {	//현재 아이템의 위치를 그림의 중앙으로 보정하여 그린다.
        int x = (int) Math.round(location.getX() - width / 2);
        int y = (int) Math.round(location.getY() - height / 2);
        g2d.drawImage(image, x, y, null);		//그려그려
    }
    /**
     * 아이템 이름을 반환하는 메소드
     * @return itemName
     */
    public int getItemNum(){
    	return itemNum;
    }
    /**
     * 아이템 객체를 반환하는 메소드
     * @param screenWidth 현재 스크린의 폭
     * @param screenHeight 현재 스크린의 높이
     * @param enemy 아이템을 가질 적기
     * @return Item 객체
     */
	public static Item itemSpawn(int screenWidth, int screenHeight, Enemy enemy) {
		Random rand = new Random();
		int itemHeight = ResourceManager.getInstance().getImage(ResourceType.ITEM).getHeight();
		float lifetime = rand.nextFloat() * RANDOM_LIFETIME + BASE_LIFETIME;	//생명시간인데 이를 이용해 나누어서 시간당 얼마나 이동할것인지 계산
		Point2D location = new Point2D.Float((int)enemy.getX(), (int)enemy.getY());		//위치 지정하고
		double a = Math.random();
		Point2D moveDelta;
		if(a <= 0.5)
			moveDelta = new Point2D.Float(-1 * (int)enemy.getX()/70 , 5);	//이동하라
		else
			moveDelta = new Point2D.Float((int)enemy.getX()/70 , 5);
		return new Item(location, moveDelta, enemy);
	}

	/**
	 * 아이템의 위치를 업데이트 하는 메소드
	 */
	public void update() {
		double x = location.getX() + moveDelta.getX();
		double y = location.getY() + moveDelta.getY();
		location.setLocation(x, y);
	}
	/**
	 * 아이템이 바깥으로 나갔는지를 판단하는 메소드
	 */
	public boolean isOutOfScreen(int screenWidth, int screenHeight) {		//밖으로 나갔는지
		float minX = (float) location.getX();// - width / 2
		float minY = (float) location.getY();//  - height / 2
		return minX > screenWidth || minY > screenHeight;
	}
	/**
	 * 아이템의 충돌 영역을 반환하는 메소드
	 */
    protected Rectangle2D getBound() {
        return new Rectangle2D.Float((float) location.getX() - width / 2,
                (float) location.getY() - height / 2, width, height);
    }
	/**
	 * 충돌 검사를 하는 메소드
	 */
    public boolean check(GameObject other) {
        if (!(other instanceof Player))
            return false;
        return super.check(other);
    }

}

