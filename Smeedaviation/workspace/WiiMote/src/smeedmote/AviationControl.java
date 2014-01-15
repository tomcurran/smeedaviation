package smeedmote;

import java.util.Observable;
import java.util.Observer;

public class AviationControl implements Observer{
	
	private Thread controlThread;
	private String latestControlString;
	private QuadControlState qcs;
	
	public AviationControl(QuadControlState qcs){
		latestControlString = "";
		this.qcs = qcs;
		
//		controlThread = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				controlLoop();	
//			}
//		});
//		controlThread.setPriority(Thread.MAX_PRIORITY);
//		controlThread.start();
		
	}
	
	public void control(){
		if(qcs.getThrust()){
			System.out.println("Thrust...");
		}
		if(qcs.getForward()){
			System.out.println("Forward...");
		}
		if(qcs.getBankLeft()){
			System.out.println("Left...");
		}
		if(qcs.getBankRight()){
			System.out.println("Right...");
		}
		if(qcs.getBackward()){
			System.out.println("Back...");
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		control();
	}
}
