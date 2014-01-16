package com.smeedaviation.quadcopter.model;

public class AccelerationModel extends AbstractModel {

	private int[] accelerationModel = {0,0,0};

	@Override
	protected boolean inputValid(int[] data) {
		return data.length == 3;
	}

	@Override
	protected void updateData(int[] data) {
		accelerationModel = data.clone() ;
	}

	@Override
	protected int[] returnData() {
		return accelerationModel.clone();
	}
}
