package com.smeedaviation.quadcopter.application;


import org.jfree.ui.RefineryUtilities;

import com.smeedaviation.quadcopter.model.AbstractModel;
import com.smeedaviation.quadcopter.model.AccelerationModel;
import com.smeedaviation.quadcopter.model.GyrationModel;
import com.smeedaviation.quadcopter.model.MotorModel;
import com.smeedaviation.quadcopter.serial.TwoWaySerialComm;
import com.smeedaviation.quadcopter.view.AccelerationGraph;
import com.smeedaviation.quadcopter.view.GyrationGraph;
import com.smeedaviation.quadcopter.view.MotorGraph;

public class SmeedAviation {

	public static void main(String[] args) {

		AccelerationModel accelModel = new AccelerationModel();
		GyrationModel gyroModel = new GyrationModel();
		MotorModel motorModel = new MotorModel();
		TwoWaySerialComm comm = new TwoWaySerialComm();
		
		AccelerationGraph accelerationGraph = new AccelerationGraph("Acceleration Graph");
		accelerationGraph.pack();
		accelModel.addObserver(accelerationGraph);
		RefineryUtilities.centerFrameOnScreen(accelerationGraph);
		accelerationGraph.setVisible(true);
		
		GyrationGraph gyrationGraph = new GyrationGraph("Gyration Graph");
		gyrationGraph.pack();
		gyroModel.addObserver(gyrationGraph);
		RefineryUtilities.centerFrameOnScreen(gyrationGraph);
		gyrationGraph.setVisible(true);
		
		MotorGraph motorGraph = new MotorGraph("Motor Graph");
		motorGraph.pack();
		motorModel.addObserver(motorGraph);
		RefineryUtilities.centerFrameOnScreen(motorGraph);
		motorGraph.setVisible(true);

		try {
			comm.connect(accelModel, gyroModel, motorModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		new Thread(new WorkBitch(motorModel)).start();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static class WorkBitch implements Runnable {

		AbstractModel motorModel;
		
		public WorkBitch(MotorModel model) {
			this.motorModel = model;
		}

		@Override
		public void run() {			
			int count = 0;
			
			while (true) {
//				count = count%255;
				int[] motorData = {count, count, count, count};
				motorModel.setData(motorData);
				
				count++;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}

}
