package com.smeedaviation.quadcopter.control;

import java.util.Observable;
import java.util.Observer;

import com.smeedaviation.quadcopter.model.AbstractModel;
import com.smeedaviation.quadcopter.model.ControlModel;
import com.smeedaviation.quadcopter.model.MotorModel;

public class MotorDriver implements Observer {
	
	private AbstractModel moterModel;
	
	public MotorDriver(MotorModel moterModel) {
		this.moterModel = moterModel;
	}

	@Override
	public void update(Observable o, Object arg) {
		AbstractModel controlModel = (ControlModel)o;
		
		int[] currentMoterValues = this.moterModel.getData();
		int[] currentControlValues = controlModel.getData();
				
		if (currentControlValues[0] == 1) {
			for (int i = 0; i < currentMoterValues.length; i++) {			
				currentMoterValues[i] += 10;
			}

		} 
		if (currentControlValues[1] == 1) {
			for (int i = 0; i < currentMoterValues.length; i++) {			
				currentMoterValues[i] -= 10;
			}
		}
		if (currentControlValues[2] == 1) {
			// left down
			currentMoterValues[0] -= 5;
			currentMoterValues[3] -= 5;
			// right up
			currentMoterValues[1] += 5;
			currentMoterValues[2] += 5;
		}
		if (currentControlValues[3] == 1) {
			// left up
			currentMoterValues[0] += 5;
			currentMoterValues[3] += 5;
			// right down
			currentMoterValues[1] -= 5;
			currentMoterValues[2] -= 5;
		}
		if (currentControlValues[4] == 1) {
			// front down
			currentMoterValues[0] -= 5;
			currentMoterValues[1] -= 5;
			// back up
			currentMoterValues[2] += 5;
			currentMoterValues[3] += 5;
		}
		if (currentControlValues[5] == 1) {
			// front up
			currentMoterValues[0] += 5;
			currentMoterValues[1] += 5;
			// back down
			currentMoterValues[2] -= 5;
			currentMoterValues[3] -= 5;
		}
		
		for (int i = 0; i < currentMoterValues.length; i++) {
			if (currentMoterValues[i] < 0) {
				currentMoterValues[i] = 0;
			}
			
			if (currentMoterValues[i] > 255) {
				currentMoterValues[i] = 255;
			}
		}
		
		moterModel.setData(currentMoterValues);
	}

}
