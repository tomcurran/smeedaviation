package com.smeedaviation.quadcopter.smeedmote;

import java.util.Observable;
import java.util.Observer;

import com.smeedaviation.quadcopter.model.AbstractModel;
import com.smeedaviation.quadcopter.model.ControlModel;

public class AviationControl implements Observer{
	
	private QuadControlState qcs;
	private AbstractModel cm;
	
	public AviationControl(QuadControlState qcs, ControlModel cm){
		this.qcs = qcs;
		this.cm = cm;
	}
	
	public void control(){
		int[] newControlData = new int[6];
		
		if(qcs.getAltitudeUp()){
			System.out.println("Altitiude...Up");
			newControlData[0] = 1;
		}
		if(qcs.getAltitudeDown()){
			System.out.println("Altitiude...Down");
			newControlData[1] = 1;
		}
		if(qcs.getForward()){
			System.out.println("Forward...");
			newControlData[2] = 1;
		}
		if(qcs.getBackward()){
			System.out.println("Back...");
			newControlData[3] = 1;
		}
		if(qcs.getBankLeft()){
			System.out.println("Left...");
			newControlData[4] = 1;
		}
		if(qcs.getBankRight()){
			System.out.println("Right...");
			newControlData[5] = 1;
		}
		
		cm.setData(newControlData);
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		control();
		
	}
}
