package Core;

import SpaceWar.ResourceManager;
import SpaceWar.ResourceType;
import Util.Bomb;
import Util.Bullet;

/**
 * Ship�� ��ӹ��� player�� ���� Ŭ����
 * @author ������
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
	 * Player�� ������. �̹����� ������ ����Ŭ���� �����ڿ� �־��ش�.
	 * @param p �÷��̾�����
	 */
	public Player(PlayerInfo p) {
		super(ResourceManager.getInstance().getImage(ResourceType.PLAYER_SHIP)); //�̹����� ������ ����Ŭ���� �����ڿ� �־��ش�.
		score = 0;
		playerInfo = p;
	}
	@Override
	/**
	 * �÷��̾��� ��ġ�� ������Ʈ �ϴ� �޼ҵ�
	 */
	public void update() {
	}
	/**
	 * �÷��̾ �����̰� �ϴ� �޼ҵ�
	 * @param dpressed �ΰ��� Ű�� ������������ ���� boolean��
	 * @param up ���� �̵��ϴ� Ű�� ������������ ���� boolean��
	 * @param down �Ʒ��� �̵��ϴ� Ű�� ������������ ���� boolean��
	 * @param left �������� �̵��ϴ� Ű�� ������������ ���� boolean��
	 * @param right ���������� �̵��ϴ� Ű�� ������������ ���� boolean��
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
	 * �÷��̾��� ź �߻翡 �����̸� �ִ� �޼ҵ�
	 * @return boolean ��
	 */
	public boolean canShot() {		//���� �Ѿ��� ���� ��� �ִ�?(�����̰� ������ �Ѿ��� �ڱⳢ�� �浹�����޾� ��������. -_-
		return (lastShotTick + SHOT_DELAY < System.currentTimeMillis());
	}
	/**
	 * ��ź�� ��� �ִ°��� ���� �޼ҵ�
	 * @return ��ź�� ��� ������ true, ��ź�� ��� ������ false ��ȯ
	 */
	public boolean canBombShot(){
		return playerInfo.getBomb() > 0 && (bombLastShotTick + SHOT_DELAY < System.currentTimeMillis());
	}
	
	/**
	 * �Ѿ��� ����� �߻��ϴ� �޼ҵ�
	 * @param x ���� x��ǥ�� ���ϴ� ��
	 * @return Bullet�� ����
	 */
	public Bullet shot(int x){		//�Ѿ��� ����� ��ȯ�Ѵ�.
		lastShotTick = System.currentTimeMillis();
		bullet = new Bullet(getId(), Bullet.PLAYER, 1);	//���̵�� ������ ���� �Ѿ� ��ü�� �����.
		bullet.setLocation((float) location.getX() + x, (float) location.getY() - height / 2 - BULLET_Y_POS_MARGIN);
		return bullet;
	}
	/**
	 * ��ź�� ����ϴ� �޼ҵ�
	 * @return bomb ��ȯ
	 */
	public Bomb bombShot(){
		playerInfo.setBomb(playerInfo.getBomb()-1);
		bombLastShotTick = System.currentTimeMillis();
		bomb = new Bomb(getId(), Bomb.PLAYER);
		bomb.setLocation((float) location.getX(), (float) location.getY() - height / 2 - BULLET_Y_POS_MARGIN);
		return bomb;
	}
	/**
	 * ��ũ�� �ٱ����� �������� �Ǵ��ϴ� �޼ҵ�
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
	 * �÷��̾ ����� �޼ҵ�
	 * @param screenWidth ���� ��ũ���� ��
	 * @param screenHeight ���� ��ũ���� ����
	 * @param p �÷��̾�����
	 * @return Player ��ü ��ȯ
	 */
	public static Player create(int screenWidth, int screenHeight, PlayerInfo p) {
		Player player = new Player(p);
		player.setLocation(screenWidth / 2, screenHeight * 2 / 3);
		return player;
	}
	/**
	 * �÷��̾��� ������ �����ϴ� �޼ҵ�
	 */
	public void setScore(){
		score++;
	}
	/**
	 * �÷��̾��� ������ ��ȯ�ϴ� �޼ҵ�
	 * @return score ��
	 */
	static public int getScore(){
		return score;
	}
	/**
	 * �÷��̾��� ���ھ �����ϴ� �޼ҵ�
	 */
	static public void resetScore(){
		score = 0;
	}
}
