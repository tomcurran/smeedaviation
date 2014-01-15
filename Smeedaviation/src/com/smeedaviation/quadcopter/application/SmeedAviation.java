package com.smeedaviation.quadcopter.application;


import com.smeedaviation.quadcopter.model.SynchronizedModel;
import com.smeedaviation.quadcopter.serial.TwoWaySerialComm;
import com.smeedaviation.quadcopter.view.AccelerationGraph;
import com.smeedaviation.quadcopter.view.GyrationGraph;
import com.smeedaviation.quadcopter.view.MotorGraph;

public class SmeedAviation {

	public static void main(String[] args) {

		SynchronizedModel model = new SynchronizedModel();
		TwoWaySerialComm comm = new TwoWaySerialComm(model);
		
		AccelerationGraph ag = new AccelerationGraph("test");
		ag.main(model);
		GyrationGraph gg = new GyrationGraph("test");
		gg.main(model);
		
		MotorGraph mg = new MotorGraph("test");
		mg.main(model);

		try {
			comm.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		new Thread(new WorkBitch(model)).start();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static class WorkBitch implements Runnable {

		SynchronizedModel model;
		
		public WorkBitch(SynchronizedModel model) {
			this.model = model;
		}
		
		@Override
		public void run() {
			int[] motorData = {200, 200, 200, 200};
			model.setMotorData(motorData);
			
//			int count = 0;
//			while (true) {
//				try {
//					Thread.sleep(100);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				int mod = count++%3;
//				switch (mod)
//				{
//					case (0): {
//						int[] motorData = {255, 0, 0, 0};
//						model.setMotorData(motorData);
//						break;
//					}
//					case (1): {
//						int[] motorData = {0, 255, 0, 0};
//						model.setMotorData(motorData);
//						break;
//					}
//					case (2): {
//						int[] motorData = {0, 0, 255, 0};
//						model.setMotorData(motorData);
//						break;
//					}
//				}
//			}
			
		}
		
	}

}
