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
 * SpaceWar2014 ���� ������
 * @author ����ö
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
	 * @param title SuperClass�� ���޵Ǵ� ���� �Ķ����
	 */
	public SpaceWar2014(String title) {
		super(title);
		stage = new Stage();
	}
	/**
	 * SpaceWar2014 �������� �����Ű�� ���θ޼ҵ�
	 * @param args Arguments�� ���޵Ǵ� �Ķ����
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
	 * �Ķ���Ϳ� �ش��ϴ� �г��̸����� �г��� �����ϴ� �޼ҵ�
	 * @param panelName ������ �г��̸� �Ķ����
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
	 * Ű���ð� ȿ������ �����ϴ� �Ǹ޴��� �����ϴ� �޼ҵ�
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

