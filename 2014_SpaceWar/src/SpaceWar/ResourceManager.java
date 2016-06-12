package SpaceWar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * �̹���, �������Ͽ� ���� ���ҽ��� �����ϴ� Ŭ����
 * @author ����ö
 * @since 2014.11.13
 * @version 1.0
 * @see ResourceType
 */
public class ResourceManager { //������ �� �� ���� ���� ������ resource�� ���� �ʿ䰡 �����Ƿ�, bufferedImage�� ����Ͽ� resource����.
	static ResourceManager instance = new ResourceManager();  //�� Ŭ������ ���� ��ü�� �� �ϳ��� �����ϸ� �ǹǷ� ���������� �� singleton���� ���
	private File stage1 = new File("stage1.jpg");
	private File stage2 = new File("stage2.jpg");
	private File stage3 = new File("stage3.jpg");
	private File enemy = new File("enemy.jpg");
	private File ship = new File("player.jpg");
	private File background = new File("���ȭ��.jpg");
	private File background2 = new File("���ȭ�� (2).jpg");
	private File boss1 = new File("boss1.jpg");
	private File item = new File("Item.png");
	private File itemEnemy = new File("itemEnemy.jpg");
	private File bomb = new File("bomb.jpg");
	private File boom = new File("booom.png");
	private File blackHoll = new File("BlackHoll.png");
	private File s_blackHoll = new File("BlackHole.wav");
	private File pBullet = new File("pBullet.png");
	private File eBullet = new File("eBullet.png");
	private File eBoom = new File("booom1.png");
	private File sBoom = new File("boom.wav");
	private File sMainMenu = new File("mainmenu.wav");
	private File sStage1 = new File("stage1.wav");
	private File sStage2 = new File("stage2.wav");
	private File sStage3 = new File("stage3.wav");
	private File sBoss = new File("sBoss.wav");
	private File sBooom = new File("booom.wav");

	/**
	 * resourceMangager�� �ν��Ͻ��� ��� �żҵ�
	 * @return ResourceManager�� ��ü�� ����
	 */
    public static ResourceManager getInstance() {     //�� Ŭ����(resourceMangager�� �ν��Ͻ��� ��´�.
        return instance;
    }
    private Map<ResourceType, BufferedImage> imageMap = new HashMap<ResourceType, BufferedImage>(); //hashmap�� �̿��Ͽ� key�� value�� ������� ����
    private Map<ResourceType, File> audioMap = new HashMap<ResourceType, File>();
    /**
     * ResourceManager ������, Resource�� �п��鿩 ResourceType�� �����Ѵ�.
     */
    private ResourceManager() {              //�����ڸ� private�� ���� �ٸ��������� ������ ������
        try {
        	imageMap.put(ResourceType.ENEMY_SHIP, ImageIO.read(enemy));
            imageMap.put(ResourceType.PLAYER_SHIP, ImageIO.read(ship));
            imageMap.put(ResourceType.BACKGROUND, ImageIO.read(background));
            imageMap.put(ResourceType.BOSS1, ImageIO.read(boss1));
            imageMap.put(ResourceType.STAGE1, ImageIO.read(stage1));
            imageMap.put(ResourceType.STAGE2, ImageIO.read(stage2));
            imageMap.put(ResourceType.STAGE3, ImageIO.read(stage3));
            imageMap.put(ResourceType.ITEM, ImageIO.read(item));
            imageMap.put(ResourceType.ITEM_ENEMY, ImageIO.read(itemEnemy));
            imageMap.put(ResourceType.BOMB, ImageIO.read(bomb));
            imageMap.put(ResourceType.BOOM, ImageIO.read(boom));
            imageMap.put(ResourceType.PLAYER_BULLET, ImageIO.read(pBullet));
            imageMap.put(ResourceType.ENEMY_BULLET, ImageIO.read(eBullet));
            imageMap.put(ResourceType.E_BOOM, ImageIO.read(eBoom));
            imageMap.put(ResourceType.BACKGROUND2, ImageIO.read(background2));
            imageMap.put(ResourceType.BLACKHOLL, ImageIO.read(blackHoll));
            audioMap.put(ResourceType.S_BOOM, sBoom);
            audioMap.put(ResourceType.S_BOOOM, sBooom);
            audioMap.put(ResourceType.S_MAINMENU, sMainMenu);
            audioMap.put(ResourceType.S_STAGE1, sStage1);
            audioMap.put(ResourceType.S_STAGE2, sStage2);
            audioMap.put(ResourceType.S_STAGE3, sStage3);
            audioMap.put(ResourceType.S_BOSS, sBoss);
            audioMap.put(ResourceType.S_BLACKHOLE, s_blackHoll);
        } catch (IOException e) {
            System.err.println("Cannot load resources");
            System.exit(0);
        }
    }
    /**
     * �ش��ϴ� �����̹����� ��� �޼ҵ�
     * @param type ResourceType�� �̹��� Ÿ��
     * @return �ش��ϴ� �����̹��� ��ȯ
     */
    public BufferedImage getImage(ResourceType type) {  //�����̹��� ���
        return imageMap.get(type);
    }
    /**
     * �ش��ϴ� ���� Clip�� ��� �޼ҵ�
     * @param type ResourceType�� Clip Ÿ��
     * @return �ش��ϴ� Clip ��ȯ
     */
    public File getAudio(ResourceType type){
    	return audioMap.get(type);
    }
}