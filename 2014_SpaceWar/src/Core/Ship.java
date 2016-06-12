package Core;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;


/**
 * GameObject를 상속받은 비행기에 관한 추상클래스. Player와 Enemy가 상속받는다.
 * @author 김현준
 * @since 2014.11.13
 * @version 1.0
 * @see GameObject
 */
public abstract class Ship extends GameObject {
    private BufferedImage image;
    /**
     * Ship의 생성자. 어떤 image 기반으로 동작할지 생성자로 이에 대한 정보를 받는다.
     * @param image BufferedImage 타입의 이미지
     */
    public Ship(BufferedImage image) {	//어떤 image 기반으로 동작할지 생성자로 이에 대한 정보를 받는다.
        super(image.getWidth(), image.getHeight());
        //상위클래스에 너비, 높이 전달하며 추상메소드를 제외한 상속받은 메소드를 완성 시킨다.
        this.image = image;
    }
    /**
     * 현재 비행기의 위치를 비행기 그림의 중앙으로 보정하여 그리는 메소드
     */
    public void paint(Graphics2D g2d) {	//현재 비행기의 위치를 그림의 중앙으로 보정하여 그린다.
        int x = (int) Math.round(location.getX() - width / 2);
        int y = (int) Math.round(location.getY() - height / 2);
        g2d.drawImage(image, x, y, null);		//그려그려
    }
    @Override
    //위의 paint로 인해 충돌영역이 바뀌게 된다. (기존 충돌 영역은 x, y) 충돌영역을 고쳐서 반환한다.
    /**
     * 이 클래스의 paint() 메소드로 인해 총돌영역이 바뀌게 되어 충돌영역을 재정의하는 메소드.
     */
    protected Rectangle2D getBound() {
        return new Rectangle2D.Float((float) location.getX() - width / 2,
                (float) location.getY() - height / 2, width, height);
    }
}