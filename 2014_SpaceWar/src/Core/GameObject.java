package Core;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
/**
 * 대부분의 게임 요소들의 상위클래스인 추상클래스. 대부분의 게임요소들은 이 클래스를 상속받아 구현된다.
 * @author 김현준
 * @since 2014.11.13
 * @version 1.0
 */
public abstract class GameObject {			//모든 게임 내의 물체에 대한 추상 클래스
    private static long idSerial = 0;  //오브젝트는 자신의 고유 아이디를 가진다.
    
    protected long id = ++idSerial;  //id는 순차적으로 발급한다.(위의 정적변수 증가시키면서 얻는다.)
    //자기가 쏜 총알에는 안 맞도록 처리하기위해 사용된다.
    protected Point2D location = new Point2D.Float();
    protected int width, height;
    protected boolean enemyCode = false;
    /**
     * GameObject의 생성자
     * @param width 게임 오브젝트의 폭
     * @param height 게임 오브젝트의 높이
     */
    public GameObject(int width, int height) {	//게임 보으젝트 생성
        this.width = width;
        this.height = height;
    }

	/**
     * 게임오브젝트의 처음 위치를 정하는 메소드
     * @param x x 좌표
     * @param y y 좌표
     */
    public void setLocation(float x, float y) {	//위치 정하기
        this.location.setLocation(x, y);
    }
    /**
     * 해당 오브젝트가 적기이면 enemyCode를 set하기위해 사용되는 메소드
     */
    public void setEnemyCode(){
    	enemyCode = true;
    }
    /**
     * 해당오브젝트가 적기인지 아닌지의 플래그를 반환하는 메소드
     * @return enemyCode를 반환한다.
     */
    public boolean getEnemyCode(){
    	return enemyCode;
    }
    /**
     * 게임오브젝트의 충돌 영역을 반환하는 메소드
     * @return 충돌영역을 반환
     */
    protected Rectangle2D getBound() {    //충돌 영역을 반환하는 메소드
        return new Rectangle2D.Float((float) location.getX(), (float) location.getY(), width, height);
    }
    /**
     * 게임오브젝트의 충돌 여부를 검사하는 메소드
     * @param other 다른 게임오브젝트
     * @return 게임 오브젝트가 같다면 false, 다르면 true 반환
     */
    public boolean check(GameObject other) {	//충돌 검사 메소드
        if (this == other)	//게임 오브젝트가 같다면 false
            return false;
        return getBound().intersects(other.getBound()); // 다른 오브젝트의 충돌영역과  겹친다면 true
    }
    /**
     * 게임오브젝트의 고유 id를 반환하는 메소드.
     * @return id의 값 반환
     */
    public long getId() {
        return id;
    }
    /**
     * 게임오브젝트의 높이를 반환하는 메소드.
     * @return height의 값 반환
     */
    public int getHeight() {
        return height;
    }
    /**
     * 게임오브젝트의 폭을 반환하는 메소드
     * @return width의 값 반환
     */
    public int getWidth() {
        return width;
    }
    /**
     * 게임오브젝트를 그리는 추상클래스. 상속받아 사용한다.
     * @param g2d Graphics2D의 레퍼런스
     */
    public abstract void paint(Graphics2D g2d);		//그리기
    /**
     * 게임오브젝트의 위치를 업데이트하는 추상클래스. 상속받아 사용한다.
     */
    public abstract void update();			//갱신
    /**
     * 게임오브젝트가 밖으로 나갔는지 판단하는 메소드
     * @param screenWidth 현재스크린의 폭
     * @param screenHeight 현재 스크린의 높이
     * @return 상속받은 클래스에서 정의한 boolean값
     */
    public abstract boolean isOutOfScreen(int screenWidth, int screenHeight);	//화면 바깥으로 나갔는지
}