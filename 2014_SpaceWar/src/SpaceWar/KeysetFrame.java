package SpaceWar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Core.GameObject;
/**
 * 키세팅을 하기위해 구현한 프레임.
 * @author 정윤철
 * @since 2014.12.05
 * @version 1.0
 * @see GameObject
 */
public class KeysetFrame extends JFrame {
   private JTextField upTf, downTf, leftTf, rightTf, shootTf, bombTf;
   private JLabel upLb, downLb, leftLb, rightLb, shootLb, bombLb;

   KeysetFrame() {
      setTitle("KeySetting");
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setSize(240, 220);
      setVisible(true);
      setLayout(null);

      upLb = new JLabel("UP");
      downLb = new JLabel("DOWN");
      leftLb = new JLabel("LEFT");
      rightLb = new JLabel("RIGHT");
      shootLb = new JLabel("SHOOT");
      bombLb = new JLabel("BOMB");

      upLb.setSize(50, 30);
      downLb.setSize(50, 30);
      leftLb.setSize(50, 30);
      rightLb.setSize(50, 30);
      shootLb.setSize(50, 30);
      bombLb.setSize(50, 30);

      upLb.setLocation(65, 10);
      downLb.setLocation(60, 110);
      leftLb.setLocation(10, 60);
      rightLb.setLocation(110, 60);
      shootLb.setLocation(165, 30);
      bombLb.setLocation(168, 110);

      upTf = new JTextField();
      downTf = new JTextField();
      leftTf = new JTextField();
      rightTf = new JTextField();
      shootTf = new JTextField();
      bombTf = new JTextField();

      upTf.setSize(30, 30);
      downTf.setSize(30, 30);
      leftTf.setSize(30, 30);
      rightTf.setSize(30, 30);
      shootTf.setSize(30, 30);
      bombTf.setSize(30, 30);

      upTf.setLocation(60, 30);
      downTf.setLocation(60, 130);
      leftTf.setLocation(10, 80);
      rightTf.setLocation(110, 80);
      shootTf.setLocation(170, 50);
      bombTf.setLocation(170, 130);

      upTf.addActionListener(new MyActionListener());
      downTf.addActionListener(new MyActionListener());
      leftTf.addActionListener(new MyActionListener());
      rightTf.addActionListener(new MyActionListener());
      shootTf.addActionListener(new MyActionListener());
      bombTf.addActionListener(new MyActionListener());

      add(upTf);
      add(downTf);
      add(leftTf);
      add(rightTf);
      add(shootTf);
      add(bombTf);

      add(upLb);
      add(downLb);
      add(leftLb);
      add(rightLb);
      add(shootLb);
      add(bombLb);
   }
   /**
    * 키세팅으로 인한 이벤트가 발생했을때 처리하는 액션리스너
    * @author 정윤철
    * @version 1.0
    */
   class MyActionListener implements ActionListener {

      private int x = 0;

      public void actionPerformed(ActionEvent e) {

         if (e.getSource() == upTf) {

            x = upTf.getText().toUpperCase().charAt(0);
            GameScreen.setUpKey(x);
         } else if (e.getSource() == downTf) {
            x = downTf.getText().toUpperCase().charAt(0);
            GameScreen.setDownKey(x);

         } else if (e.getSource() == leftTf) {
            x = leftTf.getText().toUpperCase().charAt(0);
            GameScreen.setLeftKey(x);

         } else if (e.getSource() == rightTf) {
            x = rightTf.getText().toUpperCase().charAt(0);
            GameScreen.setRightKey(x);

         } else if (e.getSource() == shootTf) {
            x = shootTf.getText().toUpperCase().charAt(0);
            GameScreen.setShootKey(x);

         } else if (e.getSource() == bombTf) {
            x = bombTf.getText().toUpperCase().charAt(0);
            GameScreen.setBombKey(x);

         }

      }

   }
}