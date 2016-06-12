package SpaceWar;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Util.Rank;
import Util.Stage;

//JavaDoc
/**
 * SpaceWar2014 게임 프레임
 * @author 정윤철
 * @since 2014.11.13
 * @version 1.0
 * @see GameScreen
 * @see MainMenu
 */
public class SpaceWar2014 extends JFrame{
	private JPanel GameScreen;
	private JPanel rank, mainMenu;
	private Stage stage;

	/**
	 * SpaceWar2014 Constructor
	 * @param title SuperClass에 전달되는 제목 파라미터
	 */
	public SpaceWar2014(String title) {
		super(title);
		stage = new Stage();
	}
	/**
	 * SpaceWar2014 프레임을 실행시키는 메인메소드
	 * @param args Arguments로 전달되는 파라미터
	 */
	public static void main(String[] args) {
		SpaceWar2014 game = new SpaceWar2014("SpaceWar 2014");

		game.add(new MainMenu(game , game.getStage()));
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.createMenu();
		game.setSize(640,700);
		game.setVisible(true);  
		game.setResizable(false);  
		game.setLocationRelativeTo(null);
	}
	public Stage getStage(){
		return stage;
	}
	/**
	 * 파라미터에 해당하는 패널이름으로 패널을 변경하는 메소드
	 * @param panelName 변경할 패널이름 파라미터
	 */
	public void change(String panelName){
		if(panelName.equals("GameScreen")){
			getContentPane().removeAll();
			GameScreen = new GameScreen(this, stage);
			getContentPane().add(GameScreen);
			KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
			manager.addKeyEventDispatcher((KeyEventDispatcher) GameScreen);
			revalidate();
			repaint();
		}
		else if(panelName.equals("Rank")){
			getContentPane().removeAll();
			rank = new Rank(this);
			getContentPane().add(rank);
			revalidate();
			repaint();
		}
		else if(panelName.equals("MainMenu")){
			getContentPane().removeAll();
			mainMenu = new MainMenu(this, stage);
			getContentPane().add(mainMenu);
			revalidate();
			repaint();
		}
	}
	/**
	 * 키세팅과 효과음을 설정하는 탭메뉴를 구현하는 메소드
	 */
	public void createMenu(){
		JMenuBar mb = new JMenuBar();
	      
	      JMenu keySetMenu = new JMenu("KeySetting");
	      JMenuItem item2 = new JMenuItem("Setting");
	      
	      
	      //keySetMenu.add(new JMenuItem());
	      item2.addActionListener(new ActionListener(){
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            // TODO Auto-generated method stub
	            KeysetFrame kf = new KeysetFrame();
	         }
	         
	      });
	      keySetMenu.add(item2);
	      mb.add(keySetMenu);
		
		JMenu soundEffectMenu = new JMenu("Sound");
		JMenuItem soundItem = new JMenuItem("Setting");
		soundItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				VolunmFrame vf = new VolunmFrame(Stage.getVolunm(), ((SpaceWar.GameScreen) GameScreen).getVolunm(), Stage.isExeClip());
			}
		});
		soundEffectMenu.add(soundItem);
		
		mb.add(soundEffectMenu);
		setJMenuBar(mb);
	}
	
}

