package Core;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;


/**
 * GameObject�� ��ӹ��� ����⿡ ���� �߻�Ŭ����. Player�� Enemy�� ��ӹ޴´�.
 * @author ������
 * @since 2014.11.13
 * @version 1.0
 * @see GameObject
 */
public abstract class Ship extends GameObject {
    private BufferedImage image;
    /**
     * Ship�� ������. � image ������� �������� �����ڷ� �̿� ���� ������ �޴´�.
     * @param image BufferedImage Ÿ���� �̹���
     */
    public Ship(BufferedImage image) {	//� image ������� �������� �����ڷ� �̿� ���� ������ �޴´�.
        super(image.getWidth(), image.getHeight());
        //����Ŭ������ �ʺ�, ���� �����ϸ� �߻�޼ҵ带 ������ ��ӹ��� �޼ҵ带 �ϼ� ��Ų��.
        this.image = image;
    }
    /**
     * ���� ������� ��ġ�� ����� �׸��� �߾����� �����Ͽ� �׸��� �޼ҵ�
     */
    public void paint(Graphics2D g2d) {	//���� ������� ��ġ�� �׸��� �߾����� �����Ͽ� �׸���.
        int x = (int) Math.round(location.getX() - width / 2);
        int y = (int) Math.round(location.getY() - height / 2);
        g2d.drawImage(image, x, y, null);		//�׷��׷�
    }
    @Override
    //���� paint�� ���� �浹������ �ٲ�� �ȴ�. (���� �浹 ������ x, y) �浹������ ���ļ� ��ȯ�Ѵ�.
    /**
     * �� Ŭ������ paint() �޼ҵ�� ���� �ѵ������� �ٲ�� �Ǿ� �浹������ �������ϴ� �޼ҵ�.
     */
    protected Rectangle2D getBound() {
        return new Rectangle2D.Float((float) location.getX() - width / 2,
                (float) location.getY() - height / 2, width, height);
    }
}