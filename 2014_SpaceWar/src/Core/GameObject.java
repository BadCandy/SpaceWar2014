package Core;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
/**
 * ��κ��� ���� ��ҵ��� ����Ŭ������ �߻�Ŭ����. ��κ��� ���ӿ�ҵ��� �� Ŭ������ ��ӹ޾� �����ȴ�.
 * @author ������
 * @since 2014.11.13
 * @version 1.0
 */
public abstract class GameObject {			//��� ���� ���� ��ü�� ���� �߻� Ŭ����
    private static long idSerial = 0;  //������Ʈ�� �ڽ��� ���� ���̵� ������.
    
    protected long id = ++idSerial;  //id�� ���������� �߱��Ѵ�.(���� �������� ������Ű�鼭 ��´�.)
    //�ڱⰡ �� �Ѿ˿��� �� �µ��� ó���ϱ����� ���ȴ�.
    protected Point2D location = new Point2D.Float();
    protected int width, height;
    protected boolean enemyCode = false;
    /**
     * GameObject�� ������
     * @param width ���� ������Ʈ�� ��
     * @param height ���� ������Ʈ�� ����
     */
    public GameObject(int width, int height) {	//���� ������Ʈ ����
        this.width = width;
        this.height = height;
    }

	/**
     * ���ӿ�����Ʈ�� ó�� ��ġ�� ���ϴ� �޼ҵ�
     * @param x x ��ǥ
     * @param y y ��ǥ
     */
    public void setLocation(float x, float y) {	//��ġ ���ϱ�
        this.location.setLocation(x, y);
    }
    /**
     * �ش� ������Ʈ�� �����̸� enemyCode�� set�ϱ����� ���Ǵ� �޼ҵ�
     */
    public void setEnemyCode(){
    	enemyCode = true;
    }
    /**
     * �ش������Ʈ�� �������� �ƴ����� �÷��׸� ��ȯ�ϴ� �޼ҵ�
     * @return enemyCode�� ��ȯ�Ѵ�.
     */
    public boolean getEnemyCode(){
    	return enemyCode;
    }
    /**
     * ���ӿ�����Ʈ�� �浹 ������ ��ȯ�ϴ� �޼ҵ�
     * @return �浹������ ��ȯ
     */
    protected Rectangle2D getBound() {    //�浹 ������ ��ȯ�ϴ� �޼ҵ�
        return new Rectangle2D.Float((float) location.getX(), (float) location.getY(), width, height);
    }
    /**
     * ���ӿ�����Ʈ�� �浹 ���θ� �˻��ϴ� �޼ҵ�
     * @param other �ٸ� ���ӿ�����Ʈ
     * @return ���� ������Ʈ�� ���ٸ� false, �ٸ��� true ��ȯ
     */
    public boolean check(GameObject other) {	//�浹 �˻� �޼ҵ�
        if (this == other)	//���� ������Ʈ�� ���ٸ� false
            return false;
        return getBound().intersects(other.getBound()); // �ٸ� ������Ʈ�� �浹������  ��ģ�ٸ� true
    }
    /**
     * ���ӿ�����Ʈ�� ���� id�� ��ȯ�ϴ� �޼ҵ�.
     * @return id�� �� ��ȯ
     */
    public long getId() {
        return id;
    }
    /**
     * ���ӿ�����Ʈ�� ���̸� ��ȯ�ϴ� �޼ҵ�.
     * @return height�� �� ��ȯ
     */
    public int getHeight() {
        return height;
    }
    /**
     * ���ӿ�����Ʈ�� ���� ��ȯ�ϴ� �޼ҵ�
     * @return width�� �� ��ȯ
     */
    public int getWidth() {
        return width;
    }
    /**
     * ���ӿ�����Ʈ�� �׸��� �߻�Ŭ����. ��ӹ޾� ����Ѵ�.
     * @param g2d Graphics2D�� ���۷���
     */
    public abstract void paint(Graphics2D g2d);		//�׸���
    /**
     * ���ӿ�����Ʈ�� ��ġ�� ������Ʈ�ϴ� �߻�Ŭ����. ��ӹ޾� ����Ѵ�.
     */
    public abstract void update();			//����
    /**
     * ���ӿ�����Ʈ�� ������ �������� �Ǵ��ϴ� �޼ҵ�
     * @param screenWidth ���罺ũ���� ��
     * @param screenHeight ���� ��ũ���� ����
     * @return ��ӹ��� Ŭ�������� ������ boolean��
     */
    public abstract boolean isOutOfScreen(int screenWidth, int screenHeight);	//ȭ�� �ٱ����� ��������
}