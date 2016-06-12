package SpaceWar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.sound.sampled.Clip;
import javax.swing.JPanel;

import Core.Enemy;
import Core.GameObject;
import Core.Player;
import Core.PlayerInfo;
import Util.BlackHoll;
import Util.Bomb;
import Util.Bullet;
import Util.Item;
import Util.Stage;

/**
 * 게임 플레이에 관한 모든 요소들을 처리하고 그리는 패널 쓰레드.
 * @author 정윤철
 * @since 2014.11.13
 * @version 1.0
 * @see Enemy
 * @see GameObject
 * @see Player
 * @see Bullet
 * @see DigtalClock
 * @see ResourceManager
 */
public class GameScreen extends JPanel implements KeyEventDispatcher, Runnable{
	final int WIDTH = 640;
	final int HEIGHT = 700;
	private SpaceWar2014 game;
	private Frame frame;
	private int bossFlag;
	private int itemEnemyFlag;
	private int flag;
	private int tFlag;
	private DigtalClock clock = new DigtalClock();
	private boolean upPressed = false, downPressed = false,leftPressed = false , rightPressed = false, dpressed = false;
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();	//게임 오브젝트를 담을 리스트
	private PlayerInfo info= new PlayerInfo();
	private Player player = Player.create(WIDTH, HEIGHT, info);	//플레이어의 경우는 move메소드 등을 직접 조작해주어야하니 따로 만들었다.
	private BufferedImage image;
	private Stage stage;
	private Thread thread;
	private boolean isLoading = true;
	static private Clip effectClip;
	static private double volunm = 0.5;
	private Bar chargeBar;
	private MinusThread mt;
	private int blackIng;



	/**
	 * GameScreen 생성자, 플레이어 객체를 GameObject 타입의 ArrayList에 넣고 타이머를 발동시킨다.
	 * @param game 게임 프레임 파라미터
	 * @param s 스테이지 파라미터
	 */
	public GameScreen(SpaceWar2014 game, Stage s) {
		stage = s;
		stage.setStage1();
		clock.exeClock();
		this.game = game;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		setFocusable(true);
		requestFocus(true);
		objects.add(player);
		makeChargeBar();
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * bar를 생성하는 메소드
	 */
	public void makeChargeBar(){
		chargeBar = new Bar(100);
		//	chargeBar.setOpaque(true);
		chargeBar.setSize(300, 20);
		add(chargeBar);

		MinusThread mt = new MinusThread(chargeBar);
		mt.start();
	}


	private static int up=38;
	private static int down=40;
	private static int left=37;
	private static int right=39;
	private static int shoot=32;
	private static int bomb=90;
	/**
	 * up키의 값을 설정하는 메소드
	 * @param upK 키의 값
	 */
	public static void setUpKey(int upK){
		up = upK;
	}
	/**
	 * down키의 값을 설정하는 메소드
	 * @param downK 키의 값
	 */
	public static void setDownKey(int downK){
		down = downK;
	}
	/**
	 * left키의 값을 설정하는 메소드
	 * @param leftK 키의값
	 */
	public static void setLeftKey(int leftK){
		left = leftK;
	}
	/**
	 * right키의 값을 설정하는 메소드
	 * @param rightK 키의값
	 */
	public static void setRightKey(int rightK){
		right = rightK;
	}
	/**
	 * 미사일키의 값을 설정하는 메소드
	 * @param shootK 키의 값
	 */
	public static void setShootKey(int shootK){
		shoot = shootK;
	}
	/**
	 * 폭탄키의 값을 설정하는 메소드
	 * @param bombK 키의 값
	 */
	public static void setBombKey(int bombK){
		bomb = bombK;
	}
	/**
	 * 게임프레임을 실행하는 쓰레드의 run()메소드. 프레임(게임반응속도)를 맞추기위해 필요하다.
	 */
	public void run() {
		boolean a = true;
		while(a){
			try{
				long pretime = System.currentTimeMillis();
				long delay = 20;
				if(System.currentTimeMillis()-pretime < delay)
					Thread.sleep(delay-System.currentTimeMillis()+pretime);
			}catch(InterruptedException e){

			}
			player.move(dpressed,upPressed,downPressed,leftPressed,rightPressed);   //키를 눌렀을 때 player객체의 move 메소드 호출

			updateAll();
			checkCollision();
			setBlackHollMoveEnemy();
			generateEnemyAndBullet();
			if(clock.getSecond() == 10 || clock.getSecond() == 25 || clock.getSecond() == 40 || clock.getSecond() == 55)
				generateItemEnemyAndBullet();
			if(clock.getSecond() == 15 || clock.getSecond() == 30 || clock.getSecond() == 45 || clock.getSecond() == 58)
				itemEnemyFlag = 0;
			if(clock.getSecond() == 58 && clock.getMinute() == 0){
				stage.offStageSound();

			}
			if(clock.getMinute() == 1)
				generateBossAndBullet();
			if(clock.getMinute() == 0)
				bossFlag = 0;
			repaint();

		}
	}
	private int firstSecond = 0;
	private int lastSecond = 0;
	private int blackHollFlag = 0;

	/**
	 * 키보드 이벤트를 처리하는 메소드
	 */
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			chargeBar.fill();
			int a = e.getKeyCode();
			if(a==up){
				upPressed = true;
			}
			if(a==down){
				downPressed = true;
			}
			if(a==left){
				leftPressed = true;
			}
			if(a==right){
				rightPressed = true;
			}
			if(a==shoot){
				if(chargeBar.getBarSize() >= 95 && blackHollFlag == 0){
					firstSecond = clock.getSecond();
					blackHollFlag = 1;

				}
				else if (player.canShot() && chargeBar.getBarSize() < 95){
					switch(info.getMissile()){
					case 1:
						objects.add(player.shot(0));
						//objects.add(new BlackHoll(player.getId()));
						break;
					case 2:
						objects.add(player.shot(8));
						objects.add(player.shot(-8));
						break;
					case 3:
						objects.add(player.shot(0));
						objects.add(player.shot(15));
						objects.add(player.shot(-15));
						break;
					}
				}
			}
			if(a==bomb){
				if(player.canBombShot())
					objects.add(player.bombShot());

			}
			if(upPressed && leftPressed == true){
				dpressed = true;
			}
			if(upPressed && rightPressed == true){
				dpressed = true;
			}
			if(downPressed && leftPressed == true){
				dpressed = true;
			}
			if(downPressed && rightPressed == true){
				dpressed = true;
			}  
		}

