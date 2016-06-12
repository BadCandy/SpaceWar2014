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
 * ���� �÷��̿� ���� ��� ��ҵ��� ó���ϰ� �׸��� �г� ������.
 * @author ����ö
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
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();	//���� ������Ʈ�� ���� ����Ʈ
	private PlayerInfo info= new PlayerInfo();
	private Player player = Player.create(WIDTH, HEIGHT, info);	//�÷��̾��� ���� move�޼ҵ� ���� ���� �������־���ϴ� ���� �������.
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
	 * GameScreen ������, �÷��̾� ��ü�� GameObject Ÿ���� ArrayList�� �ְ� Ÿ�̸Ӹ� �ߵ���Ų��.
	 * @param game ���� ������ �Ķ����
	 * @param s �������� �Ķ����
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
	 * bar�� �����ϴ� �޼ҵ�
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
	 * upŰ�� ���� �����ϴ� �޼ҵ�
	 * @param upK Ű�� ��
	 */
	public static void setUpKey(int upK){
		up = upK;
	}
	/**
	 * downŰ�� ���� �����ϴ� �޼ҵ�
	 * @param downK Ű�� ��
	 */
	public static void setDownKey(int downK){
		down = downK;
	}
	/**
	 * leftŰ�� ���� �����ϴ� �޼ҵ�
	 * @param leftK Ű�ǰ�
	 */
	public static void setLeftKey(int leftK){
		left = leftK;
	}
	/**
	 * rightŰ�� ���� �����ϴ� �޼ҵ�
	 * @param rightK Ű�ǰ�
	 */
	public static void setRightKey(int rightK){
		right = rightK;
	}
	/**
	 * �̻���Ű�� ���� �����ϴ� �޼ҵ�
	 * @param shootK Ű�� ��
	 */
	public static void setShootKey(int shootK){
		shoot = shootK;
	}
	/**
	 * ��źŰ�� ���� �����ϴ� �޼ҵ�
	 * @param bombK Ű�� ��
	 */
	public static void setBombKey(int bombK){
		bomb = bombK;
	}
	/**
	 * ������������ �����ϴ� �������� run()�޼ҵ�. ������(���ӹ����ӵ�)�� ���߱����� �ʿ��ϴ�.
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
			player.move(dpressed,upPressed,downPressed,leftPressed,rightPressed);   //Ű�� ������ �� player��ü�� move �޼ҵ� ȣ��

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
	 * Ű���� �̺�Ʈ�� ó���ϴ� �޼ҵ�
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
	 * ��Ȧ�� ��������� ������ �������� �����Ѵ�.
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
	 * ��� ���� ������Ʈ�� ��ġ�� ���� ������ ������Ʈ �ϴ� �޼ҵ�
	 */
	public synchronized void updateAll() {			//��� ���� ������Ʈ�� ������Ʈ �Ѵ�.
		for (int i = 0; i < objects.size(); i++) {
			if(objects.get(i) == null){
				continue;
			}
			else
				objects.get(i).update();
		}
	}
	/**
	 * ������ ������Ʈ�� ���Ͽ� �浹������ �ϴ� �޼ҵ�
	 */
	public synchronized void checkCollision() {		//���� ����                  //������ ���� Ŭ�������� ó���ϸ� �ڵ尡 ���������Ƿ� �ΰ����� �浹������ ���⼭ ó����. ���߿� �ڵ� �����ϱⰡ ������
		Set<GameObject> removal = new HashSet<GameObject>();	//hashset�� �̿��Ѵ�. ������ ��� ã��

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
					if (!(lhs.check(rhs)) || (rhs.getEnemyCode() && lhs.getEnemyCode())) continue; //���� ���ð� ����� ������ �浹���� üũ.
				}
				if ((lhs instanceof Bomb && rhs instanceof Player) || (lhs instanceof Player && rhs instanceof Bomb))//��ź�� �÷��̾� �浹����
					continue;
				if ((lhs instanceof Bomb && rhs instanceof Bomb))  //��ź���� �浹�ϴ°��� ��������
					continue;
				if ((lhs instanceof Bullet && rhs instanceof Bullet))  //��ź���� �浹�ϴ°��� ��������
					if(((Bullet)lhs).getEnemyCode()==false && ((Bullet)rhs).getEnemyCode()==false)
						continue;

				if(((lhs instanceof Enemy) || (rhs instanceof Enemy)) && ((lhs instanceof Bullet) || (rhs instanceof Bullet))){	//����� �Ѿ˰��� �浹��
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
						else if(((Enemy) lhs).getEnemyNumber()==1 && ((Enemy) lhs).getEnemyLife()>=1){ //lhs�� �����۱��̰� lhs�� ����� 0�� �ƴҶ�
							((Enemy) lhs).decreaseEnemyLife(Bullet.getDamage());	//�������
							if(rhs instanceof Bullet){			//rhs�� �Ѿ��̸�
								objects.remove(rhs);			//rhs�� ���ش�
							}
							continue;
						}
						if(((Enemy) lhs).getItemEnemyNumber()==1 && ((Enemy) lhs).getEnemyLife()<1){	//lhs�� �����۱��̰� lhs�� ����� 0�϶�
							Enemy boom = ((Enemy) lhs).generateBooom(volunm);
							effectClip = boom.getClip();
							generateItem((Enemy)lhs);
							objects.remove(lhs);
							objects.remove(rhs);
							continue;
						}
						else if(((Enemy) lhs).getItemEnemyNumber()==1 && ((Enemy) lhs).getEnemyLife()>=1){ //lhs�� �����۱��̰� lhs�� ����� 0�� �ƴҶ�
							((Enemy) lhs).decreaseEnemyLife(Bullet.getDamage());	//�������
							if(rhs instanceof Bullet){			//rhs�� �Ѿ��̸�
								objects.remove(rhs);			//rhs�� ���ش�
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
						if(rhs instanceof Enemy){	//�߰�
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
						}	//�߰� ��
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







				if((lhs instanceof Bomb) || (rhs instanceof Bomb)){					//��ź �������� �浹 ����
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
								else if(((Enemy) lhs).getEnemyNumber()==1 && ((Enemy) lhs).getEnemyLife()>=1){ //lhs�� �����۱��̰� lhs�� ����� 0�� �ƴҶ�
									((Enemy) lhs).decreaseEnemyLife(Bomb.getDamage());	//�������
									if(rhs instanceof Bomb){			//rhs�� �Ѿ��̸�
										objects.remove(rhs);			//rhs�� ���ش�
									}
									continue;
								}
								if(((Enemy) lhs).getItemEnemyNumber()==1 && ((Enemy) lhs).getEnemyLife()<1){	//lhs�� �����۱��̰� lhs�� ����� 0�϶�
									generateItem((Enemy)lhs);
									objects.remove(lhs);
									objects.remove(rhs);
									continue;
								}
								else if(((Enemy) lhs).getItemEnemyNumber()==1 && ((Enemy) lhs).getEnemyLife()>=1){ //lhs�� �����۱��̰� lhs�� ����� 0�� �ƴҶ�
									((Enemy) lhs).decreaseEnemyLife(Bomb.getDamage());	//�������
									if(rhs instanceof Bomb){			//rhs�� �Ѿ��̸�
										objects.remove(rhs);			//rhs�� ���ش�
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
							if(rhs instanceof Enemy){	//�߰�
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
								//�߰� ��
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

				if(lhs instanceof Item){			//������ ���� �浹 ����
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
				if(rhs instanceof Item){			//������ ���� �浹 ����
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
				if((lhs==player || rhs==player) && (lhs instanceof Bullet || rhs instanceof Bullet)){	//�÷��̾� �ڽ��� �� �Ѿ˿� �÷��̾ �¾�����
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
				if(info.getLife()!=0 && (lhs==player || rhs==player)){	//�÷��̾�� �ٸ� ������Ʈ�� �浹���� ��
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
	 * ��Ȧ�� �߻���Ű�� �޼ҵ�
	 */
	public synchronized void generateBlackHoll(){
		blackIng = 1;
		objects.add(new BlackHoll(player.getId(), volunm));
		chargeBar.setBarSize(0);
	}
	/**
	 * ����� �Ѿ��� �����ϴ� �޼ҵ�
	 */
	public synchronized void generateEnemyAndBullet() {		//�Ѿ��� ���� 
		if (Enemy.isSpawnable()){	//����Ҽ� ������
			Enemy e = Enemy.spawn(640, 700);
			if(stage.getStageLevel()==2)
				e.setEnemyLife(1);
			if(stage.getStageLevel()==3)
				e.setEnemyLife(2);
			objects.add(e);
		}
		ArrayList<GameObject> bullets1 = new ArrayList<GameObject>();
		for (int i = 0 ; i<objects.size() ; i++){
			if (!(objects.get(i) instanceof Enemy))	//�ش������Ʈ�� ���Ⱑ �ƴϸ�
				continue;	
			Enemy enemy = (Enemy) objects.get(i);
			if(enemy.canShot() && blackIng != 1){
				bullets1.add(enemy.shot());
			}

		}
		objects.addAll(bullets1);
	}

	/**
	 * ������ �Ѿ��� �����ϴ� �޼ҵ�
	 */
	public synchronized void generateBossAndBullet(){
		if (bossFlag ==0){	//����Ҽ� ������
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
			if (!(objects.get(i) instanceof Enemy))	//�ش������Ʈ�� ���Ⱑ �ƴϸ�
				continue;	
			Enemy enemy = (Enemy) objects.get(i);
			if(enemy.canShot() && blackIng != 1){
				bullets2.add(enemy.shot() );
			}
		}
		objects.addAll(bullets2);		//�Ѿ� ����Ʈ�� �ִ� ��ҵ带 ��� ������Ʈ�� ���϶�.
	}
	/**
	 * �������� ���� ����� �Ѿ��� �����ϴ� �޼ҵ�
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
			if (!(objects.get(i) instanceof Enemy))	//�ش������Ʈ�� ���Ⱑ �ƴϸ�
				continue;	
			Enemy enemy = (Enemy) objects.get(i);
			if(enemy.canShot() && blackIng != 1)
				bullets3.add(enemy.shot());
		}
		objects.addAll(bullets3);		//�Ѿ� ����Ʈ�� �ִ� ��ҵ带 ��� ������Ʈ�� ���϶�.
	}
	/**
	 * �������� �����Ѵ�.
	 * @param enemy �������� ���� ���� ��ü
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
	 * ��� ������Ʈ�� �׷��ִ� �޼ҵ�
	 */
	public synchronized void paint(Graphics g) {	//��� ������Ʈ�� �׷��ش�.
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
	 * ����Ʈ���� �׸����� �ٽ� �׸��� �޼ҵ�
	 * @param g Graphics2D �Ķ����
	 */
	public void update(Graphics2D g){
		paint(g);
	}
}
