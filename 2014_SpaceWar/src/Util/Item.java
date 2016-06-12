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
 * GameObject Ŭ������ ��ӹ��� Item Ŭ����
 * @author ������
 * @since 2014.11.13
 * @version 1.0
 */
public class Item extends GameObject{
	private int itemNum;
	private BufferedImage image;
	private Point2D moveDelta = new Point2D.Float(); //�� tick���� update����  ������ �̵� ��
	private Enemy enemy;
	private static final int RANDOM_LIFETIME = 50;
	private static final int BASE_LIFETIME = 50;
	/**
	 * Item�� ������
	 * @param location Item�� �ʱ� ��ġ
	 * @param moveDelta Item�� �ð��� �󸶳� �̵��� ������
	 * @param e �������� ���� ����
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
	 * �������� �׸��� �޼ҵ�
	 */
    public void paint(Graphics2D g2d) {	//���� �������� ��ġ�� �׸��� �߾����� �����Ͽ� �׸���.
        int x = (int) Math.round(location.getX() - width / 2);
        int y = (int) Math.round(location.getY() - height / 2);
        g2d.drawImage(image, x, y, null);		//�׷��׷�
    }
    /**
     * ������ �̸��� ��ȯ�ϴ� �޼ҵ�
     * @return itemName
     */
    public int getItemNum(){
    	return itemNum;
    }
    /**
     * ������ ��ü�� ��ȯ�ϴ� �޼ҵ�
     * @param screenWidth ���� ��ũ���� ��
     * @param screenHeight ���� ��ũ���� ����
     * @param enemy �������� ���� ����
     * @return Item ��ü
     */
	public static Item itemSpawn(int screenWidth, int screenHeight, Enemy enemy) {
		Random rand = new Random();
		int itemHeight = ResourceManager.getInstance().getImage(ResourceType.ITEM).getHeight();
		float lifetime = rand.nextFloat() * RANDOM_LIFETIME + BASE_LIFETIME;	//����ð��ε� �̸� �̿��� ����� �ð��� �󸶳� �̵��Ұ����� ���
		Point2D location = new Point2D.Float((int)enemy.getX(), (int)enemy.getY());		//��ġ �����ϰ�
		double a = Math.random();
		Point2D moveDelta;
		if(a <= 0.5)
			moveDelta = new Point2D.Float(-1 * (int)enemy.getX()/70 , 5);	//�̵��϶�
		else
			moveDelta = new Point2D.Float((int)enemy.getX()/70 , 5);
		return new Item(location, moveDelta, enemy);
	}

	/**
	 * �������� ��ġ�� ������Ʈ �ϴ� �޼ҵ�
	 */
	public void update() {
		double x = location.getX() + moveDelta.getX();
		double y = location.getY() + moveDelta.getY();
		location.setLocation(x, y);
	}
	/**
	 * �������� �ٱ����� ���������� �Ǵ��ϴ� �޼ҵ�
	 */
	public boolean isOutOfScreen(int screenWidth, int screenHeight) {		//������ ��������
		float minX = (float) location.getX();// - width / 2
		float minY = (float) location.getY();//  - height / 2
		return minX > screenWidth || minY > screenHeight;
	}
	/**
	 * �������� �浹 ������ ��ȯ�ϴ� �޼ҵ�
	 */
    protected Rectangle2D getBound() {
        return new Rectangle2D.Float((float) location.getX() - width / 2,
                (float) location.getY() - height / 2, width, height);
    }
	/**
	 * �浹 �˻縦 �ϴ� �޼ҵ�
	 */
    public boolean check(GameObject other) {
        if (!(other instanceof Player))
            return false;
        return super.check(other);
    }

}

