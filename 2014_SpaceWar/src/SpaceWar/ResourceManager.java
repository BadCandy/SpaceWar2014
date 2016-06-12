package SpaceWar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * 이미지, 음악파일에 관한 리소스를 관리하는 클래스
 * @author 정윤철
 * @since 2014.11.13
 * @version 1.0
 * @see ResourceType
 */
public class ResourceManager { //게임이 한 번 떠서 끝날 때까지 resource를 내릴 필요가 없으므로, bufferedImage를 사용하여 resource관리.
	static ResourceManager instance = new ResourceManager();  //이 클래스는 게임 전체에 딱 하나만 존재하면 되므로 정적변수를 둔 singleton패턴 사용
	private File stage1 = new File("stage1.jpg");
	private File stage2 = new File("stage2.jpg");
	private File stage3 = new File("stage3.jpg");
	private File enemy = new File("enemy.jpg");
	private File ship = new File("player.jpg");
	private File background = new File("배경화면.jpg");
	private File background2 = new File("배경화면 (2).jpg");
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
	 * resourceMangager의 인스턴스를 얻는 매소드
	 * @return ResourceManager의 객체를 리턴
	 */
    public static ResourceManager getInstance() {     //이 클래스(resourceMangager의 인스턴스를 얻는다.
        return instance;
    }
    private Map<ResourceType, BufferedImage> imageMap = new HashMap<ResourceType, BufferedImage>(); //hashmap을 이용하여 key와 value를 연결시켜 저장
    private Map<ResourceType, File> audioMap = new HashMap<ResourceType, File>();
    /**
     * ResourceManager 생성자, Resource를 읽여들여 ResourceType과 연결한다.
     */
    private ResourceManager() {              //생성자를 private로 만들어서 다른곳에서의 생성을 막았음
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
     * 해당하는 버퍼이미지를 얻는 메소드
     * @param type ResourceType의 이미지 타입
     * @return 해당하는 버퍼이미지 반환
     */
    public BufferedImage getImage(ResourceType type) {  //버퍼이미지 얻기
        return imageMap.get(type);
    }
    /**
     * 해당하는 음악 Clip을 얻는 메소드
     * @param type ResourceType의 Clip 타입
     * @return 해당하는 Clip 반환
     */
    public File getAudio(ResourceType type){
    	return audioMap.get(type);
    }
}