package Core;
/**
 * �÷��̾��� ������ ���� Ŭ����
 * @author ������
 * @since 2014.11.13
 * @version 1.0
 */
public class PlayerInfo {
	private int life;
	private int bomb;
	private int missile;
	/**
	 * PlayerInfo ������
	 */
	public PlayerInfo(){
		life = 2;
		bomb = 2;
		missile = 1;
	}
	
	/**
	 * �÷��̾��� ����� ���Ѵ�.
	 * @param l ����� ����
	 */
	public void setLife(int l){
		life = l;
		if(life > 4)
			life = 4;
	}
	/**
	 * �÷��̾��� ��ź�� ���Ѵ�.
	 * @param b ��ź�� ����
	 */
	public void setBomb(int b){
		bomb = b;
		if(bomb > 4)
			bomb = 4;
		else if(bomb <= 0)
			bomb = 0;
	}
	/**
	 * �÷��̾��� �̻��� ������ ���Ѵ�.
	 * @param m �̻��� ����
	 */
	public void setMissile(int m){
		missile = m;
		if(missile > 3)
			missile = 3;
	}
	/**
	 * �÷��̾��� ����� ��ȯ�Ѵ�.
	 * @return life
	 */
	public int getLife(){
		return life;
	}
	/**
	 * �÷��̾��� ��ź ������ ��ȯ�Ѵ�.
	 * @return bomb
	 */
	public int getBomb(){
		return bomb;
	}
	/**
	 * �÷��̾��� �̻��� ������ ��ȯ�Ѵ�.
	 * @return missile
	 */
	public int getMissile(){
		return missile;
	}
}
