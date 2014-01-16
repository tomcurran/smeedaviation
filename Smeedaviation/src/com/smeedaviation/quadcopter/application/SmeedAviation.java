package com.smeedaviation.quadcopter.application;


import com.smeedaviation.quadcopter.control.MotorDriver;
import com.smeedaviation.quadcopter.model.AccelerationModel;
import com.smeedaviation.quadcopter.model.ControlModel;
import com.smeedaviation.quadcopter.model.GyrationModel;
import com.smeedaviation.quadcopter.model.MotorModel;
import com.smeedaviation.quadcopter.serial.TwoWaySerialComm;
import com.smeedaviation.quadcopter.smeedmote.SmeedWiiMain;
import com.smeedaviation.quadcopter.view.AccelerationGraph;
import com.smeedaviation.quadcopter.view.ComplementaryFilterGraph;
import com.smeedaviation.quadcopter.view.GyrationGraph;
import com.smeedaviation.quadcopter.view.MotorGraph;

public class SmeedAviation {

	public static void main(String[] args) {

		AccelerationModel accelModel = new AccelerationModel();
		GyrationModel gyroModel = new GyrationModel();
		MotorModel motorModel = new MotorModel();
		ControlModel controlModel = new ControlModel();
		
		TwoWaySerialComm comm = new TwoWaySerialComm();
		
		try {
			comm.connect(accelModel, gyroModel, motorModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		AccelerationGraph accelerationGraph = new AccelerationGraph("Acceleration Graph");
		accelModel.addObserver(accelerationGraph);
		
		GyrationGraph gyrationGraph = new GyrationGraph("Gyration Graph");
		gyroModel.addObserver(gyrationGraph);
		
		MotorGraph motorGraph = new MotorGraph("Motor Graph");
		motorModel.addObserver(motorGraph);

		ComplementaryFilterGraph cfg = new ComplementaryFilterGraph("Orientation Graph", accelModel, gyroModel);
		accelModel.addObserver(cfg);
		gyroModel.addObserver(cfg);
		
		new SmeedWiiMain(controlModel);

		controlModel.addObserver(new MotorDriver(motorModel));
		
	}

	
//	public static class WorkBitch implements Runnable {
//
//		AbstractModel motorModel;
//		
//		public WorkBitch(MotorModel model) {
//			this.motorModel = model;
//		}
//
//		@Override
//		public void run() {			
//			int count = 0;
//			
//			while (true) {
////				count = count%255;
//				int[] motorData = {count, count, count, count};
//				motorModel.setData(motorData);
//				
//				count++;
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//		}
//		
//	}

}
