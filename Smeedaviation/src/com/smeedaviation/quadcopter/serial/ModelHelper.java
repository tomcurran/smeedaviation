package com.smeedaviation.quadcopter.serial;

import com.smeedaviation.quadcopter.model.AbstractModel;
import com.smeedaviation.quadcopter.model.AccelerationModel;
import com.smeedaviation.quadcopter.model.GyrationModel;

public class ModelHelper {
	
	private AbstractModel accelModel, gyroModel;
	
	public ModelHelper(AccelerationModel accelModel, GyrationModel gyroModel) {
		this.accelModel = accelModel;
		this.gyroModel = gyroModel;
	}
	
	public void setSensorData(String newSensorData) {		
		newSensorData = newSensorData.replaceAll("(\\r|\\n)", "");
		String[] arguments = newSensorData.split(";");
		if (arguments.length != 6) {
			// invalid arguments
			return;
		}
		
		int[] newAccelValues = new int[3];
		int[] newGyroValues = new int[3];
		int count = 0;
		
		for (int i = 0; i < newAccelValues.length; i++) {
			newAccelValues[i] = Integer.parseInt(arguments[count].split(":")[1]);
			count++;
		}
		
		for (int i = 0; i < newGyroValues.length; i++) {
			newGyroValues[i] = Integer.parseInt(arguments[count].split(":")[1]);
			count++;
		}
		
		this.accelModel.setData(newAccelValues);
		this.gyroModel.setData(newGyroValues);
	}
}
