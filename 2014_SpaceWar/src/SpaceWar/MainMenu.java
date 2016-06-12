package SpaceWar;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Core.Player;
import Util.Stage;
/**
 * ���θ޴��� �׸��� �г�
 * @author ����ö
 * @since 2014.11.13
 * @version 1.0
 * @see ResourceManager
 */
public class MainMenu extends JPanel {
   private JLabel title;
   private JButton startBtn,rankBtn;
   private SpaceWar2014 game;
   private BufferedImage backGround;
   private Stage stage;
//   private Clip clip;
   /**
    * MainMenu ������
    * @param f ����������
    * @param s ��������
    */
   public MainMenu(SpaceWar2014 f, Stage s) {
	  Player.resetScore();
      setSize(640, 700);
      setLayout(null);
      setFocusable(false);
      game = f;
      stage = s;
      backGround = ResourceManager.getInstance().getImage(
            ResourceType.BACKGROUND);
      stage.setMainMenu();

      ImageIcon titleI = new ImageIcon("Title.jpg");

      title = new JLabel(titleI);
      title.setBounds(15, 30, 600, 300);
      add(title);

      ImageIcon mainS = new ImageIcon("MainStart.jpg");
      startBtn = new JButton(mainS);
      startBtn.setBounds(230, 450, 401, 45);
      startBtn.setContentAreaFilled(false);
      startBtn.setBorderPainted(false);
      add(startBtn);

      ImageIcon Rank = new ImageIcon("Ranking.jpg");
      rankBtn = new JButton(Rank);
      rankBtn.setBounds(345, 520, 288, 45);
      rankBtn.setContentAreaFilled(false);
      rankBtn.setBorderPainted(false);
      add(rankBtn);

      setVisible(true);

      startBtn.addActionListener(new ActionListener() {
         /**
          * ��ư�� �������� GameScreen�гη� ��ȯ ó���ϴ� �޼ҵ�
          */
         public void actionPerformed(ActionEvent event) {
            stage.offStageSound();
            game.change("GameScreen");
            
         }
      });

      rankBtn.addActionListener(new ActionListener(){
         /**
          * ��ư�� �������� Rank�гη� ��ȯ ó���ϴ� �޼ҵ�
          */
         public void actionPerformed(ActionEvent event){
            stage.offStageSound();
            game.change("Rank");
         }
      });
      
      
   }
   /**
    * ���θ޴��� �׸��� �޼ҵ�
    */
   public void paintComponent(Graphics g) {
      g.drawImage(backGround, 0, 0, this);
   }

}