		if (e.getID() == KeyEvent.KEY_RELEASED) {

			int a = e.getKeyCode();

			if(a==up){
				upPressed = false;
				dpressed = false;
			}
			if(a==down){
				downPressed = false;
				dpressed = false;
			}
			if(a==left){
				leftPressed = false;
				dpressed = false;
			}
			if(a==right){
				rightPressed = false;
				dpressed = false;
			}
			if(a==shoot){


				if (player.canShot() && chargeBar.getBarSize() >= 95 && blackHollFlag == 1){
					lastSecond = clock.getSecond();
					blackHollFlag = 0;
					if(lastSecond-firstSecond >= 2){
						generateBlackHoll();
					}
					else{
						switch(info.getMissile()){
						case 1:
							objects.add(player.shot(0));
							break;
						case 2:
							objects.add(player.shot(8));
							objects.add(player.shot(-8));
							break;
						case 3:
							objects.add(player.shot(0));
							objects.add(player.shot(15));
							objects.add(player.shot(-15));
						}
					}
				}
			}        
		}

		return false;
	}
	/**
	 * 블랙홀이 방생했을때 적기의 움직임을 세팅한다.
	 */
	public void setBlackHollMoveEnemy(){
		if(blackIng == 1){
			for (int i = 0; i < objects.size(); i++) {
				GameObject go = objects.get(i);
				if(go instanceof Enemy){
					if(((Enemy) go).getBossNumber() != 1){
						((Enemy) go).setBlackHollMoveDelta();
					}
				}

			}
		}

	}

	/**
	 * 모든 게임 오브젝트의 위치에 관한 정보를 업데이트 하는 메소드
	 */
	public synchronized void updateAll() {			//모든 게임 오브젝트를 업데이트 한다.
		for (int i = 0; i < objects.size(); i++) {
			if(objects.get(i) == null){
				continue;
			}
			else
				objects.get(i).update();
		}
	}
	/**
	 * 각각의 오브젝트에 대하여 충돌판정을 하는 메소드
	 */
	public synchronized void checkCollision() {		//층돌 판정                  //일일이 각각 클래스에서 처리하면 코드가 복잡해지므로 부가적인 충돌판정은 여기서 처리함. 나중에 코드 수정하기가 쉬워짐
		Set<GameObject> removal = new HashSet<GameObject>();	//hashset을 이용한다. 제거할 대상 찾음

		for (int i = 0; i < objects.size() - 1; ++i) {
			GameObject lhs = objects.get(i);
			for (int j = i + 1; j < objects.size(); ++j) {
				GameObject rhs = objects.get(j);

				if(lhs instanceof Enemy){
					if(((Enemy) lhs).getBooomNum()==1){
						if(rhs instanceof Bullet){
							continue;
						}
						objects.remove(lhs);
						continue;
					}
				}
				if(rhs instanceof Enemy){
					if(((Enemy) rhs).getBooomNum()==1){
						if(lhs instanceof Bullet){
							continue;
						}
						objects.remove(rhs);
						continue;
					}
				}
				if(lhs != null && rhs != null){
					if (!(lhs.check(rhs)) || (rhs.getEnemyCode() && lhs.getEnemyCode())) continue; //버블 소팅과 비슷한 원리로 충돌판정 체크.
				}
				if ((lhs instanceof Bomb && rhs instanceof Player) || (lhs instanceof Player && rhs instanceof Bomb))//폭탄과 플레이어 충돌조건
					continue;
				if ((lhs instanceof Bomb && rhs instanceof Bomb))  //폭탄끼리 충돌하는것을 막기위함
					continue;
				if ((lhs instanceof Bullet && rhs instanceof Bullet))  //폭탄끼리 충돌하는것을 막기위함
					if(((Bullet)lhs).getEnemyCode()==false && ((Bullet)rhs).getEnemyCode()==false)
						continue;

				if(((lhs instanceof Enemy) || (rhs instanceof Enemy)) && ((lhs instanceof Bullet) || (rhs instanceof Bullet))){	//적기와 총알관련 충돌조
					player.setScore();
					if(lhs instanceof Enemy){
						if(((Enemy) lhs).getEnemyNumber()==1 && ((Enemy) lhs).getEnemyLife()<1){
							Enemy boom = ((Enemy) lhs).generateBooom(volunm);
							objects.add(boom);
							effectClip = boom.getClip();
							objects.remove(lhs);
							objects.remove(rhs);
							continue;
						}
						else if(((Enemy) lhs).getEnemyNumber()==1 && ((Enemy) lhs).getEnemyLife()>=1){ //lhs가 아이템기이고 lhs의 목숨이 0이 아닐때
							((Enemy) lhs).decreaseEnemyLife(Bullet.getDamage());	//목숨감소
							if(rhs instanceof Bullet){			//rhs가 총알이면
								objects.remove(rhs);			//rhs를 없앤다
							}
							continue;
						}
						if(((Enemy) lhs).getItemEnemyNumber()==1 && ((Enemy) lhs).getEnemyLife()<1){	//lhs가 아이템기이고 lhs의 목숨이 0일때
							Enemy boom = ((Enemy) lhs).generateBooom(volunm);
							effectClip = boom.getClip();
							generateItem((Enemy)lhs);
							objects.remove(lhs);
							objects.remove(rhs);
							continue;
						}
						else if(((Enemy) lhs).getItemEnemyNumber()==1 && ((Enemy) lhs).getEnemyLife()>=1){ //lhs가 아이템기이고 lhs의 목숨이 0이 아닐때
							((Enemy) lhs).decreaseEnemyLife(Bullet.getDamage());	//목숨감소
							if(rhs instanceof Bullet){			//rhs가 총알이면
								objects.remove(rhs);			//rhs를 없앤다
							}
							continue;
						}
						if(((Enemy) lhs).getBossNumber() == 1 && ((Enemy) lhs).getEnemyLife() <1){
							if(stage.getStageLevel() == 1){
								((Enemy) lhs).setBossNumber(2);
								objects.remove(lhs);
								stage.offStageSound();
								stage.setStage2();
								clock.stopClock();
								clock.exeClock();
								continue;
							}
							else if(stage.getStageLevel() == 2){
								((Enemy) lhs).setBossNumber(2);
								objects.remove(lhs);
								stage.offStageSound();
								stage.setStage3();
								clock.stopClock();
								clock.exeClock();
								continue;
							}
							else if(stage.getStageLevel() == 3){
								clock.stopClock();
								stage.offStageSound();
								game.change("Rank");

								thread.stop();
								continue;
							}
						}
					}
					if(rhs instanceof Enemy){
						if(rhs instanceof Enemy){	//추가
							if(((Enemy) rhs).getEnemyNumber()==1 && ((Enemy) rhs).getEnemyLife()<1){
								Enemy boom = ((Enemy) rhs).generateBooom(volunm);
								effectClip = boom.getClip();
								objects.add(boom);
								objects.remove(lhs);
								objects.remove(rhs);
								continue;
							}
							else if(((Enemy) rhs).getEnemyNumber()==1 && ((Enemy) rhs).getEnemyLife()>=1){
								((Enemy) rhs).decreaseEnemyLife(Bullet.getDamage());
								if(lhs instanceof Bullet){
									objects.remove(lhs);
								}
								continue;
							}
							if(((Enemy) rhs).getItemEnemyNumber()==1 && ((Enemy) rhs).getEnemyLife()<1){
								Enemy boom = ((Enemy) rhs).generateBooom(volunm);
								effectClip = boom.getClip();
								generateItem((Enemy)rhs);
								objects.remove(lhs);
								objects.remove(rhs);
								continue;
							}
							else if(((Enemy) rhs).getItemEnemyNumber()==1){
								((Enemy) rhs).decreaseEnemyLife(Bullet.getDamage());
								if(lhs instanceof Bullet){
									objects.remove(lhs);
								}
								continue;
							}
						}	//추가 끝
						if(((Enemy) rhs).getBossNumber() == 1 && ((Enemy) rhs).getEnemyLife() <1){
							if(stage.getStageLevel() == 1){
								((Enemy) lhs).setBossNumber(2);
								objects.remove(rhs);
								stage.offStageSound();
								stage.setStage2();
								clock.stopClock();
								clock.exeClock();
								continue;
							}
							else if(stage.getStageLevel() == 2){
								((Enemy) lhs).setBossNumber(2);
								objects.remove(rhs);
								stage.offStageSound();
								stage.setStage3();
								clock.stopClock();
								clock.exeClock();
								continue;
							}
							else if(stage.getStageLevel() == 3){
								clock.stopClock();
								stage.offStageSound();
								thread.stop();

								game.change("Rank");
								continue;
							}
						}
					}
					if(lhs instanceof Enemy){
						if(((Enemy) lhs).getBossNumber() == 1 && ((Enemy) lhs).getEnemyLife() >=1){
							((Enemy) lhs).decreaseEnemyLife(Bullet.getDamage());
							objects.remove(rhs);

							continue;
						}
					}
					if(rhs instanceof Enemy){
						if(((Enemy) rhs).getBossNumber() == 1 && ((Enemy) rhs).getEnemyLife() >=1){
							((Enemy) rhs).decreaseEnemyLife(Bullet.getDamage());
							objects.remove(lhs);
							continue;
						}
					}
				}

				if(lhs instanceof BlackHoll){
					if(rhs instanceof Player){
						continue;
					}
					if(rhs instanceof Enemy){
						if(((Enemy) rhs).getBossNumber() == 1){
							continue;
						}
						if(((BlackHoll) lhs).setLife(((BlackHoll) lhs).getLife()-1)){
							objects.remove(lhs);
							blackIng = 0;
						}
						player.setScore();
						objects.remove(rhs);
						continue;
					}
					if(rhs instanceof Bullet){
						if(((Bullet) rhs).getDirection() == -1)
							continue;
					}
					objects.remove(rhs);
					continue;
				}

				if(rhs instanceof BlackHoll){
					if(lhs instanceof Player){
						continue;
					}
					if(lhs instanceof Enemy){
						if(((Enemy) lhs).getBossNumber() == 1){
							continue;
						}
						if(((BlackHoll) rhs).setLife(((BlackHoll) rhs).getLife()-1)){
							objects.remove(rhs);
							blackIng = 0;
						}
						player.setScore();
						objects.remove(lhs);
						continue;
					}
					if(lhs instanceof Bullet){
						if(((Bullet) lhs).getDirection() == -1)
							continue;
					}
					objects.remove(lhs);
					continue;
				}







				if((lhs instanceof Bomb) || (rhs instanceof Bomb)){					//폭탄 껍데기의 충돌 판정
					if(rhs instanceof Bomb){
						Bomb boom = ((Bomb)rhs).generateBoom(volunm);
						effectClip = boom.getClip();
						objects.add(boom);
						objects.remove(rhs);

						if(((Bomb)rhs).getNum()!=1){
							continue;
						}
						else if(((Bomb)rhs).getNum()==1){
							if(lhs instanceof Bullet)
								objects.remove(lhs);
							if(lhs instanceof Enemy){
								if(((Enemy) lhs).getEnemyNumber()==1 && ((Enemy) lhs).getEnemyLife()<1){
									objects.remove(lhs);
									objects.remove(rhs);
									continue;
								}
								else if(((Enemy) lhs).getEnemyNumber()==1 && ((Enemy) lhs).getEnemyLife()>=1){ //lhs가 아이템기이고 lhs의 목숨이 0이 아닐때
									((Enemy) lhs).decreaseEnemyLife(Bomb.getDamage());	//목숨감소
									if(rhs instanceof Bomb){			//rhs가 총알이면
										objects.remove(rhs);			//rhs를 없앤다
									}
									continue;
								}
								if(((Enemy) lhs).getItemEnemyNumber()==1 && ((Enemy) lhs).getEnemyLife()<1){	//lhs가 아이템기이고 lhs의 목숨이 0일때
									generateItem((Enemy)lhs);
									objects.remove(lhs);
									objects.remove(rhs);
									continue;
								}
								else if(((Enemy) lhs).getItemEnemyNumber()==1 && ((Enemy) lhs).getEnemyLife()>=1){ //lhs가 아이템기이고 lhs의 목숨이 0이 아닐때
									((Enemy) lhs).decreaseEnemyLife(Bomb.getDamage());	//목숨감소
									if(rhs instanceof Bomb){			//rhs가 총알이면
										objects.remove(rhs);			//rhs를 없앤다
									}
									continue;
								}
								if(lhs instanceof Enemy){
									if(((Enemy) lhs).getBossNumber() == 1 && ((Enemy) lhs).getEnemyLife() >=1){
										((Enemy) lhs).decreaseEnemyLife(Bomb.getDamage());
										objects.remove(rhs);
										continue;
									}
								}
								if(((Enemy) lhs).getBossNumber() == 1 && ((Enemy) lhs).getEnemyLife() <1){
									if(stage.getStageLevel() == 1){
										((Enemy) lhs).setBossNumber(2);
										objects.remove(lhs);
										stage.offStageSound();
										stage.setStage2();
										clock.stopClock();
										clock.exeClock();
										continue;
									}
									else if(stage.getStageLevel() == 2){
										((Enemy) lhs).setBossNumber(2);
										objects.remove(lhs);
										stage.offStageSound();
										stage.setStage3();
										clock.stopClock();
										clock.exeClock();
										continue;
									}
									else if(stage.getStageLevel() == 3){
										stage.offStageSound();
										clock.stopClock();
										game.change("Rank");

										thread.stop();
										continue;
									}
								}
							}
							//					removal.add(lhs);
						}
					}
					if(lhs instanceof Bomb){
						Bomb boom = ((Bomb)lhs).generateBoom(volunm);
						effectClip = boom.getClip();
						objects.add(boom);
						objects.remove(lhs);
						if(((Bomb)lhs).getNum()!=1){
							continue;
						}

						else if(((Bomb)lhs).getNum()==1){
							if(rhs instanceof Bullet)
								objects.remove(rhs);
							if(rhs instanceof Enemy){	//추가
								if(((Enemy) rhs).getEnemyNumber()==1 && ((Enemy) rhs).getEnemyLife()<1){
									objects.remove(lhs);
									objects.remove(rhs);
									continue;
								}
								else if(((Enemy) rhs).getEnemyNumber()==1 && ((Enemy) rhs).getEnemyLife()>=1){
									((Enemy) rhs).decreaseEnemyLife(Bomb.getDamage());
									if(lhs instanceof Bomb){
										objects.remove(lhs);
									}
									continue;
								}
								if(((Enemy) rhs).getItemEnemyNumber()==1 && ((Enemy) rhs).getEnemyLife()<1){
									generateItem((Enemy)rhs);
									objects.remove(lhs);
									objects.remove(rhs);
									continue;
								}
								else if(((Enemy) rhs).getItemEnemyNumber()==1){
									((Enemy) rhs).decreaseEnemyLife(Bomb.getDamage());
									if(lhs instanceof Bullet){
										objects.remove(lhs);
									}
									continue;
								}
								if(rhs instanceof Enemy){
									if(((Enemy) rhs).getBossNumber() == 1 && ((Enemy) rhs).getEnemyLife() >=1){
										((Enemy) rhs).decreaseEnemyLife(Bomb.getDamage());
										objects.remove(lhs);
										continue;
									}
								}
								//추가 끝
								if(((Enemy) rhs).getBossNumber() == 1 && ((Enemy) rhs).getEnemyLife() <1){
									if(stage.getStageLevel() == 1){
										((Enemy) lhs).setBossNumber(2);
										objects.remove(rhs);
										stage.offStageSound();
										stage.setStage2();
										clock.stopClock();
										clock.exeClock();
										continue;
									}
									else if(stage.getStageLevel() == 2){
										((Enemy) lhs).setBossNumber(2);
										objects.remove(rhs);
										stage.offStageSound();
										stage.setStage3();
										clock.stopClock();
										clock.exeClock();
										continue;
									}
									else if(stage.getStageLevel() == 3){
										stage.offStageSound();
										game.change("Rank");	

										clock.stopClock();
										thread.stop();
										continue;
									}
								}
							}
						}
						//					removal.add(rhs);
					}
				}

				if(lhs instanceof Item){			//아이템 관련 충돌 조건
					if(rhs instanceof Player){
						if(((Item) lhs).getItemNum()==1){
							info.setLife(info.getLife()+1);
						}
						else if(((Item) lhs).getItemNum()==2){
							info.setBomb(info.getBomb()+1);
						}
						else if(((Item) lhs).getItemNum()==3){
							info.setMissile(info.getMissile()+1);
						}
						objects.remove(lhs);
						continue;
					}
					else{
						continue;
					}
				}
				if(rhs instanceof Item){			//아이템 관련 충돌 조건
					if(lhs instanceof Player){
						if(((Item) rhs).getItemNum()==1){
							info.setLife(info.getLife()+1);
						}
						else if(((Item) rhs).getItemNum()==2){
							info.setBomb(info.getBomb()+1);
						}
						else if(((Item) rhs).getItemNum()==3){
							info.setMissile(info.getMissile()+1);
						}
						objects.remove(rhs);
						continue;
					}
					else{
						continue;
					}
				}
				player.setScore();
				if((lhs==player || rhs==player) && (lhs instanceof Bullet || rhs instanceof Bullet)){	//플레이어 자신이 쏜 총알에 플레이어가 맞았을때
					if(lhs instanceof Bullet){
						if(((Bullet)lhs).getOwnerId() == player.getId()){
							continue;
						}
					}
					if(rhs instanceof Bullet){
						if(((Bullet)rhs).getOwnerId() == player.getId()){
							continue;
						}
					}
				}
				if(info.getLife()!=0 && (lhs==player || rhs==player)){	//플레이어와 다른 오브젝트와 충돌했을 때
					info.setLife(info.getLife()-1);
					info.setMissile(1);
					if(lhs instanceof Bullet)
						objects.remove(lhs);
					if(rhs instanceof Bullet)
						objects.remove(rhs);
					if(lhs instanceof Enemy){
						((Enemy) lhs).decreaseEnemyLife(1);
						if(((Enemy) lhs).getEnemyLife() < 1){
							objects.remove(lhs);
						}
					}
					if(rhs instanceof Enemy){
						((Enemy) rhs).decreaseEnemyLife(1);
						if(((Enemy) rhs).getEnemyLife() < 1){
							objects.remove(rhs);
						}
					}
					continue;
				}
				else if(info.getLife()<1 && (lhs==player || rhs==player)){
					if(stage.isExeClip() != null){
						stage.offStageSound();
					}

					game.change("Rank");

					clock.stopClock();
					thread.stop();
					continue;
				}
				removal.add(lhs);
				removal.add(rhs);
			}
		}

		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i).isOutOfScreen(getWidth(), getHeight()))
				removal.add(objects.get(i));
		}

		for (GameObject each: removal)
			objects.remove(each);

	}

	/**
	 * 블랙홀을 발생시키는 메소드
	 */
	public synchronized void generateBlackHoll(){
		blackIng = 1;
		objects.add(new BlackHoll(player.getId(), volunm));
		chargeBar.setBarSize(0);
	}
	/**
	 * 적기와 총알을 생성하는 메소드
	 */
	public synchronized void generateEnemyAndBullet() {		//총알을 가진 
		if (Enemy.isSpawnable()){	//출격할수 있으면
			Enemy e = Enemy.spawn(640, 700);
			if(stage.getStageLevel()==2)
				e.setEnemyLife(1);
			if(stage.getStageLevel()==3)
				e.setEnemyLife(2);
			objects.add(e);
		}
		ArrayList<GameObject> bullets1 = new ArrayList<GameObject>();
		for (int i = 0 ; i<objects.size() ; i++){
			if (!(objects.get(i) instanceof Enemy))	//해당오브젝트가 적기가 아니면
				continue;	
			Enemy enemy = (Enemy) objects.get(i);
			if(enemy.canShot() && blackIng != 1){
				bullets1.add(enemy.shot());
			}

		}
		objects.addAll(bullets1);
	}

	/**
	 * 보스와 총알을 생성하는 메소드
	 */
	public synchronized void generateBossAndBullet(){
		if (bossFlag ==0){	//출격할수 있으면
			Enemy e = Enemy.bossSpawn(640, 700);
			stage.setBossStage();
			if(stage.getStageLevel()==2)
				e.setEnemyLife(70);
			if(stage.getStageLevel()==3)
				e.setEnemyLife(100);
			objects.add(e);
			bossFlag++;
		}

		ArrayList<GameObject> bullets2 = new ArrayList<GameObject>();
		for (int i = 0 ; i<objects.size() ; i++){
			if (!(objects.get(i) instanceof Enemy))	//해당오브젝트가 적기가 아니면
				continue;	
			Enemy enemy = (Enemy) objects.get(i);
			if(enemy.canShot() && blackIng != 1){
				bullets2.add(enemy.shot() );
			}
		}
		objects.addAll(bullets2);		//총알 리스트에 있는 요소드를 모두 오브젝트에 더하라.
	}
	/**
	 * 아이템을 가진 적기와 총알을 생성하는 메소드
	 */
	public synchronized void generateItemEnemyAndBullet(){
		if (itemEnemyFlag == 0){
			Enemy e = Enemy.itemEnemySpawn(640, 700);
			if(stage.getStageLevel()==2)
				e.setEnemyLife(2);
			if(stage.getStageLevel()==3)
				e.setEnemyLife(4);
			objects.add(e);
			itemEnemyFlag++;
		}
		ArrayList<GameObject> bullets3 = new ArrayList<GameObject>();
		for (int i = 0 ; i<objects.size() ; i++){
			if (!(objects.get(i) instanceof Enemy))	//해당오브젝트가 적기가 아니면
				continue;	
			Enemy enemy = (Enemy) objects.get(i);
			if(enemy.canShot() && blackIng != 1)
				bullets3.add(enemy.shot());
		}
		objects.addAll(bullets3);		//총알 리스트에 있는 요소드를 모두 오브젝트에 더하라.
	}
	/**
	 * 아이템을 생성한다.
	 * @param enemy 아이템을 가질 적기 객체
	 */
	public void generateItem(Enemy enemy){
		objects.add(Item.itemSpawn(640, 700, enemy));
	}

	public static Clip isExeClip(){
		return effectClip;
	}

	public static double getVolunm(){
		return volunm;
	}

	public static void setVolunm(double v){
		volunm = v;
	}

	/**
	 * 모든 오브젝트를 그려주는 메소드
	 */
	public synchronized void paint(Graphics g) {	//모든 오브젝트를 그려준다.
		Graphics2D g2d = (Graphics2D) g;


		image = stage.getImage();
		g.drawImage(image, 0, 0, this);

		for (GameObject each: objects){
			each.paint(g2d);
		}
		String score = "Score:" + Integer.toString(player.getScore());
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Jokerman",Font.ITALIC,50));
		g.drawString(score,360,80);

		String life = "Life:" + Integer.toString(info.getLife());
		g.setColor(Color.RED);
		g.setFont(new Font("Helvetica",Font.PLAIN,30));
		g.drawString(life,30,600);

		String bomb = "Bomb:" + Integer.toString(info.getBomb());
		g.setColor(Color.RED);
		g.setFont(new Font("Helvetica",Font.PLAIN,30));
		g.drawString(bomb,120,600);

		String missile = "Missile:" + Integer.toString(info.getMissile());
		g.setColor(Color.RED);
		g.setFont(new Font("Helvetica",Font.PLAIN,30));
		g.drawString(missile,240,600);

		chargeBar.paint(g);
	}
	/**
	 * 페인트에서 그린것을 다시 그리는 메소드
	 * @param g Graphics2D 파라미터
	 */
	public void update(Graphics2D g){
		paint(g);
	}
}
