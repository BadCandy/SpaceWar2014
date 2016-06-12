package Util;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

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
public class Bullet extends GameObject {
	public static final int PLAYER = -1;	//�÷��̾��� �Ѿ��� -y�������� ����
	public static final int ENEMY = 1;		//������ �Ѿ��� +y�������� ����
	private static final int WIDTH = 2;
	private static final int HEIGHT = 5;
	private static final int RANDOM_LIFETIME = 50;
	private static final int BASE_LIFETIME = 50;
	private static final int BULLET_SPEED = 10;	//�Ѿ��� �ӵ� �����ϰ�
	private int direction;
	private long ownerId;		//������ ���̵�
	private boolean enemyCode = false;
	private static int damage = 1;
	private BufferedImage image;
	/**
	 * ������ Bullet ������
	 * @param ownerId �Ѿ��� �����ϰ� �ִ� ��ü�� id
	 * @param direction �Ѿ��� ���ư� ����
	 */
	public Bullet(long ownerId, int direction) {
		super(ResourceManager.getInstance().getImage(ResourceType.ENEMY_BULLET).getWidth(), ResourceManager.getInstance().getImage(ResourceType.ENEMY_BULLET).getHeight());
		//ResourceManager.getInstance().getImage(ResourceType.PLAYER_SHIP)
		image = ResourceManager.getInstance().getImage(ResourceType.ENEMY_BULLET);
		this.ownerId = ownerId;
		this.direction = direction;
	}
	/**
	 * �÷��̾��� Bullet ������
	 * @param ownerId �Ѿ��� �����ϰ� �ִ� ��ü�� ID
	 * @param direction �Ѿ��� ���ư� ����
	 * @param player �÷��̾��� �ѹ�
	 */
	public Bullet(long ownerId, int direction, int player) {
		
		super(ResourceManager.getInstance().getImage(ResourceType.PLAYER_BULLET).getWidth(), ResourceManager.getInstance().getImage(ResourceType.PLAYER_BULLET).getHeight());
		image = ResourceManager.getInstance().getImage(ResourceType.PLAYER_BULLET);
		this.ownerId = ownerId;
		this.direction = direction;
		
	}

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
	 * �Ѿ��� ��ġ�� ������Ʈ �ϴ� �޼ҵ� 
	 */
	public void update() {		//������ �ӵ��� �Ѿ��� �̵��� �� �ְ� �Ѵ�.
			location.setLocation(location.getX(), location.getY() + direction * BULLET_SPEED);
	}

	@Override
	/**
	 * �Ѿ��� �׸��� �޼ҵ�
	 */
	public void paint(Graphics2D g2d) {	//���� ������� ��ġ�� �׸��� �߾����� �����Ͽ� �׸���.
		int x = (int) Math.round(location.getX() - width / 2);
		int y = (int) Math.round(location.getY() - height / 2);
		g2d.drawImage(image, x, y, null);		//�׷��׷�
	}
	@Override
	/**
	 * �Ѿ��� ��ũ�� �ٱ����� �������� �Ǵ��ϴ� �޼ҵ�
	 */
	public boolean isOutOfScreen(int screenWidth, int screenHeight) {	//�Ѿ��� �ٱ����� ������  ���ش�.
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
	 * ���� �Ѿ� �������� id�� ��ȯ�ϴ� �޼ҵ�
	 * @return ownerId ��
	 */
	public long getOwnerId(){
		return ownerId;
	}
	/**
	 * �Ѿ��� �������� ��ȯ�ϴ� �޼ҵ�
	 * @return �Ѿ��� ������
	 */
	public static int getDamage(){
		return damage;
	}
	/**
	 * ������ ��ȯ�ϴ� �޼ҵ�
	 * @return direction
	 */
	public int getDirection(){
		return direction;
	}
	/**
	 * �浹������ ��ȯ�ϴ� �޼ҵ�
	 */
	protected Rectangle2D getBound() {    //�浹 ������ ��ȯ�ϴ� �޼ҵ�
		return new Rectangle2D.Float((float) location.getX() - width/2, (float) location.getY() - width/2 , width, height);
	}
}