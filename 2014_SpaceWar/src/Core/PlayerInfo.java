package Core;
/**
 * 플레이어의 정보를 가진 클래스
 * @author 김현준
 * @since 2014.11.13
 * @version 1.0
 */
public class PlayerInfo {
	private int life;
	private int bomb;
	private int missile;
	/**
	 * PlayerInfo 생성자
	 */
	public PlayerInfo(){
		life = 2;
		bomb = 2;
		missile = 1;
	}
	
	/**
	 * 플레이어의 목숨을 정한다.
	 * @param l 목숨의 숫자
	 */
	public void setLife(int l){
		life = l;
		if(life > 4)
			life = 4;
	}
	/**
	 * 플레이어의 폭탄을 정한다.
	 * @param b 폭탄의 숫자
	 */
	public void setBomb(int b){
		bomb = b;
		if(bomb > 4)
			bomb = 4;
		else if(bomb <= 0)
			bomb = 0;
	}
	/**
	 * 플레이어의 미사일 개수를 정한다.
	 * @param m 미사일 개수
	 */
	public void setMissile(int m){
		missile = m;
		if(missile > 3)
			missile = 3;
	}
	/**
	 * 플레이어의 목숨을 반환한다.
	 * @return life
	 */
	public int getLife(){
		return life;
	}
	/**
	 * 플레이어의 폭탄 개수를 반환한다.
	 * @return bomb
	 */
	public int getBomb(){
		return bomb;
	}
	/**
	 * 플레이어의 미사일 개수를 반환한다.
	 * @return missile
	 */
	public int getMissile(){
		return missile;
	}
}
