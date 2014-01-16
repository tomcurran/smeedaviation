package com.smeedaviation.quadcopter.smeedmote;

import java.util.Observable;
import java.util.Observer;

import com.smeedaviation.quadcopter.model.ControlModel;

public class AviationControl implements Observer{
	
	private QuadControlState qcs;
	private int[] controlModelInput;
	private ControlModel cm;
	
	public AviationControl(QuadControlState qcs, ControlModel cm){
		this.qcs = qcs;
		this.cm = cm;
		
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
		controlModelInput = cm.getData();
		
		if(qcs.getAltitudeUp()){
			System.out.println("Altitiude...Up");
		}
		if(qcs.getAltitudeDown()){
			System.out.println("Altitiude...Down");
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
