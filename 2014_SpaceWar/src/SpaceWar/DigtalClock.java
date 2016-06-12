package SpaceWar;

/**
 * 타이머로 동작하는 클래스
 * @author 정윤철
 * @since 2014.11.13
 * @version 1.0
 */
public class DigtalClock{
	private Time time;
	private Thread t1;
	private Thread t2;
	private Thread t3;
	private Switch swtch1;
	private Switch swtch2;
	private Second second;
	private Minute minute;
	private Hour hour;
	
	/**
	 * DigtalClock의 생성자
	 */
	public DigtalClock(){

	}
	/**
	 * 타이머를 작동시킨다.
	 */
	public void exeClock(){
		time = new Time();
		swtch1 = new Switch();
		swtch2 = new Switch();
		second = new Second(swtch1, time);
		t1 = new Thread(second);
		minute = new Minute(swtch1, swtch2, time);
		t2 = new Thread(minute);
		hour = new Hour(swtch2, time);
		t3 = new Thread(hour);
		t1.start();
		t2.start();
		t3.start();
	}
	/**
	 * 타이머를 멈춘다.
	 */
	public void stopClock(){
		t1.stop();
		t2.stop();
		t3.stop();
	}
	/**
	 * 타이머의 hour값을 얻는다.
	 * @return 타이머의 hour
	 */
	public int getHour(){
		return time.getHour();
	}
	/**
	 * 타이머의 second값을 얻는다.
	 * @return 타이머의 second
	 */
	public int getSecond(){
		return time.getSecond();
	}
	/**
	 * 타이머의 minute값을 얻는다.
	 * @return 타이머의 minute
	 */
	public int getMinute(){
		return time.getMinute();
	}
}


class Time{
	private int hour;
	private int minute;
	private int second;
	private Thread t1;
	private Thread t2;
	private Thread t3;

	public void setHour(int h){
		hour = h;
	}
	public void setMinute(int m){
		minute = m;
	}
	public void setSecond(int s){
		second = s;
	}
	public int getHour(){
		return hour;
	}
	public int getMinute(){
		return minute;
	}
	public int getSecond(){
		return second;
	}
	public void increaseHour(){
		hour++;
	}
	public void increaseMinute(){
		minute++;
	}
	public void increaseSecond(){
		second++;
	}
	public void printTime(){
		System.out.println(hour + ":" + minute + ":" + second);
	}
/*	public static void main(String[] args) {
		DigtalClock a = new DigtalClock();
		a.exeClock();
	}*/
}

class Second implements Runnable {
	private boolean flag;
	private Time t;
	private Switch swtch;
	public Second(Switch swtch, Time time){
		this.swtch = swtch;
		t = time;
		flag = true;
	}
	public void run() {
		while(flag) {
			try{
				Thread.sleep(1000);
			}catch (InterruptedException e){}
			if(t.getSecond() == 59){
				swtch.off();
				t.setSecond(0);
			}
			else {
				t.increaseSecond();
				t.printTime();
			}
		}
	}
	public void stop(){
		flag = false;
	}
}
class Minute implements Runnable {
	private boolean flag;
	private Time t;
	private Switch swtch1;
	private Switch swtch2;
	public Minute(Switch swtch1, Switch swtch2, Time time){
		this.swtch1 = swtch1;
		this.swtch2 = swtch2;
		t = time;
		flag = true;
	}
	public void run() {
		while(flag) {
			swtch1.on();
			if(t.getMinute() == 59){
				swtch2.off();
				t.setMinute(0);
			}else {
				t.increaseMinute();
				t.printTime();
			}
		}
	}
	public void stop(){
		flag = false;
	}
}
class Hour implements Runnable {
	private boolean flag;
	private Time t;
	private Switch swtch;
	public Hour(Switch swtch, Time time){
		this.swtch = swtch;
		t = time;
		flag = true;
	}
	public void run() {
		while(flag) {
			swtch.on();
			if(t.getHour() == 23)
				t.setHour(0);
			else {
				t.increaseHour();;
				t.printTime();
			}
		}
	}
	
	public void stop(){
		flag = false;
	}
}
class Switch{
	private boolean inUse = true;
	public synchronized void on() {
		while(inUse) {
			try{
				wait();
			}
			catch(InterruptedException e){};
		}
		inUse = true;
	}
	public synchronized void off() {
		inUse = false;
		notify();
	}
}
