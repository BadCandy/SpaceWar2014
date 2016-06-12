package SpaceWar;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Util.Stage;
/**
 * 효과음, 배경음악의 볼륨조절을 위한 프레임
 * @author 정윤철
 * @version 1.0
 */
public class VolunmFrame extends JFrame{
	private Container contentPane;
	private JSlider[] sl = new JSlider[2];
	private double bgVolunm;
	private double seVolunm;
	private Clip backGroundClip;
	private Clip effectClip;
	/**
	 * VolunmFrame의 생성자
	 * @param b 배경음악 볼륨 파라미터
	 * @param s 효과음 볼륨 파라미터
	 * @param c 클립 파라미터
	 */
	VolunmFrame(double b, double s, Clip c){
		setTitle("Volunm");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(300,100);
		setVisible(true);
		setResizable(false);
		setLocation(getWidth()/4, getHeight()/4);
		bgVolunm = b;
		seVolunm = s;
		contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		backGroundClip = Stage.isExeClip();
		effectClip = GameScreen.isExeClip();
		JLabel bgLabel = new JLabel("BackGround");
		JLabel seLabel = new JLabel("SoundEffect");
		
		
		

		for (int i = 0; i < sl.length; i++) {
			if(i == 0)
				sl[i] = new JSlider(JSlider.HORIZONTAL,0, 100, (int)(Stage.getVolunm()*100)); // (int)bgVolunm
			if(i == 1)
				sl[i] = new JSlider(JSlider.HORIZONTAL,0, 100, (int)(GameScreen.getVolunm()*100)); // (int)bgVolunm
			sl[i].setPaintLabels(false);
			sl[i].setPaintTicks(false);
			sl[i].setPaintTrack(true);
			sl[i].setMajorTickSpacing(50);
			sl[i].setMinorTickSpacing(10);
			sl[i].addChangeListener(new VolunmChangeListener());
			if(i == 0){
				contentPane.add(bgLabel);
				contentPane.add(sl[i]);
				
			}
			else{
				contentPane.add(seLabel);
				contentPane.add(sl[i]);
				
			}
		}

		bgVolunm = sl[0].getValue()*0.01;
		seVolunm = sl[1].getValue()*0.01;
		
		Stage.setVolunm(bgVolunm);
		GameScreen.setVolunm(bgVolunm);
		


	}
	/**
	 * 볼륨 조절을 했을때 발생하는 이벤트를 처리하는 ChangeListener
	 * @author 정윤철
	 * @version 1.0
	 */
	class VolunmChangeListener implements ChangeListener{
		public void stateChanged(ChangeEvent e){
			bgVolunm = sl[0].getValue()*0.01;
			System.out.println(bgVolunm);
			
			if(backGroundClip != null){
				backGroundClip.stop();
			}
			FloatControl gainControl = (FloatControl) backGroundClip.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (Math.log(bgVolunm) / Math.log(10.0) * 20.0);
			gainControl.setValue(dB);	
			BooleanControl muteControl = (BooleanControl) backGroundClip.getControl(BooleanControl.Type.MUTE);
			muteControl.setValue(false);
			Stage.setVolunm(bgVolunm);
			backGroundClip.start();
			seVolunm = sl[1].getValue()*0.01;
			
			Stage.setVolunm(bgVolunm);
			GameScreen.setVolunm(seVolunm);
		}
	}
}

