package com.smeedaviation.quadcopter.application;


import com.smeedaviation.quadcopter.model.SynchronizedModel;
import com.smeedaviation.quadcopter.serial.TwoWaySerialComm;

public class SmeedAviation {

	public static void main(String[] args) {

		SynchronizedModel model = new SynchronizedModel();
		TwoWaySerialComm comm = new TwoWaySerialComm(model);
		try {
			comm.connect("COM7");
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
			int count = 0;
			while (true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int mod = count++%3;
				switch (mod)
				{
				case (0): {
					int[] motorData = {255, 0, 0, 0};
					model.setMotorData(motorData);
					break;
				}
				case (1): {
					int[] motorData = {0, 255, 0, 0};
					model.setMotorData(motorData);
					break;
				}
				case (2): {
					int[] motorData = {0, 0, 255, 0};
					model.setMotorData(motorData);
					break;
				}
				}

			}
			
		}
		
	}

}
